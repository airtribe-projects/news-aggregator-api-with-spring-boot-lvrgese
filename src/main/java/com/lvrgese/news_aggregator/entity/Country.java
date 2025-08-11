package com.lvrgese.news_aggregator.entity;

import java.util.Arrays;

public enum Country {
    AU("au"), BR("br"), CA("ca"), CN("cn"), EG("eg"),
    FR("fr"), DE("de"), GR("gr"), HK("hk"), IN("in"),
    IE("ie"), IT("it"), JP("jp"), NL("nl"), NO("no"),
    PK("pk"), PE("pe"), PH("ph"), PT("pt"), RO("ro"),
    RU("ru"), SG("sg"), ES("es"), SE("se"), CH("ch"),
    TW("tw"), UA("ua"), GB("gb"), US("us");

    private final String code;

    Country(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static boolean isValid(String code) {
        return Arrays.stream(values())
                .anyMatch(country -> country.code.equalsIgnoreCase(code));
    }
}


