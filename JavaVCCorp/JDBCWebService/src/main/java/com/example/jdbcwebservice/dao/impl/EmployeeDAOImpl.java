package com.example.jdbcwebservice.dao.impl;

import com.example.jdbcwebservice.dao.EmployeeDAO;
import com.example.jdbcwebservice.entities.*;
import com.example.jdbcwebservice.entities.PK.DeptEmpId;
import com.example.jdbcwebservice.entities.dto.CaculatedSalary;
import com.example.jdbcwebservice.entities.dto.DeptSalary;
import com.example.jdbcwebservice.entities.dto.EmpSalary;
import com.example.jdbcwebservice.entities.dto.Tax;
import com.example.jdbcwebservice.utils.MyConnection;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
@Transactional(rollbackFor = Exception.class)
public class EmployeeDAOImpl implements EmployeeDAO {
    @PersistenceContext
    private EntityManager entityManager;

    private static Connection connection = null;

    private Employee getEmployee(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();
        employee.setEmpNo(resultSet.getInt("emp_no"));
        employee.setBirthDate(resultSet.getDate("birth_date"));
        employee.setFirstName(resultSet.getString("first_name"));
        employee.setLastName(resultSet.getString("last_name"));
        employee.setGender(resultSet.getString("gender"));
        employee.setHireDate(resultSet.getDate("hire_date"));
        return employee;
    }

    private void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Employee> getEmployees(Date hireDateFrom, int salary, String deptNo, String title) {
        List<Employee> employees = new ArrayList<>();
        int count = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        StringBuilder sql = new StringBuilder("SELECT e.* FROM employees AS e " + "INNER JOIN salaries AS s ON e.emp_no = s.emp_no AND s.to_date = '9999-01-01' " + "INNER JOIN dept_emp AS de ON e.emp_no = de.emp_no AND de.to_date = '9999-01-01' " + "INNER JOIN titles AS t ON e.emp_no = t.emp_no ");
        if (!Objects.isNull(hireDateFrom)) {
            if (count == 0) {
                sql.append("WHERE ");
            } else {
                sql.append("AND ");
            }
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            sql.append("e.hire_date > '").append(dateFormat.format(hireDateFrom)).append("' ");
            count++;
        }
        if (salary != 0) {
            if (count == 0) {
                sql.append("WHERE ");
            } else {
                sql.append("AND ");
            }
            sql.append("s.salary > ").append(salary).append(" ");
            count++;
        }
        if (!StringUtils.isEmpty(title)) {
            if (count == 0) {
                sql.append("WHERE ");
            } else {
                sql.append("AND ");
            }
            sql.append("t.title = '").append(title).append("' ");
            count++;
        }
        if (!StringUtils.isEmpty(deptNo)) {
            if (count == 0) {
                sql.append("WHERE ");
            } else {
                sql.append("AND ");
            }
            sql.append("de.dept_no = '").append(deptNo).append("' ");
            count++;
        }

        try {
            connection = MyConnection.connect();
            preparedStatement = connection.prepareStatement(sql.toString());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                employees.add(getEmployee(resultSet));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            close(connection, preparedStatement, resultSet);
        }
        return employees;
    }

    @Override
    public Employee addEmployeeHibernate(Employee employee, Title title, Salary salary) {
        try {
            List<Employee> employees = entityManager.createQuery("FROM Employee ORDER BY empNo DESC").setFirstResult(0).setMaxResults(1).getResultList();
            int employeeId = employees.get(0).getEmpNo() + 1;

            employee.setEmpNo(employeeId);
            title.setEmpNo(employeeId);
            salary.setEmpNo(employeeId);

            entityManager.persist(employee);
            entityManager.persist(title);
            entityManager.persist(salary);
            Employee employee1 = entityManager.find(Employee.class, employeeId);
            return employee1;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Employee addEmployeeJDBC(Employee employee, Title title, Salary salary) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = MyConnection.connect();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("SELECT * FROM employees ORDER BY emp_no DESC LIMIT 1");
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int empLastNo = resultSet.getInt("emp_no");
            int employeeId = empLastNo + 1;

            preparedStatement = connection.prepareStatement("INSERT INTO employees VALUES(?, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, employeeId);
            preparedStatement.setDate(2, new java.sql.Date(employee.getBirthDate().getTime()));
            preparedStatement.setString(3, employee.getFirstName());
            preparedStatement.setString(4, employee.getLastName());
            preparedStatement.setString(5, employee.getGender());
            preparedStatement.setDate(6, new java.sql.Date(employee.getHireDate().getTime()));
            int rs = preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("INSERT INTO salaries VALUES(?, ?, ?, ?)");
            preparedStatement.setInt(1, employeeId);
            preparedStatement.setInt(2, salary.getSalary());
            preparedStatement.setDate(3, new java.sql.Date(salary.getFromDate().getTime()));
            preparedStatement.setDate(4, new java.sql.Date(salary.getToDate().getTime()));
            int rs1 = preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("INSERT INTO titles VALUES(?, ?, ?, ?)");
            preparedStatement.setInt(1, employeeId);
            preparedStatement.setString(2, title.getTitle());
            preparedStatement.setDate(3, new java.sql.Date(title.getFromDate().getTime()));
            preparedStatement.setDate(4, new java.sql.Date(title.getToDate().getTime()));
            int rs2 = preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("SELECT * FROM employees WHERE emp_no = ?");
            preparedStatement.setInt(1, employeeId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            connection.commit();

            Employee employee1 = new Employee(resultSet.getInt("emp_no"), resultSet.getDate("birth_date"), resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("gender"), resultSet.getDate("hire_date"));
            return employee1;
        } catch (SQLException | ClassNotFoundException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return null;
        } finally {
            close(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public DeptEmp changeDeptHibernate(int empNo, String deptNo) {
        try {
            Date currentDate = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Query query = entityManager.createQuery("UPDATE DeptEmp SET toDate = :currentDate WHERE empNo = :empNo AND toDate = :toDate");
            query.setParameter("currentDate", currentDate);
            query.setParameter("empNo", empNo);
            query.setParameter("toDate", dateFormat.parse("9999-01-01"));
            query.executeUpdate();

            DeptEmp deptEmp = new DeptEmp(empNo, deptNo, currentDate, dateFormat.parse("9999-01-01"));
            entityManager.persist(deptEmp);

            DeptEmp result = entityManager.find(DeptEmp.class, new DeptEmpId(empNo, deptNo));

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DeptEmp changeDeptJDBC(int empNo, String deptNo) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = MyConnection.connect();
            connection.setAutoCommit(false);
            Date currentDate = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            preparedStatement = connection.prepareStatement("UPDATE dept_emp SET to_date = ? WHERE emp_no = ? AND to_date = '9999-01-01'");
            preparedStatement.setDate(1, new java.sql.Date(currentDate.getTime()));
            preparedStatement.setInt(2, empNo);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("INSERT INTO dept_emp VALUE(?, ?, ?, '9999-01-01')");
            preparedStatement.setInt(1, empNo);
            preparedStatement.setString(2, deptNo);
            preparedStatement.setDate(3, new java.sql.Date(currentDate.getTime()));
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("SELECT * FROM dept_emp WHERE emp_no = ? AND dept_no = ?");
            preparedStatement.setInt(1, empNo);
            preparedStatement.setString(2, deptNo);

            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            connection.commit();

            DeptEmp deptEmp = new DeptEmp(resultSet.getInt("emp_no"), resultSet.getString("dept_no"), resultSet.getDate("from_date"), resultSet.getDate("to_date"));

            return deptEmp;
        } catch (SQLException | ClassNotFoundException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return null;
        } finally {
            close(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public CaculatedSalary getSalaryHibernate(Date fromDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Query query = entityManager.createNativeQuery("SELECT de.emp_no, de.dept_no, SUM(s.salary) AS total_salary " +
                "FROM salaries AS s INNER JOIN dept_emp AS de ON de.emp_no = s.emp_no " +
                "WHERE s.from_date > ? AND de.from_date > ? " +
                "GROUP BY de.emp_no, de.dept_no", EmpSalary.class);
        query.setParameter(1, dateFormat.format(fromDate));
        query.setParameter(2, dateFormat.format(fromDate));
        List<EmpSalary> empSalaries = query.getResultList();

        List<DeptSalary> deptSalaries = new ArrayList<>();
        Map<String, Long> deptSalaryMap = new HashMap<>();
        for (EmpSalary empSalary : empSalaries) {
            if (deptSalaryMap.containsKey(empSalary.getDeptNo())) {
                deptSalaryMap.put(empSalary.getDeptNo(), empSalary.getTotalSalary() + deptSalaryMap.get(empSalary.getDeptNo()));
            } else {
                deptSalaryMap.put(empSalary.getDeptNo(), empSalary.getTotalSalary());
            }
        }
        long companySalary = 0;

        for (String deptNo : deptSalaryMap.keySet()) {
            deptSalaries.add(new DeptSalary(deptNo, deptSalaryMap.get(deptNo)));
            companySalary += deptSalaryMap.get(deptNo);
        }

        CaculatedSalary caculatedSalary = new CaculatedSalary(empSalaries, deptSalaries, companySalary);
        return caculatedSalary;
    }

    @Override
    public CaculatedSalary getSalaryJDBC(Date fromDate) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = MyConnection.connect();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            preparedStatement = connection.prepareStatement("SELECT de.emp_no, de.dept_no, SUM(s.salary) AS total_salary FROM salaries AS s " + "INNER JOIN dept_emp AS de ON de.emp_no = s.emp_no " + "WHERE s.from_date > ? AND de.from_date > ? " + "GROUP BY de.emp_no, de.dept_no");
            java.sql.Date date = new java.sql.Date(fromDate.getTime());
            preparedStatement.setDate(1, date);
            preparedStatement.setDate(2, date);
            resultSet = preparedStatement.executeQuery();
            List<EmpSalary> employeeSalaries = new ArrayList<>();
            while (resultSet.next()) {
                employeeSalaries.add(new EmpSalary(resultSet.getInt("emp_no"), resultSet.getString("dept_no"), resultSet.getInt("total_salary")));
            }

            preparedStatement = connection.prepareStatement("SELECT a.dept_no, SUM(total_salary) AS dept_salary FROM " + "(SELECT de.emp_no, de.dept_no, SUM(s.salary) AS total_salary FROM salaries AS s " + "INNER JOIN dept_emp AS de ON de.emp_no = s.emp_no " + "WHERE s.from_date > ? AND de.from_date > ? " + "GROUP BY de.emp_no, de.dept_no) AS a " + "GROUP BY a.dept_no");
            preparedStatement.setDate(1, date);
            preparedStatement.setDate(2, date);
            resultSet = preparedStatement.executeQuery();
            List<DeptSalary> departmentSalaries = new ArrayList<>();
            while (resultSet.next()) {
                departmentSalaries.add(new DeptSalary(resultSet.getString("dept_no"), resultSet.getLong("dept_salary")));
            }

            preparedStatement = connection.prepareStatement("SELECT SUM(a.total_salary) AS company_salary FROM " + "(SELECT de.emp_no, de.dept_no, SUM(s.salary) AS total_salary FROM salaries AS s " + "INNER JOIN dept_emp AS de ON de.emp_no = s.emp_no " + "WHERE s.from_date > ? AND de.from_date > ? " + "GROUP BY de.emp_no, de.dept_no) AS a");
            preparedStatement.setDate(1, date);
            preparedStatement.setDate(2, date);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            CaculatedSalary caculatedSalary = new CaculatedSalary(employeeSalaries, departmentSalaries, resultSet.getLong("company_salary"));

            return caculatedSalary;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            close(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public List<Tax> getEmpTaxHibernate(String month, String year) {
        entityManager.createNativeQuery("LOCK TABLE salaries WRITE").executeUpdate();

        Query query = entityManager.createNativeQuery("SELECT emp_no, salary*10/100 AS tax FROM salaries " +
                "WHERE from_date LIKE ? OR to_date LIKE ? " +
                "GROUP BY emp_no", Tax.class);
        query.setParameter(1, year + "-" + month + "-%");
        query.setParameter(2, year + "-" + month + "-%");

        entityManager.createNativeQuery("UNLOCK TABLE").executeUpdate();

        List<Tax> taxes = query.getResultList();

        query = entityManager.createNativeQuery("SHOW tables like 'income_tax'");
        if (query.getResultList().size() == 0) {
            query = entityManager.createNativeQuery("CALL createTableTax()");
            query.executeUpdate();
        }

        for (Tax tax : taxes) {
            IncomeTax incomeTax = new IncomeTax(tax.getEmpNo(), tax.getTax(), Integer.parseInt(month), Integer.parseInt(year));
            entityManager.persist(incomeTax);
        }

        return taxes;
    }

    @Override
    public List<Tax> getEmpTaxJDBC(String month, String year) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = MyConnection.connect();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("LOCK TABLE salaries WRITE");
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("SELECT emp_no, salary*10/100 AS tax FROM salaries " +
                    "WHERE from_date LIKE ? OR to_date LIKE ? " +
                    "GROUP BY emp_no");

            preparedStatement.setString(1, year + "-" + month + "-%");
            preparedStatement.setString(2, year + "-" + month + "-%");
            resultSet = preparedStatement.executeQuery();
            List<Tax> taxes = new ArrayList<>();
            while (resultSet.next()) {
                taxes.add(new Tax(resultSet.getInt("emp_no"), resultSet.getInt("tax")));
            }

            preparedStatement = connection.prepareStatement("UNLOCK TABLE");
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("SHOW tables like 'income_tax'");
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                preparedStatement = connection.prepareStatement("CALL createTableTax()");
                preparedStatement.executeUpdate();
                connection.commit();
            }

            for (Tax tax : taxes) {
                IncomeTax incomeTax = new IncomeTax(tax.getEmpNo(), tax.getTax(), Integer.parseInt(month), Integer.parseInt(year));
                preparedStatement = connection.prepareStatement("INSERT INTO income_tax VALUES(?, ?, ?, ?)");
                preparedStatement.setInt(1, incomeTax.getEmpNo());
                preparedStatement.setInt(2, incomeTax.getTax());
                preparedStatement.setInt(3, incomeTax.getMonth());
                preparedStatement.setInt(4, incomeTax.getYear());
                preparedStatement.executeUpdate();
            }

            connection.commit();
            return taxes;
        } catch (SQLException | ClassNotFoundException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return null;
        } finally {
            close(connection, preparedStatement, resultSet);
        }
    }
}
