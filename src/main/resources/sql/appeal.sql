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

 Date: 10/01/2024 20:40:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for appeal
-- ----------------------------
DROP TABLE IF EXISTS `appeal`;
CREATE TABLE `appeal`
(
    `appeal_id`     int                                                           NOT NULL AUTO_INCREMENT,
    `appeal_status` int                                                           NULL DEFAULT 0 COMMENT '0: rawAppeal | 1: pendingAppeal | 2: fulfilledAppeal | 3: rejectedAppeal',
    `appeal_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `version`       int                                                           NULL DEFAULT 1,
    `is_deleted`    int                                                           NULL DEFAULT 0,
    PRIMARY KEY (`appeal_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
