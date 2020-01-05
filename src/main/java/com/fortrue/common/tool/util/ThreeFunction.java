package com.fortrue.common.tool.util;

@FunctionalInterface
public interface ThreeFunction<T, U, V, R> {
    /**
     * 三功能的函数
     * @param t
     * @param u
     * @param v
     * @return
     */
    R apply(T t, U u, V v);
}
