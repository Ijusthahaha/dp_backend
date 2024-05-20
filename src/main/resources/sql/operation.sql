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

 Date: 12/05/2024 18:09:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for operation
-- ----------------------------
DROP TABLE IF EXISTS `operation`;
CREATE TABLE `operation`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `module` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `request_param` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `response_param` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `user_id` int NULL DEFAULT NULL,
  `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `uri` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `version` int NULL DEFAULT NULL,
  `is_deleted` int NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
