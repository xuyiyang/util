package com.fortrue.common.tool.util;

import java.util.Optional;

public class StringUtil {

    private StringUtil(){}


    public static final Long string2Long(String s, Long defaultValue){
        return Optional.ofNullable(s).map(Long::valueOf).orElse(defaultValue);
    }

    public static final Integer string2Integer(String s, Integer defaultValue){
        return Optional.ofNullable(s).map(Integer::valueOf).orElse(defaultValue);
    }

    public static final Double string2Double(String s, Double defaultValue){
        return Optional.ofNullable(s).map(Double::valueOf).orElse(defaultValue);
    }

    /**
     * string.isBlank()
     * @since jdk11
     */
    public static final boolean isStringEmpty(String s){
        return Optional.ofNullable(s).map(t -> t.isEmpty() || t.isBlank()).orElse(true);
    }










}
