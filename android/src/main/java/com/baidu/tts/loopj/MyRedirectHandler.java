/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.http.Header
 *  org.apache.http.HttpHost
 *  org.apache.http.HttpRequest
 *  org.apache.http.HttpResponse
 *  org.apache.http.ProtocolException
 *  org.apache.http.RequestLine
 *  org.apache.http.StatusLine
 *  org.apache.http.client.CircularRedirectException
 *  org.apache.http.client.utils.URIUtils
 *  org.apache.http.impl.client.DefaultRedirectHandler
 *  org.apache.http.impl.client.RedirectLocations
 *  org.apache.http.params.HttpParams
 *  org.apache.http.protocol.HttpContext
 */
package com.baidu.tts.loopj;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.client.CircularRedirectException;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.impl.client.RedirectLocations;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

class MyRedirectHandler
extends DefaultRedirectHandler {
    private static final String REDIRECT_LOCATIONS = "http.protocol.redirect-locations";
    private final boolean enableRedirects;

    public MyRedirectHandler(boolean allowRedirects) {
        this.enableRedirects = allowRedirects;
    }

    public boolean isRedirectRequested(HttpResponse response, HttpContext context) {
        if (!this.enableRedirects) {
            return false;
        }
        if (response == null) {
            throw new IllegalArgumentException("HTTP response may not be null");
        }
        int n2 = response.getStatusLine().getStatusCode();
        switch (n2) {
            case 301: 
            case 302: 
            case 303: 
            case 307: {
                return true;
            }
        }
        return false;
    }

    public URI getLocationURI(HttpResponse response, HttpContext context) throws ProtocolException {
        URI object;
        HttpHost httpHost;
        URI uRI;
        URI uRI2;
        if (response == null) {
            throw new IllegalArgumentException("HTTP response may not be null");
        }
        Header header = response.getFirstHeader("location");
        if (header == null) {
            throw new ProtocolException("Received redirect response " + (Object)response.getStatusLine() + " but no location header");
        }
        String string = header.getValue().replaceAll(" ", "%20");
        try {
            uRI = new URI(string);
        }
        catch (URISyntaxException uRISyntaxException) {
            throw new ProtocolException("Invalid redirect URI: " + string, (Throwable)uRISyntaxException);
        }
        HttpParams httpParams = response.getParams();
        if (!uRI.isAbsolute()) {
            if (httpParams.isParameterTrue("http.protocol.reject-relative-redirect")) {
                throw new ProtocolException("Relative redirect location '" + uRI + "' not allowed");
            }
            httpHost = (HttpHost)context.getAttribute("http.target_host");
            if (httpHost == null) {
                throw new IllegalStateException("Target host not available in the HTTP context");
            }
            HttpRequest request = (HttpRequest)context.getAttribute("http.request");
            try {
                uRI2 = new URI(request.getRequestLine().getUri());
                URI uRI3 = URIUtils.rewriteURI((URI)uRI2, (HttpHost)httpHost, (boolean)true);
                uRI = URIUtils.resolve((URI)uRI3, (URI)uRI);
            }
            catch (URISyntaxException uRISyntaxException) {
                throw new ProtocolException(uRISyntaxException.getMessage(), (Throwable)uRISyntaxException);
            }
        }
        if (httpParams.isParameterFalse("http.protocol.allow-circular-redirects")) {
            RedirectLocations redirectLocations = (RedirectLocations)context.getAttribute(REDIRECT_LOCATIONS);
            if (redirectLocations == null) {
                redirectLocations = new RedirectLocations();
                context.setAttribute(REDIRECT_LOCATIONS, (Object)redirectLocations);
            }
            if (uRI.getFragment() != null) {
                try {
                    object = URIUtils.rewriteURI((URI)uRI, new HttpHost(uRI.getHost(), uRI.getPort(), uRI.getScheme()), (boolean)true);
                }
                catch (URISyntaxException uRISyntaxException) {
                    throw new ProtocolException(uRISyntaxException.getMessage(), (Throwable)uRISyntaxException);
                }
            } else {
                object = uRI;
            }
            if (redirectLocations.contains((URI)object)) {
                throw new CircularRedirectException("Circular redirect to '" + (Object)object + "'");
            }
            redirectLocations.add((URI)object);
        }
        return uRI;
    }
}

