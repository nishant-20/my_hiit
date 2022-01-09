create database if not exists `my_hiit`;
use my_hiit;

drop table if exists `EXERCISE`;
create table `EXERCISE` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(80) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE(`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

drop table if exists `USER`;
create table `USER` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(80) COLLATE utf8_bin NOT NULL,
  `email` varchar(80) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE(`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

drop table if exists `WORKOUT`;
create table `WORKOUT` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(80) COLLATE utf8_bin NOT NULL,
  `description` varchar(10000) COLLATE utf8_bin NOT NULL,
  `trending` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE(`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

drop table if exists `WORKOUT_USER_REL`;
create table `WORKOUT_USER_REL` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `workout_id` int(11) UNSIGNED NOT NULL,
  `user_id` int(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_workout_user` (`workout_id`,`user_id`),
  CONSTRAINT `constraint_workout_user_rel_workout_fk`
    FOREIGN KEY `workout_fk` (`workout_id`) references `WORKOUT` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `constraint_workout_user_rel_user_fk`
    FOREIGN KEY `user_fk` (`user_id`) references `USER` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

drop table if exists `WORKOUT_HISTORY`;
create table `WORKOUT_HISTORY` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(11) UNSIGNED NOT NULL,
  `name` varchar(80) COLLATE utf8_bin NOT NULL,
  `description` varchar(10000) COLLATE utf8_bin NOT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_user_created_time` (`user_id`,`created_time`),
  CONSTRAINT `constraint_workout_history_user_fk`
    FOREIGN KEY `user_fk` (`user_id`) references `USER` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;