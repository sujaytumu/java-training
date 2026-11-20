package day_09_day_10.model;
import java.util.Objects;
public final class Employee {
 private final int id; private final String name; private final EmployeeType type; private final double basicSalary; private final boolean active;
 private Employee(Builder b) { id=b.id; name=Objects.requireNonNull(b.name); type=Objects.requireNonNull(b.type); basicSalary=b.basicSalary; active=b.active; if(id<=0 || name.isBlank() || !Double.isFinite(basicSalary) || basicSalary<0) throw new IllegalArgumentException("Invalid employee"); }
 public int getId(){return id;} public String getName(){return name;} public EmployeeType getType(){return type;} public double getBasicSalary(){return basicSalary;} public boolean isActive(){return active;}
 public static class Builder { private int id; private String name; private EmployeeType type; private double basicSalary; private boolean active=true;
 public Builder id(int x){id=x;return this;} public Builder name(String x){name=x;return this;} public Builder type(EmployeeType x){type=x;return this;} public Builder basicSalary(double x){basicSalary=x;return this;} public Builder active(boolean x){active=x;return this;} public Employee build(){return new Employee(this);} }
}
