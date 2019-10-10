/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.sqlite.SQLiteException
 *  android.text.TextUtils
 *  org.apache.http.Header
 *  org.apache.http.HttpEntity
 *  org.apache.http.client.entity.UrlEncodedFormEntity
 *  org.apache.http.client.utils.URLEncodedUtils
 *  org.apache.http.message.BasicNameValuePair
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.baidu.tts.e;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.e.C;
import com.baidu.tts.e.D;
import com.baidu.tts.h.b.B;
import com.baidu.tts.loopj.AsyncHttpResponseHandler;
import com.baidu.tts.loopj.RequestHandle;
import com.baidu.tts.loopj.ResponseHandlerInterface;
import com.baidu.tts.loopj.SyncHttpClient;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class A {
    public static synchronized void a(Context context, String string) {
        boolean bl = D.h(context);
        if (bl) {
            long l2 = D.a(context);
            long l3 = 86400000L;
            long l4 = System.currentTimeMillis();
            Date date = new Date(l2);
            Date date2 = new Date(l4);
            if (l4 - l2 < l3 && l4 - l2 > 0L) {
                LoggerProxy.d("StatHelper", "lastTime " + l2 + ", curTime " + System.currentTimeMillis());
                LoggerProxy.d("StatHelper", "lastDate " + date + "\ncurDate " + date2);
                return;
            }
            boolean bl2 = A.b(context, string);
            LoggerProxy.d("StatHelper", "updated " + bl2);
        }
    }

    private static boolean b(Context context, String string) {
        boolean bl = false;
        try {
            C c2 = C.a(context);
            int n2 = c2.a();
            if (n2 >= 1) {
                int n3 = 0;
                LoggerProxy.d("StatHelper", "cursor.getCount: " + n2);
                n3 = n2 % 500 == 0 ? n2 / 500 : n2 / 500 + 1;
                for (int i2 = 0; i2 < n3; ++i2) {
                    JSONObject jSONObject = new JSONObject();
                    Map<String, ArrayList> map = c2.b();
                    ArrayList arrayList = map.get("listId");
                    ArrayList arrayList2 = map.get("list");
                    JSONArray jSONArray = new JSONArray((Collection)arrayList2);
                    jSONObject.put("recog_results", (Object)jSONArray);
                    LoggerProxy.d("StatHelper", "jsonObj all: " + jSONObject.toString());
                    byte[] arrby = D.a(jSONObject.toString());
                    if (arrby.length >= 2) {
                        arrby[0] = 117;
                        arrby[1] = 123;
                    }
                    String string2 = D.a(arrby);
                    LoggerProxy.d("StatHelper", " postContent:" + string2);
                    boolean bl2 = A.a(context, string, string2);
                    D.a(context, System.currentTimeMillis());
                    if (!bl2) continue;
                    c2.a(arrayList);
                    bl = true;
                }
            }
        }
        catch (SQLiteException sQLiteException) {
            LoggerProxy.d("StatHelper", "exception:" + sQLiteException.toString());
            return bl;
        }
        catch (IllegalStateException illegalStateException) {
            LoggerProxy.d("StatHelper", "exception:" + illegalStateException.toString());
            return bl;
        }
        catch (Exception exception) {
            LoggerProxy.d("StatHelper", "exception:" + exception.toString());
            return bl;
        }
        return bl;
    }

    private static boolean a(Context context, String string, String string2) {
        final boolean[] arrbl = new boolean[]{false};
        String string3 = A.c(context, string);
        LoggerProxy.d("StatHelper", "statHelper url:" + string3);
        UrlEncodedFormEntity urlEncodedFormEntity = A.a(string2);
        SyncHttpClient syncHttpClient = new SyncHttpClient(true, 80, 443);
        syncHttpClient.post(null, string3, (HttpEntity)urlEncodedFormEntity, null, new AsyncHttpResponseHandler(){

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String string = new String(responseBody);
                LoggerProxy.d("StatHelper", "response=" + new String(responseBody));
                if (!TextUtils.isEmpty((CharSequence)string)) {
                    try {
                        JSONObject jSONObject = new JSONObject(string);
                        String string2 = jSONObject.optString("errno");
                        if ("0".equals(string2)) {
                            arrbl[0] = true;
                            LoggerProxy.d("StatHelper", "ret=" + arrbl[0]);
                        }
                    }
                    catch (JSONException jSONException) {
                        LoggerProxy.d("StatHelper", "parse:" + jSONException.toString());
                    }
                    catch (Exception exception) {
                        LoggerProxy.d("StatHelper", "parse:" + exception.toString());
                    }
                }
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                LoggerProxy.d("StatHelper", "statusCode: " + statusCode + "responseBody: " + responseBody);
            }
        });
        return arrbl[0];
    }

    private static String c(Context context, String string) {
        LinkedList<BasicNameValuePair> linkedList = new LinkedList<BasicNameValuePair>();
        linkedList.add(new BasicNameValuePair("wise_cuid", B.a().i()));
        linkedList.add(new BasicNameValuePair("sdk_version", D.a()));
        linkedList.add(new BasicNameValuePair("app_name", D.b(context)));
        linkedList.add(new BasicNameValuePair("platform", D.c(context)));
        linkedList.add(new BasicNameValuePair("os", D.b()));
        linkedList.add(new BasicNameValuePair("net_type", D.d(context) + ""));
        linkedList.add(new BasicNameValuePair("appid", string));
        linkedList.add(new BasicNameValuePair("screen", D.e(context)));
        linkedList.add(new BasicNameValuePair("sdk_name", D.c()));
        linkedList.add(new BasicNameValuePair("app_signature", D.f(context)));
        String string2 = URLEncodedUtils.format(linkedList, (String)"utf-8");
        String string3 = "https://upl.baidu.com/voice?osname=voiceopen&action=usereventflow&";
        string3 = string3 + string2;
        return string3;
    }

    private static UrlEncodedFormEntity a(String string) {
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
        arrayList.add(new BasicNameValuePair("records", string));
        UrlEncodedFormEntity urlEncodedFormEntity = null;
        try {
            urlEncodedFormEntity = new UrlEncodedFormEntity(arrayList, "utf-8");
            urlEncodedFormEntity.setContentType("application/x-www-form-urlencoded");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
        }
        return urlEncodedFormEntity;
    }

}

