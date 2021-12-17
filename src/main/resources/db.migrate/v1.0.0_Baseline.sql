create database my_hiit;
use my_hiit;

create table `exercises` (
  `id` int(11) NOT NULL AUTO_INCREMENT ,
  `name` varchar(80) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE(`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

create table `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT ,
  `name` varchar(80) NOT NULL,
  `email` varchar(80) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE(`name`),
  UNIQUE(`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

create table `workouts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(80) NOT NULL,
  `user_id` int(11) NOT NULL,
  `description` varchar(10000) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_id_user_id` (`id`,`user_id`),
  FOREIGN KEY(`user_id`) references `users`(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;