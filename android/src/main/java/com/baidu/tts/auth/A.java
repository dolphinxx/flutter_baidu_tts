/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.auth;

import com.baidu.tts.aop.tts.TtsError;
//import com.baidu.tts.auth.AuthInfo;
//import com.baidu.tts.auth.B;
//import com.baidu.tts.auth.C;
import com.baidu.tts.b.a.b.E;
import com.baidu.tts.b.a.b.F;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.f.L;
import com.baidu.tts.f.M;
import com.baidu.tts.f.N;
import com.baidu.tts.m.J;
import com.baidu.tts.tools.StringTool;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class A {
    private static volatile A a = null;
    private com.baidu.tts.k.C<C, C.a> b = new com.baidu.tts.k.C<>();
    private com.baidu.tts.k.C<B, B.a> c = new com.baidu.tts.k.C<>();

    private A() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static A a() {
        if (a != null) return a;
//        Class<a> class_ = a.class;
        synchronized (a.class) {
            if (a != null) return a;
            {
                a = new A();
            }
            // ** MonitorExit[var0] (shouldn't be in output)
            return a;
        }
    }

    public AuthInfo a(M m2, J j2) {
        com.baidu.tts.m.B b2 = j2.a();
        AuthInfo authInfo = new AuthInfo();
        authInfo.setTtsEnum(m2);
        switch (m2) {
            case a: {
                C.a a2 = this.a(b2.a());
                authInfo.setOnlineResult(a2);
                break;
            }
            case b: {
                B.a a3 = this.a(b2.b());
                authInfo.setOfflineResult(a3);
                break;
            }
            case c: {
                authInfo = this.a(b2);
                break;
            }
        }
        return authInfo;
    }

    public C.a a(F.b b2) {
        C.a a2 = new C.a();
        try {
            a2 = this.a(new b(b2), L.a.a());
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            a2.a(com.baidu.tts.h.a.C.a().a(N.d, interruptedException));
        }
        catch (ExecutionException executionException) {
            a2.a(com.baidu.tts.h.a.C.a().a(N.e, executionException.getCause()));
        }
        catch (TimeoutException timeoutException) {
            a2.a(com.baidu.tts.h.a.C.a().a(N.f, timeoutException));
        }
        catch (CancellationException cancellationException) {
            a2.a(com.baidu.tts.h.a.C.a().a(N.p, cancellationException));
        }
        return a2;
    }

    public B.a a(E.b b2) {
        B.a a2 = new B.a();
        try {
            a2 = this.a(new a(b2), L.a.a());
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            a2.a(com.baidu.tts.h.a.C.a().a(N.v, interruptedException));
        }
        catch (ExecutionException executionException) {
            a2.a(com.baidu.tts.h.a.C.a().a(N.w, executionException.getCause()));
        }
        catch (TimeoutException timeoutException) {
            a2.a(com.baidu.tts.h.a.C.a().a(N.x, timeoutException));
        }
        catch (CancellationException cancellationException) {
            a2.a(com.baidu.tts.h.a.C.a().a(N.I, cancellationException));
        }
        return a2;
    }

    public AuthInfo a(final com.baidu.tts.m.B b2) {
        LoggerProxy.d("AuthClient", "enter authMix");
        final CountDownLatch countDownLatch = new CountDownLatch(2);
        FutureTask<C.a> futureTask = new FutureTask<C.a>(new Callable<C.a>(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            public C.a a() throws Exception {
                try {
                    F.b b22 = b2.a();
                    C.a a2 = A.this.a(b22);
                    return a2;
                }
                finally {
                    countDownLatch.countDown();
                }
            }

            @Override
            public /* synthetic */ C.a call() throws Exception {
                return this.a();
            }
        });
        FutureTask<B.a> futureTask2 = new FutureTask<B.a>(new Callable<B.a>(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            public B.a a() throws Exception {
                try {
                    E.b b22 = b2.b();
                    B.a a2 = A.this.a(b22);
                    return a2;
                }
                finally {
                    countDownLatch.countDown();
                }
            }

            @Override
            public /* synthetic */ B.a call() throws Exception {
                return this.a();
            }
        });
        Thread thread = new Thread(futureTask);
        thread.start();
        Thread thread2 = new Thread(futureTask2);
        thread2.start();
        try {
            LoggerProxy.d("AuthClient", "+ await");
            countDownLatch.await();
            LoggerProxy.d("AuthClient", "- await");
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            futureTask.cancel(true);
            futureTask2.cancel(true);
        }
        C.a a2 = new C.a();
        LoggerProxy.d("AuthClient", "+ mix online get onlineResult=" + a2);
        try {
            a2 = futureTask.get();
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            futureTask.cancel(true);
            a2.a(com.baidu.tts.h.a.C.a().a(N.d, interruptedException));
        }
        catch (ExecutionException executionException) {
            a2.a(com.baidu.tts.h.a.C.a().a(N.e, executionException.getCause()));
        }
        catch (CancellationException cancellationException) {
            a2.a(com.baidu.tts.h.a.C.a().a(N.p, cancellationException));
        }
        LoggerProxy.d("AuthClient", "- online get");
        B.a a3 = new B.a();
        LoggerProxy.d("AuthClient", "+ mix offline get offlineResult=" + a3);
        try {
            a3 = futureTask2.get();
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            futureTask2.cancel(true);
            a3.a(com.baidu.tts.h.a.C.a().a(N.v, interruptedException));
        }
        catch (ExecutionException executionException) {
            a3.a(com.baidu.tts.h.a.C.a().a(N.w, executionException.getCause()));
        }
        catch (CancellationException cancellationException) {
            a3.a(com.baidu.tts.h.a.C.a().a(N.I, cancellationException));
        }
        LoggerProxy.d("AuthClient", "- offline get");
        AuthInfo authInfo = new AuthInfo();
        authInfo.setTtsEnum(M.c);
        authInfo.setOnlineResult(a2);
        authInfo.setOfflineResult(a3);
        LoggerProxy.d("AuthClient", "end authMix");
        return authInfo;
    }

    private <T> T a(Callable<T> callable, long l2) throws InterruptedException, ExecutionException, TimeoutException {
        FutureTask<T> futureTask = this.a(callable);
        return futureTask.get(l2, TimeUnit.MILLISECONDS);
    }

    private <T> FutureTask<T> a(Callable<T> callable) {
        FutureTask<T> futureTask = new FutureTask<T>(callable);
        Thread thread = new Thread(futureTask);
        thread.start();
        return futureTask;
    }

    public void b() {
        if (this.b != null) {
            this.b.a();
        }
        if (this.c != null) {
            this.c.a();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private class a
    implements Callable<B.a> {
        private E.b b;

        public a(E.b b2) {
            this.b = b2;
        }

        public B.a a() throws Exception {
            B.a a2 = new B.a();
            com.baidu.tts.h.b.A a3 = com.baidu.tts.h.b.B.a().g();
            if (a3 == null) {
                TtsError ttsError = com.baidu.tts.h.a.C.a().b(N.Z);
                a2.a(ttsError);
                return a2;
            }
            String string = this.b.g();
            String string2 = this.b.f();
            if (StringTool.isEmpty(string2)) {
                string2 = a3.b();
            }
            LoggerProxy.d("AuthClient", "appCode=" + string);
            LoggerProxy.d("AuthClient", "licenseFilePath=" + string2);
            com.baidu.tts.auth.B b2 = new com.baidu.tts.auth.B();
            b2.a(string);
            b2.b(string2);
            return (B.a)A.this.c.a(b2);
        }

        @Override
        public /* synthetic */ B.a call() throws Exception {
            return this.a();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private class b
    implements Callable<C.a> {
        private F.b b;

        public b(F.b b2) {
            this.b = b2;
        }

        public C.a a() throws Exception {
            String string = this.b.i();
            String string2 = this.b.e();
            String string3 = this.b.f();
            String string4 = this.b.j();
            String string5 = this.b.b();
            LoggerProxy.d("AuthClient", "pid=" + string);
            LoggerProxy.d("AuthClient", "key=" + string4);
            LoggerProxy.d("AuthClient", "ak=" + string2);
            LoggerProxy.d("AuthClient", "sk=" + string3);
            C c2 = new C();
            c2.b(string);
            c2.c(string2);
            c2.d(string3);
            c2.a(string5);
            return (C.a)A.this.b.a(c2);
        }

        @Override
        public /* synthetic */ C.a call() throws Exception {
            return this.a();
        }
    }

}

