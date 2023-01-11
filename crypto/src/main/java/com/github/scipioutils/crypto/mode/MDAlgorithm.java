package com.github.scipioutils.crypto.mode;

/**
 * 信息摘要算法
 *
 * @author Alan Scipio
 * created on: 2020/9/22
 */
public enum MDAlgorithm implements CryptoAlgorithm {

    MD5("MD5"),
    SHA_1("SHA-1"),
    SHA_256("SHA-256");

    private final String name;

    MDAlgorithm(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
