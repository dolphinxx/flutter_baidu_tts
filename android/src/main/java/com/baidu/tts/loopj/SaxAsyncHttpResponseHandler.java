/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.http.Header
 *  org.apache.http.HttpEntity
 */
package com.baidu.tts.loopj;

import com.baidu.tts.loopj.AsyncHttpClient;
import com.baidu.tts.loopj.AsyncHttpResponseHandler;
import com.baidu.tts.loopj.LogInterface;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class SaxAsyncHttpResponseHandler<T extends DefaultHandler>
extends AsyncHttpResponseHandler {
    private T handler = null;
    private static final String LOG_TAG = "SaxAsyncHttpRH";

    public SaxAsyncHttpResponseHandler(T t) {
        if (t == null) {
            throw new Error("null instance of <T extends DefaultHandler> passed to constructor");
        }
        this.handler = t;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected byte[] getResponseData(HttpEntity entity) throws IOException {
        if (entity != null) {
            InputStream inputStream = entity.getContent();
            InputStreamReader inputStreamReader = null;
            if (inputStream != null) {
                try {
                    SAXParserFactory sAXParserFactory = SAXParserFactory.newInstance();
                    SAXParser sAXParser = sAXParserFactory.newSAXParser();
                    XMLReader xMLReader = sAXParser.getXMLReader();
                    xMLReader.setContentHandler((ContentHandler)this.handler);
                    inputStreamReader = new InputStreamReader(inputStream, this.getCharset());
                    xMLReader.parse(new InputSource(inputStreamReader));
                }
                catch (SAXException sAXException) {
                    AsyncHttpClient.log.e(LOG_TAG, "getResponseData exception", sAXException);
                }
                catch (ParserConfigurationException parserConfigurationException) {
                    AsyncHttpClient.log.e(LOG_TAG, "getResponseData exception", parserConfigurationException);
                }
                finally {
                    AsyncHttpClient.silentCloseInputStream(inputStream);
                    if (inputStreamReader != null) {
                        try {
                            inputStreamReader.close();
                        }
                        catch (IOException iOException) {}
                    }
                }
            }
        }
        return null;
    }

    public abstract void onSuccess(int var1, Header[] var2, T var3);

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        this.onSuccess(statusCode, headers, this.handler);
    }

    public abstract void onFailure(int var1, Header[] var2, T var3);

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        this.onFailure(statusCode, headers, this.handler);
    }
}

