package com.ggkps.kawasip.entities;

import com.ggkps.kawasip.models.Details;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    private String status;

    @Getter
    @Column(name = "customer_id")
    private Integer customerId;

    @Getter
    @Transient
    private List<Product> products;

    @Override
    public String toString() {
        return
        "{" +
            "\"id\": " + id + "," +
            "\"status\": \"" + status + "\"," +
            "\"customer_id\": " + customerId + "," +
            "\"products\": " + products +
        "}";
    }
}