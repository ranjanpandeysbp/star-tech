package com.ims.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Long vendorId;
    private Long managerId;
    private Long locationId;
    private Long categoryId;
    private Long brandId;
    private double price;
    private boolean active;
}
