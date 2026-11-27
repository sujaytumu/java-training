package com.sujay.week3.controller;
import com.sujay.week3.dto.ApiDtos.*;
import com.sujay.week3.service.EmployeeService;
import com.sujay.week3.service.IdempotencyKeyRegistry;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/v1/employees")
public class EmployeeController {
 private final EmployeeService service;private final IdempotencyKeyRegistry idempotency;
 public EmployeeController(EmployeeService s,IdempotencyKeyRegistry i){service=s;idempotency=i;}
 @PostMapping @PreAuthorize("hasRole('ADMIN')")
 public ResponseEntity<EmployeeResponse> create(@RequestHeader(value="Idempotency-Key",required=false) String key,@Valid @RequestBody EmployeeRequest r){
  EmployeeResponse e;
  if(key==null||key.isBlank())e=service.create(r);
  else{
   String canonical=String.join("|",r.name().trim(),r.email().trim().toLowerCase(),r.salary().toPlainString(),r.departmentId().toString());
   e=idempotency.execute(key,IdempotencyKeyRegistry.fingerprint(canonical),()->service.create(r));
  }
  return ResponseEntity.created(URI.create("/api/v1/employees/"+e.id())).body(e);
 }
 @GetMapping public Page<EmployeeResponse> list(@RequestParam(required=false) String name,@RequestParam(required=false) Long departmentId,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="10") int size,@RequestParam(defaultValue="name") String sortBy,@RequestParam(defaultValue="asc") String direction){return service.list(name,departmentId,page,size,sortBy,direction);}
 @GetMapping("/{id}") public EmployeeResponse get(@PathVariable Long id){return service.get(id);}
 @PutMapping("/{id}") @PreAuthorize("hasRole('ADMIN')") public EmployeeResponse replace(@PathVariable Long id,@Valid @RequestBody EmployeeRequest r){return service.replace(id,r);}
 @DeleteMapping("/{id}") @PreAuthorize("hasRole('ADMIN')") public ResponseEntity<Void> delete(@PathVariable Long id){service.delete(id);return ResponseEntity.noContent().build();}
}