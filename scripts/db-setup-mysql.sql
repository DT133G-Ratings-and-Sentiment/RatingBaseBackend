

CREATE DATABASE `reviews` /*!40100 DEFAULT CHARACTER SET utf16 COLLATE utf16_unicode_ci */;

USE reviews;

CREATE TABLE `adjective` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `word` varchar(45) COLLATE utf16_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `word_index` (`word`)
) ENGINE=InnoDB AUTO_INCREMENT=12816 DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

CREATE TABLE `reviews` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rating` int(11) DEFAULT NULL,
  `comment` varchar(2000) COLLATE utf16_unicode_ci DEFAULT NULL,
  `normalised_average_sentence_score` decimal(11,0) DEFAULT NULL,
  `normalised_median_sentence_score` decimal(11,0) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `rating_index` (`rating`),
  KEY `normalised_average_sentence_score_index` (`normalised_average_sentence_score`),
  KEY `normalised_median_sentence_score_index` (`normalised_median_sentence_score`),
  KEY `rating_to_normalised_average_sentence_score_index` (`rating`,`normalised_average_sentence_score`),
  KEY `rating_to_normalised_median_sentence_score_index` (`rating`,`normalised_median_sentence_score`)
) ENGINE=InnoDB AUTO_INCREMENT=449303 DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

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
  `normalised_score` decimal(11,0) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `reviews_id` (`reviews_id`),
  CONSTRAINT `sentence_ibfk_1` FOREIGN KEY (`reviews_id`) REFERENCES `reviews` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62855 DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;


CREATE TABLE `sentence_2_adjective` (
  `sentence_id` int(11) NOT NULL,
  `adjective_id` int(11) NOT NULL,
  `number_of_occurence` int(11) DEFAULT NULL,
  PRIMARY KEY (`sentence_id`,`adjective_id`),
  KEY `adjective_id` (`adjective_id`),
  CONSTRAINT `sentence_2_adjective_ibfk_1` FOREIGN KEY (`sentence_id`) REFERENCES `sentence` (`id`),
  CONSTRAINT `sentence_2_adjective_ibfk_2` FOREIGN KEY (`adjective_id`) REFERENCES `adjective` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

