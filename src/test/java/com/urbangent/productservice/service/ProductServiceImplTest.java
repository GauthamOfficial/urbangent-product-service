package com.urbangent.productservice.service;

import com.urbangent.productservice.dto.ProductRequest;
import com.urbangent.productservice.dto.ProductResponse;
import com.urbangent.productservice.entity.Category;
import com.urbangent.productservice.entity.Product;
import com.urbangent.productservice.exception.ProductNotFoundException;
import com.urbangent.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void createProduct_Success() {
        ProductRequest request = ProductRequest.builder()
                .name("Slim Fit Chino Trousers")
                .description("Stretch cotton chinos with tapered leg")
                .category(Category.TROUSERS)
                .unitPrice(new BigDecimal("45.99"))
                .stock(75)
                .size("32")
                .color("Khaki")
                .brand("UrbanGent Originals")
                .material("98% Cotton, 2% Elastane")
                .build();

        Product saved = Product.builder()
                .productId(UUID.randomUUID())
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .unitPrice(request.getUnitPrice())
                .stock(request.getStock())
                .size(request.getSize())
                .color(request.getColor())
                .brand(request.getBrand())
                .material(request.getMaterial())
                .build();

        when(productRepository.save(any(Product.class))).thenReturn(saved);

        ProductResponse response = productService.createProduct(request);

        assertNotNull(response);
        assertEquals("Slim Fit Chino Trousers", response.getName());
        assertEquals(Category.TROUSERS, response.getCategory());
        assertEquals(new BigDecimal("45.99"), response.getUnitPrice());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void getProductById_Success() {
        UUID id = UUID.randomUUID();
        Product product = Product.builder()
                .productId(id)
                .name("Classic Leather Derby Shoes")
                .category(Category.SHOES)
                .unitPrice(new BigDecimal("129.99"))
                .stock(30)
                .size("42")
                .color("Dark Brown")
                .brand("UrbanGent Originals")
                .material("Full Grain Leather")
                .build();

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        ProductResponse response = productService.getProductById(id);

        assertNotNull(response);
        assertEquals("Classic Leather Derby Shoes", response.getName());
        assertEquals(id, response.getProductId());
    }

    @Test
    void getProductById_NotFound() {
        UUID id = UUID.randomUUID();
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(id));
    }

    @Test
    void deleteProductById_Success() {
        UUID id = UUID.randomUUID();
        when(productRepository.existsById(id)).thenReturn(true);

        productService.deleteProductById(id);

        verify(productRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteProductById_NotFound() {
        UUID id = UUID.randomUUID();
        when(productRepository.existsById(id)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProductById(id));
    }

    @Test
    void getAllProducts_ReturnsList() {
        Product product1 = Product.builder()
                .productId(UUID.randomUUID())
                .name("Slim Fit Oxford Shirt")
                .category(Category.SHIRTS)
                .unitPrice(new BigDecimal("39.99"))
                .build();

        Product product2 = Product.builder()
                .productId(UUID.randomUUID())
                .name("Wool Blend Overcoat")
                .category(Category.OUTERWEAR)
                .unitPrice(new BigDecimal("189.99"))
                .build();

        when(productRepository.findAll()).thenReturn(List.of(product1, product2));

        List<ProductResponse> responses = productService.getAllProducts();

        assertEquals(2, responses.size());
        assertEquals("Slim Fit Oxford Shirt", responses.get(0).getName());
        assertEquals("Wool Blend Overcoat", responses.get(1).getName());
    }

    @Test
    void getAllProducts_EmptyList() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        List<ProductResponse> responses = productService.getAllProducts();

        assertTrue(responses.isEmpty());
    }
}
