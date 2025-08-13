package com.lvrgese.news_aggregator.entity;

import java.util.Arrays;

public enum Language {
    EN("en"), FR("fr"), DE("de"), EL("el"), HI("hi"),
    IT("it"), JA("ja"), ML("ml"), MR("mr"), NO("no"),
    PT("pt"), RO("ro"), RU("ru"), ES("es"), SV("sv"),
    TA("ta"), TE("te"), UK("uk");

    private final String code;

    Language(String code) {
        this.code = code;
    }

    public static boolean isValid(String code) {
        return Arrays.stream(values())
                .anyMatch(lang -> lang.code.equalsIgnoreCase(code));
    }
}
