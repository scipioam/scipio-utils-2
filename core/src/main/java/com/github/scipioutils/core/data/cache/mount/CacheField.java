package com.github.scipioutils.core.data.cache.mount;

import java.lang.annotation.*;

/**
 * @author Alan Scipio
 * @since 1.0.2
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface CacheField {

    /**
     * 多个缓存字段时，需要指定id
     */
    int id() default MountedCache.DEFAULT_ID;

}
