package com.urbangent.productservice.service;

import com.urbangent.productservice.dto.ProductRequest;
import com.urbangent.productservice.dto.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse getProductById(UUID id);

    void deleteProductById(UUID id);

    List<ProductResponse> getAllProducts();

    ProductResponse updateProduct(UUID id, ProductRequest request);
}
