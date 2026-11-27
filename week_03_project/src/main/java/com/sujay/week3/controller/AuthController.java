package com.sujay.week3.controller;
import com.sujay.week3.dto.ApiDtos.*;
import com.sujay.week3.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/v1/auth")
public class AuthController {
 private final AuthenticationManager manager;private final JwtService jwt;
 public AuthController(AuthenticationManager m,JwtService j){manager=m;jwt=j;}
 @PostMapping("/login") public TokenResponse login(@Valid @RequestBody LoginRequest r){manager.authenticate(new UsernamePasswordAuthenticationToken(r.username(),r.password()));return new TokenResponse(jwt.create(r.username()),"Bearer");}
}