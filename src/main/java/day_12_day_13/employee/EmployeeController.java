package day_12_day_13.employee;
import java.net.URI;
import java.util.Set;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/v1/employees")
public class EmployeeController {
 private static final int MAX_PAGE_SIZE=100;
 private static final Set<String> ALLOWED_SORT_FIELDS=Set.of("id","name","salary");
 private final EmployeeService service;
 public EmployeeController(EmployeeService service){this.service=service;}
 @PostMapping
 public ResponseEntity<EmployeeResponse> create(@Valid @RequestBody EmployeeRequest request){
  EmployeeResponse created=service.create(request);
  return ResponseEntity.created(URI.create("/api/v1/employees/"+created.id())).body(created);
 }
 @GetMapping
 public ResponseEntity<Page<EmployeeResponse>> findAll(
  @RequestParam(defaultValue="0") int page,
  @RequestParam(defaultValue="10") int size,
  @RequestParam(defaultValue="id") String sortBy,
  @RequestParam(defaultValue="asc") String direction){
  if(page<0) throw new IllegalArgumentException("Page must be zero or greater");
  if(size<1||size>MAX_PAGE_SIZE) throw new IllegalArgumentException("Page size must be between 1 and "+MAX_PAGE_SIZE);
  if(!ALLOWED_SORT_FIELDS.contains(sortBy)) throw new IllegalArgumentException("Unsupported sort field. Allowed fields: "+ALLOWED_SORT_FIELDS);
  Sort.Direction sortDirection;
  try{sortDirection=Sort.Direction.fromString(direction);}
  catch(IllegalArgumentException ex){throw new IllegalArgumentException("Sort direction must be 'asc' or 'desc'");}
  Pageable pageable=PageRequest.of(page,size,Sort.by(sortDirection,sortBy));
  return ResponseEntity.ok(service.findAll(pageable));
 }
 @GetMapping("/{id}")
 public ResponseEntity<EmployeeResponse> findById(@PathVariable Long id){return ResponseEntity.ok(service.findById(id));}
 @PutMapping("/{id}")
 public ResponseEntity<EmployeeResponse> replace(@PathVariable Long id,@Valid @RequestBody EmployeeRequest request){return ResponseEntity.ok(service.replace(id,request));}
 @DeleteMapping("/{id}")
 public ResponseEntity<Void> delete(@PathVariable Long id){service.delete(id);return ResponseEntity.noContent().build();}
}