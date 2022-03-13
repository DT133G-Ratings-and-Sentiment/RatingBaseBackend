CREATE DATABASE `reviews` /*!40100 DEFAULT CHARACTER SET utf16 COLLATE utf16_unicode_ci */;

USE reviews;

CREATE TABLE `reviews` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rating` int(11) DEFAULT NULL,
  `comment` varchar(1000) COLLATE utf16_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=139958 DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;
