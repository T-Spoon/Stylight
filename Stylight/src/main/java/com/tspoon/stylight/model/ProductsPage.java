package com.tspoon.stylight.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductsPage {
    @JsonProperty("productlist")
    private List<Product> productList;

    public List<Product> getProductList() {
        return productList;
    }

    public List<ProductPair> getPairedProductList() {
        int numProducts = productList.size();
        ArrayList<ProductPair> pairList = new ArrayList<ProductPair>(numProducts / 2 + 10);
        ProductPair currentPair = null;
        for (int i = 0; i < numProducts; i++) {
            if (i % 2 == 0) {
                currentPair = new ProductPair();
                currentPair.setProductOne(productList.get(i));
                pairList.add(currentPair);
            } else {
                currentPair.setProductTwo(productList.get(i));
            }
        }
        return pairList;
    }
}
