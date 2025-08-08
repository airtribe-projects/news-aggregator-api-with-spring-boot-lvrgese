package com.lvrgese.news_aggregator.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsPreferencesDTO {

    private Long prefId;
    @NotBlank
    @NotNull
    private String query; //Mandatory, Keywords separated by space or combined with logical operators
    private String lang; //Optional
    private String country; //Optional
    @Min(1)
    @Max(100)
    private int count;
    private String sortBy;

    public NewsPreferencesDTO() {
    }

    public NewsPreferencesDTO(Long prefId, String query, String lang, String country, int count, String sortBy) {
        this.prefId = prefId;
        this.query = query;
        this.lang = lang;
        this.country = country;
        this.count = count;
        this.sortBy = sortBy;
    }

    public Long getPrefId() {
        return prefId;
    }

    public void setPrefId(Long prefId) {
        this.prefId = prefId;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
