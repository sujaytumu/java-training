package day_12_day_13.employee;
import day_12_day_13.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service @Transactional
public class EmployeeService {
 private final EmployeeRepository repository;
 public EmployeeService(EmployeeRepository repository){this.repository=repository;}
 public EmployeeResponse create(EmployeeRequest request){return EmployeeResponse.from(repository.save(new Employee(request.name(),request.salary())));}
 @Transactional(readOnly=true)
 public Page<EmployeeResponse> findAll(Pageable pageable){return repository.findAll(pageable).map(EmployeeResponse::from);}
 @Transactional(readOnly=true)
 public EmployeeResponse findById(Long id){return EmployeeResponse.from(getEmployeeOrThrow(id));}
 public EmployeeResponse replace(Long id,EmployeeRequest request){
  Employee employee=getEmployeeOrThrow(id); employee.replaceDetails(request.name(),request.salary());
  return EmployeeResponse.from(repository.save(employee));
 }
 public void delete(Long id){repository.delete(getEmployeeOrThrow(id));}
 private Employee getEmployeeOrThrow(Long id){
  return repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee not found with ID: "+id));
 }
}