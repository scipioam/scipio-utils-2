package com.github.scipioutils.core.data.structure;

/**
 * 通用的迭代遍历回调
 *
 * @author Alan Scipio
 * create date: 2022/10/12
 */
@FunctionalInterface
public interface IterateHandler<K, V> {

    /**
     * 每次遍历
     *
     * @param index 遍历下标(0-based)
     */
    void handle(int index, K key, V value);

}
