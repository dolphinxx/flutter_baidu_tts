/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.baidu.tts.client.model;

import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.l.A;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.json.JSONException;
import org.json.JSONObject;

public class RecordData {
    private A b;
    ExecutorService a = Executors.newSingleThreadExecutor();

    public RecordData(A modelMediator) {
        this.b = modelMediator;
    }

    public void setStartInfo(String uuid, String modeId, String startTime) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("startTime", (Object)startTime);
            jSONObject.put("modeId", (Object)modeId);
            LoggerProxy.d("RecordData", " StartInfo json= " + jSONObject.toString());
            this.a.submit(new InsertData(null, uuid, null));
            this.a.submit(new InsertData(jSONObject, uuid, "startInfo"));
        }
        catch (JSONException jSONException) {
            jSONException.printStackTrace();
        }
    }

    public void setEndInfo(String uuid, String modeId, int result, String endTime) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("modeId", (Object)modeId);
            jSONObject.put("result", result);
            jSONObject.put("endTime", (Object)endTime);
            LoggerProxy.d("RecordData", "EndInfo json= " + jSONObject.toString());
            this.a.submit(new InsertData(jSONObject, uuid, "endInfo"));
        }
        catch (JSONException jSONException) {
            jSONException.printStackTrace();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public class InsertData
    implements Callable<Integer> {
        private JSONObject b;
        private String c;
        private String d;

        public InsertData(JSONObject jsonObject, String uuid, String columnName) {
            this.b = jsonObject;
            this.c = uuid;
            this.d = columnName;
        }

        @Override
        public Integer call() throws Exception {
            if (this.b == null && this.d == null) {
                RecordData.this.b.c(this.c);
            } else {
                RecordData.this.b.a(this.c, this.d, this.b.toString());
            }
            return 0;
        }
    }

}

