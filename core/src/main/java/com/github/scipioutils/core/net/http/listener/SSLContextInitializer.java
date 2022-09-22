package com.github.scipioutils.core.net.http.listener;

import com.github.scipioutils.core.StringUtils;
import org.openjsse.net.ssl.OpenJSSE;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

/**
 * HTTPS连接时的初始化
 *
 * @author Alan Scipio
 * @since 1.0.1-p1
 * date 2021/8/23
 */
@FunctionalInterface
public interface SSLContextInitializer {

    /**
     * 构建SSLContext
     *
     * @param trustManagers 信任器
     * @return SSLContext
     * @throws NoSuchAlgorithmException Protocol被设置了非法的算法名称
     * @throws KeyManagementException   SSLContext初始化异常
     * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#SSLContext">Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     */
    SSLContext build(TrustManager[] trustManagers, String protocol) throws Exception;

    /**
     * 默认SSLContext创建者
     */
    SSLContextInitializer DEFAULT = (trustManagers, protocol) -> {
        // 支持TLSv1.3协议的依赖注册到提供者中
        Security.addProvider(new OpenJSSE());
        String finalProtocol = StringUtils.isBlank(protocol) ? "TLSv1.2" : protocol;
        SSLContext sslContext = SSLContext.getInstance(finalProtocol);
        sslContext.init(null, trustManagers, new SecureRandom());
        return sslContext;
    };

}
