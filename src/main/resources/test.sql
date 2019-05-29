/*
 Navicat Premium Data Transfer

 Source Server         : mysql-local
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 29/05/2019 11:21:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for condition
-- ----------------------------
DROP TABLE IF EXISTS `condition`;
CREATE TABLE `condition` (
  `conditionID` bigint(20) NOT NULL AUTO_INCREMENT,
  `mouldNum` varchar(200) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '模具号',
  `robotKind` varchar(200) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '机种',
  `machineNum` varchar(200) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '注塑机编号',
  `date` bigint(20) NOT NULL DEFAULT '0' COMMENT '日期',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '时间',
  `remark` varchar(2000) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '备注',
  `userID` bigint(200) NOT NULL DEFAULT '0' COMMENT '所属用户ID',
  `actionTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改时间',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`conditionID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `menuID` bigint(20) NOT NULL AUTO_INCREMENT,
  `menuName` varchar(200) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '菜单名称',
  `parentID` bigint(20) NOT NULL DEFAULT '0' COMMENT '父菜单ID',
  `menuLevel` tinyint(10) NOT NULL DEFAULT '0' COMMENT '菜单级别',
  `action` tinyint(10) NOT NULL DEFAULT '1' COMMENT '1-启用 2-禁用',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `actionTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改时间',
  PRIMARY KEY (`menuID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `recordID` bigint(20) NOT NULL AUTO_INCREMENT,
  `conditionID` bigint(20) NOT NULL DEFAULT '0' COMMENT '成型条件ID',
  `menuID` bigint(20) NOT NULL DEFAULT '0' COMMENT '菜单ID',
  `data` varchar(200) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '成型条件数据',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `actionTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改时间',
  PRIMARY KEY (`recordID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userID` bigint(20) NOT NULL AUTO_INCREMENT,
  `realname` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '真实姓名',
  `password` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '密码',
  `username` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '登陆账号',
  `isAdmin` tinyint(10) NOT NULL DEFAULT '1' COMMENT '1-普通用户 2-管理员',
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

SET FOREIGN_KEY_CHECKS = 1;
