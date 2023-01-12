-- --------------------------------------------------------
-- Hôte:                         127.0.0.1
-- Version du serveur:           10.4.20-MariaDB - mariadb.org binary distribution
-- SE du serveur:                Win64
-- HeidiSQL Version:             12.0.0.6468
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Listage de la structure de la base pour petpal_db
DROP DATABASE IF EXISTS `petpal_db`;
CREATE DATABASE IF NOT EXISTS `petpal_db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `petpal_db`;

-- Listage de la structure de table petpal_db. cat_breed
DROP TABLE IF EXISTS `cat_breed`;
CREATE TABLE IF NOT EXISTS `cat_breed` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `breed` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Listage des données de la table petpal_db.cat_breed : ~0 rows (environ)
INSERT IGNORE INTO `cat_breed` (`id`, `breed`) VALUES
    (1, 'BENGAL'),
    (2, 'NORVEGIEN'),
    (3, 'PERSAN'),
    (4, 'SIAMOIS'),
    (5, 'MUNCHKIN');

-- Listage de la structure de table petpal_db. pet_category
DROP TABLE IF EXISTS `pet_category`;
CREATE TABLE IF NOT EXISTS `pet_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- Listage des données de la table petpal_db.pet_category : ~0 rows (environ)
INSERT IGNORE INTO `pet_category` (`id`, `category`) VALUES
    (1, 'CHAT'),
    (2, 'CHIEN'),
    (3, 'LAPIN'),
    (4, 'HAMSTER'),
    (5, 'CHINCHILLA'),
    (6, 'OISEAU');

-- Listage de la structure de table petpal_db. cat
DROP TABLE IF EXISTS `cat`;
CREATE TABLE IF NOT EXISTS `cat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pet_id` int(11) NOT NULL,
  `cat_breed_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_cat_pet` (`pet_id`),
  KEY `FK_cat_cat_breed` (`cat_breed_id`),
  CONSTRAINT `FK_cat_pet` FOREIGN KEY (`pet_id`) REFERENCES `pet` (`id`),
  CONSTRAINT `FK_cat_cat_breed` FOREIGN KEY (`cat_breed_id`) REFERENCES `cat_breed` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Listage des données de la table petpal_db.cat : ~0 rows (environ)
INSERT IGNORE INTO `cat` (`id`, `pet_id`, `cat_breed_id`) VALUES
    (1, 1, 1),
    (2, 2, 2),
    (3, 3, 3),
    (4, 4, 4),
    (5, 5, 5);

-- Listage de la structure de table petpal_db. pet
DROP TABLE IF EXISTS `pet`;
CREATE TABLE IF NOT EXISTS `pet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `owner_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_pet_owner` (`owner_id`),
  CONSTRAINT `FK_pet_owner` FOREIGN KEY (`owner_id`) REFERENCES `owner` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Listage des données de la table petpal_db.pet : ~0 rows (environ)
INSERT IGNORE INTO `pet` (`id`, `name`, `birth_date`, `picture`, `owner_id`) VALUES
    (1, 'Eliot', '2017-11-16', 'pictures/bengal.jpg', 1),
    (2, 'Felix', '2016-07-05', 'pictures/norvegien.jpg', 1),
    (3, 'Garfield', '1978-06-19', 'pictures/persan.jpg', 2),
    (4, 'Azraël', '2018-12-02', 'pictures/siamois.jpg', 3),
    (5, 'Noisette', '2021-01-25', 'pictures/munchkin.jpg', 3);

-- Listage de la structure de table petpal_db. owner
DROP TABLE IF EXISTS `owner`;
CREATE TABLE IF NOT EXISTS `owner` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `login` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Listage des données de la table petpal_db.owner : ~3 rows (environ)
INSERT IGNORE INTO `owner` (`id`, `name`, `surname`, `login`, `password`) VALUES
	(1, 'Axel', 'Katz', 'a', 'a'),
	(2, 'Brad', 'Pitt', 'b', 'b'),
	(3, 'Mahatma', 'Gandhi', 'c', 'c');

-- Listage de la structure de table petpal_db. owner_pet_category
DROP TABLE IF EXISTS `owner_pet_category`;
CREATE TABLE IF NOT EXISTS `owner_pet_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `owner_id` int(11) DEFAULT NULL,
  `pet_category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_owner_pet_category_owner` (`owner_id`),
  KEY `FK_owner_pet_category_pet_category` (`pet_category_id`),
  CONSTRAINT `FK_owner_pet_category_owner` FOREIGN KEY (`owner_id`) REFERENCES `owner` (`id`),
  CONSTRAINT `FK_owner_pet_category_pet_category` FOREIGN KEY (`pet_category_id`) REFERENCES `pet_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- Listage des données de la table petpal_db.pet : ~0 rows (environ)
INSERT IGNORE INTO `owner_pet_category` (`id`, `owner_id`, `pet_category_id`) VALUES
    (1, 1, 1),
    (2, 1, 2),
    (3, 1, 6),
    (4, 2, 3),
    (5, 2, 4),
    (6, 3, 1),
    (7, 3, 3),
    (8, 3, 5);

-- Listage de la structure de table petpal_db. glossary
DROP TABLE IF EXISTS `glossary`;
CREATE TABLE IF NOT EXISTS `glossary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `expression` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Listage des données de la table petpal_db.pet : ~0 rows (environ)
INSERT IGNORE INTO `glossary` (`id`, `expression`) VALUES
    (1, 'Chat'),
    (2, 'Animal de compagnie'),
    (3, 'Croquette'),
    (4, 'Animal domestique'),
    (5, 'Animalerie');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
