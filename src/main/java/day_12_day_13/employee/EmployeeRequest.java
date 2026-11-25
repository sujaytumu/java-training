package day_12_day_13.employee;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record EmployeeRequest(
 @NotBlank(message="Employee name is required") @Size(max=100,message="Employee name must not exceed 100 characters") String name,
 @NotNull(message="Salary is required") @DecimalMin(value="0.01",message="Salary must be greater than zero") Double salary
){}