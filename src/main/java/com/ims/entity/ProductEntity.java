package com.ims.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_table")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    private String productName;
    private Integer quantity;

    @ManyToOne
    private UserEntity merchant;

    @ManyToOne
    private CategoryEntity category;

    private double price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean active;
    @Enumerated(EnumType.STRING)
    private ECurrency currency;
}
