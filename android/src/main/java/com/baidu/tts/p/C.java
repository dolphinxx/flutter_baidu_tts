/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  org.apache.http.HttpEntity
 *  org.apache.http.client.entity.UrlEncodedFormEntity
 *  org.apache.http.message.BasicNameValuePair
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.baidu.tts.p;

import android.content.Context;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.f.O;
import com.baidu.tts.loopj.RequestHandle;
import com.baidu.tts.loopj.ResponseHandlerInterface;
import com.baidu.tts.loopj.SyncHttpClient;
import com.baidu.tts.p.D;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class C {
    private Context b;
    private com.baidu.tts.l.A c;
    private FutureTask<Integer> d;
    private int e = 0;
    private int f = 0;
    ExecutorService a = Executors.newSingleThreadExecutor();

    public C(Context context) {
        this.b = context;
        this.c = new com.baidu.tts.l.A(context);
    }

    public FutureTask<Integer> a() {
        this.d = new FutureTask<Integer>(new a());
        this.a.submit(this.d);
        return this.d;
    }

    public void b() {
        this.d.cancel(true);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public class a
    implements Callable<Integer> {
        private RequestHandle b;

        public Integer a() throws Exception {
            SyncHttpClient syncHttpClient = new SyncHttpClient(true, 80, 443);
            String string = O.c.a();
            UrlEncodedFormEntity urlEncodedFormEntity = this.b();
            D d2 = new D();
            this.b = syncHttpClient.post(null, string, (HttpEntity)urlEncodedFormEntity, null, d2);
            final int n2 = d2.a();
            C.this.a.submit(new Runnable(){

                public void run() {
                    if (n2 == 0) {
                        int n22 = C.this.c.a(C.this.e, C.this.f);
                        LoggerProxy.d("UploadStatistics", "delete database code==" + n22);
                    }
                }
            });
            return n2;
        }

        private UrlEncodedFormEntity b() {
            ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
            JSONObject jSONObject = new JSONObject();
            ArrayList arrayList2 = C.this.c.f().get("listId");
            if (arrayList2.size() != 0) {
                C.this.e = (Integer)arrayList2.get(0);
                C.this.f = (Integer)arrayList2.get(arrayList2.size() - 1);
            }
            ArrayList arrayList3 = C.this.c.f().get("list");
            JSONArray jSONArray = new JSONArray((Collection)arrayList3);
            try {
                jSONObject.put("deviceInfo", (Object) A.a(C.this.b));
                jSONObject.put("appinfo", (Object) A.b(C.this.b));
                jSONObject.put("methodinfo", (Object)jSONArray);
                LoggerProxy.d("UploadStatistics", "StatisticsData= " + jSONObject.toString());
            }
            catch (JSONException jSONException) {
                jSONException.printStackTrace();
            }
            String string = jSONObject.toString();
            arrayList.add(new BasicNameValuePair("d", string));
            UrlEncodedFormEntity urlEncodedFormEntity = null;
            try {
                urlEncodedFormEntity = new UrlEncodedFormEntity(arrayList, "UTF-8");
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                unsupportedEncodingException.printStackTrace();
            }
            return urlEncodedFormEntity;
        }

        @Override
        public /* synthetic */ Integer call() throws Exception {
            return this.a();
        }

    }

}

