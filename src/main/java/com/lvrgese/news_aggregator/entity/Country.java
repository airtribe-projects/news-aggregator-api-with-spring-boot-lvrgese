package com.lvrgese.news_aggregator.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Country {
    @JsonProperty("au") AUSTRALIA,
    @JsonProperty("br") BRAZIL,
    @JsonProperty("ca") CANADA,
    @JsonProperty("cn") CHINA,
    @JsonProperty("eg") EGYPT,
    @JsonProperty("fr") FRANCE,
    @JsonProperty("de") GERMANY,
    @JsonProperty("gr") GREECE,
    @JsonProperty("hk") HONG_KONG,
    @JsonProperty("in") INDIA,
    @JsonProperty("ie") IRELAND,
    @JsonProperty("it") ITALY,
    @JsonProperty("jp") JAPAN,
    @JsonProperty("nl") NETHERLANDS,
    @JsonProperty("no") NORWAY,
    @JsonProperty("pk") PAKISTAN,
    @JsonProperty("pe") PERU,
    @JsonProperty("ph") PHILIPPINES,
    @JsonProperty("pt") PORTUGAL,
    @JsonProperty("ro") ROMANIA,
    @JsonProperty("ru") RUSSIAN_FEDERATION,
    @JsonProperty("sg") SINGAPORE,
    @JsonProperty("es") SPAIN,
    @JsonProperty("se") SWEDEN,
    @JsonProperty("ch") SWITZERLAND,
    @JsonProperty("tw") TAIWAN,
    @JsonProperty("ua") UKRAINE,
    @JsonProperty("gb") UNITED_KINGDOM,
    @JsonProperty("us") UNITED_STATES
}
