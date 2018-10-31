DROP TABLE IF EXISTS `LIMS_PRODUCT`;
CREATE TABLE `LIMS_PRODUCT` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '产品编号',
  `name` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '产品名称',
  `price` decimal(20,2) DEFAULT NULL COMMENT '产品定价￥',
  `cycle` int(11) DEFAULT NULL COMMENT '周期（天）',
  `detection_classfy` int(11) DEFAULT NULL COMMENT '检测分类',
  `quantity` int(11) DEFAULT NULL COMMENT '数据量',
  `probe_site` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '探针坐标',
  `primer_id` int(20) DEFAULT NULL COMMENT '引物',
  `analysis_method` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '分析方法',
  `test_method` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '检测方法',
  `state` int(4) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;