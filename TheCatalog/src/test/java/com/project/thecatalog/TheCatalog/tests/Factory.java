package com.project.thecatalog.TheCatalog.tests;

import com.project.thecatalog.TheCatalog.dto.ProductDTO;
import com.project.thecatalog.TheCatalog.entities.Category;
import com.project.thecatalog.TheCatalog.entities.Product;

import java.time.Instant;

public class Factory {
    public static Product createProduct() {
        Product product = new Product(1L, "Phone", "Good Phone", 800.0, "https://img.com/img.png", Instant.now());
        product.getCategories().add(new Category(2L, "Electronics"));
        return product;
    }

    public static ProductDTO createProductDTO() {
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }

}
