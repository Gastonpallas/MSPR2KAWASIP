package com.ggkps.kawasip.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Details {

    private Integer price;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String color;

    public Details() {
    }

    public Details(Integer price, String description, String color) {
        this.price = price;
        this.description = description;
        this.color = color;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String toString() {
        return
        "{" +
            "\"price\":" + price +
            ", \"description\":\"" + description + "\"," +
            "\"color\":\"" + color + "\"," +
        "}";
    }
}