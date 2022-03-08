USE employees;

SELECT * FROM employees AS e WHERE year(e.hire_date) >= 1999 LIMIT 10;

SELECT COUNT(*) FROM employees AS e WHERE e.gender = 'M' 
AND year(e.birth_date) >= 1950 AND year(e.birth_date) <= 1960
AND e.last_name LIKE 'MON%';

SELECT e.first_name, e.last_name, e.hire_date, SUM(s.salary) AS salary_total FROM salaries AS s
INNER JOIN titles AS t ON s.emp_no = t.emp_no
INNER JOIN employees AS e ON s.emp_no = e.emp_no
WHERE t.title = 'STAFF' AND s.from_date >= t.from_date 
AND s.to_date <= t.to_date AND t.emp_no = 10005;

SELECT COUNT(*) FROM dept_emp AS de
INNER JOIN dept_manager AS dm ON dm.dept_no = de.dept_no
INNER JOIN employees AS e ON dm.emp_no = e.emp_no
WHERE de.from_date >= dm.from_date AND de.from_date <= dm.to_date 
AND e.last_name = 'Markovitch' AND e.first_name = 'Margareta';

SELECT de.dept_no, SUM(s.salary) as total_salary FROM salaries AS s 
INNER JOIN dept_emp AS de ON de.emp_no = s.emp_no 
AND s.from_date >= '1988-06-25' AND s.to_date <= '1989-06-25'
GROUP BY de.dept_no
HAVING total_salary > 3000000
ORDER BY total_salary DESC;

SELECT a.emp_no, COUNT(a.title) AS count_dept 
FROM (SELECT DISTINCT t.emp_no, t.title FROM titles AS t) AS a 
GROUP BY a.emp_no HAVING count_dept >= 2;

SELECT t1.emp_no, t1.count_dept, t2.count_title FROM
(SELECT de.emp_no, COUNT(DISTINCT de.dept_no) AS count_dept FROM dept_emp AS de
GROUP BY de.emp_no
HAVING count_dept >= 2) AS t1
INNER JOIN
(SELECT t.emp_no, COUNT(DISTINCT t.title) AS count_title FROM titles AS t
GROUP BY t.emp_no
HAVING count_title >= 2) AS t2 ON t1.emp_no = t2.emp_no 
AND t1.count_dept >= 2 AND t2.count_title >= 2;

SELECT * FROM (SELECT s.emp_no, de.dept_no, s.salary, t.title, RANK() OVER(
	PARTITION BY de.dept_no
    ORDER BY s.salary DESC
) salary_rank
FROM salaries AS s
INNER JOIN titles AS t ON t.emp_no = s.emp_no AND s.from_date = t.from_date
INNER JOIN dept_emp AS de ON de.emp_no = s.emp_no
AND s.to_date = t.to_date AND t.title = 'Staff') AS r WHERE r.salary_rank IN(1,2,3,4,5);