/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 *  org.apache.http.Header
 *  org.apache.http.HttpEntity
 *  org.apache.http.message.BasicHeader
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.baidu.tts.loopj;

import android.text.TextUtils;
import com.baidu.tts.loopj.AsyncHttpClient;
import com.baidu.tts.loopj.Base64OutputStream;
import com.baidu.tts.loopj.JsonValueInterface;
import com.baidu.tts.loopj.LogInterface;
import com.baidu.tts.loopj.RequestParams;
import com.baidu.tts.loopj.ResponseHandlerInterface;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPOutputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonStreamerEntity
implements HttpEntity {
    private static final String LOG_TAG = "JsonStreamerEntity";
    private static final UnsupportedOperationException ERR_UNSUPPORTED = new UnsupportedOperationException("Unsupported operation in this implementation.");
    private static final int BUFFER_SIZE = 4096;
    private final byte[] buffer = new byte[4096];
    private static final byte[] JSON_TRUE = "true".getBytes();
    private static final byte[] JSON_FALSE = "false".getBytes();
    private static final byte[] JSON_NULL = "null".getBytes();
    private static final byte[] STREAM_NAME = JsonStreamerEntity.escape("name");
    private static final byte[] STREAM_TYPE = JsonStreamerEntity.escape("type");
    private static final byte[] STREAM_CONTENTS = JsonStreamerEntity.escape("contents");
    private static final Header HEADER_JSON_CONTENT = new BasicHeader("Content-Type", "application/json");
    private static final Header HEADER_GZIP_ENCODING = new BasicHeader("Content-Encoding", "gzip");
    private final Map<String, Object> jsonParams = new HashMap<String, Object>();
    private final Header contentEncoding;
    private final byte[] elapsedField;
    private final ResponseHandlerInterface progressHandler;

    public JsonStreamerEntity(ResponseHandlerInterface progressHandler, boolean useGZipCompression, String elapsedField) {
        this.progressHandler = progressHandler;
        this.contentEncoding = useGZipCompression ? HEADER_GZIP_ENCODING : null;
        this.elapsedField = TextUtils.isEmpty((CharSequence)elapsedField) ? null : JsonStreamerEntity.escape(elapsedField);
    }

    public void addPart(String key, Object value) {
        this.jsonParams.put(key, value);
    }

    public boolean isRepeatable() {
        return false;
    }

    public boolean isChunked() {
        return false;
    }

    public boolean isStreaming() {
        return false;
    }

    public long getContentLength() {
        return -1L;
    }

    public Header getContentEncoding() {
        return this.contentEncoding;
    }

    public Header getContentType() {
        return HEADER_JSON_CONTENT;
    }

    public void consumeContent() throws IOException, UnsupportedOperationException {
    }

    public InputStream getContent() throws IOException, UnsupportedOperationException {
        throw ERR_UNSUPPORTED;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void writeTo(OutputStream out) throws IOException {
        if (out == null) {
            throw new IllegalStateException("Output stream cannot be null.");
        }
        long l2 = System.currentTimeMillis();
        OutputStream outputStream = this.contentEncoding != null ? new GZIPOutputStream(out, 4096) : out;
        outputStream.write(123);
        Set<String> set = this.jsonParams.keySet();
        int n2 = set.size();
        if (0 < n2) {
            int n3 = 0;
            for (String string : set) {
                ++n3;
                try {
                    Object object = this.jsonParams.get(string);
                    outputStream.write(JsonStreamerEntity.escape(string));
                    outputStream.write(58);
                    if (object == null) {
                        outputStream.write(JSON_NULL);
                        continue;
                    }
                    boolean bl = object instanceof RequestParams.FileWrapper;
                    if (bl || object instanceof RequestParams.StreamWrapper) {
                        outputStream.write(123);
                        if (bl) {
                            this.writeToFromFile(outputStream, (RequestParams.FileWrapper)object);
                        } else {
                            this.writeToFromStream(outputStream, (RequestParams.StreamWrapper)object);
                        }
                        outputStream.write(125);
                        continue;
                    }
                    if (object instanceof JsonValueInterface) {
                        outputStream.write(((JsonValueInterface)object).getEscapedJsonValue());
                        continue;
                    }
                    if (object instanceof JSONObject) {
                        outputStream.write(object.toString().getBytes());
                        continue;
                    }
                    if (object instanceof JSONArray) {
                        outputStream.write(object.toString().getBytes());
                        continue;
                    }
                    if (object instanceof Boolean) {
                        outputStream.write((Boolean)object != false ? JSON_TRUE : JSON_FALSE);
                        continue;
                    }
                    if (object instanceof Long) {
                        outputStream.write((((Number)object).longValue() + "").getBytes());
                        continue;
                    }
                    if (object instanceof Double) {
                        outputStream.write((((Number)object).doubleValue() + "").getBytes());
                        continue;
                    }
                    if (object instanceof Float) {
                        outputStream.write((((Number)object).floatValue() + "").getBytes());
                        continue;
                    }
                    if (object instanceof Integer) {
                        outputStream.write((((Number)object).intValue() + "").getBytes());
                        continue;
                    }
                    outputStream.write(JsonStreamerEntity.escape(object.toString()));
                }
                finally {
                    if (this.elapsedField == null && n3 >= n2) continue;
                    outputStream.write(44);
                }
            }
            long l3 = System.currentTimeMillis() - l2;
            if (this.elapsedField != null) {
                outputStream.write(this.elapsedField);
                outputStream.write(58);
                outputStream.write((l3 + "").getBytes());
            }
            AsyncHttpClient.log.i(LOG_TAG, "Uploaded JSON in " + Math.floor(l3 / 1000L) + " seconds");
        }
        outputStream.write(125);
        outputStream.flush();
        AsyncHttpClient.silentCloseOutputStream(outputStream);
    }

    private void writeToFromStream(OutputStream os, RequestParams.StreamWrapper entry) throws IOException {
        int n2;
        this.writeMetaData(os, entry.name, entry.contentType);
        Base64OutputStream base64OutputStream = new Base64OutputStream(os, 18);
        while ((n2 = entry.inputStream.read(this.buffer)) != -1) {
            base64OutputStream.write(this.buffer, 0, n2);
        }
        AsyncHttpClient.silentCloseOutputStream(base64OutputStream);
        this.endMetaData(os);
        if (entry.autoClose) {
            AsyncHttpClient.silentCloseInputStream(entry.inputStream);
        }
    }

    private void writeToFromFile(OutputStream os, RequestParams.FileWrapper wrapper) throws IOException {
        int n2;
        this.writeMetaData(os, wrapper.file.getName(), wrapper.contentType);
        long l2 = 0L;
        long l3 = wrapper.file.length();
        FileInputStream fileInputStream = new FileInputStream(wrapper.file);
        Base64OutputStream base64OutputStream = new Base64OutputStream(os, 18);
        while ((n2 = fileInputStream.read(this.buffer)) != -1) {
            base64OutputStream.write(this.buffer, 0, n2);
            this.progressHandler.sendProgressMessage(l2 += (long)n2, l3);
        }
        AsyncHttpClient.silentCloseOutputStream(base64OutputStream);
        this.endMetaData(os);
        AsyncHttpClient.silentCloseInputStream(fileInputStream);
    }

    private void writeMetaData(OutputStream os, String name, String contentType) throws IOException {
        os.write(STREAM_NAME);
        os.write(58);
        os.write(JsonStreamerEntity.escape(name));
        os.write(44);
        os.write(STREAM_TYPE);
        os.write(58);
        os.write(JsonStreamerEntity.escape(contentType));
        os.write(44);
        os.write(STREAM_CONTENTS);
        os.write(58);
        os.write(34);
    }

    private void endMetaData(OutputStream os) throws IOException {
        os.write(34);
    }

    static byte[] escape(String string) {
        if (string == null) {
            return JSON_NULL;
        }
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append('\"');
        int n2 = string.length();
        int n3 = -1;
        block9 : while (++n3 < n2) {
            char c2 = string.charAt(n3);
            switch (c2) {
                case '\"': {
                    stringBuilder.append("\\\"");
                    continue block9;
                }
                case '\\': {
                    stringBuilder.append("\\\\");
                    continue block9;
                }
                case '\b': {
                    stringBuilder.append("\\b");
                    continue block9;
                }
                case '\f': {
                    stringBuilder.append("\\f");
                    continue block9;
                }
                case '\n': {
                    stringBuilder.append("\\n");
                    continue block9;
                }
                case '\r': {
                    stringBuilder.append("\\r");
                    continue block9;
                }
                case '\t': {
                    stringBuilder.append("\\t");
                    continue block9;
                }
            }
            if (c2 <= '\u001f' || c2 >= '' && c2 <= '\u009f' || c2 >= '\u2000' && c2 <= '\u20ff') {
                String string2 = Integer.toHexString(c2);
                stringBuilder.append("\\u");
                int n4 = 4 - string2.length();
                for (int i2 = 0; i2 < n4; ++i2) {
                    stringBuilder.append('0');
                }
                stringBuilder.append(string2.toUpperCase(Locale.US));
                continue;
            }
            stringBuilder.append(c2);
        }
        stringBuilder.append('\"');
        return stringBuilder.toString().getBytes();
    }
}

