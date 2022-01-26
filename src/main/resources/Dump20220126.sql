-- MySQL dump 10.13  Distrib 8.0.12, for macos10.13 (x86_64)
--
-- Host: 127.0.0.1    Database: db_clinic
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `appointments`
--

DROP TABLE IF EXISTS `appointments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `appointments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `client_id` int NOT NULL,
  `doctor_id` int NOT NULL,
  `date` date NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `time` time DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `appointments_users_id_fk` (`client_id`),
  KEY `appointments_users_id_fk_2` (`doctor_id`),
  CONSTRAINT `appointments_users_id_fk` FOREIGN KEY (`client_id`) REFERENCES `users` (`id`),
  CONSTRAINT `appointments_users_id_fk_2` FOREIGN KEY (`doctor_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointments`
--

LOCK TABLES `appointments` WRITE;
/*!40000 ALTER TABLE `appointments` DISABLE KEYS */;
/*!40000 ALTER TABLE `appointments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'NURSE'),(2,'DENTIST'),(3,'THEER');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_name_uindex` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (69,'.'),(68,'0'),(66,'1'),(64,'123'),(30,'30'),(65,'33'),(1,'ADMIN'),(2,'CLIENT'),(67,'CLX'),(58,'CVXB'),(63,'DAS'),(3,'DOCTOR'),(51,'DSA'),(56,'F'),(78,'FD'),(76,'FDS'),(29,'KIM'),(39,'KIMANO'),(59,'KJHG'),(35,'KKK'),(71,'KL;'),(75,'QWE'),(70,'SECTA'),(34,'SSS'),(61,'XC'),(79,'XXX'),(57,'YTER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `birthday` date DEFAULT NULL,
  `second_name` varchar(255) NOT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `role_id` int NOT NULL,
  `category_id` int DEFAULT NULL,
  `room_number` int DEFAULT NULL,
  `password` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `users_role_id_fk` (`role_id`),
  CONSTRAINT `users_role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (NULL,'Adm',1,'Adm','',1,NULL,NULL,'12345','admin'),(NULL,'e_name',2,'m_name',NULL,2,NULL,111,'',''),(NULL,'d_name',3,'name_d',NULL,3,2,NULL,'',''),('2000-02-28','Inoski',4,'Lina','1023120',3,1,NULL,'ghosty','gETTER'),('0001-01-01','sec',7,'fir','123',65,NULL,222,'secret','terces'),(NULL,'rename',8,'rename','ds',1,NULL,NULL,'12','pika'),('1997-03-03','Tame',10,'Deelay','1337',57,NULL,NULL,'root','Oquestion'),('1899-01-22','Gex',11,'tttt','qqqq',29,NULL,NULL,'eeee','Rex'),('2012-09-15','Axe',12,'wwww','',67,NULL,NULL,'ffff','fireCloud'),('1999-01-08','Niko',13,'Pass','98389382',56,NULL,NULL,'snake','Kovac'),(NULL,'Cloud',16,'Cyclon','7777777',2,NULL,NULL,'Clock','Clear'),('2000-10-19','Lolipororu',17,'Old','666777',30,NULL,NULL,'dclinicb','Sai'),(NULL,'Vyneek',31,'Oleg','874321576',3,NULL,NULL,'tntcacke','Domestos'),('2000-01-08','Kiol',32,'Saito','00',34,NULL,NULL,'factoryno','Name'),('2000-01-01','Morgana',33,'Fata','90390303',29,NULL,304,'akamusic','maksym'),('2003-02-01','Joji',34,'Joji','0203',66,NULL,NULL,'22tt','Joke'),('2222-02-01','Der',35,'Der','',79,NULL,NULL,'','Name'),('2000-01-01','Lopster',36,'Lopster','2222',51,NULL,NULL,'21312','Key'),('1701-11-22','Vert',37,'Vert','134566',39,NULL,NULL,'777','Oqtopus'),('2006-12-21','Sale',38,'Sale','6647322987',59,NULL,NULL,'dajs213jdka','Flex'),('2002-10-17','Akina',39,'Akina','2318',78,NULL,NULL,'qwerty2002','Keo');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'db_clinic'
--

--
-- Dumping routines for database 'db_clinic'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-01-26 19:09:11
