package com.urbangent.productservice.controller;

import com.urbangent.productservice.dto.ProductRequest;
import com.urbangent.productservice.dto.ProductResponse;
import com.urbangent.productservice.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing the UrbanGent product catalog.
 * Provides endpoints for creating, retrieving, updating, and deleting products.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Creates a new product in the catalog.
     * @param request the product details including name, category, price, and stock
     * @return the created product with a generated UUID
     */
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request));
    }

    /**
     * Retrieves a product by its unique identifier.
     * @param id the product UUID
     * @return the product details
     * @throws com.urbangent.productservice.exception.ProductNotFoundException if no product exists with the given ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    /**
     * Retrieves all products in the catalog.
     * @return list of all available products
     */
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * Updates an existing product with new details.
     * @param id the product UUID to update
     * @param request the updated product details
     * @return the updated product
     * @throws com.urbangent.productservice.exception.ProductNotFoundException if no product exists with the given ID
     */

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable UUID id,
                                                         @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    /**
     * Deletes a product from the catalog.
     * @param id the product UUID to delete
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable UUID id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }
}