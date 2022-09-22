package com.github.scipioutils.core.reflect;

import java.lang.reflect.*;

/**
 * @author Alan Scipio
 * @since 2022/6/13
 */
public class ReflectUtils extends FieldUtils {

    private ReflectUtils() {
    }

    //==================================================================================================================

    /**
     * 获取interface上泛型的具体类型
     *
     * @param obj            要获取的类对象
     * @param interfaceIndex 接口索引(第几个接口，0-based index)
     * @param typeIndex      泛型索引(第几个泛型，0-based index)
     * @return 泛型的具体类型
     */
    public static Class<?> getGenericInterface(Object obj, int interfaceIndex, int typeIndex) {
        Type[] types = obj.getClass().getGenericInterfaces();
        ParameterizedType parameterizedType = (ParameterizedType) types[interfaceIndex];
        Type type = parameterizedType.getActualTypeArguments()[typeIndex];
        return checkAndGetType(type, typeIndex);

    }

    /**
     * 获取interface上泛型的具体类型
     * <p>默认第1个接口的第1个泛型</p>
     */
    public static Class<?> getGenericInterface(Object obj) {
        return getGenericInterface(obj, 0, 0);
    }

    /**
     * 获取class上泛型的具体类型
     *
     * @param obj   要获取的类对象
     * @param index 泛型索引(第几个泛型，0-based index)
     * @return 泛型的具体类型
     */
    public static Class<?> getGenericClass(Object obj, int index) {
        Type type = obj.getClass().getGenericSuperclass();
        return checkAndGetType(type, index);
    }

    /**
     * 获取class上泛型的具体类型
     * <p>默认第1个泛型</p>
     */
    public static Class<?> getGenericClass(Object obj) {
        return getGenericClass(obj, 0);
    }

    /**
     * 检查并获取具体类型
     *
     * @param type  类型对象
     * @param index 类型索引
     * @return 具体类型
     */
    private static Class<?> checkAndGetType(Type type, int index) {
        if (type instanceof Class<?>) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            Type t = pt.getActualTypeArguments()[index];
            return checkAndGetType(t, index);
        } else {
            String className = type == null ? "null" : type.getClass().getName();
            throw new RuntimeException("Expected a Class: java.lang.reflect.ParameterizedType," + " but <" + type + "> is of type " + className);
        }
    }

}
