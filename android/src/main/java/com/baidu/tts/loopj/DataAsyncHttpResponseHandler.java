/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Message
 *  org.apache.http.HttpEntity
 *  org.apache.http.util.ByteArrayBuffer
 */
package com.baidu.tts.loopj;

import android.os.Message;
import com.baidu.tts.loopj.AsyncHttpClient;
import com.baidu.tts.loopj.AsyncHttpResponseHandler;
import com.baidu.tts.loopj.LogInterface;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.util.ByteArrayBuffer;

public abstract class DataAsyncHttpResponseHandler
extends AsyncHttpResponseHandler {
    private static final String LOG_TAG = "DataAsyncHttpRH";
    protected static final int PROGRESS_DATA_MESSAGE = 7;

    public void onProgressData(byte[] responseBody) {
        AsyncHttpClient.log.d(LOG_TAG, "onProgressData(byte[]) was not overriden, but callback was received");
    }

    public final void sendProgressDataMessage(byte[] responseBytes) {
        this.sendMessage(this.obtainMessage(7, new Object[]{responseBytes}));
    }

    protected void handleMessage(Message message) {
        super.handleMessage(message);
        switch (message.what) {
            case 7: {
                Object[] arrobject = (Object[])message.obj;
                if (arrobject != null && arrobject.length >= 1) {
                    try {
                        this.onProgressData((byte[])arrobject[0]);
                    }
                    catch (Throwable throwable) {
                        AsyncHttpClient.log.e(LOG_TAG, "custom onProgressData contains an error", throwable);
                    }
                    break;
                }
                AsyncHttpClient.log.e(LOG_TAG, "PROGRESS_DATA_MESSAGE didn't got enough params");
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
            if (l2 < 0L) {
                l2 = 4096L;
            }
            try {
                ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer((int)l2);
                try {
                    int n2;
                    byte[] arrby2 = new byte[4096];
                    int n3 = 0;
                    while ((n2 = inputStream.read(arrby2)) != -1 && !Thread.currentThread().isInterrupted()) {
                        byteArrayBuffer.append(arrby2, 0, n2);
                        this.sendProgressDataMessage(DataAsyncHttpResponseHandler.copyOfRange(arrby2, 0, n2));
                        this.sendProgressMessage(n3, l2);
                    }
                }
                finally {
                    AsyncHttpClient.silentCloseInputStream(inputStream);
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

    public static byte[] copyOfRange(byte[] original, int start, int end) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NullPointerException {
        if (start > end) {
            throw new IllegalArgumentException();
        }
        int n2 = original.length;
        if (start < 0 || start > n2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int n3 = end - start;
        int n4 = Math.min(n3, n2 - start);
        byte[] arrby = new byte[n3];
        System.arraycopy(original, start, arrby, 0, n4);
        return arrby;
    }
}

