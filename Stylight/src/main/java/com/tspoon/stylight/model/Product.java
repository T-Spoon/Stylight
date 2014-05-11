package com.tspoon.stylight.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    @JsonProperty("available")
    private boolean available;

    @JsonProperty("date")
    private String date;

    @JsonProperty("desc")
    private String description;

    @JsonProperty("id")
    private int id;

    @JsonProperty("images")
    private ArrayList<Image> images;

    @JsonProperty("likes")
    private int likes;

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private float price;

    @JsonProperty("sale")
    private boolean sale;

    @JsonProperty("savings")
    private double savings;

    @JsonProperty("shopLink")
    private String shopLink;

    @JsonProperty("url")
    private String url;

    private String primaryImage;

    @JsonProperty("brand")
    private String brandName;

    public boolean isAvailable() {
        return available;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public String getPrimaryImage() {
        return primaryImage;
    }

    public int getLikes() {
        return likes;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public boolean isSale() {
        return sale;
    }

    public double getSavings() {
        return savings;
    }

    public String getShopLink() {
        return shopLink;
    }

    public String getUrl() {
        return url;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
        int numImages = images.size();
        for (int i = 0; i < numImages; i++) {
            if (images.get(i).isPrimary()) {
                primaryImage = images.get(i).getUrl();
                break;
            }
        }
    }

    public void setBrandName(LinkedHashMap o) {
        brandName = (String) o.get("bname");
    }


}
