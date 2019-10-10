/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.loopj;

import com.baidu.tts.loopj.Base64;
import com.baidu.tts.loopj.Base64DataException;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Base64OutputStream
extends FilterOutputStream {
    private final Base64.Coder coder;
    private final int flags;
    private byte[] buffer = null;
    private int bpos = 0;
    private static byte[] EMPTY = new byte[0];

    public Base64OutputStream(OutputStream out, int flags) {
        this(out, flags, true);
    }

    public Base64OutputStream(OutputStream out, int flags, boolean encode) {
        super(out);
        this.flags = flags;
        this.coder = encode ? new Base64.Encoder(flags, null) : new Base64.Decoder(flags, null);
    }

    public void write(int b2) throws IOException {
        if (this.buffer == null) {
            this.buffer = new byte[1024];
        }
        if (this.bpos >= this.buffer.length) {
            this.internalWrite(this.buffer, 0, this.bpos, false);
            this.bpos = 0;
        }
        this.buffer[this.bpos++] = (byte)b2;
    }

    private void flushBuffer() throws IOException {
        if (this.bpos > 0) {
            this.internalWrite(this.buffer, 0, this.bpos, false);
            this.bpos = 0;
        }
    }

    public void write(byte[] b2, int off, int len) throws IOException {
        if (len <= 0) {
            return;
        }
        this.flushBuffer();
        this.internalWrite(b2, off, len, false);
    }

    public void close() throws IOException {
        IOException iOException;
        block7 : {
            iOException = null;
            try {
                this.flushBuffer();
                this.internalWrite(EMPTY, 0, 0, true);
            }
            catch (IOException iOException2) {
                iOException = iOException2;
            }
            try {
                if ((this.flags & 16) == 0) {
                    this.out.close();
                } else {
                    this.out.flush();
                }
            }
            catch (IOException iOException3) {
                if (iOException == null) break block7;
                iOException = iOException3;
            }
        }
        if (iOException != null) {
            throw iOException;
        }
    }

    private void internalWrite(byte[] b2, int off, int len, boolean finish) throws IOException {
        this.coder.output = this.embiggen(this.coder.output, this.coder.maxOutputSize(len));
        if (!this.coder.process(b2, off, len, finish)) {
            throw new Base64DataException("bad base-64");
        }
        this.out.write(this.coder.output, 0, this.coder.op);
    }

    private byte[] embiggen(byte[] b2, int len) {
        if (b2 == null || b2.length < len) {
            return new byte[len];
        }
        return b2;
    }
}

