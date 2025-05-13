/*
SQLyog Community v13.3.0 (64 bit)
MySQL - 8.0.40 : Database - rmt_domaci
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`rmt_domaci` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `rmt_domaci`;

/*Table structure for table `korisnik` */

DROP TABLE IF EXISTS `korisnik`;

CREATE TABLE `korisnik` (
  `KorisnikID` int NOT NULL AUTO_INCREMENT,
  `KorisnickoIme` varchar(50) NOT NULL,
  `Mail` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Lozinka` varchar(50) NOT NULL,
  PRIMARY KEY (`KorisnikID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `korisnik` */

insert  into `korisnik`(`KorisnikID`,`KorisnickoIme`,`Mail`,`Lozinka`) values 
(1,'a','a@gmail.com','a'),
(2,'b','b@gmail.com','b'),
(3,'c','c@gmail.com','c'),
(4,'d','d@gmail.com','d');

/*Table structure for table `prijava` */

DROP TABLE IF EXISTS `prijava`;

CREATE TABLE `prijava` (
  `PrijavaID` int NOT NULL AUTO_INCREMENT,
  `DatumUlaska` date NOT NULL,
  `DatumIzlaska` date NOT NULL,
  `NacinPutovanja` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `StanovnikID` int DEFAULT NULL,
  PRIMARY KEY (`PrijavaID`),
  KEY `KorisnikID` (`StanovnikID`),
  CONSTRAINT `prijava_ibfk_1` FOREIGN KEY (`StanovnikID`) REFERENCES `stanovnik` (`StanovnikID`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `prijava` */

insert  into `prijava`(`PrijavaID`,`DatumUlaska`,`DatumIzlaska`,`NacinPutovanja`,`StanovnikID`) values 
(1,'2001-01-20','2001-02-20','Motocikl',1),
(9,'2025-12-11','2025-12-11','Autobus',1);

/*Table structure for table `stanovnik` */

DROP TABLE IF EXISTS `stanovnik`;

CREATE TABLE `stanovnik` (
  `StanovnikID` int NOT NULL AUTO_INCREMENT,
  `Ime` varchar(50) DEFAULT NULL,
  `Prezime` varchar(50) DEFAULT NULL,
  `JMBG` varchar(50) DEFAULT NULL,
  `BrojPasosa` varchar(50) DEFAULT NULL,
  `KorisnikID` int DEFAULT NULL,
  PRIMARY KEY (`StanovnikID`),
  KEY `KorisnikID` (`KorisnikID`),
  CONSTRAINT `stanovnik_ibfk_1` FOREIGN KEY (`KorisnikID`) REFERENCES `korisnik` (`KorisnikID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `stanovnik` */

insert  into `stanovnik`(`StanovnikID`,`Ime`,`Prezime`,`JMBG`,`BrojPasosa`,`KorisnikID`) values 
(1,'Ana','Anic','0101925111111','111111111',1),
(2,'Boba','Bobanic','0101002111111','222222222',2),
(3,'Ceca','Cecilic','0101003111111','333333333',3),
(4,'Deni','Devito','0101004111111','444444444',4),
(5,'Neprijavljen1','Neprijavljen1','0101005111111','555555555',NULL),
(6,'Neprijavljen2','Neprijavljen2','0101006111111','666666666',NULL),
(7,'Neprijavljen3','Neprijavljen3','0101007111111','777777777',NULL);

/*Table structure for table `stavkaprijave` */

DROP TABLE IF EXISTS `stavkaprijave`;

CREATE TABLE `stavkaprijave` (
  `prijavaid` int NOT NULL,
  `zemljaid` int NOT NULL,
  PRIMARY KEY (`prijavaid`,`zemljaid`),
  KEY `zemljaid` (`zemljaid`),
  CONSTRAINT `stavkaprijave_ibfk_1` FOREIGN KEY (`prijavaid`) REFERENCES `prijava` (`PrijavaID`),
  CONSTRAINT `stavkaprijave_ibfk_2` FOREIGN KEY (`zemljaid`) REFERENCES `zemlja` (`ZemljaID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `stavkaprijave` */

insert  into `stavkaprijave`(`prijavaid`,`zemljaid`) values 
(1,1),
(9,1),
(1,2),
(1,3),
(1,4);

/*Table structure for table `zemlja` */

DROP TABLE IF EXISTS `zemlja`;

CREATE TABLE `zemlja` (
  `ZemljaID` int NOT NULL AUTO_INCREMENT,
  `Naziv` varchar(50) NOT NULL,
  PRIMARY KEY (`ZemljaID`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `zemlja` */

insert  into `zemlja`(`ZemljaID`,`Naziv`) values 
(1,'Austrija'),
(2,'Belgija'),
(3,'Bugarska'),
(4,'Hrvatska'),
(5,'Kipar'),
(6,'Češka'),
(7,'Danska'),
(8,'Estonija'),
(9,'Finska'),
(10,'Francuska'),
(11,'Nemačka'),
(12,'Grčka'),
(13,'Mađarska'),
(14,'Irska'),
(15,'Italija'),
(16,'Letonija'),
(17,'Litvanija'),
(18,'Luksemburg'),
(19,'Malta'),
(20,'Holandija'),
(21,'Poljska'),
(22,'Portugalija'),
(23,'Rumunija'),
(24,'Slovačka'),
(25,'Slovenija'),
(26,'Španija '),
(27,'Švedska');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
