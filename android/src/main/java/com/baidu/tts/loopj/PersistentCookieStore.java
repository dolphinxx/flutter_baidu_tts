/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.text.TextUtils
 *  org.apache.http.client.CookieStore
 *  org.apache.http.cookie.Cookie
 */
package com.baidu.tts.loopj;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.baidu.tts.loopj.AsyncHttpClient;
import com.baidu.tts.loopj.LogInterface;
import com.baidu.tts.loopj.SerializableCookie;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class PersistentCookieStore
implements CookieStore {
    private static final String LOG_TAG = "PersistentCookieStore";
    private static final String COOKIE_PREFS = "CookiePrefsFile";
    private static final String COOKIE_NAME_STORE = "names";
    private static final String COOKIE_NAME_PREFIX = "cookie_";
    private boolean omitNonPersistentCookies = false;
    private final ConcurrentHashMap<String, Cookie> cookies;
    private final SharedPreferences cookiePrefs;

    public PersistentCookieStore(Context context) {
        this.cookiePrefs = context.getSharedPreferences(COOKIE_PREFS, 0);
        this.cookies = new ConcurrentHashMap();
        String string = this.cookiePrefs.getString(COOKIE_NAME_STORE, null);
        if (string != null) {
            String[] arrstring;
            for (String string2 : arrstring = TextUtils.split((String)string, (String)",")) {
                Cookie cookie;
                String string3 = this.cookiePrefs.getString(COOKIE_NAME_PREFIX + string2, null);
                if (string3 == null || (cookie = this.decodeCookie(string3)) == null) continue;
                this.cookies.put(string2, cookie);
            }
            this.clearExpired(new Date());
        }
    }

    public void addCookie(Cookie cookie) {
        if (this.omitNonPersistentCookies && !cookie.isPersistent()) {
            return;
        }
        String string = cookie.getName() + cookie.getDomain();
        if (!cookie.isExpired(new Date())) {
            this.cookies.put(string, cookie);
        } else {
            this.cookies.remove(string);
        }
        SharedPreferences.Editor editor = this.cookiePrefs.edit();
        editor.putString(COOKIE_NAME_STORE, TextUtils.join((CharSequence)",", (Iterable)this.cookies.keySet()));
        editor.putString(COOKIE_NAME_PREFIX + string, this.encodeCookie(new SerializableCookie(cookie)));
        editor.commit();
    }

    public void clear() {
        SharedPreferences.Editor editor = this.cookiePrefs.edit();
        for (String string : this.cookies.keySet()) {
            editor.remove(COOKIE_NAME_PREFIX + string);
        }
        editor.remove(COOKIE_NAME_STORE);
        editor.commit();
        this.cookies.clear();
    }

    public boolean clearExpired(Date date) {
        boolean bl = false;
        SharedPreferences.Editor editor = this.cookiePrefs.edit();
        for (Map.Entry<String, Cookie> entry : this.cookies.entrySet()) {
            String string = entry.getKey();
            Cookie cookie = entry.getValue();
            if (!cookie.isExpired(date)) continue;
            this.cookies.remove(string);
            editor.remove(COOKIE_NAME_PREFIX + string);
            bl = true;
        }
        if (bl) {
            editor.putString(COOKIE_NAME_STORE, TextUtils.join((CharSequence)",", (Iterable)this.cookies.keySet()));
        }
        editor.commit();
        return bl;
    }

    public List<Cookie> getCookies() {
        return new ArrayList<Cookie>(this.cookies.values());
    }

    public void setOmitNonPersistentCookies(boolean omitNonPersistentCookies) {
        this.omitNonPersistentCookies = omitNonPersistentCookies;
    }

    public void deleteCookie(Cookie cookie) {
        String string = cookie.getName() + cookie.getDomain();
        this.cookies.remove(string);
        SharedPreferences.Editor editor = this.cookiePrefs.edit();
        editor.remove(COOKIE_NAME_PREFIX + string);
        editor.commit();
    }

    protected String encodeCookie(SerializableCookie cookie) {
        if (cookie == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(cookie);
        }
        catch (IOException iOException) {
            AsyncHttpClient.log.d(LOG_TAG, "IOException in encodeCookie", iOException);
            return null;
        }
        return this.byteArrayToHexString(byteArrayOutputStream.toByteArray());
    }

    protected Cookie decodeCookie(String cookieString) {
        byte[] arrby = this.hexStringToByteArray(cookieString);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrby);
        Cookie cookie = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            cookie = ((SerializableCookie)objectInputStream.readObject()).getCookie();
        }
        catch (IOException iOException) {
            AsyncHttpClient.log.d(LOG_TAG, "IOException in decodeCookie", iOException);
        }
        catch (ClassNotFoundException classNotFoundException) {
            AsyncHttpClient.log.d(LOG_TAG, "ClassNotFoundException in decodeCookie", classNotFoundException);
        }
        return cookie;
    }

    protected String byteArrayToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder(bytes.length * 2);
        for (byte by : bytes) {
            int n2 = by & 255;
            if (n2 < 16) {
                stringBuilder.append('0');
            }
            stringBuilder.append(Integer.toHexString(n2));
        }
        return stringBuilder.toString().toUpperCase(Locale.US);
    }

    protected byte[] hexStringToByteArray(String hexString) {
        int n2 = hexString.length();
        byte[] arrby = new byte[n2 / 2];
        for (int i2 = 0; i2 < n2; i2 += 2) {
            arrby[i2 / 2] = (byte)((Character.digit(hexString.charAt(i2), 16) << 4) + Character.digit(hexString.charAt(i2 + 1), 16));
        }
        return arrby;
    }
}

