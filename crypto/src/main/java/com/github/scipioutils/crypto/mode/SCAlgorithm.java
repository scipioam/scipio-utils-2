package com.github.scipioutils.crypto.mode;

/**
 * 对称加密的算法
 *
 * @author Alan Scipio
 * created on:2020/9/22
 */
public enum SCAlgorithm implements CryptoAlgorithm {

    AES("AES"),
    AES_CBC_PKCS7PADDING("AES/CBC/PKCS7Padding"),
    DES("DES"),
    DESEDE("DESede");//3DES

    private final String name;

    SCAlgorithm(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
