/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ActivityInfo
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.content.pm.Signature
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Environment
 *  android.os.Process
 *  android.os.SystemClock
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.provider.Settings$System
 *  android.telephony.TelephonyManager
 *  android.text.TextUtils
 *  android.util.Log
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.baidu.tts.tools;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.crypto.Cipher;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class DeviceId {
    private static final String a;
    private static DeviceInfo b;
    private final Context c;
    private int d = 0;
    private PublicKey e;

    private DeviceId(Context var1) {
        this.c = var1.getApplicationContext();
        this.a();
    }

    private static String a(byte[] arrby) {
        if (arrby == null) {
            throw new IllegalArgumentException("Argument b ( byte array ) is null! ");
        }
        String string = "";
        String string2 = "";
        for (int i2 = 0; i2 < arrby.length; ++i2) {
            string2 = Integer.toHexString(arrby[i2] & 255);
            string = string2.length() == 1 ? string + "0" + string2 : string + string2;
        }
        return string.toLowerCase();
    }

    private static byte[] a(byte[] arrby, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(2, publicKey);
        return cipher.doFinal(arrby);
    }

    private static void b(Throwable throwable) {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static String a(File file) {
        FileReader fileReader = null;
        try {
            int n2;
            String string;
            fileReader = new FileReader(file);
            char[] arrc = new char[8192];
            CharArrayWriter charArrayWriter = new CharArrayWriter();
            while ((n2 = fileReader.read(arrc)) > 0) {
                charArrayWriter.write(arrc, 0, n2);
            }
            String string2 = string = charArrayWriter.toString();
            return string2;
        }
        catch (Exception exception) {
            DeviceId.b(exception);
        }
        finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                }
                catch (Exception exception) {
                    DeviceId.b(exception);
                }
            }
        }
        return null;
    }

    public static String getCUID(Context var0) {
        return DeviceId.a(var0).b();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static DeviceInfo a(Context context) {
        if (b != null) return b;
        Class<DeviceInfo> class_ = DeviceInfo.class;
        Class<DeviceInfo> class_2 = DeviceInfo.class;
        synchronized (DeviceInfo.class) {
            if (b != null) return b;
            {
                long l2 = SystemClock.uptimeMillis();
                b = new DeviceId(context).b();
                long l3 = SystemClock.uptimeMillis();
            }
            // ** MonitorExit[var2_2] (shouldn't be in output)
            return b;
        }
    }

    public static String getDeviceID(Context var0) {
        return DeviceId.a(var0).deviceId;
    }

    public static String getIMEI(Context var0) {
        return DeviceId.a(var0).imei;
    }

    public static String getAndroidId(Context var0) {
//        String string = "";
//        string = Settings.Secure.getString(var0.getContentResolver(), "android_id");
//        if (TextUtils.isEmpty(string)) {
//            string = "";
//        }
//        return string;
        return "";
    }

    private static String a(String string) {
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        try {
            return Base64.encode(AESUtil.encrypt(a, a, string.getBytes()), "utf-8");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            DeviceId.b(unsupportedEncodingException);
        }
        catch (Exception exception) {
            DeviceId.b(exception);
        }
        return "";
    }

    private static String b(String string) {
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        try {
            return new String(AESUtil.decrypt(a, a, Base64.decode(string.getBytes())));
        }
        catch (Exception exception) {
            DeviceId.b(exception);
            return "";
        }
    }

    private static void c(String string) {
        File file = new File(Environment.getExternalStorageDirectory(), "backups/.SystemConfig");
        File file2 = new File(file, ".cuid2");
        try {
            Object object;
            if (file.exists() && !file.isDirectory()) {
                object = new Random();
                File file3 = null;
                File file4 = file.getParentFile();
                String string2 = file.getName();
                while ((file3 = new File(file4, string2 + ((Random)object).nextInt() + ".tmp")).exists()) {
                }
                file.renameTo(file3);
                file3.delete();
            }
            file.mkdirs();
            object = new FileWriter(file2, false);
            ((Writer)object).write(string);
            ((OutputStreamWriter)object).flush();
            ((OutputStreamWriter)object).close();
        }
        catch (IOException iOException) {
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private static void a(String string, String string2) {
        if (!TextUtils.isEmpty(string)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append("=");
            stringBuilder.append(string2);
            File file = new File(Environment.getExternalStorageDirectory(), "backups/.SystemConfig");
            File file2 = new File(file, ".cuid");
            try {
                Object object;
                Object object2;
                if (file.exists() && !file.isDirectory()) {
                    object2 = new Random();
                    object = null;
                    File file3 = file.getParentFile();
                    String string3 = file.getName();
                    while (((File)(object = new File(file3, string3 + ((Random)object2).nextInt() + ".tmp"))).exists()) {
                    }
                    file.renameTo((File)object);
                    ((File)object).delete();
                }
                file.mkdirs();
                object2 = new FileWriter(file2, false);
                object = Base64.encode(AESUtil.encrypt(a, a, stringBuilder.toString().getBytes()), "utf-8");
                ((Writer)object2).write((String)object);
                ((OutputStreamWriter)object2).flush();
                ((OutputStreamWriter)object2).close();
            }
            catch (IOException iOException) {
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    private static String d(String string) {
        return null != string && string.contains(":") ? "" : string;
    }

    private String[] a(Signature[] arrsignature) {
        String[] arrstring = new String[arrsignature.length];
        for (int i2 = 0; i2 < arrstring.length; ++i2) {
            arrstring[i2] = DeviceId.a(SHA1Util.sha1(arrsignature[i2].toByteArray()));
        }
        return arrstring;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a() {
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(A.a());
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            Certificate certificate = certificateFactory.generateCertificate(byteArrayInputStream);
            this.e = certificate.getPublicKey();
        }
        catch (Exception exception) {
        }
        finally {
            if (byteArrayInputStream != null) {
                try {
                    byteArrayInputStream.close();
                }
                catch (Exception exception) {
                    DeviceId.b(exception);
                }
            }
        }
    }

    private List<a> a(Intent intent, boolean bl) {
        ArrayList<a> arrayList = new ArrayList<a>();
        PackageManager packageManager = this.c.getPackageManager();
        List<ResolveInfo> list = packageManager.queryBroadcastReceivers(intent, 0);
        if (list != null) {
            for (ResolveInfo resolveInfo : list) {
                if (resolveInfo.activityInfo == null || resolveInfo.activityInfo.applicationInfo == null) continue;
                try {
                    String string;
                    String string2;
                    ActivityInfo activityInfo = packageManager.getReceiverInfo(new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name), 128);
                    Bundle bundle = activityInfo.metaData;
                    if (bundle == null || TextUtils.isEmpty(string2 = bundle.getString("galaxy_data"))) continue;
                    byte[] arrby = Base64.decode(string2.getBytes("utf-8"));
                    String string3 = new String(arrby);
                    JSONObject jSONObject = new JSONObject(string3);
                    a a2 = new a();
                    a2.b = jSONObject.getInt("priority");
                    a2.a = resolveInfo.activityInfo.applicationInfo;
                    if (this.c.getPackageName().equals(resolveInfo.activityInfo.applicationInfo.packageName)) {
                        a2.d = true;
                    }
                    if (bl && !TextUtils.isEmpty(string = bundle.getString("galaxy_sf"))) {
                        PackageInfo packageInfo = packageManager.getPackageInfo(resolveInfo.activityInfo.applicationInfo.packageName, 64);
                        JSONArray jSONArray = jSONObject.getJSONArray("sigs");
                        String[] arrstring = new String[jSONArray.length()];
                        for (int i2 = 0; i2 < arrstring.length; ++i2) {
                            arrstring[i2] = jSONArray.getString(i2);
                        }
                        String[] arrstring2 = this.a(packageInfo.signatures);
                        if (this.a(arrstring, arrstring2)) {
                            boolean bl2;
                            byte[] arrby2 = DeviceId.a(Base64.decode(string.getBytes()), this.e);
                            byte[] arrby3 = SHA1Util.sha1(arrby);
                            boolean bl3 = bl2 = arrby2 != null && Arrays.equals(arrby2, arrby3);
                            if (bl2) {
                                a2.c = true;
                            }
                        }
                    }
                    arrayList.add(a2);
                }
                catch (Exception exception) {}
            }
        }
        Collections.sort(arrayList, new Comparator<a>(){

            public int a(a a2, a a3) {
                int n2 = a3.b - a2.b;
                if (n2 == 0) {
                    if (a2.d && a3.d) {
                        return 0;
                    }
                    if (a2.d) {
                        return -1;
                    }
                    if (a3.d) {
                        return 1;
                    }
                }
                return n2;
            }

            @Override
            public /* synthetic */ int compare(a x0, a x1) {
                return this.a(x0, x1);
            }
        });
        return arrayList;
    }

    private boolean a(String[] arrstring, String[] arrstring2) {
        if (arrstring != null && arrstring2 != null && arrstring.length == arrstring2.length) {
            Object object;
            int n2;
            HashSet<Object> hashSet = new HashSet<Object>();
            String[] arrstring3 = arrstring;
            int n3 = arrstring.length;
            for (n2 = 0; n2 < n3; ++n2) {
                object = arrstring3[n2];
                hashSet.add(object);
            }
            object = new HashSet();
            String[] arrstring4 = arrstring2;
            n2 = arrstring2.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                String string = arrstring4[i2];
                ((HashSet)object).add(string);
            }
            return hashSet.equals(object);
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean e(String string) {
        FileOutputStream fileOutputStream = null;
        try {
            boolean bl;
            fileOutputStream = this.c.openFileOutput("libcuid.so", 1);
            fileOutputStream.write(string.getBytes());
            fileOutputStream.flush();
            boolean bl2 = bl = true;
            return bl2;
        }
        catch (Exception exception) {
            DeviceId.b(exception);
        }
        finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                }
                catch (Exception exception) {
                    DeviceId.b(exception);
                }
            }
        }
        return false;
    }

    private String f(String string) {
        try {
            return Settings.System.getString(this.c.getContentResolver(), string);
        }
        catch (Exception exception) {
            DeviceId.b(exception);
            return null;
        }
    }

    private boolean b(String string, String string2) {
        try {
            return Settings.System.putString(this.c.getContentResolver(), string, string2);
        }
        catch (Exception exception) {
            DeviceId.b(exception);
            return false;
        }
    }

    private DeviceInfo b() {
        boolean bl;
        boolean bl2;
        File file;
        Object object3;
        Object object2;
        boolean bl22;
        int n2;
        Object object;
        DeviceInfo b2 = null;
        String string = null;
        List<a> list = this.a(new Intent("com.baidu.intent.action.GALAXY").setPackage(this.c.getPackageName()), true);
        if (list != null && list.size() != 0) {
            object3 = list.get(0);
            bl22 = ((a)object3).c;
            if (!((a)object3).c) {
                for (n2 = 0; n2 < 3; ++n2) {
                    Log.w("DeviceId", "galaxy config err, In the release version of the signature should be matched");
                }
            }
        } else {
            bl22 = false;
            for (int i2 = 0; i2 < 3; ++i2) {
                Log.w("DeviceId", "galaxy lib host missing meta-data,make sure you know the right way to integrate galaxy");
            }
        }
        if (((File)(object3 = new File(this.c.getFilesDir(), "libcuid.so"))).exists()) {
            b2 = b.a(DeviceId.b(DeviceId.a((File)object3)));
        }
        if (b2 == null) {
            this.d |= 16;
            List<a> list2 = this.a(new Intent("com.baidu.intent.action.GALAXY"), bl22);
            if (list2 != null) {
                String string2 = "files";
                object = this.c.getFilesDir();
                if (!string2.equals(((File)object).getName())) {
                    Log.e("DeviceId", "fetal error:: app files dir name is unexpectedly :: " + ((File)object).getAbsolutePath());
                    string2 = ((File)object).getName();
                }
                for (a object4 : list2) {
                    File file2;
                    if (object4.d || !(file2 = new File(new File(object4.a.dataDir, string2), "libcuid.so")).exists() || (b2 = b.a(DeviceId.b(DeviceId.a(file2)))) == null) continue;
                    break;
                }
            }
        }
        if (b2 == null) {
            b2 = b.a(DeviceId.b(this.f("com.baidu.deviceid.v2")));
        }
        n2 = this.g("android.permission.READ_EXTERNAL_STORAGE") ? 1 : 0;
        if (b2 == null && n2 != 0) {
            this.d |= 2;
            b2 = this.e();
        }
        if (b2 == null) {
            this.d |= 8;
            b2 = this.d();
        }
        boolean bl4 = false;
        if (b2 == null && n2 != 0) {
            this.d |= 1;
            string = this.i("");
            bl4 = true;
            b2 = this.h(string);
        }
        if (b2 == null) {
            this.d |= 4;
            if (!bl4) {
                string = this.i("");
            }
            b2 = new DeviceInfo();
            object = DeviceId.getAndroidId(this.c);
            if (Build.VERSION.SDK_INT < 23) {
                String bl3 = UUID.randomUUID().toString();
                object2 = string + object + bl3;
            } else {
                object2 = "com.baidu" + object;
            }
            b2.deviceId = MD5Util.toMd5(((String)object2).getBytes(), true);
            b2.imei = string;
        }
        object = null;
        object2 = new File(this.c.getFilesDir(), "libcuid.so");
        if ((this.d & 16) != 0 || !((File)object2).exists()) {
            if (TextUtils.isEmpty((CharSequence)object)) {
                object = DeviceId.a(b2.a());
            }
            this.e((String)object);
        }
        if ((bl = this.c()) && ((this.d & 2) != 0 || TextUtils.isEmpty(this.f("com.baidu.deviceid.v2")))) {
            if (TextUtils.isEmpty((CharSequence)object)) {
                object = DeviceId.a(b2.a());
            }
            this.b("com.baidu.deviceid.v2", (String)object);
        }
        if (bl2 = this.g("android.permission.WRITE_EXTERNAL_STORAGE")) {
            file = new File(Environment.getExternalStorageDirectory(), "backups/.SystemConfig/.cuid2");
            if ((this.d & 8) != 0 || !file.exists()) {
                if (TextUtils.isEmpty((CharSequence)object)) {
                    object = DeviceId.a(b2.a());
                }
                DeviceId.c((String)object);
            }
        }
        if (bl && ((this.d & 1) != 0 || TextUtils.isEmpty(this.f("com.baidu.deviceid")))) {
            this.b("com.baidu.deviceid", b2.deviceId);
            this.b("bd_setting_i", b2.imei);
        }
        if (bl && !TextUtils.isEmpty(b2.imei)) {
            file = new File(Environment.getExternalStorageDirectory(), "backups/.SystemConfig/.cuid");
            if ((this.d & 2) != 0 || !file.exists()) {
                DeviceId.a(b2.imei, b2.deviceId);
            }
        }
        return b2;
    }

    private boolean c() {
        return this.g("android.permission.WRITE_SETTINGS");
    }

    private boolean g(String string) {
        return this.c.checkPermission(string, Process.myPid(), Process.myUid()) == 0;
    }

    private DeviceInfo d() {
        DeviceInfo object;
        String string = this.f("com.baidu.deviceid");
        String string2 = this.f("bd_setting_i");
        if (TextUtils.isEmpty(string2) && !TextUtils.isEmpty(string2 = this.i(""))) {
            this.b("bd_setting_i", string2);
        }
        if (TextUtils.isEmpty(string)) {
            String string3 = MD5Util.toMd5(("com.baidu" + string2 + DeviceId.getAndroidId(this.c)).getBytes(), true);
            string = this.f(string3);
        }
        if (!TextUtils.isEmpty(string)) {
            object = new DeviceInfo();
            object.deviceId = string;
            object.imei = string2;
            return object;
        }
        return null;
    }

    private DeviceInfo e() {
        String string;
        File file = new File(Environment.getExternalStorageDirectory(), "backups/.SystemConfig/.cuid2");
        if (file.exists() && !TextUtils.isEmpty(string = DeviceId.a(file))) {
            try {
                return b.a(new String(AESUtil.decrypt(a, a, Base64.decode(string.getBytes()))));
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    private DeviceInfo h(String string) {
        boolean bl;
        DeviceInfo object;
        boolean bl2 = bl = Build.VERSION.SDK_INT < 23;
        if (bl && TextUtils.isEmpty(string)) {
            return null;
        }
        boolean bl3 = false;
        String string2 = "";
        File file = new File(Environment.getExternalStorageDirectory(), "baidu/.cuid");
        if (!file.exists()) {
            file = new File(Environment.getExternalStorageDirectory(), "backups/.SystemConfig/.cuid");
            bl3 = true;
        }
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String string3 = null;
            while ((string3 = bufferedReader.readLine()) != null) {
                stringBuilder.append(string3);
                stringBuilder.append("\r\n");
            }
            bufferedReader.close();
            String string4 = new String(AESUtil.decrypt(a, a, Base64.decode(stringBuilder.toString().getBytes())));
            String[] arrstring = string4.split("=");
            if (arrstring != null && arrstring.length == 2) {
                if (bl && string.equals(arrstring[0])) {
                    string2 = arrstring[1];
                } else if (!bl) {
                    if (TextUtils.isEmpty(string)) {
                        string = arrstring[1];
                    }
                    string2 = arrstring[1];
                }
            }
            if (!bl3) {
                DeviceId.a(string, string2);
            }
        }
        catch (FileNotFoundException fileNotFoundException) {
        }
        catch (IOException iOException) {
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (!TextUtils.isEmpty(string2)) {
            object = new DeviceInfo();
            object.deviceId = string2;
            object.imei = string;
            return object;
        }
        return null;
    }

    // FIXME: don't retrieve IMEI
    private String i(String string) {
        String string2 = null;
        try {
            TelephonyManager telephonyManager = (TelephonyManager)this.c.getSystemService("phone");
            if (telephonyManager != null) {
                string2 = telephonyManager.getDeviceId();
            }
        }
        catch (Exception exception) {
            Log.e("DeviceId", "Read IMEI failed", exception);
        }
        string2 = DeviceId.d(string2);
        if (TextUtils.isEmpty(string2)) {
            string2 = string;
        }
        return string2;
    }

    static {
        String string = new String(Base64.decode(new byte[]{77, 122, 65, 121, 77, 84, 73, 120, 77, 68, 73, 61}));
        String string2 = new String(Base64.decode(new byte[]{90, 71, 108, 106, 100, 87, 82, 112, 89, 87, 73, 61}));
        a = string + string2;
    }

    private static class a {
        public ApplicationInfo a;
        public int b = 0;
        public boolean c = false;
        public boolean d = false;

        private a() {
        }
    }

    private static class DeviceInfo {
        public String deviceId;
        public String imei;
        public int version = 2;

        private DeviceInfo() {
        }

        public static DeviceInfo a(String string) {
            if (TextUtils.isEmpty(string)) {
                return null;
            }
            try {
                JSONObject jSONObject = new JSONObject(string);
                String string2 = jSONObject.getString("deviceid");
                String string3 = jSONObject.getString("imei");
                int n2 = jSONObject.getInt("ver");
                if (!TextUtils.isEmpty(string2) && string3 != null) {
                    DeviceInfo b2 = new DeviceInfo();
                    b2.deviceId = string2;
                    b2.imei = string3;
                    b2.version = n2;
                    return b2;
                }
            }
            catch (JSONException jSONException) {
                DeviceId.b(jSONException);
            }
            return null;
        }

        public String a() {
            try {
                return new JSONObject().put("deviceid", this.deviceId).put("imei", this.imei).put("ver", this.version).toString();
            }
            catch (JSONException jSONException) {
                DeviceId.b(jSONException);
                return null;
            }
        }

        public String b() {
            String string = this.imei;
            if (TextUtils.isEmpty(string)) {
                string = "0";
            }
            StringBuffer stringBuffer = new StringBuffer(string);
            string = stringBuffer.reverse().toString();
            String string2 = this.deviceId + "|" + string;
            return string2;
        }
    }

}

