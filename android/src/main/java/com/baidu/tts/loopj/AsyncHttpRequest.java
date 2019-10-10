/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.http.Header
 *  org.apache.http.HttpResponse
 *  org.apache.http.client.HttpRequestRetryHandler
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.impl.client.AbstractHttpClient
 *  org.apache.http.protocol.HttpContext
 */
package com.baidu.tts.loopj;

import com.baidu.tts.loopj.AsyncHttpClient;
import com.baidu.tts.loopj.LogInterface;
import com.baidu.tts.loopj.RangeFileAsyncHttpResponseHandler;
import com.baidu.tts.loopj.ResponseHandlerInterface;
import com.baidu.tts.loopj.Utils;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;

public class AsyncHttpRequest
implements Runnable {
    private final AbstractHttpClient client;
    private final HttpContext context;
    private final HttpUriRequest request;
    private final ResponseHandlerInterface responseHandler;
    private int executionCount;
    private final AtomicBoolean isCancelled = new AtomicBoolean();
    private boolean cancelIsNotified;
    private volatile boolean isFinished;
    private boolean isRequestPreProcessed;

    public AsyncHttpRequest(AbstractHttpClient client, HttpContext context, HttpUriRequest request, ResponseHandlerInterface responseHandler) {
        this.client = Utils.notNull(client, "client");
        this.context = Utils.notNull(context, "context");
        this.request = Utils.notNull(request, "request");
        this.responseHandler = Utils.notNull(responseHandler, "responseHandler");
    }

    public void onPreProcessRequest(AsyncHttpRequest request) {
    }

    public void onPostProcessRequest(AsyncHttpRequest request) {
    }

    public void run() {
        if (this.isCancelled()) {
            return;
        }
        if (!this.isRequestPreProcessed) {
            this.isRequestPreProcessed = true;
            this.onPreProcessRequest(this);
        }
        if (this.isCancelled()) {
            return;
        }
        this.responseHandler.sendStartMessage();
        if (this.isCancelled()) {
            return;
        }
        try {
            this.makeRequestWithRetries();
        }
        catch (IOException iOException) {
            if (!this.isCancelled()) {
                this.responseHandler.sendFailureMessage(0, null, null, iOException);
            }
            AsyncHttpClient.log.e("AsyncHttpRequest", "makeRequestWithRetries returned error", iOException);
        }
        if (this.isCancelled()) {
            return;
        }
        this.responseHandler.sendFinishMessage();
        if (this.isCancelled()) {
            return;
        }
        this.onPostProcessRequest(this);
        this.isFinished = true;
    }

    private void makeRequest() throws IOException {
        if (this.isCancelled()) {
            return;
        }
        if (this.request.getURI().getScheme() == null) {
            throw new MalformedURLException("No valid URI scheme was provided");
        }
        if (this.responseHandler instanceof RangeFileAsyncHttpResponseHandler) {
            ((RangeFileAsyncHttpResponseHandler)this.responseHandler).updateRequestHeaders(this.request);
        }
        HttpResponse httpResponse = this.client.execute(this.request, this.context);
        if (this.isCancelled()) {
            return;
        }
        this.responseHandler.onPreProcessResponse(this.responseHandler, httpResponse);
        if (this.isCancelled()) {
            return;
        }
        this.responseHandler.sendResponseMessage(httpResponse);
        if (this.isCancelled()) {
            return;
        }
        this.responseHandler.onPostProcessResponse(this.responseHandler, httpResponse);
    }

    private void makeRequestWithRetries() throws IOException {
        boolean bl = true;
        IOException iOException = null;
        HttpRequestRetryHandler httpRequestRetryHandler = this.client.getHttpRequestRetryHandler();
        try {
            while (bl) {
                try {
                    this.makeRequest();
                    return;
                }
                catch (UnknownHostException unknownHostException) {
                    iOException = new IOException("UnknownHostException exception: " + unknownHostException.getMessage());
                    bl = this.executionCount >= 0 && httpRequestRetryHandler.retryRequest((IOException)unknownHostException, ++this.executionCount, this.context);
                }
                catch (NullPointerException nullPointerException) {
                    iOException = new IOException("NPE in HttpClient: " + nullPointerException.getMessage());
                    bl = httpRequestRetryHandler.retryRequest(iOException, ++this.executionCount, this.context);
                }
                catch (IOException iOException2) {
                    if (this.isCancelled()) {
                        return;
                    }
                    iOException = iOException2;
                    bl = httpRequestRetryHandler.retryRequest(iOException, ++this.executionCount, this.context);
                }
                if (!bl) continue;
                this.responseHandler.sendRetryMessage(this.executionCount);
            }
        }
        catch (Exception exception) {
            AsyncHttpClient.log.e("AsyncHttpRequest", "Unhandled exception origin cause", exception);
            iOException = new IOException("Unhandled exception: " + exception.getMessage());
        }
        throw iOException;
    }

    public boolean isCancelled() {
        boolean bl = this.isCancelled.get();
        if (bl) {
            this.sendCancelNotification();
        }
        return bl;
    }

    private synchronized void sendCancelNotification() {
        if (!this.isFinished && this.isCancelled.get() && !this.cancelIsNotified) {
            this.cancelIsNotified = true;
            this.responseHandler.sendCancelMessage();
        }
    }

    public boolean isDone() {
        return this.isCancelled() || this.isFinished;
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        this.isCancelled.set(true);
        this.request.abort();
        return this.isCancelled();
    }

    public AsyncHttpRequest setRequestTag(Object TAG) {
        this.responseHandler.setTag(TAG);
        return this;
    }

    public Object getTag() {
        return this.responseHandler.getTag();
    }
}

