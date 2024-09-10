package com.ims.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Long merchantId;
    private Long categoryId;
    private CategoryDTO category;
    private Double price;
    private String currency;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean active;
}
