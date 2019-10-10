/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 *  org.apache.http.Header
 *  org.apache.http.HttpEntity
 *  org.apache.http.client.utils.URLEncodedUtils
 *  org.apache.http.message.BasicNameValuePair
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.baidu.tts.auth;

import android.content.Context;
import android.text.TextUtils;
import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.f.N;
import com.baidu.tts.k.B;
import com.baidu.tts.loopj.AsyncHttpResponseHandler;
import com.baidu.tts.loopj.RequestHandle;
import com.baidu.tts.loopj.ResponseHandlerInterface;
import com.baidu.tts.loopj.SyncHttpClient;
import com.baidu.tts.tools.StringTool;
import java.util.LinkedList;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class C implements B<C, C.a> {
    private String a;
    private String b;
    private String c;
    private String d = "https";

    public void a(String string) {
        this.d = string;
    }

    public String a() {
        return this.a;
    }

    public void b(String string) {
        this.a = string;
    }

    public String b() {
        return this.b;
    }

    public void c(String string) {
        this.b = string;
    }

    public String c() {
        return this.c;
    }

    public void d(String string) {
        this.c = string;
    }

    public int _compareTo(C c2) {
        String string = c2.a();
        if (StringTool.isEmpty(this.a)) {
            LoggerProxy.d("OnlineAuth", "mAK=" + this.b + "--mSK=" + this.c + "--ak2=" + c2.b() + "--sk2=" + c2.c());
            if ((StringTool.isEqual(b, c2.b())) && (StringTool.isEqual(c, c2.c()))) {
                return 0;
            }
            return 1;
        }
        LoggerProxy.d("OnlineAuth", "mProductId=" + this.a + "--productId2=" + string);
        if (string == null) {
            return 1;
        }
        return this.a.compareTo(string);
    }

    public a d() throws Exception {
        a a2;
        block8 : {
            LoggerProxy.d("OnlineAuth", "enter online auth");
            a2 = new a();
            if (StringTool.isEmpty(this.a)) {
                try {
                    boolean bl = this.a(this.b, this.c);
                    if (bl) {
                        String string = this.a(this.b, this.c, this.d);
                        LoggerProxy.d("OnlineAuth", "url=" + string);
                        SyncHttpClient syncHttpClient = null;
                        if ("http".equals(this.d)) {
                            syncHttpClient = new SyncHttpClient();
                        } else if ("https".equals(this.d)) {
                            syncHttpClient = new SyncHttpClient(true, 80, 443);
                        }
                        syncHttpClient.post(null, string, null, null, new AsyncHttpResponseHandler(){

                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                String string = new String(responseBody);
                                LoggerProxy.d("OnlineAuth", "body=" + string + "--code=" + statusCode);
                                if (!TextUtils.isEmpty((CharSequence)string)) {
                                    try {
                                        Object object;
                                        JSONObject jSONObject = new JSONObject(string);
                                        if (jSONObject.has("access_token")) {
                                            object = jSONObject.getString("access_token");
                                            a2.b((String)object);
                                        } else {
                                            object = com.baidu.tts.h.a.C.a().b(N.a);
                                            a2.a((TtsError)object);
                                        }
                                        if (jSONObject.has("expires_in")) {
                                            int n2 = jSONObject.getInt("expires_in");
                                            long l2 = System.nanoTime() + Math.min((long)n2, 86400L) * 1000000000L;
                                            a2.a(l2);
                                        }
                                    }
                                    catch (JSONException jSONException) {
                                        LoggerProxy.d("OnlineAuth", "parse:" + jSONException.toString());
                                    }
                                    catch (Exception exception) {
                                        LoggerProxy.d("OnlineAuth", "parse:" + exception.toString());
                                    }
                                }
                            }

                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                TtsError ttsError = com.baidu.tts.h.a.C.a().b(N.a);
                                a2.a(ttsError);
                            }
                        });
                        break block8;
                    }
                    TtsError ttsError = com.baidu.tts.h.a.C.a().b(N.Y);
                    a2.a(ttsError);
                }
                catch (Exception exception) {
                    TtsError ttsError = com.baidu.tts.h.a.C.a().a(N.a, exception);
                    a2.a(ttsError);
                }
            } else {
                a2.a(this.a);
            }
        }
        LoggerProxy.d("OnlineAuth", "end online auth");
        return a2;
    }

    private boolean a(String string, String string2) {
        return !StringTool.isEmpty(string) && !StringTool.isEmpty(string2);
    }

    private String a(String string, String string2, String string3) {
        LinkedList<BasicNameValuePair> linkedList = new LinkedList<BasicNameValuePair>();
        linkedList.add(new BasicNameValuePair("grant_type", "client_credentials"));
        linkedList.add(new BasicNameValuePair("client_id", string));
        linkedList.add(new BasicNameValuePair("client_secret", string2));
        String string4 = URLEncodedUtils.format(linkedList, (String)"utf-8");
        String string5 = string3 + "://openapi.baidu.com/oauth/2.0/token" + "?";
        string5 = string5 + string4;
        return string5;
    }

    @Override
    public /* synthetic */ int compareTo(C x0) {
        return this._compareTo(x0);
    }

    @Override
    public /* synthetic */ C.a call() throws Exception {
        return this.d();
    }

    public static class a
    implements com.baidu.tts.k.A {
        private String a;
        private String b;
        private long c;
        private TtsError d;

        public void a(String string) {
            this.a = string;
        }

        public String a() {
            return this.b;
        }

        public void b(String string) {
            this.b = string;
        }

        public void a(long l2) {
            this.c = l2;
        }

        public TtsError b() {
            return this.d;
        }

        public void a(TtsError ttsError) {
            if (ttsError != null) {
                LoggerProxy.d("OnlineAuth", "this=" + this + "--error=" + ttsError.getDetailMessage());
            }
            this.d = ttsError;
        }

        public boolean g() {
            if (StringTool.isEmpty(this.a)) {
                if (this.b == null) {
                    return false;
                }
                long l2 = System.currentTimeMillis();
                return l2 < this.c;
            }
            return true;
        }
    }

}

