package com.ggkps.kawasip.models;

import lombok.Getter;

@Getter
public class ProductModel {

    private String name;

    private Details details;

    private Integer stocks;

    public void setName(String name) {
        this.name = name;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public void setStocks(Integer stocks) {
        this.stocks = stocks;
    }

    public String toString() {
        return
        "{" +
            "\"name\":\"" + name + "\"," +
            "\"details\":" + details + "," +
            "\"stocks\":" + stocks +
        "}";
    }
}