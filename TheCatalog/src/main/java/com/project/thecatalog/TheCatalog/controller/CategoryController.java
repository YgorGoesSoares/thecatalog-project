package com.project.thecatalog.TheCatalog.controller;

import com.project.thecatalog.TheCatalog.dto.CategoryDTO;
import com.project.thecatalog.TheCatalog.services.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @SecurityRequirement(name = "bearer-key")
    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> findAll(Pageable pageable) {

        Page<CategoryDTO> list = service.findAllPaged(pageable);

        return ResponseEntity.ok().body(list);
    }


    @SecurityRequirement(name = "bearer-key")
    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        CategoryDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }


    @SecurityRequirement(name = "bearer-key")
    @PostMapping
    public ResponseEntity<CategoryDTO> insertNewCategory(@Valid @RequestBody CategoryDTO dto) {
        dto = service.insertNewCategory(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }


    @SecurityRequirement(name = "bearer-key")
    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO dto) {
    dto = service.updateCategory(id, dto);
    return ResponseEntity.ok().body(dto);
    }


    @SecurityRequirement(name = "bearer-key")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id) {
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
