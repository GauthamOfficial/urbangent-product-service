package com.urbangent.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbangent.productservice.dto.ProductRequest;
import com.urbangent.productservice.dto.ProductResponse;
import com.urbangent.productservice.entity.Category;
import com.urbangent.productservice.exception.ProductNotFoundException;
import com.urbangent.productservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createProduct_ValidData_Returns201() throws Exception {
        ProductRequest request = ProductRequest.builder()
                .name("Slim Fit Chino Trousers")
                .description("Stretch cotton chinos")
                .category(Category.TROUSERS)
                .unitPrice(new BigDecimal("45.99"))
                .stock(75)
                .size("32")
                .color("Khaki")
                .brand("UrbanGent Originals")
                .material("98% Cotton, 2% Elastane")
                .build();

        ProductResponse response = ProductResponse.builder()
                .productId(UUID.randomUUID())
                .name("Slim Fit Chino Trousers")
                .description("Stretch cotton chinos")
                .category(Category.TROUSERS)
                .unitPrice(new BigDecimal("45.99"))
                .stock(75)
                .size("32")
                .color("Khaki")
                .brand("UrbanGent Originals")
                .material("98% Cotton, 2% Elastane")
                .build();

        when(productService.createProduct(any(ProductRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Slim Fit Chino Trousers"))
                .andExpect(jsonPath("$.category").value("TROUSERS"));
    }

    @Test
    void createProduct_BlankName_Returns400() throws Exception {
        ProductRequest request = ProductRequest.builder()
                .name("")
                .category(Category.SHIRTS)
                .unitPrice(new BigDecimal("39.99"))
                .stock(10)
                .build();

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getProductById_Found_Returns200() throws Exception {
        UUID id = UUID.randomUUID();
        ProductResponse response = ProductResponse.builder()
                .productId(id)
                .name("Classic Leather Derby Shoes")
                .category(Category.SHOES)
                .unitPrice(new BigDecimal("129.99"))
                .stock(30)
                .build();

        when(productService.getProductById(id)).thenReturn(response);

        mockMvc.perform(get("/api/products/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Classic Leather Derby Shoes"));
    }

    @Test
    void getProductById_NotFound_Returns404() throws Exception {
        UUID id = UUID.randomUUID();
        when(productService.getProductById(id)).thenThrow(new ProductNotFoundException("Product not found"));

        mockMvc.perform(get("/api/products/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProduct_Returns204() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(productService).deleteProductById(id);

        mockMvc.perform(delete("/api/products/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllProducts_Returns200() throws Exception {
        ProductResponse p1 = ProductResponse.builder()
                .productId(UUID.randomUUID())
                .name("Slim Fit Oxford Shirt")
                .category(Category.SHIRTS)
                .unitPrice(new BigDecimal("39.99"))
                .build();

        ProductResponse p2 = ProductResponse.builder()
                .productId(UUID.randomUUID())
                .name("Two-Piece Formal Suit")
                .category(Category.SUITS)
                .unitPrice(new BigDecimal("249.99"))
                .build();

        when(productService.getAllProducts()).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
        void updateProduct_ValidData_Returns200() throws Exception {
        UUID id = UUID.randomUUID();

        ProductRequest request = ProductRequest.builder()
                .name("Updated Oxford Shirt")
                .category(Category.SHIRTS)
                .unitPrice(new BigDecimal("44.99"))
                .stock(30)
                .size("M")
                .color("White")
                .brand("UrbanGent")
                .material("Cotton")
                .build();

        ProductResponse response = ProductResponse.builder()
                .productId(id)
                .name("Updated Oxford Shirt")
                .category(Category.SHIRTS)
                .unitPrice(new BigDecimal("44.99"))
                .stock(30)
                .size("M")
                .color("White")
                .brand("UrbanGent")
                .material("Cotton")
                .build();

        when(productService.updateProduct(any(UUID.class), any(ProductRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Oxford Shirt"))
                .andExpect(jsonPath("$.unitPrice").value(44.99));
        }
}
