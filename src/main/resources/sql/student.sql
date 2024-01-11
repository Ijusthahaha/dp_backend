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

 Date: 10/01/2024 20:41:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`
(
    `student_uuid`     int                                                           NOT NULL AUTO_INCREMENT,
    `student_id`       int                                                           NOT NULL,
    `student_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `student_name`     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL,
    `student_class`    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL,
    `student_sex`      char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci      NULL DEFAULT NULL,
    `student_age`      int                                                           NULL DEFAULT NULL,
    `version`          int                                                           NULL DEFAULT 1,
    `is_deleted`       int                                                           NULL DEFAULT 0,
    PRIMARY KEY (`student_uuid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
