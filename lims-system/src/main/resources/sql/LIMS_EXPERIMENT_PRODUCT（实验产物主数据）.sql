/*
Navicat MySQL Data Transfer

Source Server         : lims
Source Server Version : 50712
Source Host           : 192.168.12.24:3306
Source Database       : lims

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2016-08-30 16:55:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for LIMS_EXPERIMENT_PRODUCT
-- ----------------------------
DROP TABLE IF EXISTS `LIMS_EXPERIMENT_PRODUCT`;
CREATE TABLE `LIMS_EXPERIMENT_PRODUCT` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '产物编号',
  `name` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '产物名称',
  `contain_type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '存储容器',
  `unit` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '单位',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of LIMS_EXPERIMENT_PRODUCT
-- ----------------------------
INSERT INTO `LIMS_EXPERIMENT_PRODUCT` VALUES ('2', 'LDC', '文库', 'BX20', '4');
INSERT INTO `LIMS_EXPERIMENT_PRODUCT` VALUES ('3', '0003', 'DNA', 'BX20', '4');
