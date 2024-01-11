/*
 Navicat Premium Data Transfer

 Source Server         : Local Database
 Source Server Type    : MySQL
 Source Server Version : 80034
 Source Host           : localhost:3306
 Source Schema         : dp_management

 Target Server Type    : MySQL
 Target Server Version : 80034
 File Encoding         : 65001

 Date: 10/01/2024 20:41:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`
(
    `log_id`       int                                                           NOT NULL AUTO_INCREMENT,
    `log_type`     varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL DEFAULT 'others',
    `log_location` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL,
    `dp`           int                                                           NOT NULL,
    `date`         datetime                                                      NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `appeal_id`    int                                                           NULL     DEFAULT NULL,
    `remark`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT 'why?',
    `student_id`   int                                                           NOT NULL COMMENT 'which student got?',
    `teacher_id`   int                                                           NULL     DEFAULT NULL COMMENT 'which teacher gave?',
    `version`      int                                                           NULL     DEFAULT 1,
    `is_deleted`   int                                                           NULL     DEFAULT 0,
    PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1599787009
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
