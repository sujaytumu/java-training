package day_12_day_13.employee;
public record EmployeeResponse(Long id,String name,Double salary){
 public static EmployeeResponse from(Employee employee){return new EmployeeResponse(employee.getId(),employee.getName(),employee.getSalary());}
}