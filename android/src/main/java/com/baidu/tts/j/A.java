/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.j;

import com.baidu.tts.aop.tts.TtsError;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.j.B;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class A
implements B {
    protected final Lock d = new ReentrantLock();
    protected final Condition e = this.d.newCondition();

    public synchronized TtsError b() {
        return this.g();
    }

    protected abstract TtsError g();

    public synchronized void A() {
        this.h();
    }

    protected abstract void h();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void c() {
        this.i();
        try {
            this.d.lock();
            this.e.signalAll();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        finally {
            this.d.unlock();
        }
    }

    protected abstract void i();

    public synchronized void d() {
        this.j();
    }

    protected abstract void j();

    public synchronized void e() {
        this.k();
    }

    protected abstract void k();

    public synchronized void f() {
        this.l();
    }

    protected abstract void l();

    public void a(a a2) throws InterruptedException {
        while (this.m()) {
            this.b(a2);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void b(a a2) throws InterruptedException {
        try {
            this.d.lock();
            if (a2 != null) {
                a2.a();
            }
            LoggerProxy.d("ASafeLife", "before await");
            this.e.await();
            LoggerProxy.d("ASafeLife", "after await");
        }
        finally {
            this.d.unlock();
        }
    }

    public void B() {
        Thread.currentThread().interrupt();
    }

    public boolean C() {
        try {
            this.a(null);
        }
        catch (InterruptedException interruptedException) {
            this.B();
        }
        return !this.n();
    }

    public abstract boolean m();

    public abstract boolean n();

    public static interface a {
        public void a();
    }

}

