package com.fortrue.common.tool.unit;

import java.math.BigDecimal;

@FunctionalInterface
public interface UnitConversion {

    BigDecimal convert(Unit sourceUnit, Unit destUnit, BigDecimal sourceValue);

}
