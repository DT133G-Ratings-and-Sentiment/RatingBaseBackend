

CREATE DATABASE `reviews` /*!40100 DEFAULT CHARACTER SET utf16 COLLATE utf16_unicode_ci */;

USE reviews;

CREATE TABLE `adjective` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `word` varchar(45) COLLATE utf16_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=154 DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

CREATE TABLE `reviews` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rating` int(11) DEFAULT NULL,
  `comment` varchar(1000) COLLATE utf16_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=429465 DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

CREATE TABLE `sentence` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(2000) COLLATE utf16_unicode_ci DEFAULT NULL,
  `very_positive` decimal(11,0) DEFAULT NULL,
  `positive` decimal(11,0) DEFAULT NULL,
  `neutral` decimal(11,0) DEFAULT NULL,
  `negative` decimal(11,0) DEFAULT NULL,
  `very_negative` decimal(11,0) DEFAULT NULL,
  `reviews_id` int(11) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

CREATE TABLE `sentence_2_adjective` (
  `sentence_id` int(11) NOT NULL,
  `adjective_id` int(11) NOT NULL,
  `number_of_occurence` int(11) DEFAULT NULL,
  PRIMARY KEY (`sentence_id`,`adjective_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;
