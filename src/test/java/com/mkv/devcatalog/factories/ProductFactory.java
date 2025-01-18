package com.mkv.devcatalog.factories;

import com.mkv.devcatalog.domain.category.Category;
import com.mkv.devcatalog.domain.product.Product;
import com.mkv.devcatalog.domain.product.ProductDTO;

import java.time.Instant;
import java.util.HashSet;

public class ProductFactory {
    public static Product createProduct() {
        Product product = new Product(1L, "Phone", "Good Phone", 800.0, "https://img.com/img.png", Instant.now(), new HashSet<>());
        Category cat = new Category();
        cat.setId(2L);
        cat.setName("Eletronics");
        product.getCategories().add(cat);

        return product;
    }

    public static ProductDTO createProductDTO() {
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }
}
