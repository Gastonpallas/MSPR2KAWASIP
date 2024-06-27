package com.ggkps.kawasip.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "link")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Column(name = "created_at")
    private Integer createdAt;

    @Getter
    @Column(name = "order_id")
    private Integer orderId;

    @Getter
    @Column(name = "product_id")
    private Integer productId;

    @Override
    public String toString() {
        return
        "{" +
            "\"id\": " + id + "," +
            "\"created_at\": \"" + createdAt + "\"," +
            "\"customer_id\": " + orderId + "," +
            "\"product_id\": " + productId + "," +
        "}";
    }
}