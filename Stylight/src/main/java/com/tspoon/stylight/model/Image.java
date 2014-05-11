package com.tspoon.stylight.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Image {
    @JsonProperty("primary")
    private boolean primary;

    @JsonProperty("url")
    private String url;

    public boolean isPrimary() {
        return primary;
    }

    public String getUrl() {
        return url;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
