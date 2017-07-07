
CREATE SCHEMA if not exists `cs548_reststudentdb` ;

USE cs548_reststudentdb;

CREATE TABLE `cs548_reststudentdb`.`student` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(45) NOT NULL,
  `lastname` VARCHAR(45) NOT NULL,
  `age` SMALLINT ZEROFILL NULL,
  `enrolldate` DATE NOT NULL,
  PRIMARY KEY (`id`));
  
INSERT INTO `cs548_reststudentdb`.`student` (`id`, `firstname`, `lastname`, `age`, `enrolldate`) VALUES ('100', 'Bob', 'Watson', '24', '2015-10-29');
INSERT INTO `cs548_reststudentdb`.`student` (`id`, `firstname`, `lastname`, `age`, `enrolldate`) VALUES ('101', 'Joan', 'Bae', '25', '2015-04-17');
