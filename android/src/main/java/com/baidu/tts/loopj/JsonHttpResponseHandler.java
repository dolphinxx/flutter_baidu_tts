/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.http.Header
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 *  org.json.JSONTokener
 */
package com.baidu.tts.loopj;

import com.baidu.tts.loopj.AsyncHttpClient;
import com.baidu.tts.loopj.LogInterface;
import com.baidu.tts.loopj.TextHttpResponseHandler;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonHttpResponseHandler
extends TextHttpResponseHandler {
    private static final String LOG_TAG = "JsonHttpRH";
    private boolean useRFC5179CompatibilityMode = true;

    public JsonHttpResponseHandler() {
        super("UTF-8");
    }

    public JsonHttpResponseHandler(String encoding) {
        super(encoding);
    }

    public JsonHttpResponseHandler(boolean useRFC5179CompatibilityMode) {
        super("UTF-8");
        this.useRFC5179CompatibilityMode = useRFC5179CompatibilityMode;
    }

    public JsonHttpResponseHandler(String encoding, boolean useRFC5179CompatibilityMode) {
        super(encoding);
        this.useRFC5179CompatibilityMode = useRFC5179CompatibilityMode;
    }

    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        AsyncHttpClient.log.w(LOG_TAG, "onSuccess(int, Header[], JSONObject) was not overriden, but callback was received");
    }

    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        AsyncHttpClient.log.w(LOG_TAG, "onSuccess(int, Header[], JSONArray) was not overriden, but callback was received");
    }

    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        AsyncHttpClient.log.w(LOG_TAG, "onFailure(int, Header[], Throwable, JSONObject) was not overriden, but callback was received", throwable);
    }

    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        AsyncHttpClient.log.w(LOG_TAG, "onFailure(int, Header[], Throwable, JSONArray) was not overriden, but callback was received", throwable);
    }

    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        AsyncHttpClient.log.w(LOG_TAG, "onFailure(int, Header[], String, Throwable) was not overriden, but callback was received", throwable);
    }

    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        AsyncHttpClient.log.w(LOG_TAG, "onSuccess(int, Header[], String) was not overriden, but callback was received");
    }

    public final void onSuccess(final int statusCode, final Header[] headers, final byte[] responseBytes) {
        if (statusCode != 204) {
            Runnable runnable = new Runnable(){

                public void run() {
                    try {
                        final Object object = JsonHttpResponseHandler.this.parseResponse(responseBytes);
                        JsonHttpResponseHandler.this.postRunnable(new Runnable(){

                            public void run() {
                                if (!JsonHttpResponseHandler.this.useRFC5179CompatibilityMode && object == null) {
                                    JsonHttpResponseHandler.this.onSuccess(statusCode, headers, (String)null);
                                } else if (object instanceof JSONObject) {
                                    JsonHttpResponseHandler.this.onSuccess(statusCode, headers, (JSONObject)object);
                                } else if (object instanceof JSONArray) {
                                    JsonHttpResponseHandler.this.onSuccess(statusCode, headers, (JSONArray)object);
                                } else if (object instanceof String) {
                                    if (JsonHttpResponseHandler.this.useRFC5179CompatibilityMode) {
                                        JsonHttpResponseHandler.this.onFailure(statusCode, headers, (String)object, (Throwable)new JSONException("Response cannot be parsed as JSON data"));
                                    } else {
                                        JsonHttpResponseHandler.this.onSuccess(statusCode, headers, (String)object);
                                    }
                                } else {
                                    JsonHttpResponseHandler.this.onFailure(statusCode, headers, (Throwable)new JSONException("Unexpected response type " + object.getClass().getName()), (JSONObject)null);
                                }
                            }
                        });
                    }
                    catch (JSONException jSONException) {
                        JsonHttpResponseHandler.this.postRunnable(new Runnable(){

                            public void run() {
                                JsonHttpResponseHandler.this.onFailure(statusCode, headers, (Throwable)jSONException, (JSONObject)null);
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
            this.onSuccess(statusCode, headers, new JSONObject());
        }
    }

    public final void onFailure(final int statusCode, final Header[] headers, final byte[] responseBytes, final Throwable throwable) {
        if (responseBytes != null) {
            Runnable runnable = new Runnable(){

                public void run() {
                    try {
                        final Object object = JsonHttpResponseHandler.this.parseResponse(responseBytes);
                        JsonHttpResponseHandler.this.postRunnable(new Runnable(){

                            public void run() {
                                if (!JsonHttpResponseHandler.this.useRFC5179CompatibilityMode && object == null) {
                                    JsonHttpResponseHandler.this.onFailure(statusCode, headers, (String)null, throwable);
                                } else if (object instanceof JSONObject) {
                                    JsonHttpResponseHandler.this.onFailure(statusCode, headers, throwable, (JSONObject)object);
                                } else if (object instanceof JSONArray) {
                                    JsonHttpResponseHandler.this.onFailure(statusCode, headers, throwable, (JSONArray)object);
                                } else if (object instanceof String) {
                                    JsonHttpResponseHandler.this.onFailure(statusCode, headers, (String)object, throwable);
                                } else {
                                    JsonHttpResponseHandler.this.onFailure(statusCode, headers, (Throwable)new JSONException("Unexpected response type " + object.getClass().getName()), (JSONObject)null);
                                }
                            }
                        });
                    }
                    catch (JSONException jSONException) {
                        JsonHttpResponseHandler.this.postRunnable(new Runnable(){

                            public void run() {
                                JsonHttpResponseHandler.this.onFailure(statusCode, headers, (Throwable)jSONException, (JSONObject)null);
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
            AsyncHttpClient.log.v(LOG_TAG, "response body is null, calling onFailure(Throwable, JSONObject)");
            this.onFailure(statusCode, headers, throwable, (JSONObject)null);
        }
    }

    protected Object parseResponse(byte[] responseBody) throws JSONException {
        if (null == responseBody) {
            return null;
        }
        Object object = null;
        String string = JsonHttpResponseHandler.getResponseString(responseBody, this.getCharset());
        if (string != null) {
            string = string.trim();
            if (this.useRFC5179CompatibilityMode) {
                if (string.startsWith("{") || string.startsWith("[")) {
                    object = new JSONTokener(string).nextValue();
                }
            } else if (string.startsWith("{") && string.endsWith("}") || string.startsWith("[") && string.endsWith("]")) {
                object = new JSONTokener(string).nextValue();
            } else if (string.startsWith("\"") && string.endsWith("\"")) {
                object = string.substring(1, string.length() - 1);
            }
        }
        if (object == null) {
            object = string;
        }
        return object;
    }

    public boolean isUseRFC5179CompatibilityMode() {
        return this.useRFC5179CompatibilityMode;
    }

    public void setUseRFC5179CompatibilityMode(boolean useRFC5179CompatibilityMode) {
        this.useRFC5179CompatibilityMode = useRFC5179CompatibilityMode;
    }

}

