package net.jkcat.core.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public final class GenericUtils {

    private GenericUtils() {

    }

    public static Class<?> getGenericType(Object obj) {
        Class<?> genericType = null;
        Type superclass = obj.getClass().getGenericSuperclass();
        if (superclass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) superclass;
            Type[] types = parameterizedType.getActualTypeArguments();

            if (types != null && types.length > 0) {
                Type type = types[0];
                if (type instanceof Class) {
                    genericType = (Class<?>) type;
                }
            }
        }
        return genericType;
    }

}
