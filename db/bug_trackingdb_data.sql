-- MariaDB dump 10.19  Distrib 10.9.3-MariaDB, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: bug_trackingdb
-- ------------------------------------------------------
-- Server version	10.9.3-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `confirmation_tokens`
--

LOCK TABLES `confirmation_tokens` WRITE;
/*!40000 ALTER TABLE `confirmation_tokens` DISABLE KEYS */;
/*!40000 ALTER TABLE `confirmation_tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `projects`
--

LOCK TABLES `projects` WRITE;
/*!40000 ALTER TABLE `projects` DISABLE KEYS */;
INSERT INTO `projects` VALUES
(1,'Project 1','Project 1 description'),
(2,'Project 2 title','Project 2 description'),
(3,'Project 3 title','Project 3 description'),
(4,'Project 4 title','Project 4 description'),
(5,'Project 5 title','Project 5 description'),
(6,'Project 6 title','Project 6 description'),
(7,'Project 7 title','Project 7 description'),
(8,'Project 8 title','Project 8 description'),
(9,'Project 9 title','Project 9 description'),
(10,'Project 10 title','Project 10 description'),
(11,'Project 11 title','Project 11 description'),
(12,'Project 12 title','Project 12 description'),
(13,'Project 13 title','Project 13 description'),
(14,'Project 14 title','Project 14 description'),
(15,'Project 15 title','Project 15 description'),
(16,'Project 16 title','Project 16 description'),
(17,'Project 17 title','Project 17 description'),
(18,'Project 18 title','Project 18 description'),
(19,'Project 19 title','Project 19 description'),
(20,'Project 20 title','Project 20 description'),
(21,'Project 21 title','Project 21 description'),
(22,'Project 22 title','Project 22 description'),
(23,'Project 23 title','Project 23 description'),
(24,'Project 24 title','Project 24 description'),
(25,'Project 25 title','Project 25 description'),
(26,'Project 26 title','Project 26 description'),
(27,'Project 27 title','Project 27 description'),
(28,'Project 28 title','Project 28 description'),
(29,'Project 29 title','Project 29 description'),
(30,'Project 30 title','Project 30 description'),
(31,'Project 31 title','Project 31 description'),
(32,'Project 32 title','Project 32 description'),
(33,'Project 33 title','Project 33 description'),
(34,'Project 34 title','Project 34 description'),
(35,'Project 35 title','Project 35 description'),
(36,'Project 36 title','Project 36 description'),
(37,'Project 37 title','Project 37 description'),
(38,'Project 38 title','Project 38 description'),
(39,'Project 39 title','Project 39 description'),
(40,'Project 40 title','Project 40 description'),
(41,'Project 41 title','Project 41 description'),
(42,'Project 42 title','Project 42 description'),
(43,'Project 43 title','Project 43 description'),
(44,'Project 44 title','Project 44 description'),
(45,'Project 45 title','Project 45 description'),
(46,'Project 46 title','Project 46 description'),
(47,'Project 47 title','Project 47 description'),
(48,'Project 48 title','Project 48 description'),
(49,'Project 49 title','Project 49 description');
/*!40000 ALTER TABLE `projects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `projects_tickets`
--

LOCK TABLES `projects_tickets` WRITE;
/*!40000 ALTER TABLE `projects_tickets` DISABLE KEYS */;
/*!40000 ALTER TABLE `projects_tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tickets`
--

LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` VALUES
(1,'Ticket 1','Ticket 1 description','TRAINING_OR_DOCUMENT_REQUEST','MEDIUM','NEW','2022-08-21 04:32:28.000000',NULL,NULL,1,1),
(2,'Ticket 2','Ticket 2 description','BUG_OR_ERROR','HIGH','IN_PROGRESS','2022-09-08 10:28:00.000000',NULL,NULL,1,1),
(3,'Ticket 3','Ticket 3 description','BUG_OR_ERROR','HIGH','OPEN','2022-09-30 01:14:27.000000',NULL,NULL,1,1),
(4,'Ticket 4','Ticket 4 description','BUG_OR_ERROR','HIGH','RESOLVED','2022-09-21 18:11:55.000000',NULL,NULL,1,1),
(5,'Ticket 5','Ticket 5 description','FEATURE_REQUESTS','MEDIUM','IN_PROGRESS','2022-09-11 02:46:36.000000',NULL,NULL,1,1),
(6,'Ticket 6','Ticket 6 description','TRAINING_OR_DOCUMENT_REQUEST','HIGH','IN_PROGRESS','2022-08-07 16:43:03.000000',NULL,NULL,1,1),
(7,'Ticket 7','Ticket 7 description','FEATURE_REQUESTS','MEDIUM','NEW','2022-10-01 02:03:58.000000',NULL,NULL,1,1),
(8,'Ticket 8','Ticket 8 description','FEATURE_REQUESTS','MEDIUM','IN_PROGRESS','2022-08-27 15:03:50.000000',NULL,NULL,1,1),
(9,'Ticket 9','Ticket 9 description','TRAINING_OR_DOCUMENT_REQUEST','HIGH','OPEN','2022-08-13 14:50:22.000000',NULL,NULL,1,1),
(10,'Ticket 10','Ticket 10 description','BUG_OR_ERROR','MEDIUM','NEW','2022-09-30 08:10:59.000000',NULL,NULL,1,1),
(11,'Ticket 11','Ticket 11 description','TRAINING_OR_DOCUMENT_REQUEST','MEDIUM','NEW','2022-09-08 06:44:57.000000',NULL,NULL,1,1),
(12,'Ticket 12','Ticket 12 description','TRAINING_OR_DOCUMENT_REQUEST','LOW','RESOLVED','2022-09-01 14:35:04.000000',NULL,NULL,1,1),
(13,'Ticket 13','Ticket 13 description','TRAINING_OR_DOCUMENT_REQUEST','LOW','NEW','2022-08-01 22:20:01.000000',NULL,NULL,1,1),
(14,'Ticket 14','Ticket 14 description','BUG_OR_ERROR','HIGH','NEW','2022-08-14 04:11:49.000000',NULL,NULL,1,1),
(15,'Ticket 15','Ticket 15 description','TRAINING_OR_DOCUMENT_REQUEST','LOW','IN_PROGRESS','2022-09-20 19:03:53.000000',NULL,NULL,1,1),
(16,'Ticket 16','Ticket 16 description','FEATURE_REQUESTS','LOW','NEW','2022-08-29 12:55:42.000000',NULL,NULL,1,1),
(17,'Ticket 17','Ticket 17 description','FEATURE_REQUESTS','LOW','OPEN','2022-08-12 18:11:56.000000',NULL,NULL,1,1),
(18,'Ticket 18','Ticket 18 description','FEATURE_REQUESTS','MEDIUM','RESOLVED','2022-09-24 23:01:39.000000',NULL,NULL,1,1),
(19,'Ticket 19','Ticket 19 description','TRAINING_OR_DOCUMENT_REQUEST','MEDIUM','RESOLVED','2022-08-13 00:02:09.000000',NULL,NULL,1,1),
(20,'Ticket 20','Ticket 20 description','BUG_OR_ERROR','HIGH','IN_PROGRESS','2022-08-18 13:57:30.000000',NULL,NULL,1,1),
(21,'Ticket 21','Ticket 21 description','TRAINING_OR_DOCUMENT_REQUEST','MEDIUM','IN_PROGRESS','2022-08-23 07:26:42.000000',NULL,NULL,1,1),
(22,'Ticket 22','Ticket 22 description','BUG_OR_ERROR','HIGH','NEW','2022-08-18 12:47:00.000000',NULL,NULL,1,1),
(23,'Ticket 23','Ticket 23 description','FEATURE_REQUESTS','MEDIUM','NEW','2022-09-25 06:05:02.000000',NULL,NULL,1,1),
(24,'Ticket 24','Ticket 24 description','TRAINING_OR_DOCUMENT_REQUEST','HIGH','NEW','2022-09-23 09:52:14.000000',NULL,NULL,1,1),
(25,'Ticket 25','Ticket 25 description','BUG_OR_ERROR','LOW','RESOLVED','2022-09-29 00:37:06.000000',NULL,NULL,1,1),
(26,'Ticket 26','Ticket 26 description','FEATURE_REQUESTS','HIGH','RESOLVED','2022-08-24 04:41:37.000000',NULL,NULL,1,1),
(27,'Ticket 27','Ticket 27 description','FEATURE_REQUESTS','HIGH','OPEN','2022-09-26 01:27:22.000000',NULL,NULL,1,1),
(28,'Ticket 28','Ticket 28 description','BUG_OR_ERROR','MEDIUM','NEW','2022-10-01 14:29:22.000000',NULL,NULL,1,1),
(29,'Ticket 29','Ticket 29 description','BUG_OR_ERROR','MEDIUM','OPEN','2022-09-18 20:53:33.000000',NULL,NULL,1,1),
(30,'Ticket 30','Ticket 30 description','BUG_OR_ERROR','MEDIUM','OPEN','2022-09-15 15:07:11.000000',NULL,NULL,1,1),
(31,'Ticket 31','Ticket 31 description','FEATURE_REQUESTS','LOW','RESOLVED','2022-09-12 23:50:40.000000',NULL,NULL,1,1),
(32,'Ticket 32','Ticket 32 description','BUG_OR_ERROR','MEDIUM','RESOLVED','2022-09-25 20:27:17.000000',NULL,NULL,1,1),
(33,'Ticket 33','Ticket 33 description','FEATURE_REQUESTS','LOW','IN_PROGRESS','2022-08-03 07:36:18.000000',NULL,NULL,1,1),
(34,'Ticket 34','Ticket 34 description','TRAINING_OR_DOCUMENT_REQUEST','MEDIUM','OPEN','2022-08-10 09:12:00.000000',NULL,NULL,1,1),
(35,'Ticket 35','Ticket 35 description','BUG_OR_ERROR','LOW','NEW','2022-08-06 17:59:49.000000',NULL,NULL,1,1),
(36,'Ticket 36','Ticket 36 description','TRAINING_OR_DOCUMENT_REQUEST','MEDIUM','NEW','2022-09-05 13:50:10.000000',NULL,NULL,1,1),
(37,'Ticket 37','Ticket 37 description','TRAINING_OR_DOCUMENT_REQUEST','MEDIUM','OPEN','2022-08-03 14:33:35.000000',NULL,NULL,1,1),
(38,'Ticket 38','Ticket 38 description','TRAINING_OR_DOCUMENT_REQUEST','LOW','RESOLVED','2022-08-23 08:24:05.000000',NULL,NULL,1,1),
(39,'Ticket 39','Ticket 39 description','TRAINING_OR_DOCUMENT_REQUEST','LOW','OPEN','2022-09-30 22:12:34.000000',NULL,NULL,1,1),
(40,'Ticket 40','Ticket 40 description','BUG_OR_ERROR','HIGH','RESOLVED','2022-08-15 21:56:34.000000',NULL,NULL,1,1),
(41,'Ticket 41','Ticket 41 description','FEATURE_REQUESTS','LOW','IN_PROGRESS','2022-09-16 05:09:50.000000',NULL,NULL,1,1),
(42,'Ticket 42','Ticket 42 description','FEATURE_REQUESTS','LOW','RESOLVED','2022-09-15 10:01:16.000000',NULL,NULL,1,1),
(43,'Ticket 43','Ticket 43 description','FEATURE_REQUESTS','HIGH','NEW','2022-09-24 23:17:44.000000',NULL,NULL,1,1),
(44,'Ticket 44','Ticket 44 description','BUG_OR_ERROR','MEDIUM','NEW','2022-08-18 12:22:51.000000',NULL,NULL,1,1),
(45,'Ticket 45','Ticket 45 description','FEATURE_REQUESTS','HIGH','NEW','2022-08-07 04:00:10.000000',NULL,NULL,1,1),
(46,'Ticket 46','Ticket 46 description','BUG_OR_ERROR','MEDIUM','NEW','2022-09-29 01:59:09.000000',NULL,NULL,1,1),
(47,'Ticket 47','Ticket 47 description','TRAINING_OR_DOCUMENT_REQUEST','HIGH','RESOLVED','2022-08-22 13:15:32.000000',NULL,NULL,1,1),
(48,'Ticket 48','Ticket 48 description','TRAINING_OR_DOCUMENT_REQUEST','MEDIUM','OPEN','2022-08-05 08:20:46.000000',NULL,NULL,1,1),
(49,'Ticket 49','Ticket 49 description','FEATURE_REQUESTS','LOW','OPEN','2022-08-26 19:57:46.000000',NULL,NULL,1,1),
(50,'Ticket 50','Ticket 50 description','BUG_OR_ERROR','HIGH','IN_PROGRESS','2022-09-09 16:17:04.000000',NULL,NULL,1,1),
(51,'Ticket 51','Ticket 51 description','FEATURE_REQUESTS','HIGH','OPEN','2022-08-21 22:11:58.000000',NULL,NULL,1,1),
(52,'Ticket 52','Ticket 52 description','BUG_OR_ERROR','MEDIUM','IN_PROGRESS','2022-08-02 23:55:43.000000',NULL,NULL,1,1);
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES
(1,'youssef@mail.com','Youssef','Wadie','$2a$10$0aJdeOyY9bMQ8kFt7bYjQeTWEYBX9Rj50Pgd9GwFQwA8S.DTLynfG','ROLE_ADMIN',''),
(2,'user1@mail.com','Developer','1','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(3,'user2@mail.com','Developer','2','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(4,'user3@mail.com','Developer','3','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(5,'user4@mail.com','Developer','4','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(6,'user5@mail.com','Developer','5','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(7,'user6@mail.com','Developer','6','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(8,'user7@mail.com','Developer','7','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(9,'user8@mail.com','Developer','8','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(10,'user9@mail.com','Developer','9','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(11,'user10@mail.com','Developer','10','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(12,'user11@mail.com','Developer','11','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(13,'user12@mail.com','Developer','12','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(14,'user13@mail.com','Developer','13','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(15,'user14@mail.com','Developer','14','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(16,'user15@mail.com','Developer','15','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(17,'user16@mail.com','Developer','16','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(18,'user17@mail.com','Developer','17','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(19,'user18@mail.com','Developer','18','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(20,'user19@mail.com','Developer','19','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(21,'user20@mail.com','Developer','20','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(22,'user21@mail.com','Developer','21','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(23,'user22@mail.com','Developer','22','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(24,'user23@mail.com','Developer','23','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(25,'user24@mail.com','Developer','24','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(26,'user25@mail.com','Developer','25','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(27,'user26@mail.com','Developer','26','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(28,'user27@mail.com','Developer','27','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(29,'user28@mail.com','Developer','28','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(30,'user29@mail.com','Developer','29','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(31,'user30@mail.com','Developer','30','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(32,'user31@mail.com','Developer','31','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(33,'user32@mail.com','Developer','32','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(34,'user33@mail.com','Developer','33','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(35,'user34@mail.com','Developer','34','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(36,'user35@mail.com','Developer','35','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(37,'user36@mail.com','Developer','36','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(38,'user37@mail.com','Developer','37','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(39,'user38@mail.com','Developer','38','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(40,'user39@mail.com','Developer','39','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(41,'user40@mail.com','Developer','40','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(42,'user41@mail.com','Developer','41','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(43,'user42@mail.com','Developer','42','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(44,'user43@mail.com','Developer','43','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(45,'user44@mail.com','Developer','44','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(46,'user45@mail.com','Developer','45','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(47,'user46@mail.com','Developer','46','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(48,'user47@mail.com','Developer','47','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(49,'user48@mail.com','Developer','48','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(50,'user49@mail.com','Developer','49','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0'),
(51,'user50@mail.com','Developer','50','$2a$10$xpVz3iYAE1SWjENH/Q67NOy2P63C9WpMDFZhzfKFivgnyYHsVuYOC','ROLE_DEVELOPER','\0');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `works_on`
--

LOCK TABLES `works_on` WRITE;
/*!40000 ALTER TABLE `works_on` DISABLE KEYS */;
INSERT INTO `works_on` VALUES
(1,1),
(2,43),
(2,32),
(2,16),
(2,21),
(2,30),
(2,36),
(2,45),
(2,28),
(2,23),
(2,35),
(2,24),
(3,43),
(3,40),
(3,8),
(3,50),
(4,16),
(4,32),
(4,51),
(4,41),
(4,30),
(4,36),
(4,38),
(4,48),
(5,31),
(5,32),
(5,6),
(5,14),
(5,21),
(5,42),
(6,46),
(6,31),
(6,43),
(6,32),
(6,14),
(6,23),
(6,11),
(6,50),
(6,22),
(6,35),
(6,19),
(7,12),
(7,31),
(7,33),
(7,39),
(7,26),
(7,41),
(7,28),
(7,50),
(7,11),
(8,5),
(8,23),
(8,22),
(8,19),
(9,46),
(9,34),
(9,6),
(9,28),
(9,22),
(10,43),
(10,13),
(10,16),
(10,41),
(10,8),
(10,23),
(10,19),
(11,50),
(12,31),
(12,10),
(12,30),
(12,2),
(12,37),
(13,12),
(13,33),
(13,51),
(13,38),
(13,49),
(13,37),
(14,33),
(14,51),
(14,6),
(14,5),
(14,15),
(15,34),
(15,10),
(15,21),
(15,28),
(15,23),
(16,31),
(16,13),
(16,6),
(16,5),
(16,29),
(16,7),
(16,2),
(17,12),
(17,18),
(17,3),
(17,25),
(18,10),
(18,2),
(19,13),
(19,32),
(19,44),
(19,41),
(19,42),
(19,37),
(19,19),
(20,32),
(20,23),
(20,15),
(20,9),
(20,4),
(21,33),
(21,44),
(21,18),
(21,30),
(21,17),
(21,38),
(21,49),
(21,7),
(21,25),
(22,13),
(22,10),
(22,26),
(22,28),
(22,23),
(22,24),
(22,42),
(23,12),
(23,21),
(23,38),
(23,20),
(25,12),
(26,32),
(26,49),
(26,38),
(27,21),
(27,45),
(27,28),
(27,48),
(27,23),
(27,15),
(27,35),
(28,21),
(28,28),
(29,14),
(29,5),
(29,24),
(29,15),
(29,4),
(30,6),
(30,14),
(30,36),
(30,49),
(30,7),
(30,20),
(30,2),
(31,46),
(31,12),
(31,31),
(31,33),
(31,49),
(31,22),
(31,42),
(31,27),
(31,4),
(32,32),
(32,16),
(32,10),
(32,39),
(32,30),
(32,29),
(32,28),
(32,3),
(32,37),
(32,20),
(33,31),
(33,8),
(33,17),
(33,3),
(33,49),
(33,25),
(34,33),
(34,32),
(34,16),
(34,39),
(34,30),
(34,28),
(34,3),
(34,38),
(34,35),
(34,9),
(36,44),
(36,36),
(36,29),
(38,32),
(38,44),
(38,18),
(38,41),
(38,28),
(38,50),
(38,24),
(38,2),
(39,51),
(39,30),
(40,31),
(40,45),
(40,50),
(40,42),
(40,19),
(42,43),
(42,13),
(42,17),
(42,49),
(43,34),
(44,51),
(44,10),
(44,45),
(44,38),
(44,24),
(44,20),
(44,37),
(44,19),
(44,25),
(45,32),
(45,51),
(45,18),
(45,41),
(45,5),
(45,36),
(45,17),
(45,11),
(45,22),
(45,42),
(46,20),
(47,31),
(47,21),
(47,3),
(47,50),
(47,11),
(47,19),
(47,2),
(48,46),
(48,32),
(48,13),
(48,30),
(48,3),
(48,49),
(48,42),
(48,19),
(49,45),
(49,48),
(49,22),
(49,20);
/*!40000 ALTER TABLE `works_on` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-02 20:05:50
