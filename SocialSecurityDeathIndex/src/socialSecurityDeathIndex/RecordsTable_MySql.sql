-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 02, 2014 at 10:02 PM
-- Server version: 5.5.35
-- PHP Version: 5.3.10-1ubuntu3.10

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `SSDI`
--

-- --------------------------------------------------------

--
-- Table structure for table `RECORDS`
--

CREATE TABLE IF NOT EXISTS `RECORDS` (
  `SSAN` int(11) NOT NULL,
  `LASTNAME` varchar(20) NOT NULL,
  `FIRSTNAME` varchar(15) NOT NULL,
  `MIDDLENAME` varchar(15) NOT NULL,
  `BIRTHBEGIN` datetime NOT NULL,
  `BIRTHEND` datetime NOT NULL,
  `DEATHBEGIN` datetime NOT NULL,
  `DEATHEND` datetime NOT NULL,
  PRIMARY KEY (`SSAN`)
) ENGINE=InnoDB DEFAULT CHARSET=ascii COMMENT='Master table of death records indexed by SSAN';

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
