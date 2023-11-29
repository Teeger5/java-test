-- MariaDB dump 10.19-11.1.2-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: wumpus
-- ------------------------------------------------------
-- Server version	11.0.2-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `gamestates`
--

DROP TABLE IF EXISTS `gamestates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gamestates` (
  `player` int(11) NOT NULL,
  `level` text NOT NULL,
  PRIMARY KEY (`player`),
  CONSTRAINT `gamestates_ibfk_1` FOREIGN KEY (`player`) REFERENCES `players` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gamestates`
--

LOCK TABLES `gamestates` WRITE;
/*!40000 ALTER TABLE `gamestates` DISABLE KEYS */;
INSERT INTO `gamestates` VALUES
(13,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Level size=\"9\"><Hero arrows=\"2\" direction=\"E\" hasGold=\"false\"/><Entities><Entity posX=\"1\" posY=\"4\">W</Entity><Entity posX=\"4\" posY=\"1\">W</Entity><Entity posX=\"7\" posY=\"9\">W</Entity><Entity posX=\"9\" posY=\"8\">W</Entity><Entity posX=\"5\" posY=\"1\">W</Entity><Entity posX=\"6\" posY=\"9\">W</Entity><Entity posX=\"9\" posY=\"7\">W</Entity><Entity posX=\"1\" posY=\"3\">W</Entity><Entity posX=\"6\" posY=\"1\">W</Entity><Entity posX=\"5\" posY=\"9\">W</Entity><Entity posX=\"9\" posY=\"6\">W</Entity><Entity posX=\"7\" posY=\"1\">W</Entity><Entity posX=\"4\" posY=\"9\">W</Entity><Entity posX=\"9\" posY=\"5\">W</Entity><Entity posX=\"2\" posY=\"1\">W</Entity><Entity posX=\"1\" posY=\"8\">W</Entity><Entity posX=\"9\" posY=\"4\">W</Entity><Entity posX=\"1\" posY=\"7\">W</Entity><Entity posX=\"3\" posY=\"9\">W</Entity><Entity posX=\"3\" posY=\"1\">W</Entity><Entity posX=\"1\" posY=\"6\">W</Entity><Entity posX=\"9\" posY=\"3\">W</Entity><Entity posX=\"1\" posY=\"5\">W</Entity><Entity posX=\"2\" posY=\"9\">W</Entity><Entity posX=\"1\" posY=\"1\">W</Entity><Entity posX=\"9\" posY=\"2\">W</Entity><Entity posX=\"1\" posY=\"9\">W</Entity><Entity posX=\"1\" posY=\"2\">W</Entity><Entity posX=\"8\" posY=\"1\">W</Entity><Entity posX=\"9\" posY=\"1\">W</Entity><Entity posX=\"9\" posY=\"9\">W</Entity><Entity posX=\"8\" posY=\"9\">W</Entity><Entity posX=\"5\" posY=\"4\">H</Entity></Entities></Level>'),
(16,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Level size=\"9\"><Hero arrows=\"2\" direction=\"E\" hasGold=\"false\"/><Entities><Entity posX=\"1\" posY=\"4\">W</Entity><Entity posX=\"4\" posY=\"1\">W</Entity><Entity posX=\"7\" posY=\"9\">W</Entity><Entity posX=\"9\" posY=\"8\">W</Entity><Entity posX=\"5\" posY=\"1\">W</Entity><Entity posX=\"6\" posY=\"9\">W</Entity><Entity posX=\"9\" posY=\"7\">W</Entity><Entity posX=\"1\" posY=\"3\">W</Entity><Entity posX=\"6\" posY=\"1\">W</Entity><Entity posX=\"5\" posY=\"9\">W</Entity><Entity posX=\"9\" posY=\"6\">W</Entity><Entity posX=\"7\" posY=\"1\">W</Entity><Entity posX=\"4\" posY=\"9\">W</Entity><Entity posX=\"9\" posY=\"5\">W</Entity><Entity posX=\"2\" posY=\"1\">W</Entity><Entity posX=\"1\" posY=\"8\">W</Entity><Entity posX=\"9\" posY=\"4\">W</Entity><Entity posX=\"1\" posY=\"7\">W</Entity><Entity posX=\"3\" posY=\"9\">W</Entity><Entity posX=\"3\" posY=\"1\">W</Entity><Entity posX=\"1\" posY=\"6\">W</Entity><Entity posX=\"9\" posY=\"3\">W</Entity><Entity posX=\"1\" posY=\"5\">W</Entity><Entity posX=\"2\" posY=\"9\">W</Entity><Entity posX=\"1\" posY=\"1\">W</Entity><Entity posX=\"9\" posY=\"2\">W</Entity><Entity posX=\"1\" posY=\"9\">W</Entity><Entity posX=\"1\" posY=\"2\">W</Entity><Entity posX=\"8\" posY=\"1\">W</Entity><Entity posX=\"9\" posY=\"1\">W</Entity><Entity posX=\"9\" posY=\"9\">W</Entity><Entity posX=\"8\" posY=\"9\">W</Entity><Entity posX=\"5\" posY=\"4\">H</Entity></Entities></Level>'),
(30,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Level size=\"6\"><Hero arrows=\"1\" direction=\"S\" hasGold=\"true\"/><Entities><Entity posX=\"1\" posY=\"1\">W</Entity><Entity posX=\"6\" posY=\"3\">W</Entity><Entity posX=\"3\" posY=\"6\">W</Entity><Entity posX=\"1\" posY=\"4\">W</Entity><Entity posX=\"4\" posY=\"1\">W</Entity><Entity posX=\"5\" posY=\"1\">W</Entity><Entity posX=\"6\" posY=\"1\">W</Entity><Entity posX=\"2\" posY=\"6\">W</Entity><Entity posX=\"1\" posY=\"3\">W</Entity><Entity posX=\"6\" posY=\"4\">W</Entity><Entity posX=\"1\" posY=\"2\">W</Entity><Entity posX=\"2\" posY=\"1\">W</Entity><Entity posX=\"6\" posY=\"6\">W</Entity><Entity posX=\"6\" posY=\"5\">W</Entity><Entity posX=\"1\" posY=\"6\">W</Entity><Entity posX=\"6\" posY=\"2\">W</Entity><Entity posX=\"3\" posY=\"1\">W</Entity><Entity posX=\"4\" posY=\"6\">W</Entity><Entity posX=\"1\" posY=\"5\">W</Entity><Entity posX=\"5\" posY=\"6\">W</Entity><Entity posX=\"3\" posY=\"2\">H</Entity></Entities></Level>'),
(31,'<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Level size=\"6\" startX=\"3\" startY=\"3\"><Hero arrows=\"1\" direction=\"S\" hasGold=\"false\"/><Entities><Entity posX=\"1\" posY=\"1\">W</Entity><Entity posX=\"6\" posY=\"3\">W</Entity><Entity posX=\"3\" posY=\"6\">W</Entity><Entity posX=\"1\" posY=\"4\">W</Entity><Entity posX=\"4\" posY=\"1\">W</Entity><Entity posX=\"5\" posY=\"1\">W</Entity><Entity posX=\"6\" posY=\"1\">W</Entity><Entity posX=\"2\" posY=\"6\">W</Entity><Entity posX=\"1\" posY=\"3\">W</Entity><Entity posX=\"6\" posY=\"4\">W</Entity><Entity posX=\"3\" posY=\"2\">G</Entity><Entity posX=\"1\" posY=\"2\">W</Entity><Entity posX=\"2\" posY=\"1\">W</Entity><Entity posX=\"6\" posY=\"6\">W</Entity><Entity posX=\"6\" posY=\"5\">W</Entity><Entity posX=\"1\" posY=\"6\">W</Entity><Entity posX=\"6\" posY=\"2\">W</Entity><Entity posX=\"3\" posY=\"1\">W</Entity><Entity posX=\"4\" posY=\"6\">W</Entity><Entity posX=\"1\" posY=\"5\">W</Entity><Entity posX=\"5\" posY=\"6\">W</Entity><Entity posX=\"3\" posY=\"2\">H</Entity></Entities></Level>');
/*!40000 ALTER TABLE `gamestates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `players`
--

DROP TABLE IF EXISTS `players`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `players` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `score` int(11) DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `players`
--

LOCK TABLES `players` WRITE;
/*!40000 ALTER TABLE `players` DISABLE KEYS */;
INSERT INTO `players` VALUES
(1,'lksjdf',0),
(2,'lésakdfj',0),
(3,'élaskdjf',0),
(4,'sdlfj',0),
(5,'séaldkfj',0),
(6,'sléakdfj',0),
(7,'sdflkj',0),
(9,'sdlkjf',0),
(10,'Hajime mashite',20),
(11,'Yoku doragon anime o mimasu',90),
(12,'Doragon ga suki desu',80),
(13,'Doragon ga arimasen',85),
(16,'Doragon ga arimasu ka',0),
(30,'Teszt',0),
(31,'Doragon raidaa',4);
/*!40000 ALTER TABLE `players` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-28 19:21:44
