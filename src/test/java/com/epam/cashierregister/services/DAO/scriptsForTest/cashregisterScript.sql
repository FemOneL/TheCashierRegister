SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema cash_register_test
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `cash_register_test` ;

-- -----------------------------------------------------
-- Schema cash_register_test
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cash_register_test` DEFAULT CHARACTER SET utf8 ;
USE `cash_register_test` ;

-- -----------------------------------------------------
-- Table `cash_register_test`.`authorize`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cash_register_test`.`authorize` ;

CREATE TABLE IF NOT EXISTS `cash_register_test`.`authorize` (
  `authorize_id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(100) NOT NULL,
  `password` VARCHAR(128) NOT NULL,
  PRIMARY KEY (`authorize_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cash_register_test`.`roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cash_register_test`.`roles` ;

CREATE TABLE IF NOT EXISTS `cash_register_test`.`roles` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(20) NOT NULL,
  UNIQUE INDEX `rolescol_UNIQUE` (`role` ASC) VISIBLE,
  PRIMARY KEY (`role_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cash_register_test`.`employee`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cash_register_test`.`employee` ;

CREATE TABLE IF NOT EXISTS `cash_register_test`.`employee` (
  `emp_id` INT NOT NULL AUTO_INCREMENT,
  `user_photo` VARCHAR(200) NOT NULL DEFAULT 'usersPhotos/nonuser.jpg',
  `firstname` VARCHAR(50) NOT NULL,
  `secondname` VARCHAR(50) NOT NULL,
  `role_id` INT NOT NULL,
  `auth_id` INT NOT NULL,
  PRIMARY KEY (`emp_id`, `auth_id`),
  UNIQUE INDEX `info_id_UNIQUE` (`emp_id` ASC) VISIBLE,
  INDEX `role_id_idx` (`role_id` ASC) VISIBLE,
  UNIQUE INDEX `authorize_id_UNIQUE` (`auth_id` ASC) VISIBLE,
  CONSTRAINT `auth_id`
    FOREIGN KEY (`auth_id`)
    REFERENCES `cash_register_test`.`authorize` (`authorize_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `role_id`
    FOREIGN KEY (`role_id`)
    REFERENCES `cash_register_test`.`roles` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cash_register_test`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cash_register_test`.`category` ;

CREATE TABLE IF NOT EXISTS `cash_register_test`.`category` (
  `category_id` INT NOT NULL AUTO_INCREMENT,
  `category` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`category_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cash_register_test`.`producer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cash_register_test`.`producer` ;

CREATE TABLE IF NOT EXISTS `cash_register_test`.`producer` (
  `producer_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`producer_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cash_register_test`.`goods`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cash_register_test`.`goods` ;

CREATE TABLE IF NOT EXISTS `cash_register_test`.`goods` (
  `goods_id` INT NOT NULL AUTO_INCREMENT,
  `model` VARCHAR(50) NOT NULL,
  `photo` VARCHAR(200) NOT NULL DEFAULT 'goodsPhotos/nopicture.png',
  `numbers` VARCHAR(45) NOT NULL,
  `cost` DECIMAL(10,2) NOT NULL,
  `category_id` INT NOT NULL,
  `producer_id` INT NOT NULL,
  PRIMARY KEY (`goods_id`),
  UNIQUE INDEX `goods_id_UNIQUE` (`goods_id` ASC) VISIBLE,
  INDEX `category_id_idx` (`category_id` ASC) VISIBLE,
  INDEX `producer_id_idx` (`producer_id` ASC) VISIBLE,
  UNIQUE INDEX `model_UNIQUE` (`model` ASC) VISIBLE,
  CONSTRAINT `category_id`
    FOREIGN KEY (`category_id`)
    REFERENCES `cash_register_test`.`category` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `producer_id`
    FOREIGN KEY (`producer_id`)
    REFERENCES `cash_register_test`.`producer` (`producer_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cash_register_test`.`check`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cash_register_test`.`check` ;

CREATE TABLE IF NOT EXISTS `cash_register_test`.`check` (
  `check_id` INT NOT NULL AUTO_INCREMENT,
  `employee_id` INT NOT NULL,
  `time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `total_cost` DECIMAL(10,2) NOT NULL,
  UNIQUE INDEX `check_id_UNIQUE` (`check_id` ASC) VISIBLE,
  PRIMARY KEY (`check_id`),
  INDEX `employee_id_idx` (`employee_id` ASC) VISIBLE,
  CONSTRAINT `employee_id`
    FOREIGN KEY (`employee_id`)
    REFERENCES `cash_register_test`.`employee` (`emp_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cash_register_test`.`check_has_goods`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cash_register_test`.`check_has_goods` ;

CREATE TABLE IF NOT EXISTS `cash_register_test`.`check_has_goods` (
  `check_id` INT NOT NULL,
  `goods_id` INT NOT NULL,
  `number_of_goods` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`check_id`, `goods_id`),
  INDEX `fk_check_has_goods_goods1_idx` (`goods_id` ASC) VISIBLE,
  CONSTRAINT `fk_check_has_goods_check1`
    FOREIGN KEY (`check_id`)
    REFERENCES `cash_register_test`.`check` (`check_id`)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_check_has_goods_goods1`
    FOREIGN KEY (`goods_id`)
    REFERENCES `cash_register_test`.`goods` (`goods_id`)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cash_register_test`.`selling`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cash_register_test`.`selling` ;

CREATE TABLE IF NOT EXISTS `cash_register_test`.`selling` (
  `selling_id` INT NOT NULL AUTO_INCREMENT,
  `number_of_checks` INT NOT NULL,
  `selling_sum` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`selling_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cash_register_test`.`return`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cash_register_test`.`return` ;

CREATE TABLE IF NOT EXISTS `cash_register_test`.`return` (
  `return_id` INT NOT NULL AUTO_INCREMENT,
  `number_of_returning` INT NOT NULL,
  `return_sum` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`return_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cash_register_test`.`report`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cash_register_test`.`report` ;

CREATE TABLE IF NOT EXISTS `cash_register_test`.`report` (
  `report_id` INT NOT NULL AUTO_INCREMENT,
  `selling_id` INT NOT NULL,
  `return_id` INT NOT NULL,
  `createdDate` TIMESTAMP NOT NULL,
  `date` TIMESTAMP NOT NULL,
  `profit` DECIMAL(10,2) NOT NULL,
  `senior_cashier_id` INT NOT NULL,
  PRIMARY KEY (`report_id`, `selling_id`, `return_id`),
  INDEX `senior_cashier_id_idx` (`senior_cashier_id` ASC) VISIBLE,
  INDEX `selling_id_idx` (`selling_id` ASC) VISIBLE,
  INDEX `return_id_idx` (`return_id` ASC) VISIBLE,
  CONSTRAINT `senior_cashier_id`
    FOREIGN KEY (`senior_cashier_id`)
    REFERENCES `cash_register_test`.`employee` (`emp_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `selling_id`
    FOREIGN KEY (`selling_id`)
    REFERENCES `cash_register_test`.`selling` (`selling_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `return_id`
    FOREIGN KEY (`return_id`)
    REFERENCES `cash_register_test`.`return` (`return_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

insert into roles values (default, 'CASHIER');
insert into roles values (default, 'SENIOR_CASHIER');
insert into roles values (default, 'COMMODITY_EXPERT');
insert into roles values (default, 'ADMIN');
-- fill authorize
insert into authorize values(default, 'tfemyak@gmail.com', '4bba5971ce3b67759e2a9de07a225d715ec53fe71e51b9bf285cd97841c734eba49b71cd81568e424c17fe3944abf32d1a8300c04c42c76450a3723be216dcd6');
insert into authorize values(default, 'vfemyak@epam.com', '1708b129d2dc66c3b53f3fa3567539b0d2fcc8e40bfde504e1c355b9fe338a8bc70689b9e0d74eded1f6bb936beda6edfc3d960553f78314d683e0de744685c9');
insert into authorize values(default, 'lyudazubr@ukr.net', 'b67bad762edd734840d4ace08e3036dd8af166cd6c216f18c3ddfe3c524fe7bc38ff5f6682966e82a7051223828713a0eb2c5ee02ad040f1dcddc3914f0e3abc');
insert into authorize values(default, 'admin@gmail.com', 'f9d3cf3377a4509094b37b67c8d7d3688095f7b0cd2a7dcb9a47975c8d778606e15b6063de11039fbea1074e43ab814971daa2c70fbb4e87cd12aa9204e1fad8');
-- fill employee
insert into employee values (default, default, 'Taras', 'Femiak', 1, 1);
insert into employee values (default, default,'Volodymyr', 'Femiak', 2, 2);
insert into employee values (default, default, 'Lyuda', 'Zubr', 3, 3);
insert into employee values (default, default, 'System', 'Admin', 4, 4);
-- fill category
insert into category values (default, 'TV');
insert into category values (default, 'Computers');
insert into category values (default, 'Phones');
-- fill producer
insert into producer values (default, 'samsung');
insert into producer values (default, 'xiaomi');
insert into producer values (default, 'soni');
-- fill goods
insert into cash_register_test.goods
values (default, 'iphone 13', 'goodsPhotos/nopicture.png', 24, 500.34, 3, 2);
insert into cash_register_test.goods
values (default, 'phil232',	'goodsPhotos/nopicture.png', 12, 233.00, 2, 3);