package com.lvrgese.news_aggregator.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Language {
    @JsonProperty("en") ENGLISH,
    @JsonProperty("fr") FRENCH,
    @JsonProperty("de") GERMAN,
    @JsonProperty("el") GREEK,
    @JsonProperty("hi") HINDI,
    @JsonProperty("it") ITALIAN,
    @JsonProperty("ja") JAPANESE,
    @JsonProperty("ml") MALAYALAM,
    @JsonProperty("mr") MARATHI,
    @JsonProperty("no") NORWEGIAN,
    @JsonProperty("pt") PORTUGUESE,
    @JsonProperty("ro") ROMANIAN,
    @JsonProperty("ru") RUSSIAN,
    @JsonProperty("es") SPANISH,
    @JsonProperty("sv") SWEDISH,
    @JsonProperty("ta") TAMIL,
    @JsonProperty("te") TELUGU,
    @JsonProperty("uk") UKRAINIAN
}
