package com.github.scipioutils.core.net.http;

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 * X509TrustManager的空实现(信任任何证书)
 *
 * @author Alan Min
 */
public class AllTrustX509TrustManager implements X509TrustManager {

    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
