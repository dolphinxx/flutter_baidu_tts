/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  org.apache.http.Header
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpResponse
 *  org.apache.http.StatusLine
 *  org.apache.http.client.HttpResponseException
 *  org.apache.http.util.ByteArrayBuffer
 */
package com.baidu.tts.loopj;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.baidu.tts.loopj.AsyncHttpClient;
import com.baidu.tts.loopj.LogInterface;
import com.baidu.tts.loopj.ResponseHandlerInterface;
import com.baidu.tts.loopj.Utils;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URI;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.util.ByteArrayBuffer;

public abstract class AsyncHttpResponseHandler
implements ResponseHandlerInterface {
    private static final String LOG_TAG = "AsyncHttpRH";
    protected static final int SUCCESS_MESSAGE = 0;
    protected static final int FAILURE_MESSAGE = 1;
    protected static final int START_MESSAGE = 2;
    protected static final int FINISH_MESSAGE = 3;
    protected static final int PROGRESS_MESSAGE = 4;
    protected static final int RETRY_MESSAGE = 5;
    protected static final int CANCEL_MESSAGE = 6;
    protected static final int BUFFER_SIZE = 4096;
    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final String UTF8_BOM = "\ufeff";
    private String responseCharset = "UTF-8";
    private Handler handler;
    private boolean useSynchronousMode;
    private boolean usePoolThread;
    private URI requestURI = null;
    private Header[] requestHeaders = null;
    private Looper looper = null;
    private WeakReference<Object> TAG = new WeakReference<Object>(null);

    public AsyncHttpResponseHandler() {
        this(null);
    }

    public AsyncHttpResponseHandler(Looper looper) {
        this.looper = looper == null ? Looper.myLooper() : looper;
        this.setUseSynchronousMode(false);
        this.setUsePoolThread(false);
    }

    public AsyncHttpResponseHandler(boolean usePoolThread) {
        this.setUsePoolThread(usePoolThread);
        if (!this.getUsePoolThread()) {
            this.looper = Looper.myLooper();
            this.setUseSynchronousMode(false);
        }
    }

    public void setTag(Object TAG) {
        this.TAG = new WeakReference<Object>(TAG);
    }

    public Object getTag() {
        return this.TAG.get();
    }

    public URI getRequestURI() {
        return this.requestURI;
    }

    public Header[] getRequestHeaders() {
        return this.requestHeaders;
    }

    public void setRequestURI(URI requestURI) {
        this.requestURI = requestURI;
    }

    public void setRequestHeaders(Header[] requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public boolean getUseSynchronousMode() {
        return this.useSynchronousMode;
    }

    public void setUseSynchronousMode(boolean sync) {
        if (!sync && this.looper == null) {
            sync = true;
        }
        if (!sync && this.handler == null) {
            this.handler = new ResponderHandler(this, this.looper);
        } else if (sync && this.handler != null) {
            this.handler = null;
        }
        this.useSynchronousMode = sync;
    }

    public boolean getUsePoolThread() {
        return this.usePoolThread;
    }

    public void setUsePoolThread(boolean pool) {
        if (pool) {
            this.looper = null;
            this.handler = null;
        }
        this.usePoolThread = pool;
    }

    public void setCharset(String charset) {
        this.responseCharset = charset;
    }

    public String getCharset() {
        return this.responseCharset == null ? DEFAULT_CHARSET : this.responseCharset;
    }

    public void onProgress(long bytesWritten, long totalSize) {
        AsyncHttpClient.log.v(LOG_TAG, String.format("Progress %d from %d (%2.0f%%)", bytesWritten, totalSize, totalSize > 0L ? (double)bytesWritten * 1.0 / (double)totalSize * 100.0 : -1.0));
    }

    public void onStart() {
    }

    public void onFinish() {
    }

    public void onPreProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
    }

    public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
    }

    public abstract void onSuccess(int var1, Header[] var2, byte[] var3);

    public abstract void onFailure(int var1, Header[] var2, byte[] var3, Throwable var4);

    public void onRetry(int retryNo) {
        AsyncHttpClient.log.d("AsyncHttpRH", String.format("Request retry no. %d", retryNo));
    }

    public void onCancel() {
        AsyncHttpClient.log.d("AsyncHttpRH", "Request got cancelled");
    }

    public void onUserException(Throwable error) {
        AsyncHttpClient.log.e("AsyncHttpRH", "User-space exception detected!", error);
        throw new RuntimeException(error);
    }

    public final void sendProgressMessage(long bytesWritten, long bytesTotal) {
        this.sendMessage(this.obtainMessage(4, new Object[]{bytesWritten, bytesTotal}));
    }

    public final void sendSuccessMessage(int statusCode, Header[] headers, byte[] responseBytes) {
        this.sendMessage(this.obtainMessage(0, new Object[]{statusCode, headers, responseBytes}));
    }

    public final void sendFailureMessage(int statusCode, Header[] headers, byte[] responseBody, Throwable throwable) {
        this.sendMessage(this.obtainMessage(1, new Object[]{statusCode, headers, responseBody, throwable}));
    }

    public final void sendStartMessage() {
        this.sendMessage(this.obtainMessage(2, null));
    }

    public final void sendFinishMessage() {
        this.sendMessage(this.obtainMessage(3, null));
    }

    public final void sendRetryMessage(int retryNo) {
        this.sendMessage(this.obtainMessage(5, new Object[]{retryNo}));
    }

    public final void sendCancelMessage() {
        this.sendMessage(this.obtainMessage(6, null));
    }

    protected void handleMessage(Message message) {
        try {
            switch (message.what) {
                case 0: {
                    Object[] arrobject = (Object[])message.obj;
                    if (arrobject != null && arrobject.length >= 3) {
                        this.onSuccess((Integer)arrobject[0], (Header[])arrobject[1], (byte[])arrobject[2]);
                        break;
                    }
                    AsyncHttpClient.log.e("AsyncHttpRH", "SUCCESS_MESSAGE didn't got enough params");
                    break;
                }
                case 1: {
                    Object[] arrobject = (Object[])message.obj;
                    if (arrobject != null && arrobject.length >= 4) {
                        this.onFailure((Integer)arrobject[0], (Header[])arrobject[1], (byte[])arrobject[2], (Throwable)arrobject[3]);
                        break;
                    }
                    AsyncHttpClient.log.e("AsyncHttpRH", "FAILURE_MESSAGE didn't got enough params");
                    break;
                }
                case 2: {
                    this.onStart();
                    break;
                }
                case 3: {
                    this.onFinish();
                    break;
                }
                case 4: {
                    Object[] arrobject = (Object[])message.obj;
                    if (arrobject != null && arrobject.length >= 2) {
                        try {
                            this.onProgress((Long)arrobject[0], (Long)arrobject[1]);
                        }
                        catch (Throwable throwable) {
                            AsyncHttpClient.log.e("AsyncHttpRH", "custom onProgress contains an error", throwable);
                        }
                        break;
                    }
                    AsyncHttpClient.log.e("AsyncHttpRH", "PROGRESS_MESSAGE didn't got enough params");
                    break;
                }
                case 5: {
                    Object[] arrobject = (Object[])message.obj;
                    if (arrobject != null && arrobject.length == 1) {
                        this.onRetry((Integer)arrobject[0]);
                        break;
                    }
                    AsyncHttpClient.log.e("AsyncHttpRH", "RETRY_MESSAGE didn't get enough params");
                    break;
                }
                case 6: {
                    this.onCancel();
                }
            }
        }
        catch (Throwable throwable) {
            this.onUserException(throwable);
        }
    }

    protected void sendMessage(Message msg) {
        if (this.getUseSynchronousMode() || this.handler == null) {
            this.handleMessage(msg);
        } else if (!Thread.currentThread().isInterrupted()) {
            Utils.asserts(this.handler != null, "handler should not be null!");
            this.handler.sendMessage(msg);
        }
    }

    protected void postRunnable(Runnable runnable) {
        if (runnable != null) {
            if (this.getUseSynchronousMode() || this.handler == null) {
                runnable.run();
            } else {
                this.handler.post(runnable);
            }
        }
    }

    protected Message obtainMessage(int responseMessageId, Object responseMessageData) {
        return Message.obtain((Handler)this.handler, (int)responseMessageId, (Object)responseMessageData);
    }

    public void sendResponseMessage(HttpResponse response) throws IOException {
        if (!Thread.currentThread().isInterrupted()) {
            StatusLine statusLine = response.getStatusLine();
            byte[] arrby = this.getResponseData(response.getEntity());
            if (!Thread.currentThread().isInterrupted()) {
                if (statusLine.getStatusCode() >= 300) {
                    this.sendFailureMessage(statusLine.getStatusCode(), response.getAllHeaders(), arrby, (Throwable)new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase()));
                } else {
                    this.sendSuccessMessage(statusLine.getStatusCode(), response.getAllHeaders(), arrby);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    byte[] getResponseData(HttpEntity entity) throws IOException {
        InputStream inputStream;
        byte[] arrby = null;
        if (entity != null && (inputStream = entity.getContent()) != null) {
            long l2 = entity.getContentLength();
            if (l2 > Integer.MAX_VALUE) {
                throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
            }
            int n2 = l2 <= 0L ? 4096 : (int)l2;
            try {
                ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(n2);
                try {
                    int n3;
                    byte[] arrby2 = new byte[4096];
                    long l3 = 0L;
                    while ((n3 = inputStream.read(arrby2)) != -1 && !Thread.currentThread().isInterrupted()) {
                        byteArrayBuffer.append(arrby2, 0, n3);
                        this.sendProgressMessage(l3 += (long)n3, l2 <= 0L ? 1L : l2);
                    }
                }
                finally {
                    AsyncHttpClient.silentCloseInputStream(inputStream);
                    AsyncHttpClient.endEntityViaReflection(entity);
                }
                arrby = byteArrayBuffer.toByteArray();
            }
            catch (OutOfMemoryError outOfMemoryError) {
                System.gc();
                throw new IOException("File too large to fit into available memory");
            }
        }
        return arrby;
    }

    private static class ResponderHandler
    extends Handler {
        private final AsyncHttpResponseHandler mResponder;

        ResponderHandler(AsyncHttpResponseHandler mResponder, Looper looper) {
            super(looper);
            this.mResponder = mResponder;
        }

        public void handleMessage(Message msg) {
            this.mResponder.handleMessage(msg);
        }
    }

}

