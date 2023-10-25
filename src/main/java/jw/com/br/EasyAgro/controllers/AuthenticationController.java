package jw.com.br.EasyAgro.controllers;

import jw.com.br.EasyAgro.domain.user.User;
import jw.com.br.EasyAgro.dtos.AuthenticationDTO;
import jw.com.br.EasyAgro.dtos.JWTokenDTO;
import jw.com.br.EasyAgro.services.auth.TokenService;
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
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    ResponseEntity singIn(@RequestBody @Valid AuthenticationDTO credential){
        var tokenAuthentication = new UsernamePasswordAuthenticationToken(credential.login(),credential.password());
        var authentication = authenticationManager.authenticate(tokenAuthentication);
        var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new JWTokenDTO(tokenJWT));
    }

}
