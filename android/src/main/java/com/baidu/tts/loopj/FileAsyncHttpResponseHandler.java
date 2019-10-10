/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  org.apache.http.Header
 *  org.apache.http.HttpEntity
 */
package com.baidu.tts.loopj;

import android.content.Context;
import com.baidu.tts.loopj.AsyncHttpClient;
import com.baidu.tts.loopj.AsyncHttpResponseHandler;
import com.baidu.tts.loopj.LogInterface;
import com.baidu.tts.loopj.Utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import org.apache.http.Header;
import org.apache.http.HttpEntity;

public abstract class FileAsyncHttpResponseHandler
extends AsyncHttpResponseHandler {
    protected final File file;
    protected final boolean append;
    protected final boolean renameIfExists;
    protected File frontendFile;
    private static final String LOG_TAG = "FileAsyncHttpRH";

    public FileAsyncHttpResponseHandler(File file) {
        this(file, false);
    }

    public FileAsyncHttpResponseHandler(File file, boolean append) {
        this(file, append, false);
    }

    public FileAsyncHttpResponseHandler(File file, boolean append, boolean renameTargetFileIfExists) {
        Utils.asserts(file != null, "File passed into FileAsyncHttpResponseHandler constructor must not be null");
        if (!file.isDirectory() && !file.getParentFile().isDirectory()) {
            Utils.asserts(file.getParentFile().mkdirs(), "Cannot create parent directories for requested File location");
        }
        if (file.isDirectory() && !file.mkdirs()) {
            AsyncHttpClient.log.d(LOG_TAG, "Cannot create directories for requested Directory location, might not be a problem");
        }
        this.file = file;
        this.append = append;
        this.renameIfExists = renameTargetFileIfExists;
    }

    public FileAsyncHttpResponseHandler(Context context) {
        this.file = this.getTemporaryFile(context);
        this.append = false;
        this.renameIfExists = false;
    }

    public boolean deleteTargetFile() {
        return this.getTargetFile() != null && this.getTargetFile().delete();
    }

    protected File getTemporaryFile(Context context) {
        Utils.asserts(context != null, "Tried creating temporary file without having Context");
        try {
            return File.createTempFile("temp_", "_handled", context.getCacheDir());
        }
        catch (IOException iOException) {
            AsyncHttpClient.log.e(LOG_TAG, "Cannot create temporary file", iOException);
            return null;
        }
    }

    protected File getOriginalFile() {
        Utils.asserts(this.file != null, "Target file is null, fatal!");
        return this.file;
    }

    public File getTargetFile() {
        if (this.frontendFile == null) {
            this.frontendFile = this.getOriginalFile().isDirectory() ? this.getTargetFileByParsingURL() : this.getOriginalFile();
        }
        return this.frontendFile;
    }

    protected File getTargetFileByParsingURL() {
        Utils.asserts(this.getOriginalFile().isDirectory(), "Target file is not a directory, cannot proceed");
        Utils.asserts(this.getRequestURI() != null, "RequestURI is null, cannot proceed");
        String string = this.getRequestURI().toString();
        String string2 = string.substring(string.lastIndexOf(47) + 1, string.length());
        File file = new File(this.getOriginalFile(), string2);
        if (file.exists() && this.renameIfExists) {
            String string3 = !string2.contains(".") ? string2 + " (%d)" : string2.substring(0, string2.lastIndexOf(46)) + " (%d)" + string2.substring(string2.lastIndexOf(46), string2.length());
            int n2 = 0;
            do {
                if (!(file = new File(this.getOriginalFile(), String.format(string3, n2))).exists()) {
                    return file;
                }
                ++n2;
            } while (true);
        }
        return file;
    }

    public final void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
        this.onFailure(statusCode, headers, throwable, this.getTargetFile());
    }

    public abstract void onFailure(int var1, Header[] var2, Throwable var3, File var4);

    public final void onSuccess(int statusCode, Header[] headers, byte[] responseBytes) {
        this.onSuccess(statusCode, headers, this.getTargetFile());
    }

    public abstract void onSuccess(int var1, Header[] var2, File var3);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected byte[] getResponseData(HttpEntity entity) throws IOException {
        if (entity != null) {
            InputStream inputStream = entity.getContent();
            long l2 = entity.getContentLength();
            FileOutputStream fileOutputStream = new FileOutputStream(this.getTargetFile(), this.append);
            if (inputStream != null) {
                try {
                    int n2;
                    byte[] arrby = new byte[4096];
                    int n3 = 0;
                    while ((n2 = inputStream.read(arrby)) != -1 && !Thread.currentThread().isInterrupted()) {
                        fileOutputStream.write(arrby, 0, n2);
                        this.sendProgressMessage(n3 += n2, l2);
                    }
                }
                finally {
                    AsyncHttpClient.silentCloseInputStream(inputStream);
                    fileOutputStream.flush();
                    AsyncHttpClient.silentCloseOutputStream(fileOutputStream);
                }
            }
        }
        return null;
    }
}

