DROP TABLE IF EXISTS `tickets`;
DROP TABLE IF EXISTS `sessions`;
DROP TABLE IF EXISTS `user_roles`;
DROP TABLE IF EXISTS `roles`;
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id`        INT         NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(40) NOT NULL,
  `lastname`  VARCHAR(40) NOT NULL,
  `email`     VARCHAR(40) NOT NULL UNIQUE,
  `password`  VARCHAR(40) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `roles` (
  `id`          INT         NOT NULL AUTO_INCREMENT,
  `title`       VARCHAR(40) NOT NULL UNIQUE,
  `description` VARCHAR(255),
  PRIMARY KEY (`id`)
);

CREATE TABLE `user_roles`
(
  `user_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  CONSTRAINT user_roles_idx UNIQUE (`user_id`, `role_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
    ON DELETE CASCADE,
  FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
    ON DELETE CASCADE
);

CREATE TABLE `sessions` (
  `id`    INT         NOT NULL AUTO_INCREMENT,
  `day`   VARCHAR(9)  NOT NULL,
  `time`  VARCHAR(5)  NOT NULL,
  `movie` VARCHAR(40) NOT NULL,
  CONSTRAINT sessions_idx UNIQUE (`day`, `time`),
  PRIMARY KEY (`id`)
);

CREATE TABLE `tickets` (
  `id`         INT    NOT NULL AUTO_INCREMENT,
  `row`        INT(2) NOT NULL,
  `seat`       INT(2) NOT NULL,
  `price`      INT    NOT NULL,
  `sold`       BOOL   NOT NULL DEFAULT '0',
  `user_id`    INT,
  `session_id` INT    NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT session_tickets_idx UNIQUE (`row`, `seat`, `session_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
    ON DELETE CASCADE,
  FOREIGN KEY (`session_id`) REFERENCES `sessions` (`id`)
    ON DELETE CASCADE
);



