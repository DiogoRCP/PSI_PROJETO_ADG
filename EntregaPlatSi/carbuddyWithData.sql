-- MySQL dump 10.13  Distrib 5.7.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: carbuddy
-- ------------------------------------------------------
-- Server version	5.7.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `auth_assignment`
--

DROP TABLE IF EXISTS `auth_assignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auth_assignment` (
  `item_name` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `user_id` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `created_at` int(11) DEFAULT NULL,
  PRIMARY KEY (`item_name`,`user_id`),
  KEY `idx-auth_assignment-user_id` (`user_id`),
  CONSTRAINT `auth_assignment_ibfk_1` FOREIGN KEY (`item_name`) REFERENCES `auth_item` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_assignment`
--

LOCK TABLES `auth_assignment` WRITE;
/*!40000 ALTER TABLE `auth_assignment` DISABLE KEYS */;
INSERT INTO `auth_assignment` VALUES ('admin','1',1639000580),('client','17',1640790848),('collaborator','14',1639000592),('collaborator','15',1639000602),('collaborator','16',1639669248);
/*!40000 ALTER TABLE `auth_assignment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_item`
--

DROP TABLE IF EXISTS `auth_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auth_item` (
  `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `type` smallint(6) NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  `rule_name` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `data` blob,
  `created_at` int(11) DEFAULT NULL,
  `updated_at` int(11) DEFAULT NULL,
  PRIMARY KEY (`name`),
  KEY `rule_name` (`rule_name`),
  KEY `idx-auth_item-type` (`type`),
  CONSTRAINT `auth_item_ibfk_1` FOREIGN KEY (`rule_name`) REFERENCES `auth_rule` (`name`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_item`
--

LOCK TABLES `auth_item` WRITE;
/*!40000 ALTER TABLE `auth_item` DISABLE KEYS */;
INSERT INTO `auth_item` VALUES ('admin',1,NULL,NULL,NULL,1638290607,1638290607),('backendCrudCompany',2,'backendCrudCompany',NULL,NULL,1638290607,1638290607),('backendCrudContributor',2,'backendCrudContributor',NULL,NULL,1638290607,1638290607),('backendCrudUser',2,'backendCrudUser',NULL,NULL,1638290607,1638290607),('client',1,NULL,NULL,NULL,1638290607,1638290607),('collaborator',1,NULL,NULL,NULL,1638290607,1638290607),('frontendCrudRepair',2,'frontendCrudRepair',NULL,NULL,1638290607,1638290607),('frontendCrudSchedulesClient',2,'frontendCrudSchedulesClient',NULL,NULL,1638290607,1638290607),('frontendCrudSchedulesCollaborator',2,'frontendCrudSchedulesCollaborator',NULL,NULL,1638290607,1638290607),('frontendCrudVehicle',2,'frontendCrudVehicle',NULL,NULL,1638290607,1638290607),('frontendReadRepair',2,'frontendReadRepair',NULL,NULL,1638290607,1638290607);
/*!40000 ALTER TABLE `auth_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_item_child`
--

DROP TABLE IF EXISTS `auth_item_child`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auth_item_child` (
  `parent` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `child` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`parent`,`child`),
  KEY `child` (`child`),
  CONSTRAINT `auth_item_child_ibfk_1` FOREIGN KEY (`parent`) REFERENCES `auth_item` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `auth_item_child_ibfk_2` FOREIGN KEY (`child`) REFERENCES `auth_item` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_item_child`
--

LOCK TABLES `auth_item_child` WRITE;
/*!40000 ALTER TABLE `auth_item_child` DISABLE KEYS */;
INSERT INTO `auth_item_child` VALUES ('admin','backendCrudCompany'),('admin','backendCrudContributor'),('admin','backendCrudUser'),('admin','client'),('collaborator','client'),('collaborator','frontendCrudRepair'),('client','frontendCrudSchedulesClient'),('collaborator','frontendCrudSchedulesCollaborator'),('client','frontendCrudVehicle'),('client','frontendReadRepair');
/*!40000 ALTER TABLE `auth_item_child` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_rule`
--

DROP TABLE IF EXISTS `auth_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auth_rule` (
  `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `data` blob,
  `created_at` int(11) DEFAULT NULL,
  `updated_at` int(11) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_rule`
--

LOCK TABLES `auth_rule` WRITE;
/*!40000 ALTER TABLE `auth_rule` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_rule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cars`
--

DROP TABLE IF EXISTS `cars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cars` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `vin` varchar(100) NOT NULL,
  `brand` varchar(100) NOT NULL,
  `model` varchar(100) NOT NULL,
  `color` varchar(100) NOT NULL,
  `carType` varchar(100) NOT NULL,
  `displacement` float NOT NULL,
  `fuelType` varchar(100) NOT NULL,
  `registration` varchar(100) NOT NULL,
  `modelyear` year(4) NOT NULL,
  `kilometers` int(11) NOT NULL,
  `state` varchar(100) NOT NULL,
  `userId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_cars_vin` (`vin`),
  KEY `fk_cars_userId` (`userId`),
  CONSTRAINT `fk_cars_userId` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cars`
--

LOCK TABLES `cars` WRITE;
/*!40000 ALTER TABLE `cars` DISABLE KEYS */;
INSERT INTO `cars` VALUES (1,'WBAWC73578E067076','BMW','335xi','#000000','PASSENGER CAR',2979,'Gasoline','AC-06-FH',2008,12313,'Pending',1),(2,'1J4BA3H10AL171412','JEEP','Wrangler','#0602f2','MULTIPURPOSE PASSENGER VEHICLE (MPV)',3800,'Gasoline','AA-00-AA',2010,123123,'Pending',1),(3,'YV1SW61R021197119','VOLVO','V70','#935c5c','MULTIPURPOSE PASSENGER VEHICLE (MPV)',2435,'Gasoline','AC-48-FJ',2002,123123,'Pending',1),(4,'3A4FY48B56T247505','CHRYSLER','PT Cruiser','#502626','PASSENGER CAR',2400,'Gasoline','AC-06-FK',2006,123123,'Pending',14),(5,'JH4DB1540NS801082','ACURA','Integra','#ff0000','PASSENGER CAR',1800,'Gasoline','AA-99-AZ',1992,12314,'Accepted',1),(6,'JH4DB1550PS001358','ACURA','Integra','#000000','PASSENGER CAR',1800,'Gasoline','ZZ-AA-BG',1993,1231312321,'Accepted',1);
/*!40000 ALTER TABLE `cars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `companies`
--

DROP TABLE IF EXISTS `companies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `companies` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `companyname` varchar(100) NOT NULL,
  `nif` varchar(9) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phonenumber` varchar(40) NOT NULL,
  `registrationdate` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_companies_Nif` (`nif`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `companies`
--

LOCK TABLES `companies` WRITE;
/*!40000 ALTER TABLE `companies` DISABLE KEYS */;
INSERT INTO `companies` VALUES (1,'Banix','123123124','bmw@bmw.pt','912123123','2021-11-30 17:17:45'),(2,'Midas','123123156','midas@midas.pt','966186484','2021-11-30 17:19:07'),(23,'Mercedes','123456789','mercedes@gmail.com','966186487','2021-12-06 15:46:49');
/*!40000 ALTER TABLE `companies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contributors`
--

DROP TABLE IF EXISTS `contributors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contributors` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `speciality` varchar(100) NOT NULL,
  `companyId` int(10) unsigned NOT NULL,
  `userId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_contributors_userId` (`userId`),
  KEY `fk_contributors_companyId` (`companyId`),
  CONSTRAINT `fk_contributors_companyId` FOREIGN KEY (`companyId`) REFERENCES `companies` (`id`),
  CONSTRAINT `fk_contributors_userId` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contributors`
--

LOCK TABLES `contributors` WRITE;
/*!40000 ALTER TABLE `contributors` DISABLE KEYS */;
INSERT INTO `contributors` VALUES (3,'Mechanical',1,14),(4,'Mechanical',2,15),(5,'Electrician',23,16);
/*!40000 ALTER TABLE `contributors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `migration`
--

DROP TABLE IF EXISTS `migration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `migration` (
  `version` varchar(180) NOT NULL,
  `apply_time` int(11) DEFAULT NULL,
  PRIMARY KEY (`version`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `migration`
--

LOCK TABLES `migration` WRITE;
/*!40000 ALTER TABLE `migration` DISABLE KEYS */;
INSERT INTO `migration` VALUES ('m000000_000000_base',1638290602),('m130524_201442_init',1638290607),('m140506_102106_rbac_init',1638290604),('m170907_052038_rbac_add_index_on_auth_assignment_user_id',1638290604),('m180523_151638_rbac_updates_indexes_without_prefix',1638290604),('m190124_110200_add_verification_token_column_to_user_table',1638290607),('m200409_110543_rbac_update_mssql_trigger',1638290604),('m211102_131502_init_rbac',1638290607);
/*!40000 ALTER TABLE `migration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repairs`
--

DROP TABLE IF EXISTS `repairs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repairs` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `kilometers` int(11) NOT NULL,
  `repairdate` datetime DEFAULT CURRENT_TIMESTAMP,
  `repairdescription` varchar(100) NOT NULL,
  `state` varchar(100) NOT NULL,
  `repairtype` varchar(100) NOT NULL,
  `carId` int(10) unsigned NOT NULL,
  `contributorId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_repairs_carId` (`carId`),
  KEY `fk_repairs_contributorId` (`contributorId`),
  CONSTRAINT `fk_repairs_carId` FOREIGN KEY (`carId`) REFERENCES `cars` (`id`),
  CONSTRAINT `fk_repairs_contributorId` FOREIGN KEY (`contributorId`) REFERENCES `contributors` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repairs`
--

LOCK TABLES `repairs` WRITE;
/*!40000 ALTER TABLE `repairs` DISABLE KEYS */;
INSERT INTO `repairs` VALUES (2,12312,'2021-12-14 13:09:44','Mudança do Filtro do óleo','Under Repair','Repair',2,4),(3,123123,'2021-12-16 11:10:10','Mudança do Filtro do óleo','Repaired','Maintenance',3,4),(4,12331,'2021-12-16 11:10:19','Mudança do Filtro do óleo','Under Repair','Maintenance',5,4),(5,22313,'2021-12-16 11:10:33','3213','Repaired','Repair',1,3),(6,3213,'2021-12-16 11:10:41','Mudança do Filtro do óleo','Repaired','Maintenance',4,3);
/*!40000 ALTER TABLE `repairs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedules`
--

DROP TABLE IF EXISTS `schedules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedules` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `currentdate` datetime DEFAULT CURRENT_TIMESTAMP,
  `schedulingdate` datetime NOT NULL,
  `repairdescription` varchar(100) NOT NULL,
  `state` varchar(100) NOT NULL,
  `repairtype` varchar(100) NOT NULL,
  `carId` int(10) unsigned NOT NULL,
  `companyId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_schedules_carId` (`carId`),
  KEY `fk_schedules_companyId` (`companyId`),
  CONSTRAINT `fk_schedules_carId` FOREIGN KEY (`carId`) REFERENCES `cars` (`id`),
  CONSTRAINT `fk_schedules_companyId` FOREIGN KEY (`companyId`) REFERENCES `companies` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedules`
--

LOCK TABLES `schedules` WRITE;
/*!40000 ALTER TABLE `schedules` DISABLE KEYS */;
INSERT INTO `schedules` VALUES (1,'2021-11-30 17:27:36','2022-10-10 14:00:00','Maintenance','Accepted','Manuntencao',2,1),(2,'2021-11-30 17:28:19','2021-12-29 19:56:00','Maintenance','Pending','Test',4,2);
/*!40000 ALTER TABLE `schedules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `verification_token` text NOT NULL,
  `password_reset_token` varchar(255) DEFAULT NULL,
  `auth_key` varchar(32) NOT NULL,
  `status` smallint(6) NOT NULL DEFAULT '10',
  `updated_at` int(11) NOT NULL,
  `created_at` int(11) NOT NULL,
  `usertype` varchar(100) NOT NULL,
  `nif` varchar(9) NOT NULL,
  `birsthday` date NOT NULL,
  `email` varchar(255) NOT NULL,
  `phonenumber` varchar(40) NOT NULL,
  `registrationdate` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_users_Nif` (`nif`),
  UNIQUE KEY `uk_users_user` (`username`),
  UNIQUE KEY `uk_users_email` (`email`),
  UNIQUE KEY `uk_users_passwordresettoken` (`password_reset_token`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Goncalo','$2y$13$9xhIvL/N.YFLH6mramOBwu7arDerLmRVeC2JhVctUdZphvsk.Ybzq','XJe766xRVUZxXNo6ZZGNPGwSD7DyEZcC_1639000307',NULL,'NL1pWC3C2QNQdDLZWhFbTMuceMhjz7p7',10,1639000307,1639000307,'admin','132132132','2021-12-08','badalha81@gmail.com','966186487','2021-12-08 21:51:47'),(14,'Goncalo22','$2y$13$.NN89Wo69kHH9A3vLKjCpuEwVuz7kXQkLYPSwc/Lhvz/Gy9/P7it2','s6I4uBFko6A7aq1ssfYgSPZTR65vlzb9_1639000365',NULL,'5ft7TRO-AFQVty3EjGTSRnqkAjUbONKq',10,1639000365,1639000365,'collaborator','345543567','2021-12-08','badalha81@hotmail.com','966186487','2021-12-08 21:52:45'),(15,'Goncalo123','$2y$13$NNoHrMuzgFjYWUv0r9liVu.wOV1SeXPi1ldQqVJJ4LUgmnhgkp11i','K0DPUd-_3nQogYQZkfcOzkF1raKNw9av_1639000434',NULL,'IdPP_r8mHdxeOQals72tbNCLJjPcEWrY',10,1639000434,1639000434,'collaborator','321445098','2021-12-08','badalh81@gmail.com','966186487','2021-12-08 21:53:54'),(16,'Goncalo1234','$2y$13$d5XOC5Czl2RMMOOI9An5WOrjPxcHR7DPQ2hU6lDHWz5lVidNPUc7q','V-VqdjFOrjlsbHTRtE_LLr-UcuvrMHzt_1639669226',NULL,'60Si98cxQoBRkcUSjk4drckQDeAKPaZM',10,1640796030,1639669226,'collaborator','123123765','2021-12-16','goncalo1234@gmail.pt','912123123','2021-12-16 15:40:26'),(17,'goncalo12345678','$2y$13$04yntUq3/1qmlAomo.1UrOT7QRo9Sqzk.RuWWgQ.6ZyoB21n7Vzga','2Xd0zazfTZR60MsrJdK52vk62AMLHds7_1640790846',NULL,'4X6Omto1kbTLXR6H6wnBKp0Qb3PY1jK5',10,1640790846,1640790846,'client','123123000','2001-10-04','goncalo12345678@hotmail.com','912123098','2021-12-29 15:14:06');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-30 13:08:06
