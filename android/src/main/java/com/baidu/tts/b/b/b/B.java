/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.media.AudioTrack
 */
package com.baidu.tts.b.b.b;

import android.media.AudioTrack;
import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.f.E;
import com.baidu.tts.f.K;
import com.baidu.tts.m.C;
import com.baidu.tts.m.H;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class B
extends com.baidu.tts.b.b.b.A {
    private volatile AudioTrack d;
    private a e;
    private com.baidu.tts.i.a.B f = new com.baidu.tts.i.a.B();
    protected final Lock b = new ReentrantLock();
    protected final Condition c = this.b.newCondition();
    private boolean g = false;
    private int h;

    @Override
    public void a(com.baidu.tts.b.b.A a2) {
        this.a = a2;
    }

    public <AudioTrackPlayerParams> void a(AudioTrackPlayerParams AudioTrackPlayerParams) {
        this.e = (a)AudioTrackPlayerParams;
    }

    @Override
    public TtsError a() {
        int n2 = this.e.a();
        int n3 = this.e.b();
        int n4 = this.e.c();
        int n5 = this.e.g();
        int n6 = this.e.d();
        int n7 = this.a(n2, n3, n4);
        this.d = new AudioTrack(n5, n2, n3, n4, n7, n6);
        float f2 = this.e.e();
        float f3 = this.e.f();
        this.d.setStereoVolume(f2, f3);
        return null;
    }

    private int a(int n2, int n3, int n4) {
        int n5 = AudioTrack.getMinBufferSize((int)n2, (int)n3, (int)n4);
        n5 *= 2;
        int n6 = 1;
        switch (n3) {
            case 1: 
            case 2: 
            case 4: {
                n6 = 1;
                break;
            }
            case 3: 
            case 12: {
                n6 = 2;
                break;
            }
            default: {
                n6 = Integer.bitCount(n3);
            }
        }
        int n7 = n6 * (n4 == 3 ? 1 : 2);
        if (n5 % n7 != 0 || n5 < 1) {
            n5 = 5120;
        }
        return n5;
    }

    @Override
    public int a(int n2) {
        if (n2 != this.e.g()) {
            int n3 = this.e.a();
            int n4 = this.e.b();
            int n5 = this.e.c();
            int n6 = this.e.d();
            int n7 = this.a(n3, n4, n5);
            this.d = new AudioTrack(n2, n3, n4, n5, n7, n6);
            this.e.b(n2);
            float f2 = this.e.e();
            float f3 = this.e.f();
            this.d.setStereoVolume(f2, f3);
            this.d.play();
        }
        return 0;
    }

    @Override
    public int b(int n2) {
        if (n2 != this.e.a()) {
            int n3 = n2;
            int n4 = this.e.g();
            int n5 = this.e.b();
            int n6 = this.e.c();
            int n7 = this.e.d();
            int n8 = this.a(n3, n5, n6);
            this.d = new AudioTrack(n4, n3, n5, n6, n8, n7);
            this.e.b(n4);
            float f2 = this.e.e();
            float f3 = this.e.f();
            this.d.setStereoVolume(f2, f3);
            this.d.play();
        }
        return 0;
    }

    @Override
    public void b() {
        if (this.d != null) {
            this.d.play();
        }
    }

    @Override
    public void c() {
        this.g = true;
        if (this.d != null) {
            this.d.pause();
        }
    }

    @Override
    public void d() {
        this.g = false;
        if (this.d != null) {
            this.d.play();
        }
        this.g();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void g() {
        try {
            this.b.lock();
            this.c.signalAll();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        finally {
            this.b.unlock();
        }
    }

    @Override
    public void e() {
        if (this.g) {
            this.g = false;
            this.g();
        }
        if (this.d != null) {
            this.d.pause();
            this.d.flush();
            this.d.stop();
        }
    }

    @Override
    public TtsError f() {
        this.e();
        if (this.d != null) {
            this.d.release();
        }
        this.d = null;
        return null;
    }

    @Override
    public int a(float f2, float f3) {
        int n2 = this.d.setStereoVolume(f2, f3);
        this.e.a(f2);
        this.e.b(f3);
        return n2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public TtsError a(H h2) {
        LoggerProxy.d("AudioTrackPlayer", "enter put");
        if (h2 != null) {
            byte[] arrby;
            E e2 = h2.g();
            if (e2 == E.a) {
                this.b(h2);
            }
            if (e2 == E.c) {
                this.f.c(h2.c());
            }
            if ((arrby = h2.d()) != null) {
                this.f.b(arrby.length);
            }
            while (this.f.hasNext()) {
                int n2;
                int n3;
                Object object;
                float f2;
                com.baidu.tts.i.a.A a2 = this.f.c();
                int n4 = 0;
                int n5 = a2.a();
                int n6 = a2.b();
                while (n4 < n6 && this.d.getPlayState() != 1) {
                    n3 = n6 - n4;
                    LoggerProxy.d("AudioTrackPlayer", "before write");
                    f2 = n4 + n5;
                    n2 = this.d.write(arrby, (int)f2, n3);
                    LoggerProxy.d("AudioTrackPlayer", "writtenbytes=" + n2 + "--offset=" + n4 + "--dataLength=" + n6);
                    if (n2 >= 0) {
                        n4 += n2;
                    }
                    while (this.g) {
                        try {
                            this.b.lock();
                            LoggerProxy.d("AudioTrackPlayer", "await before" + System.currentTimeMillis());
                            this.c.await();
                            LoggerProxy.d("AudioTrackPlayer", "await after" + System.currentTimeMillis());
                        }
                        catch (Exception exception) {
                            object = exception;
                            ((Throwable)object).printStackTrace();
                        }
                        finally {
                            this.b.unlock();
                        }
                    }
                }
                if (this.d.getPlayState() == 1) {
                    return null;
                }
                if (!a2.c()) continue;
                n3 = h2.c();
                f2 = a2.d();
                n2 = Math.round((float)n3 * f2);
                object = this.c(n2);
                LoggerProxy.d("AudioTrackPlayer", "percent=" + f2 + "--currentProgress=" + n2 + "--progress=" + (int)object);
                H h3 = (H)h2.E();
                h3.d((int)object);
                this.e(h3);
            }
            if (e2 == E.b) {
                int n7 = this.f.d();
                H h4 = (H)h2.E();
                h4.d(n7);
                this.e(h4);
                this.c(h2);
            }
        } else {
            LoggerProxy.d("AudioTrackPlayer", "put responseBag=null");
        }
        LoggerProxy.d("AudioTrackPlayer", "end put");
        return null;
    }

    private int h() {
        int n2 = this.e.h();
        int n3 = this.e.a();
        int n4 = n3 * 2 / n2;
        return n4;
    }

    private void b(H h2) {
        this.f.a(this.h());
        this.f.a();
        this.h = 0;
        this.d(h2);
    }

    private void c(H h2) {
        this.f.b();
        this.f(h2);
    }

    private int c(int n2) {
        if (n2 > this.h) {
            this.h = n2;
        }
        return this.h;
    }

    private void d(H h2) {
        if (this.a != null) {
            a.a(h2);
        }
    }

    private void e(H h2) {
        if (this.a != null) {
            a.b(h2);
        }
    }

    private void f(H h2) {
        if (this.a != null) {
            a.c(h2);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class a
    extends C<A> {
        private int a = K.b.a();
        private int b = 4;
        private int c = 2;
        private int d = 1;
        private float e = 1.0f;
        private float f = 1.0f;

        public int a() {
            return this.a;
        }

        public void a(int n2) {
            this.a = n2;
        }

        public int b() {
            return this.b;
        }

        public int c() {
            return this.c;
        }

        public int d() {
            return this.d;
        }

        public float e() {
            return this.e;
        }

        public void a(float f2) {
            this.e = f2;
        }

        public float f() {
            return this.f;
        }

        public void b(float f2) {
            this.f = f2;
        }
    }

}

