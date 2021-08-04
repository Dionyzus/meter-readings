INSERT INTO `electric_device`.`device`
(`device_id`)
VALUES
(1),
(2);

INSERT INTO `electric_device`.`client`
(`id`,
`client_city`,
`client_postal_code`,
`client_street_name`,
`device_id`,
`full_name`)
VALUES
(1,
"City",
"10000",
"Street",
1,
"Test Name");

INSERT INTO `electric_device`.`meter`
(`id`,
`reading_time`,
`reading_value`,
`device_id`)
VALUES
(1,
"2021-06-01",
100.5,
1);

INSERT INTO `electric_device`.`meter`
(`id`,
`reading_time`,
`reading_value`,
`device_id`)
VALUES
(2,
"2021-07-01",
90.5,
1);

INSERT INTO `electric_device`.`meter`
(`id`,
`reading_time`,
`reading_value`,
`device_id`)
VALUES
(3,
"2021-08-01",
155.5,
1);
