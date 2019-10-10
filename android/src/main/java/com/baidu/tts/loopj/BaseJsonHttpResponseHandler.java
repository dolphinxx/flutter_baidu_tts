/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.http.Header
 */
package com.baidu.tts.loopj;

import com.baidu.tts.loopj.AsyncHttpClient;
import com.baidu.tts.loopj.LogInterface;
import com.baidu.tts.loopj.TextHttpResponseHandler;
import org.apache.http.Header;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class BaseJsonHttpResponseHandler<JSON_TYPE>
extends TextHttpResponseHandler {
    private static final String LOG_TAG = "BaseJsonHttpRH";

    public BaseJsonHttpResponseHandler() {
        this("UTF-8");
    }

    public BaseJsonHttpResponseHandler(String encoding) {
        super(encoding);
    }

    public abstract void onSuccess(int var1, Header[] var2, String var3, JSON_TYPE var4);

    public abstract void onFailure(int var1, Header[] var2, Throwable var3, String var4, JSON_TYPE var5);

    @Override
    public final void onSuccess(final int statusCode, final Header[] headers, final String responseString) {
        if (statusCode != 204) {
            Runnable runnable = new Runnable(){

                public void run() {
                    try {
                        final JSON_TYPE jsonType = BaseJsonHttpResponseHandler.this.parseResponse(responseString, false);
                        BaseJsonHttpResponseHandler.this.postRunnable(new Runnable(){

                            public void run() {
                                BaseJsonHttpResponseHandler.this.onSuccess(statusCode, headers, responseString, jsonType);
                            }
                        });
                    }
                    catch (Throwable throwable) {
                        AsyncHttpClient.log.d(BaseJsonHttpResponseHandler.LOG_TAG, "parseResponse thrown an problem", throwable);
                        BaseJsonHttpResponseHandler.this.postRunnable(new Runnable(){

                            public void run() {
                                BaseJsonHttpResponseHandler.this.onFailure(statusCode, headers, throwable, responseString, null);
                            }
                        });
                    }
                }

            };
            if (!this.getUseSynchronousMode() && !this.getUsePoolThread()) {
                new Thread(runnable).start();
            } else {
                runnable.run();
            }
        } else {
            this.onSuccess(statusCode, headers, null, null);
        }
    }

    @Override
    public final void onFailure(final int statusCode, final Header[] headers, final String responseString, final Throwable throwable) {
        if (responseString != null) {
            Runnable runnable = new Runnable(){

                public void run() {
                    try {
                        final JSON_TYPE jsonType = BaseJsonHttpResponseHandler.this.parseResponse(responseString, true);
                        BaseJsonHttpResponseHandler.this.postRunnable(new Runnable(){

                            public void run() {
                                BaseJsonHttpResponseHandler.this.onFailure(statusCode, headers, throwable, responseString, jsonType);
                            }
                        });
                    }
                    catch (Throwable throwable2) {
                        AsyncHttpClient.log.d(BaseJsonHttpResponseHandler.LOG_TAG, "parseResponse thrown an problem", throwable2);
                        BaseJsonHttpResponseHandler.this.postRunnable(new Runnable(){

                            public void run() {
                                BaseJsonHttpResponseHandler.this.onFailure(statusCode, headers, throwable, responseString, null);
                            }
                        });
                    }
                }

            };
            if (!this.getUseSynchronousMode() && !this.getUsePoolThread()) {
                new Thread(runnable).start();
            } else {
                runnable.run();
            }
        } else {
            this.onFailure(statusCode, headers, throwable, null, null);
        }
    }

    protected abstract JSON_TYPE parseResponse(String var1, boolean var2) throws Throwable;

}

