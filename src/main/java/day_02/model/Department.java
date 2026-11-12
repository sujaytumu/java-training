package day_02.model;
import java.util.Objects;
public class Department {
    //a)private instance vars
    private final int deptId;
    private final String deptName;
    //b)Constructor
    public Department(int deptId, String deptName) {
        //<0
        if(deptId <=0) {
            throw new IllegalArgumentException("Invalid DeptId");
        }
        //deptName issue,,,//blank checks whitespaces+null also
        if(deptName == null || deptName.isBlank()) {
            throw new IllegalArgumentException("Invalid DeptName");
        }
        this.deptId = deptId;
        this.deptName = deptName;
    }

    //c)get && set
    public int getDeptId() {
        return deptId;
    }
    public String getDeptName() {
        return deptName;
    }
    //set

    @Override
    public String toString(){
        return "deptId is:" + deptId + "and deptName is: " + deptName;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department other = (Department) o;
        return deptId == other.deptId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(deptId);

    }


}
