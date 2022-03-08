package entities.hbase;

import java.util.List;
import java.util.Objects;

public class EmpHbase {
    private String empNoDeptNo;

    private String birthDate;

    private String firstName;

    private String lastName;

    private String gender;

    private String hireDate;

    private List<String> durationEmp;

    private String durationManager;

    public EmpHbase() {
    }

    public EmpHbase(String empNoDeptNo) {
        this.empNoDeptNo = empNoDeptNo;
    }

    public EmpHbase(String empNoDeptNo, String birthDate, String firstName, String lastName, String gender, String hireDate, List<String> durationEmp, String durationManager) {
        this.empNoDeptNo = empNoDeptNo;
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.hireDate = hireDate;
        this.durationEmp = durationEmp;
        this.durationManager = durationManager;
    }

    public String getEmpNoDeptNo() {
        return empNoDeptNo;
    }

    public void setEmpNoDeptNo(String empNoDeptNo) {
        this.empNoDeptNo = empNoDeptNo;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public List<String> getDurationEmp() {
        return durationEmp;
    }

    public void setDurationEmp(List<String> durationEmp) {
        this.durationEmp = durationEmp;
    }

    public String getDurationManager() {
        return durationManager;
    }

    public void setDurationManager(String durationManager) {
        this.durationManager = durationManager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmpHbase empHbase = (EmpHbase) o;
        return Objects.equals(empNoDeptNo, empHbase.empNoDeptNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNoDeptNo);
    }

    @Override
    public String toString() {
        return "EmpHbase{" +
                "empNoDeptNo='" + empNoDeptNo + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", hireDate='" + hireDate + '\'' +
                ", durationEmp='" + durationEmp + '\'' +
                ", durationManager='" + durationManager + '\'' +
                '}';
    }
}
