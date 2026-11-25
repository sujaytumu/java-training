package day_12_day_13.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
@ConfigurationProperties(prefix="app")
public class AppProperties {
 private String companyName;
 private String employeeCodePrefix;
 public String getCompanyName(){return companyName;}
 public void setCompanyName(String companyName){this.companyName=companyName;}
 public String getEmployeeCodePrefix(){return employeeCodePrefix;}
 public void setEmployeeCodePrefix(String employeeCodePrefix){this.employeeCodePrefix=employeeCodePrefix;}
}