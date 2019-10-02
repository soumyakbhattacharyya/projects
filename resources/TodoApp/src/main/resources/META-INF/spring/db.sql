/*
SQLyog - Free MySQL GUI v5.15
Host - 5.1.52 : Database - todorepo
*********************************************************************
Server version : 5.1.52
*/

SET NAMES utf8;

SET SQL_MODE='';

create database if not exists `todorepo`;

USE `todorepo`;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO';

/*Table structure for table `todo` */

DROP TABLE IF EXISTS `todo`;

CREATE TABLE `todo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `todo_long_desc` varchar(255) DEFAULT NULL,
  `todo_short_desc` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

SET SQL_MODE=@OLD_SQL_MODE;