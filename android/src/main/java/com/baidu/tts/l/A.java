/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.baidu.tts.l;

import android.content.Context;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.model.BasicHandler;
import com.baidu.tts.client.model.Conditions;
import com.baidu.tts.client.model.DownloadHandler;
import com.baidu.tts.client.model.LibEngineParams;
import com.baidu.tts.client.model.ModelBags;
import com.baidu.tts.client.model.ModelFileBags;
import com.baidu.tts.d.B;
import com.baidu.tts.d.D;
import com.baidu.tts.f.G;
import com.baidu.tts.l.a.I;
import com.baidu.tts.tools.DataTool;
import com.baidu.tts.tools.MD5;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class A {
    private Context a;
    private com.baidu.tts.database.A b;
    private com.baidu.tts.database.D c;
    private I d;
    private D e;

    public A(Context context) {
        this.a = context;
        this.g();
    }

    private void g() {
        this.b = new com.baidu.tts.database.A(this);
        this.c = new com.baidu.tts.database.D(this);
        this.d = new I(this);
        this.e = new D();
        this.e.a(this);
        this.e.a();
    }

    public BasicHandler<ModelBags> a(Conditions conditions) {
        return this.d.a(conditions);
    }

    public BasicHandler<ModelBags> a(Conditions conditions, boolean bl) {
        return this.d.a(conditions, bl);
    }

    public LibEngineParams a() {
        return this.d.a();
    }

    public BasicHandler<ModelFileBags> a(Set<String> set) {
        return this.d.a(set);
    }

    public BasicHandler<ModelFileBags> b(Set<String> set) {
        return this.d.b(set);
    }

    public BasicHandler<ModelBags> b() {
        return this.d.b();
    }

    public String a(String string, String string2) {
        return this.b.a(string, string2);
    }

    public boolean a(String string) {
        Map<String, String> map = this.b.d(string);
        if (DataTool.isMapEmpty(map)) {
            return false;
        }
        String string2 = map.get(G.h.b());
        String string3 = map.get(G.g.b());
        String string4 = map.get(G.f.b());
        File file = new File(string2);
        if (file.exists()) {
            long l2;
            long l3 = file.length();
            if (l3 != (l2 = Long.parseLong(string3))) {
                return false;
            }
            String string5 = MD5.getInstance().getBigFileMd5(file);
            return string5.equalsIgnoreCase(string4);
        }
        return false;
    }

    public boolean b(String string) {
        Map<String, String> map = this.b.e(string);
        if (DataTool.isMapEmpty(map)) {
            return false;
        }
        String string2 = map.get(G.r.b());
        String string3 = map.get(G.s.b());
        boolean bl = this.a(string2);
        boolean bl2 = this.a(string3);
        return bl && bl2;
    }

    public DownloadHandler a(B b2) {
        return this.e.a(b2);
    }

    public void c() {
        this.e.b();
    }

    public Context d() {
        return this.a;
    }

    public com.baidu.tts.database.A e() {
        return this.b;
    }

    public void c(String string) {
        this.c.a(string);
    }

    public void a(String string, String string2, String string3) {
        this.c.a(string, string2, string3);
    }

    public Map<String, ArrayList> f() {
        return this.c.a();
    }

    public int a(int n2, int n3) {
        return this.c.a(n2, n3);
    }

    public String c(Set<String> set) {
        JSONArray jSONArray = new JSONArray();
        JSONArray jSONArray2 = new JSONArray();
        if (set == null || set.size() == 0 || set.isEmpty()) {
            return "params error";
        }
        try {
            Conditions conditions;
            for (String string : set) {
                conditions = new Conditions();
                conditions.appendId(string);
                BasicHandler<ModelBags> basicHandler = this.a(conditions, false);
                ModelBags modelBags = basicHandler.get();
                JSONArray jSONArray3 = modelBags.toJson();
                if (!modelBags.isEmpty()) {
                    jSONArray2.put(jSONArray3.get(0));
                    continue;
                }
                JSONObject jSONObject = new JSONObject();
                Matcher matcher = Pattern.compile("^[0-9]+$").matcher(string);
                if (matcher.find()) {
                    jSONObject.put("id", (Object)Integer.valueOf(string));
                    jSONObject.put("needUpdate", 2);
                    jSONArray.put((Object)jSONObject);
                    continue;
                }
                LoggerProxy.e("GetServerModelsWork", "params error id " + string + " is not int");
            }
            if (jSONArray2.length() != 0) {
                String string;
                BasicHandler<String> basicHandler = this.d.a(jSONArray2);
                string = (String)basicHandler.get();
                if (!string.equals("")) {
                    JSONArray jsonArray = new JSONArray(string);
                    for (int i2 = 0; i2 < jsonArray.length(); ++i2) {
                        jSONArray.put(jsonArray.get(i2));
                    }
                } else {
                    LoggerProxy.d("GetServerModelsWork", "servers return result is empty");
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return jSONArray.toString();
    }
}

