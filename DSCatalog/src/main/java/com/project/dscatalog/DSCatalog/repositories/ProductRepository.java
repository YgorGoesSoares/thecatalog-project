package com.project.dscatalog.DSCatalog.repositories;

import com.project.dscatalog.DSCatalog.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
