/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 *  org.apache.http.NoHttpResponseException
 *  org.apache.http.client.HttpRequestRetryHandler
 *  org.apache.http.conn.ConnectTimeoutException
 *  org.apache.http.protocol.HttpContext
 */
package com.baidu.tts.loopj;

import android.os.SystemClock;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashSet;
import javax.net.ssl.SSLException;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.protocol.HttpContext;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class RetryHandler
implements HttpRequestRetryHandler {
    private static final HashSet<Class<?>> exceptionWhitelist = new HashSet();
    private static final HashSet<Class<?>> exceptionBlacklist = new HashSet();
    private final int maxRetries;
    private final int retrySleepTimeMS;

    public RetryHandler(int maxRetries, int retrySleepTimeMS) {
        this.maxRetries = maxRetries;
        this.retrySleepTimeMS = retrySleepTimeMS;
    }

    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        boolean bl;
        boolean bl2 = true;
        Boolean bl3 = (Boolean)context.getAttribute("http.request_sent");
        boolean bl4 = bl = bl3 != null && bl3 != false;
        if (executionCount > this.maxRetries) {
            bl2 = false;
        } else if (this.isInList(exceptionWhitelist, exception)) {
            bl2 = true;
        } else if (this.isInList(exceptionBlacklist, exception)) {
            bl2 = false;
        } else if (!bl) {
            bl2 = true;
        }
        if (bl2) {
            SystemClock.sleep((long)this.retrySleepTimeMS);
        } else {
            exception.printStackTrace();
        }
        return bl2;
    }

    static void addClassToWhitelist(Class<?> cls) {
        exceptionWhitelist.add(cls);
    }

    static void addClassToBlacklist(Class<?> cls) {
        exceptionBlacklist.add(cls);
    }

    protected boolean isInList(HashSet<Class<?>> list, Throwable error) {
        for (Class<?> class_ : list) {
            if (!class_.isInstance(error)) continue;
            return true;
        }
        return false;
    }

    static {
        exceptionWhitelist.add(NoHttpResponseException.class);
        exceptionWhitelist.add(UnknownHostException.class);
        exceptionWhitelist.add(SocketException.class);
        exceptionWhitelist.add(ConnectTimeoutException.class);
        exceptionWhitelist.add(SocketTimeoutException.class);
        exceptionBlacklist.add(InterruptedIOException.class);
        exceptionBlacklist.add(SSLException.class);
    }
}

