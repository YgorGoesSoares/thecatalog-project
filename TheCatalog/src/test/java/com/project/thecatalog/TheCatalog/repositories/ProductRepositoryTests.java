package com.project.thecatalog.TheCatalog.repositories;

import com.project.thecatalog.TheCatalog.entities.Product;
import com.project.thecatalog.TheCatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;


@DataJpaTest
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository repository;

    private Long existingId;
    private Long notExistingId;
    private Long countTotalProducts;

    @BeforeEach
    void setUp() throws Exception {
        this.existingId = 1L;
        this.notExistingId = 100L;
        this.countTotalProducts = repository.count();
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
    Product product = Factory.createProduct();
    product.setId(null);
    product = repository.save(product);

    Assertions.assertNotNull(product.getId());
    Assertions.assertEquals(countTotalProducts+1L, product.getId());
    Assertions.assertTrue(repository.existsById(product.getId()));
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        repository.deleteById(existingId);

        Assertions.assertTrue(!repository.existsById(existingId));
    }

    @Test
    public void deleteShouldNotDoAnythingWhenIdDoesNotExists() {
        Long countBefore = repository.count();

        repository.deleteById(notExistingId);
        Long countAfter = repository.count();

        Assertions.assertEquals(countBefore, countAfter);

    }

    @Test
    public void findByIdShouldReturnAOptionalProductNotNullWhenIdExists() {
        Optional<Product> product = repository.findById(existingId);
        Assertions.assertTrue(product.isPresent());
    }


    @Test
    public void findByIdShouldReturnAEmptyOptionalProductWhenIdDoesNotExists() {
        Optional<Product> product = repository.findById(notExistingId);
        Assertions.assertTrue(product.isEmpty());
    }

}
