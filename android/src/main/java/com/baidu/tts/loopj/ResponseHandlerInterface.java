/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.http.Header
 *  org.apache.http.HttpResponse
 */
package com.baidu.tts.loopj;

import java.io.IOException;
import java.net.URI;
import org.apache.http.Header;
import org.apache.http.HttpResponse;

public interface ResponseHandlerInterface {
    public void sendResponseMessage(HttpResponse var1) throws IOException;

    public void sendStartMessage();

    public void sendFinishMessage();

    public void sendProgressMessage(long var1, long var3);

    public void sendCancelMessage();

    public void sendSuccessMessage(int var1, Header[] var2, byte[] var3);

    public void sendFailureMessage(int var1, Header[] var2, byte[] var3, Throwable var4);

    public void sendRetryMessage(int var1);

    public URI getRequestURI();

    public Header[] getRequestHeaders();

    public void setRequestURI(URI var1);

    public void setRequestHeaders(Header[] var1);

    public void setUseSynchronousMode(boolean var1);

    public boolean getUseSynchronousMode();

    public void setUsePoolThread(boolean var1);

    public boolean getUsePoolThread();

    public void onPreProcessResponse(ResponseHandlerInterface var1, HttpResponse var2);

    public void onPostProcessResponse(ResponseHandlerInterface var1, HttpResponse var2);

    public void setTag(Object var1);

    public Object getTag();
}

