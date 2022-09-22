package com.github.scipioutils.core.data.cache;

import java.util.Map;

/**
 * @author Alan Scipio
 * @since 1.0.2
 */
class CacheRemoveThread<K> implements Runnable {

    private final Map<K, CacheEntity<K>> cachePool;
    private final K key;

    CacheRemoveThread(Map<K, CacheEntity<K>> cachePool, K key) {
        this.cachePool = cachePool;
        this.key = key;
    }

    @Override
    public void run() {
        cachePool.remove(key);
    }

}
