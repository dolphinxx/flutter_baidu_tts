/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.Signature
 *  android.content.res.Resources
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Base64
 *  android.util.DisplayMetrics
 */
package com.baidu.tts.e;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Base64;
import android.util.DisplayMetrics;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.zip.GZIPOutputStream;

public class D {
    public static void a(Context context, long l2) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("tts", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("last_upload_stat_time", l2);
        editor.commit();
    }

    public static long a(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("tts", 0);
        long l2 = sharedPreferences.getLong("last_upload_stat_time", 0L);
        return l2;
    }

    public static String a(byte[] arrby) {
        if (arrby == null || arrby.length == 0) {
            return null;
        }
        try {
            return new String(Base64.encode((byte[])arrby, (int)0, (int)arrby.length, (int)0), "utf-8");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
            return null;
        }
    }

    public static byte[] a(String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(string.getBytes("utf-8"));
            gZIPOutputStream.close();
            byte[] arrby = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
            return arrby;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        return null;
    }

    public static String a() {
        return "1.0.0-20140804";
    }

    public static String b(Context context) {
        return D.i(context);
    }

    public static String c(Context context) {
        return D.b() + "&" + Build.MODEL + "&" + Build.VERSION.RELEASE + "&" + Build.VERSION.SDK_INT + "&" + D.d(context);
    }

    public static String b() {
        return "Android";
    }

    @SuppressLint(value={"DefaultLocale"})
    public static int d(Context context) {
        int n2 = 3;
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && "wifi".equals(networkInfo.getTypeName().toLowerCase())) {
            n2 = 1;
        }
        return n2;
    }

    public static String e(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int n2 = displayMetrics.widthPixels;
        int n3 = displayMetrics.heightPixels;
        return n2 + "*" + n3;
    }

    public static String c() {
        return "\u79bb\u7ebfTTS SDK";
    }

    public static String f(Context context) {
        String string = null;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 64);
            Signature[] arrsignature = packageInfo.signatures;
            Signature signature = arrsignature[0];
            string = D.b(signature.toByteArray());
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return string;
    }

    private static String b(byte[] arrby) {
        String string = null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            X509Certificate x509Certificate = (X509Certificate)certificateFactory.generateCertificate(new ByteArrayInputStream(arrby));
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(x509Certificate.getEncoded());
            string = D.c(messageDigest.digest());
        }
        catch (CertificateException certificateException) {
            certificateException.printStackTrace();
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
        }
        return string;
    }

    private static String c(byte[] arrby) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < arrby.length; ++i2) {
            String string = Integer.toHexString(255 & arrby[i2]);
            if (string.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(string);
        }
        return stringBuffer.toString();
    }

    private static String i(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            applicationInfo = null;
        }
        String string = (String)packageManager.getApplicationLabel(applicationInfo);
        return string;
    }

    public static NetworkInfo g(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
        return connectivityManager.getActiveNetworkInfo();
    }

    public static boolean h(Context context) {
        NetworkInfo networkInfo = D.g(context);
        return networkInfo != null && networkInfo.isConnected();
    }
}

