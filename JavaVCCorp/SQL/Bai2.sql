USE employees;

UPDATE titles SET to_date = CURDATE() WHERE emp_no = 10002 AND to_date = '9999-01-01';
INSERT INTO titles VALUES(10002, 'Senior Staff', CURDATE(), '9999-01-01');

DELETE FROM departments WHERE dept_name = 'Production';

INSERT INTO departments VALUES('d010', 'Bigdata & ML');
INSERT INTO dept_manager VALUES('d010', 10173, CURDATE(), '9999-01-01');