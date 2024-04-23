package com.project.dscatalog.DSCatalog.services;

import com.project.dscatalog.DSCatalog.dto.ProductDTO;
import com.project.dscatalog.DSCatalog.entities.Product;
import com.project.dscatalog.DSCatalog.repositories.ProductRepository;
import com.project.dscatalog.DSCatalog.services.exceptions.DatabaseException;
import com.project.dscatalog.DSCatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {

        Page<Product> list = repository.findAll(pageRequest);
        return list.map(x -> new ProductDTO(x));

    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> obj = repository.findById(id);
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found."));
        return new ProductDTO(entity, entity.getCategories());
    }

    @Transactional
    public ProductDTO insertNewProduct(ProductDTO dto) {
        Product entity = new Product();
        entity.setName(dto.getName());
        entity = repository.save(entity);
        return new ProductDTO(entity);

    }
    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO dto) {
        try {Product entity = repository.getReferenceById(id);
        entity.setName(dto.getName());
        entity = repository.save(entity);
        return new ProductDTO(entity);}
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }

    }

    public void deleteProduct(Long id) {
        Optional<Product> optionalProduct = repository.findById(id);
        if (!optionalProduct.isPresent()) {
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
