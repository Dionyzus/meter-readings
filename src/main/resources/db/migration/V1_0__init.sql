CREATE TABLE IF NOT EXISTS `device` (
  `device_id` bigint NOT NULL,
  PRIMARY KEY (`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `client` (
  `id` bigint NOT NULL,
  `client_city` varchar(255) DEFAULT NULL,
  `client_postal_code` varchar(255) DEFAULT NULL,
  `client_street_name` varchar(255) DEFAULT NULL,
  `device_id` bigint NOT NULL,
  `full_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `meter` (
  `id` bigint NOT NULL,
  `reading_time` date NOT NULL,
  `reading_value` double NOT NULL,
  `device_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3k6cix6hjunn497y25qh0huiv` (`device_id`),
  CONSTRAINT `FK3k6cix6hjunn497y25qh0huiv` FOREIGN KEY (`device_id`) REFERENCES `device` (`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;