package com.github.scipioutils.core.reflect;

/**
 * 包扫描时的处理接口
 * @author Alan Scipio
 * @since 2021/4/13
 */
public interface PackageScanHandler {

    /**
     * 对扫描到的类进行处理
     * @param clazz 扫描到的类
     */
    void handleClass(Class<?> clazz);

}
