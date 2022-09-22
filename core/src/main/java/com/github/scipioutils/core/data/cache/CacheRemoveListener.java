package com.github.scipioutils.core.data.cache;

import java.util.Map;

/**
 * {@link IEasyCache}移除缓存数据时的监听回调
 *
 * @author Alan Scipio
 * @since 1.0.2
 */
@FunctionalInterface
public interface CacheRemoveListener {

    /**
     * 移除缓存数据时的监听回调
     *
     * @param cachePool 缓存池
     * @param key       键
     * @param <K>       键的类型
     */
    <K> void onRemove(Map<K, CacheEntity<K>> cachePool, Object key);

}
