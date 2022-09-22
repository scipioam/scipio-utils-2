package com.github.scipioutils.core.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @since 2022/9/21
 */
public class FieldExt<T extends Annotation> {

    private Field field;
    private T annotation;

    public FieldExt() {
    }

    public FieldExt(Field field, T annotation) {
        this.field = field;
        this.annotation = annotation;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public T getAnnotation() {
        return annotation;
    }

    public void setAnnotation(T annotation) {
        this.annotation = annotation;
    }

}
