package com.project.thecatalog.TheCatalog.repositories;

import com.project.thecatalog.TheCatalog.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
