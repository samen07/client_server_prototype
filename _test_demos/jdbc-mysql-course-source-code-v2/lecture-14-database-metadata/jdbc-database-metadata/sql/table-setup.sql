create database if not exists users;

use users;

CREATE TABLE if not exists `players` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `email` varchar(32) NOT NULL UNIQUE,
  `password` varchar(12) NOT NULL,
  `locked` boolean DEFAULT false,
  `balance` DECIMAL(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `players` (`email`, `password`, `balance`) 
VALUES ('john.doe@siabro.pp.ua', 'qwerty123', 100.00);
