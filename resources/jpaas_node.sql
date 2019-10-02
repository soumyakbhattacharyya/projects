/*
SQLyog - Free MySQL GUI v5.15
Host - 5.1.52 : Database - rundeckdb
*********************************************************************
Server version : 5.1.52
*/

SET NAMES utf8;

SET SQL_MODE='';

create database if not exists `rundeckdb`;

USE `rundeckdb`;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO';

/*Table structure for table `jpaas_node` */

DROP TABLE IF EXISTS `jpaas_node`;

CREATE TABLE `jpaas_node` (
  `idjpaas_node` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `hostname` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `osName` varchar(45) DEFAULT NULL,
  `osFamily` varchar(45) DEFAULT NULL,
  `osArch` varchar(45) DEFAULT NULL,
  `osVersion` varchar(45) DEFAULT NULL,
  `tags` varchar(100) NOT NULL,
  PRIMARY KEY (`idjpaas_node`)
) ENGINE=MyISAM AUTO_INCREMENT=90 DEFAULT CHARSET=latin1;

SET SQL_MODE=@OLD_SQL_MODE;