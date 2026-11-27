package com.sujay.week3.controller;
import com.sujay.week3.dto.ApiDtos.*;
import com.sujay.week3.service.DepartmentService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/v1/departments")
public class DepartmentController {
 private final DepartmentService service;public DepartmentController(DepartmentService s){service=s;}
 @PostMapping @PreAuthorize("hasRole('ADMIN')") public ResponseEntity<DepartmentResponse> create(@Valid @RequestBody DepartmentRequest r){DepartmentResponse d=service.create(r);return ResponseEntity.created(URI.create("/api/v1/departments/"+d.id())).body(d);}
 @GetMapping public List<DepartmentResponse> list(){return service.list();}
}