CREATE USER 'reststudentuser'@'localhost' IDENTIFIED BY 'zupassword';

GRANT ALL PRIVILEGES ON cs548_reststudentdb.* TO 'reststudentuser'@'localhost' WITH GRANT OPTION;

SHOW GRANTS FOR 'reststudentuser'@'localhost';
