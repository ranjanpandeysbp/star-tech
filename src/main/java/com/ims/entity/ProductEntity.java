package com.ims.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    private String productName;
    private Integer quantity;

    @ManyToOne
    private MerchantVendorEntity merchantVendorEntity;
    @ManyToOne
    private MerchantManagerEntity merchantManagerEntity;
    @ManyToOne
    private LocationEntity locationEntity;

    @ManyToOne
    private CategoryEntity category;

    private double price;

    private boolean active;

    @ManyToOne
    private BrandEntity brand;
}
