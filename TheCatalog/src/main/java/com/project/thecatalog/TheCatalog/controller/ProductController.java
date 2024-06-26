package com.project.thecatalog.TheCatalog.controller;

import com.project.thecatalog.TheCatalog.dto.ProductDTO;
import com.project.thecatalog.TheCatalog.services.ProductService;
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
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService service;
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable) {
        Page<ProductDTO> list = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }


    @SecurityRequirement(name = "bearer-key")
    @PostMapping
    public ResponseEntity<ProductDTO> insertNewProduct(@Valid @RequestBody ProductDTO dto) {
        dto = service.insertNewProduct(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }


    @SecurityRequirement(name = "bearer-key")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
    dto = service.updateProduct(id, dto);
    return ResponseEntity.ok().body(dto);
    }


    @SecurityRequirement(name = "bearer-key")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
