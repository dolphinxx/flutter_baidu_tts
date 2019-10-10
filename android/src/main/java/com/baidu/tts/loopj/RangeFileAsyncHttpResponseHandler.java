/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.http.Header
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpResponse
 *  org.apache.http.StatusLine
 *  org.apache.http.client.HttpResponseException
 *  org.apache.http.client.methods.HttpUriRequest
 */
package com.baidu.tts.loopj;

import com.baidu.tts.loopj.AsyncHttpClient;
import com.baidu.tts.loopj.FileAsyncHttpResponseHandler;
import com.baidu.tts.loopj.LogInterface;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpUriRequest;

public abstract class RangeFileAsyncHttpResponseHandler
extends FileAsyncHttpResponseHandler {
    private static final String LOG_TAG = "RangeFileAsyncHttpRH";
    private long current = 0L;
    private boolean append = false;

    public RangeFileAsyncHttpResponseHandler(File file) {
        super(file);
    }

    public void sendResponseMessage(HttpResponse response) throws IOException {
        if (!Thread.currentThread().isInterrupted()) {
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == 416) {
                if (!Thread.currentThread().isInterrupted()) {
                    this.sendSuccessMessage(statusLine.getStatusCode(), response.getAllHeaders(), null);
                }
            } else if (statusLine.getStatusCode() >= 300) {
                if (!Thread.currentThread().isInterrupted()) {
                    this.sendFailureMessage(statusLine.getStatusCode(), response.getAllHeaders(), null, (Throwable)new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase()));
                }
            } else if (!Thread.currentThread().isInterrupted()) {
                Header header = response.getFirstHeader("Content-Range");
                if (header == null) {
                    this.append = false;
                    this.current = 0L;
                } else {
                    AsyncHttpClient.log.v(LOG_TAG, "Content-Range: " + header.getValue());
                }
                this.sendSuccessMessage(statusLine.getStatusCode(), response.getAllHeaders(), this.getResponseData(response.getEntity()));
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected byte[] getResponseData(HttpEntity entity) throws IOException {
        if (entity != null) {
            InputStream inputStream = entity.getContent();
            long l2 = entity.getContentLength() + this.current;
            FileOutputStream fileOutputStream = new FileOutputStream(this.getTargetFile(), this.append);
            if (inputStream != null) {
                try {
                    int n2;
                    byte[] arrby = new byte[4096];
                    while (this.current < l2 && (n2 = inputStream.read(arrby)) != -1 && !Thread.currentThread().isInterrupted()) {
                        this.current += (long)n2;
                        fileOutputStream.write(arrby, 0, n2);
                        this.sendProgressMessage(this.current, l2);
                    }
                }
                finally {
                    inputStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            }
        }
        return null;
    }

    public void updateRequestHeaders(HttpUriRequest uriRequest) {
        if (this.file.exists() && this.file.canWrite()) {
            this.current = this.file.length();
        }
        if (this.current > 0L) {
            this.append = true;
            uriRequest.setHeader("Range", "bytes=" + this.current + "-");
        }
    }
}

