-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: hellobill
-- ------------------------------------------------------
-- Server version	8.0.16

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
-- Table structure for table `class_info`
--

DROP TABLE IF EXISTS `class_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `class_info` (
  `uuid` varchar(36) NOT NULL,
  `topClass` varchar(8) NOT NULL,
  `secondClass` varchar(8) NOT NULL,
  `type` varchar(1) NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `secondClass_UNIQUE` (`secondClass`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_info`
--

LOCK TABLES `class_info` WRITE;
/*!40000 ALTER TABLE `class_info` DISABLE KEYS */;
INSERT INTO `class_info` VALUES ('00000000-0000-0000-0000-000000000000','日常支出','衣','0'),('00000000-0000-0000-0000-000000000001','日常支出','食','0'),('00000000-0000-0000-0000-000000000002','日常支出','住','0'),('00000000-0000-0000-0000-000000000003','日常支出','行','0'),('00000000-0000-0000-0000-000000000004','日常支出','用','0'),('00000000-0000-0000-0000-000000000005','日常支出','玩','0'),('00000000-0000-0000-0000-000000000006','日常支出','学','0'),('00000000-0000-0000-0001-000000000000','固定支出','房贷','0'),('00000000-0000-0000-0001-000000000001','固定支出','车贷','0'),('00000000-0000-0000-0001-000000000002','固定支出','医保','0'),('00000000-0000-0000-0001-000000000003','固定支出','社保','0'),('00000000-0000-0000-0001-000000000004','固定支出','公积金','0'),('00000000-0000-0001-0000-000000000000','收入','工资','1'),('00000000-0000-0001-0000-000000000001','收入','私活','1');
/*!40000 ALTER TABLE `class_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expend_info`
--

DROP TABLE IF EXISTS `expend_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `expend_info` (
  `uuid` varchar(36) NOT NULL,
  `username` varchar(20) NOT NULL,
  `expendTime` datetime NOT NULL,
  `topClass` varchar(8) NOT NULL,
  `secondClass` varchar(8) NOT NULL,
  `detail` varchar(100) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expend_info`
--

LOCK TABLES `expend_info` WRITE;
/*!40000 ALTER TABLE `expend_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `expend_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `income_info`
--

DROP TABLE IF EXISTS `income_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `income_info` (
  `uuid` varchar(36) NOT NULL,
  `username` varchar(20) NOT NULL,
  `incomeDate` date NOT NULL,
  `topClass` varchar(8) NOT NULL,
  `secondClass` varchar(8) NOT NULL,
  `detail` varchar(100) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `income_info`
--

LOCK TABLES `income_info` WRITE;
/*!40000 ALTER TABLE `income_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `income_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_info` (
  `username` varchar(20) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES ('admin','E10ADC3949BA59ABBE56E057F20F883E');
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-01 21:18:59
