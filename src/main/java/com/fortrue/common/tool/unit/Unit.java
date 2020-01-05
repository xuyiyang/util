package com.fortrue.common.tool.unit;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum Unit{
    /**
     * length
     */
    KM(UnitType.LENGTH, BigDecimal.TEN.pow(12), "千米", "公里"),
    M(UnitType.LENGTH, BigDecimal.TEN.pow(9), "米", null),
    DM(UnitType.LENGTH, BigDecimal.TEN.pow(8), "分米", null),
    CM(UnitType.LENGTH, BigDecimal.TEN.pow(7), "厘米",null),
    MM(UnitType.LENGTH, BigDecimal.TEN.pow(6), "毫米", null),
    UM(UnitType.LENGTH, BigDecimal.TEN.pow(3), "微米", null),
    NM(UnitType.LENGTH, BigDecimal.TEN.pow(0), "纳米", null),

    /**
     * time，排除年月，不固定
     */
    W(UnitType.TIME, BigDecimal.TEN.pow(0).multiply(BigDecimal.valueOf(60 * 60 * 24 * 7)), "周", null),
    D(UnitType.TIME, BigDecimal.TEN.pow(0).multiply(BigDecimal.valueOf(60 * 60 * 24)), "天", null),
    H(UnitType.TIME, BigDecimal.TEN.pow(0).multiply(BigDecimal.valueOf(60 * 60)), "小时", null),
    MI(UnitType.TIME, BigDecimal.TEN.pow(0).multiply(BigDecimal.valueOf(60)), "分", null),
    S(UnitType.TIME, BigDecimal.TEN.pow(9), "秒", null),
    MS(UnitType.TIME, BigDecimal.TEN.pow(6), "毫秒", null),
    US(UnitType.TIME, BigDecimal.TEN.pow(3), "微秒", null),
    NS(UnitType.TIME, BigDecimal.TEN.pow(0), "纳秒", null),

    /**
     * volume
     */
    M3(UnitType.VOLUME, BigDecimal.TEN.pow(9), "立方米", null),
    DM3(UnitType.VOLUME, BigDecimal.TEN.pow(6), "立方分米", "升"),
    CM3(UnitType.VOLUME, BigDecimal.TEN.pow(3), "立方厘米", "毫升"),
    MM3(UnitType.VOLUME, BigDecimal.TEN.pow(0), "立方毫米", null),

    /**
     * weight
     */
    T(UnitType.WEIGHT, BigDecimal.TEN.pow(9), "吨", null),
    KG(UnitType.WEIGHT, BigDecimal.TEN.pow(6), "千克", null),
    G(UnitType.WEIGHT, BigDecimal.TEN.pow(3), "克", null),
    MG(UnitType.WEIGHT, BigDecimal.TEN.pow(0), "毫克", null),

    /**
     * acreage
     */
    M2(UnitType.ACREAGE, BigDecimal.TEN.pow(6), "平方米", null),
    DM2(UnitType.ACREAGE, BigDecimal.TEN.pow(4), "平方分米", null),
    CM2(UnitType.ACREAGE, BigDecimal.TEN.pow(2), "平方厘米", null),
    MM2(UnitType.ACREAGE, BigDecimal.TEN.pow(0), "平方毫米", null),

    /**
     * price
     */
    YUAN(UnitType.PRICE, BigDecimal.TEN.pow(2), "元", null),
    JIAO(UnitType.PRICE, BigDecimal.TEN.pow(1), "角", null),
    FEN(UnitType.PRICE, BigDecimal.TEN.pow(0), "分", null);


    private static final Map<String, Unit> nameCodeMap = new HashMap<>();
    private static final UnitConversion UNIT_CONVERSION = (sourceUnit, destUnit, sourceValue) -> destUnit.ReferenceValue.divide(sourceUnit.ReferenceValue).multiply(sourceValue);
    private UnitType unitType;
    private BigDecimal ReferenceValue;
    private String name;
    private String alias;

    Unit(UnitType unitType, BigDecimal ReferenceValue, String name, String alias) {
        this.unitType = unitType;
        this.ReferenceValue = ReferenceValue;
        this.name = name;
        this.alias = alias;
    }

    static {
        EnumSet<Unit> unitEnumSet = EnumSet.allOf(Unit.class);
        unitEnumSet.forEach(Unit::addNameAndAlias);
    }

    private static void addNameAndAlias(Unit unit){

        nameCodeMap.put(unit.name, unit);
        String[] aliases = Optional.ofNullable(unit.alias).map(t -> t.split(",")).get();
        for (String aliasItem : aliases) {
            nameCodeMap.put(aliasItem, unit);
        }

    }

    public static final Unit getUnitByName(String name){
        return Optional.ofNullable(name).map(nameCodeMap::get).orElse(null);
    }

    public BigDecimal unitConvert(BigDecimal sourceValue, Unit destUnit){

        if(!this.unitType.equals(destUnit.unitType)) {
            throw new IllegalArgumentException("dest unit and source unit should be same unit type");
        }

        return UNIT_CONVERSION.convert(this, destUnit, sourceValue);
    }

    enum UnitType{
        LENGTH,
        TIME,
        VOLUME,
        WEIGHT,
        ACREAGE,
        PRICE
    }


}
