/*
Navicat MySQL Data Transfer

Source Server         : lims
Source Server Version : 50712
Source Host           : 192.168.12.24:3306
Source Database       : lims

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2016-09-08 14:19:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for LIMS_ROLE
-- ----------------------------
DROP TABLE IF EXISTS `LIMS_ROLE`;
CREATE TABLE `LIMS_ROLE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of LIMS_ROLE
-- ----------------------------
INSERT INTO `LIMS_ROLE` VALUES ('1', '管理员');
INSERT INTO `LIMS_ROLE` VALUES ('2', '客户');
INSERT INTO `LIMS_ROLE` VALUES ('3', '取样员');
INSERT INTO `LIMS_ROLE` VALUES ('4', '销售');
INSERT INTO `LIMS_ROLE` VALUES ('5', '销售主管');
INSERT INTO `LIMS_ROLE` VALUES ('6', '片区经理');
INSERT INTO `LIMS_ROLE` VALUES ('7', '片区助理');
INSERT INTO `LIMS_ROLE` VALUES ('8', '销售助理');
INSERT INTO `LIMS_ROLE` VALUES ('9', '销售总监');
INSERT INTO `LIMS_ROLE` VALUES ('10', '市场总监');
INSERT INTO `LIMS_ROLE` VALUES ('11', '实验操作实验员');
INSERT INTO `LIMS_ROLE` VALUES ('12', '实验操作组长');
INSERT INTO `LIMS_ROLE` VALUES ('13', '平台负责人');
INSERT INTO `LIMS_ROLE` VALUES ('14', '实验室主管');
INSERT INTO `LIMS_ROLE` VALUES ('15', '项目管理员');
INSERT INTO `LIMS_ROLE` VALUES ('16', '财务开票人员');
INSERT INTO `LIMS_ROLE` VALUES ('17', '财务收费人员');
INSERT INTO `LIMS_ROLE` VALUES ('18', '财务主管');
INSERT INTO `LIMS_ROLE` VALUES ('19', '财务总监');
INSERT INTO `LIMS_ROLE` VALUES ('20', '设备管理员');
INSERT INTO `LIMS_ROLE` VALUES ('21', '试剂管理员');
INSERT INTO `LIMS_ROLE` VALUES ('22', '样本管理员');
