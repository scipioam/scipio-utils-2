package com.github.scipioutils.core.data.cache.mount;

/**
 * 数据赋值\取值的策略
 *
 * @author Alan Scipio
 * @since 1.0.2
 */
public enum DataAssignmentPolicy {

    /**
     * 直接赋值\取值
     */
    DIRECT,

    /**
     * 使用set\get方法
     */
    USE_METHOD

}
