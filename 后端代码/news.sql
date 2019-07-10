/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 100137
 Source Host           : localhost:3306
 Source Schema         : news

 Target Server Type    : MySQL
 Target Server Version : 100137
 File Encoding         : 65001

 Date: 14/05/2019 13:05:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for collection
-- ----------------------------
DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection`  (
  `col_id` int(11) NOT NULL AUTO_INCREMENT,
  `col_nid` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `col_uid` int(11) DEFAULT NULL,
  `col_ntitle` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `col_date` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`col_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `c_nid` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `from_uid` int(11) DEFAULT NULL,
  `to_cid` int(11) DEFAULT 0,
  `c_date` datetime(0),
  `c_delete` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '0',
  PRIMARY KEY (`c_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for reading
-- ----------------------------
DROP TABLE IF EXISTS `reading`;
CREATE TABLE `reading`  (
  `r_id` int(11) NOT NULL AUTO_INCREMENT,
  `r_nid` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `r_ntitle` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `charnnelid` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  PRIMARY KEY (`r_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for thumbup
-- ----------------------------
DROP TABLE IF EXISTS `thumbup`;
CREATE TABLE `thumbup`  (
  `thu_id` int(11) NOT NULL AUTO_INCREMENT,
  `thu_nid` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `thu_uid` int(11) DEFAULT NULL,
  PRIMARY KEY (`thu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `u_id` int(11) NOT NULL AUTO_INCREMENT,
  `wname` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `sex` int(1) DEFAULT NULL,
  `age` int(3) DEFAULT NULL,
  `country` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `province` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `city` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `headimg` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `openid` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`u_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
