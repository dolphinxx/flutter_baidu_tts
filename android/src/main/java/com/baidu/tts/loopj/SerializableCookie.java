/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.http.cookie.Cookie
 *  org.apache.http.impl.cookie.BasicClientCookie
 */
package com.baidu.tts.loopj;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

public class SerializableCookie
implements Serializable {
    private static final long serialVersionUID = 6374381828722046732L;
    private final transient Cookie cookie;
    private transient BasicClientCookie clientCookie;

    public SerializableCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public Cookie getCookie() {
        Cookie cookie = this.cookie;
        if (this.clientCookie != null) {
            cookie = this.clientCookie;
        }
        return cookie;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(this.cookie.getName());
        out.writeObject(this.cookie.getValue());
        out.writeObject(this.cookie.getComment());
        out.writeObject(this.cookie.getDomain());
        out.writeObject(this.cookie.getExpiryDate());
        out.writeObject(this.cookie.getPath());
        out.writeInt(this.cookie.getVersion());
        out.writeBoolean(this.cookie.isSecure());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        String string = (String)in.readObject();
        String string2 = (String)in.readObject();
        this.clientCookie = new BasicClientCookie(string, string2);
        this.clientCookie.setComment((String)in.readObject());
        this.clientCookie.setDomain((String)in.readObject());
        this.clientCookie.setExpiryDate((Date)in.readObject());
        this.clientCookie.setPath((String)in.readObject());
        this.clientCookie.setVersion(in.readInt());
        this.clientCookie.setSecure(in.readBoolean());
    }
}

