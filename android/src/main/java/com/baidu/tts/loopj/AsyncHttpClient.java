/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Looper
 *  org.apache.http.Header
 *  org.apache.http.HeaderElement
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpException
 *  org.apache.http.HttpHost
 *  org.apache.http.HttpRequest
 *  org.apache.http.HttpRequestInterceptor
 *  org.apache.http.HttpResponse
 *  org.apache.http.HttpResponseInterceptor
 *  org.apache.http.HttpVersion
 *  org.apache.http.ProtocolVersion
 *  org.apache.http.auth.AuthScheme
 *  org.apache.http.auth.AuthScope
 *  org.apache.http.auth.AuthState
 *  org.apache.http.auth.Credentials
 *  org.apache.http.auth.UsernamePasswordCredentials
 *  org.apache.http.client.CookieStore
 *  org.apache.http.client.CredentialsProvider
 *  org.apache.http.client.HttpClient
 *  org.apache.http.client.HttpRequestRetryHandler
 *  org.apache.http.client.RedirectHandler
 *  org.apache.http.client.methods.HttpEntityEnclosingRequestBase
 *  org.apache.http.client.methods.HttpHead
 *  org.apache.http.client.methods.HttpPost
 *  org.apache.http.client.methods.HttpPut
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.conn.ClientConnectionManager
 *  org.apache.http.conn.params.ConnManagerParams
 *  org.apache.http.conn.params.ConnPerRoute
 *  org.apache.http.conn.params.ConnPerRouteBean
 *  org.apache.http.conn.scheme.PlainSocketFactory
 *  org.apache.http.conn.scheme.Scheme
 *  org.apache.http.conn.scheme.SchemeRegistry
 *  org.apache.http.conn.scheme.SocketFactory
 *  org.apache.http.conn.ssl.SSLSocketFactory
 *  org.apache.http.entity.HttpEntityWrapper
 *  org.apache.http.impl.auth.BasicScheme
 *  org.apache.http.impl.client.AbstractHttpClient
 *  org.apache.http.impl.client.DefaultHttpClient
 *  org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager
 *  org.apache.http.params.BasicHttpParams
 *  org.apache.http.params.HttpConnectionParams
 *  org.apache.http.params.HttpParams
 *  org.apache.http.params.HttpProtocolParams
 *  org.apache.http.protocol.BasicHttpContext
 *  org.apache.http.protocol.HttpContext
 *  org.apache.http.protocol.SyncBasicHttpContext
 */
package com.baidu.tts.loopj;

import android.content.Context;
import android.os.Looper;
import com.baidu.tts.loopj.AsyncHttpRequest;
import com.baidu.tts.loopj.AsyncHttpResponseHandler;
import com.baidu.tts.loopj.HttpDelete;
import com.baidu.tts.loopj.HttpGet;
import com.baidu.tts.loopj.HttpPatch;
import com.baidu.tts.loopj.LogHandler;
import com.baidu.tts.loopj.LogInterface;
import com.baidu.tts.loopj.MyRedirectHandler;
import com.baidu.tts.loopj.MySSLSocketFactory;
import com.baidu.tts.loopj.PreemptiveAuthorizationHttpRequestInterceptor;
import com.baidu.tts.loopj.RequestHandle;
import com.baidu.tts.loopj.RequestParams;
import com.baidu.tts.loopj.ResponseHandlerInterface;
import com.baidu.tts.loopj.RetryHandler;
import com.baidu.tts.loopj.Utils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class AsyncHttpClient {
    public static final String LOG_TAG = "AsyncHttpClient";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_CONTENT_RANGE = "Content-Range";
    public static final String HEADER_CONTENT_ENCODING = "Content-Encoding";
    public static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";
    public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    public static final String ENCODING_GZIP = "gzip";
    public static final int DEFAULT_MAX_CONNECTIONS = 10;
    public static final int DEFAULT_SOCKET_TIMEOUT = 10000;
    public static final int DEFAULT_MAX_RETRIES = 5;
    public static final int DEFAULT_RETRY_SLEEP_TIME_MILLIS = 1500;
    public static final int DEFAULT_SOCKET_BUFFER_SIZE = 8192;
    private int maxConnections = 10;
    private int connectTimeout = 10000;
    private int responseTimeout = 10000;
    private final DefaultHttpClient httpClient;
    private final HttpContext httpContext;
    private ExecutorService threadPool;
    private final Map<Context, List<RequestHandle>> requestMap;
    private final Map<String, String> clientHeaderMap;
    private boolean isUrlEncodingEnabled = true;
    public static LogInterface log = new LogHandler();

    public AsyncHttpClient() {
        this(false, 80, 443);
    }

    public AsyncHttpClient(int httpPort) {
        this(false, httpPort, 443);
    }

    public AsyncHttpClient(int httpPort, int httpsPort) {
        this(false, httpPort, httpsPort);
    }

    public AsyncHttpClient(boolean fixNoHttpResponseException, int httpPort, int httpsPort) {
        this(AsyncHttpClient.getDefaultSchemeRegistry(fixNoHttpResponseException, httpPort, httpsPort));
    }

    private static SchemeRegistry getDefaultSchemeRegistry(boolean fixNoHttpResponseException, int httpPort, int httpsPort) {
        if (fixNoHttpResponseException) {
            // empty if block
        }
        if (httpPort < 1) {
            httpPort = 80;
            log.d(LOG_TAG, "Invalid HTTP port number specified, defaulting to 80");
        }
        if (httpsPort < 1) {
            httpsPort = 443;
            log.d(LOG_TAG, "Invalid HTTPS port number specified, defaulting to 443");
        }
        SSLSocketFactory sSLSocketFactory = fixNoHttpResponseException ? MySSLSocketFactory.getFixedSocketFactory() : SSLSocketFactory.getSocketFactory();
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", (SocketFactory)PlainSocketFactory.getSocketFactory(), httpPort));
        schemeRegistry.register(new Scheme("https", (SocketFactory)sSLSocketFactory, httpsPort));
        return schemeRegistry;
    }

    public AsyncHttpClient(SchemeRegistry schemeRegistry) {
        BasicHttpParams basicHttpParams = new BasicHttpParams();
        ConnManagerParams.setTimeout((HttpParams)basicHttpParams, (long)this.connectTimeout);
        ConnManagerParams.setMaxConnectionsPerRoute((HttpParams)basicHttpParams, (ConnPerRoute)new ConnPerRouteBean(this.maxConnections));
        ConnManagerParams.setMaxTotalConnections((HttpParams)basicHttpParams, (int)10);
        HttpConnectionParams.setSoTimeout((HttpParams)basicHttpParams, (int)this.responseTimeout);
        HttpConnectionParams.setConnectionTimeout((HttpParams)basicHttpParams, (int)this.connectTimeout);
        HttpConnectionParams.setTcpNoDelay((HttpParams)basicHttpParams, (boolean)true);
        HttpConnectionParams.setSocketBufferSize((HttpParams)basicHttpParams, (int)8192);
        HttpProtocolParams.setVersion((HttpParams)basicHttpParams, (ProtocolVersion)HttpVersion.HTTP_1_1);
        ClientConnectionManager clientConnectionManager = this.createConnectionManager(schemeRegistry, basicHttpParams);
        Utils.asserts(clientConnectionManager != null, "Custom implementation of #createConnectionManager(SchemeRegistry, BasicHttpParams) returned null");
        this.threadPool = this.getDefaultThreadPool();
        this.requestMap = Collections.synchronizedMap(new WeakHashMap());
        this.clientHeaderMap = new HashMap<String, String>();
        this.httpContext = new SyncBasicHttpContext((HttpContext)new BasicHttpContext());
        this.httpClient = new DefaultHttpClient(clientConnectionManager, (HttpParams)basicHttpParams);
        this.httpClient.addRequestInterceptor(new HttpRequestInterceptor(){

            public void process(HttpRequest request, HttpContext context) {
                if (!request.containsHeader(AsyncHttpClient.HEADER_ACCEPT_ENCODING)) {
                    request.addHeader(AsyncHttpClient.HEADER_ACCEPT_ENCODING, AsyncHttpClient.ENCODING_GZIP);
                }
                for (String string : AsyncHttpClient.this.clientHeaderMap.keySet()) {
                    if (request.containsHeader(string)) {
                        Header header = request.getFirstHeader(string);
                        log.d(AsyncHttpClient.LOG_TAG, String.format("Headers were overwritten! (%s | %s) overwrites (%s | %s)", string, AsyncHttpClient.this.clientHeaderMap.get(string), header.getName(), header.getValue()));
                        request.removeHeader(header);
                    }
                    request.addHeader(string, (String)AsyncHttpClient.this.clientHeaderMap.get(string));
                }
            }
        });
        this.httpClient.addResponseInterceptor(new HttpResponseInterceptor(){

            public void process(HttpResponse response, HttpContext context) {
                HttpEntity httpEntity = response.getEntity();
                if (httpEntity == null) {
                    return;
                }
                Header header = httpEntity.getContentEncoding();
                if (header != null) {
                    for (HeaderElement headerElement : header.getElements()) {
                        if (!headerElement.getName().equalsIgnoreCase(AsyncHttpClient.ENCODING_GZIP)) continue;
                        response.setEntity((HttpEntity)new InflatingEntity(httpEntity));
                        break;
                    }
                }
            }
        });
        this.httpClient.addRequestInterceptor(new HttpRequestInterceptor(){

            public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
                AuthScope authScope;
                Credentials credentials;
                AuthState authState = (AuthState)context.getAttribute("http.auth.target-scope");
                CredentialsProvider credentialsProvider = (CredentialsProvider)context.getAttribute("http.auth.credentials-provider");
                HttpHost httpHost = (HttpHost)context.getAttribute("http.target_host");
                if (authState.getAuthScheme() == null && (credentials = credentialsProvider.getCredentials(authScope = new AuthScope(httpHost.getHostName(), httpHost.getPort()))) != null) {
                    authState.setAuthScheme((AuthScheme)new BasicScheme());
                    authState.setCredentials(credentials);
                }
            }
        }, 0);
        this.httpClient.setHttpRequestRetryHandler((HttpRequestRetryHandler)new RetryHandler(5, 1500));
    }

    public static void allowRetryExceptionClass(Class<?> cls) {
        if (cls != null) {
            RetryHandler.addClassToWhitelist(cls);
        }
    }

    public static void blockRetryExceptionClass(Class<?> cls) {
        if (cls != null) {
            RetryHandler.addClassToBlacklist(cls);
        }
    }

    public HttpClient getHttpClient() {
        return this.httpClient;
    }

    public HttpContext getHttpContext() {
        return this.httpContext;
    }

    public void setLoggingEnabled(boolean loggingEnabled) {
        log.setLoggingEnabled(loggingEnabled);
    }

    public boolean isLoggingEnabled() {
        return log.isLoggingEnabled();
    }

    public void setLoggingLevel(int logLevel) {
        log.setLoggingLevel(logLevel);
    }

    public int getLoggingLevel() {
        return log.getLoggingLevel();
    }

    public LogInterface getLogInterface() {
        return log;
    }

    public void setLogInterface(LogInterface logInterfaceInstance) {
        if (logInterfaceInstance != null) {
            log = logInterfaceInstance;
        }
    }

    public void setCookieStore(CookieStore cookieStore) {
        this.httpContext.setAttribute("http.cookie-store", (Object)cookieStore);
    }

    public void setThreadPool(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }

    public ExecutorService getThreadPool() {
        return this.threadPool;
    }

    protected ExecutorService getDefaultThreadPool() {
        return Executors.newCachedThreadPool();
    }

    protected ClientConnectionManager createConnectionManager(SchemeRegistry schemeRegistry, BasicHttpParams httpParams) {
        return new ThreadSafeClientConnManager((HttpParams)httpParams, schemeRegistry);
    }

    public void setEnableRedirects(boolean enableRedirects, boolean enableRelativeRedirects, boolean enableCircularRedirects) {
        this.httpClient.getParams().setBooleanParameter("http.protocol.reject-relative-redirect", !enableRelativeRedirects);
        this.httpClient.getParams().setBooleanParameter("http.protocol.allow-circular-redirects", enableCircularRedirects);
        this.httpClient.setRedirectHandler((RedirectHandler)new MyRedirectHandler(enableRedirects));
    }

    public void setEnableRedirects(boolean enableRedirects, boolean enableRelativeRedirects) {
        this.setEnableRedirects(enableRedirects, enableRelativeRedirects, true);
    }

    public void setEnableRedirects(boolean enableRedirects) {
        this.setEnableRedirects(enableRedirects, enableRedirects, enableRedirects);
    }

    public void setRedirectHandler(RedirectHandler customRedirectHandler) {
        this.httpClient.setRedirectHandler(customRedirectHandler);
    }

    public void setUserAgent(String userAgent) {
        HttpProtocolParams.setUserAgent((HttpParams)this.httpClient.getParams(), (String)userAgent);
    }

    public int getMaxConnections() {
        return this.maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        if (maxConnections < 1) {
            maxConnections = 10;
        }
        this.maxConnections = maxConnections;
        HttpParams httpParams = this.httpClient.getParams();
        ConnManagerParams.setMaxConnectionsPerRoute((HttpParams)httpParams, (ConnPerRoute)new ConnPerRouteBean(this.maxConnections));
    }

    public void setTimeout(int value) {
        value = value < 1000 ? 10000 : value;
        this.setConnectTimeout(value);
        this.setResponseTimeout(value);
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public void setConnectTimeout(int value) {
        this.connectTimeout = value < 1000 ? 10000 : value;
        HttpParams httpParams = this.httpClient.getParams();
        ConnManagerParams.setTimeout((HttpParams)httpParams, (long)this.connectTimeout);
        HttpConnectionParams.setConnectionTimeout((HttpParams)httpParams, (int)this.connectTimeout);
    }

    public int getResponseTimeout() {
        return this.responseTimeout;
    }

    public void setResponseTimeout(int value) {
        this.responseTimeout = value < 1000 ? 10000 : value;
        HttpParams httpParams = this.httpClient.getParams();
        HttpConnectionParams.setSoTimeout((HttpParams)httpParams, (int)this.responseTimeout);
    }

    public void setProxy(String hostname, int port) {
        HttpHost httpHost = new HttpHost(hostname, port);
        HttpParams httpParams = this.httpClient.getParams();
        httpParams.setParameter("http.route.default-proxy", (Object)httpHost);
    }

    public void setProxy(String hostname, int port, String username, String password) {
        this.httpClient.getCredentialsProvider().setCredentials(new AuthScope(hostname, port), (Credentials)new UsernamePasswordCredentials(username, password));
        HttpHost httpHost = new HttpHost(hostname, port);
        HttpParams httpParams = this.httpClient.getParams();
        httpParams.setParameter("http.route.default-proxy", (Object)httpHost);
    }

    public void setSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", (SocketFactory)sslSocketFactory, 443));
    }

    public void setMaxRetriesAndTimeout(int retries, int timeout) {
        this.httpClient.setHttpRequestRetryHandler((HttpRequestRetryHandler)new RetryHandler(retries, timeout));
    }

    public void removeAllHeaders() {
        this.clientHeaderMap.clear();
    }

    public void addHeader(String header, String value) {
        this.clientHeaderMap.put(header, value);
    }

    public void removeHeader(String header) {
        this.clientHeaderMap.remove(header);
    }

    public void setBasicAuth(String username, String password) {
        this.setBasicAuth(username, password, false);
    }

    public void setBasicAuth(String username, String password, boolean preemptive) {
        this.setBasicAuth(username, password, null, preemptive);
    }

    public void setBasicAuth(String username, String password, AuthScope scope) {
        this.setBasicAuth(username, password, scope, false);
    }

    public void setBasicAuth(String username, String password, AuthScope scope, boolean preemptive) {
        UsernamePasswordCredentials usernamePasswordCredentials = new UsernamePasswordCredentials(username, password);
        this.setCredentials(scope, (Credentials)usernamePasswordCredentials);
        this.setAuthenticationPreemptive(preemptive);
    }

    public void setCredentials(AuthScope authScope, Credentials credentials) {
        if (credentials == null) {
            log.d(LOG_TAG, "Provided credentials are null, not setting");
            return;
        }
        this.httpClient.getCredentialsProvider().setCredentials(authScope == null ? AuthScope.ANY : authScope, credentials);
    }

    public void setAuthenticationPreemptive(boolean isPreemptive) {
        if (isPreemptive) {
            this.httpClient.addRequestInterceptor((HttpRequestInterceptor)new PreemptiveAuthorizationHttpRequestInterceptor(), 0);
        } else {
            this.httpClient.removeRequestInterceptorByClass(PreemptiveAuthorizationHttpRequestInterceptor.class);
        }
    }

    public void clearCredentialsProvider() {
        this.httpClient.getCredentialsProvider().clear();
    }

    public void cancelRequests(Context context, final boolean mayInterruptIfRunning) {
        if (context == null) {
            log.e(LOG_TAG, "Passed null Context to cancelRequests");
            return;
        }
        final List<RequestHandle> list = this.requestMap.get((Object)context);
        this.requestMap.remove((Object)context);
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Runnable runnable = new Runnable(){

                public void run() {
                    AsyncHttpClient.this.cancelRequests(list, mayInterruptIfRunning);
                }
            };
            this.threadPool.submit(runnable);
        } else {
            this.cancelRequests(list, mayInterruptIfRunning);
        }
    }

    private void cancelRequests(List<RequestHandle> requestList, boolean mayInterruptIfRunning) {
        if (requestList != null) {
            for (RequestHandle requestHandle : requestList) {
                requestHandle.cancel(mayInterruptIfRunning);
            }
        }
    }

    public void cancelAllRequests(boolean mayInterruptIfRunning) {
        for (List<RequestHandle> list : this.requestMap.values()) {
            if (list == null) continue;
            for (RequestHandle requestHandle : list) {
                requestHandle.cancel(mayInterruptIfRunning);
            }
        }
        this.requestMap.clear();
    }

    public void cancelRequestsByTAG(Object TAG, boolean mayInterruptIfRunning) {
        if (TAG == null) {
            log.d(LOG_TAG, "cancelRequestsByTAG, passed TAG is null, cannot proceed");
            return;
        }
        for (List<RequestHandle> list : this.requestMap.values()) {
            if (list == null) continue;
            for (RequestHandle requestHandle : list) {
                if (!TAG.equals(requestHandle.getTag())) continue;
                requestHandle.cancel(mayInterruptIfRunning);
            }
        }
    }

    public RequestHandle head(String url, ResponseHandlerInterface responseHandler) {
        return this.head(null, url, null, responseHandler);
    }

    public RequestHandle head(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        return this.head(null, url, params, responseHandler);
    }

    public RequestHandle head(Context context, String url, ResponseHandlerInterface responseHandler) {
        return this.head(context, url, null, responseHandler);
    }

    public RequestHandle head(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        return this.sendRequest(this.httpClient, this.httpContext, (HttpUriRequest)new HttpHead(AsyncHttpClient.getUrlWithQueryString(this.isUrlEncodingEnabled, url, params)), null, responseHandler, context);
    }

    public RequestHandle head(Context context, String url, Header[] headers, RequestParams params, ResponseHandlerInterface responseHandler) {
        HttpHead httpHead = new HttpHead(AsyncHttpClient.getUrlWithQueryString(this.isUrlEncodingEnabled, url, params));
        if (headers != null) {
            httpHead.setHeaders(headers);
        }
        return this.sendRequest(this.httpClient, this.httpContext, (HttpUriRequest)httpHead, null, responseHandler, context);
    }

    public RequestHandle get(String url, ResponseHandlerInterface responseHandler) {
        return this.get(null, url, null, responseHandler);
    }

    public RequestHandle get(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        return this.get(null, url, params, responseHandler);
    }

    public RequestHandle get(Context context, String url, ResponseHandlerInterface responseHandler) {
        return this.get(context, url, null, responseHandler);
    }

    public RequestHandle get(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        return this.sendRequest(this.httpClient, this.httpContext, (HttpUriRequest)new HttpGet(AsyncHttpClient.getUrlWithQueryString(this.isUrlEncodingEnabled, url, params)), null, responseHandler, context);
    }

    public RequestHandle get(Context context, String url, Header[] headers, RequestParams params, ResponseHandlerInterface responseHandler) {
        HttpGet httpGet = new HttpGet(AsyncHttpClient.getUrlWithQueryString(this.isUrlEncodingEnabled, url, params));
        if (headers != null) {
            httpGet.setHeaders(headers);
        }
        return this.sendRequest(this.httpClient, this.httpContext, (HttpUriRequest)httpGet, null, responseHandler, context);
    }

    public RequestHandle get(Context context, String url, HttpEntity entity, String contentType, ResponseHandlerInterface responseHandler) {
        return this.sendRequest(this.httpClient, this.httpContext, (HttpUriRequest)this.addEntityToRequestBase(new HttpGet(URI.create(url).normalize()), entity), contentType, responseHandler, context);
    }

    public RequestHandle post(String url, ResponseHandlerInterface responseHandler) {
        return this.post(null, url, null, responseHandler);
    }

    public RequestHandle post(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        return this.post(null, url, params, responseHandler);
    }

    public RequestHandle post(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        return this.post(context, url, this.paramsToEntity(params, responseHandler), null, responseHandler);
    }

    public RequestHandle post(Context context, String url, HttpEntity entity, String contentType, ResponseHandlerInterface responseHandler) {
        return this.sendRequest(this.httpClient, this.httpContext, (HttpUriRequest)this.addEntityToRequestBase((HttpEntityEnclosingRequestBase)new HttpPost(this.getURI(url)), entity), contentType, responseHandler, context);
    }

    public RequestHandle post(Context context, String url, Header[] headers, RequestParams params, String contentType, ResponseHandlerInterface responseHandler) {
        HttpPost httpPost = new HttpPost(this.getURI(url));
        if (params != null) {
            httpPost.setEntity(this.paramsToEntity(params, responseHandler));
        }
        if (headers != null) {
            httpPost.setHeaders(headers);
        }
        return this.sendRequest(this.httpClient, this.httpContext, (HttpUriRequest)httpPost, contentType, responseHandler, context);
    }

    public RequestHandle post(Context context, String url, Header[] headers, HttpEntity entity, String contentType, ResponseHandlerInterface responseHandler) {
        HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase = this.addEntityToRequestBase((HttpEntityEnclosingRequestBase)new HttpPost(this.getURI(url)), entity);
        if (headers != null) {
            httpEntityEnclosingRequestBase.setHeaders(headers);
        }
        return this.sendRequest(this.httpClient, this.httpContext, (HttpUriRequest)httpEntityEnclosingRequestBase, contentType, responseHandler, context);
    }

    public RequestHandle put(String url, ResponseHandlerInterface responseHandler) {
        return this.put(null, url, null, responseHandler);
    }

    public RequestHandle put(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        return this.put(null, url, params, responseHandler);
    }

    public RequestHandle put(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        return this.put(context, url, this.paramsToEntity(params, responseHandler), null, responseHandler);
    }

    public RequestHandle put(Context context, String url, HttpEntity entity, String contentType, ResponseHandlerInterface responseHandler) {
        return this.sendRequest(this.httpClient, this.httpContext, (HttpUriRequest)this.addEntityToRequestBase((HttpEntityEnclosingRequestBase)new HttpPut(this.getURI(url)), entity), contentType, responseHandler, context);
    }

    public RequestHandle put(Context context, String url, Header[] headers, HttpEntity entity, String contentType, ResponseHandlerInterface responseHandler) {
        HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase = this.addEntityToRequestBase((HttpEntityEnclosingRequestBase)new HttpPut(this.getURI(url)), entity);
        if (headers != null) {
            httpEntityEnclosingRequestBase.setHeaders(headers);
        }
        return this.sendRequest(this.httpClient, this.httpContext, (HttpUriRequest)httpEntityEnclosingRequestBase, contentType, responseHandler, context);
    }

    public RequestHandle patch(String url, ResponseHandlerInterface responseHandler) {
        return this.patch(null, url, null, responseHandler);
    }

    public RequestHandle patch(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        return this.patch(null, url, params, responseHandler);
    }

    public RequestHandle patch(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        return this.patch(context, url, this.paramsToEntity(params, responseHandler), null, responseHandler);
    }

    public RequestHandle patch(Context context, String url, HttpEntity entity, String contentType, ResponseHandlerInterface responseHandler) {
        return this.sendRequest(this.httpClient, this.httpContext, (HttpUriRequest)this.addEntityToRequestBase(new HttpPatch(this.getURI(url)), entity), contentType, responseHandler, context);
    }

    public RequestHandle patch(Context context, String url, Header[] headers, HttpEntity entity, String contentType, ResponseHandlerInterface responseHandler) {
        HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase = this.addEntityToRequestBase(new HttpPatch(this.getURI(url)), entity);
        if (headers != null) {
            httpEntityEnclosingRequestBase.setHeaders(headers);
        }
        return this.sendRequest(this.httpClient, this.httpContext, (HttpUriRequest)httpEntityEnclosingRequestBase, contentType, responseHandler, context);
    }

    public RequestHandle delete(String url, ResponseHandlerInterface responseHandler) {
        return this.delete(null, url, responseHandler);
    }

    public RequestHandle delete(Context context, String url, ResponseHandlerInterface responseHandler) {
        HttpDelete httpDelete = new HttpDelete(this.getURI(url));
        return this.sendRequest(this.httpClient, this.httpContext, (HttpUriRequest)httpDelete, null, responseHandler, context);
    }

    public RequestHandle delete(Context context, String url, Header[] headers, ResponseHandlerInterface responseHandler) {
        HttpDelete httpDelete = new HttpDelete(this.getURI(url));
        if (headers != null) {
            httpDelete.setHeaders(headers);
        }
        return this.sendRequest(this.httpClient, this.httpContext, (HttpUriRequest)httpDelete, null, responseHandler, context);
    }

    public void delete(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        HttpDelete httpDelete = new HttpDelete(AsyncHttpClient.getUrlWithQueryString(this.isUrlEncodingEnabled, url, params));
        this.sendRequest(this.httpClient, this.httpContext, (HttpUriRequest)httpDelete, null, responseHandler, null);
    }

    public RequestHandle delete(Context context, String url, Header[] headers, RequestParams params, ResponseHandlerInterface responseHandler) {
        HttpDelete httpDelete = new HttpDelete(AsyncHttpClient.getUrlWithQueryString(this.isUrlEncodingEnabled, url, params));
        if (headers != null) {
            httpDelete.setHeaders(headers);
        }
        return this.sendRequest(this.httpClient, this.httpContext, (HttpUriRequest)httpDelete, null, responseHandler, context);
    }

    public RequestHandle delete(Context context, String url, HttpEntity entity, String contentType, ResponseHandlerInterface responseHandler) {
        return this.sendRequest(this.httpClient, this.httpContext, (HttpUriRequest)this.addEntityToRequestBase(new HttpDelete(URI.create(url).normalize()), entity), contentType, responseHandler, context);
    }

    protected AsyncHttpRequest newAsyncHttpRequest(DefaultHttpClient client, HttpContext httpContext, HttpUriRequest uriRequest, String contentType, ResponseHandlerInterface responseHandler, Context context) {
        return new AsyncHttpRequest((AbstractHttpClient)client, httpContext, uriRequest, responseHandler);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected RequestHandle sendRequest(DefaultHttpClient client, HttpContext httpContext, HttpUriRequest uriRequest, String contentType, ResponseHandlerInterface responseHandler, Context context) {
        if (uriRequest == null) {
            throw new IllegalArgumentException("HttpUriRequest must not be null");
        }
        if (responseHandler == null) {
            throw new IllegalArgumentException("ResponseHandler must not be null");
        }
        if (responseHandler.getUseSynchronousMode() && !responseHandler.getUsePoolThread()) {
            throw new IllegalArgumentException("Synchronous ResponseHandler used in AsyncHttpClient. You should create your response handler in a looper thread or use SyncHttpClient instead.");
        }
        if (contentType != null) {
            if (uriRequest instanceof HttpEntityEnclosingRequestBase && ((HttpEntityEnclosingRequestBase)uriRequest).getEntity() != null && uriRequest.containsHeader(HEADER_CONTENT_TYPE)) {
                log.w(LOG_TAG, "Passed contentType will be ignored because HttpEntity sets content type");
            } else {
                uriRequest.setHeader(HEADER_CONTENT_TYPE, contentType);
            }
        }
        responseHandler.setRequestHeaders(uriRequest.getAllHeaders());
        responseHandler.setRequestURI(uriRequest.getURI());
        AsyncHttpRequest asyncHttpRequest = this.newAsyncHttpRequest(client, httpContext, uriRequest, contentType, responseHandler, context);
        this.threadPool.submit(asyncHttpRequest);
        RequestHandle requestHandle = new RequestHandle(asyncHttpRequest);
        if (context != null) {
            List<RequestHandle> list;
            Object object = this.requestMap;
            synchronized (object) {
                list = this.requestMap.get((Object)context);
                if (list == null) {
                    list = Collections.synchronizedList(new LinkedList());
                    this.requestMap.put(context, list);
                }
            }
            list.add(requestHandle);
            Iterator<RequestHandle> handles = list.iterator();
            while (handles.hasNext()) {
                if (!(handles.next()).shouldBeGarbageCollected()) continue;
                handles.remove();
            }
        }
        return requestHandle;
    }

    protected URI getURI(String url) {
        return URI.create(url).normalize();
    }

    public void setURLEncodingEnabled(boolean enabled) {
        this.isUrlEncodingEnabled = enabled;
    }

    public static String getUrlWithQueryString(boolean shouldEncodeUrl, String url, RequestParams params) {
        String string;
        if (url == null) {
            return null;
        }
        if (shouldEncodeUrl) {
            try {
                string = URLDecoder.decode(url, "UTF-8");
                URL uRL = new URL(string);
                URI uRI = new URI(uRL.getProtocol(), uRL.getUserInfo(), uRL.getHost(), uRL.getPort(), uRL.getPath(), uRL.getQuery(), uRL.getRef());
                url = uRI.toASCIIString();
            }
            catch (Exception exception) {
                log.e(LOG_TAG, "getUrlWithQueryString encoding URL", exception);
            }
        }
        if (params != null && !(string = params.getParamString().trim()).equals("") && !string.equals("?")) {
            url = url + (url.contains("?") ? "&" : "?");
            url = url + string;
        }
        return url;
    }

    public static boolean isInputStreamGZIPCompressed(PushbackInputStream inputStream) throws IOException {
        if (inputStream == null) {
            return false;
        }
        byte[] arrby = new byte[2];
        int n2 = inputStream.read(arrby);
        inputStream.unread(arrby);
        int n3 = arrby[0] & 255 | arrby[1] << 8 & 65280;
        return n2 == 2 && 35615 == n3;
    }

    public static void silentCloseInputStream(InputStream is) {
        try {
            if (is != null) {
                is.close();
            }
        }
        catch (IOException iOException) {
            log.w(LOG_TAG, "Cannot close input stream", iOException);
        }
    }

    public static void silentCloseOutputStream(OutputStream os) {
        try {
            if (os != null) {
                os.close();
            }
        }
        catch (IOException iOException) {
            log.w(LOG_TAG, "Cannot close output stream", iOException);
        }
    }

    private HttpEntity paramsToEntity(RequestParams params, ResponseHandlerInterface responseHandler) {
        HttpEntity httpEntity = null;
        try {
            if (params != null) {
                httpEntity = params.getEntity(responseHandler);
            }
        }
        catch (IOException iOException) {
            if (responseHandler != null) {
                responseHandler.sendFailureMessage(0, null, null, iOException);
            }
            iOException.printStackTrace();
        }
        return httpEntity;
    }

    public boolean isUrlEncodingEnabled() {
        return this.isUrlEncodingEnabled;
    }

    private HttpEntityEnclosingRequestBase addEntityToRequestBase(HttpEntityEnclosingRequestBase requestBase, HttpEntity entity) {
        if (entity != null) {
            requestBase.setEntity(entity);
        }
        return requestBase;
    }

    public static void endEntityViaReflection(HttpEntity entity) {
        if (entity instanceof HttpEntityWrapper) {
            try {
                Field field = null;
                for (Field field2 : HttpEntityWrapper.class.getDeclaredFields()) {
                    if (!field2.getName().equals("wrappedEntity")) continue;
                    field = field2;
                    break;
                }
                if (field != null) {
                    field.setAccessible(true);
                    HttpEntity httpEntity2 = (HttpEntity)field.get(entity);
                    if (httpEntity2 != null) {
                        httpEntity2.consumeContent();
                    }
                }
            }
            catch (Throwable throwable) {
                log.e(LOG_TAG, "wrappedEntity consume", throwable);
            }
        }
    }

    private static class InflatingEntity
    extends HttpEntityWrapper {
        InputStream wrappedStream;
        PushbackInputStream pushbackStream;
        GZIPInputStream gzippedStream;

        public InflatingEntity(HttpEntity wrapped) {
            super(wrapped);
        }

        public InputStream getContent() throws IOException {
            this.wrappedStream = this.wrappedEntity.getContent();
            this.pushbackStream = new PushbackInputStream(this.wrappedStream, 2);
            if (AsyncHttpClient.isInputStreamGZIPCompressed(this.pushbackStream)) {
                this.gzippedStream = new GZIPInputStream(this.pushbackStream);
                return this.gzippedStream;
            }
            return this.pushbackStream;
        }

        public long getContentLength() {
            return this.wrappedEntity == null ? 0L : this.wrappedEntity.getContentLength();
        }

        public void consumeContent() throws IOException {
            AsyncHttpClient.silentCloseInputStream(this.wrappedStream);
            AsyncHttpClient.silentCloseInputStream(this.pushbackStream);
            AsyncHttpClient.silentCloseInputStream(this.gzippedStream);
            super.consumeContent();
        }
    }

}

