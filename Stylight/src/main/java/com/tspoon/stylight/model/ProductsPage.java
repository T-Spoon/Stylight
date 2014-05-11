package com.tspoon.stylight.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductsPage {
    @JsonProperty("productlist")
    private List<Product> productList;

    public List<Product> getProductList() {
        return productList;
    }
}
