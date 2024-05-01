package com.project.thecatalog.TheCatalog.controller;

import com.project.thecatalog.TheCatalog.entities.User;
import com.project.thecatalog.TheCatalog.entities.authentication.UserAuthenticationData;
import com.project.thecatalog.TheCatalog.infra.security.TokenJWTData;
import com.project.thecatalog.TheCatalog.services.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/signin")
public class UserLoginController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity signIn (@RequestBody @Valid UserAuthenticationData data) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.tokenGenerate((User) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenJWTData(tokenJWT));
    }

}
