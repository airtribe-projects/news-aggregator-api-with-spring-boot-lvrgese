package com.lvrgese.news_aggregator.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class NewsPreferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prefId;
    @NotBlank
    @NotNull
    private String query; //Mandatory, Keywords separated by space or combined with logical operators
    private String lang; //Optional
    private String country; //Optional
    @Min(1)
    @Max(100)
    private int count;
    private String sortBy; //Optional - Either "publishedAt" or "relevance"

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public NewsPreferences() {
    }

    public NewsPreferences(builder b){
        this.query = b.query;
        this.lang = b.lang;
        this.count = b.count;
        this.country = b.country;
        this.sortBy = b.sortBy;
        this.user = b.user;
    }

    public static class builder{
        private String query;
        private String lang;
        private String country;
        private int count;
        private String sortBy;
        private User user;

        public builder query(String query){
            this.query = query;
            return this;
        }
        public builder lang(String lang){
            this.lang = lang;
            return this;
        }
        public builder country(String country){
            this.country = country;
            return this;
        }
        public builder count(int count){
            this.count = count;
            return this;
        }
        public builder sortBy(String sortBy){
            this.sortBy = sortBy;
            return this;
        }

        public builder user(User user){
            this.user = user;
            return this;
        }

        public NewsPreferences build(){
            return new NewsPreferences(this);
        }
    }

    public long getPrefId() {
        return prefId;
    }

    public void setPrefId(Long prefId) {
        this.prefId = prefId;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

