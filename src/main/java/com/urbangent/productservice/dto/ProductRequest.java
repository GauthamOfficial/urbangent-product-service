package com.urbangent.productservice.dto;

import com.urbangent.productservice.entity.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    @NotBlank(message = "Product name is required")
    private String name;

    private String description;

    @NotNull(message = "Category is required")
    private Category category;

    @Positive(message = "Unit price must be positive")
    private BigDecimal unitPrice;

    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;

    private String size;

    private String color;

    private String brand;

    private String material;
}
