package com.fortrue.common.tool.util;

import java.math.BigDecimal;
import java.util.Optional;

public class NumberUtil {

    private NumberUtil() {
    }

    /**
     * number -> string
     * @param number
     * @param defaultValue
     * @return
     */
    public static final String Number2String(Number number, String defaultValue){
        return Optional.ofNullable(number).map(String::valueOf).orElse(defaultValue);
    }

    /**
     * number -> string ,defaultValue为null
     * @param number
     * @return
     */
    public static final String Number2String(Number number){
        return Number2String(number, null);
    }


    public static final BigDecimal toBigDecimal(Long value){
        return Optional.ofNullable(value).map(BigDecimal::valueOf).orElse(BigDecimal.ZERO);
    }

    public static final BigDecimal toBigDecimal(Double value){
        return Optional.ofNullable(value).map(BigDecimal::valueOf).orElse(BigDecimal.ZERO);
    }

    public static final BigDecimal toBigDecimal(Integer value){
        return Optional.ofNullable(value).map(BigDecimal::valueOf).orElse(BigDecimal.ZERO);
    }

    public static final BigDecimal toBigDecimal(Float value){
        return Optional.ofNullable(value).map(BigDecimal::valueOf).orElse(BigDecimal.ZERO);
    }


}
