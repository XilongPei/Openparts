CREATE DATABASE IF NOT EXISTS `whisper`
    DEFAULT CHARACTER SET utf8
    DEFAULT COLLATE utf8_general_ci;

CREATE USER 'whisper'@'%' IDENTIFIED WITH mysql_native_password;
SET old_passwords = 0;
SET PASSWORD FOR 'whisper'@'%' = PASSWORD('password');

GRANT ALL ON whisper.* TO 'whisper'@'%';

USE `whisper`;

CREATE TABLE IF NOT EXISTS `developers` (
    `id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    `name` VARCHAR(127) NOT NULL,
    `locked` BIT NOT NULL DEFAULT 0,
    `login` VARCHAR(127) NOT NULL,
    `password` VARCHAR(64) NOT NULL,
    `contact_name` VARCHAR(63),
    `contact_phone` VARCHAR(31),
    `contact_email` VARCHAR(127),
    `website` VARCHAR(127),
    `register_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `last_modified` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX `idx_dev_login` (`login`)
) AUTO_INCREMENT=2048 ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `apps` (
    `iid` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    `id` VARCHAR(63) NOT NULL UNIQUE,
    `key` VARCHAR(63) NOT NULL,
    `dev_id` INTEGER UNSIGNED NOT NULL REFERENCES `developers`(`id`),
    `capabilities` INTEGER UNSIGNED DEFAULT NULL,
    `locked` BIT NOT NULL DEFAULT 0,
    `name` VARCHAR(63) NOT NULL,
    `description` VARCHAR(127),
    `contact_name` VARCHAR(63),
    `contact_phone` VARCHAR(31),
    `contact_email` VARCHAR(127),
    `register_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `last_modified` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX `idx_app_id` (`id`)
) AUTO_INCREMENT=2048 ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `users` (
    `iid` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    `id` VARCHAR(63) NOT NULL UNIQUE,
    `key` VARCHAR(63) NOT NULL,
    `locked` BIT NOT NULL DEFAULT 0,
    `login` VARCHAR(127) NOT NULL,
    `password` VARCHAR(64) NOT NULL,
    `name` VARCHAR(63),
    `description` VARCHAR(127),
    `gender` VARCHAR(31),
    `phone` VARCHAR(31),
    `email` VARCHAR(127),
    `region` VARCHAR(127),
    `avatar` BLOB,
    `register_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `last_modified` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX `idx_user_id` (`id`),
    INDEX `idx_user_login` (`login`)
) AUTO_INCREMENT=2048 ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `nodes` (
    `iid` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    `id` VARCHAR(63) NOT NULL UNIQUE,
    `key` VARCHAR(63) NOT NULL,
    `app_iid` INTEGER UNSIGNED NOT NULL REFERENCES `apps`(`iid`),
    `locked` BIT NOT NULL DEFAULT 0,
    `name` VARCHAR(63) NOT NULL,
    `description` VARCHAR(127),
    `version` VARCHAR(31),
    `device_id` VARCHAR(127),
    `real_device_id` VARCHAR(127),
    `device_type` VARCHAR(63),
    `manufacturer` VARCHAR(63),
    `model` VARCHAR(63),
    `os` VARCHAR(63),
    `os_version` VARCHAR(63),
    `register_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `last_modified` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX `idx_node_id` (`id`),
    INDEX `idx_node_device_id` (`device_id`)
) AUTO_INCREMENT=2048 ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `archive_nodes` (
    `iid` INTEGER UNSIGNED NOT NULL PRIMARY KEY,
    `id` VARCHAR(63) NOT NULL,
    `key` VARCHAR(63) NOT NULL,
    `app_iid` INTEGER UNSIGNED NOT NULL REFERENCES `apps`(`iid`),
    `locked` BIT NOT NULL DEFAULT 0,
    `name` VARCHAR(63) NOT NULL,
    `description` VARCHAR(127),
    `version` VARCHAR(31),
    `device_id` VARCHAR(127),
    `real_device_id` VARCHAR(127),
    `device_type` VARCHAR(63),
    `manufacturer` VARCHAR(63),
    `model` VARCHAR(63),
    `os` VARCHAR(63),
    `os_version` VARCHAR(63),
    `register_time` DATETIME,
    `last_modified` DATETIME,
    `archive_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX `idx_archive_node_device_id` (`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `relations` (
    `user_iid` INTEGER UNSIGNED NOT NULL REFERENCES `users`(`iid`),
    `friend_iid` INTEGER UNSIGNED NOT NULL REFERENCES `users`(`iid`),
    `entrusted` SMALLINT DEFAULT 0,
    `label` VARCHAR(63),
    `expire` DATETIME DEFAULT NULL,
    `request_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `response_time` DATETIME DEFAULT NULL,
    `last_modified` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY(`user_iid`, `friend_iid`),
    INDEX `idx_relation_user_iid` (`user_iid`),
    INDEX `idx_relation_friend_iid` (`friend_iid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `friend_requests` (
    `from_iid` INTEGER UNSIGNED NOT NULL REFERENCES  `users`(`iid`),
    `from_node_id` VARCHAR(63),
    `to_iid` INTEGER UNSIGNED NOT NULL REFERENCES  `users`(`iid`),
    `tid` BIGINT UNSIGNED NOT NULL,
    `hello` varchar(511),
    `request_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `delivered` BIT NOT NULL DEFAULT 0,
    PRIMARY KEY(`from_iid`, `to_iid`),
    INDEX `idx_pair_requests_to` (`to_iid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `signins` (
    `user_iid` INTEGER UNSIGNED NOT NULL REFERENCES `users`(`iid`),
    `node_iid` INTEGER UNSIGNED NOT NULL REFERENCES `nodes`(`iid`),
    `presence` VARCHAR(31),
    `signin_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `last_modified` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY(`user_iid`, `node_iid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `activities` (
    `user_iid` INTEGER UNSIGNED NOT NULL REFERENCES `users`(`iid`),
    `node_iid` INTEGER UNSIGNED NOT NULL REFERENCES `nodes`(`iid`),
    `timestamp` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `action` INTEGER,
    `success` BIT,
    `data` VARCHAR(255) DEFAULT NULL,
    INDEX `idx_activity_user_iid` (`user_iid`),
    INDEX `idx_activity_node_iid` (`node_iid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE VIEW signins_verbose AS
    SELECT u.`id` AS user_id, u.`key` AS user_key,
        u.`name` AS user_name, s.`user_iid` AS user_iid,
        u.`locked` AS user_locked,
        n.`id` AS node_id, n.`key` AS node_key, s.`node_iid` AS node_iid,
        n.`locked` AS node_locked,
        a.`id` AS app_id, a.`key` AS app_key, a.`iid` AS app_iid,
        a.`locked` AS app_locked,
        s.`signin_time`, s.`presence`
    FROM signins s
        LEFT OUTER JOIN users u ON s.`user_iid`=u.`iid`
        LEFT OUTER JOIN nodes n ON s.`node_iid`=n.`iid`
        LEFT OUTER JOIN apps a ON n.`app_iid`=a.`iid`;
