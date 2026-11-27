package com.sujay.week3.controller;
import com.sujay.week3.dto.ApiDtos.*;
import com.sujay.week3.service.AttendanceService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/v1/attendance")
public class AttendanceController {
 private final AttendanceService service;public AttendanceController(AttendanceService s){service=s;}
 @PostMapping @PreAuthorize("hasRole('ADMIN')") public AttendanceResponse record(@Valid @RequestBody AttendanceRequest r){return service.record(r);}
 @GetMapping("/employee/{id}") public List<AttendanceResponse> list(@PathVariable Long id){return service.list(id);}
}