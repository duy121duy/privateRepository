
import entities.DeptEmp;
import entities.DeptManager;
import entities.Employee;
import entities.hbase.DeptEmpHbase;
import entities.hbase.EmpHbase;
import org.hibernate.Session;
import utils.HibernateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HibernateExample {
    public static void main(String[] args) {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();

            List<Employee> employees = session.createQuery("FROM Employee", Employee.class).list();

            List<DeptEmpHbase> deptEmpHbases = session.createQuery("FROM DeptEmp", DeptEmp.class).list()
                    .stream().map(deptEmp -> {
                        String duration = new SimpleDateFormat("yyyy/MM/dd").format(deptEmp.getFromDate())
                                + "-" + new SimpleDateFormat("yyyy/MM/dd").format(deptEmp.getToDate());
                        return new DeptEmpHbase(deptEmp.getEmpNo(), deptEmp.getDeptNo(), duration);
                    }).collect(Collectors.toList());

            List<DeptEmpHbase> deptManagerHbases = session.createQuery("FROM DeptManager ", DeptManager.class).list()
                    .stream().map(deptEmp -> {
                        String duration = new SimpleDateFormat("yyyy/MM/dd").format(deptEmp.getFromDate())
                                + "-" + new SimpleDateFormat("yyyy/MM/dd").format(deptEmp.getToDate());
                        return new DeptEmpHbase(deptEmp.getEmpNo(), deptEmp.getDeptNo(), duration);
                    }).collect(Collectors.toList());

            EmpHbase empHbase = null;
            Employee employee = null;

            List<EmpHbase> empHbases = new ArrayList<>();

            for (DeptEmpHbase deptEmpHbase : deptEmpHbases) {
                empHbase = new EmpHbase(deptEmpHbase.getDeptNo() + "-" + deptEmpHbase.getEmpNo());
                employee = employees.get(employees.indexOf(new Employee(deptEmpHbase.getEmpNo())));
                DeptEmpHbase deptEmpHbase1 = deptEmpHbases.get(deptEmpHbases.indexOf(new DeptEmpHbase(deptEmpHbase.getEmpNo(),
                        String.valueOf(deptEmpHbase.getDeptNo()), null)));
                if (!empHbases.contains(empHbase)) {
                    if (deptEmpHbase1 == null) {
                        empHbases.add(new EmpHbase(deptEmpHbase.getDeptNo() + "-" + deptEmpHbase.getEmpNo(),
                                new SimpleDateFormat("yyyy/MM/dd").format(employee.getBirthDate()), employee.getFirstName(),
                                employee.getLastName(), employee.getGender(), new SimpleDateFormat("yyyy/MM/dd").format(employee.getHireDate()),
                                Stream.of(deptEmpHbase.getDuration()).collect(Collectors.toList()), null));
                    } else {
                        empHbases.add(new EmpHbase(deptEmpHbase.getDeptNo() + "-" + deptEmpHbase.getEmpNo(),
                                new SimpleDateFormat("yyyy/MM/dd").format(employee.getBirthDate()), employee.getFirstName(),
                                employee.getLastName(), employee.getGender(), new SimpleDateFormat("yyyy/MM/dd").format(employee.getHireDate()),
                                Stream.of(deptEmpHbase.getDuration()).collect(Collectors.toList()), deptEmpHbase1.getDuration()));
                    }
                } else {
                    // Employee chuyen vao phong 2 lan
                    empHbases.get(empHbases.indexOf(empHbase)).getDurationEmp().add(deptEmpHbase.getDuration());
                }
            }

            empHbase = new EmpHbase("d001-110039");
            System.out.println(empHbases.get(empHbases.indexOf(empHbase)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
