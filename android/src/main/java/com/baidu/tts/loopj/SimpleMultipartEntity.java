/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 *  org.apache.http.Header
 *  org.apache.http.HttpEntity
 *  org.apache.http.message.BasicHeader
 */
package com.baidu.tts.loopj;

import android.text.TextUtils;
import com.baidu.tts.loopj.AsyncHttpClient;
import com.baidu.tts.loopj.LogInterface;
import com.baidu.tts.loopj.ResponseHandlerInterface;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;

class SimpleMultipartEntity
implements HttpEntity {
    private static final String LOG_TAG = "SimpleMultipartEntity";
    private static final String STR_CR_LF = "\r\n";
    private static final byte[] CR_LF = "\r\n".getBytes();
    private static final byte[] TRANSFER_ENCODING_BINARY = "Content-Transfer-Encoding: binary\r\n".getBytes();
    private static final char[] MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private final String boundary;
    private final byte[] boundaryLine;
    private final byte[] boundaryEnd;
    private boolean isRepeatable;
    private final List<FilePart> fileParts = new ArrayList<FilePart>();
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final ResponseHandlerInterface progressHandler;
    private long bytesWritten;
    private long totalSize;

    public SimpleMultipartEntity(ResponseHandlerInterface progressHandler) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i2 = 0; i2 < 30; ++i2) {
            stringBuilder.append(MULTIPART_CHARS[random.nextInt(MULTIPART_CHARS.length)]);
        }
        this.boundary = stringBuilder.toString();
        this.boundaryLine = ("--" + this.boundary + STR_CR_LF).getBytes();
        this.boundaryEnd = ("--" + this.boundary + "--" + STR_CR_LF).getBytes();
        this.progressHandler = progressHandler;
    }

    public void addPart(String key, String value, String contentType) {
        try {
            this.out.write(this.boundaryLine);
            this.out.write(this.createContentDisposition(key));
            this.out.write(this.createContentType(contentType));
            this.out.write(CR_LF);
            this.out.write(value.getBytes());
            this.out.write(CR_LF);
        }
        catch (IOException iOException) {
            AsyncHttpClient.log.e(LOG_TAG, "addPart ByteArrayOutputStream exception", iOException);
        }
    }

    public void addPartWithCharset(String key, String value, String charset) {
        if (charset == null) {
            charset = "UTF-8";
        }
        this.addPart(key, value, "text/plain; charset=" + charset);
    }

    public void addPart(String key, String value) {
        this.addPartWithCharset(key, value, null);
    }

    public void addPart(String key, File file) {
        this.addPart(key, file, null);
    }

    public void addPart(String key, File file, String type) {
        this.fileParts.add(new FilePart(key, file, this.normalizeContentType(type)));
    }

    public void addPart(String key, File file, String type, String customFileName) {
        this.fileParts.add(new FilePart(key, file, this.normalizeContentType(type), customFileName));
    }

    public void addPart(String key, String streamName, InputStream inputStream, String type) throws IOException {
        int n2;
        this.out.write(this.boundaryLine);
        this.out.write(this.createContentDisposition(key, streamName));
        this.out.write(this.createContentType(type));
        this.out.write(TRANSFER_ENCODING_BINARY);
        this.out.write(CR_LF);
        byte[] arrby = new byte[4096];
        while ((n2 = inputStream.read(arrby)) != -1) {
            this.out.write(arrby, 0, n2);
        }
        this.out.write(CR_LF);
        this.out.flush();
    }

    private String normalizeContentType(String type) {
        return type == null ? "application/octet-stream" : type;
    }

    private byte[] createContentType(String type) {
        String string = "Content-Type: " + this.normalizeContentType(type) + STR_CR_LF;
        return string.getBytes();
    }

    private byte[] createContentDisposition(String key) {
        return ("Content-Disposition: form-data; name=\"" + key + "\"" + STR_CR_LF).getBytes();
    }

    private byte[] createContentDisposition(String key, String fileName) {
        return ("Content-Disposition: form-data; name=\"" + key + "\"" + "; filename=\"" + fileName + "\"" + STR_CR_LF).getBytes();
    }

    private void updateProgress(long count) {
        this.bytesWritten += count;
        this.progressHandler.sendProgressMessage(this.bytesWritten, this.totalSize);
    }

    public long getContentLength() {
        long l2 = this.out.size();
        for (FilePart filePart : this.fileParts) {
            long l3 = filePart.getTotalLength();
            if (l3 < 0L) {
                return -1L;
            }
            l2 += l3;
        }
        return l2 += (long)this.boundaryEnd.length;
    }

    public Header getContentType() {
        return new BasicHeader("Content-Type", "multipart/form-data; boundary=" + this.boundary);
    }

    public boolean isChunked() {
        return false;
    }

    public void setIsRepeatable(boolean isRepeatable) {
        this.isRepeatable = isRepeatable;
    }

    public boolean isRepeatable() {
        return this.isRepeatable;
    }

    public boolean isStreaming() {
        return false;
    }

    public void writeTo(OutputStream outstream) throws IOException {
        this.bytesWritten = 0L;
        this.totalSize = (int)this.getContentLength();
        this.out.writeTo(outstream);
        this.updateProgress(this.out.size());
        for (FilePart filePart : this.fileParts) {
            filePart.writeTo(outstream);
        }
        outstream.write(this.boundaryEnd);
        this.updateProgress(this.boundaryEnd.length);
    }

    public Header getContentEncoding() {
        return null;
    }

    public void consumeContent() throws IOException, UnsupportedOperationException {
        if (this.isStreaming()) {
            throw new UnsupportedOperationException("Streaming entity does not implement #consumeContent()");
        }
    }

    public InputStream getContent() throws IOException, UnsupportedOperationException {
        throw new UnsupportedOperationException("getContent() is not supported. Use writeTo() instead.");
    }

    private class FilePart {
        public File file;
        public byte[] header;

        public FilePart(String key, File file, String type, String customFileName) {
            this.header = this.createHeader(key, TextUtils.isEmpty((CharSequence)customFileName) ? file.getName() : customFileName, type);
            this.file = file;
        }

        public FilePart(String key, File file, String type) {
            this.header = this.createHeader(key, file.getName(), type);
            this.file = file;
        }

        private byte[] createHeader(String key, String filename, String type) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                byteArrayOutputStream.write(SimpleMultipartEntity.this.boundaryLine);
                byteArrayOutputStream.write(SimpleMultipartEntity.this.createContentDisposition(key, filename));
                byteArrayOutputStream.write(SimpleMultipartEntity.this.createContentType(type));
                byteArrayOutputStream.write(TRANSFER_ENCODING_BINARY);
                byteArrayOutputStream.write(CR_LF);
            }
            catch (IOException iOException) {
                AsyncHttpClient.log.e(SimpleMultipartEntity.LOG_TAG, "createHeader ByteArrayOutputStream exception", iOException);
            }
            return byteArrayOutputStream.toByteArray();
        }

        public long getTotalLength() {
            long l2 = this.file.length() + (long)CR_LF.length;
            return (long)this.header.length + l2;
        }

        public void writeTo(OutputStream out) throws IOException {
            int n2;
            out.write(this.header);
            SimpleMultipartEntity.this.updateProgress(this.header.length);
            FileInputStream fileInputStream = new FileInputStream(this.file);
            byte[] arrby = new byte[4096];
            while ((n2 = fileInputStream.read(arrby)) != -1) {
                out.write(arrby, 0, n2);
                SimpleMultipartEntity.this.updateProgress(n2);
            }
            out.write(CR_LF);
            SimpleMultipartEntity.this.updateProgress(CR_LF.length);
            out.flush();
            AsyncHttpClient.silentCloseInputStream(fileInputStream);
        }
    }

}

