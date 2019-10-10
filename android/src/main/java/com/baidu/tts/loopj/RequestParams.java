/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.http.HttpEntity
 *  org.apache.http.client.entity.UrlEncodedFormEntity
 *  org.apache.http.client.utils.URLEncodedUtils
 *  org.apache.http.message.BasicNameValuePair
 */
package com.baidu.tts.loopj;

import com.baidu.tts.loopj.AsyncHttpClient;
import com.baidu.tts.loopj.JsonStreamerEntity;
import com.baidu.tts.loopj.LogInterface;
import com.baidu.tts.loopj.ResponseHandlerInterface;
import com.baidu.tts.loopj.SimpleMultipartEntity;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class RequestParams
implements Serializable {
    public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
    public static final String APPLICATION_JSON = "application/json";
    protected static final String LOG_TAG = "RequestParams";
    protected boolean isRepeatable;
    protected boolean forceMultipartEntity = false;
    protected boolean useJsonStreamer;
    protected String elapsedFieldInJsonStreamer = "_elapsed";
    protected boolean autoCloseInputStreams;
    protected final ConcurrentHashMap<String, String> urlParams = new ConcurrentHashMap();
    protected final ConcurrentHashMap<String, StreamWrapper> streamParams = new ConcurrentHashMap();
    protected final ConcurrentHashMap<String, FileWrapper> fileParams = new ConcurrentHashMap();
    protected final ConcurrentHashMap<String, List<FileWrapper>> fileArrayParams = new ConcurrentHashMap();
    protected final ConcurrentHashMap<String, Object> urlParamsWithObjects = new ConcurrentHashMap();
    protected String contentEncoding = "UTF-8";

    public void setContentEncoding(String encoding) {
        if (encoding != null) {
            this.contentEncoding = encoding;
        } else {
            AsyncHttpClient.log.d(LOG_TAG, "setContentEncoding called with null attribute");
        }
    }

    public void setForceMultipartEntityContentType(boolean force) {
        this.forceMultipartEntity = force;
    }

    public RequestParams() {
        this((Map<String, String>)null);
    }

    public RequestParams(Map<String, String> source) {
        if (source != null) {
            for (Map.Entry<String, String> entry : source.entrySet()) {
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public RequestParams(String key, final String value) {
        this(createMap(key, value));
    }

    private static Map<String, String> createMap(String key, String value) {
        Map<String, String> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    public RequestParams(Object ... keysAndValues) {
        int n2 = keysAndValues.length;
        if (n2 % 2 != 0) {
            throw new IllegalArgumentException("Supplied arguments must be even");
        }
        for (int i2 = 0; i2 < n2; i2 += 2) {
            String string = String.valueOf(keysAndValues[i2]);
            String string2 = String.valueOf(keysAndValues[i2 + 1]);
            this.put(string, string2);
        }
    }

    public void put(String key, String value) {
        if (key != null && value != null) {
            this.urlParams.put(key, value);
        }
    }

    public void put(String key, File[] files) throws FileNotFoundException {
        this.put(key, files, null, null);
    }

    public void put(String key, File[] files, String contentType, String customFileName) throws FileNotFoundException {
        if (key != null) {
            ArrayList<FileWrapper> arrayList = new ArrayList<FileWrapper>();
            for (File file : files) {
                if (file == null || !file.exists()) {
                    throw new FileNotFoundException();
                }
                arrayList.add(new FileWrapper(file, contentType, customFileName));
            }
            this.fileArrayParams.put(key, arrayList);
        }
    }

    public void put(String key, File file) throws FileNotFoundException {
        this.put(key, file, null, null);
    }

    public void put(String key, String customFileName, File file) throws FileNotFoundException {
        this.put(key, file, null, customFileName);
    }

    public void put(String key, File file, String contentType) throws FileNotFoundException {
        this.put(key, file, contentType, null);
    }

    public void put(String key, File file, String contentType, String customFileName) throws FileNotFoundException {
        if (file == null || !file.exists()) {
            throw new FileNotFoundException();
        }
        if (key != null) {
            this.fileParams.put(key, new FileWrapper(file, contentType, customFileName));
        }
    }

    public void put(String key, InputStream stream) {
        this.put(key, stream, null);
    }

    public void put(String key, InputStream stream, String name) {
        this.put(key, stream, name, null);
    }

    public void put(String key, InputStream stream, String name, String contentType) {
        this.put(key, stream, name, contentType, this.autoCloseInputStreams);
    }

    public void put(String key, InputStream stream, String name, String contentType, boolean autoClose) {
        if (key != null && stream != null) {
            this.streamParams.put(key, StreamWrapper.newInstance(stream, name, contentType, autoClose));
        }
    }

    public void put(String key, Object value) {
        if (key != null && value != null) {
            this.urlParamsWithObjects.put(key, value);
        }
    }

    public void put(String key, int value) {
        if (key != null) {
            this.urlParams.put(key, String.valueOf(value));
        }
    }

    public void put(String key, long value) {
        if (key != null) {
            this.urlParams.put(key, String.valueOf(value));
        }
    }

    public void add(String key, String value) {
        if (key != null && value != null) {
            HashSet hashSet = (HashSet)this.urlParamsWithObjects.get(key);
            if (hashSet == null) {
                hashSet = new HashSet();
                this.put(key, hashSet);
            }
            if (hashSet instanceof List) {
                ((List)((Object)hashSet)).add(value);
            } else if (hashSet instanceof Set) {
                ((Set)hashSet).add(value);
            }
        }
    }

    public void remove(String key) {
        this.urlParams.remove(key);
        this.streamParams.remove(key);
        this.fileParams.remove(key);
        this.urlParamsWithObjects.remove(key);
        this.fileArrayParams.remove(key);
    }

    public boolean has(String key) {
        return this.urlParams.get(key) != null || this.streamParams.get(key) != null || this.fileParams.get(key) != null || this.urlParamsWithObjects.get(key) != null || this.fileArrayParams.get(key) != null;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> object2 : this.urlParams.entrySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            stringBuilder.append(object2.getKey());
            stringBuilder.append("=");
            stringBuilder.append(object2.getValue());
        }
        for (Map.Entry<String, StreamWrapper> entry : this.streamParams.entrySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            stringBuilder.append(entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append("STREAM");
        }
        for (Map.Entry<String, FileWrapper> entry : this.fileParams.entrySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            stringBuilder.append(entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append("FILE");
        }
        for (Map.Entry<String, List<FileWrapper>> entry : this.fileArrayParams.entrySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            stringBuilder.append(entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append("FILES(SIZE=").append(((List)entry.getValue()).size()).append(")");
        }
        List<BasicNameValuePair> list = this.getParamsList(null, this.urlParamsWithObjects);
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            BasicNameValuePair basicNameValuePair = (BasicNameValuePair)iterator.next();
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            stringBuilder.append(basicNameValuePair.getName());
            stringBuilder.append("=");
            stringBuilder.append(basicNameValuePair.getValue());
        }
        return stringBuilder.toString();
    }

    public void setHttpEntityIsRepeatable(boolean flag) {
        this.isRepeatable = flag;
    }

    public void setUseJsonStreamer(boolean flag) {
        this.useJsonStreamer = flag;
    }

    public void setElapsedFieldInJsonStreamer(String value) {
        this.elapsedFieldInJsonStreamer = value;
    }

    public void setAutoCloseInputStreams(boolean flag) {
        this.autoCloseInputStreams = flag;
    }

    public HttpEntity getEntity(ResponseHandlerInterface progressHandler) throws IOException {
        if (this.useJsonStreamer) {
            return this.createJsonStreamerEntity(progressHandler);
        }
        if (!this.forceMultipartEntity && this.streamParams.isEmpty() && this.fileParams.isEmpty() && this.fileArrayParams.isEmpty()) {
            return this.createFormEntity();
        }
        return this.createMultipartEntity(progressHandler);
    }

    private HttpEntity createJsonStreamerEntity(ResponseHandlerInterface progressHandler) throws IOException {
        JsonStreamerEntity jsonStreamerEntity = new JsonStreamerEntity(progressHandler, !this.fileParams.isEmpty() || !this.streamParams.isEmpty(), this.elapsedFieldInJsonStreamer);
        for (Map.Entry<String, String> entry : this.urlParams.entrySet()) {
            jsonStreamerEntity.addPart(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Object> entry : this.urlParamsWithObjects.entrySet()) {
            jsonStreamerEntity.addPart(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, FileWrapper> entry : this.fileParams.entrySet()) {
            jsonStreamerEntity.addPart(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, StreamWrapper> entry : this.streamParams.entrySet()) {
            StreamWrapper streamWrapper = (StreamWrapper)entry.getValue();
            if (streamWrapper.inputStream == null) continue;
            jsonStreamerEntity.addPart(entry.getKey(), StreamWrapper.newInstance(streamWrapper.inputStream, streamWrapper.name, streamWrapper.contentType, streamWrapper.autoClose));
        }
        return jsonStreamerEntity;
    }

    private HttpEntity createFormEntity() {
        try {
            return new UrlEncodedFormEntity(this.getParamsList(), this.contentEncoding);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            AsyncHttpClient.log.e(LOG_TAG, "createFormEntity failed", unsupportedEncodingException);
            return null;
        }
    }

    private HttpEntity createMultipartEntity(ResponseHandlerInterface progressHandler) throws IOException {
        SimpleMultipartEntity simpleMultipartEntity = new SimpleMultipartEntity(progressHandler);
        simpleMultipartEntity.setIsRepeatable(this.isRepeatable);
        for (Map.Entry<String, String> iterator2 : this.urlParams.entrySet()) {
            simpleMultipartEntity.addPartWithCharset(iterator2.getKey(), iterator2.getValue(), this.contentEncoding);
        }
        List<BasicNameValuePair> list2 = this.getParamsList(null, this.urlParamsWithObjects);
        Iterator iterator = list2.iterator();
        while (iterator.hasNext()) {
            BasicNameValuePair basicNameValuePair = (BasicNameValuePair)iterator.next();
            simpleMultipartEntity.addPartWithCharset(basicNameValuePair.getName(), basicNameValuePair.getValue(), this.contentEncoding);
        }
        for (Map.Entry<String, StreamWrapper> entry : this.streamParams.entrySet()) {
            StreamWrapper v = entry.getValue();
            if (((StreamWrapper)v).inputStream == null) continue;
            simpleMultipartEntity.addPart(entry.getKey(), ((StreamWrapper)v).name, ((StreamWrapper)v).inputStream, ((StreamWrapper)v).contentType);
        }
        for (Map.Entry<String, FileWrapper> entry : this.fileParams.entrySet()) {
            FileWrapper v = entry.getValue();
            simpleMultipartEntity.addPart(entry.getKey(), ((FileWrapper)v).file, ((FileWrapper)v).contentType, ((FileWrapper)v).customFileName);
        }
        for (Map.Entry<String, List<FileWrapper>> entry : this.fileArrayParams.entrySet()) {
            List<FileWrapper> list = entry.getValue();
            for (FileWrapper fileWrapper : list) {
                simpleMultipartEntity.addPart(entry.getKey(), fileWrapper.file, fileWrapper.contentType, fileWrapper.customFileName);
            }
        }
        return simpleMultipartEntity;
    }

    protected List<BasicNameValuePair> getParamsList() {
        LinkedList<BasicNameValuePair> linkedList = new LinkedList<BasicNameValuePair>();
        for (Map.Entry<String, String> entry : this.urlParams.entrySet()) {
            linkedList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        linkedList.addAll(this.getParamsList(null, this.urlParamsWithObjects));
        return linkedList;
    }

    private List<BasicNameValuePair> getParamsList(String key, Object value) {
        LinkedList<BasicNameValuePair> linkedList = new LinkedList<BasicNameValuePair>();
        if (value instanceof Map) {
            Map map = (Map)value;
            ArrayList arrayList = new ArrayList(map.keySet());
            if (arrayList.size() > 0 && arrayList.get(0) instanceof Comparable) {
                Collections.sort(arrayList);
            }
            for (Object e2 : arrayList) {
                Object v;
                if (!(e2 instanceof String) || (v = map.get(e2)) == null) continue;
                linkedList.addAll(this.getParamsList(key == null ? (String)e2 : String.format(Locale.US, "%s[%s]", key, e2), v));
            }
        } else if (value instanceof List) {
            List list = (List)value;
            int n2 = list.size();
            for (int i2 = 0; i2 < n2; ++i2) {
                linkedList.addAll(this.getParamsList(String.format(Locale.US, "%s[%d]", key, i2), list.get(i2)));
            }
        } else if (value instanceof Object[]) {
            Object[] arrobject = (Object[])value;
            int n3 = arrobject.length;
            for (int i3 = 0; i3 < n3; ++i3) {
                linkedList.addAll(this.getParamsList(String.format(Locale.US, "%s[%d]", key, i3), arrobject[i3]));
            }
        } else if (value instanceof Set) {
            Set set = (Set)value;
            for (Object e3 : set) {
                linkedList.addAll(this.getParamsList(key, e3));
            }
        } else {
            linkedList.add(new BasicNameValuePair(key, value.toString()));
        }
        return linkedList;
    }

    protected String getParamString() {
        return URLEncodedUtils.format(this.getParamsList(), (String)this.contentEncoding);
    }

    public static class StreamWrapper {
        public final InputStream inputStream;
        public final String name;
        public final String contentType;
        public final boolean autoClose;

        public StreamWrapper(InputStream inputStream, String name, String contentType, boolean autoClose) {
            this.inputStream = inputStream;
            this.name = name;
            this.contentType = contentType;
            this.autoClose = autoClose;
        }

        static StreamWrapper newInstance(InputStream inputStream, String name, String contentType, boolean autoClose) {
            return new StreamWrapper(inputStream, name, contentType == null ? RequestParams.APPLICATION_OCTET_STREAM : contentType, autoClose);
        }
    }

    public static class FileWrapper
    implements Serializable {
        public final File file;
        public final String contentType;
        public final String customFileName;

        public FileWrapper(File file, String contentType, String customFileName) {
            this.file = file;
            this.contentType = contentType;
            this.customFileName = customFileName;
        }
    }

}

