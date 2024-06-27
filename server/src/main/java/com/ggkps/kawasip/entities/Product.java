package com.ggkps.kawasip.entities;

import com.ggkps.kawasip.models.Details;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    private Date created_at;

    @Getter
    private String name;

    @Getter
    @Embedded
    private Details details;

    @Getter
    private Integer stocks;

    @Getter
    @Column(name = "order_id")
    private Integer orderId;

    public String toString() {
        return
        "{" +
            "\"id\": " + id + "," +
            "\"created_at\": \"" + created_at + "\"," +
            "\"name\": \"" + name + "\"," +
            "\"details\": " + details + "," +
            "\"stocks\": " + stocks + "," +
            "\"order_id\": " + orderId +
        "}";
    }
}