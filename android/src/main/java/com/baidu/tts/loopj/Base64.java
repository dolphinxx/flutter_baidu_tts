/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.loopj;

import java.io.UnsupportedEncodingException;

public class Base64 {
    public static final int DEFAULT = 0;
    public static final int NO_PADDING = 1;
    public static final int NO_WRAP = 2;
    public static final int CRLF = 4;
    public static final int URL_SAFE = 8;
    public static final int NO_CLOSE = 16;

    public static byte[] decode(String str, int flags) {
        return Base64.decode(str.getBytes(), flags);
    }

    public static byte[] decode(byte[] input, int flags) {
        return Base64.decode(input, 0, input.length, flags);
    }

    public static byte[] decode(byte[] input, int offset, int len, int flags) {
        Decoder decoder = new Decoder(flags, new byte[len * 3 / 4]);
        if (!decoder.process(input, offset, len, true)) {
            throw new IllegalArgumentException("bad base-64");
        }
        if (decoder.op == decoder.output.length) {
            return decoder.output;
        }
        byte[] arrby = new byte[decoder.op];
        System.arraycopy(decoder.output, 0, arrby, 0, decoder.op);
        return arrby;
    }

    public static String encodeToString(byte[] input, int flags) {
        try {
            return new String(Base64.encode(input, flags), "US-ASCII");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new AssertionError(unsupportedEncodingException);
        }
    }

    public static String encodeToString(byte[] input, int offset, int len, int flags) {
        try {
            return new String(Base64.encode(input, offset, len, flags), "US-ASCII");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new AssertionError(unsupportedEncodingException);
        }
    }

    public static byte[] encode(byte[] input, int flags) {
        return Base64.encode(input, 0, input.length, flags);
    }

    public static byte[] encode(byte[] input, int offset, int len, int flags) {
        Encoder encoder = new Encoder(flags, null);
        int n2 = len / 3 * 4;
        if (encoder.do_padding) {
            if (len % 3 > 0) {
                n2 += 4;
            }
        } else {
            switch (len % 3) {
                case 0: {
                    break;
                }
                case 1: {
                    n2 += 2;
                    break;
                }
                case 2: {
                    n2 += 3;
                }
            }
        }
        if (encoder.do_newline && len > 0) {
            n2 += ((len - 1) / 57 + 1) * (encoder.do_cr ? 2 : 1);
        }
        encoder.output = new byte[n2];
        encoder.process(input, offset, len, true);
        return encoder.output;
    }

    private Base64() {
    }

    static class Encoder
    extends Coder {
        public static final int LINE_GROUPS = 19;
        private static final byte[] ENCODE = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
        private static final byte[] ENCODE_WEBSAFE = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
        private final byte[] tail;
        int tailLen;
        private int count;
        public final boolean do_padding;
        public final boolean do_newline;
        public final boolean do_cr;
        private final byte[] alphabet;

        public Encoder(int flags, byte[] output) {
            this.output = output;
            this.do_padding = (flags & 1) == 0;
            this.do_newline = (flags & 2) == 0;
            this.do_cr = (flags & 4) != 0;
            this.alphabet = (flags & 8) == 0 ? ENCODE : ENCODE_WEBSAFE;
            this.tail = new byte[2];
            this.tailLen = 0;
            this.count = this.do_newline ? 19 : -1;
        }

        public int maxOutputSize(int len) {
            return len * 8 / 5 + 10;
        }

        public boolean process(byte[] input, int offset, int len, boolean finish) {
            byte[] arrby = this.alphabet;
            byte[] arrby2 = this.output;
            int n2 = 0;
            int n3 = this.count;
            int n4 = offset;
            len += offset;
            int n5 = -1;
            switch (this.tailLen) {
                case 0: {
                    break;
                }
                case 1: {
                    if (n4 + 2 > len) break;
                    n5 = (this.tail[0] & 255) << 16 | (input[n4++] & 255) << 8 | input[n4++] & 255;
                    this.tailLen = 0;
                    break;
                }
                case 2: {
                    if (n4 + 1 > len) break;
                    n5 = (this.tail[0] & 255) << 16 | (this.tail[1] & 255) << 8 | input[n4++] & 255;
                    this.tailLen = 0;
                }
            }
            if (n5 != -1) {
                arrby2[n2++] = arrby[n5 >> 18 & 63];
                arrby2[n2++] = arrby[n5 >> 12 & 63];
                arrby2[n2++] = arrby[n5 >> 6 & 63];
                arrby2[n2++] = arrby[n5 & 63];
                if (--n3 == 0) {
                    if (this.do_cr) {
                        arrby2[n2++] = 13;
                    }
                    arrby2[n2++] = 10;
                    n3 = 19;
                }
            }
            while (n4 + 3 <= len) {
                n5 = (input[n4] & 255) << 16 | (input[n4 + 1] & 255) << 8 | input[n4 + 2] & 255;
                arrby2[n2] = arrby[n5 >> 18 & 63];
                arrby2[n2 + 1] = arrby[n5 >> 12 & 63];
                arrby2[n2 + 2] = arrby[n5 >> 6 & 63];
                arrby2[n2 + 3] = arrby[n5 & 63];
                n4 += 3;
                n2 += 4;
                if (--n3 != 0) continue;
                if (this.do_cr) {
                    arrby2[n2++] = 13;
                }
                arrby2[n2++] = 10;
                n3 = 19;
            }
            if (finish) {
                if (n4 - this.tailLen == len - 1) {
                    int n6 = 0;
                    n5 = ((this.tailLen > 0 ? this.tail[n6++] : input[n4++]) & 255) << 4;
                    this.tailLen -= n6;
                    arrby2[n2++] = arrby[n5 >> 6 & 63];
                    arrby2[n2++] = arrby[n5 & 63];
                    if (this.do_padding) {
                        arrby2[n2++] = 61;
                        arrby2[n2++] = 61;
                    }
                    if (this.do_newline) {
                        if (this.do_cr) {
                            arrby2[n2++] = 13;
                        }
                        arrby2[n2++] = 10;
                    }
                } else if (n4 - this.tailLen == len - 2) {
                    int n7 = 0;
                    n5 = ((this.tailLen > 1 ? this.tail[n7++] : input[n4++]) & 255) << 10 | ((this.tailLen > 0 ? this.tail[n7++] : input[n4++]) & 255) << 2;
                    this.tailLen -= n7;
                    arrby2[n2++] = arrby[n5 >> 12 & 63];
                    arrby2[n2++] = arrby[n5 >> 6 & 63];
                    arrby2[n2++] = arrby[n5 & 63];
                    if (this.do_padding) {
                        arrby2[n2++] = 61;
                    }
                    if (this.do_newline) {
                        if (this.do_cr) {
                            arrby2[n2++] = 13;
                        }
                        arrby2[n2++] = 10;
                    }
                } else if (this.do_newline && n2 > 0 && n3 != 19) {
                    if (this.do_cr) {
                        arrby2[n2++] = 13;
                    }
                    arrby2[n2++] = 10;
                }
            } else if (n4 == len - 1) {
                this.tail[this.tailLen++] = input[n4];
            } else if (n4 == len - 2) {
                this.tail[this.tailLen++] = input[n4];
                this.tail[this.tailLen++] = input[n4 + 1];
            }
            this.op = n2;
            this.count = n3;
            return true;
        }
    }

    static class Decoder
    extends Coder {
        private static final int[] DECODE = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int[] DECODE_WEBSAFE = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int SKIP = -1;
        private static final int EQUALS = -2;
        private int state;
        private int value;
        private final int[] alphabet;

        public Decoder(int flags, byte[] output) {
            this.output = output;
            this.alphabet = (flags & 8) == 0 ? DECODE : DECODE_WEBSAFE;
            this.state = 0;
            this.value = 0;
        }

        public int maxOutputSize(int len) {
            return len * 3 / 4 + 10;
        }

        public boolean process(byte[] input, int offset, int len, boolean finish) {
            if (this.state == 6) {
                return false;
            }
            int n2 = offset;
            int n3 = this.state;
            int n4 = this.value;
            int n5 = 0;
            byte[] arrby = this.output;
            int[] arrn = this.alphabet;
            while (n2 < (len += offset)) {
                if (n3 == 0) {
                    while (n2 + 4 <= len && (n4 = arrn[input[n2] & 255] << 18 | arrn[input[n2 + 1] & 255] << 12 | arrn[input[n2 + 2] & 255] << 6 | arrn[input[n2 + 3] & 255]) >= 0) {
                        arrby[n5 + 2] = (byte)n4;
                        arrby[n5 + 1] = (byte)(n4 >> 8);
                        arrby[n5] = (byte)(n4 >> 16);
                        n5 += 3;
                        n2 += 4;
                    }
                    if (n2 >= len) break;
                }
                int n6 = arrn[input[n2++] & 255];
                switch (n3) {
                    case 0: {
                        if (n6 >= 0) {
                            n4 = n6;
                            ++n3;
                            break;
                        }
                        if (n6 == -1) break;
                        this.state = 6;
                        return false;
                    }
                    case 1: {
                        if (n6 >= 0) {
                            n4 = n4 << 6 | n6;
                            ++n3;
                            break;
                        }
                        if (n6 == -1) break;
                        this.state = 6;
                        return false;
                    }
                    case 2: {
                        if (n6 >= 0) {
                            n4 = n4 << 6 | n6;
                            ++n3;
                            break;
                        }
                        if (n6 == -2) {
                            arrby[n5++] = (byte)(n4 >> 4);
                            n3 = 4;
                            break;
                        }
                        if (n6 == -1) break;
                        this.state = 6;
                        return false;
                    }
                    case 3: {
                        if (n6 >= 0) {
                            n4 = n4 << 6 | n6;
                            arrby[n5 + 2] = (byte)n4;
                            arrby[n5 + 1] = (byte)(n4 >> 8);
                            arrby[n5] = (byte)(n4 >> 16);
                            n5 += 3;
                            n3 = 0;
                            break;
                        }
                        if (n6 == -2) {
                            arrby[n5 + 1] = (byte)(n4 >> 2);
                            arrby[n5] = (byte)(n4 >> 10);
                            n5 += 2;
                            n3 = 5;
                            break;
                        }
                        if (n6 == -1) break;
                        this.state = 6;
                        return false;
                    }
                    case 4: {
                        if (n6 == -2) {
                            ++n3;
                            break;
                        }
                        if (n6 == -1) break;
                        this.state = 6;
                        return false;
                    }
                    case 5: {
                        if (n6 == -1) break;
                        this.state = 6;
                        return false;
                    }
                }
            }
            if (!finish) {
                this.state = n3;
                this.value = n4;
                this.op = n5;
                return true;
            }
            switch (n3) {
                case 0: {
                    break;
                }
                case 1: {
                    this.state = 6;
                    return false;
                }
                case 2: {
                    arrby[n5++] = (byte)(n4 >> 4);
                    break;
                }
                case 3: {
                    arrby[n5++] = (byte)(n4 >> 10);
                    arrby[n5++] = (byte)(n4 >> 2);
                    break;
                }
                case 4: {
                    this.state = 6;
                    return false;
                }
            }
            this.state = n3;
            this.op = n5;
            return true;
        }
    }

    static abstract class Coder {
        public byte[] output;
        public int op;

        Coder() {
        }

        public abstract boolean process(byte[] var1, int var2, int var3, boolean var4);

        public abstract int maxOutputSize(int var1);
    }

}

