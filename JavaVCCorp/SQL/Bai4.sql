DELIMITER $	
CREATE PROCEDURE changeDept (IN employee_no INT, IN new_title VARCHAR(50), IN new_dept CHAR(4))
BEGIN
	UPDATE dept_emp SET to_date = CURDATE() WHERE emp_no = employee_no AND to_date = '9999-01-01';
	INSERT INTO dept_emp VALUES(employee_no, new_dept, CURDATE(), '9999-01-01');
		
	UPDATE titles SET to_date = CURDATE() WHERE emp_no = employee_no AND to_date = '9999-01-01';
	INSERT INTO titles VALUES(employee_no, new_title, CURDATE(), '9999-01-01');

	SELECT e.emp_no, concat(e.last_name, ' ', e.first_name) AS fullname, e.gender, t.title, de.dept_no
	FROM employees AS e
	INNER JOIN dept_emp AS de ON e.emp_no = de.emp_no AND de.to_date = '9999-01-01' AND e.emp_no = employee_no
	INNER JOIN titles AS t ON e.emp_no = t.emp_no AND t.to_date = '9999-01-01';
END;

CALL changeDept(10012, 'Senior Staff', 'd010');

DROP PROCEDURE changeDept;