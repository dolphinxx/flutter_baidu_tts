/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.f;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public enum N {
    a(Na.b, -1, "online engine auth failure"),
    b(Na.b, -2, "request failure"),
    c(Na.b, -3, "cancel failure"),
    d(Na.b, -4, "InterruptedException"),
    e(Na.b, -5, "ExecutionException"),
    f(Na.b, -6, "TimeoutException"),
    g(Na.b, -7, "request result contains error message"),
    h(Na.b, -8, "access token is null, please check your apikey and secretkey or product id"),
    i(Na.b, -9, "online engine is not initial"),
    j(Na.b, -10, "online engine call synthesize exception"),
    k(Na.b, -11, "this method is not supported by online mode(please use other mode)"),
    l(Na.b, -12, "request result parse error may responsebag is null"),
    m(Na.b, -13, "online synthesize get was interrupted"),
    n(Na.b, -14, "online synthesize get exception"),
    o(Na.b, -15, "online synthesize get was timeout"),
    p(Na.b, -16, "CancellationException"),
    q(Na.b, -17, "online engine server ip is null"),
    r(Na.c, -100, "offline engine auth failure,please check you offline auth params"),
    s(Na.c, -101, "offline engine cancel failure"),
    t(Na.c, -102, "offline engine download license failure"),
    u(Na.c, -103, "offline engine auth authinfo is null"),
    v(Na.c, -105, "InterruptedException"),
    w(Na.c, -106, "ExecutionException"),
    x(Na.c, -107, "TimeoutException"),
    y(Na.c, -108, "bdTTSEngineInit failed,please check you offline params"),
    z(Na.c, -109, "offline engine is uninitialized,please invoke initTts() method"),
    A(Na.c, -110, "offline engine call synthesize exception"),
    B(Na.c, -111, "offline engine synthesize result not 0"),
    C(Na.c, -112, "offline engine auth verify expired,formal expired or temp expired"),
    D(Na.c, -113, "package name is unmatch"),
    E(Na.c, -114, "app sign is unmatch"),
    F(Na.c, -115, "devices cuid is unmatch"),
    G(Na.c, -116, "platform is unmatch"),
    H(Na.c, -117, "license file not exist or file length is 0 (download license fail)"),
    I(Na.b, -118, "CancellationException"),
    J(Na.a, -200, "both online and offline engine auth failue"),
    K(Na.a, -201, "InterruptedException"),
    L(Na.a, -202, "ExecutionException"),
    M(Na.a, -203, "TimeoutException"),
    N(Na.a, -204, "mix engine initTTS, the offline init failure"),
    O(Na.a, -205, "CancellationException"),
    P(Na.d, -300, "text is null or empty double quotation marks"),
    Q(Na.d, -301, "text length in gbk is more than 1024, the text is too long, cut it short than 1024"),
    R(Na.d, -302, "text encode is not gbk, please use gbk text"),
    S(Na.e, -400, "tts has not been initialized,invoke in a wrong state"),
    T(Na.e, -401, "tts mode unset or not the spechified value"),
    U(Na.e, -402, "\u961f\u5217\u957f\u5ea6\u5c0f\u4e8eMAX_QUEUE_SIZE\u65f6\u624d\u80fd\u52a0\u5165\u5408\u6210\u961f\u5217"),
    V(Na.e, -403, "list\u7684size\u5c0f\u4e8eMAX_LIST_SIZE\u65f6\u624d\u6709\u6548"),
    W(Na.e, -404, "\u5f15\u64ce\u505c\u6b62\u5931\u8d25"),
    X(Na.e, -405, "app id is invalid,must be less than int(11)"),
    Y(Na.e, -406, "arguments of the method is invalid"),
    Z(Na.f, -500, "context was released or persistent app value is null"),
    aa(Na.g, -600, "player is null"),
    ab(Na.h, -1000, "params is wrong"),
    ac(Na.h, -1001, "request error"),
    ad(Na.h, -1002, "server error"),
    ae(Na.h, -1003, "model item in db is invalid(fileids is empty)"),
    af(Na.h, -1004, "model file in db is invalid(abspath is empty)"),
    ag(Na.h, -1005, "this model exists(have downloaded success ever)"),
    ah(Na.h, -1006, "can't get server model info,maybe modelid invalid or request failure"),
    ai(Na.h, -1007, "can't get server file info,maybe fileid invalid or request failure"),
    aj(Na.h, -1008, "CheckWork exception happened"),
    ak(Na.h, -1009, "exception happens when file downloadwork execute"),
    al(Na.i, -9999, "unknow");
    
    private final Na am;
    private final int an;
    private final String ao;

    private N(Na a2, int n3, String string2) {
        this.am = a2;
        this.an = n3;
        this.ao = string2;
    }

    public Na a() {
        return this.am;
    }

    public int b() {
        return this.an;
    }

    public String c() {
        return this.ao;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum Na {
        a,
        b,
        c,
        d,
        e,
        f,
        g,
        h,
        i;
        
    }

}

