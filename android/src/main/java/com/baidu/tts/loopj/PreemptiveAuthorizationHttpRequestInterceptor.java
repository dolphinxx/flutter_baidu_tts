/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.http.HttpException
 *  org.apache.http.HttpHost
 *  org.apache.http.HttpRequest
 *  org.apache.http.HttpRequestInterceptor
 *  org.apache.http.auth.AuthScheme
 *  org.apache.http.auth.AuthScope
 *  org.apache.http.auth.AuthState
 *  org.apache.http.auth.Credentials
 *  org.apache.http.client.CredentialsProvider
 *  org.apache.http.impl.auth.BasicScheme
 *  org.apache.http.protocol.HttpContext
 */
package com.baidu.tts.loopj;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.protocol.HttpContext;

public class PreemptiveAuthorizationHttpRequestInterceptor
implements HttpRequestInterceptor {
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
}

