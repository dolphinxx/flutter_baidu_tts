/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.http.Header
 *  org.apache.http.HttpEntity
 *  org.apache.http.ParseException
 *  org.apache.http.util.EntityUtils
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.baidu.tts.b.a.b;

import com.baidu.tts.a.a.A;
import com.baidu.tts.a.a.B;
import com.baidu.tts.a.a.C;
import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.b.a.b.F;
import com.baidu.tts.b.a.b.G;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.f.D;
import com.baidu.tts.f.N;
import com.baidu.tts.tools.CommonUtility;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class H
extends G {
    private A<byte[], byte[]> a;
    private F.b b;
    private com.baidu.tts.m.H c;

    public H(com.baidu.tts.m.H h2) {
        this.c = h2;
        this.a = new A();
        B b2 = new B();
        this.a.a(b2);
        this.a.a();
    }

    public void a(F.b b2) {
        this.b = b2;
    }

    public void a(int n2, Header[] arrheader, String string, HttpEntity httpEntity) {
        if ("application/json".equals(string)) {
            this.c(httpEntity);
        } else {
            this.b(httpEntity);
        }
    }

    private void b(HttpEntity httpEntity) {
        String string = "--BD**TTS++LIB";
        String string2 = "--";
        String string3 = string2 + string;
        byte[] arrby = null;
        try {
            arrby = string3.getBytes("utf-8");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
        }
        byte[] arrby2 = null;
        try {
            arrby2 = EntityUtils.toByteArray((HttpEntity)httpEntity);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        int n2 = CommonUtility.indexOf(arrby2, arrby, 0);
        if (n2 < 0) {
            TtsError ttsError = com.baidu.tts.h.a.C.a().b(N.l);
            this.c.a(ttsError);
            return;
        }
        int n3 = CommonUtility.indexOf(arrby2, arrby, n2 + arrby.length);
        if (n3 < 0) {
            TtsError ttsError = com.baidu.tts.h.a.C.a().b(N.l);
            this.c.a(ttsError);
            return;
        }
        byte[] arrby3 = CommonUtility.copyBytesOfRange(arrby2, n2 + arrby.length, n3);
        try {
            this.a(new String(arrby3, "utf-8"));
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
        }
        int n4 = CommonUtility.indexOf(arrby2, arrby, n3 + arrby.length);
        if (n4 >= 0) {
            byte[] arrby4 = CommonUtility.copyBytesOfRange(arrby2, n3 + arrby.length, n4);
            this.c.a(arrby4);
        }
    }

    private void c(HttpEntity httpEntity) {
        String string = null;
        try {
            string = EntityUtils.toString((HttpEntity)httpEntity, (String)D.c.a());
        }
        catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        this.a(string);
    }

    private void a(String string) {
        try {
            JSONObject jSONObject = new JSONObject(string);
            int n2 = jSONObject.optInt(com.baidu.tts.f.G.v.a());
            LoggerProxy.d("TtsResponseHandler", "parseJSON errNo=" + n2);
            this.c.a(n2);
            if (n2 != 0) {
                String string2 = jSONObject.getString(com.baidu.tts.f.G.w.a());
                TtsError ttsError = com.baidu.tts.h.a.C.a().a(N.g, n2, string2);
                this.c.a(ttsError);
            } else {
                String string3 = jSONObject.optString(com.baidu.tts.f.G.W.a());
                this.c.a(string3);
                int n3 = jSONObject.optInt(com.baidu.tts.f.G.X.a());
                this.c.b(n3);
                int n4 = jSONObject.optInt(com.baidu.tts.f.G.z.b());
                this.c.d(n4);
            }
        }
        catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        catch (JSONException jSONException) {
            jSONException.printStackTrace();
        }
    }

    public void a(int n2, Header[] arrheader, String string, HttpEntity httpEntity, Throwable throwable) {
        LoggerProxy.d("TtsResponseHandler", "onFailure error = " + throwable.getMessage());
        TtsError ttsError = com.baidu.tts.h.a.C.a().a(N.b, n2, null, throwable);
        this.c.a(ttsError);
    }
}

