DELIMITER $	
CREATE PROCEDURE findByName (IN emp_name VARCHAR(30))
BEGIN
	SELECT e.emp_no, concat(e.last_name, ' ', e.first_name) AS name, t.title, de.dept_no, e.gender FROM employees AS e
    INNER JOIN titles AS t ON e.emp_no = t.emp_no
    INNER JOIN dept_emp AS de ON e.emp_no = de.emp_no AND e.first_name = emp_name;
    
    SELECT e.emp_no, concat(e.last_name, ' ', e.first_name) AS name, SUM(s.salary) AS total_salary FROM employees AS e 
    INNER JOIN salaries AS s ON e.emp_no = s.emp_no AND e.first_name = emp_name
    GROUP BY e.emp_no;
END;

CALL findByName('Georgi')

DROP PROCEDURE findByName
