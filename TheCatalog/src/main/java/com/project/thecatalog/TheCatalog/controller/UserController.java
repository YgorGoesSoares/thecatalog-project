package com.project.thecatalog.TheCatalog.controller;

import com.project.thecatalog.TheCatalog.dto.UserDTO;
import com.project.thecatalog.TheCatalog.dto.UserInsertDTO;
import com.project.thecatalog.TheCatalog.entities.User;
import com.project.thecatalog.TheCatalog.entities.authentication.UserAuthenticationData;
import com.project.thecatalog.TheCatalog.infra.security.TokenJWTData;
import com.project.thecatalog.TheCatalog.services.UserService;
import com.project.thecatalog.TheCatalog.services.security.TokenService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/users")
public class UserController {


    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService service;

    @SecurityRequirement(name = "bearer-key")
    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) {
        Page<UserDTO> list = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @SecurityRequirement(name = "bearer-key")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        UserDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @SecurityRequirement(name = "bearer-key")
    @PostMapping
    public ResponseEntity<UserDTO> insertNewUser(@Valid @RequestBody UserInsertDTO dto) {
        UserDTO newDto = service.insertNewUser(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDto.getId()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }


    @SecurityRequirement(name = "bearer-key")
    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO dto) {
    dto = service.updateUser(id, dto);
    return ResponseEntity.ok().body(dto);
    }


    @SecurityRequirement(name = "bearer-key")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


}
