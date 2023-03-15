-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Feb 04, 2023 at 01:12 PM
-- Server version: 5.7.24
-- PHP Version: 8.0.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bank`
--

-- --------------------------------------------------------

--
-- Table structure for table `cards`
--

CREATE TABLE `cards` (
  `card_id` varchar(45) NOT NULL,
  `users_user_id` int(11) NOT NULL,
  `pay_systems_supported_pay_system_name` varchar(45) NOT NULL,
  `member_name` varchar(45) DEFAULT NULL,
  `expire_date` varchar(45) DEFAULT NULL,
  `cvv_code` varchar(45) DEFAULT NULL,
  `pin_code` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cards`
--

INSERT INTO `cards` (`card_id`, `users_user_id`, `pay_systems_supported_pay_system_name`, `member_name`, `expire_date`, `cvv_code`, `pin_code`) VALUES
('2023217755681337', 5, 'MIR', 'VALERY ZHMYSHENKO', '02 26', '228', '1337');

-- --------------------------------------------------------

--
-- Table structure for table `card_balance`
--

CREATE TABLE `card_balance` (
  `cards_card_id` varchar(45) NOT NULL,
  `users_user_id` int(11) NOT NULL,
  `balance` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `card_balance`
--

INSERT INTO `card_balance` (`cards_card_id`, `users_user_id`, `balance`) VALUES
('2023217755681337', 5, 5000);

-- --------------------------------------------------------

--
-- Table structure for table `logins`
--

CREATE TABLE `logins` (
  `users_user_id` int(11) NOT NULL,
  `user_login` varchar(45) DEFAULT NULL,
  `user_password` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `logins`
--

INSERT INTO `logins` (`users_user_id`, `user_login`, `user_password`) VALUES
(5, 'valerik228', '1337');

-- --------------------------------------------------------

--
-- Table structure for table `pay_systems`
--

CREATE TABLE `pay_systems` (
  `supported_pay_system_name` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `pay_systems`
--

INSERT INTO `pay_systems` (`supported_pay_system_name`) VALUES
('MASTER CARD'),
('MIR'),
('VISA');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `user_first_name` varchar(45) DEFAULT NULL,
  `user_last_name` varchar(45) DEFAULT NULL,
  `user_patronymic` varchar(45) DEFAULT NULL,
  `user_passport_data` varchar(45) DEFAULT NULL,
  `user_cellphone_number` varchar(45) DEFAULT NULL,
  `user_email` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `user_first_name`, `user_last_name`, `user_patronymic`, `user_passport_data`, `user_cellphone_number`, `user_email`) VALUES
(5, 'Valery', 'Zhmyshenko', 'Albertovich', '2022133722', '89009601337', 'valeraborov@yandex.ru');

--
-- Triggers `users`
--
DELIMITER $$
CREATE TRIGGER `auto_id` AFTER INSERT ON `users` FOR EACH ROW INSERT INTO logins (users_user_id) VALUES (new.user_id)
$$
DELIMITER ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cards`
--
ALTER TABLE `cards`
  ADD PRIMARY KEY (`card_id`),
  ADD KEY `fk_Cards_Pay_systems1_idx` (`pay_systems_supported_pay_system_name`),
  ADD KEY `fk_Cards_Users1_idx` (`users_user_id`);

--
-- Indexes for table `card_balance`
--
ALTER TABLE `card_balance`
  ADD PRIMARY KEY (`cards_card_id`),
  ADD KEY `fk_Card_Balance_Users1_idx` (`users_user_id`);

--
-- Indexes for table `logins`
--
ALTER TABLE `logins`
  ADD PRIMARY KEY (`users_user_id`);

--
-- Indexes for table `pay_systems`
--
ALTER TABLE `pay_systems`
  ADD PRIMARY KEY (`supported_pay_system_name`),
  ADD UNIQUE KEY `System_name_UNIQUE` (`supported_pay_system_name`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cards`
--
ALTER TABLE `cards`
  ADD CONSTRAINT `fk_Cards_Pay_systems1` FOREIGN KEY (`pay_systems_supported_pay_system_name`) REFERENCES `pay_systems` (`supported_pay_system_name`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Cards_Users1` FOREIGN KEY (`users_user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `card_balance`
--
ALTER TABLE `card_balance`
  ADD CONSTRAINT `fk_Card_Balance_Users1` FOREIGN KEY (`users_user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_card_balance_cards1` FOREIGN KEY (`cards_card_id`) REFERENCES `cards` (`card_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `logins`
--
ALTER TABLE `logins`
  ADD CONSTRAINT `fk_login_table_users1` FOREIGN KEY (`users_user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
