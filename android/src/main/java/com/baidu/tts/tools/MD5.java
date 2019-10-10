/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.security.MessageDigest;

public class MD5 {
    private static volatile MD5 a = null;
    private char[] b = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private MD5() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static MD5 getInstance() {
        if (a != null) return a;
        Class<MD5> class_ = MD5.class;
        synchronized (MD5.class) {
            if (a != null) return a;
            {
                a = new MD5();
            }
            // ** MonitorExit[var0] (shouldn't be in output)
            return a;
        }
    }

    public String getMD5(byte[] data) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(data);
            byte[] arrby = messageDigest.digest();
            int n2 = arrby.length;
            char[] arrc = new char[n2 * 2];
            int n3 = 0;
            for (int i2 = 0; i2 < n2; ++i2) {
                arrc[n3++] = this.b[arrby[i2] >>> 4 & 15];
                arrc[n3++] = this.b[arrby[i2] & 15];
            }
            return new String(arrc);
        }
        catch (Exception exception) {
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getBigFileMd5(File file) {
        if (file != null) {
            FileInputStream fileInputStream = null;
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                fileInputStream = new FileInputStream(file);
                byte[] arrby = new byte[8192];
                int n2 = 0;
                while ((n2 = fileInputStream.read(arrby)) != -1) {
                    messageDigest.update(arrby, 0, n2);
                }
                String string = this.a(messageDigest.digest());
                return string;
            }
            catch (Exception exception) {
                String string = null;
                return string;
            }
            finally {
                try {
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                }
                catch (IOException iOException) {
                    iOException.printStackTrace();
                }
            }
        }
        return null;
    }

    public String getBigFileMd5(String filePath) {
        File file = new File(filePath);
        return this.getBigFileMd5(file);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getFileMd5(String filePath) {
        FileInputStream fileInputStream = null;
        AbstractInterruptibleChannel abstractInterruptibleChannel = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            File file = new File(filePath);
            fileInputStream = new FileInputStream(file);
            abstractInterruptibleChannel = fileInputStream.getChannel();
            MappedByteBuffer mappedByteBuffer = ((FileChannel)abstractInterruptibleChannel).map(FileChannel.MapMode.READ_ONLY, 0L, file.length());
            messageDigest.update(mappedByteBuffer);
            String string = this.a(messageDigest.digest());
            return string;
        }
        catch (Exception exception) {
            String string = null;
            return string;
        }
        finally {
            try {
                if (abstractInterruptibleChannel != null) {
                    abstractInterruptibleChannel.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }
    }

    private String a(byte[] arrby) {
        return this.a(arrby, 0, arrby.length);
    }

    private String a(byte[] arrby, int n2, int n3) {
        StringBuffer stringBuffer = new StringBuffer(2 * n3);
        int n4 = n2 + n3;
        for (int i2 = n2; i2 < n4; ++i2) {
            this.a(arrby[i2], stringBuffer);
        }
        return stringBuffer.toString();
    }

    private void a(byte by, StringBuffer stringBuffer) {
        char c2 = this.b[(by & 240) >> 4];
        char c3 = this.b[by & 15];
        stringBuffer.append(c2);
        stringBuffer.append(c3);
    }
}

