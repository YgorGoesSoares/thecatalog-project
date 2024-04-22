package com.project.dscatalog.DSCatalog.repositories;

import com.project.dscatalog.DSCatalog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
