package com.github.scipioutils.crypto.mode;

/**
 * 非对称加密的算法
 *
 * @author Alan Scipio
 * Created on: 2020/9/27
 */
public enum ACAlgorithm implements CryptoAlgorithm {

    RSA("RSA"),
    DSA("DSA");

    private final String name;

    ACAlgorithm(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
