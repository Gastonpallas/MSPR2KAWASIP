package com.ggkps.kawasip.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class OrderModel {

    private Integer customer_id;

    private List<ProductModel> products;

    public void setCustomerId(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public Integer getCustomerId() {
        return customer_id;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public String toString() {
        return
        "{" +
            "\"customer_id\":" + customer_id + "," +
            "\"products\":" + products +
        "}";
    }
}