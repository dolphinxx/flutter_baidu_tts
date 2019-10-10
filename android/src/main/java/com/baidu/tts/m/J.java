/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.m;

import com.baidu.tts.b.a.b.E;
import com.baidu.tts.b.a.b.F;
import com.baidu.tts.b.b.b.B;
import com.baidu.tts.f.C;
import com.baidu.tts.f.D;
import com.baidu.tts.f.G;
import com.baidu.tts.m.A;
//import com.baidu.tts.m.B;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class J
extends com.baidu.tts.n.A<J> {
    private com.baidu.tts.m.B a = new com.baidu.tts.m.B();
    private A b = new A();

    public com.baidu.tts.m.B a() {
        return this.a;
    }

    public A b() {
        return this.b;
    }

    public F.b c() {
        return this.a.a();
    }

    public E.b d() {
        return this.a.b();
    }

    public B.a e() {
        return this.b.a();
    }

    public int a(G g2, String string) {
        return this.b(g2, string);
    }

    private int b(G g2, String string) {
        E.b b2 = this.a.b();
        F.b b3 = this.a.a();
        B.a a2 = this.b.a();
        switch (g2) {
            case D: {
                this.a.a(string);
                break;
            }
            case E: {
                this.a.b(string);
                break;
            }
            case F: {
                this.a.c(string);
                break;
            }
            case P: {
                b2.d(string);
                break;
            }
            case Q: {
                b2.e(string);
                break;
            }
            case R: {
                b2.f(string);
                break;
            }
            case A: {
                b2.g(string);
                break;
            }
            case H: {
                D d2 = D.a(string);
                b3.a(d2);
                break;
            }
            case y: {
                a2.a(string);
                break;
            }
            case O: {
                b3.f(string);
                break;
            }
            case al: {
                b3.g(string);
                break;
            }
            case J: {
                com.baidu.tts.f.B b4 = com.baidu.tts.f.B.a(string);
                return b3.a(b4);
            }
            case K: {
                C c2 = C.a(string);
                b3.a(c2);
                break;
            }
            case N: {
                b3.j(string);
                break;
            }
            case S: {
                return b2.c(string);
            }
            case G: {
                b3.n(string);
                b2.n(string);
                break;
            }
            case T: {
                return this.a.d(string);
            }
            case af: {
                b3.l(string);
                break;
            }
            case L: {
                b3.h(string);
                break;
            }
            case M: {
                b3.i(string);
                break;
            }
            case ae: {
                b3.k(string);
                break;
            }
            case U: {
                return b2.a(string);
            }
            case ah: {
                b3.d(string);
                break;
            }
            case ai: {
                b3.e(string);
                break;
            }
            case I: {
                int n2 = Integer.parseInt(string);
                this.b.a(n2);
                break;
            }
            case x: {
                com.baidu.tts.f.J j2 = null;
                try {
                    j2 = com.baidu.tts.f.J.valueOf(string);
                }
                catch (Exception exception) {
                    j2 = com.baidu.tts.f.J.a;
                }
                this.a.a(j2);
                break;
            }
            case V: {
                b2.b(string);
                break;
            }
            case ak: {
                b2.h(string);
                break;
            }
            case am: {
                b3.c(string);
                break;
            }
            case an: {
                int n3 = Integer.parseInt(string);
                b3.a(n3);
                break;
            }
            case ao: {
                b3.b(string);
                break;
            }
            case ap: {
                b3.a(string);
                break;
            }
            case e: {
                b3.m(string);
                break;
            }
        }
        return 0;
    }

}

