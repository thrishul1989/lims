/*
Navicat MySQL Data Transfer

Source Server         : lims
Source Server Version : 50712
Source Host           : 192.168.12.24:3306
Source Database       : lims

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2016-08-30 16:56:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for LIMS_SAMPLE
-- ----------------------------
DROP TABLE IF EXISTS `LIMS_SAMPLE`;
CREATE TABLE `LIMS_SAMPLE` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '样本编号',
  `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '样本名称',
  `type` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '样本类型1-实验样本，2-非实验样本',
  `store_container` varchar(127) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '存储容器代码',
  `unit` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '计量单位',
  PRIMARY KEY (`id`),
  UNIQUE KEY `SAMPLE_CODE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of LIMS_SAMPLE
-- ----------------------------
INSERT INTO `LIMS_SAMPLE` VALUES ('24', 'F', '石蜡组织', '2', 'TBOX', '21');
INSERT INTO `LIMS_SAMPLE` VALUES ('25', 'C', '全血', '2', 'BX20', '2');
INSERT INTO `LIMS_SAMPLE` VALUES ('30', 'G', '基因组DNA', '1', 'BX20', '4');
INSERT INTO `LIMS_SAMPLE` VALUES ('32', 'R', '基因组RNA', '1', 'BX80', '4');
INSERT INTO `LIMS_SAMPLE` VALUES ('38', 'T', '新鲜组织', '2', 'BX20', '21');
INSERT INTO `LIMS_SAMPLE` VALUES ('39', 'S', '唾液', '2', 'BX20', '2');
INSERT INTO `LIMS_SAMPLE` VALUES ('40', 'U', '尿液', '2', 'BX20', '2');
INSERT INTO `LIMS_SAMPLE` VALUES ('41', 'Q', '其他', '2', 'BX20', '2');
INSERT INTO `LIMS_SAMPLE` VALUES ('42', 'P', '血浆', '2', 'BX20', '2');
INSERT INTO `LIMS_SAMPLE` VALUES ('44', 'D', '石蜡DNA', '2', 'BX20', '17');
INSERT INTO `LIMS_SAMPLE` VALUES ('45', 'B', '骨髓液', '2', 'BX20', '2');
INSERT INTO `LIMS_SAMPLE` VALUES ('47', 'E', '石蜡切片', '2', 'BX20', '17');
INSERT INTO `LIMS_SAMPLE` VALUES ('48', 'A', '羊水', '2', 'BX20', '2');
INSERT INTO `LIMS_SAMPLE` VALUES ('49', 'H', '细胞', '2', 'BX20', '4');
INSERT INTO `LIMS_SAMPLE` VALUES ('50', 'I', '福尔马林组织', '2', 'BX20', '4');
INSERT INTO `LIMS_SAMPLE` VALUES ('51', 'J', '血片', '2', 'BX20', '17');
INSERT INTO `LIMS_SAMPLE` VALUES ('52', 'K', '囊液', '2', 'BX20', '2');
