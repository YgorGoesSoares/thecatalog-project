package com.project.thecatalog.TheCatalog.services;

import com.project.thecatalog.TheCatalog.dto.CategoryDTO;
import com.project.thecatalog.TheCatalog.entities.Category;
import com.project.thecatalog.TheCatalog.repositories.CategoryRepository;
import com.project.thecatalog.TheCatalog.services.exceptions.DatabaseException;
import com.project.thecatalog.TheCatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;
    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(Pageable pageable) {

        Page<Category> list = repository.findAll(pageable);
        return list.map(x -> new CategoryDTO(x));

    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<Category> obj = repository.findById(id);
        Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found."));
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO insertNewCategory(CategoryDTO dto) {
        Category entity = new Category();
        entity.setName(dto.getName());
        entity = repository.save(entity);
        return new CategoryDTO(entity);

    }
    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO dto) {
        try {Category entity = repository.getReferenceById(id);
        entity.setName(dto.getName());
        entity = repository.save(entity);
        return new CategoryDTO(entity);}
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }

    }

    public void deleteCategory(Long id) {
        Optional<Category> optionalCategory = repository.findById(id);
        if (!optionalCategory.isPresent()) {
            throw new ResourceNotFoundException("Id not found (" + id + ")");
        }

        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

}
