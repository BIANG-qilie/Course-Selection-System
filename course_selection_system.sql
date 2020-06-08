/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : course_selection_system

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 29/05/2020 21:34:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `class_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `grade_id` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of class
-- ----------------------------
INSERT INTO `class` VALUES (1, '1班', 1);
INSERT INTO `class` VALUES (4, '1班', 3);
INSERT INTO `class` VALUES (5, '2班', 1);

-- ----------------------------
-- Table structure for faculty
-- ----------------------------
DROP TABLE IF EXISTS `faculty`;
CREATE TABLE `faculty`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `faculty_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `manager_user_id` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of faculty
-- ----------------------------
INSERT INTO `faculty` VALUES (1, '自动化学院', 2);
INSERT INTO `faculty` VALUES (4, '计算机学院', 8);

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `grade_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `faculty_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade` VALUES (1, '19届 物联网工程', 1);
INSERT INTO `grade` VALUES (2, '19届 计算机科学与技术', 4);
INSERT INTO `grade` VALUES (3, '19届 自动化', 1);
INSERT INTO `grade` VALUES (4, '19届 电气工程及其自动化', 1);

-- ----------------------------
-- Table structure for subject
-- ----------------------------
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `subject_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `start_time` time(0) NOT NULL,
  `stop_time` time(0) NOT NULL,
  `select_start_time` datetime(0) NOT NULL,
  `select_stop_time` datetime(0) NOT NULL,
  `day_of_week` int(0) NOT NULL,
  `belong` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `belong_id` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of subject
-- ----------------------------
INSERT INTO `subject` VALUES (1, '高等数学', '14:40:00', '16:15:00', '2020-05-25 00:00:00', '2020-06-25 23:59:59', 1, 'class', 1);
INSERT INTO `subject` VALUES (2, '大学物理实验', '14:00:00', '16:15:00', '2020-05-24 00:00:00', '2020-05-31 18:30:00', 2, 'faculty', 1);
INSERT INTO `subject` VALUES (4, '程序设计', '10:25:00', '12:00:00', '2020-05-24 15:51:47', '2020-05-24 15:54:53', 2, 'grade', 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `level` int(0) NOT NULL COMMENT '职务 1为学生 2为管理员 3为站长',
  `class_id` int(0) NOT NULL,
  `subject_id1` int(0) NOT NULL,
  `subject_id2` int(0) NOT NULL,
  `subject_id3` int(0) NOT NULL,
  `login_status` tinyint(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'BIANG', '123456', 3, 1, 1, 2, 0, 1);
INSERT INTO `user` VALUES (2, 'OMW', 'hanhan', 2, 4, 0, 0, 0, 0);
INSERT INTO `user` VALUES (6, 'Lyvhon', '123123123', 1, 4, 0, 0, 0, 0);
INSERT INTO `user` VALUES (8, '殊途', '123123', 2, 1, 0, 0, 0, 0);
INSERT INTO `user` VALUES (9, '克莱曼腾', '123123', 1, 1, 2, 0, 0, 0);

SET FOREIGN_KEY_CHECKS = 1;
