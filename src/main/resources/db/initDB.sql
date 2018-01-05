DROP TABLE IF EXISTS `tickets`;
DROP TABLE IF EXISTS `shows`;
DROP TABLE IF EXISTS `user_roles`;
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id`        INT         NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(40) NOT NULL,
  `lastname`  VARCHAR(40) NOT NULL,
  `email`     VARCHAR(40) NOT NULL UNIQUE,
  `password`  VARCHAR(40) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `user_roles`
(
  `user_id` INT NOT NULL,
  `role`    VARCHAR(40),
  CONSTRAINT user_roles_idx UNIQUE (`user_id`, `role`),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
    ON DELETE CASCADE
);

CREATE TABLE `shows` (
  `id`    INT         NOT NULL AUTO_INCREMENT,
  `day`   VARCHAR(9)  NOT NULL,
  `time`  VARCHAR(5)  NOT NULL,
  `movie` VARCHAR(40) NOT NULL,
  CONSTRAINT shows_idx UNIQUE (`day`, `time`),
  PRIMARY KEY (`id`)
);

CREATE TABLE `tickets` (
  `id`         INT    NOT NULL AUTO_INCREMENT,
  `row`        INT(2) NOT NULL,
  `seat`       INT(2) NOT NULL,
  `price`      INT    NOT NULL,
  `sold`       BOOL   NOT NULL DEFAULT '0',
  `user_id`    INT,
  `show_id` INT    NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT show_tickets_idx UNIQUE (`row`, `seat`, `show_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
    ON DELETE CASCADE,
  FOREIGN KEY (`show_id`) REFERENCES shows (`id`)
    ON DELETE CASCADE
);



