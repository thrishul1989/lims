INSERT INTO `LIMS_DICT` VALUES ('42', null, 'PAY_STATE', '付费状态', null, null, null);
INSERT INTO `LIMS_DICT` VALUES ('4201', '42', null, '未付费', '0', '1', null);
INSERT INTO `LIMS_DICT` VALUES ('4202', '42', null, '已付费', '1', '2', null);
alter table LIMS_SAMPLE_ORDER add payState varchar(128) DEFAULT '0' COMMENT '订单付费状态(0:未付费;1:已付费)';


--待执行 宋健 2016年9月23日16:43:22

CREATE TABLE `LIMS_USER_INFORM` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(128)  NOT NULL COMMENT '标题',
  `CONTENT` varchar(2500)  NOT NULL COMMENT '内容',
  `STATE` int(1) NOT NULL COMMENT '0-未阅 1-已阅',
  `INFOTIME` varchar(128) DEFAULT NULL COMMENT '通知时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--待执行 宋健 2016年9月23日18:16:12
INSERT INTO `LIMS_DICT` VALUES ('39', null, 'INFORM_STATE', '通知状态', null, null, null);
INSERT INTO `LIMS_DICT` VALUES ('3901', '39', null, '未阅', '0', '1', null);
INSERT INTO `LIMS_DICT` VALUES ('3902', '39', null, '已阅', '1', '2', null);

--待执行 宋健 2016年9月23日18:16:14
INSERT INTO `LIMS_DICT` VALUES ('40', null, 'CHARGE_TYPE', '付款方式', null, null, null);
INSERT INTO `LIMS_DICT` VALUES ('4001', '40', null, '银行转账', '1', '1', null);
INSERT INTO `LIMS_DICT` VALUES ('4002', '40', null, '支付宝', '2', '2', null);
INSERT INTO `LIMS_DICT` VALUES ('4003', '40', null, 'APP支付', '3', '2', null);

INSERT INTO `LIMS_DICT` VALUES ('41', null, 'CHARGE_STATE', '付款状态', null, null, null);
INSERT INTO `LIMS_DICT` VALUES ('4101', '41', null, '未登记', '0', '1', null);
INSERT INTO `LIMS_DICT` VALUES ('4102', '41', null, '已登记', '1', '2', null);


INSERT INTO `LIMS_MENU` VALUES ('0305', '03', '收费登记', '/charge/chargeList.do', '5', ' fa-cubes');
--待执行 宋健 2016年9月23日18:16:16
alter table LIMS_SAMPLE_ORDER add chargeType varchar(128) DEFAULT NULL COMMENT '付款方式';
alter table LIMS_SAMPLE_ORDER add chargePrice  double(11,2) DEFAULT NULL COMMENT '付款金额';
alter table LIMS_SAMPLE_ORDER add chargeTime datetime DEFAULT NULL COMMENT '收款时间';
alter table LIMS_SAMPLE_ORDER add chargePerson varchar(128) DEFAULT NULL COMMENT '收款人姓名';
alter table LIMS_SAMPLE_ORDER add chargeState varchar(128) DEFAULT '0' COMMENT '订单收款状态(0:未登记;1已登记)';

-- (yi执行)update 2016-9.20 by 宋健  检测方法关联探针补充质量
alter table LIMS_CHECKMANAGER_PROBE add UNIT varchar(128) DEFAULT NULL COMMENT '数量单位';
alter table LIMS_CHECKMANAGER_PROBE add QUALITY_SIZE  double(11,2) DEFAULT NULL COMMENT '质量';
alter table LIMS_CHECKMANAGER_PROBE add QUALITY_UNIT varchar(128) DEFAULT NULL COMMENT '质量单位';

alter table LIMS_CHECKMANAGER_PROBE modify  DATA_SIZE double(11,2);

-- (已执行)解决bug -删除数据字典重复数据 宋健 2016年9月21日10:08:23
delete from LIMS_DICT  where ID ='18' or PARENT_ID='18';

--yi执行 update 2016年9月21日10:16:47 宋健 创建生产商表
CREATE TABLE `LIMS_PRODUCTER_MANAGE` (
  `ID` int(64) NOT NULL AUTO_INCREMENT,
  `PRODUCTER_NO` varchar(128) DEFAULT NULL COMMENT '生产商编号',
  `NAME` varchar(128) DEFAULT NULL COMMENT '名称',
  `STATE` varchar(128) DEFAULT NULL COMMENT '状态',
  `PHONE` varchar(128) DEFAULT NULL COMMENT '电话',
  `EMAIL` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `ADDRESS` varchar(128) DEFAULT NULL COMMENT '地址',
  `WEBSITE` varchar(128) DEFAULT NULL COMMENT '网址',
  `DESCRIPTION` varchar(128) DEFAULT NULL COMMENT '描述',
  `CONTACT_NAME` varchar(128) DEFAULT NULL COMMENT '联系人',
  `CONTACT_PHONE` varchar(128) DEFAULT NULL COMMENT '联系人电话',
  `CONTACT_EMAILS` varchar(128) DEFAULT NULL COMMENT '联系人邮箱',
  `PROVINCE` varchar(128) DEFAULT NULL COMMENT '省',
  `CITY` varchar(128) DEFAULT NULL COMMENT '市',
  `AREA` varchar(128) DEFAULT NULL COMMENT '县，区',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

--(yi执行) update 2016-9.19 by 宋健  增加探针质量、单位字段
alter table LIMS_PROBE add QUALITY_SIZE double(11,2) DEFAULT NULL COMMENT '质量';
alter table LIMS_PROBE add QUALITY_UNIT varchar(32) DEFAULT NULL COMMENT '质量单位';

--(yi执行) update 2016-9.19 by 宋健   创建分析方法表
CREATE TABLE `LIMS_ANALYZE_METHOD` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL  COMMENT '名称',
  `coordinate` varchar(500) DEFAULT NULL COMMENT '坐标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


--(yi执行) update 2016-9.19 by 宋健   创建检测方法关联探针表
CREATE TABLE `LIMS_CHECKMANAGER_PROBE` (
  `ID` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `PROBE_ID` int(20) DEFAULT NULL COMMENT '探针id',
  `DATA_SIZE` int(20) DEFAULT NULL COMMENT '探针数量',
  `PROBE_COMPANY` int(5) DEFAULT NULL COMMENT '探针单位',
  `CHECKMANAGEMENT_ID` int(50) DEFAULT NULL COMMENT '关联检测方法',
  `PROBE_NAME` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


-- (yi执行) update  2016-9.19 by 宋健  检测方法关联分析方法
alter table LIMS_CHECK_MANAGEMENT add ANALYZE_METHOD_ARRAY varchar(128) DEFAULT NULL COMMENT '关联分析方法';












-- update 2016-9.12 by 黄文涛 修改菜单项名称

update LIMS_MENU set name='系统管理' where ID=01;
INSERT INTO `LIMS_MENU` VALUES ('0104', '01', '系统配置', '/sysconfig/list.do', '4', 'fa-cogs');

-- insert 2016-9.12 by 孔德成       绝对定量表
SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `LIMS_TESTING_SHEET_TASK_ABSOLUTE`;
CREATE TABLE `LIMS_TESTING_SHEET_TASK_ABSOLUTE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SOURCE_SAMPLE_CODE` varchar(64) DEFAULT NULL COMMENT '测序编号',
  `FRAGMENT_LENGTH` int(11) DEFAULT NULL COMMENT '片段长度',
  `CONCENTRATION_PC` double(11,2) DEFAULT NULL COMMENT '上机浓度',
  `CLUSTER` double(11,2) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `LIMS_TESTING_SHEET_TASK_ABSOLUTE_DATA`;
CREATE TABLE `LIMS_TESTING_SHEET_TASK_ABSOLUTE_DATA` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ABSOLUTE_ID` int(11) DEFAULT NULL,
  `SHEET_TASK_RESULT_ID` int(11) DEFAULT NULL,
  `QUANTIFY_INSTRUMENT` varchar(255) DEFAULT NULL COMMENT '定量仪器',
  `KW_SIZE` double(11,2) DEFAULT NULL COMMENT '文库量',
  `UNIT_CONVERSION` varchar(64) DEFAULT NULL COMMENT '单位换算',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 2016-9.13 by 丁海荣
 alter table LIMS_TESTING_SHEET_TASK add four_htone double(20,2) comment '稀释到4nm需要的ht1';
 alter table LIMS_TESTING_SHEET_TASK add oneeight_htone double(20,2) comment '稀释到1.8pm';
 alter table LIMS_TESTING_SHEET_TASK add cluster int(11);
 alter table LIMS_TESTING_SHEET_TASK add create_time datetime comment '上机时间';
 
 -- 2016-9.13 by 黄文涛
 
 DROP TABLE IF EXISTS `LIMS_CUSTOMER`;
CREATE TABLE `LIMS_CUSTOMER` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL COMMENT '编号',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `phone` varchar(255) DEFAULT NULL COMMENT '电话',
  `firm_id` int(11) DEFAULT NULL COMMENT '单位id',
  `department` varchar(255) DEFAULT NULL COMMENT '科室',
  `province` varchar(255) DEFAULT NULL COMMENT '省',
  `city` varchar(255) DEFAULT NULL COMMENT '市',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `state` varchar(255) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

  DROP TABLE IF EXISTS `LIMS_FIRM`;
CREATE TABLE `LIMS_FIRM` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '名称',
  `code` varchar(255) DEFAULT NULL COMMENT '编号',
  `name` varchar(255) DEFAULT NULL,
  `net_address` varchar(255) DEFAULT NULL COMMENT '网址',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `state` varchar(255) DEFAULT '0' COMMENT '状态',
  `other_name` varchar(255) DEFAULT NULL COMMENT '别名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

--update 2016-9.18 by 孔德成       加字段
alter table LIMS_SAMPLE_TRACING add INPUT_UNIT varchar(2) DEFAULT NULL;
alter table LIMS_SAMPLE_TRACING add OUTPUT_AMOUNT double(11,2) DEFAULT NULL comment '产出量';
alter table LIMS_SAMPLE_TRACING add COORDINATE_LIST varchar(256) DEFAULT NULL;

alter table LIMS_SAMPLE_DETAIL add CREATOR_ID varchar(64) DEFAULT NULL;

alter table LIMS_TESTING_SHEET_TASK add original_code varchar(64) DEFAULT NULL;
alter table LIMS_TESTING_SHEET_TASK add library_code varchar(64) DEFAULT NULL;
alter table LIMS_TESTING_SHEET_TASK add coordinate varchar(128) DEFAULT NULL;
alter table LIMS_TESTING_SHEET_TASK add product_name varchar(128) DEFAULT NULL;
alter table LIMS_TESTING_SHEET_TASK add sex varchar(2) DEFAULT NULL;
alter table LIMS_TESTING_SHEET_TASK add receive_type varchar(2) DEFAULT NULL;
alter table LIMS_TESTING_SHEET_TASK add institution varchar(256) DEFAULT NULL;


-- update 2016-09-20 丁海荣 增加字典 单位
INSERT INTO `lims`.`LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('2027', '20', NULL, 'G', '27', '27', NULL);=======
alter table LIMS_TESTING_SHEET_TASK add institution varchar(256) DEFAULT NULL;

--update 2016-9.23 by 季炜程       加字段

ALTER TABLE `LIMS_TASK`
ADD COLUMN `REAGENTKIT`  varchar(255) NULL DEFAULT NULL AFTER `UNIT`; 


ALTER TABLE `LIMS_CHECK_MANAGEMENT`
ADD COLUMN `TASK_ARRAY`  varchar(255) NULL DEFAULT NULL COMMENT '任务数组' AFTER `ANALYZE_METHOD_ARRAY`;

ALTER TABLE `LIMS_TESTING_SHEET`
DROP COLUMN `send_name`,
DROP COLUMN `send_date`,
ADD COLUMN `send_name`  varchar(64) NULL DEFAULT NULL COMMENT '下达人' AFTER `wkbh_code`,
ADD COLUMN `send_date`  datetime NULL DEFAULT NULL COMMENT '下达时间' AFTER `send_name`;

--update 2016-9.26 by 季炜程       添加试剂盒id字段

ALTER TABLE `LIMS_TASK_INPUT`
ADD COLUMN `REAGENT_KIT_ID`  int(64) NULL DEFAULT NULL COMMENT '试剂盒' AFTER `INPUT＿QUANTITY`;

--update 2016-9.27 by 季炜程      增加实验负责人
ALTER TABLE `LIMS_TESTING_SHEET`
ADD COLUMN `testing_name`  varchar(64) NULL DEFAULT NULL COMMENT '实验负责人' AFTER `send_date`;

--update 2016-9.28 by 季炜程      增加检测方法
ALTER TABLE `LIMS_TESTING_SHEET`
DROP COLUMN `method`,
ADD COLUMN `method`  varchar(64) NULL DEFAULT NULL COMMENT '质检方法' AFTER `testing_name`;


--update 2016-9.28 by 季炜程      增加自动生成编号
ALTER TABLE `LIMS_TESTING_SHEET_TASK`
DROP COLUMN `serial_code`,
ADD COLUMN `serial_code`  varchar(64) NULL DEFAULT NULL COMMENT '自动生成编号' AFTER `absolute_id`;

--update 2016-9.30 by 季炜程      增加默认输出量，输入量
ALTER TABLE `LIMS_TESTING_SHEET`
ADD COLUMN `Input_Quantity`  varchar(64) NULL DEFAULT NULL COMMENT '默认输入量' AFTER `testing_name`;

ALTER TABLE `LIMS_TESTING_SHEET`
MODIFY COLUMN `output_Quantity　`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '默认输出量' AFTER `Input_Quantity`;


--update 2016-9.26 by 孔德成       加字段
ALTER TABLE `LIMS_TESTING_SHEET_TASK`
ADD COLUMN `project_name`  varchar(128) NULL AFTER `institution`,
ADD COLUMN `file_name`  varchar(256) NULL AFTER `project_name`,
ADD COLUMN `case_num`  varchar(128) NULL COMMENT '病历号' AFTER `file_name`,
ADD COLUMN `send_date`  datetime NULL AFTER `case_num`,
ADD COLUMN `inspect_name`  varchar(64) NULL COMMENT '受检人' AFTER `send_date`,
ADD COLUMN `age`  int(3) NULL AFTER `inspect_name`,
ADD COLUMN `check_management`  varchar(64) NULL COMMENT '检测方法' AFTER `age`,
ADD COLUMN `report_date`  datetime NULL AFTER `check_management`,
ADD COLUMN `clinical_diagnosis`  text NULL COMMENT '临床诊断' AFTER `report_date`,
ADD COLUMN `focus_genes`  varchar(128) NULL COMMENT '重点关注基因' AFTER `clinical_diagnosis`,
ADD COLUMN `case_summary`  varchar(256) NULL COMMENT '病历摘要' AFTER `focus_genes`,
ADD COLUMN `family_history`  varchar(2) NULL COMMENT '是否有家族史' AFTER `case_summary`,
ADD COLUMN `family_history_summary`  varchar(256) NULL COMMENT '家族史摘要' AFTER `family_history`,
ADD COLUMN `customer_name`  varchar(64) NULL AFTER `family_history_summary`,
ADD COLUMN `business_leader`  varchar(64) NULL COMMENT '业务负责人' AFTER `customer_name`,
ADD COLUMN `technical_leader`  varchar(64) NULL COMMENT '技术负责人' AFTER `business_leader`,
ADD COLUMN `report_leader`  varchar(64) NULL COMMENT '报告负责人' AFTER `technical_leader`,
ADD COLUMN `report_complete_date`  datetime NULL AFTER `report_leader`,
ADD COLUMN `report_verifier`  varchar(64) NULL COMMENT '报告审核人' AFTER `report_complete_date`;

UPDATE `lims`.`LIMS_TASK` SET `ID`='175', `TASK_CODE`=NULL, `TASK_NAME`='生成报告', `TASK_DESCRIPTION`='', `OUTPUT_TYPE`='4', `OUTPUT_ID`='1', `OWNER`='65', `TASK_TYPE_NAME`='REPORT_CREATE', `TASK_TYPE`=NULL, `OUTPUT_QUANTITY`='500.00', `UNIT`='1', `REAGENTKIT`=NULL WHERE (`ID`='175');
INSERT INTO `lims`.`LIMS_TASK` (`ID`, `TASK_CODE`, `TASK_NAME`, `TASK_DESCRIPTION`, `OUTPUT_TYPE`, `OUTPUT_ID`, `OWNER`, `TASK_TYPE_NAME`, `TASK_TYPE`, `OUTPUT_QUANTITY`, `UNIT`, `REAGENTKIT`) VALUES ('177', NULL, '报告审核', NULL, '4', '1', '65', 'REPORT_CHECK', NULL, '500.00', '1', NULL);
INSERT INTO `lims`.`LIMS_TASK` (`ID`, `TASK_CODE`, `TASK_NAME`, `TASK_DESCRIPTION`, `OUTPUT_TYPE`, `OUTPUT_ID`, `OWNER`, `TASK_TYPE_NAME`, `TASK_TYPE`, `OUTPUT_QUANTITY`, `UNIT`, `REAGENTKIT`) VALUES ('178', NULL, '报告邮寄', NULL, '4', '1', '65', 'REPORT_MAIL', NULL, '500.00', '1', NULL);


--update 2016-9.27 by 孔德成       加字段
ALTER TABLE `LIMS_TESTING_SHEET_TASK`
ADD COLUMN `four_htone_value`  double(11,2) NULL AFTER `report_verifier`,
ADD COLUMN `oneeight_htone_value`  double(11,2) NULL AFTER `four_htone_value`,
ADD COLUMN `absolute_id`  int(11) NULL AFTER `oneeight_htone_value`;

--update 2016-9.28 by 孔德成  
UPDATE `lims`.`LIMS_TASK_INPUT` SET `ID`='597', `TASK_ID`='177', `TASK_INPUT_ID`='1', `TASK_INPUT_TYPE`='4', `REAGENT_ID`='4', `UNIT`='1', `INPUT＿QUANTITY`='200.00', `REAGENT_KIT_ID`=NULL WHERE (`ID`='597');
UPDATE `lims`.`LIMS_TASK_INPUT` SET `ID`='598', `TASK_ID`='178', `TASK_INPUT_ID`='1', `TASK_INPUT_TYPE`='4', `REAGENT_ID`='3', `UNIT`='1', `INPUT＿QUANTITY`='200.00', `REAGENT_KIT_ID`=NULL WHERE (`ID`='598');

INSERT INTO `lims`.`LIMS_CHECKMANAGEMENT_TASK` (`ID`, `CHECKMANAGEMENT_ID`, `TASK_ID`, `TASK_ORDER`) VALUES ('193', '59', '177', '10');
INSERT INTO `lims`.`LIMS_CHECKMANAGEMENT_TASK` (`ID`, `CHECKMANAGEMENT_ID`, `TASK_ID`, `TASK_ORDER`) VALUES ('194', '59', '178', '11');

--add 2016-9.30 by 孔德成  
ALTER TABLE `LIMS_SAMPLE_TRACING`
ADD COLUMN `CHECK_MANAGEMENT`  varchar(64) NULL COMMENT '检测方法名称' AFTER `COORDINATE_LIST`;

--add 2016-10-9 by 孔德成  
ALTER TABLE `LIMS_TESTING_SHEET_TASK`
ADD COLUMN `input_id`  int(11) NULL COMMENT '输入样本id' AFTER `serial_code`,
ADD COLUMN `input_type`  varchar(2) NULL COMMENT '输入样本类型' AFTER `input_id`;

--add 2016-10-10 by 孔德成  
ALTER TABLE `LIMS_TESTING_TASK`
ADD COLUMN `SAMPLE_DETAIL_ID`  int(11) NOT NULL AFTER `METHOD_ID`;

ALTER TABLE `LIMS_TESTING_SCHEDULE`
MODIFY COLUMN `ACTIVE_TASK`  varchar(64) NULL DEFAULT NULL COMMENT '任务类型' AFTER `START_TIME`;

--add 2016-10-11 by 孔德成  
ALTER TABLE `LIMS_TESTING_SHEET_TASK`
ADD COLUMN `testing_task_id`  varchar(64) NULL AFTER `input_type`;






--2016 -10-11 by 季炜程
DROP TABLE IF EXISTS `LIMS_TESTING_DETAIL`;
CREATE TABLE `LIMS_TESTING_DETAIL` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `test_code` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '实验编号',
  `tested_name` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '受检人姓名?',
  `source_sample_code` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '源样本编号?',
  `source_sample_type` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '源样本类型?',
  `source_sample_location` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '源样本位置?',
  `target_sample_code` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '目标样本编号',
  `target_sample_location` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '目标样本位置',
  `test_taskId` int(11) DEFAULT NULL COMMENT '下达任务主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `LIMS_TESTING_SCHEDULE`;
CREATE TABLE `LIMS_TESTING_SCHEDULE` (
  `ID` int(64) NOT NULL,
  `ORDER_ID` int(64) NOT NULL,
  `PRODUCT_ID` int(64) NOT NULL,
  `METHOD_ID` int(64) NOT NULL,
  `SAMPLE_ID` int(64) NOT NULL,
  `START_TIME` datetime DEFAULT NULL COMMENT '启动时间',
  `ACTIVE_TASK` int(64) DEFAULT NULL,
  `END_TIME` datetime DEFAULT NULL COMMENT '完成时间?',
  `END_TYPE` tinyint(1) DEFAULT NULL COMMENT '状态类型',
  `VERIFY_TARGET` int(64) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `LIMS_TESTING_SCHEDULE_ACTIVE`;
CREATE TABLE `LIMS_TESTING_SCHEDULE_ACTIVE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `SCHEDULE_ID` int(11) NOT NULL COMMENT '检测流程ID',
  `TASK_ID` int(11) NOT NULL COMMENT '检测任务ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

--2016 -10-14 by 季炜程
ALTER TABLE `LIMS_TESTING_SHEET`
ADD COLUMN `reagentKit_code`  varchar(255) NULL COMMENT '试剂盒编码' AFTER `output_Quantity`,
ADD COLUMN `reagentKit_name`  varchar(255) NULL COMMENT '试剂盒名称' AFTER `reagentKit_code`;

---add 2016年10月13日11:16:21  by 宋健
delete from LIMS_DICT where id= '06' or PARENT_ID='06';
delete from LIMS_DICT where id= '07' or PARENT_ID='07';


--  add 2016-10-14 by 季炜程  
ALTER TABLE `LIMS_TESTING_SHEET`
ADD COLUMN `unit`  varchar(64) NULL COMMENT '单位' AFTER `reagentKit_name`;

--  add 2016-10-16 by 孔德成
ALTER TABLE `LIMS_TESTING_SHEET`
ADD COLUMN `QT_executer`  int(11) NULL COMMENT '质检试验员' AFTER `unit`;

--  add 2016-10-17 by 薛建威
ALTER TABLE `LIMS_SUPPLIER_REAGENT_KIT`
MODIFY COLUMN `price`  double(20,2) NULL DEFAULT NULL COMMENT '单价' AFTER `reagent_kit_id`;

--  add 2016-10-17 by 薛建威
ALTER TABLE `LIMS_SUPPLIER_REAGENT`
MODIFY COLUMN `price`  double(20,2) NULL DEFAULT NULL COMMENT '单价' AFTER `reagent_id`;

--  add 2016-10-18 by 孔德成
ALTER TABLE `LIMS_TESTING_SHEET_TASK`
ADD COLUMN `products`  varchar(512) NULL AFTER `testing_task_id`;

--  add 2016-10-20 by 季炜程
ALTER TABLE `LIMS_TESTING_SHEET`
ADD COLUMN `QT_executer_name`  varchar(255) NULL COMMENT '质检负责人名' AFTER `QT_executer`;

--  add 2016-10-20 by 孔德成
ALTER TABLE `LIMS_TESTING_SHEET_TASK`
ADD COLUMN `input_quantity`  double(11,2) NULL COMMENT '默认输入量' AFTER `products`,
ADD COLUMN `output_quantity`  double(11,2) NULL COMMENT '默认输出量' AFTER `input_quantity`;

--  add 2016-10-21 by 季炜程
CREATE TABLE `LIMS_TASK_INPUT_REAGENTKIT` (
`ID`  int(20) NOT NULL ,
`TASK_INPUT_ID`  int(20) NULL COMMENT '任务输入' ,
`REAGENT_KIT_ID`  int(20) NULL COMMENT '试剂盒Id' ,
`UNIT`  varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '试剂盒单位' ,
`INPUT＿QUANTITY`  double(20,2) NULL COMMENT '默认量' ,
PRIMARY KEY (`ID`)
)
;
--  add 2016-10-21 by 薛建威
ALTER TABLE `LIMS_EQUIPMENT`
MODIFY COLUMN `price`  double(11,2) NULL DEFAULT NULL AFTER `specification_no`;

--  add 2016-10-21 by 孔德成
ALTER TABLE `LIMS_PRODUCT_PROBE`
MODIFY COLUMN `DATA_SIZE`  int(20) NULL DEFAULT NULL COMMENT '数据量' AFTER `PROBE_ID`,
ADD COLUMN `QUALITY_SIZE`  double(11,2) NULL COMMENT '探针质量' AFTER `PROBE_NAME`,
ADD COLUMN `QUALITY_UNIT`  varchar(2) NULL AFTER `QUALITY_SIZE`;

--  add 2016-10-23 by 季炜程
ALTER TABLE `LIMS_TASK_INPUT_REAGENTKIT`
MODIFY COLUMN `ID`  int(20) NOT NULL AUTO_INCREMENT FIRST ;


--  add 2016-10-24 by 季炜程
ALTER TABLE `LIMS_PRODUCT`
ADD COLUMN `experiment_samples`  varchar(8) NULL COMMENT '实验样本' AFTER `state`;
--  添加字典项 add 2016-10-24 by 季炜程
insert LIMS_DICT (ID,CATEGORY,DICT_TEXT) values(49,'EXPERIMENT_SAMPLES','实验样本');
insert LIMS_DICT (ID,PARENT_ID,DICT_TEXT,DICT_VALUE,SORT) values('4901','49','DNA','1',1);
insert LIMS_DICT (ID,PARENT_ID,DICT_TEXT,DICT_VALUE,SORT) values('4902','49','CDNA','2',2);
--   add 2016-10-24 by 季炜程
ALTER TABLE `LIMS_SAMPLE`
ADD COLUMN `sample_type`  varchar(16) NULL COMMENT '样本类型，只有DNA和RNA有值' AFTER `unit`;
--   add 2016-10-24 by 季炜程
update LIMS_SAMPLE set sample_type='1' where id='30';
update LIMS_SAMPLE set sample_type='2' where id='32';



---update  by 宋健 2016年10月24日13:46:25
INSERT INTO `LIMS_MENU` VALUES ('0205', '02', '订单管理_NEW', '/order/paging.do', '5', 'fa-hospital-o');


DROP TABLE IF EXISTS `LIMS_ATTACHMENT`;
CREATE TABLE `LIMS_ATTACHMENT`
(
    `ID`                    varchar(64)         NOT NULL        COMMENT '主键',
    `NAME`                  varchar(256)        NOT NULL        COMMENT '附件名称',
    `SUFFIX`                varchar(16)         NOT NULL        COMMENT '附件后缀',
    `STORAGE_TYPE`          varchar(8)          NOT NULL        COMMENT '存储方式：1-内部存储 2-阿里OSS',
    `STORAGE_KEY`           varchar(64)                         COMMENT '外部存储对应主键',
    `INNER_URL`             varchar(512)                        COMMENT '内部地址',
    CONSTRAINT PK_ATTACHMENT PRIMARY KEY (`ID`)
)
;


DROP TABLE IF EXISTS `LIMS_ATTACHMENT_ALIOSS`;
CREATE TABLE `LIMS_ATTACHMENT_ALIOSS`
(
    `ID`                    varchar(64)         NOT NULL        COMMENT '主键',
    `BUCKET_NAME`           varchar(256)        NOT NULL,
    `OBJECT_KEY`            varchar(256)        NOT NULL,
    CONSTRAINT PK_ATTACHMENT_ALIOSS PRIMARY KEY (`ID`)
)
;

DROP TABLE IF EXISTS `LIMS_CONTRACT`;
CREATE TABLE `LIMS_CONTRACT`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `CONTRACT_TYPE`     tinyint(1)                          COMMENT '合同类型',
    `CODE`              varchar(64)         NOT NULL        COMMENT '合同编号',
    `NAME`              varchar(128)                        COMMENT '合同名称',
    `PARTY_A`           varchar(128)                        COMMENT '甲方',
    `STATUS`            tinyint(2)          NOT NULL        COMMENT '合同状态',
    `EFFECTIVE_START`   date                                COMMENT '有效期-开始日期',
    `EFFECTIVE_END`     date                                COMMENT '有效期-结束日期',
    `ORIGINAL_ID`       varchar(64)                         COMMENT '合同原件-关联附件表',
    `CREATOR_ID`        varchar(64)                         COMMENT '创建人ID',
    `CREATOR_NAME`      varchar(32)                         COMMENT '创建人姓名',
    `CREATE_TIME`       datetime            NOT NULL        COMMENT '创建时间',
    CONSTRAINT PK_CONTRACT PRIMARY KEY (`ID`)
)
;


DROP TABLE IF EXISTS `LIMS_CONTRACT_PRODUCT`;
CREATE TABLE `LIMS_CONTRACT_PRODUCT`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `CONTRACT_ID`       varchar(64)         NOT NULL        COMMENT '合同',
    `PRODUCT_ID`        varchar(64)         NOT NULL        COMMENT '产品',
    CONSTRAINT PK_CONTRACT_PRODUCT PRIMARY KEY (`ID`)
)
;


DROP TABLE IF EXISTS `LIMS_ORDER`;
CREATE TABLE `LIMS_ORDER`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `ORDER_TYPE`        tinyint(1)          NOT NULL        COMMENT '订单类型（临检、科研、健康）',
    `CONTRACT_ID`       varchar(64)                         COMMENT '归属合同',
    `CODE`              varchar(64)         NOT NULL        COMMENT '订单编号',
    `STATUS`            tinyint(2)          NOT NULL        COMMENT '订单状态',
    `AMOUNT`            int(11)                             COMMENT '订单金额，单位（分）',
    `RECIPIENT_NAME`    varchar(64)                         COMMENT '收件人姓名',
    `RECIPIENT_PHONE`   varchar(32)                         COMMENT '收件人联系方式',
    `RECIPIENT_EMAIL`   varchar(256)                        COMMENT '收件人邮箱',
    `RECIPIENT_ADDRESS` varchar(256)                        COMMENT '收件人地址',
    `DOCTOR_ASSIST`     varchar(8)                          COMMENT '客户参与（X/X）',
    `INVOICE_TITLE`     varchar(128)                        COMMENT '发票抬头',
    `CREATOR_ID`        varchar(64)                         COMMENT '创建人ID',
    `CREATOR_NAME`      varchar(32)                         COMMENT '创建人姓名',
    `CREATE_TIME`       datetime            NOT NULL        COMMENT '创建时间',
    CONSTRAINT PK_ORDER PRIMARY KEY (`ID`)
)
;


DROP TABLE IF EXISTS `LIMS_ORDER_PRODUCT`;
CREATE TABLE `LIMS_ORDER_PRODUCT`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `ORDER_ID`          varchar(64)         NOT NULL        COMMENT '订单ID',
    `PRODUCT_ID`        varchar(64)         NOT NULL        COMMENT '产品ID',
    `PRODUCT_NAME`      varchar(128)                        COMMENT '产品名称',
    `PRODUCT_PRICE`     int(11)                             COMMENT '产品价格',
    CONSTRAINT PK_ORDER_PRODUCT PRIMARY KEY (`ID`)
)
;


DROP TABLE IF EXISTS `LIMS_ORDER_EXAMINEE`;
CREATE TABLE `LIMS_ORDER_EXAMINEE`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `ORDER_ID`          varchar(64)         NOT NULL        COMMENT '订单ID',
    `NAME`              varchar(32)                         COMMENT '姓名',
    `SEX`               tinyint(1)                          COMMENT '性别：1-男 2-女',
    `BIRTHDAY`          varchar(32)                         COMMENT '出生日期',
    `AGE_SNAPSHOT`      varchar(32)                         COMMENT '申请检测时年龄（X/X/X）',
    `NATION`            varchar(4)                          COMMENT '民族',
    `ORIGIN`            varchar(128)                        COMMENT '籍贯',
    `CONTACT_NAME`      varchar(32)                         COMMENT '联系人',
    `CONTACT_PHONE`     varchar(16)                         COMMENT '联系电话 ',
    `CONTACT_EMAIL`     varchar(128)                        COMMENT '联系邮箱 ',
    `RECORD_NO`         varchar(128)                        COMMENT '病历号',
    `MEDICAL_HISTORY`   varchar(512)                        COMMENT '既往病史',
    CONSTRAINT PK_ORDER_EXAMINEE PRIMARY KEY (`ID`)
)
;


DROP TABLE IF EXISTS `LIMS_ORDER_EXAMINEE_ATTACHMENT`;
CREATE TABLE `LIMS_ORDER_EXAMINEE_ATTACHMENT`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `OE_ID`             varchar(64)         NOT NULL        COMMENT '订单受检人',
    `ATTACHMENT_TYPE`   varchar(8)          NOT NULL        COMMENT '附件类型：1-知情同意书 2-病历',
    `IMAGE_ID`          varchar(64)         NOT NULL        COMMENT '附件图片ID',
    CONSTRAINT PK_ORDER_EXAMINEE_ATTACHMENT PRIMARY KEY (`ID`)
)
;


DROP TABLE IF EXISTS `LIMS_ORDER_EXAMINEE_DIAGNOSIS`;
CREATE TABLE `LIMS_ORDER_EXAMINEE_DIAGNOSIS`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `OE_ID`             varchar(64)         NOT NULL        COMMENT '订单受检人',
    `DISEASE_ID`        varchar(64)         NOT NULL        COMMENT '疾病ID',
    CONSTRAINT PK_ORDER_EXAMINEE_DIAGNOSIS PRIMARY KEY (`ID`)
)
;


DROP TABLE IF EXISTS `LIMS_ORDER_EXAMINEE_PHENOTYPE`;
CREATE TABLE `LIMS_ORDER_EXAMINEE_PHENOTYPE`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `OE_ID`             varchar(64)         NOT NULL        COMMENT '订单受检人',
    `PHENOTYPE_ID`      varchar(64)         NOT NULL        COMMENT '表型ID',
    CONSTRAINT PK_ORDER_EXAMINEE_PHENOTYPE PRIMARY KEY (`ID`)
)
;


DROP TABLE IF EXISTS `LIMS_ORDER_EXAMINEE_GENE`;
CREATE TABLE `LIMS_ORDER_EXAMINEE_GENE`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `OE_ID`             varchar(64)         NOT NULL        COMMENT '订单受检人',
    `GENE_ID`           varchar(64)         NOT NULL        COMMENT '基因ID',
    CONSTRAINT PK_ORDER_EXAMINEE_GENE PRIMARY KEY (`ID`)
)
;




DROP TABLE IF EXISTS `LIMS_ORDER_PRIMARY_SAMPLE`;
CREATE TABLE `LIMS_ORDER_PRIMARY_SAMPLE`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `ORDER_ID`          varchar(64)         NOT NULL        COMMENT '订单ID',
    `EXAMINEE_ID`       varchar(64)                         COMMENT '受检人ID', 
    `SAMPLE_CODE`       varchar(64)         NOT NULL        COMMENT '样本编号',
    `SAMPLE_TYPE_ID`    varchar(64)         NOT NULL        COMMENT '样本类型ID',
    `SAMPLE_SIZE`       int(5)                              COMMENT '样本量',
    `SAMPLING_DATE`     date                                COMMENT '采样时间',
    CONSTRAINT PK_ORDER_PRIMARY_SAMPLE PRIMARY KEY (`ID`)
)
;


DROP TABLE IF EXISTS `LIMS_ORDER_SUBSIDIARY_SAMPLE`;
CREATE TABLE `LIMS_ORDER_SUBSIDIARY_SAMPLE`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `ORDER_ID`          varchar(64)         NOT NULL        COMMENT '订单ID',
    `EXAMINEE_ID`       varchar(64)                         COMMENT '受检人ID',
    `SAMPLE_CODE`       varchar(64)         NOT NULL        COMMENT '样本编号',
    `SAMPLE_TYPE_ID`    varchar(64)         NOT NULL        COMMENT '样本类型ID',
    `FAMILY_RELATION`   varchar(16)                         COMMENT '家系关系',
    `OWNER_NAME`        varchar(32)                         COMMENT '样本提供者姓名',
    `OWNER_AGE`         smallint(3)                         COMMENT '样本提供者年龄',
    `OWNER_PHENOTYPE`   tinyint(1)                          COMMENT '样本提供者表型：1-正常 2-与受检人类型',
    `PURPOSE`           tinyint(1)                          COMMENT '用途：1-验证 2-检测',
    `SAMPLE_SIZE`       int(5)                              COMMENT '样本量',  
    `SAMPLING_DATE`     date                                COMMENT '采样时间',
    CONSTRAINT PK_ORDER_SUBSIDIARY_SAMPLE PRIMARY KEY (`ID`)
)
;


DROP TABLE IF EXISTS `LIMS_ORDER_SAMPLE_GROUP`;
CREATE TABLE `LIMS_ORDER_SAMPLE_GROUP`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `NAME`              varchar(64)         NOT NULL        COMMENT '组名称',
    CONSTRAINT PK_ORDER_SAMPLE_GROUP PRIMARY KEY (`ID`)
)
;


DROP TABLE IF EXISTS `LIMS_ORDER_RESEARCH_SAMPLE`;
CREATE TABLE `LIMS_ORDER_RESEARCH_SAMPLE`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `ORDER_ID`          varchar(64)         NOT NULL        COMMENT '订单ID',
    `GROUP_ID`          varchar(64)         NOT NULL        COMMENT '样本组ID',
    `SAMPLE_CODE`       varchar(64)         NOT NULL        COMMENT '样本编号',
    `SAMPLE_TYPE_ID`    varchar(64)         NOT NULL        COMMENT '样本类型ID',
    `SAMPLE_NAME`       varchar(128)                        COMMENT '样本名称',
    `SAMPLE_SIZE`       int(5)                              COMMENT '样本量',
    `DIAGNOSIS`         varchar(256)                        COMMENT '临床诊断',
    `FOCUS_GENE`        varchar(256)                        COMMENT '重点关注基因',
    `FAMILY_RELATION`   varchar(256)                        COMMENT '家系关系',
    `REMARK`            varchar(512)                        COMMENT '备注',
    CONSTRAINT PK_ORDER_RESEARCH_SAMPLE PRIMARY KEY (`ID`)
)
;

DROP TABLE IF EXISTS `LIMS_ORDER_RESEARCH_PRODUCT`;
CREATE TABLE `LIMS_ORDER_RESEARCH_PRODUCT`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `ORS_ID`            varchar(64)         NOT NULL        COMMENT '订单-科研样本ID',
    `PRODUCT_ID`        varchar(64)         NOT NULL        COMMENT '产品ID',
    `PRODUCT_NAME`      varchar(128)                        COMMENT '产品名称',
    `PRODUCT_PRICE`     int(11)                             COMMENT '产品价格',
    CONSTRAINT PK_ORDER_RESEARCH_PRODUCT PRIMARY KEY (`ID`)
)
;

INSERT INTO `lims`.`LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('48', NULL, 'FAMILY_RELATION', '家系关系', NULL, NULL, NULL);
INSERT INTO `lims`.`LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('4801', '48', NULL, '父亲', '1', '1', NULL);
INSERT INTO `lims`.`LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('4802', '48', NULL, '母亲', '2', '2', NULL);

--   add 2016-10-24 by 季炜程
ALTER TABLE `LIMS_TASK_INPUT`
ADD COLUMN `TASK_SET_ID`  int(20) NULL COMMENT '前端传入task_id 用作查询set Task用' AFTER `REAGENT_KIT_ID`;




----add by 宋健  2016年10月26日09:36:52
INSERT INTO `LIMS_MENU` VALUES ('0206', '02', '收样管理_NEW', '/receiveManager/paging.do', '6', 'fa-hospital-o');

alter table `LIMS_ORDER_SAMPLE_GROUP` add ORDER_ID varchar(64)  COMMENT '订单编号';


DROP TABLE IF EXISTS `LIMS_SAMPLE_RECEIVING`;
CREATE TABLE `LIMS_SAMPLE_RECEIVING`
(
    `ID`                    varchar(64)         NOT NULL        COMMENT '主键',
    `CODE`                  varchar(64)         NOT NULL        COMMENT '接收编号',
    `ORDER_ID`              varchar(64)         NOT NULL        COMMENT '关联订单',
    `STATUS`                tinyint(1)                          COMMENT '接收状态：0-异常 1-正常',
    `REMARK`                varchar(256)                        COMMENT '接收备注',
    `RECEIVER_ID`           varchar(64)                         COMMENT '接收人ID',
    `RECEIVER_NAME`         varchar(64)                         COMMENT '接收人姓名',
    `RECEIVE_TIME`          datetime                            COMMENT '接收时间',
    CONSTRAINT PK_SAMPLE_RECEIVING PRIMARY KEY (`ID`)
)
;
ALTER TABLE `LIMS_SAMPLE_RECEIVING` COMMENT '业务库-样本接收记录';


DROP TABLE IF EXISTS `LIMS_SAMPLE_RECEIVING_DETAIL`;
CREATE TABLE `LIMS_SAMPLE_RECEIVING_DETAIL`
(
    `ID`                    varchar(64)         NOT NULL        COMMENT '主键',
    `RECORD_ID`             varchar(64)         NOT NULL        COMMENT '接收记录',
    `SAMPLE_CODE`           varchar(64)         NOT NULL        COMMENT '样本编号',
    `MC_STATUS`             tinyint(1)                          COMMENT '匹配状态：0-不匹配 1-匹配',
    `QC_STATUS`             tinyint(1)                          COMMENT '质检状态：0-不合格 1-合格',
    `QC_REMARK`             varchar(256)                        COMMENT '质检备注',
    CONSTRAINT PK_SAMPLE_RECEIVING_DETAIL PRIMARY KEY (`ID`)
)
;
ALTER TABLE `LIMS_SAMPLE_RECEIVING_DETAIL` COMMENT '业务库-样本接收明细';



DROP TABLE IF EXISTS `LIMS_SAMPLE_TRANSFERRING`;
CREATE TABLE `LIMS_SAMPLE_TRANSFERRING`
(
    `ID`                    varchar(64)         NOT NULL        COMMENT '主键',
    `CODE`                  varchar(64)         NOT NULL        COMMENT '转存编号',
    `REMARK`                varchar(256)                        COMMENT '转存备注',
    `OPERATOR_ID`           varchar(64)                         COMMENT '操作人ID',
    `OPERATOR_NAME`         varchar(64)                         COMMENT '操作人姓名',
    `OPERATE_TIME`          datetime                            COMMENT '操作时间',
    CONSTRAINT PK_SAMPLE_TRANSFERRING PRIMARY KEY (`ID`)
)
;
ALTER TABLE `LIMS_SAMPLE_TRANSFERRING` COMMENT '业务库-样本转存记录';


DROP TABLE IF EXISTS `LIMS_SAMPLE_TRANSFERRING_DETAIL`;
CREATE TABLE `LIMS_SAMPLE_TRANSFERRING_DETAIL`
(
    `ID`                    varchar(64)         NOT NULL        COMMENT '主键',
    `RECORD_ID`             varchar(64)         NOT NULL        COMMENT '转存记录',
    `SAMPLE_CODE`           varchar(64)         NOT NULL        COMMENT '样本编号',
    `SAMPLE_SIZE`           integer                             COMMENT '接收样本量',
    `LS_SIZE`               integer                             COMMENT '长期存储量',
    `TS_SIZE`               integer                             COMMENT '临时存储量',
    `SIZE_UNIT`             varchar(16)                         COMMENT '收样量、存储量单位名称',
    CONSTRAINT PK_SAMPLE_TRANSFERRING_DETAIL PRIMARY KEY (`ID`)
)
;
ALTER TABLE `LIMS_SAMPLE_TRANSFERRING_DETAIL` COMMENT '业务库-样本转存明细';


DROP TABLE IF EXISTS `LIMS_SAMPLE_STORAGING`;
CREATE TABLE `LIMS_SAMPLE_STORAGING`
(
    `ID`                    varchar(64)         NOT NULL        COMMENT '主键',
    `CODE`                  varchar(64)         NOT NULL        COMMENT '入库编号',
    `TYPE`                  tinyint(1)          NOT NULL        COMMENT '入库类型',
    `REMARK`                varchar(256)                        COMMENT '入库备注',
    `OPERATOR_ID`           varchar(64)                         COMMENT '操作人ID',
    `OPERATOR_NAME`         varchar(64)                         COMMENT '操作人姓名',
    `OPERATE_TIME`          datetime                            COMMENT '操作时间',
    CONSTRAINT PK_SAMPLE_STORAGING PRIMARY KEY (`ID`)
)
;
ALTER TABLE `LIMS_SAMPLE_STORAGING` COMMENT '业务库-样本入库记录';


DROP TABLE IF EXISTS `LIMS_SAMPLE_STORAGING_DETAIL`;
CREATE TABLE `LIMS_SAMPLE_STORAGING_DETAIL`
(
    `ID`                    varchar(64)         NOT NULL        COMMENT '主键',
    `RECORD_ID`             varchar(64)         NOT NULL        COMMENT '入库记录',
    `SAMPLE_CODE`           varchar(64)         NOT NULL        COMMENT '样本编号',
    `LOCATION`              varchar(64)         NOT NULL        COMMENT '存储位置',
    CONSTRAINT PK_SAMPLE_STORAGING_DETAIL PRIMARY KEY (`ID`)
)
;
ALTER TABLE `LIMS_SAMPLE_STORAGING_DETAIL` COMMENT '业务库-样本入库明细';


DROP VIEW IF EXISTS `LIMS_ORDER_SAMPLE_VIEW`;
CREATE VIEW `LIMS_ORDER_SAMPLE_VIEW` (ID, ORDER_ID, SAMPLE_TYPE, SAMPLE_CODE, SAMPLE_NAME, SAMPLE_SIZE, SAMPLING_DATE) AS 
    SELECT CONCAT_WS('-', '1', PS.ID) AS ID, PS.ORDER_ID AS ORDER_ID, PS.SAMPLE_TYPE_ID AS SAMPLE_TYPE, PS.SAMPLE_CODE AS SAMPLE_CODE, OE.NAME AS SAMPLE_NAME, PS.SAMPLE_SIZE AS SAMPLE_SIZE, PS.SAMPLING_DATE AS SAMPLING_DATE
    FROM LIMS_ORDER_PRIMARY_SAMPLE PS LEFT JOIN LIMS_ORDER_EXAMINEE OE ON PS.EXAMINEE_ID = OE.ID
UNION ALL
    SELECT CONCAT_WS('-', '2', SS.ID) AS ID, SS.ORDER_ID AS ORDER_ID, SS.SAMPLE_TYPE_ID AS SAMPLE_TYPE, SS.SAMPLE_CODE AS SAMPLE_CODE, SS.OWNER_NAME AS SAMPLE_NAME, SS.SAMPLE_SIZE AS SAMPLE_SIZE, SS.SAMPLING_DATE AS SAMPLING_DATE
    FROM LIMS_ORDER_SUBSIDIARY_SAMPLE SS
UNION ALL
    SELECT CONCAT_WS('-', '3', RS.ID) AS ID, RS.ORDER_ID AS ORDER_ID, RS.SAMPLE_TYPE_ID AS SAMPLE_TYPE, RS.SAMPLE_CODE AS SAMPLE_CODE, RS.SAMPLE_NAME AS SAMPLE_NAME, RS.SAMPLE_SIZE AS SAMPLE_SIZE, NULL
    FROM LIMS_ORDER_RESEARCH_SAMPLE RS ;
 
--   add 2016-10-24 by 薛建威   
INSERT INTO `LIMS_DICT` VALUES ('50', null, 'PROCESS_STAGE', '流程阶段', null, null, null);
INSERT INTO `LIMS_DICT` VALUES ('5001', '50', null, '准备阶段', '1', '1', null);
INSERT INTO `LIMS_DICT` VALUES ('5002', '50', null, '实验阶段', '2', '2', null);
INSERT INTO `LIMS_DICT` VALUES ('5003', '50', null, '数据上传阶段', '3', '3', null);    

ALTER TABLE `LIMS_TASK`
ADD COLUMN `PROCESS_STAGE`  varchar(20) NULL COMMENT '流程阶段 1 准备 2 实验 3 数据上传' AFTER `REAGENTKIT`;

-- 修改product by huangwentao 2016-10-26
alter table LIMS_PRODUCT add column product_type int(11);
alter table LIMS_PRODUCT add column testing_duration int(11);
alter table LIMS_PRODUCT add column creator_name varchar(255);
alter table LIMS_PRODUCT drop column detection_classfy;
alter table LIMS_PRODUCT drop column test_method;
alter table LIMS_PRODUCT drop column cycle;
alter table LIMS_PRODUCT drop column en_sample;
alter table LIMS_PRODUCT drop column probe_ids;
alter table LIMS_PRODUCT drop column test_sample;
alter table LIMS_PRODUCT drop column testing_genes;
alter table LIMS_PRODUCT change creater creator_id int(11);
alter table LIMS_PRODUCT change state enabled varchar(255);
--  by 季炜程 2016-10-26 
ALTER TABLE `LIMS_TESTING_SHEET_TASK`
ADD COLUMN `notes`  varchar(512) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '备注' AFTER `output_quantity`;


ALTER TABLE `LIMS_TASK_INPUT_REAGENTKIT`
ADD COLUMN `REAGENT_NAME`  varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '试剂盒名' AFTER `INPUT＿QUANTITY`,
ADD COLUMN `REAGENT_CODE`  varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '试剂盒代码' AFTER `REAGENT_NAME`;





--add by宋健 2016年10月27日14:38:09
alter table LIMS_RECEIVED_SAMPLE add  BUSINESS_TYPE        tinyint(1)          NOT NULL        COMMENT '业务类型：1-非科研主样本 2-非科研附属样本 3-科研样本';

DROP VIEW IF EXISTS `LIMS_ORDER_SAMPLE_VIEW`;
CREATE VIEW `LIMS_ORDER_SAMPLE_VIEW` (ID, ORDER_ID, SAMPLE_ID, SAMPLE_BTYPE, SAMPLE_TYPE, SAMPLE_CODE, SAMPLE_NAME, SAMPLE_SIZE, SAMPLING_DATE) AS 
    SELECT CONCAT_WS('-', '1', PS.ID) AS ID, PS.ORDER_ID AS ORDER_ID, PS.ID, 1, PS.SAMPLE_TYPE_ID AS SAMPLE_TYPE, PS.SAMPLE_CODE AS SAMPLE_CODE, OE.NAME AS SAMPLE_NAME, PS.SAMPLE_SIZE AS SAMPLE_SIZE, PS.SAMPLING_DATE AS SAMPLING_DATE
    FROM LIMS_ORDER_PRIMARY_SAMPLE PS LEFT JOIN LIMS_ORDER_EXAMINEE OE ON PS.EXAMINEE_ID = OE.ID
UNION ALL
    SELECT CONCAT_WS('-', '2', SS.ID) AS ID, SS.ORDER_ID AS ORDER_ID, SS.ID, 2, SS.SAMPLE_TYPE_ID AS SAMPLE_TYPE, SS.SAMPLE_CODE AS SAMPLE_CODE, SS.OWNER_NAME AS SAMPLE_NAME, SS.SAMPLE_SIZE AS SAMPLE_SIZE, SS.SAMPLING_DATE AS SAMPLING_DATE
    FROM LIMS_ORDER_SUBSIDIARY_SAMPLE SS
UNION ALL
    SELECT CONCAT_WS('-', '3', RS.ID) AS ID, RS.ORDER_ID AS ORDER_ID, RS.ID, 3, RS.SAMPLE_TYPE_ID AS SAMPLE_TYPE, RS.SAMPLE_CODE AS SAMPLE_CODE, RS.SAMPLE_NAME AS SAMPLE_NAME, RS.SAMPLE_SIZE AS SAMPLE_SIZE, NULL
    FROM LIMS_ORDER_RESEARCH_SAMPLE RS
;



--  by 薛建威 2016-10-27 
INSERT INTO `lims`.`LIMS_MENU` (`ID`, `PARENT_ID`, `NAME`, `URI`, `SORT`, `ICON`) VALUES ('0410', '04', '样本转化配置', '/sample/handleSample.do', '10', 'fa-eyedropper');
--  by 季炜程 2016-10-28 
ALTER TABLE `LIMS_TESTING_SHEET_TASK`
ADD COLUMN `index`  varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '接头' AFTER `notes`;

ALTER TABLE `LIMS_TESTING_SHEET_TASK`
ADD COLUMN `sourceSampleType`  varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '收样类型' AFTER `index`,
ADD COLUMN `sourceSampleCode`  varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT 'DNA编号' AFTER `sourceSampleType`,
ADD COLUMN `targetSampleCode`  varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '文库编号' AFTER `sourceSampleCode`;
--  by 季炜程 2016-10-30 
insert into BCM_MEASURE_DIMENSION(id,name,base_unit,del_flag) VALUES ('1','克','g','0');
insert into BCM_MEASURE_DIMENSION(id,name,base_unit,del_flag) VALUES ('2','毫克','mg','0');
insert into BCM_MEASURE_DIMENSION(id,name,base_unit,del_flag) VALUES ('3','微克','μg','0');
insert into BCM_MEASURE_DIMENSION(id,name,base_unit,del_flag) VALUES ('4','克','ng','0');
insert into BCM_MEASURE_DIMENSION(id,name,base_unit,del_flag) VALUES ('5','毫升','mL','0');
insert into BCM_MEASURE_DIMENSION(id,name,base_unit,del_flag) VALUES ('6','微升','μL','0');
insert into BCM_MEASURE_DIMENSION(id,name,base_unit,del_flag) VALUES ('7','纳升','nL','0');
insert into BCM_MEASURE_DIMENSION(id,name,base_unit,del_flag) VALUES ('8','管','管','0');
insert into BCM_MEASURE_DIMENSION(id,name,base_unit,del_flag) VALUES ('9','盒','盒','0');
insert into BCM_MEASURE_DIMENSION(id,name,base_unit,del_flag) VALUES ('10','箱','箱','0');


insert into BCM_MEASURE_UNIT(id,DIMENSION_ID,NAME,RATIO,del_flag) VALUES ('1','1','quality','1000','0');
insert into BCM_MEASURE_UNIT(id,DIMENSION_ID,NAME,RATIO,del_flag) VALUES ('2','2','quality','1000','0');
insert into BCM_MEASURE_UNIT(id,DIMENSION_ID,NAME,RATIO,del_flag) VALUES ('3','3','quality','1000','0');
insert into BCM_MEASURE_UNIT(id,DIMENSION_ID,NAME,RATIO,del_flag) VALUES ('4','4','quality','1000','0');
insert into BCM_MEASURE_UNIT(id,DIMENSION_ID,NAME,RATIO,del_flag) VALUES ('5','5','volume','1000','0');
insert into BCM_MEASURE_UNIT(id,DIMENSION_ID,NAME,RATIO,del_flag) VALUES ('6','6','volume','1000','0');
insert into BCM_MEASURE_UNIT(id,DIMENSION_ID,NAME,RATIO,del_flag) VALUES ('7','7','volume','1000','0');

insert into BCM_MEASURE_UNIT(id,DIMENSION_ID,NAME,RATIO,del_flag) VALUES ('8','8','container','1','0');
insert into BCM_MEASURE_UNIT(id,DIMENSION_ID,NAME,RATIO,del_flag) VALUES ('9','9','container','1','0');
insert into BCM_MEASURE_UNIT(id,DIMENSION_ID,NAME,RATIO,del_flag) VALUES ('10','10','container','1','0');


--  by 孔德成      2016-10-31
ALTER TABLE `LIMS_SAMPLE_EXTRACT`
MODIFY COLUMN `ID`  varchar(64) NOT NULL FIRST ,
MODIFY COLUMN `SOURCE_SAMPLE`  varchar(64) NOT NULL COMMENT '待提取样本类型' AFTER `ID`,
MODIFY COLUMN `TARGET_SAMPLE`  varchar(64) NOT NULL COMMENT '目标样本类型' AFTER `SOURCE_SAMPLE`;

DROP TABLE IF EXISTS `LIMS_METADATA_SAMPLE`;
CREATE TABLE `LIMS_METADATA_SAMPLE`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `NAME`              varchar(128)        NOT NULL        COMMENT '类型名称',
    `TESTING_UNIT`      varchar(64)         NOT NULL        COMMENT '实验单位',
    `INTERMEDIATE`      tinyint(1)          NOT NULL        COMMENT '是否中间产物 1-否，2-是',
    `STORAGE_TYPE`      varchar(64)         NOT NULL        COMMENT '存储类型',
    `RECEIVING_UNIT`    varchar(64)         NOT NULL        COMMENT '收样单位',
    `LS_SIZE`           decimal                             COMMENT '长期存储量',
    `SAMPLING_TIPS`     varchar(1024)                       COMMENT '样本采集要求',
    `TRANSPORTING_TIPS` varchar(1024)                       COMMENT '样本运输要求',
    `STORAGING_TIPS`    varchar(1024)                       COMMENT '样本保存要求',
    `DEL_FLAG`          tinyint(1)          NOT NULL        COMMENT '删除标记 0-未删除 1-已删除',
    CONSTRAINT PK_METADATA_SAMPLE PRIMARY KEY (`ID`)
)
;
ALTER TABLE `LIMS_METADATA_SAMPLE` COMMENT '样本类型';

UPDATE `lims`.`LIMS_DICT` SET `ID`='0502', `PARENT_ID`='05', `CATEGORY`=NULL, `DICT_TEXT`='中间产物', `DICT_VALUE`='2', `SORT`='2', `EDITABLE`='0' WHERE (`ID`='0502');

--  by 薛建威      2016-11-1
INSERT INTO `lims`.`LIMS_MENU` (`ID`, `PARENT_ID`, `NAME`, `ICON`, `URI`, `SORT`) VALUES ('0904', '09', '合同管理', 'fa-building-o', '/contract/list.do', '4');
-- by jiweicheng 2016-11-2

UPDATE LIMS_MENU set URI ="/testing/dna_extract_tasks.do" WHERE ID="0301";
UPDATE LIMS_MENU set URI ="/testing/dna_qc_tasks.do" WHERE ID="0302";
UPDATE LIMS_MENU set URI ="/testing/cdna_extract_tasks.do" WHERE ID="0303";
UPDATE LIMS_MENU set URI ="/testing/cdna_qc_tasks.do" WHERE ID="0304";
UPDATE LIMS_MENU set URI ="/testing/wk_create_tasks.do" WHERE ID="0305";
UPDATE LIMS_MENU set URI ="/testing/wk_qc_tasks.do" WHERE ID="0306";
UPDATE LIMS_MENU set URI ="/testing/wk_catch_tasks.do" WHERE ID="0307";
UPDATE LIMS_MENU set URI ="/testing/pooling_tasks.do" WHERE ID="0308";
UPDATE LIMS_MENU set URI ="/testing/qt_tasks.do" WHERE ID="0309";
UPDATE LIMS_MENU set URI ="/testing/onTesting_tasks.do" WHERE ID="0310";
UPDATE LIMS_MENU set URI ="/testing/biAnalysis_tasks.do" WHERE ID="0311";
UPDATE LIMS_MENU set URI ="/testing/technicalAnalysis_tasks.do" WHERE ID="0312";
UPDATE LIMS_MENU set URI ="/testing/test_tasks.do" WHERE ID="0313";
--  by 薛建威      2016-11-4
INSERT INTO `lims`.`LIMS_MENU` (`ID`, `PARENT_ID`, `NAME`, `ICON`, `URI`, `SORT`) VALUES ('0101', '01', '个人主页', 'fa-user', '/personal/list.do', '1');
INSERT INTO `lims`.`LIMS_MENU` (`ID`, `PARENT_ID`, `NAME`, `ICON`, `URI`, `SORT`) VALUES ('0102', '01', '待办事项', 'fa-file', '/schedule/list.do', '2');
INSERT INTO `lims`.`LIMS_MENU` (`ID`, `PARENT_ID`, `NAME`, `ICON`, `URI`, `SORT`) VALUES ('0103', '01', '通知提醒', 'fa-file-word-o', 'notice/list.do', '3');


----by 宋健 2016年11月4日11:22:00  【先展现列表】
update LIMS_MENU  set URI ='/sampleReceiving/sampleStoragePaging.do' where id='0204'

--  by jiweicheng 2016 11/6
UPDATE LIMS_MENU set URI ="/testing/test_tasks.do" WHERE ID="0313";

--  by 薛建威      2016-11-8
INSERT INTO `lims`.`LIMS_MENU` (`ID`, `PARENT_ID`, `NAME`, `ICON`, `URI`, `SORT`) VALUES ('10', NULL, 'APP', 'fa-database', NULL, NULL);
INSERT INTO `lims`.`LIMS_MENU` (`ID`, `PARENT_ID`, `NAME`, `ICON`, `URI`, `SORT`) VALUES ('1001', '10', '客户管理', 'fa-life-ring', '/customer/list.do', '1');
INSERT INTO `lims`.`LIMS_MENU` (`ID`, `PARENT_ID`, `NAME`, `ICON`, `URI`, `SORT`) VALUES ('1002', '10', '单位管理', 'fa-life-ring', '/company/list.do', '2');

--  by 黄文涛    2016-11-8 16:23
alter table LIMS_PRODUCT add column IF_MADE tinyint(1)  COMMENT '是否定制 0：不定制 1：定制';
alter table LIMS_PRODUCT add column TEST_INSTITUTION tinyint(1)  COMMENT '检测机构 0：检验所 1：麦诺基公司';
alter table LIMS_PRODUCT add column UPDATE_TIME datetime  COMMENT '更新时间';

--  by 黄文涛    2016-11-11
DROP TABLE IF EXISTS `LIMS_DEPARTMENT`;
CREATE TABLE `LIMS_DEPARTMENT` (
  `ID` varchar(64) NOT NULL COMMENT '主键',
  `PARENT_ID` varchar(64) DEFAULT NULL COMMENT '上级部门ID',
  `NAME` varchar(128) DEFAULT NULL COMMENT '部门名称',
  `PRINCIPAL_ID` varchar(64) DEFAULT NULL COMMENT '部门负责人ID',
  `REMARK` varchar(256) DEFAULT NULL COMMENT '部门备注',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `DEL_FLAG` tinyint(1) NOT NULL COMMENT '删除标记 0-未删除 1-已删除',
  `DELETE_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

-- 丁海荣 2016-11-16 创建任务单号重置函数
DROP FUNCTION IF EXISTS resetval;
DELIMITER $$
CREATE FUNCTION resetval( seq_name varchar(255))
RETURNS VARCHAR(255)
BEGIN
     UPDATE LIMS_SEQUENCE
                   SET current_value = 1
                   WHERE name = seq_name;
         RETURN 1;
END $$
DELIMITER $$

--- 2016年11月16日14:50:59 宋健
update LIMS_SEQUENCE set `NAME`='containerSeqB8' where `NAME`='containerSeqBX80';
update LIMS_SEQUENCE set `NAME`='containerSeqTB' where `NAME`='containerSeqTBOX';
update LIMS_SEQUENCE set `NAME`='containerSeqB2' where `NAME`='containerSeqBX20';
update LIMS_SEQUENCE set `NAME`='containerSeqFC' where `NAME`='containerSeqF';

---宋健  2016年11月17日14:45:10
alter table LIMS_PHENOTYPE add DESCRIPTION varchar(500);

alter table LIMS_GENE add UNIPORT_ID varchar(64) ;
alter table LIMS_GENE add MISSENSE_NONSENSE int(11) ;
alter table LIMS_GENE add SPLICING int(11) ;
alter table LIMS_GENE add REGULATORY int(11) ;
alter table LIMS_GENE add SMALL_DELETIONS int(11) ;
alter table LIMS_GENE add SMALL_INSERTIONS int(11) ;
alter table LIMS_GENE add SMALL_INDELS int(11) ;
alter table LIMS_GENE add GROSS_DELETIONS int(11) ;
alter table LIMS_GENE add GROSS_INSERTIONS_DUPLICATIONS int(11)  ;
alter table LIMS_GENE add COMPLEX_REARRANGEMENTS int(11) ;
alter table LIMS_GENE add REPEAT_VARIATIONS int(11)  ;
--alter table LIMS_GENE add PUBLIC_TOTAL int(11) ;



--宋健  2016年11月20日10:41:24 增加基因-疾病文献
INSERT INTO `LIMS_MENU` VALUES ('0905', '基因&疾病文献报道', '/document/paging.do', '09', 'fa-signal', '5');
INSERT INTO `LIMS_RESOURCE` VALUES ('0905', '基因&疾病文献报道', '/document/paging.do');
INSERT INTO `LIMS_AUTHORITY` VALUES ('85', '8', '文献管理', null);
INSERT INTO `LIMS_AUTHORITY` VALUES ('851', '85', '文献新增', null);
INSERT INTO `LIMS_AUTHORITY` VALUES ('852', '85', '文献修改', null);
INSERT INTO `LIMS_AUTHORITY` VALUES ('853', '85', '文献删除', null);
INSERT INTO `LIMS_AUTHORITY_RESOURCE` VALUES ('85', '0905');
INSERT INTO `LIMS_ROLE_AUTHORITY` VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '85');
INSERT INTO `LIMS_ROLE_AUTHORITY` VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '851');
INSERT INTO `LIMS_ROLE_AUTHORITY` VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '852');
INSERT INTO `LIMS_ROLE_AUTHORITY` VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '853');





-- 知识库-报到文献
DROP TABLE IF EXISTS `LIMS_DOCUMENT`;
CREATE TABLE `LIMS_DOCUMENT`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `TITLE`             varchar(256)        NOT NULL        COMMENT '文献标题',
    `LOCUS`             varchar(256)                        COMMENT '报到位点',
    `REFER_BASE`        varchar(256)                        COMMENT '参考碱基',
    `MUTATION_BASE`     varchar(256)                        COMMENT '突变碱基',
    `AMINO_ACID`        varchar(256)                        COMMENT '氨基酸变化',
    `RIBOTIDE`          varchar(256)                        COMMENT '核苷酸变化',
    `VFS`               varchar(16)                         COMMENT '致病因子-字典项（VFS）',
    `LINK`              varchar(512)                        COMMENT '文献链接',
    `CREATE_TIME`       datetime            NOT NULL        COMMENT '创建时间',
    `DEL_FLAG`          tinyint(1)          NOT NULL        COMMENT '删除标记 0-未删除 1-已删除',
    `DELETE_TIME`       datetime                            COMMENT '删除时间',
    CONSTRAINT PK_DOCUMENT PRIMARY KEY (`ID`)
)
;
ALTER TABLE `LIMS_DOCUMENT` COMMENT '知识库-报到文献';

-- 知识库-文献-基因
DROP TABLE IF EXISTS `LIMS_DOCUMENT_GENE`;
CREATE TABLE `LIMS_DOCUMENT_GENE`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `DOCUMENT_ID`       varchar(64)         NOT NULL        COMMENT '文献ID',
    `GENE_ID`           varchar(64)         NOT NULL        COMMENT '基因ID',
    CONSTRAINT PK_DOCUMENT_GENE PRIMARY KEY (`ID`)
)
;
ALTER TABLE `LIMS_DOCUMENT_GENE` COMMENT '知识库-文献与基因关联表';

-- 知识库-文献-疾病
DROP TABLE IF EXISTS `LIMS_DOCUMENT_DISEASE`;
CREATE TABLE `LIMS_DOCUMENT_DISEASE`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `DOCUMENT_ID`       varchar(64)         NOT NULL        COMMENT '文献ID',
    `DISEASE_ID`        varchar(64)         NOT NULL        COMMENT '疾病ID',
    CONSTRAINT PK_DOCUMENT_DISEASE PRIMARY KEY (`ID`)
)
;
ALTER TABLE `LIMS_DOCUMENT_DISEASE` COMMENT '知识库-文献与疾病关联表';


--去掉基因多余字段
alter table `LIMS_GENE` drop column PUBLIC_TOTAL;  

--薛建威  2016年11月21日
ALTER TABLE `LIMS_CONNECT`
MODIFY COLUMN `CONNECT_NUM`  int(64) NULL DEFAULT NULL COMMENT '接头号' AFTER `ID`;

--by 黄文涛 2016-11-21 20:23 数据权限
DROP TABLE IF EXISTS `LIMS_DATA_AUTHORITY`;
CREATE TABLE `LIMS_DATA_AUTHORITY` (
  `RESOURCE_KEY` varchar(64) NOT NULL COMMENT '数据权限控制资源Key',
  `RESOURCE_NAME` varchar(128) DEFAULT NULL COMMENT '数据权限控制资源名称',
  `DEFAULT_CONFIG` tinyint(1) NOT NULL COMMENT '默认配置 1-本人 2-本人及下属 3-所属机构 4-所属机构及下属机构 5-所选机构 6-所有数据',
  `SPECIAL_DEPTS` text COMMENT '配置为选定机构时保存机构ID',
  PRIMARY KEY (`RESOURCE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据权限表';

INSERT INTO `LIMS_DATA_AUTHORITY` VALUES ('ORDER_LIST', '订单列表数据', '1', null);

DROP TABLE IF EXISTS `LIMS_DATA_AUTHORITY_ROLE`;
CREATE TABLE `LIMS_DATA_AUTHORITY_ROLE` (
  `ID` varchar(64) NOT NULL COMMENT '主键',
  `RESOURCE_KEY` varchar(64) NOT NULL COMMENT '数据权限控制资源Key',
  `ROLE_ID` varchar(64) NOT NULL COMMENT '角色ID',
  `CONFIG` tinyint(1) NOT NULL COMMENT '权限配置',
  `SPECIAL_DEPTS` text COMMENT '配置为选定机构时保存机构ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据权限-角色配置表';

DROP TABLE IF EXISTS `LIMS_DATA_AUTHORITY_USER`;
CREATE TABLE `LIMS_DATA_AUTHORITY_USER` (
  `ID` varchar(64) NOT NULL COMMENT '主键',
  `RESOURCE_KEY` varchar(64) NOT NULL COMMENT '数据权限控制资源Key',
  `USER_ID` varchar(64) NOT NULL COMMENT '用户ID',
  `CONFIG` tinyint(1) NOT NULL COMMENT '权限配置',
  `SPECIAL_DEPTS` text COMMENT '配置为选定机构时保存机构ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据权限-用户配置表';

INSERT INTO `lims`.`LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`) VALUES ('0805', '数据范围管理', '/dataAuthority/list.do', '08', 'fa-life-ring', '5');

INSERT INTO `lims`.`LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('0805', '数据范围管理', '/dataAuthority/list.do');

INSERT INTO `lims`.`LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('75', '7', '数据范围管理', NULL);

INSERT INTO `lims`.`LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('75', '0805');

INSERT INTO `lims`.`LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('21a46725345d4e26b64b529bc6328211', NULL, 'DATA_AUTHORITY_CONFIG', '数据权限配置', NULL, '14', NULL, '0');
INSERT INTO `lims`.`LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('048aeadd76424dbd92a176720149c62c', '21a46725345d4e26b64b529bc6328211', 'DATA_AUTHORITY_CONFIG', '所在机构数据', '3', '3', '数据权限配置', '0');
INSERT INTO `lims`.`LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('40231a6a198948b5bf8c7138ac062295', '21a46725345d4e26b64b529bc6328211', 'DATA_AUTHORITY_CONFIG', '选定机构数据', '5', '5', '数据权限配置', '0');
INSERT INTO `lims`.`LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('7abb7b388079459ebb59ee9fba990d93', '21a46725345d4e26b64b529bc6328211', 'DATA_AUTHORITY_CONFIG', '本人数据', '1', '1', '数据权限配置', '0');
INSERT INTO `lims`.`LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('9e997c5271074ea6878611ebbf671abe', '21a46725345d4e26b64b529bc6328211', 'DATA_AUTHORITY_CONFIG', '所有数据', '6', '6', '数据权限配置', '0');
INSERT INTO `lims`.`LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('f1361747552e48b0b60cd70fd37b22d9', '21a46725345d4e26b64b529bc6328211', 'DATA_AUTHORITY_CONFIG', '本人及下属数据', '2', '2', '数据权限配置', '0');
INSERT INTO `lims`.`LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('fd4ac99181fc4cbdbfd3216b89dd6f91', '21a46725345d4e26b64b529bc6328211', 'DATA_AUTHORITY_CONFIG', '所在机构及下属机构数据', '4', '4', '数据权限配置', '0');


-- by 丁海荣
update LIMS_MENU set name = '文库构建' where id = '0305';

DELETE from LIMS_MENU where id='0306';


--by huangwentao 2016-11-22 16:42
DROP TABLE IF EXISTS `LIMS_TESTING_TYPE`;
CREATE TABLE `LIMS_TESTING_TYPE` (
  `ID` varchar(64) NOT NULL COMMENT '主键',
  `PARENT_ID` varchar(64) DEFAULT NULL COMMENT '上级ID',
  `NAME` varchar(128) NOT NULL COMMENT '类型名称',
  `SORT` smallint(2) DEFAULT NULL COMMENT '排序编号',
  `DEL_FLAG` tinyint(1) NOT NULL COMMENT '删除标记 0-未删除 1-已删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='检测类型';


INSERT INTO `LIMS_TESTING_TYPE` VALUES ('1', null, '临床遗传', '1', '0');
INSERT INTO `LIMS_TESTING_TYPE` VALUES ('101', '1', '遗传一分类', '2', '0');
INSERT INTO `LIMS_TESTING_TYPE` VALUES ('102', '1', '遗传二分类', '3', '0');
INSERT INTO `LIMS_TESTING_TYPE` VALUES ('2', null, '临床肿瘤', '1', '0');
INSERT INTO `LIMS_TESTING_TYPE` VALUES ('201', '2', '肿瘤一类', '2', '0');
INSERT INTO `LIMS_TESTING_TYPE` VALUES ('202', '2', '肿瘤二类', '3', '0');
INSERT INTO `LIMS_TESTING_TYPE` VALUES ('3', null, '健康筛查', '1', '0');
INSERT INTO `LIMS_TESTING_TYPE` VALUES ('301', '3', '筛查一类', '2', '0');
INSERT INTO `LIMS_TESTING_TYPE` VALUES ('302', '3', '筛查二类', '3', '0');
INSERT INTO `LIMS_TESTING_TYPE` VALUES ('4', null, '科技服务', '1', '0');
INSERT INTO `LIMS_TESTING_TYPE` VALUES ('401', '4', '科技一类', '2', '0');
INSERT INTO `LIMS_TESTING_TYPE` VALUES ('402', '4', '科技二类', '3', '0');





---update by 宋健  2016年11月23日13:41:01
update  LIMS_DICT set  DICT_VALUE ='B8' where ID ='1dceefc98c6e487496426decab41870f'
update  LIMS_DICT set  DICT_VALUE ='B2' where ID ='5a746ed14db846adb29f418506f31f62'
update  LIMS_DICT set  DICT_VALUE ='FC' where ID ='8dceaf1a237845db866f7291e106488e'
update  LIMS_DICT set  DICT_VALUE ='TB' where ID ='9f06c762066540a688048948fcdc4553'


INSERT INTO `LIMS_DICT` VALUES ('111111111111111111111', 'c324fab2c315426581a02c36a89a866d', null, 'XLR', '4', '4', null, '1');
update LIMS_DICT set DICT_TEXT='XLD' ,DICT_VALUE='3',SORT='3' where ID='fe502077e0b541dbb7f7d7a0ecd80505';
update LIMS_DICT set DICT_VALUE='2',SORT='2' where ID='9b10f612162f48c585a3a3894faa7479';


-- by丁海荣 20161125 删除产物类型菜单
delete FROM `LIMS_MENU` where id='0502'
alter table LIMS_ORDER add SHEDULE_STATUS TINYINT(1) DEFAULT NULL COMMENT '流程状态初始0不可启动1可启动2已启动';

-- by 宋健 2016年11月25日12:50:05 ---把合同管理菜单放到业务管理下
INSERT INTO `LIMS_MENU` VALUES ('0206', '合同管理', '/contract/list.do', '02', 'fa-building-o', '6');
delete  from  LIMS_MENU where ID='0904';



-- by dhr 20161128 15:15 菜单增加状态字段
alter table LIMS_MENU ADD COLUMN STATUS TINYINT(1) DEFAULT 0 COMMENT '菜单状态(0显示1隐藏)';
update `LIMS_MENU` set URI='/testing/test_tasks.do' ,ICON='fa-tasks' where id = '0102';
update `LIMS_MENU` set `STATUS`= 1 where id = '0101';
update `LIMS_MENU` set `STATUS`= 1 where id = '0313';
update `LIMS_MENU` set `STATUS`= 1 where id = '0103';
update `LIMS_MENU` set `STATUS`= 1 where id = '0205';
update `LIMS_MENU` set `STATUS`= 1 where id = '0304';


-- by huangwentao 2016-11-29 16:16
INSERT INTO `lims`.`LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('asd53d362484efdgh3e5632df48230hsw', NULL, 'TEST_INSTITUTION', '检测机构', NULL, NULL, '检测机构', NULL);
INSERT INTO `lims`.`LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('asd0u194y285hr13n4215908u34j', 'asd53d362484efdgh3e5632df48230hsw', NULL, '迈诺基', '1', NULL, NULL, NULL);
INSERT INTO `lims`.`LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('oki239dhnkj2390137dsbh3202309w', 'asd53d362484efdgh3e5632df48230hsw', NULL, '检验所', '0', NULL, NULL, NULL);

-- by 薛建威 2016-11-30
INSERT INTO LIMS_SEQUENCE VALUES('160db301dde34974a8061c68adaa77c0','contractCode',0,1)

--by jiweicheng 2016-11-30 创建系统配置管理
INSERT INTO `LIMS_MENU` VALUES ('0508', '系统参数配置', '/config/list.do', '05', ' fa-book',8,'0');

--by 孔德成   2016-11-30  系统配置增加字段
DROP TABLE IF EXISTS `LIMS_CONFIG`;
CREATE TABLE `LIMS_CONFIG` (
  `ID` varchar(64) NOT NULL COMMENT '主键',
  `CONFIG_NAME` varchar(64) DEFAULT NULL COMMENT '配置名称',
  `CONFIG_KEY` varchar(64) NOT NULL COMMENT '配置Key',
  `CONFIG_VALUE` varchar(64) DEFAULT NULL COMMENT '配置值',
  `MAINTAIN_TYPE` tinyint(1) NOT NULL COMMENT '维护类型 0-显示 1-不显示',
  `VALUE_TYPE` tinyint(1) NOT NULL COMMENT '值类型 1-数字 2-字符串',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统配置表';

INSERT INTO `LIMS_CONFIG` VALUES ('057bca6f211c47999367a8abd32dd281', null, 'SUBSIDIARY_SAMPLE_BASE', '2', '0', '1');
INSERT INTO `LIMS_CONFIG` VALUES ('777bc63cee0b4918ae57648498a0bd64', '相对定量样本投入体积', 'RQT_LIBRARY_SAMPLE_INPUT_VOLUME', '5', '0', '1');
INSERT INTO `LIMS_CONFIG` VALUES ('8d9fae84122b4fc9896032719c8836ab', null, 'STARTABLE_SAMPLE', '1000c3317cd0486890167d94e60820ad', '0', '2');
INSERT INTO `LIMS_CONFIG` VALUES ('ab11240196434289ad4daa3017fc0e0e', null, 'STARTABLE_SAMPLE', '1002c10cca5b4ecc86d94108eadfe067', '0', '2');
INSERT INTO `LIMS_CONFIG` VALUES ('ab9f144df51d4195afd92a7e945ff02f', null, 'RQT_CAPTURE_SAMPLE_INPUT_VOLUME', '35', '0', '1');
INSERT INTO `LIMS_CONFIG` VALUES ('b0b2ca0011074daf8ccecaa246ced855', null, 'SUBSIDIARY_SAMPLE_EXTRA', '100', '0', '1');
INSERT INTO `LIMS_CONFIG` VALUES ('c5b079328e6d47c2bbf9a4568e425304', null, 'LIBRARY_CAPTURE_INPUT_SIZE', '500', '0', '1');




--by  宋健 2016年12月1日09:17:44
alter table LIMS_RECEIVED_SAMPLE add SAMPLE_SIZE int(5) COMMENT '样本量';

--by  jiweicheng 2016年12月4日
ALTER TABLE `LIMS_TASK_INPUT_CONFIG`
MODIFY COLUMN `INPUT_SIZE`  decimal(10,2) NULL DEFAULT NULL COMMENT '投入样本量' AFTER `REAGENT_KIT`,
MODIFY COLUMN `INPUT_VOLUME`  decimal(10,2) NULL DEFAULT NULL COMMENT '投入样本体积' AFTER `INPUT_SIZE`;


--by 宋健 2016年12月5日14:38:22
INSERT INTO LIMS_SEQUENCE VALUES ('orderCodeSeq', 0, 1);

--by jiweicheng 2016年12月7日
ALTER TABLE `LIMS_PRIMER`
MODIFY COLUMN `START_LOCUS`  int(20) NULL DEFAULT NULL COMMENT 'PCR起始点' AFTER `CHROMOSOME_LOCATION`,
MODIFY COLUMN `END_LOCUS`  int(20) NULL DEFAULT NULL COMMENT '终止点' AFTER `START_LOCUS`;

--by 孔德成  2016年12月9日
UPDATE `lims`.`LIMS_METADATA_SAMPLE` SET `ID`='1000c3317cd0486890167d94e60820ad', `NAME`='基因组DNA', `TESTING_UNIT`='2', `INTERMEDIATE`='1', `STORAGE_TYPE`='B2', `RECEIVING_UNIT`='2', `LS_SIZE`='100', `SAMPLING_TIPS`='11', `TRANSPORTING_TIPS`='11', `STORAGING_TIPS`='11', `DEL_FLAG`='0' WHERE (`ID`='1000c3317cd0486890167d94e60820ad');
UPDATE `lims`.`LIMS_METADATA_SAMPLE` SET `ID`='10016a775b7a48adafcac610c3f66d1e', `NAME`='基因组RNA', `TESTING_UNIT`='2', `INTERMEDIATE`='1', `STORAGE_TYPE`='B8', `RECEIVING_UNIT`='2', `LS_SIZE`=NULL, `SAMPLING_TIPS`=NULL, `TRANSPORTING_TIPS`=NULL, `STORAGING_TIPS`=NULL, `DEL_FLAG`='0' WHERE (`ID`='10016a775b7a48adafcac610c3f66d1e');
UPDATE `lims`.`LIMS_METADATA_SAMPLE` SET `ID`='1002c10cca5b4ecc86d94108eadfe067', `NAME`='CDNA', `TESTING_UNIT`='2', `INTERMEDIATE`='1', `STORAGE_TYPE`='B2', `RECEIVING_UNIT`='2', `LS_SIZE`=NULL, `SAMPLING_TIPS`=NULL, `TRANSPORTING_TIPS`=NULL, `STORAGING_TIPS`=NULL, `DEL_FLAG`='0' WHERE (`ID`='1002c10cca5b4ecc86d94108eadfe067');
UPDATE `lims`.`LIMS_METADATA_SAMPLE` SET `ID`='10030e97d46d4b0d93337a1aeb92812c', `NAME`='全血', `TESTING_UNIT`='5fdfd0b0548e43c7b35b84cebc3ba90c', `INTERMEDIATE`='1', `STORAGE_TYPE`='B2', `RECEIVING_UNIT`='5fdfd0b0548e43c7b35b84cebc3ba90c', `LS_SIZE`='2000', `SAMPLING_TIPS`='34r4', `TRANSPORTING_TIPS`='ee', `STORAGING_TIPS`='', `DEL_FLAG`='0' WHERE (`ID`='10030e97d46d4b0d93337a1aeb92812c');
UPDATE `lims`.`LIMS_METADATA_SAMPLE` SET `ID`='1004f9f08a834cedb2fa74722581d35d', `NAME`='血片', `TESTING_UNIT`='574d2c1dae6946809638e3b8f579499b', `INTERMEDIATE`='1', `STORAGE_TYPE`='TB', `RECEIVING_UNIT`='574d2c1dae6946809638e3b8f579499b', `LS_SIZE`='1', `SAMPLING_TIPS`=NULL, `TRANSPORTING_TIPS`=NULL, `STORAGING_TIPS`=NULL, `DEL_FLAG`='0' WHERE (`ID`='1004f9f08a834cedb2fa74722581d35d');
UPDATE `lims`.`LIMS_METADATA_SAMPLE` SET `ID`='1005ab3a28464cda9bfcf25a1ef315a0', `NAME`='血浆', `TESTING_UNIT`='5fdfd0b0548e43c7b35b84cebc3ba90c', `INTERMEDIATE`='1', `STORAGE_TYPE`='B8', `RECEIVING_UNIT`='5fdfd0b0548e43c7b35b84cebc3ba90c', `LS_SIZE`='2000', `SAMPLING_TIPS`=NULL, `TRANSPORTING_TIPS`=NULL, `STORAGING_TIPS`=NULL, `DEL_FLAG`='0' WHERE (`ID`='1005ab3a28464cda9bfcf25a1ef315a0');
UPDATE `lims`.`LIMS_METADATA_SAMPLE` SET `ID`='10066d3b9b38494db2bdb1cc56afa9b9', `NAME`='新鲜组织', `TESTING_UNIT`='4eb5b9b0368e4b6b9b3e52aa2a31682e', `INTERMEDIATE`='1', `STORAGE_TYPE`='TB', `RECEIVING_UNIT`='4eb5b9b0368e4b6b9b3e52aa2a31682e', `LS_SIZE`='50', `SAMPLING_TIPS`='', `TRANSPORTING_TIPS`='', `STORAGING_TIPS`='', `DEL_FLAG`='0' WHERE (`ID`='10066d3b9b38494db2bdb1cc56afa9b9');
UPDATE `lims`.`LIMS_METADATA_SAMPLE` SET `ID`='1007666fef20464884714d103cd51d19', `NAME`='石蜡组织', `TESTING_UNIT`='4eb5b9b0368e4b6b9b3e52aa2a31682e', `INTERMEDIATE`='1', `STORAGE_TYPE`='FC', `RECEIVING_UNIT`='4eb5b9b0368e4b6b9b3e52aa2a31682e', `LS_SIZE`=NULL, `SAMPLING_TIPS`=NULL, `TRANSPORTING_TIPS`=NULL, `STORAGING_TIPS`=NULL, `DEL_FLAG`='0' WHERE (`ID`='1007666fef20464884714d103cd51d19');
UPDATE `lims`.`LIMS_METADATA_SAMPLE` SET `ID`='100847a67de94a8f846523bebac48fc9', `NAME`='福尔马林组织', `TESTING_UNIT`='5fdfd0b0548e43c7b35b84cebc3ba90c', `INTERMEDIATE`='1', `STORAGE_TYPE`='B2', `RECEIVING_UNIT`='5fdfd0b0548e43c7b35b84cebc3ba90c', `LS_SIZE`=NULL, `SAMPLING_TIPS`=NULL, `TRANSPORTING_TIPS`=NULL, `STORAGING_TIPS`=NULL, `DEL_FLAG`='0' WHERE (`ID`='100847a67de94a8f846523bebac48fc9');
UPDATE `lims`.`LIMS_METADATA_SAMPLE` SET `ID`='1009612f45d54758b584437cc9822ec7', `NAME`='石蜡DNA', `TESTING_UNIT`='4eb5b9b0368e4b6b9b3e52aa2a31682e', `INTERMEDIATE`='1', `STORAGE_TYPE`='B2', `RECEIVING_UNIT`='4eb5b9b0368e4b6b9b3e52aa2a31682e', `LS_SIZE`=NULL, `SAMPLING_TIPS`=NULL, `TRANSPORTING_TIPS`=NULL, `STORAGING_TIPS`=NULL, `DEL_FLAG`='0' WHERE (`ID`='1009612f45d54758b584437cc9822ec7');
UPDATE `lims`.`LIMS_METADATA_SAMPLE` SET `ID`='101068dbf2cc489dbe9d8999a6b20188', `NAME`='唾液', `TESTING_UNIT`='5fdfd0b0548e43c7b35b84cebc3ba90c', `INTERMEDIATE`='1', `STORAGE_TYPE`='B2', `RECEIVING_UNIT`='5fdfd0b0548e43c7b35b84cebc3ba90c', `LS_SIZE`='2000', `SAMPLING_TIPS`=NULL, `TRANSPORTING_TIPS`=NULL, `STORAGING_TIPS`=NULL, `DEL_FLAG`='0' WHERE (`ID`='101068dbf2cc489dbe9d8999a6b20188');
UPDATE `lims`.`LIMS_METADATA_SAMPLE` SET `ID`='1011b0fc546044a1843da048f109779e', `NAME`='尿液', `TESTING_UNIT`='5fdfd0b0548e43c7b35b84cebc3ba90c', `INTERMEDIATE`='1', `STORAGE_TYPE`='B2', `RECEIVING_UNIT`='5fdfd0b0548e43c7b35b84cebc3ba90c', `LS_SIZE`='2000', `SAMPLING_TIPS`=NULL, `TRANSPORTING_TIPS`=NULL, `STORAGING_TIPS`=NULL, `DEL_FLAG`='0' WHERE (`ID`='1011b0fc546044a1843da048f109779e');
UPDATE `lims`.`LIMS_METADATA_SAMPLE` SET `ID`='1012526918e7499e8e3d08ae0e8a9535', `NAME`='骨髓液', `TESTING_UNIT`='5fdfd0b0548e43c7b35b84cebc3ba90c', `INTERMEDIATE`='1', `STORAGE_TYPE`='B2', `RECEIVING_UNIT`='5fdfd0b0548e43c7b35b84cebc3ba90c', `LS_SIZE`='2000', `SAMPLING_TIPS`=NULL, `TRANSPORTING_TIPS`=NULL, `STORAGING_TIPS`=NULL, `DEL_FLAG`='0' WHERE (`ID`='1012526918e7499e8e3d08ae0e8a9535');
UPDATE `lims`.`LIMS_METADATA_SAMPLE` SET `ID`='10135b130d4f4d468f9710b43251c87d', `NAME`='脑脊液', `TESTING_UNIT`='5fdfd0b0548e43c7b35b84cebc3ba90c', `INTERMEDIATE`='1', `STORAGE_TYPE`='B2', `RECEIVING_UNIT`='5fdfd0b0548e43c7b35b84cebc3ba90c', `LS_SIZE`=NULL, `SAMPLING_TIPS`=NULL, `TRANSPORTING_TIPS`=NULL, `STORAGING_TIPS`=NULL, `DEL_FLAG`='0' WHERE (`ID`='10135b130d4f4d468f9710b43251c87d');
UPDATE `lims`.`LIMS_METADATA_SAMPLE` SET `ID`='1014ab54047d4226a2c895add1bdb0d4', `NAME`='羊水', `TESTING_UNIT`='5fdfd0b0548e43c7b35b84cebc3ba90c', `INTERMEDIATE`='1', `STORAGE_TYPE`='B2', `RECEIVING_UNIT`='5fdfd0b0548e43c7b35b84cebc3ba90c', `LS_SIZE`='2000', `SAMPLING_TIPS`=NULL, `TRANSPORTING_TIPS`=NULL, `STORAGING_TIPS`=NULL, `DEL_FLAG`='0' WHERE (`ID`='1014ab54047d4226a2c895add1bdb0d4');
UPDATE `lims`.`LIMS_METADATA_SAMPLE` SET `ID`='1015dbbced9546a3a0cc199ff48cf6e2', `NAME`='细胞', `TESTING_UNIT`='4eb5b9b0368e4b6b9b3e52aa2a31682e', `INTERMEDIATE`='1', `STORAGE_TYPE`='B2', `RECEIVING_UNIT`='4eb5b9b0368e4b6b9b3e52aa2a31682e', `LS_SIZE`=NULL, `SAMPLING_TIPS`=NULL, `TRANSPORTING_TIPS`=NULL, `STORAGING_TIPS`=NULL, `DEL_FLAG`='0' WHERE (`ID`='1015dbbced9546a3a0cc199ff48cf6e2');
UPDATE `lims`.`LIMS_METADATA_SAMPLE` SET `ID`='2000d8debb1f4c3e825dd5a3f4de4966', `NAME`='文库', `TESTING_UNIT`='2', `INTERMEDIATE`='2', `STORAGE_TYPE`='B2', `RECEIVING_UNIT`='2', `LS_SIZE`=NULL, `SAMPLING_TIPS`=NULL, `TRANSPORTING_TIPS`=NULL, `STORAGING_TIPS`=NULL, `DEL_FLAG`='0' WHERE (`ID`='2000d8debb1f4c3e825dd5a3f4de4966');
UPDATE `lims`.`LIMS_METADATA_SAMPLE` SET `ID`='200173e8bd454bb7b860e1113c01ab8c', `NAME`='捕获产物', `TESTING_UNIT`='2', `INTERMEDIATE`='2', `STORAGE_TYPE`='B2', `RECEIVING_UNIT`='2', `LS_SIZE`=NULL, `SAMPLING_TIPS`=NULL, `TRANSPORTING_TIPS`=NULL, `STORAGING_TIPS`=NULL, `DEL_FLAG`='0' WHERE (`ID`='200173e8bd454bb7b860e1113c01ab8c');
UPDATE `lims`.`LIMS_METADATA_SAMPLE` SET `ID`='200208b3f0604ac7a14b4ec4668db4e8', `NAME`='混样产物', `TESTING_UNIT`='2', `INTERMEDIATE`='2', `STORAGE_TYPE`='B2', `RECEIVING_UNIT`='2', `LS_SIZE`=NULL, `SAMPLING_TIPS`=NULL, `TRANSPORTING_TIPS`=NULL, `STORAGING_TIPS`=NULL, `DEL_FLAG`='0' WHERE (`ID`='200208b3f0604ac7a14b4ec4668db4e8');


-- by jiweicheng 2016年12月13日
ALTER TABLE `LIMS_TASK_CONFIG`
MODIFY COLUMN `OUTPUT_VOLUME`  decimal(10,2) NULL DEFAULT NULL COMMENT '输出样本体积' AFTER `OUTPUT_SAMPLE`;
=======



---------------------------------以下开始是正式环境的增量脚本----------------------------------------------------
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('1202', '减免申请管理', '/orderApply/reduceList.do', '12', 'fa-database', '2', '0');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('1202', '减免申请管理', '/orderApply/reduceList.do');
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('11-2-', '11-', '减免申请管理', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('11-2-0', '11-2-', '减免申请查看', NULL);
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('11-2-0', '1202');
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('1203', '延迟付款申请管理', '/orderApply/delayList.do', '12', 'fa-database', '2', '0');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('1203', '延迟付款申请管理', '/orderApply/delayList.do');
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('11-3-', '11-', '延迟付款申请管理', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('11-3-0', '11-3-', '延迟付款申请查看', NULL);
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('11-3-0', '1203');

alter table LIMS_SEQUENCE ADD COLUMN BATCH_DATE datetime COMMENT '日期';
INSERT INTO `LIMS_SEQUENCE` (`ID`, `NAME`, `CURRENT_VALUE`, `INCREMENT`, `BATCH_DATE`) VALUES ('561712e9c5e711e6bf600242ac110003', 'technicalCombineBatch', '0', '1', NULL);


--------2016-12-20 20:33 by 黄文涛   缴费相关增量脚本
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('1304', '需补款订单', '/payment/replenishmentList.do', '13', 'fa-futbol-o', '4', '0');
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('1305', '需退款订单', '/payment/refundmentList.do', '13', 'fa-adjust', '5', '0');
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('1306', '订单缴费列表', '/payment/paymentHistory.do', '13', 'fa-adjust', '6', '0');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('1304', '需补款订单', '/payment/replenishmentList.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('1305', '需退款订单', '/payment/refundmentList.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('1306', '订单缴费列表', '/payment/paymentHistory.do');
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('12-4-', '12-', '需补款订单', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('12-4-0', '12-4-', '需补款查看', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('12-5-', '12-', '需退款订单', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('12-5-0', '12-5-', '需退款查看', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('12-6-', '12-', '订单缴费列表', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('12-6-0', '12-6-', '订单缴费列表查看', NULL);
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('12-4-0', '1304');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('12-5-0', '1305');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('12-6-0', '1306');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '12-4-');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '12-4-0');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '12-5-');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '12-5-0');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '12-6-');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '12-6-0');

update LIMS_MENU set URI='/payment/delayConfirmList.do' where id='1303'
update LIMS_RESOURCE set URI='/payment/delayConfirmList.do' where id='1303'




--------2016-12-20 20:33 by 宋健   缴费相关全量脚本
---订单-转账费用表
DROP TABLE IF EXISTS `LIMS_ORDER_TRANSFER`;
CREATE TABLE `LIMS_ORDER_TRANSFER` (
  `ID` varchar(64) NOT NULL COMMENT '主键',
  `ORDER_ID` varchar(64) NOT NULL COMMENT '订单ID',
  `PAYMENT_INSTRUMENT_IMG` varchar(256) DEFAULT NULL COMMENT '支付凭证图片',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务库-订单转账支付';




---订单-费用确认表
DROP TABLE IF EXISTS `LIMS_ORDER_PAYMENT_CONFIRM`;
CREATE TABLE `LIMS_ORDER_PAYMENT_CONFIRM` (
  `ID` varchar(64) NOT NULL COMMENT '主键',
  `ORDER_ID` varchar(64) NOT NULL COMMENT '订单ID',
  `OPOS_ID` varchar(64) DEFAULT NULL COMMENT 'POS记录ID',
  `OTRS_ID` varchar(64) DEFAULT NULL COMMENT '转账记录ID',
  `CHECK_ID` varchar(64) NOT NULL COMMENT '确认人ID',
  `CHECK_NAME` varchar(64) NOT NULL COMMENT '确认人姓名',
  `CHECK_TIME` datetime DEFAULT NULL COMMENT '确认日期',
  `PAYMENT_TIME` datetime DEFAULT NULL COMMENT '缴费日期',
  `CHECK_AMOUNT` decimal DEFAULT NULL COMMENT '确认到账金额',
  `REMARK` varchar(512) DEFAULT NULL COMMENT '备注',  
  `PAYMENTER` varchar(64) NOT NULL COMMENT '付款人',
  `PAYMENT_TYPE` tinyint(1) DEFAULT NULL COMMENT '收款类别：1收款；2补款',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务库-订单-费用确认表';


---订单-退款确认表
DROP TABLE IF EXISTS `LIMS_ORDER_REFUND_RECORD`;
CREATE TABLE `LIMS_ORDER_REFUND_RECORD` (
  `ID` varchar(64) NOT NULL COMMENT '主键',
  `ORDER_ID` varchar(64) DEFAULT NULL COMMENT '订单ID',
  `REFUND_AMOUNT` int(11) DEFAULT NULL COMMENT '退款金额',
  `REFUND_TIME` datetime DEFAULT NULL COMMENT '退款时间',
  `REFUND_NAME` varchar(64) DEFAULT NULL COMMENT '退款人姓名',
  `OPERATE_ID` varchar(64) DEFAULT NULL COMMENT '操作人ID',
  `OPERATE_NAME` varchar(64) DEFAULT NULL COMMENT '操作人姓名',
  `OPERATE_TIME` datetime DEFAULT NULL COMMENT '操作时间',
  `OPERATE_IMG` varchar(64) DEFAULT NULL COMMENT '退款凭证',
  `REMARK` varchar(1024) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='退款确认表';


---函数
DELIMITER $$
USE `lims`$$
DROP FUNCTION IF EXISTS `getOrderBalanceFun`$$
CREATE FUNCTION `getOrderBalanceFun`(p1 int,p2 int,p3 int ,p4 int) RETURNS INT(11)
BEGIN

  DECLARE return_val INT;
  set return_val = p1+p2-p3-p4;
   
  RETURN return_val;
END$$
DELIMITER ;

---视图
DROP VIEW IF EXISTS `LIMS_ORDER_PAYMENT_VIEW`;
CREATE VIEW `LIMS_ORDER_PAYMENT_VIEW` (ID, ORDER_ID,PROTO_ID, PAYMENT_TYPE, PAYMENT_AMOUNT, PAYMENT_CREATOR, PAYMENT_TIME,REMARK) AS 
    SELECT CONCAT_WS('-', '1', PS.ID) AS ID, PS.ORDER_ID AS ORDER_ID,PS.ID AS PROT0_ID, PS.PAYMENT_TYPE, PS.CHECK_AMOUNT AS CHECK_AMOUNT,  PS.CHECK_NAME AS CHECK_NAME, PS.CHECK_TIME AS CHECK_TIME ,PS.REMARK AS REMARK
    FROM LIMS_ORDER_PAYMENT_CONFIRM PS
UNION ALL
    SELECT CONCAT_WS('-', '2', SS.ID) AS ID, SS.ORDER_ID AS ORDER_ID, SS.ID AS PROTO_ID,3, SS.REFUND_AMOUNT AS REFUND_AMOUNT, SS.OPERATE_NAME AS OPERATE_NAME, SS.OPERATE_TIME AS OPERATE_TIME,  SS.REMARK AS REMARK
    FROM LIMS_ORDER_REFUND_RECORD SS
;

---更新之前样本视图，补充一个科技样本的收样收件
DROP VIEW IF EXISTS `LIMS_ORDER_SAMPLE_VIEW`;
CREATE VIEW `LIMS_ORDER_SAMPLE_VIEW` (ID, ORDER_ID, SAMPLE_ID, SAMPLE_BTYPE, SAMPLE_TYPE, SAMPLE_CODE, SAMPLE_NAME, SAMPLE_SIZE, SAMPLING_DATE, PURPOSE) AS 
    SELECT CONCAT_WS('-', '1', PS.ID) AS ID, PS.ORDER_ID AS ORDER_ID, PS.ID, 1, PS.SAMPLE_TYPE_ID AS SAMPLE_TYPE, PS.SAMPLE_CODE AS SAMPLE_CODE, OE.NAME AS SAMPLE_NAME, PS.SAMPLE_SIZE AS SAMPLE_SIZE, PS.SAMPLING_DATE AS SAMPLING_DATE, NULL
    FROM LIMS_ORDER_PRIMARY_SAMPLE PS LEFT JOIN LIMS_ORDER_EXAMINEE OE ON PS.EXAMINEE_ID = OE.ID
UNION ALL
    SELECT CONCAT_WS('-', '2', SS.ID) AS ID, SS.ORDER_ID AS ORDER_ID, SS.ID, 2, SS.SAMPLE_TYPE_ID AS SAMPLE_TYPE, SS.SAMPLE_CODE AS SAMPLE_CODE, SS.OWNER_NAME AS SAMPLE_NAME, SS.SAMPLE_SIZE AS SAMPLE_SIZE, SS.SAMPLING_DATE AS SAMPLING_DATE, SS.PURPOSE AS PURPOSE
    FROM LIMS_ORDER_SUBSIDIARY_SAMPLE SS
UNION ALL
    SELECT CONCAT_WS('-', '3', RS.ID) AS ID, RS.ORDER_ID AS ORDER_ID, RS.ID, 3, RS.SAMPLE_TYPE_ID AS SAMPLE_TYPE, RS.SAMPLE_CODE AS SAMPLE_CODE, RS.SAMPLE_NAME AS SAMPLE_NAME, RS.SAMPLE_SIZE AS SAMPLE_SIZE,RS.SAMPLING_DATE AS SAMPLING_DATE, NULL
    FROM LIMS_ORDER_RESEARCH_SAMPLE RS
;


---给存储容器增加样本类型字段
alter table  LIMS_STORAGE_DEVICE add SAMPLE_TYPE varchar(64) DEFAULT NULL COMMENT '样本类型'; 





-- 2016-12-22 20:00 by 黄文涛 
INSERT INTO `lims`.`LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('0325', 'MLPA实验', '/testing/mlpa_tasks.do', '03', 'fa-database', '21', '0');
INSERT INTO `lims`.`LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('0325', 'MLPA实验', '/testing/mlpa_tasks.do');
INSERT INTO `lims`.`LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('3-10', '3', 'MLPA实验', NULL);
INSERT INTO `lims`.`LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('3-10-0', '3-10', 'MLPA实验查看', NULL);
INSERT INTO `lims`.`LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('3-10-0', '0325');
INSERT INTO `lims`.`LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '3-10');
INSERT INTO `lims`.`LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '3-10-0');


INSERT INTO `lims`.`LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('0326', 'MLPA数据分析', '/testing/mlpa_data_tasks.do', '03', 'fa-database', '22', '0');
INSERT INTO `lims`.`LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('0326', 'MLPA数据分析', '/testing/mlpa_data_tasks.do');
INSERT INTO `lims`.`LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('3-11', '3', 'MLPA数据分析', NULL);
INSERT INTO `lims`.`LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('3-11-0', '3-11', 'MLPA数据分析查看', NULL);
INSERT INTO `lims`.`LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('3-11-0', '0326');
INSERT INTO `lims`.`LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '3-11');
INSERT INTO `lims`.`LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '3-11-0');



-- by 孔德成 2016年12月23日
UPDATE `lims`.`LIMS_CONFIG` SET `CONFIG_NAME`='免费附属样本数(个)' WHERE (`ID`='057bca6f211c47999367a8abd32dd281');
UPDATE `lims`.`LIMS_CONFIG` SET `CONFIG_NAME`='一次PCR加样体积最小值(ul)' WHERE (`ID`='49550f01bd2111e6bf600242ac110003');
UPDATE `lims`.`LIMS_CONFIG` SET `CONFIG_NAME`='文库相对定量默认投入量(ul)' WHERE (`ID`='777bc63cee0b4918ae57648498a0bd64');
UPDATE `lims`.`LIMS_CONFIG` SET `CONFIG_NAME`='一次PCR加样体积最大值(ul)' WHERE (`ID`='95be1f65bd2111e6bf600242ac110003');
UPDATE `lims`.`LIMS_CONFIG` SET `CONFIG_NAME`='绝对定量默认片段长度(bp)' WHERE (`ID`='a9ea5097f6de477da4e47eb440247d03');
UPDATE `lims`.`LIMS_CONFIG` SET `CONFIG_NAME`='捕获产物相对定量默认投入量(ul)' WHERE (`ID`='ab9f144df51d4195afd92a7e945ff02f');
UPDATE `lims`.`LIMS_CONFIG` SET `CONFIG_NAME`='额外附属样本收费金额(分)' WHERE (`ID`='b0b2ca0011074daf8ccecaa246ced855');
UPDATE `lims`.`LIMS_CONFIG` SET `CONFIG_NAME`='文库捕获默认投入量(ng)' WHERE (`ID`='c5b079328e6d47c2bbf9a4568e425304');

---确认到账记录表增加 支付类型 字段
alter table  LIMS_ORDER_PAYMENT_CONFIRM add `PAY_TYPE` tinyint(1) DEFAULT NULL COMMENT '支付类型，0：支付宝；1：微信；3：POS机；4：转账';


-- 调整三级菜单 2016-12-27 by huangwentao
delete from LIMS_MENU where id='0321';
delete from LIMS_MENU where id='0322';
delete from LIMS_MENU where id='0323';
delete from LIMS_MENU where id='0324';

delete from LIMS_RESOURCE where id='0321';
delete from LIMS_RESOURCE where id='0322';
delete from LIMS_RESOURCE where id='0323';
delete from LIMS_RESOURCE where id='0324';

delete from LIMS_AUTHORITY where id='3030';
delete from LIMS_AUTHORITY where id='3031';
delete from LIMS_AUTHORITY where id='3040';
delete from LIMS_AUTHORITY where id='3041';
delete from LIMS_AUTHORITY where id='3050';
delete from LIMS_AUTHORITY where id='3051';
delete from LIMS_AUTHORITY where id='3060';
delete from LIMS_AUTHORITY where id='3061';

delete from LIMS_AUTHORITY_RESOURCE where authority_id='3031';
delete from LIMS_AUTHORITY_RESOURCE where authority_id='3041';
delete from LIMS_AUTHORITY_RESOURCE where authority_id='3051';
delete from LIMS_AUTHORITY_RESOURCE where authority_id='3061';

delete from LIMS_ROLE_AUTHORITY where authority_id='3030';
delete from LIMS_ROLE_AUTHORITY where authority_id='3031';
delete from LIMS_ROLE_AUTHORITY where authority_id='3040';
delete from LIMS_ROLE_AUTHORITY where authority_id='3041';
delete from LIMS_ROLE_AUTHORITY where authority_id='3050';
delete from LIMS_ROLE_AUTHORITY where authority_id='3051';
delete from LIMS_ROLE_AUTHORITY where authority_id='3060';
delete from LIMS_ROLE_AUTHORITY where authority_id='3061';


INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('0327', 'Sanger验证', '', '03', 'fa-adjust', '27', '0');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('0327', 'Sanger验证', '');
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('032701', '一次PCR', '/testing/pcrOneList.do', '0327', 'fa-adjust', '1', '0');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('032701', '一次PCR', '/testing/pcrOneList.do');
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('032702', '引物设计合成', '/testing/primer_design_tasks.do', '0327', 'fa-tasks', '0', '0');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('032702', '引物设计合成', '/testing/primer_design_tasks.do');
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('032703', '二次PCR', '/testing/pcrTwoList.do', '0327', 'fa-tasks', '19', '0');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('032703', '二次PCR', '/testing/pcrTwoList.do');
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('032704', '数据分析', '/testing/dataAnalysisList.do', '0327', 'fa-tasks', '20', '0');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('032704', '数据分析', '/testing/dataAnalysisList.do');




INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('3-12-', '3', 'Sanger验证', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('3-12-2', '3-12-', '引物设计合成', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('3-12-2-0', '3-12-2', '引物设计合成查看', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('3-12-1', '3-12-', '一次PCR', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('3-12-1-0', '3-12-1', '一次PCR查看', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('3-12-3', '3-12-', '二次PCR', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('3-12-3-0', '3-12-3', '二次PCR查看', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('3-12-4', '3-12-', '数据分析', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('3-12-4-0', '3-12-4', '数据分析查看', NULL);


INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('3-12-1-0', '032701');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('3-12-2-0', '032702');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('3-12-3-0', '032703');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('3-12-4-0', '032704');



INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '3-12-');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '3-12-1');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '3-12-1-0');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '3-12-2');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '3-12-2-0');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '3-12-3');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '3-12-3-0');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '3-12-4');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '3-12-4-0');

-- 增加样本出入库 by huangwentao 2016-12-27 15:00
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('0330', '实验样本出入库', '/testingSheetSampleStorage/tab.do', '03', 'fa-adjust', '30', '0');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('0330', '实验样本出入库', '/testingSheetSampleStorage/tab.do');
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('3-30', '3', '实验样本出入库', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('3-30-0', '3-30', '实验样本出入库查看', NULL);
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('3-30-0', '0330');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '3-30');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '3-30-0');

alter table  LIMS_ORDER_PAYMENT_CONFIRM add `PAY_TYPE` tinyint(1) DEFAULT NULL COMMENT '支付类型，0：支付宝；1：微信；3：POS机；4：转账';

-- 解决bug 更改数据字典顺序 宋健2016年12月29日08:32:51
delete from LIMS_DICT  where ID ='24790816161530094' or PARENT_ID='24790816161530094';
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('24790816161530094', null, 'ANALYSIS_TYPE', '是否参与分析', null, '23', null, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('24790816161530098', '24790816161530094', 'ANALYSIS_TYPE', '不参与', '0/0', '1', '是否参与分析', '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('24790816161530095', '24790816161530094', 'ANALYSIS_TYPE', '参与技术分析', '1/0', '2', '是否参与分析', '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('24790816161530096', '24790816161530094', 'ANALYSIS_TYPE', '参与报告评审', '0/1', '3', '是否参与分析', '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('24790816161530097', '24790816161530094', 'ANALYSIS_TYPE', '参与技术分析与报告评审', '1/1', '4', '是否参与分析', '0');

---增加检测样本管理菜单 2016年12月29日08:32:54
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`)  VALUES ('28', '2', '检测样本管理', null);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`)  VALUES ('280', '28', '检测样本详情', null);
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('280', '0208');
INSERT INTO `LIMS_RESOURCE`  (`ID`, `NAME`, `URI`) VALUES ('0208', '检测样本管理', '/order/orderSampleList.do');
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('0208', '检测样本管理', '/order/orderSampleList.do', '02', 'fa-bullseye', '8', '0');

-- 增加历史任务菜单   孔德成  2016年12月29日
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('0398', '已完成任务列表', '/bpm/completeTasks/tasks.do', '03', 'fa-futbol-o', '98', '0');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('0398', '已完成任务列表', '/bpm/completeTasks/tasks.do');
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('039801', '0398', '已完成任务查看', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('039802', '0398', '已完成任务修改', NULL);
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('0398', '0398');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '0398');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '039801');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '039802');


---增加样本类型、样本名称 宋健 2017年1月3日09:20:08
alter table LIMS_SAMPLE_STORAGING_DETAIL add column SAMPLE_TYPE varchar(64) DEFAULT NULL COMMENT '样本类型';
alter table LIMS_SAMPLE_STORAGING_DETAIL add column SAMPLE_NAME varchar(64) DEFAULT NULL COMMENT '样本名称';

-- 增加APP_用戶反饋導航   jiweicheng  2017年1月4日
INSERT INTO `lims`.`LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('1004', '用户反馈管理', '/userFeedback/list.do', '10', 'fa-tasks', '4', '0');
INSERT INTO `lims`.`LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('1004', '用户反馈管理', '/userFeedback/list.do');
INSERT INTO `lims`.`LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('94', '9', '用户反馈管理', NULL);
INSERT INTO `lims`.`LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('940', '94', '用户反馈查看', NULL);
INSERT INTO `lims`.`LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('94', '1004');
INSERT INTO `lims`.`LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '94');
INSERT INTO `lims`.`LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '940');

---菜单sql修改 2017年1月4日
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('0398', '3', '已完成任务管理', '98');
DELETE FROM LIMS_AUTHORITY_RESOURCE WHERE AUTHORITY_ID = '0398';
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('039801', '0398');


--- 增加合同订单管理 菜单项 宋健 2017年1月4日11:19:32
INSERT INTO `LIMS_MENU`(`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('0209', '合同订单管理', '/order/orderContractManager.do', '02', 'fa-tasks', '9', '0');
INSERT INTO `LIMS_RESOURCE`(`ID`, `NAME`, `URI`)  VALUES ('0209', '合同订单管理', '/order/orderContractManager.do');
INSERT INTO `LIMS_AUTHORITY_RESOURCE`(`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('290', '0209');
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('29', '2', '合同订单管理', null);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('290', '29', '合同订单查看', null);
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '29');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '290');
--订单增加付费检测状态 宋健2017年1月4日11:20:36

alter table `LIMS_ORDER` add `SCHEDULE_PAYMENT_STATUS`  TINYINT(1) DEFAULT NULL COMMENT '付费检测状态初始0未确认; 1已确认';
update LIMS_ORDER set SCHEDULE_PAYMENT_STATUS='0' where SCHEDULE_PAYMENT_STATUS is null;
---函数
DELIMITER $$
USE `lims`$$
DROP FUNCTION IF EXISTS `getOrderBalanceFun`$$
CREATE FUNCTION `getOrderBalanceFun`(p1 int,p2 int,p3 int ,p4 int , p5 int) RETURNS INT(11)
BEGIN

  DECLARE return_val INT;
  set return_val = p1+p2-p3-p4-p5;
   
  RETURN return_val;
END$$
DELIMITER ;


UPDATE `LIMS_MENU` SET `NAME`='已完成任务' WHERE (`ID`='0398');
UPDATE `LIMS_RESOURCE` SET `NAME`='已完成任务' WHERE (`ID`='0398');
UPDATE `LIMS_AUTHORITY` SET `NAME`='已完成任务' WHERE (`ID`='0398');


---增加订单状态 已退款  宋健

INSERT INTO `LIMS_DICT` VALUES ('7fb762f2af6a4fe0b558a87ae6ce2857', 'c4862859bf4a41859702723c11fc4eb0', 'ORDER_STATE', '已退款', '8', '9', '订单状态', '0');


--增加数据库索引  宋健2017年1月10日11:30:29

ALTER TABLE `LIMS_DOCUMENT` ADD INDEX index_id ( `ID`);
ALTER TABLE `LIMS_DOCUMENT` ADD INDEX index_title ( `TITLE`);
ALTER TABLE `LIMS_DOCUMENT_KNOWLEDGE` ADD INDEX index_key ( `DOCUMENT_ID` );

---订单 新增字段 2017年1月12日16:13:06
alter table `LIMS_ORDER` add `RECEIVED_SAMPLE_STATUS`  TINYINT(1) DEFAULT NULL COMMENT '样本入库完成0-未入库1-已入库';
alter table `LIMS_ORDER` add `REMARK`  varchar(256) DEFAULT NULL COMMENT '启动失败原因,成功的-未知的不填';

-- 异常订单启动 菜单 dhr 20170717
INSERT INTO `lims`.`LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('0328', '异常订单启动', '/order/startErrPaging.do', '03', 'fa-adjust', '28', '0');
INSERT INTO `lims`.`LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('0328', '异常样本启动', '/order/startErrPaging.do');
INSERT INTO `lims`.`LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('3-13-', '0328');
INSERT INTO `lims`.`LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('3-13-', '3', '异常订单启动', NULL);
INSERT INTO `lims`.`LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '3-13-');

-- 发票申请功能按钮过滤      孔德成     20170119
UPDATE `LIMS_AUTHORITY` SET `PARENT_ID`='11-' WHERE (`ID`='11-5-');
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('11-5-1', '11-5-', '发票申请处理', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('11-5-2', '11-5-', '发票申请寄送', NULL);

INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('120501', '发票申请处理', '/bmm/invoiceApply/doApply_forward.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('120502', '发票申请寄送', '/bmm/invoiceApply/send_forward.do');

INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('11-5-1', '120501');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('11-5-2', '120502');

INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '11-5-1');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '11-5-2');

-- 数据字典按钮过滤      孔德成     20170119
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('080301', '数据字典修改', '/smm/dict/modify.do');
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('731', '73', '数据字典修改', NULL);
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('731', '080301');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '731');

-- 样本类型按钮过滤      孔德成     20170119
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('050101', '样本类型新增', '/sample/create.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('050102', '样本类型修改', '/sample/modify.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('050103', '样本类型删除', '/sample/delete.do');
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('513', '51', '样本类型删除', NULL);
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('511', '050101');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('512', '050102');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('513', '050103');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '511');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '512');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '513');

-- 样本配置      孔德成     2017-02-04
INSERT INTO `LIMS_SAMPLE_PRETESTING_CONFIG` (`ID`, `RECEIVED_SAMPLE`, `TESTING_SAMPLE`, `PRETESTING_NODES`) VALUES ('b06bf18fea8611e68ee90242ac110004', '35f94697a53f414987bea925615736a2', '1000c3317cd0486890167d94e60820ad', 'DNA-EXT/DNA-QC');
INSERT INTO `LIMS_SAMPLE_PRETESTING_CONFIG` (`ID`, `RECEIVED_SAMPLE`, `TESTING_SAMPLE`, `PRETESTING_NODES`) VALUES ('c28d7ebdea8611e68ee90242ac110004', '35f94697a53f414987bea925615736a2', '1002c10cca5b4ecc86d94108eadfe067', 'CDNA-EXT/CDNA-QC');
INSERT INTO `LIMS_SAMPLE_PRETESTING_CONFIG` (`ID`, `RECEIVED_SAMPLE`, `TESTING_SAMPLE`, `PRETESTING_NODES`) VALUES ('ca4f0bd4ea8611e68ee90242ac110004', '776891b3e0234a3d9a11f1107a9b0802', '1000c3317cd0486890167d94e60820ad', 'DNA-EXT/DNA-QC');
INSERT INTO `LIMS_SAMPLE_PRETESTING_CONFIG` (`ID`, `RECEIVED_SAMPLE`, `TESTING_SAMPLE`, `PRETESTING_NODES`) VALUES ('d02005c4ea8611e68ee90242ac110004', '776891b3e0234a3d9a11f1107a9b0802', '1002c10cca5b4ecc86d94108eadfe067', 'CDNA-EXT/CDNA-QC');
INSERT INTO `LIMS_SAMPLE_PRETESTING_CONFIG` (`ID`, `RECEIVED_SAMPLE`, `TESTING_SAMPLE`, `PRETESTING_NODES`) VALUES ('dd2c8e04ea8611e68ee90242ac110004', 'c0e4935c0e0b4b99a7e732e4e384fba6', '1000c3317cd0486890167d94e60820ad', 'DNA-EXT/DNA-QC');
INSERT INTO `LIMS_SAMPLE_PRETESTING_CONFIG` (`ID`, `RECEIVED_SAMPLE`, `TESTING_SAMPLE`, `PRETESTING_NODES`) VALUES ('e30f6974ea8611e68ee90242ac110004', 'c0e4935c0e0b4b99a7e732e4e384fba6', '1002c10cca5b4ecc86d94108eadfe067', 'CDNA-EXT/CDNA-QC');
INSERT INTO `LIMS_SAMPLE_PRETESTING_CONFIG` (`ID`, `RECEIVED_SAMPLE`, `TESTING_SAMPLE`, `PRETESTING_NODES`) VALUES ('e83955fdea8611e68ee90242ac110004', 'ceb078e060dd45ce8cf0f9f2a5a8340e', '1000c3317cd0486890167d94e60820ad', 'DNA-EXT/DNA-QC');
INSERT INTO `LIMS_SAMPLE_PRETESTING_CONFIG` (`ID`, `RECEIVED_SAMPLE`, `TESTING_SAMPLE`, `PRETESTING_NODES`) VALUES ('ec03f7e4ea8611e68ee90242ac110004', 'ceb078e060dd45ce8cf0f9f2a5a8340e', '1002c10cca5b4ecc86d94108eadfe067', 'CDNA-EXT/CDNA-QC');
INSERT INTO `LIMS_SAMPLE_PRETESTING_CONFIG` (`ID`, `RECEIVED_SAMPLE`, `TESTING_SAMPLE`, `PRETESTING_NODES`) VALUES ('ef7a7002ea8611e68ee90242ac110004', 'ea98378f85ce437a9a414c521eea8af5', '1000c3317cd0486890167d94e60820ad', 'DNA-EXT/DNA-QC');
INSERT INTO `LIMS_SAMPLE_PRETESTING_CONFIG` (`ID`, `RECEIVED_SAMPLE`, `TESTING_SAMPLE`, `PRETESTING_NODES`) VALUES ('f25452eaea8611e68ee90242ac110004', 'ea98378f85ce437a9a414c521eea8af5', '1002c10cca5b4ecc86d94108eadfe067', 'CDNA-EXT/CDNA-QC');
INSERT INTO `LIMS_SAMPLE_PRETESTING_CONFIG` (`ID`, `RECEIVED_SAMPLE`, `TESTING_SAMPLE`, `PRETESTING_NODES`) VALUES ('fe223d07ea8611e68ee90242ac110004', '1009612f45d54758b584437cc9822ec7', '1000c3317cd0486890167d94e60820ad', 'DNA-QC');
INSERT INTO `LIMS_SAMPLE_PRETESTING_CONFIG` (`ID`, `RECEIVED_SAMPLE`, `TESTING_SAMPLE`, `PRETESTING_NODES`) VALUES ('6c2d3d37ea8911e68ee90242ac110004', '40f3ecff13184daaa48ccb187d086cd0', '1000c3317cd0486890167d94e60820ad', 'DNA-QC');

-- 报告模板表      孔德成     2017-02-04
DROP TABLE IF EXISTS `LIMS_REPORT_DATA_TEMPLATE`;
CREATE TABLE `LIMS_REPORT_DATA_TEMPLATE`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `NAME`              varchar(128)        NOT NULL        COMMENT '模板名称',
    `START_ROW_INDEX`   tinyint(1)                          COMMENT '数据起始行索引',
    `DEL_FLAG`          tinyint(1)          NOT NULL        COMMENT '删除标记 0-未删除 1-已删除',
    CONSTRAINT PK_REPORT_DATA_TEMPLATE PRIMARY KEY (`ID`)
)
;
ALTER TABLE `LIMS_REPORT_DATA_TEMPLATE` COMMENT '数据导入模板-主表';

DROP TABLE IF EXISTS `LIMS_REPORT_DATA_TEMPLATE_COLUMN`;
CREATE TABLE `LIMS_REPORT_DATA_TEMPLATE_COLUMN`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `TEMPLATE_ID`       varchar(64)         NOT NULL        COMMENT '模板ID',
    `COLUMN_INDEX`      tinyint(1)                          COMMENT '列索引',
    `COLUMN_NAME`       varchar(128)                        COMMENT '列名',
    `SEMANTIC`          varchar(64)                         COMMENT '特殊语义标识',
    CONSTRAINT PK_REPORT_DATA_TEMPLATE_COLUMN PRIMARY KEY (`ID`)
)
;
ALTER TABLE `LIMS_REPORT_DATA_TEMPLATE_COLUMN` COMMENT '数据导入模板-列定义';

-- 报告模板      孔德成     2017-02-06
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('14', '报告模板', NULL, NULL, 'fa-book', NULL, '0');
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('1401', '数据导入模板', '/report/dataTemplate/list.do', '14', 'fa-database', '1', '0');
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('1402', '报告生成模板', '/report/reportTemplate/list.do', '14', 'fa-object-group', '2', '0');
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('1403', '报告模板定义', '/report/reportTemplateDefine/list.do', '14', 'fa-eyedropper', '3', '0');

INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('14', '报告模板', NULL);
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('1401', '数据导入模板', '/report/dataTemplate/list.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('1402', '报告生成模板', '/report/reportTemplate/list.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('1403', '报告模板定义', '/report/reportTemplateDefine/list.do');

INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('01', NULL, '报告模板', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('0101', '01', '数据导入模板', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('01011', '0101', '数据导入模板查看', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('01012', '0101', '数据导入模板新增', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('01013', '0101', '数据导入模板修改', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('01014', '0101', '数据导入模板删除', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('0102', '01', '报告生成模板', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('01021', '0102', '报告生成模板查看', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('01022', '0102', '报告生成模板新增', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('01023', '0102', '报告生成模板修改', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('01024', '0102', '报告生成模板删除', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('0103', '01', '报告模板定义', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('01031', '0103', '报告模板定义查看', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('01032', '0103', '报告模板定义新增', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('01033', '0103', '报告模板定义修改', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('01034', '0103', '报告模板定义删除', NULL);

INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('01011', '1401');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('01021', '1402');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('01031', '1403');

INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '01011');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '01021');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '01031');




-- 报告生成模板 表     孔德成     2017-02-07
DROP TABLE IF EXISTS `LIMS_REPORT_CONTENT_TEMPLATE`;
CREATE TABLE `LIMS_REPORT_CONTENT_TEMPLATE` (
  `ID`               varchar(64)   NOT NULL   COMMENT '主键',
  `NAME`             varchar(128)  NOT NULL   COMMENT '模板名称',
  `DATA_TEMPLATE_ID` varchar(64)   NOT NULL   COMMENT '数据导入模板',
  `URL`              varchar(500)             COMMENT '报告模板地址',
  `DEL_FLAG`         tinyint(1)    NOT NULL   COMMENT '删除标记 0-未删除 1-已删除',
  CONSTRAINT PK_REPORT_CONTENT_TEMPLATE PRIMARY KEY (`ID`)
);
ALTER TABLE `LIMS_REPORT_CONTENT_TEMPLATE` COMMENT '报告生成模板';



--- 增加合同订单管理 菜单项 宋健 2017年1月4日11:19:32
INSERT INTO `LIMS_MENU`(`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('0210', '合同付款管理', '/order/contractPayManager.do', '02', 'fa-building-o', '10', '0');
INSERT INTO `LIMS_RESOURCE`(`ID`, `NAME`, `URI`)  VALUES ('0210', '合同付款管理', '/order/contractPayManager.do');
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('2901', '2', '合同付款管理', null);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('290101', '2901', '不定期付款列表', null);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('290102', '2901', '结算清单付款列表', null);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('290103', '2901', '合同款项查询', null);
INSERT INTO `LIMS_AUTHORITY_RESOURCE`(`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('290101', '0210');
INSERT INTO `LIMS_AUTHORITY_RESOURCE`(`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('290102', '0210');
INSERT INTO `LIMS_AUTHORITY_RESOURCE`(`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('290103', '0210');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '2901');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '290101');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '290102');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '290103');

---合同增加金额、到账金额字段
alter table `LIMS_CONTRACT` add `AMOUNT` int DEFAULT 0 COMMENT '合同金额，单位（分）';
alter table `LIMS_CONTRACT` add `INCOMING_AMOUNT` int DEFAULT 0 COMMENT '实际到账金额';  

-- 合同-付款记录表 宋健 2017年2月20日10:07:59
DROP TABLE IF EXISTS `LIMS_CONTRACT_PAYMENT_RECORD`;
CREATE TABLE `LIMS_CONTRACT_PAYMENT_RECORD`
(
    `ID`                         		varchar(64)         NOT NULL        COMMENT '主键',
    `CONTRACT_ID`       				varchar(64)         NOT NULL        COMMENT '合同ID',
    `CHECK_ID`          				varchar(64)         NOT NULL        COMMENT '确认人ID',
    `CHECK_NAME`        				varchar(128)        NOT NULL        COMMENT '确认人姓名',
	`CHECK_TIME`        				datetime        	NOT NULL        COMMENT '确认时间',
	`PAY_TYPE`          				tinyint(11)                         COMMENT '支付方式 默认转账1',
    `PAYMENTER`         				varchar(64)                         COMMENT '付款人',
	`PAYMENT_TIME`      				datetime                            COMMENT '付款时间',
	`CHECK_AMOUNT`     					decimal                             COMMENT '付款金额',
	`REMARK`            				varchar(512)                        COMMENT '备注',
	`PAYMENT_INSTRUMENT_IMG`            varchar(512)        				COMMENT '支付凭证图片',
    CONSTRAINT PK_CONTRACT_PAYMENT_RECORD PRIMARY KEY (`ID`)
);

-- 书签表      孔德成     2017-02-10
DROP TABLE IF EXISTS `LIMS_REPORT_BUILTIN_VARIABLE`;
CREATE TABLE `LIMS_REPORT_BUILTIN_VARIABLE` (
  `ID`       varchar(64)  NOT NULL COMMENT '主键',
  `NAME`     varchar(128) NOT NULL COMMENT '变量名称',
  `SEMANTIC` varchar(64)  NOT NULL COMMENT '语义标识',
  CONSTRAINT PK_REPORT_BUILTIN_VARIABLE PRIMARY KEY (`ID`)
);
ALTER TABLE `LIMS_REPORT_BUILTIN_VARIABLE` COMMENT '内置变量表';

DROP TABLE IF EXISTS `LIMS_REPORT_CONTENT_BOOKMARK`;
CREATE TABLE `LIMS_REPORT_CONTENT_BOOKMARK` (
  `ID`                  varchar(64)  NOT NULL COMMENT '主键',
  `NAME`                varchar(128) NOT NULL COMMENT '书签名称',
  `CONTENT_TYPE`        varchar(1)   NOT NULL COMMENT '内容类型，1内置变量，2列表变量',
  `BUILTIN_ID`          varchar(64)           COMMENT '内置变量',
  `CONTENT_TEMPLATE_ID` varchar(64)  NOT NULL COMMENT '报告模板',
  CONSTRAINT PK_REPORT_CONTENT_BOOKMARK PRIMARY KEY (`ID`)
);
ALTER TABLE `LIMS_REPORT_CONTENT_BOOKMARK` COMMENT '书签表';

-- 书签表      孔德成     2017-02-011
DROP TABLE IF EXISTS `LIMS_REPORT_CONTENT_TABLE_COLUMN`;
CREATE TABLE `LIMS_REPORT_CONTENT_TABLE_COLUMN` (
  `ID`                      varchar(64)  NOT NULL COMMENT '主键',
  `COLUMN_INDEX`            int(3)       NOT NULL COMMENT '模板表格列索引',
  `CONTENT_TYPE`            varchar(1)   NOT NULL COMMENT '内容类型，1内置变量，2数据变量',
  `BUILTIN_ID`              varchar(64)           COMMENT '内置变量',
  `DATA_TYPE`               varchar(1)            COMMENT '数据类型，1数据模板定义列值，2图片',
  `DATA_TEMPLATE_COLUMN_ID` varchar(64)           COMMENT '数据模板列',
  `IMG_URL`                 varchar(500)          COMMENT '图片地址',
  CONSTRAINT PK_REPORT_CONTENT_TABLE_COLUMN PRIMARY KEY (`ID`)
);
ALTER TABLE `LIMS_REPORT_CONTENT_TABLE_COLUMN` COMMENT '模板表格列定义';

ALTER TABLE `LIMS_REPORT_CONTENT_BOOKMARK`
ADD COLUMN `LINE_FILTER`  varchar(500) NULL COMMENT '行筛选' AFTER `CONTENT_TEMPLATE_ID`;


--合同结算清单管理 菜单 宋健 
INSERT INTO `LIMS_MENU`(`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('0211', '合同结算管理', '/contract/contractSettleManager.do', '02', 'fa-file-word-o', '11', '0');
INSERT INTO `LIMS_RESOURCE`(`ID`, `NAME`, `URI`)  VALUES ('0211', '合同结算管理', '/contract/contractSettleManager.do');
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('2902', '2', '合同结算管理', null);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('290201', '2902', '合同列表', null);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('290202', '2902', '出账单', null);
INSERT INTO `LIMS_AUTHORITY_RESOURCE`(`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('290201', '0211');
INSERT INTO `LIMS_AUTHORITY_RESOURCE`(`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('290202', '0211');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '2902');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '290201');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '290202');



-- 孔德成     2017-02-13
ALTER TABLE `LIMS_REPORT_CONTENT_TABLE_COLUMN`
CHANGE COLUMN `CONTENT_TYPE` `VARIABLE_TYPE`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容类型，1内置变量，2数据变量' AFTER `COLUMN_INDEX`;




-- 合同-结算清单出账表
DROP TABLE IF EXISTS `LIMS_CONTRACT_SETTLE_BILL`;
CREATE TABLE `LIMS_CONTRACT_SETTLE_BILL`
(
    `ID`                         		varchar(64)         NOT NULL        COMMENT '主键',
	`CODE`       						varchar(64)         NOT NULL        COMMENT '清单编号',	
    `CONTRACT_ID`       				varchar(64)         NOT NULL        COMMENT '合同ID',	
	`ORDER_COUNT`        				int        			NOT NULL        COMMENT '冗余字段-订单数量',
	`ORDER_AMOUNT`        				int        					        COMMENT '冗余字段-应结费用',
	`STATUS`        				    int        			DEFAULT 0       COMMENT '付款状态 0 未支付 1 已支付',
	`CREATE_ID`          				varchar(64)                 		COMMENT '出账人ID',
    `CREATE_NAME`        				varchar(64)                 		COMMENT '出账人姓名',
	`CREATE_TIME`        				datetime        	        		COMMENT '清单创建时间',	
    CONSTRAINT PK_CONTRACT_SETTLE_BILL PRIMARY KEY (`ID`)
)
;

DROP TABLE IF EXISTS `LIMS_CONTRACT_SETTLE_BILL_DETAIL`;
CREATE TABLE `LIMS_CONTRACT_SETTLE_BILL_DETAIL`
(
    `ID`                         		varchar(64)         NOT NULL        COMMENT '主键',
	`SETTLE_BILL_ID`          			varchar(64)         NOT NULL        COMMENT '结算清单表ID', 
	`ORDER_ID`          				varchar(64)         NOT NULL        COMMENT '订单ID', 
    CONSTRAINT PK_CONTRACT_SETTLE_BILL_DETAIL PRIMARY KEY (`ID`)
)
;

-- 孔德成     2017-02-15
ALTER TABLE `LIMS_REPORT_CONTENT_TABLE_COLUMN`
ADD COLUMN `BOOKMARK_ID`  varchar(64) NOT NULL COMMENT '书签' AFTER `IMG_URL`;

--内置变量数据     孔德成     2017-02-16
INSERT INTO `LIMS_REPORT_BUILTIN_VARIABLE` (`ID`, `NAME`, `SEMANTIC`) VALUES ('04b8c203f00211e68ee90242ac110004', '样本编号', 'SAMPLE_CODE');
INSERT INTO `LIMS_REPORT_BUILTIN_VARIABLE` (`ID`, `NAME`, `SEMANTIC`) VALUES ('1f58bc152cec4c4b8687c0a9750abd86', '订单名称', 'ORDER_NAME');
INSERT INTO `LIMS_REPORT_BUILTIN_VARIABLE` (`ID`, `NAME`, `SEMANTIC`) VALUES ('496dd5d1f41311e68ee90242ac110004', '位点', 'LOCUS');
INSERT INTO `LIMS_REPORT_BUILTIN_VARIABLE` (`ID`, `NAME`, `SEMANTIC`) VALUES ('67543a2ff41611e68ee90242ac110004', '样本名称', 'SAMPLE_NAME');
INSERT INTO `LIMS_REPORT_BUILTIN_VARIABLE` (`ID`, `NAME`, `SEMANTIC`) VALUES ('6758dd73f41611e68ee90242ac110004', '样本类型', 'SAMPLE_TYPE');
INSERT INTO `LIMS_REPORT_BUILTIN_VARIABLE` (`ID`, `NAME`, `SEMANTIC`) VALUES ('675cc08cf41611e68ee90242ac110004', '客户姓名', 'CUSTOMER_NAME');
INSERT INTO `LIMS_REPORT_BUILTIN_VARIABLE` (`ID`, `NAME`, `SEMANTIC`) VALUES ('67625b19f41611e68ee90242ac110004', '订单下单时间', 'ORDER_CREATE_TIME');
INSERT INTO `LIMS_REPORT_BUILTIN_VARIABLE` (`ID`, `NAME`, `SEMANTIC`) VALUES ('67682983f41611e68ee90242ac110004', '性别', 'SEX');
INSERT INTO `LIMS_REPORT_BUILTIN_VARIABLE` (`ID`, `NAME`, `SEMANTIC`) VALUES ('676c46c6f41611e68ee90242ac110004', '样本接收时间', 'SAMPLE_RECEIVE_TIME');
INSERT INTO `LIMS_REPORT_BUILTIN_VARIABLE` (`ID`, `NAME`, `SEMANTIC`) VALUES ('67709dcbf41611e68ee90242ac110004', '合同名称', 'CONTRACT_NAME');
INSERT INTO `LIMS_REPORT_BUILTIN_VARIABLE` (`ID`, `NAME`, `SEMANTIC`) VALUES ('6774d917f41611e68ee90242ac110004', '客户是否参与', 'DOCTOR_ASSIST');
INSERT INTO `LIMS_REPORT_BUILTIN_VARIABLE` (`ID`, `NAME`, `SEMANTIC`) VALUES ('6779d300f41611e68ee90242ac110004', '病历号', 'CASE_NUM');
INSERT INTO `LIMS_REPORT_BUILTIN_VARIABLE` (`ID`, `NAME`, `SEMANTIC`) VALUES ('677eb448f41611e68ee90242ac110004', '数据编号', 'DATA_CODE');
INSERT INTO `LIMS_REPORT_BUILTIN_VARIABLE` (`ID`, `NAME`, `SEMANTIC`) VALUES ('678387f9f41611e68ee90242ac110004', '年龄', 'AGE');
INSERT INTO `LIMS_REPORT_BUILTIN_VARIABLE` (`ID`, `NAME`, `SEMANTIC`) VALUES ('6787a9c8f41611e68ee90242ac110004', '检测方法', 'METHOD_NAME');
INSERT INTO `LIMS_REPORT_BUILTIN_VARIABLE` (`ID`, `NAME`, `SEMANTIC`) VALUES ('678c251af41611e68ee90242ac110004', '临床诊断', 'CLINICAL_DIAGNOSIS');
INSERT INTO `LIMS_REPORT_BUILTIN_VARIABLE` (`ID`, `NAME`, `SEMANTIC`) VALUES ('6790b292f41611e68ee90242ac110004', '重点关注基因', 'FOCUS_GENES');
INSERT INTO `LIMS_REPORT_BUILTIN_VARIABLE` (`ID`, `NAME`, `SEMANTIC`) VALUES ('67953cfbf41611e68ee90242ac110004', '病史摘要', 'CASE_SUMMARY');
INSERT INTO `LIMS_REPORT_BUILTIN_VARIABLE` (`ID`, `NAME`, `SEMANTIC`) VALUES ('679a043bf41611e68ee90242ac110004', '家族式摘要', 'FAMILY_HISTORY_SUMMARY');
INSERT INTO `LIMS_REPORT_BUILTIN_VARIABLE` (`ID`, `NAME`, `SEMANTIC`) VALUES ('679e607bf41611e68ee90242ac110004', '业务负责人', 'BUSINESS_PRINCIPAL');
INSERT INTO `LIMS_REPORT_BUILTIN_VARIABLE` (`ID`, `NAME`, `SEMANTIC`) VALUES ('67a313a9f41611e68ee90242ac110004', '技术负责人', 'TECHNICAL_PRINCIPAL');
INSERT INTO `LIMS_REPORT_BUILTIN_VARIABLE` (`ID`, `NAME`, `SEMANTIC`) VALUES ('67a77adef41611e68ee90242ac110004', '送检单位', 'CUSTOMER_COMPANY_NAME');
INSERT INTO `LIMS_REPORT_BUILTIN_VARIABLE` (`ID`, `NAME`, `SEMANTIC`) VALUES ('67ac5fbaf41611e68ee90242ac110004', '报告日期', 'REPORT_DATA');
INSERT INTO `LIMS_REPORT_BUILTIN_VARIABLE` (`ID`, `NAME`, `SEMANTIC`) VALUES ('929d25e000ed4a218aef6176c22d6b07', '订单编号', 'ORDER_CODE');
INSERT INTO `LIMS_REPORT_BUILTIN_VARIABLE` (`ID`, `NAME`, `SEMANTIC`) VALUES ('e0b7deecf00111e68ee90242ac110004', '关联基因', 'GENE');
INSERT INTO `LIMS_REPORT_BUILTIN_VARIABLE` (`ID`, `NAME`, `SEMANTIC`) VALUES ('ffc17faff00111e68ee90242ac110004', '营销中心', 'MARKETING_CENTER');


---宋健 2017年2月17日12:15:45
alter table LIMS_CONTRACT_PAYMENT_RECORD add SETTLE_BILL_ID varchar(64) DEFAULT null COMMENT '账单id' AFTER `CONTRACT_ID`;
update LIMS_CONTRACT o set o.INCOMING_AMOUNT = 0 where o.INCOMING_AMOUNT is null; 
update LIMS_CONTRACT o set o.AMOUNT = 0 where o.AMOUNT is null;

-- 孔德成     2017-02-17
UPDATE `LIMS_TASK_CONFIG` SET `NAME`='PCR-NGS引物设计合成' WHERE (`ID`='3a6c74f4ec1d11e68ee90242ac110004');
UPDATE `LIMS_TASK_CONFIG` SET `NAME`='PCR-NGS数据分析' WHERE (`ID`='8675049eec1d11e68ee90242ac110004');

-- 孔德成     2017-02-20
ALTER TABLE `LIMS_PRODUCT`
ADD COLUMN `REPORT_TEMPLATE_ID`  varchar(64) NULL COMMENT '报告模板' AFTER `UPDATE_TIME`;


--合同发票管理 菜单 宋健  2017年2月20日17:03:56
INSERT INTO `LIMS_MENU`(`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('0212', '合同发票管理', '/contract/contractInvoiceManager.do', '02', 'fa-file-word-o', '11', '0');
INSERT INTO `LIMS_RESOURCE`(`ID`, `NAME`, `URI`)  VALUES ('0212', '合同发票管理', '/contract/contractInvoiceManager.do');
INSERT INTO `LIMS_RESOURCE`(`ID`, `NAME`, `URI`)  VALUES ('021210', '合同管理未处理列表', '/contract/contractInvoiceList.do');
INSERT INTO `LIMS_RESOURCE`(`ID`, `NAME`, `URI`)  VALUES ('021211', '合同发票已处理列表', '/contract/contractInvoiceHandleList.do');
INSERT INTO `LIMS_RESOURCE`(`ID`, `NAME`, `URI`)  VALUES ('021212', '合同发票处理', '/contract/handleContractInvoice.do');

INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('2903', '2', '合同发票管理', null);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('290301', '2903', '合同管理未处理列表', null);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('290302', '2903', '合同发票处理', null);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('290303', '2903', '合同管理处理列表', null);


INSERT INTO `LIMS_AUTHORITY_RESOURCE`(`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('2903', '0212');
INSERT INTO `LIMS_AUTHORITY_RESOURCE`(`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('290301', '021210');
INSERT INTO `LIMS_AUTHORITY_RESOURCE`(`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('290302', '021212');
INSERT INTO `LIMS_AUTHORITY_RESOURCE`(`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('290303', '021211');

INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '2903');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '290301');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '290302');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '290303');




---订单减免申请增加最终审批金额字段  宋健 
ALTER TABLE `LIMS_ORDER_REDUCE` ADD COLUMN `CHECK_AMOUNT` int(11) default 0 COMMENT '减免审批金额' AFTER `APPLY_AMOUNT`;

-- 删除报告模板定义菜单       孔德成     2017-02-22
DELETE FROM LIMS_MENU WHERE ID = '1403';
DELETE FROM LIMS_RESOURCE WHERE ID = '1403';
DELETE FROM LIMS_AUTHORITY WHERE ID = '0103';
DELETE FROM LIMS_AUTHORITY WHERE ID = '01031';
DELETE FROM LIMS_AUTHORITY WHERE ID = '01032';
DELETE FROM LIMS_AUTHORITY WHERE ID = '01033';
DELETE FROM LIMS_AUTHORITY WHERE ID = '01034';
DELETE FROM LIMS_AUTHORITY_RESOURCE WHERE AUTHORITY_ID = '01031';
DELETE FROM LIMS_ROLE_AUTHORITY WHERE AUTHORITY_ID = '01031';

-- 修改实验样本的实验单位       孔德成     2017-02-24
UPDATE `LIMS_METADATA_SAMPLE` SET `TESTING_UNIT`='fa80fa8348f74720a4d43dd1045a7b24' WHERE (`ID`='200173e8bd454bb7b860e1113c01ab8c');
UPDATE `LIMS_METADATA_SAMPLE` SET `TESTING_UNIT`='fa80fa8348f74720a4d43dd1045a7b24' WHERE (`ID`='200208b3f0604ac7a14b4ec4668db4e8');
UPDATE `LIMS_METADATA_SAMPLE` SET `TESTING_UNIT`='fa80fa8348f74720a4d43dd1045a7b24' WHERE (`ID`='ced00cd8f18c11e68ee90242ac110004');
UPDATE `LIMS_METADATA_SAMPLE` SET `TESTING_UNIT`='fa80fa8348f74720a4d43dd1045a7b24' WHERE (`ID`='d3f030aff25c11e68ee90242ac110004');

-- 报告模板菜单权限       孔德成     2017-02-27
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('14011', '数据导入模板新增', '/report/dataTemplate/create.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('14012', '数据导入模板修改', '/report/dataTemplate/modify.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('14013', '数据导入模板删除', '/report/dataTemplate/delete.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('14021', '报告生成模板新增', '/report/reportTemplate/create.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('14022', '报告生成模板修改', '/report/reportTemplate/modify.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('14023', '报告生成模板删除', '/report/reportTemplate/delete.do');


--- 合同-开票信息表 宋健 2017年2月28日14:33:43
DROP TABLE IF EXISTS `LIMS_CONTRACT_INVOICE_INFO`;
CREATE TABLE `LIMS_CONTRACT_INVOICE_INFO`
(
    `ID`                         		varchar(64)         NOT NULL        COMMENT '主键',
    `CONTRACT_ID`       				varchar(64)         NOT NULL        COMMENT '合同ID',
	`INVOICEAPPLY_ID`       			varchar(64)         NOT NULL        COMMENT '合同发票申请ID',
    `CREATOR_ID`          				varchar(64)         NOT NULL        COMMENT '确认人ID',
    `CREATOR_NAME`        				varchar(64)                 		COMMENT '确认人姓名',
	`CREATE_TIME`        				datetime        	        		COMMENT '确认时间',
	`INVOICE_ACCOUNT`          			int(11)                         	COMMENT '开票金额',
	`INVOICE_PERSON`         			varchar(64)                         COMMENT '开票人',
    `INVOICE_COMPANY`         			varchar(64)                         COMMENT '开票公司',
	`INVOICE_TIME`      				datetime                            COMMENT '开票时间',
	`INVOICER_NO`     					varchar(64)                         COMMENT '开票单号',
	`RECEIVER_NAME`            			varchar(64)        				    COMMENT '领取人',
	`REMARK`            				varchar(512)                        COMMENT '备注',
    CONSTRAINT PK_CONTRACT_INVOICE_INFO PRIMARY KEY (`ID`)
)
;




---权限修改  宋健 2017年3月1日10:24:13
INSERT INTO `LIMS_RESOURCE`(`ID`, `NAME`, `URI`)  VALUES ('021210', '合同管理未处理列表', '/contract/contractInvoiceList.do');
INSERT INTO `LIMS_RESOURCE`(`ID`, `NAME`, `URI`)  VALUES ('021211', '合同发票已处理列表', '/contract/contractInvoiceHandleList.do');
INSERT INTO `LIMS_RESOURCE`(`ID`, `NAME`, `URI`)  VALUES ('021212', '合同发票处理', '/contract/handleContractInvoice.do');
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('290303', '2903', '合同管理处理列表', null);
DELETE FROM LIMS_AUTHORITY_RESOURCE WHERE AUTHORITY_ID = '290301' || AUTHORITY_ID = '290302';
INSERT INTO `LIMS_AUTHORITY_RESOURCE`(`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('2903', '0212');
INSERT INTO `LIMS_AUTHORITY_RESOURCE`(`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('290301', '021210');
INSERT INTO `LIMS_AUTHORITY_RESOURCE`(`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('290302', '021212');
INSERT INTO `LIMS_AUTHORITY_RESOURCE`(`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('290303', '021211');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '290303');

-- 订单样本视图 增加 收样状态
	
DROP VIEW IF EXISTS `LIMS_ORDER_SAMPLE_VIEW`;
CREATE VIEW `LIMS_ORDER_SAMPLE_VIEW` (ID, ORDER_ID, SAMPLE_ID, SAMPLE_BTYPE, SAMPLE_TYPE, SAMPLE_CODE, SAMPLE_NAME, SAMPLE_SIZE,SAMPLE_PACKAGE_STATUS, SAMPLING_DATE, PURPOSE, TRANSPORT_STATUS, UPDATE_TIME) AS 
    SELECT CONCAT_WS('-', '1', PS.ID) AS ID, PS.ORDER_ID AS ORDER_ID, PS.ID, 1, PS.SAMPLE_TYPE_ID AS SAMPLE_TYPE, PS.SAMPLE_CODE AS SAMPLE_CODE, OE.NAME AS SAMPLE_NAME, PS.SAMPLE_SIZE AS SAMPLE_SIZE,
	PS.SAMPLE_PACKAGE_STATUS AS SAMPLE_PACKAGE_STATUS,	PS.SAMPLING_DATE AS SAMPLING_DATE, NULL, PS.SAMPLE_PACKAGE_STATUS, PS.UPDATE_TIME
    FROM LIMS_ORDER_PRIMARY_SAMPLE PS LEFT JOIN LIMS_ORDER_EXAMINEE OE ON PS.EXAMINEE_ID = OE.ID
UNION ALL
    SELECT CONCAT_WS('-', '2', SS.ID) AS ID, SS.ORDER_ID AS ORDER_ID, SS.ID, 2, SS.SAMPLE_TYPE_ID AS SAMPLE_TYPE, SS.SAMPLE_CODE AS SAMPLE_CODE, SS.OWNER_NAME AS SAMPLE_NAME, SS.SAMPLE_SIZE AS SAMPLE_SIZE, 
	SS.SAMPLE_PACKAGE_STATUS AS SAMPLE_PACKAGE_STATUS,SS.SAMPLING_DATE AS SAMPLING_DATE, SS.PURPOSE AS PURPOSE, SS.SAMPLE_PACKAGE_STATUS, SS.UPDATE_TIME
    FROM LIMS_ORDER_SUBSIDIARY_SAMPLE SS
UNION ALL
    SELECT CONCAT_WS('-', '3', RS.ID) AS ID, RS.ORDER_ID AS ORDER_ID, RS.ID, 3, RS.SAMPLE_TYPE_ID AS SAMPLE_TYPE, RS.SAMPLE_CODE AS SAMPLE_CODE, RS.SAMPLE_NAME AS SAMPLE_NAME, RS.SAMPLE_SIZE AS SAMPLE_SIZE,
	RS.SAMPLE_PACKAGE_STATUS AS SAMPLE_PACKAGE_STATUS,RS.SAMPLING_DATE AS SAMPLING_DATE, NULL, RS.SAMPLE_PACKAGE_STATUS, RS.UPDATE_TIME
    FROM LIMS_ORDER_RESEARCH_SAMPLE RS
;

-- 孔德成  2017-3-3
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('01012', '14011');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('01013', '14012');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('01014', '14013');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('01022', '14021');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('01023', '14022');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('01024', '14023');



-- 订单-追加样本表 宋健 2017年3月3日11:42:16
DROP TABLE IF EXISTS `LIMS_ORDER_EXTRA_SAMPLE`;
CREATE TABLE `LIMS_ORDER_EXTRA_SAMPLE`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `ORDER_ID`       	varchar(64)                         COMMENT '订单id',
    `CREATOR_ID`        varchar(64)                         COMMENT '追加人ID',
    `CREATOR_NAME`      varchar(32)                         COMMENT '追加人姓名',
    `CREATE_TIME`       datetime            NOT NULL        COMMENT '追加时间',
    CONSTRAINT PK_ORDER_EXTRA_SAMPLE PRIMARY KEY (`ID`)
)
;

-- 订单-追加样本明细表 宋健 2017年3月3日11:42:20
DROP TABLE IF EXISTS `LIMS_ORDER_EXTRA_SAMPLE_DETAIL`;
CREATE TABLE `LIMS_ORDER_EXTRA_SAMPLE_DETAIL`
(
    `ID`                varchar(64)         NOT NULL        COMMENT '主键',
    `RECORD_ID`       	varchar(64)                         COMMENT '追加样本表id',
    `SAMPLE_ID`         varchar(64)                         COMMENT '附属样本ID',
    CONSTRAINT PK_ORDER_EXTRA_SAMPLE_DETAIL PRIMARY KEY (`ID`)
)
;

---见面申请  增加最新处理时间  宋健 2017年3月3日17:38:36

ALTER TABLE `LIMS_ORDER_REDUCE` ADD COLUMN `UPDATE_TIME`  datetime  NULL COMMENT '更新时间';

-- 增加样本记录   孔德成   2017-03-13
INSERT INTO `LIMS_METADATA_SAMPLE` (`ID`, `NAME`, `TESTING_UNIT`, `INTERMEDIATE`, `STORAGE_TYPE`, `RECEIVING_UNIT`, `LS_SIZE`, `SAMPLING_TIPS`, `TRANSPORTING_TIPS`, `STORAGING_TIPS`, `SORT`, `DEL_FLAG`) VALUES ('572aecfa07af11e7b49c0242ac110006', '宫颈刮片', '574d2c1dae6946809638e3b8f579499b', '1', 'TB', '574d2c1dae6946809638e3b8f579499b', NULL, NULL, NULL, NULL, '29', '0');
INSERT INTO `LIMS_SAMPLE_PRETESTING_CONFIG` (`ID`, `RECEIVED_SAMPLE`, `TESTING_SAMPLE`, `PRETESTING_NODES`) VALUES ('388d782b07b211e7b49c0242ac110006', '572aecfa07af11e7b49c0242ac110006', '1002c10cca5b4ecc86d94108eadfe067', 'CDNA-EXT/CDNA-QC');
INSERT INTO `LIMS_SAMPLE_PRETESTING_CONFIG` (`ID`, `RECEIVED_SAMPLE`, `TESTING_SAMPLE`, `PRETESTING_NODES`) VALUES ('dd78253e07b111e7b49c0242ac110006', '572aecfa07af11e7b49c0242ac110006', '1000c3317cd0486890167d94e60820ad', 'DNA-EXT/DNA-QC');



--订单权限脚本  by 宋健       2017年3月13日17:36:58
INSERT INTO `LIMS_RESOURCE`(`ID`, `NAME`, `URI`)  VALUES ('020101', '订单新增', '/order/createCheckOrder.do');
INSERT INTO `LIMS_AUTHORITY_RESOURCE`(`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('211', '020101');
INSERT INTO `LIMS_RESOURCE`(`ID`, `NAME`, `URI`)  VALUES ('020102', '订单修改', '/order/modifyOrder.do');
INSERT INTO `LIMS_AUTHORITY_RESOURCE`(`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('212', '020102');
INSERT INTO `LIMS_RESOURCE`(`ID`, `NAME`, `URI`)  VALUES ('020103', '订单删除', '/order/delete.do');
INSERT INTO `LIMS_AUTHORITY_RESOURCE`(`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('213', '020103');
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('214', '21', '追加样本', null);
INSERT INTO `LIMS_RESOURCE`(`ID`, `NAME`, `URI`)  VALUES ('020104', '追加样本', '/order/addOrderSampleForm.do');
INSERT INTO `LIMS_AUTHORITY_RESOURCE`(`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('214', '020104');


--- 合同结算 默认月结 2017年3月15日13:32:44  ---by 宋健
UPDATE `LIMS_MENU` SET `URI`='/contract/contractSettleManager.do?settlementMode=3' WHERE (`ID`='0211' and  `NAME`='合同结算管理');
UPDATE `LIMS_RESOURCE` SET `URI`='/contract/contractSettleManager.do?settlementMode=3' WHERE (`ID`='0211' and  `NAME`='合同结算管理');


-- 新增 开票人员管理   孔德成   2017-03-16
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('1309', '开票人员管理', '/smm/invoiceUser/list.do', '13', 'fa-life-ring', '12', '0');

INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('12-09-01', '1309');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('12-09-02', '13091');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('12-09-03', '13092');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('12-09-04', '13093');

INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '12-09');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '12-09-01');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '12-09-02');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '12-09-03');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '12-09-04');

DROP TABLE IF EXISTS `LIMS_INVOICE_USER`;
CREATE TABLE `LIMS_INVOICE_USER` (
  `ID`               varchar(64) NOT NULL,
  `TEST_INSTITUTION` varchar(2)  NOT NULL COMMENT '检测机构',
  `DEL_FLAG`         tinyint(1)  NOT NULL COMMENT '删除标记 0-未删除 1-已删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `LIMS_INVOICE_USER_MODEL`;
CREATE TABLE `LIMS_INVOICE_USER_MODEL` (
  `ID`              varchar(64) NOT NULL,
  `INVOICE_USER_ID` varchar(64) NOT NULL COMMENT '开票人员所属机构',
  `USER_ID`         varchar(64) NOT NULL COMMENT '用户ID',
  `NAME`            varchar(64),
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--2017年3月25日15:39:17   by 宋健
alter table LIMS_STORAGE_DEVICE add COLUMN cell int(11) DEFAULT 0 COMMENT '位宽' AFTER `SITE`;

-- 2017-01-01  默认开票、提前开票       孔德成
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('1310', '默认开票管理', '/bmm/defaultInvoice/list.do', '13', 'fa-life-ring', '13', '0');
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('1311', '提前开票管理', '/bmm/advanceInvoice/list.do', '13', 'fa-adjust', '14', '0');

INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('1310', '默认开票管理', '/bmm/defaultInvoice/list.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('13101', '默认开票处理', '/bmm/defaultInvoice/solve_forward.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('1311', '提前开票管理', '/bmm/advanceInvoice/list.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('13111', '提前开票处理', '/bmm/advanceInvoice/solve_forward.do');

INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('12-10', '12-', '默认开票管理', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('12-10-01', '12-10', '默认开票查看', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('12-10-02', '12-10', '默认开票处理', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('12-11', '12-', '提前开票管理', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('12-11-01', '12-11', '提前开票查看', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('12-11-02', '12-11', '提前开票处理', NULL);

INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('12-10-01', '1310');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('12-10-02', '13101');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('12-11-01', '1311');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('12-11-02', '13111');

INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '12-10-01');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '12-10-02');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '12-11-01');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '12-11-02');




INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('1601', '全文检索同步管理', '/disease/esSearchMapper.do', '09', 'fa-life-ring', '6', '0');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('160101', '全文检索同步管理', '/disease/esSearchMapper.do');
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('86', '8', '全文检索同步管理', NULL);
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('86', '160101');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '86');


DROP TABLE IF EXISTS `LIMS_FINANCE_INVOICE_TASK`;
CREATE TABLE `LIMS_FINANCE_INVOICE_TASK`
(
    `ID`                    varchar(64)         NOT NULL        COMMENT '主键',
    `CATEGORY`              varchar(64)         NOT NULL        COMMENT '任务类型：1-默认开票 2-申请开票',
    `RECORD_KEY`            varchar(64)                         COMMENT '默认开票订单ID/申请开票记录ID',
    `INSTITUTION`           varchar(64)                         COMMENT '开票机构',
    `AMOUNT`                decimal                             COMMENT '开票金额',
    `STATUS`                tinyint(1)                          COMMENT '状态：1-待开票 2-可开票 3-已开票',
    CONSTRAINT PK_FINANCE_INVOICE_TASK PRIMARY KEY (`ID`)
)
;

DROP TABLE IF EXISTS `LIMS_INVOICE_INFO`;
CREATE TABLE `LIMS_INVOICE_INFO` (
  `ID`                     varchar(64)   NOT NULL,
  `DRAWER_ID`              varchar(64)   NOT NULL   COMMENT '开票人ID',
  `INVOICE_TIME`           datetime      NOT NULL   COMMENT '开票时间',
  `DRAWER_NO`              varchar(64)   NOT NULL   COMMENT '开票单号',
  `RECEIVER_NAME`          varchar(64)              COMMENT '领取人姓名',
  `INVOICE_TASK_ID`        varchar(255)  NOT NULL   COMMENT '开票拆分任务',
  `ORDER_IDS`              text                     COMMENT '开票信息关联的订单',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;






ALTER table LIMS_SAMPLE_TRANSFERRING_DETAIL ADD COLUMN SORT int(11) DEFAULT 0 COMMENT '排序';

--2017年4月5日15:58:20   by 宋健
INSERT INTO `LIMS_DICT`(`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('7fb762h2ar6a4fe0b556a87ae6ce2857', 'c4862859bf4a41859702723c11fc4eb0', 'ORDER_STATE', '已取消', '9', '10', '订单状态', '0');




---2017年4月7日16:14:09  宋健
alter table  LIMS_ORDER_REFUND_RECORD add column APPLY_ID varchar(64) COMMENT '退款申请ID' AFTER ORDER_ID;

---2017年4月12日15:32:11  宋健
alter table  LIMS_ORDER_REFUND add column HANDLER varchar(64) DEFAULT FALSE COMMENT '是否已处理' AFTER STATUS;

-- 2017年4月25日    孔德成
ALTER TABLE `LIMS_INVOICE_INFO`
ADD COLUMN `INVOICE_AMOUNT`  decimal(11,2) NOT NULL DEFAULT 0 AFTER `ORDER_IDS`;



----2017年5月3日14:53:16  宋健  样本用途增加 数据字典项
INSERT INTO `LIMS_DICT`(`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b043a56ggc3443bbbef8fe77b78d6432', '94fbb46b47a940f7a76a201355d109a0', 'SAMPLE_PURPOSE', '暂存', '4', '4', '附属样本用途', '0');




-----2017年5月5日09:37:46   宋健
alter table LIMS_CONTRACT_SETTLE_BILL_DETAIL add COLUMN ORDER_AMOUNT int(32) default 0 COMMENT '合同订单清单金额' ;


--- 2017-05-05   发票管理  王巍
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('0216', '发票管理', '/invoiceSend/list.do', '02', 'fa-tasks', '7', '0');

INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('0216', '发票管理', '/invoiceSend/list.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('021601', '发票寄送', '/invoiceSend/send.do');

INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('2-16', '2', '发票管理', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('2-16-0', '2-16', '发票管理查看', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('2-16-1', '2-16', '发票管理寄送', NULL);

INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('2-16-0', '0216');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('2-16-1', '021601');

INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '2-16-0');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '2-16-1');

DROP TABLE IF EXISTS `LIMS_INVOICE_SEND`;
CREATE TABLE `LIMS_INVOICE_SEND` (
  `ID` varchar(64) NOT NULL,
  `SEND_TIME` datetime DEFAULT NULL COMMENT '寄件日期',
  `TRANSPORT_TYPE` varchar(32) DEFAULT NULL COMMENT '运输方式',
  `TRACK_NUMBER` varchar(64) DEFAULT NULL COMMENT '快递单号',
  `TRANSPORT_NAME` varchar(64) DEFAULT NULL COMMENT '运输人姓名',
  `TRANSPORT_PHONE` varchar(64) DEFAULT NULL COMMENT '运输人电话',
  `SEND_DETAIL` varchar(255) DEFAULT NULL COMMENT '寄件内容',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `LIMS_INVOICE_SEND_RECORD_KEY`;
CREATE TABLE `LIMS_INVOICE_SEND_RECORD_KEY` (
  `ID` varchar(64) NOT NULL,
  `SEND_ID` varchar(64) DEFAULT NULL COMMENT '寄件信息id',
  `RECORD_KEY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;





			
			

	
	
	
---异常样本导航菜单 宋健 2017年5月5日18:15:37	
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('1507', '异常样本管理', '/order/errorSampleList.do', '15', 'fa-life-ring', '8', '0');

INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('150701', '异常样本列表', '/order/errorSampleList.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('150702', '异常样本处理', '/order/handleErrorSample.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('150703', '异常样本详情', '/order/viewhandleErrorSample.do');

INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('15-07', '15', '异常样本管理', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('15-07-01', '15-07', '异常样本列表', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('15-07-02', '15-07', '异常样本处理', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('15-07-03', '15-07', '异常样本详情', NULL);

INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('15-07', '1507');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('15-07-01', '150701');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('15-07-02', '150702');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('15-07-03', '150703');

INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '15-07-01');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '15-07-02');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '15-07-03');
	
	
-- 报告审核   2017-07-05     孔德成	
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('1405', '报告审核', NULL, '14', 'fa-life-ring', '5', '0');
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('14051', '报告一审', '/bpm/report/firstReview/list.do', '1405', 'fa-adjust', '1', '0');
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('14052', '报告二审', '/bpm/report/secondReview/list.do', '1405', 'fa-adjust', '2', '0');
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('14053', '已出报告', '/bpm/report/completedReport/list.do', '1405', 'fa-tasks', '3', '0');

INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('140511', '报告一审', '/bpm/report/firstReview/list.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('140512', '报告一审审核', '/bpm/report/firstReview/review.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('140513', '报告导出', '/bpm/report/firstReview/download.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('140514', '报告预览', '/bpm/report/firstReview/preview.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('140521', '报告二审', '/bpm/report/secondReview/list.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('140522', '报告二审审核', '/bpm/report/secondReview/review.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('140531', '已出报告', '/bpm/report/completedReport/list.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('140532', '已出报告审核详情', '/bpm/report/completedReport/details.do');

INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('0105', '01', '报告审核', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('01051', '0105', '报告一审', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('010511', '01051', '报告一审列表', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('010512', '01051', '报告一审审核', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('01052', '0105', '报告二审', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('010521', '01052', '报告二审列表', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('010522', '01052', '报告二审审核', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('010513', '01051', '报告导出', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('010514', '01051', '报告预览', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('01053', '0105', '已出报告', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('010531', '01053', '已出报告列表', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('010532', '01053', '已出报告审核详情', NULL);

INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('010511', '140511');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('010512', '140512');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('010513', '140513');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('010514', '140514');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('010521', '140521');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('010522', '140522');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('010531', '140531');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('010532', '140532');

INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '010511');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '010512');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '010513');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '010514');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '010521');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '010522');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '010531');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '010532');


DROP TABLE IF EXISTS `LIMS_TESTING_REPORT_REVIEW`;
CREATE TABLE `LIMS_TESTING_REPORT_REVIEW` (
  `ID`             varchar(64) NOT NULL COMMENT '主键',
  `REPORT_ID`      varchar(64)          COMMENT '报告整合ID',
  `REVIEW_NODE`    char(1)              COMMENT '1-一审，2-二审',
  `REVIEW_RESULT`  char(1)              COMMENT '1-通过，2-不通过',
  `REVIEW_OPINION` varchar(512)         COMMENT '审核意见',
  `REVIEWER_ID`    varchar(64)          COMMENT '审核人ID',
  `REVIEWER_NAME`  varchar(64)          COMMENT '审核人姓名',
  `REVIEW_TIME`    datetime             COMMENT '审核时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `LIMS_TESTING_REPORT`
ADD COLUMN `REPORT_CODE`  varchar(64) NULL COMMENT '报告编号' AFTER `STATUS`,
ADD COLUMN `REPORT_NAME`  varchar(64) NULL COMMENT '报告名称' AFTER `REPORT_CODE`,
ADD COLUMN `FIRST_REVIEW_STATUS`  char(2) NULL COMMENT '0-待审核，1-审核通过，2-审核不通过' AFTER `REPORT_NAME`,
ADD COLUMN `SECOND_REVIEW_STATUS`  char(2) NULL COMMENT '0-待审核，1-审核通过，2-审核不通过' AFTER `FIRST_REVIEW_STATUS`;




--2017年5月11日09:20:36   by 宋健  打包运输查询
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('1508', '打包运输', '/sampleReceiving/packageSampleList.do', '15', 'fa-random', '8', '0');

INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('150801', '打包运输列表', '/sampleReceiving/packageSampleList.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('150802', '打包运输查看', '/sampleReceiving/packageSampleDetail.do');


INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('15-08', '15', '打包运输', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('15-08-01', '15-08', '打包运输列表', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('15-08-02', '15-08', '打包运输查看', NULL);


INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('15-08', '1508');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('15-08-01', '150801');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('15-08-02', '150802');


INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '15-08-01');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '15-08-02');




---2017年5月12日11:26:33  sj  更新文本
update  LIMS_AUTHORITY set NAME='异常样本管理' where ID='15-07';
update  LIMS_AUTHORITY set NAME='异常样本列表' where ID='15-07-01';
update  LIMS_AUTHORITY set NAME='异常样本处理' where ID='15-07-02';
update  LIMS_AUTHORITY set NAME='异常样本详情' where ID='15-07-03';


--2017年5月12日11:26:50   宋健  视图增加字段显示
DROP VIEW IF EXISTS `LIMS_ORDER_SAMPLE_VIEW`;
CREATE VIEW `LIMS_ORDER_SAMPLE_VIEW` (
	ID,
	ORDER_ID,
	SAMPLE_ID,
	SAMPLE_BTYPE,
	SAMPLE_TYPE,
	SAMPLE_CODE,
	SAMPLE_NAME,
	SAMPLE_SIZE,
	SAMPLE_PACKAGE_STATUS,
	SAMPLING_DATE,
	PURPOSE,
	TRANSPORT_STATUS,
	UPDATE_TIME,
	ERROR_DEAL_REMARK,
	SAMPLE_ERROR_STATUS,
	ERROR_REASON,
	ACCEPT_SAMPLE_TIME,
	SAMPLE_ERROR_NEW_NO
) AS SELECT
	concat_ws('-', '1', `PS`.`ID`) AS `ID`,
	`PS`.`ORDER_ID` AS `ORDER_ID`,
	`PS`.`ID` AS `SAMPLE_ID`,
	1 AS `SAMPLE_BTYPE`,
	`PS`.`SAMPLE_TYPE_ID` AS `SAMPLE_TYPE`,
	`PS`.`SAMPLE_CODE` AS `SAMPLE_CODE`,
	`OE`.`NAME` AS `SAMPLE_NAME`,
	`PS`.`SAMPLE_SIZE` AS `SAMPLE_SIZE`,
	`PS`.`SAMPLE_PACKAGE_STATUS` AS `SAMPLE_PACKAGE_STATUS`,
	`PS`.`SAMPLING_DATE` AS `SAMPLING_DATE`,
	NULL AS `PURPOSE`,
	`PS`.`SAMPLE_PACKAGE_STATUS` AS `TRANSPORT_STATUS`,
	`PS`.`UPDATE_TIME` AS `UPDATE_TIME`,
	`PS`.`ERROR_DEAL_REMARK` AS `ERROR_DEAL_REMARK`,
	`PS`.`SAMPLE_ERROR_STATUS` AS `SAMPLE_ERROR_STATUS`,
	`PS`.`ERROR_REASON` AS `ERROR_REASON`,
	`PS`.`ACCEPT_SAMPLE_TIME` AS `ACCEPT_SAMPLE_TIME`,
	`PS`.`SAMPLE_ERROR_NEW_NO` AS `SAMPLE_ERROR_NEW_NO`
FROM
	(
		`LIMS_ORDER_PRIMARY_SAMPLE` `PS`
		LEFT JOIN `LIMS_ORDER_EXAMINEE` `OE` ON (
			(
				`PS`.`EXAMINEE_ID` = `OE`.`ID`
			)
		)
	)
UNION ALL
	SELECT
		concat_ws('-', '2', `SS`.`ID`) AS `ID`,
		`SS`.`ORDER_ID` AS `ORDER_ID`,
		`SS`.`ID` AS `ID`,
		2 AS `2`,
		`SS`.`SAMPLE_TYPE_ID` AS `SAMPLE_TYPE`,
		`SS`.`SAMPLE_CODE` AS `SAMPLE_CODE`,
		`SS`.`OWNER_NAME` AS `SAMPLE_NAME`,
		`SS`.`SAMPLE_SIZE` AS `SAMPLE_SIZE`,
		`SS`.`SAMPLE_PACKAGE_STATUS` AS `SAMPLE_PACKAGE_STATUS`,
		`SS`.`SAMPLING_DATE` AS `SAMPLING_DATE`,
		`SS`.`PURPOSE` AS `PURPOSE`,
		`SS`.`SAMPLE_PACKAGE_STATUS` AS `SAMPLE_PACKAGE_STATUS`,
		`SS`.`UPDATE_TIME` AS `UPDATE_TIME`,
		`SS`.`ERROR_DEAL_REMARK` AS `ERROR_DEAL_REMARK`,
		`SS`.`SAMPLE_ERROR_STATUS` AS `SAMPLE_ERROR_STATUS`,
		`SS`.`ERROR_REASON` AS `ERROR_REASON`,
		`SS`.`ACCEPT_SAMPLE_TIME` AS `ACCEPT_SAMPLE_TIME`,
		`SS`.`SAMPLE_ERROR_NEW_NO` AS `SAMPLE_ERROR_NEW_NO`
	FROM
		`LIMS_ORDER_SUBSIDIARY_SAMPLE` `SS`
	UNION ALL
		SELECT
			concat_ws('-', '3', `RS`.`ID`) AS `ID`,
			`RS`.`ORDER_ID` AS `ORDER_ID`,
			`RS`.`ID` AS `ID`,
			3 AS `3`,
			`RS`.`SAMPLE_TYPE_ID` AS `SAMPLE_TYPE`,
			`RS`.`SAMPLE_CODE` AS `SAMPLE_CODE`,
			`RS`.`SAMPLE_NAME` AS `SAMPLE_NAME`,
			`RS`.`SAMPLE_SIZE` AS `SAMPLE_SIZE`,
			`RS`.`SAMPLE_PACKAGE_STATUS` AS `SAMPLE_PACKAGE_STATUS`,
			`RS`.`SAMPLING_DATE` AS `SAMPLING_DATE`,
			NULL AS `NULL`,
			`RS`.`SAMPLE_PACKAGE_STATUS` AS `SAMPLE_PACKAGE_STATUS`,
			`RS`.`UPDATE_TIME` AS `UPDATE_TIME`,
			`RS`.`ERROR_DEAL_REMARK` AS `ERROR_DEAL_REMARK`,
			`RS`.`SAMPLE_ERROR_STATUS` AS `SAMPLE_ERROR_STATUS`,
			`RS`.`ERROR_REASON` AS `ERROR_REASON`,
			`RS`.`ACCEPT_SAMPLE_TIME` AS `ACCEPT_SAMPLE_TIME`,
			`RS`.`SAMPLE_ERROR_NEW_NO` AS `SAMPLE_ERROR_NEW_NO`
		FROM
			`LIMS_ORDER_RESEARCH_SAMPLE` `RS`
			
			
			
			
DROP VIEW IF EXISTS `LIMS_ORDER_SAMPLE_DETAILS`;
CREATE VIEW `LIMS_ORDER_SAMPLE_DETAILS` (
	ID,
	ORDER_ID,
	ORDER_CODE,
	ORDER_TYPE,
	CUSTOMER_ID,	
	CUSTOMER_NAME,
	SALESMAN_ID,
	SALESMAN_NAME,
	SAMPLE_ID,
	SAMPLE_BTYPE,
	SAMPLE_TYPE_ID,
	SAMPLE_TYPE_NAME,
	SAMPLE_CODE,
	SAMPLE_NAME,
	SAMPLE_SIZE,
	SAMPLING_DATE,
	PURPOSE,
	TRANSPORT_STATUS,
	UPDATE_TIME,
	SAMPLE_ERROR_STATUS,
	ERROR_REASON,
	ACCEPT_SAMPLE_TIME,
	SAMPLE_ERROR_NEW_NO,
	ERROR_DEAL_REMARK
) AS 			
			
SELECT
	`S`.`ID` AS `ID`,
	`S`.`ORDER_ID` AS `ORDER_ID`,
	`O`.`CODE` AS `ORDER_CODE`,
	`O`.`ORDER_TYPE` AS `ORDER_TYPE`,
	
	`U`.`ID` AS `CUSTOMER_ID`,	
	`U`.`REAL_NAME` AS `CUSTOMER_NAME`,
	`B`.`ID` AS `SALESMAN_ID`,
	`B`.`REAL_NAME` AS `SALESMAN_NAME`,
	`S`.`SAMPLE_ID` AS `SAMPLE_ID`,
	`S`.`SAMPLE_BTYPE` AS `SAMPLE_BTYPE`,
	`S`.`SAMPLE_TYPE` AS `SAMPLE_TYPE_ID`,
	`MS`.`NAME` AS `SAMPLE_TYPE_NAME`,
	`S`.`SAMPLE_CODE` AS `SAMPLE_CODE`,
	`S`.`SAMPLE_NAME` AS `SAMPLE_NAME`,
	`S`.`SAMPLE_SIZE` AS `SAMPLE_SIZE`,
	`S`.`SAMPLING_DATE` AS `SAMPLING_DATE`,
	`S`.`PURPOSE` AS `PURPOSE`,
	`S`.`TRANSPORT_STATUS` AS `TRANSPORT_STATUS`,
	`S`.`UPDATE_TIME` AS `UPDATE_TIME`,
	`S`.`SAMPLE_ERROR_STATUS` AS `SAMPLE_ERROR_STATUS`,
	`S`.`ERROR_REASON` AS `ERROR_REASON`,
	`S`.`ACCEPT_SAMPLE_TIME` AS `ACCEPT_SAMPLE_TIME`,
	`S`.`SAMPLE_ERROR_NEW_NO` AS `SAMPLE_ERROR_NEW_NO`,
	`S`.`ERROR_DEAL_REMARK` AS `ERROR_DEAL_REMARK`
FROM
	(
		(
			(
				(
					`LIMS_ORDER_SAMPLE_VIEW` `S`
					LEFT JOIN `LIMS_METADATA_SAMPLE` `MS` ON (
						(
							`MS`.`ID` = `S`.`SAMPLE_TYPE`
						)
					)
				)
				LEFT JOIN `LIMS_ORDER` `O` ON ((`S`.`ORDER_ID` = `O`.`ID`))
			)
			LEFT JOIN `APP_USER_INFO` `U` ON ((`O`.`OWNER_ID` = `U`.`ID`))
		)
		LEFT JOIN `BUSINESS_INFO` `B` ON (
			(`O`.`CREATOR_ID` = `B`.`ID`)
		)
	)
	
--- 2017-05-15  日志记录   王巍
DROP TABLE IF EXISTS `LIMS_OPERATION_LOG`;
CREATE TABLE `LIMS_OPERATION_LOG` (
  `ID` varchar(64) NOT NULL,
  `OPERATION_TYPE` tinyint(1) DEFAULT NULL COMMENT '操作单据类型',
  `CONTENT` varchar(255) DEFAULT NULL COMMENT '操作内容',
  `REMARKS` text COMMENT '参数',
  `URL` varchar(255) DEFAULT NULL COMMENT '地址',
  `HTTP_METHOD` varchar(255) DEFAULT NULL COMMENT '请求方法',
  `IP` varchar(20) DEFAULT NULL COMMENT 'ip',
  `CLASS_METHOD` varchar(255) DEFAULT NULL COMMENT '类方法',
  `CREATE_ID` varchar(64) DEFAULT NULL COMMENT '操作人id',
  `CREATE_NAME` varchar(64) DEFAULT NULL COMMENT '操作人',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




----样本增加 异常处理人 异常处理时间  by  宋健 2017年5月16日08:45:25
ALTER TABLE `LIMS_ORDER_PRIMARY_SAMPLE` ADD COLUMN `ERROR_OPERATOR_ID`  varchar(64) DEFAULT NULL COMMENT '异常处理人ID';
ALTER TABLE `LIMS_ORDER_PRIMARY_SAMPLE` ADD COLUMN `ERROR_OPERATOR_NAME`  varchar(64) DEFAULT NULL COMMENT '异常处理人姓名';
ALTER TABLE `LIMS_ORDER_PRIMARY_SAMPLE` ADD COLUMN `ERROR_OPERATOR_TIME`  datetime DEFAULT NULL COMMENT '异常处理人时间';


ALTER TABLE `LIMS_ORDER_SUBSIDIARY_SAMPLE` ADD COLUMN `ERROR_OPERATOR_ID`  varchar(64) DEFAULT NULL COMMENT '异常处理人ID';
ALTER TABLE `LIMS_ORDER_SUBSIDIARY_SAMPLE` ADD COLUMN `ERROR_OPERATOR_NAME`  varchar(64) DEFAULT NULL COMMENT '异常处理人姓名';
ALTER TABLE `LIMS_ORDER_SUBSIDIARY_SAMPLE` ADD COLUMN `ERROR_OPERATOR_TIME`  datetime DEFAULT NULL COMMENT '异常处理人时间';

ALTER TABLE `LIMS_ORDER_RESEARCH_SAMPLE` ADD COLUMN `ERROR_OPERATOR_ID`  varchar(64) DEFAULT NULL COMMENT '异常处理人ID';
ALTER TABLE `LIMS_ORDER_RESEARCH_SAMPLE` ADD COLUMN `ERROR_OPERATOR_NAME`  varchar(64) DEFAULT NULL COMMENT '异常处理人姓名';
ALTER TABLE `LIMS_ORDER_RESEARCH_SAMPLE` ADD COLUMN `ERROR_OPERATOR_TIME`  datetime DEFAULT NULL COMMENT '异常处理人时间';



	
-- 合同发票申请列表  宋健  2017年5月16日09:06:04
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('1209', '合同发票申请列表', '/contract/contractInvoiceApplyList.do', '12', 'fa-random', '9', '0');

INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('120901', '合同发票申请列表', '/contract/contractInvoiceApplyList.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('120902', '合同发票申请查看', '/contract/contractInvoiceApplyDetail.do');

INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('11-09', '11-', '合同发票申请列表', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('11-09-01', '11-09', '合同发票申请列表', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('11-09-02', '11-09', '合同发票申请查看', NULL);

INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('11-09-01', '120901');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('11-09-02', '120902');

INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '11-09-01');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '11-09-02');

---将异常样本菜单放到业务管理下面

delete from LIMS_MENU  where ID ='1507';
delete from LIMS_RESOURCE  where ID ='150701' || ID ='150702' || ID='150703';
delete from LIMS_AUTHORITY  where ID ='15-07' || ID ='15-07-01' || ID='15-07-02' || ID='15-07-03';	
delete from LIMS_AUTHORITY_RESOURCE  where AUTHORITY_ID ='15-07' || AUTHORITY_ID ='15-07-01' || AUTHORITY_ID='15-07-02' || AUTHORITY_ID='15-07-03';	
delete from LIMS_ROLE_AUTHORITY  where ROLE_ID ='f5c6ad89615a4b44a971dc85d60f481c' and  AUTHORITY_ID='15-07-01';
delete from LIMS_ROLE_AUTHORITY  where ROLE_ID ='f5c6ad89615a4b44a971dc85d60f481c' and  AUTHORITY_ID='15-07-02';	
delete from LIMS_ROLE_AUTHORITY  where ROLE_ID ='f5c6ad89615a4b44a971dc85d60f481c' and  AUTHORITY_ID='15-07-03';	

INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('1210', '异常样本管理', '/order/errorSampleList.do', '12', 'fa-life-ring', '10', '0');

INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('121001', '异常样本列表', '/order/errorSampleList.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('121002', '异常样本处理', '/order/handleErrorSample.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('121003', '异常样本详情', '/order/viewhandleErrorSample.do');

INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('11-10', '11-', '异常样本管理', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('11-10-01', '11-10', '异常样本列表', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('11-10-02', '11-10', '异常样本处理', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('11-10-03', '11-10', '异常样本详情', NULL);


INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('11-10-01', '121001');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('11-10-02', '121002');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('11-10-03', '121003');

INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '11-10-01');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '11-10-02');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '11-10-03');
	

-- 2017-05-16  孔德成    报告表和订单产品表增加字段
ALTER TABLE `LIMS_TESTING_REPORT`
ADD COLUMN `RESUBMIT`  tinyint(1) NULL COMMENT '是否重出报告：0-不是 1-是' AFTER `REVIEWER_NAME`,
ADD COLUMN `RESUBMIT_COUNT`  int(11) NULL COMMENT '重新出报告次数' AFTER `RESUBMIT`;

ALTER TABLE `LIMS_ORDER_PRODUCT`
ADD COLUMN `REPORT_STATUS`  tinyint(1) NULL COMMENT '报告状态：0-未出报告，1-待一审，2-待二审，3-待寄送，4-已寄送' AFTER `REPORT_TIME`;



---2017年5月16日15:36:11 宋健 异常样本放在项目管理
delete from LIMS_MENU  where ID ='1210';
delete from LIMS_RESOURCE  where ID ='121001' || ID ='121002' || ID='121003';
delete from LIMS_AUTHORITY  where ID ='11-10' || ID ='11-10-01' || ID='11-10-02' || ID='11-10-03';	
delete from LIMS_AUTHORITY_RESOURCE  where  AUTHORITY_ID ='11-10-01' || AUTHORITY_ID='11-10-02' || AUTHORITY_ID='11-10-03';	
delete from LIMS_ROLE_AUTHORITY  where ROLE_ID ='f5c6ad89615a4b44a971dc85d60f481c' and  AUTHORITY_ID='11-10-01';
delete from LIMS_ROLE_AUTHORITY  where ROLE_ID ='f5c6ad89615a4b44a971dc85d60f481c' and  AUTHORITY_ID='11-10-02';	
delete from LIMS_ROLE_AUTHORITY  where ROLE_ID ='f5c6ad89615a4b44a971dc85d60f481c' and  AUTHORITY_ID='11-10-03';	


-- 开发环境 1
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('0218', '异常样本管理', '/order/errorSampleList.do', '02', 'fa-life-ring', '10', '0');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('021801', '异常样本列表', '/order/errorSampleList.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('021802', '异常样本处理', '/order/handleErrorSample.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('021803', '异常样本详情', '/order/viewhandleErrorSample.do');
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('2-18', '2', '异常样本管理', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('2-18-01', '2-18', '异常样本列表', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('2-18-02', '2-18', '异常样本处理', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('2-18-03', '2-18', '异常样本详情', NULL);
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('2-18-01', '021801');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('2-18-02', '021802');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('2-18-03', '021803');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '2-18-01');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '2-18-02');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '2-18-03');


-- 开发环境 2
delete from LIMS_MENU  where ID ='0218';
delete from LIMS_RESOURCE  where ID ='021801' || ID ='021802' || ID='021803';
delete from LIMS_AUTHORITY  where ID ='2-18' || ID ='2-18-01' || ID='2-18-02' || ID='2-18-03';	
delete from LIMS_AUTHORITY_RESOURCE  where  AUTHORITY_ID ='2-18-01' || AUTHORITY_ID='2-18-02' || AUTHORITY_ID='2-18-03';	
delete from LIMS_ROLE_AUTHORITY  where ROLE_ID ='f5c6ad89615a4b44a971dc85d60f481c' and  AUTHORITY_ID='2-18-01';
delete from LIMS_ROLE_AUTHORITY  where ROLE_ID ='f5c6ad89615a4b44a971dc85d60f481c' and  AUTHORITY_ID='2-18-02';	
delete from LIMS_ROLE_AUTHORITY  where ROLE_ID ='f5c6ad89615a4b44a971dc85d60f481c' and  AUTHORITY_ID='2-18-03';


-----测试环境 将异常样本从 业务管理 ---样本管理下面
delete from LIMS_MENU  where ID ='1210';
delete from LIMS_RESOURCE  where ID ='121001' || ID ='121002' || ID='121003';
delete from LIMS_AUTHORITY  where ID ='11-10' || ID ='11-10-01' || ID='11-10-02' || ID='11-10-03';	
delete from LIMS_AUTHORITY_RESOURCE  where  AUTHORITY_ID ='11-10-01' || AUTHORITY_ID='11-10-02' || AUTHORITY_ID='11-10-03';	
delete from LIMS_ROLE_AUTHORITY  where ROLE_ID ='f5c6ad89615a4b44a971dc85d60f481c' and  AUTHORITY_ID='11-10-01';
delete from LIMS_ROLE_AUTHORITY  where ROLE_ID ='f5c6ad89615a4b44a971dc85d60f481c' and  AUTHORITY_ID='11-10-02';	
delete from LIMS_ROLE_AUTHORITY  where ROLE_ID ='f5c6ad89615a4b44a971dc85d60f481c' and  AUTHORITY_ID='11-10-03';	


INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('1507', '异常样本管理', '/order/errorSampleList.do', '15', 'fa-life-ring', '8', '0');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('150701', '异常样本列表', '/order/errorSampleList.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('150702', '异常样本处理', '/order/handleErrorSample.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('150703', '异常样本详情', '/order/viewhandleErrorSample.do');
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('15-07', '15', '异常样本管理', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('15-07-01', '15-07', '异常样本列表', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('15-07-02', '15-07', '异常样本处理', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('15-07-03', '15-07', '异常样本详情', NULL);
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('15-07', '1507');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('15-07-01', '150701');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('15-07-02', '150702');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('15-07-03', '150703');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '15-07-01');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '15-07-02');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '15-07-03');



-- 订单增加采样费用字段
ALTER TABLE `LIMS_ORDER` ADD COLUMN `SAMPLING_AMOUNT`  int(11) default 0 COMMENT '采样费用' AFTER `INCOMING_AMOUNT`;



----APP端  视图增加订单id
DROP VIEW IF EXISTS `MY_SAMPLE_VIEW`;
CREATE VIEW `MY_SAMPLE_VIEW` AS
SELECT
    `O`.`CODE` AS `ORDER_NO`,
	`O`.`ID` AS `ORDER_ID`,
    `O`.`ORDER_TYPE` AS `ORDER_TYPE`,
    `O`.`STATUS` AS `ORDER_STATUS`,
    `O`.`OWNER_ID` AS `OWNER_ID`,
    `PS`.`EXAMINEE_ID` AS `EXAMINEE_ID`,
    `E`.`NAME` AS `EXAMINEE_NAME`,
    NULL AS `FAMILY_RELATION`,
    `PS`.`ID` AS `SAMPLE_ID`,
    `PS`.`SAMPLE_CODE` AS `SAMPLE_CODE`,
    `MS`.`NAME` AS `SAMPLE_TYPE`,
    NULL AS `SAMPLE_NAME`,
    `PS`.`SAMPLE_PACKAGE_NO` AS `SAMPLE_PACKAGE_NO`,
    `PS`.`SAMPLE_PACKAGE_STATUS` AS `SAMPLE_PACKAGE_STATUS`,
    `PS`.`SAMPLE_ERROR_STATUS` AS `SAMPLE_ERROR_STATUS`,
    `PS`.`SAMPLE_ERROR_NEW_NO` AS `SAMPLE_ERROR_NEW_NO`,
    1 AS `SAMPLE_TABLE_TYPE`,
    `PS`.`SAMPLE_BACK_STATUS` AS `SAMPLE_BACK_STATUS`,
    `PS`.`SAMPLE_BACK_OPTION` AS `SAMPLE_BACK_OPTION`,
    `PS`.`SAMPLE_BACK_NO` AS `SAMPLE_BACK_NO`,
	`PS`.`ACCEPT_SAMPLE_TIME` AS `ACCEPT_SAMPLE_TIME`,
	`PS`.`ERROR_REASON` AS `ERROR_REASON`,
	`PS`.`ERROR_DEAL_REMARK` AS `ERROR_DEAL_REMARK`
FROM
    (
        (
            (
                `LIMS_ORDER_PRIMARY_SAMPLE` `PS`
                LEFT JOIN `LIMS_ORDER` `O` ON ((`PS`.`ORDER_ID` = `O`.`ID`)) 
            )
            LEFT JOIN `LIMS_ORDER_EXAMINEE` `E` ON (
                (
                    `PS`.`EXAMINEE_ID` = `E`.`ID`
                )
            )
        )
        LEFT JOIN `LIMS_METADATA_SAMPLE` `MS` ON (
            (
                `PS`.`SAMPLE_TYPE_ID` = `MS`.`ID`
            )
        )

    )
where `O`.DEL_FLAG ='0'

UNION ALL
    SELECT
        `O`.`CODE` AS `ORDER_NO`,
		`O`.`ID` AS `ORDER_ID`,
        `O`.`ORDER_TYPE` AS `ORDER_TYPE`,
        `O`.`STATUS` AS `ORDER_STATUS`,
        `O`.`OWNER_ID` AS `OWNER_ID`,
        `SS`.`EXAMINEE_ID` AS `EXAMINEE_ID`,
        NULL AS `EXAMINEE_NAME`,
        `D`.`DICT_TEXT` AS `FAMILY_RELATION`,
        `SS`.`ID` AS `SAMPLE_ID`,
        `SS`.`SAMPLE_CODE` AS `SAMPLE_CODE`,
        `MS`.`NAME` AS `SAMPLE_TYPE`,
        NULL AS `SAMPLE_NAME`,
        `SS`.`SAMPLE_PACKAGE_NO` AS `SAMPLE_PACKAGE_NO`,
        `SS`.`SAMPLE_PACKAGE_STATUS` AS `SAMPLE_PACKAGE_STATUS`,
        `SS`.`SAMPLE_ERROR_STATUS` AS `SAMPLE_ERROR_STATUS`,
        `SS`.`SAMPLE_ERROR_NEW_NO` AS `SAMPLE_ERROR_NEW_NO`,
        2 AS `SAMPLE_TABLE_TYPE`,
        `SS`.`SAMPLE_BACK_STATUS` AS `SAMPLE_BACK_STATUS`,
        `SS`.`SAMPLE_BACK_OPTION` AS `SAMPLE_BACK_OPTION`,
        `SS`.`SAMPLE_BACK_NO` AS `SAMPLE_BACK_NO`,
		`SS`.`ACCEPT_SAMPLE_TIME` AS `ACCEPT_SAMPLE_TIME`,
		`SS`.`ERROR_REASON` AS `ERROR_REASON`,
		`SS`.`ERROR_DEAL_REMARK` AS `ERROR_DEAL_REMARK`
    FROM
        (
            (
                (
                    `LIMS_ORDER_SUBSIDIARY_SAMPLE` `SS`
                    LEFT JOIN `LIMS_ORDER` `O` ON ((`SS`.`ORDER_ID` = `O`.`ID`)) 
                )
                LEFT JOIN `LIMS_METADATA_SAMPLE` `MS` ON (
                    (
                        `SS`.`SAMPLE_TYPE_ID` = `MS`.`ID`
                    )
                )
            )
            LEFT JOIN `LIMS_DICT` `D` ON (
                (
                    (
                        `D`.`CATEGORY` = 'FAMILY_RELATION'
                    )
                    AND (
                        `D`.`DICT_VALUE` = `SS`.`FAMILY_RELATION`
                    )
                )
            )
        )
where `O`.DEL_FLAG ='0'

    UNION ALL
        SELECT
            `O`.`CODE` AS `ORDER_NO`,
			`O`.`ID` AS `ORDER_ID`,
            `O`.`ORDER_TYPE` AS `ORDER_TYPE`,
            `O`.`STATUS` AS `ORDER_STATUS`,
            `O`.`OWNER_ID` AS `OWNER_ID`,
            NULL AS `EXAMINEE_ID`,
            NULL AS `EXAMINEE_NAME`,
            NULL AS `FAMILY_RELATION`,
            `RS`.`ID` AS `SAMPLE_ID`,
            `RS`.`SAMPLE_CODE` AS `SAMPLE_CODE`,
            `MS`.`NAME` AS `SAMPLE_TYPE`,
            `RS`.`SAMPLE_NAME` AS `SAMPLE_NAME`,
            `RS`.`SAMPLE_PACKAGE_NO` AS `SAMPLE_PACKAGE_NO`,
            `RS`.`SAMPLE_PACKAGE_STATUS` AS `SAMPLE_PACKAGE_STATUS`,
            `RS`.`SAMPLE_ERROR_STATUS` AS `SAMPLE_ERROR_STATUS`,
            `RS`.`SAMPLE_ERROR_NEW_NO` AS `SAMPLE_ERROR_NEW_NO`,
            3 AS `SAMPLE_TABLE_TYPE`,
            `RS`.`SAMPLE_BACK_STATUS` AS `SAMPLE_BACK_STATUS`,
            `RS`.`SAMPLE_BACK_OPTION` AS `SAMPLE_BACK_OPTION`,
            `RS`.`SAMPLE_BACK_NO` AS `SAMPLE_BACK_NO`,
			`RS`.`ACCEPT_SAMPLE_TIME` AS `ACCEPT_SAMPLE_TIME`,
			`RS`.`ERROR_REASON` AS `ERROR_REASON`,
			`RS`.`ERROR_DEAL_REMARK` AS `ERROR_DEAL_REMARK`
        FROM
            (
                (
                    `LIMS_ORDER_RESEARCH_SAMPLE` `RS`
                    LEFT JOIN `LIMS_ORDER` `O` ON ((`RS`.`ORDER_ID` = `O`.`ID`)) 
                )
                LEFT JOIN `LIMS_METADATA_SAMPLE` `MS` ON (
                    (
                        `RS`.`SAMPLE_TYPE_ID` = `MS`.`ID`
                    )
                )
            )  where `O`.DEL_FLAG ='0';

            
            
-- 检测方法分析节点  孔德成  2017-05-24
UPDATE `LIMS_TESTING_METHOD` SET `TESTING_PROCESS`='MLPA', `ANALYSE_PROCESS`='MLPA-DATA-ANALYSIS' WHERE (`ID`='7438a1b9ccc011e685b40242ac110002');
UPDATE `LIMS_TESTING_METHOD` SET `TESTING_PROCESS`='PCR-ONE/PCR-TWO', `ANALYSE_PROCESS`='DATA-ANALYSIS' WHERE (`ID`='81d62b545cf24ac7993f1f4d404dd0c9');
UPDATE `LIMS_TESTING_METHOD` SET `TESTING_PROCESS`='DT', `ANALYSE_PROCESS`='DT-DATA-ANALYSIS' WHERE (`ID`='a01cb8cad22b11e68ee90242ac110004');
UPDATE `LIMS_TESTING_METHOD` SET `TESTING_PROCESS`='MLPA', `ANALYSE_PROCESS`='MLPA-DATA-ANALYSIS' WHERE (`ID`='ce753621e9b711e68ee90242ac110004');
UPDATE `LIMS_TESTING_METHOD` SET `TESTING_PROCESS`='FLUO-TEST', `ANALYSE_PROCESS`='FLUO-ANALYSE' WHERE (`ID`='doks8934gfdscvf7834ihsdhb');

ALTER TABLE `LIMS_TESTING_REPORT`
MODIFY COLUMN `RESUBMIT`  tinyint(1) NULL DEFAULT 0 COMMENT '是否重出报告：0-不是 1-是' AFTER `REVIEWER_NAME`;      

----周期配置  2017.5.26 王巍
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('0218', '项目监控', '', '02', 'fa-building-o', '11', '0');
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('021801', '周期配置', '/bpm/cycleConfig/tabList.do', '0218', 'fa-tasks', '1', '0');

INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('021801', '周期配置', '/bpm/cycleConfig/tabList.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('0218011', '检测添加', '/bpm/cycleConfig/create.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('0218012', '检测修改', '/bpm/cycleConfig/modify.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('0218013', '检测删除', '/bpm/cycleConfig/delete.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('0218014', '固定修改', '/bpm/cycleConfig/fixedModify.do');

INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('2-18', '2', '项目监控', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('2-18-0', '2-18', '周期配置', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('2-18-0-0', '2-18-0', '检测配置添加', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('2-18-0-1', '2-18-0', '检测配置修改', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('2-18-0-2', '2-18-0', '检测配置删除', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('2-18-0-3', '2-18-0', '固定修改', NULL);

INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('2-18-0', '021801');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('2-18-0-0', '0218011');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('2-18-0-1', '0218012');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('2-18-0-2', '0218013');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('2-18-0-3', '0218014');

INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '2-18-0');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '2-18-0-0');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '2-18-0-1');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '2-18-0-2');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '2-18-0-3');

--------周期配置
insert into LIMS_WARNING_GLOBAL_CONFIG(ID,EVENT_KEY,EVENT_NAME,EVENT_DESC,THRESHOLD,DISABLED) values('1590eb0c640f4765893ff8e50927b642','SAMPLE_RECEIVE','物流运输','下单时间到样本全部接收',5,0);
insert into LIMS_WARNING_GLOBAL_CONFIG(ID,EVENT_KEY,EVENT_NAME,EVENT_DESC,THRESHOLD,DISABLED) values('21b6c8dc566f4d8393ec2c5a0bd283a1','SAMPLE_STORAGE','转存入库','全部收样到全部入库',5,0);
insert into LIMS_WARNING_GLOBAL_CONFIG(ID,EVENT_KEY,EVENT_NAME,EVENT_DESC,THRESHOLD,DISABLED) values('6578c6cbd8604ebe924452fed57ac9d3','PAYMENT_CONFIRM','财务下放','缴费日期到付款确认',5,0);
insert into LIMS_WARNING_GLOBAL_CONFIG(ID,EVENT_KEY,EVENT_NAME,EVENT_DESC,THRESHOLD,DISABLED) values('6607dcc3654f49eca4bcea40d38926e3','REPORT_GENERATE','报告整合','进入报告整合到出报告',5,0);
insert into LIMS_WARNING_GLOBAL_CONFIG(ID,EVENT_KEY,EVENT_NAME,EVENT_DESC,THRESHOLD,DISABLED) values('73ce07d86428497abf767676c7e4e877','REPORT_VERIFY','报告审核','进入一审到二审核结束',5,0);
insert into LIMS_WARNING_GLOBAL_CONFIG(ID,EVENT_KEY,EVENT_NAME,EVENT_DESC,THRESHOLD,DISABLED) values('95c408d347d9479a98fe6443c6b339ed','REPORT_DELIVER','报告寄送','进入待寄送到报告寄送',5,0);

-----检测配置 4个验证
insert into LIMS_SCHEDULE_TESTING_CONFIG(ID,TESTING_METHOD_ID,CONFIG_NAME,CONFIG_DESC,THRESHOLD,CREATE_TIME,DEL_FLAG) 
values('15c04eb88da04dcf9ec5ddfc2098d407','81d62b545cf24ac7993f1f4d404dd0c9','Sanger验证','',6,NOW(),false);

insert into LIMS_SCHEDULE_TESTING_NODE_CONFIG(ID,CONFIG_ID,NODE_SEMANTIC,NODE_NAME,THRESHOLD)values('1ac0b597c303467ab4c122a77792d8cf','15c04eb88da04dcf9ec5ddfc2098d407','DNA-EXT','DNA提取',1);
insert into LIMS_SCHEDULE_TESTING_NODE_CONFIG(ID,CONFIG_ID,NODE_SEMANTIC,NODE_NAME,THRESHOLD)values('170a75c620a044938eaadde2632d5620','15c04eb88da04dcf9ec5ddfc2098d407','DNA-QC','DNA质检',1);
insert into LIMS_SCHEDULE_TESTING_NODE_CONFIG(ID,CONFIG_ID,NODE_SEMANTIC,NODE_NAME,THRESHOLD)values('412b4de9db824c7bbe25b4f6dab9f3c1','15c04eb88da04dcf9ec5ddfc2098d407','PCR-ONE','一次PCR',1);
insert into LIMS_SCHEDULE_TESTING_NODE_CONFIG(ID,CONFIG_ID,NODE_SEMANTIC,NODE_NAME,THRESHOLD)values('d89c79a2ccc142f08b718c7d933e47e5','15c04eb88da04dcf9ec5ddfc2098d407','PCR-TWO','二次PCR',1);
insert into LIMS_SCHEDULE_TESTING_NODE_CONFIG(ID,CONFIG_ID,NODE_SEMANTIC,NODE_NAME,THRESHOLD)values('72cdf555458e428b8829d50b154b4c54','15c04eb88da04dcf9ec5ddfc2098d407','DATA-ANALYSIS','SANGER验证-数据分析',2);

insert into LIMS_SCHEDULE_TESTING_CONFIG(ID,TESTING_METHOD_ID,CONFIG_NAME,CONFIG_DESC,THRESHOLD,CREATE_TIME,DEL_FLAG) 
values('e5cc0700f7164018b5fa65eef84853e6','ce753621e9b711e68ee90242ac110004','MLPA验证','',6,NOW(),false);

insert into LIMS_SCHEDULE_TESTING_NODE_CONFIG(ID,CONFIG_ID,NODE_SEMANTIC,NODE_NAME,THRESHOLD)values('5e9cb66cf6594a819a3dd4bcd37e7101','e5cc0700f7164018b5fa65eef84853e6','DNA-EXT','DNA提取',1);
insert into LIMS_SCHEDULE_TESTING_NODE_CONFIG(ID,CONFIG_ID,NODE_SEMANTIC,NODE_NAME,THRESHOLD)values('d0bce0182900422c896ee0f9b38c858f','e5cc0700f7164018b5fa65eef84853e6','DNA-QC','DNA质检',1);
insert into LIMS_SCHEDULE_TESTING_NODE_CONFIG(ID,CONFIG_ID,NODE_SEMANTIC,NODE_NAME,THRESHOLD)values('2daa09b08ab34958873214fc9b8d1dd9','e5cc0700f7164018b5fa65eef84853e6','MLPA','MLPA实验',2);
insert into LIMS_SCHEDULE_TESTING_NODE_CONFIG(ID,CONFIG_ID,NODE_SEMANTIC,NODE_NAME,THRESHOLD)values('021545e6d67f4298b244813976a4bbb3','e5cc0700f7164018b5fa65eef84853e6','MLPA-DATA-ANALYSIS','MLPA数据分析',2);

insert into LIMS_SCHEDULE_TESTING_CONFIG(ID,TESTING_METHOD_ID,CONFIG_NAME,CONFIG_DESC,THRESHOLD,CREATE_TIME,DEL_FLAG) 
values('379e2b991207463da5130669e9b91744','5e6445e4b93547409639685eb3fb6116','QPCR验证','',4,NOW(),false);

insert into LIMS_SCHEDULE_TESTING_NODE_CONFIG(ID,CONFIG_ID,NODE_SEMANTIC,NODE_NAME,THRESHOLD)values('209fc3facb664d23b5c4f476a8c8c664','379e2b991207463da5130669e9b91744','DNA-EXT','DNA提取',1);
insert into LIMS_SCHEDULE_TESTING_NODE_CONFIG(ID,CONFIG_ID,NODE_SEMANTIC,NODE_NAME,THRESHOLD)values('ab8cef630f124a60b101f31986dc453a','379e2b991207463da5130669e9b91744','DNA-QC','DNA质检',1);
insert into LIMS_SCHEDULE_TESTING_NODE_CONFIG(ID,CONFIG_ID,NODE_SEMANTIC,NODE_NAME,THRESHOLD)values('6518fca249a44201bc851180cff2fcba','379e2b991207463da5130669e9b91744','QPCR','QPCR实验',2);

insert into LIMS_SCHEDULE_TESTING_CONFIG(ID,TESTING_METHOD_ID,CONFIG_NAME,CONFIG_DESC,THRESHOLD,CREATE_TIME,DEL_FLAG) 
values('ff03467dc91a4322b1d7e33fdfce2097','fbfbf1d9ec0911e68ee90242ac110004','PCR-NGS验证','',9,NOW(),false);

insert into LIMS_SCHEDULE_TESTING_NODE_CONFIG(ID,CONFIG_ID,NODE_SEMANTIC,NODE_NAME,THRESHOLD)values('a74e7bae0ab345149b7c538ff010af06','ff03467dc91a4322b1d7e33fdfce2097','DNA-EXT','DNA提取',1);
insert into LIMS_SCHEDULE_TESTING_NODE_CONFIG(ID,CONFIG_ID,NODE_SEMANTIC,NODE_NAME,THRESHOLD)values('be7cac5898014dd08b5a01e0e356eea6','ff03467dc91a4322b1d7e33fdfce2097','DNA-QC','DNA质检',1);
insert into LIMS_SCHEDULE_TESTING_NODE_CONFIG(ID,CONFIG_ID,NODE_SEMANTIC,NODE_NAME,THRESHOLD)values('bf83d1063bf6451882713caa88432462','ff03467dc91a4322b1d7e33fdfce2097','PCR-NGS','PCR-NGS实验',1);
insert into LIMS_SCHEDULE_TESTING_NODE_CONFIG(ID,CONFIG_ID,NODE_SEMANTIC,NODE_NAME,THRESHOLD)values('d1a83da287b44396b0165920a1172480','ff03467dc91a4322b1d7e33fdfce2097','RQT','相对定量',1);
insert into LIMS_SCHEDULE_TESTING_NODE_CONFIG(ID,CONFIG_ID,NODE_SEMANTIC,NODE_NAME,THRESHOLD)values('96ef3bd6531945da8b88fe39bcad52de','ff03467dc91a4322b1d7e33fdfce2097','POOLING','混样',1);
insert into LIMS_SCHEDULE_TESTING_NODE_CONFIG(ID,CONFIG_ID,NODE_SEMANTIC,NODE_NAME,THRESHOLD)values('89ed8d5aee3641749a2d93d81a869af1','ff03467dc91a4322b1d7e33fdfce2097','QT','绝对定量',1);
insert into LIMS_SCHEDULE_TESTING_NODE_CONFIG(ID,CONFIG_ID,NODE_SEMANTIC,NODE_NAME,THRESHOLD)values('03f9faa71aee42ffb9f48465f7780fff','ff03467dc91a4322b1d7e33fdfce2097','NGS-SEQ','NGS测序',1);
insert into LIMS_SCHEDULE_TESTING_NODE_CONFIG(ID,CONFIG_ID,NODE_SEMANTIC,NODE_NAME,THRESHOLD)values('8734cc5db69940dbb0824dc44c97cafe','ff03467dc91a4322b1d7e33fdfce2097','BIOLOGY-ANALY','生信分析',1);
insert into LIMS_SCHEDULE_TESTING_NODE_CONFIG(ID,CONFIG_ID,NODE_SEMANTIC,NODE_NAME,THRESHOLD)values('1f3d2cf599ec45819c3acb3bcc9c4f5a','ff03467dc91a4322b1d7e33fdfce2097','PCR-NGS-DATA-ANALYSIS','PCR-NGS数据分析',1);



-- xiugai shujuzidian 
update  LIMS_DICT set DICT_TEXT ='一代验证' where DICT_TEXT ='验证';
update  LIMS_DICT set DICT_TEXT ='本人对照' where DICT_TEXT ='对照';

-- 2017.6.5 孔德成
UPDATE `LIMS_DATA_AUTHORITY` SET `RESOURCE_NAME`='任务列表' WHERE (`RESOURCE_KEY`='COMPLETE_TASK_LIST');


---订单产品状态设置默认值 更改旧数据 宋健
update LIMS_ORDER_PRODUCT set PRODUCT_STATUS ='0' where PRODUCT_STATUS is NULL;
update LIMS_ORDER_PAYMENT_CONFIRM set PAYMENT_TYPE ='1' where PAYMENT_TYPE is NULL;


-- 2017.6.8 孔德成
ALTER TABLE `LIMS_FINANCE_INVOICE_TASK`
ADD COLUMN `UPDATE_TIME`  datetime NULL COMMENT '处理时间' AFTER `STATUS`;

ALTER TABLE `LIMS_CONTRACT_ORG`
ADD COLUMN `TEST_INSTITUTION`  varchar(64) NULL COMMENT '检测机构' AFTER `BANK_ACCOUNT_NO`;

UPDATE `LIMS_CONTRACT_ORG` SET `TEST_INSTITUTION`='0' WHERE (`ID`='1');
UPDATE `LIMS_CONTRACT_ORG` SET `TEST_INSTITUTION`='1' WHERE (`ID`='2');

ALTER TABLE `LIMS_CONTRACT_PARTY_B`
ADD COLUMN `TEST_INSTITUTION`  varchar(64) NULL COMMENT '检测机构' AFTER `BANK_ACCOUNT_NO`;

UPDATE `LIMS_CONTRACT_PARTY_B` SET `TEST_INSTITUTION`='0' WHERE (`COMPANY_NAME`='北京迈基诺基因科技有限责任公司医学检验所');


UPDATE `LIMS_CONTRACT_PARTY_B` SET `TEST_INSTITUTION`='1' WHERE (`COMPANY_NAME`='北京迈基诺基因科技股份有限公司');




---增加数据字典 已执行  宋健
INSERT INTO `LIMS_DICT`(`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) 
VALUES ('d013a56cvc3443bbbef8he77b78d6417', '', 'TESTING_STATUS', '订单主状态', '', '', '', '0');

INSERT INTO `LIMS_DICT`(`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) 
VALUES ('d013a56cvc3443bbbef8he77b78d6401', 'd013a56cvc3443bbbef8he77b78d6417', 'TESTING_STATUS', '暂存', '0', '0', '', '0');

INSERT INTO `LIMS_DICT`(`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) 
VALUES ('d013a56cvc3443bbbef8he77b78d6402', 'd013a56cvc3443bbbef8he77b78d6417', 'TESTING_STATUS', '待检测下放', '1', '1', '', '0');

INSERT INTO `LIMS_DICT`(`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) 
VALUES ('d013a56cvc3443bbbef8he77b78d6403', 'd013a56cvc3443bbbef8he77b78d6417', 'TESTING_STATUS', '检测中', '2', '2', '', '0');

INSERT INTO `LIMS_DICT`(`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) 
VALUES ('d013a56cvc3443bbbef8he77b78d6404', 'd013a56cvc3443bbbef8he77b78d6417', 'TESTING_STATUS', '已暂停', '3', '3', '', '0');

INSERT INTO `LIMS_DICT`(`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) 
VALUES ('d013a56cvc3443bbbef8he77b78d6405', 'd013a56cvc3443bbbef8he77b78d6417', 'TESTING_STATUS', '已取消', '4', '4', '', '0');

INSERT INTO `LIMS_DICT`(`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) 
VALUES ('d013a56cvc3443bbbef8he77b78d6406', 'd013a56cvc3443bbbef8he77b78d6417', 'TESTING_STATUS', '已完成', '5', '5', '', '0');


UPDATE `LIMS_CONTRACT_PARTY_B` SET `TEST_INSTITUTION`='1' WHERE (`COMPANY_NAME`='北京迈基诺基因科技股份有限公司');
UPDATE `LIMS_CONTRACT_PARTY_B` SET `TEST_INSTITUTION`='1' WHERE (`COMPANY_NAME`='北京迈基诺基因科技股份有限公司');




-- 更改老数据 宋健 已执行
update  LIMS_ORDER SET TESTING_STATUS = 1 WHERE TESTING_STATUS IS NULL OR TESTING_STATUS ='0';
update  LIMS_ORDER SET PAYMENT_STATUS = 2 WHERE STATUS ='4';
update  LIMS_ORDER SET PAYMENT_STATUS = 1 WHERE STATUS ='2';
update  LIMS_ORDER SET TESTING_STATUS = 2 WHERE STATUS ='7'; 
update  LIMS_ORDER SET TESTING_STATUS = 4 WHERE STATUS ='9'; 
update  LIMS_ORDER SET PAYMENT_DELAY_STATUS = 0 WHERE PAYMENT_DELAY_STATUS  is null; 
update  LIMS_ORDER SET PAYMENT_STATUS = 0 WHERE PAYMENT_STATUS  is null; 
ALTER TABLE `LIMS_ORDER` MODIFY `STATUS` tinyint NULL;

UPDATE LIMS_ORDER o
SET o.SETTLEMENT_TYPE = 2
WHERE

 o.CONTRACT_ID IN (
	SELECT
		c.CONTRACT_ID
	FROM
		LIMS_CONTRACT_CONTENT c
	WHERE
		c.SETTLEMENT_MODE != '4'
);

UPDATE LIMS_ORDER o
SET o.SETTLEMENT_TYPE = 1
WHERE
o.CONTRACT_ID is null or 
 o.CONTRACT_ID IN (
	SELECT
		c.CONTRACT_ID
	FROM
		LIMS_CONTRACT_CONTENT c
	WHERE
		c.SETTLEMENT_MODE = '4'
);




----合同收款记录表增加是否已对账字段  宋健已执行
ALTER TABLE `LIMS_CONTRACT_PAYMENT_RECORD` ADD COLUMN `RECONCILIATION`  tinyint  default 0 COMMENT '是否对账 0：未对账, 1 已对账' ;


----增加合同对账记录表 宋健 已执行
DROP TABLE IF EXISTS `LIMS_CONTRACT_RECONCILIATION_RECORD`;
CREATE TABLE `LIMS_CONTRACT_RECONCILIATION_RECORD` (
  `ID` varchar(64) NOT NULL,  
  `PAYMENT_RECORD_ID` varchar(64) DEFAULT NULL COMMENT '收款记录表id',
  `CONTRACT_ID` varchar(64) DEFAULT NULL COMMENT '对账合同id',
  `ORDER_ID` varchar(64) DEFAULT NULL COMMENT '对账订单id',
  `ORDER_CODE` varchar(64) DEFAULT NULL COMMENT '对账订单编号',
  `RECONCILIATION_AMOUNT` int DEFAULT 0 COMMENT '对账金额', 
  `CREATE_ID` varchar(64) DEFAULT NULL COMMENT '操作人id',
  `CREATE_NAME` varchar(64) DEFAULT NULL COMMENT '操作人',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

---合同增加未对账金额字段 2017年6月21日14:20:44 By 宋健 已执行
ALTER table LIMS_CONTRACT add COLUMN UNRECONCILED_AMOUNT int default 0 COMMENT '未对账金额';
update  LIMS_CONTRACT a set a.UNRECONCILED_AMOUNT = a.INCOMING_AMOUNT ;
-- 2017.6.19 孔德成 
ALTER TABLE `LIMS_TESTING_SCHEDULE_HISTORY`
ADD COLUMN `REMARK`  varchar(1024) NULL COMMENT '取消/暂停备注' AFTER `RESTART_NAME`;



----增加已取消订单处理列表  2017年6月22日15:14:58  By 宋健 
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('1312', '已取消订单管理', '/payment/cancelOrderList.do', '13', 'fa-life-ring', '7', '0');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('1307', '已取消订单管理', '');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('130701', '已取消订单列表', '/payment/cancelOrderList.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('130702', '已取消订单处理', '/payment/handleCancelOrder.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('130703', '已取消订单详情', '/payment/viewCancelOrder.do');
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('12-12', '12-', '已取消订单管理', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('12-12-01', '12-12', '已取消订单列表', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('12-12-02', '12-12', '已取消订单处理', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('12-12-03', '12-12', '已取消订单详情', NULL);
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('12-12', '1307');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('12-12-01', '130701');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('12-12-02', '130702');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('12-12-03', '130703');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '12-12-01');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '12-12-02');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '12-12-03');

-- 合同变更业务员  2017.6.26  王巍
DROP TABLE IF EXISTS `LIMS_CONTRACT_CHANGE_SIGN_USER`;
CREATE TABLE `LIMS_CONTRACT_CHANGE_SIGN_USER` (
  `ID` varchar(64) NOT NULL,
  `CONTRACT_ID` varchar(64) NOT NULL,
  `BEFORE_SIGN_USER` varchar(64) NOT NULL COMMENT '变更前业务员',
  `AFTER_SIGN_USER` varchar(64) NOT NULL COMMENT '变更后业务员',
  `OPERATE_ID` varchar(64) DEFAULT NULL COMMENT '操作员ID',
  `OPERATE_NAME` varchar(64) DEFAULT NULL COMMENT '操作员',
  `OPERATE_TIME` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--  物资项管理 2017.6.26 王巍
DROP TABLE IF EXISTS `LIMS_MATERIAL`;
CREATE TABLE `LIMS_MATERIAL` (
  `ID` varchar(64) NOT NULL,
  `TYPE` tinyint(1) NOT NULL COMMENT '类型 1类别  2名称',
  `MAT_NAME` varchar(128) DEFAULT NULL,
  `SORT_ID` varchar(64) DEFAULT NULL COMMENT '类别id',
  `DESCRIPTION` varchar(256) DEFAULT NULL COMMENT '描述',
  `CREATE_TIME` datetime DEFAULT NULL,
  `STATE` tinyint(1) DEFAULT NULL COMMENT '状态 0 启用  1禁用',
  `DEL_FLAG` tinyint(1) DEFAULT NULL COMMENT '删除标记  1未删除   1删除',
  `DELETE_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('0809', '物资项管理', '/smm/material/tabList.do', '08', 'fa-building-o', '9', '0');

INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('0809', '物资项管理', '/smm/material/tabList.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('080901', '类别管理新增', '/smm/material/sortCreate.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('080902', '类别管理修改', '/smm/material/sortModify.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('080903', '类别管理删除', '/smm/material/sortDelete.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('080904', '名称管理新增', '/smm/material/nameCreate.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('080905', '名称管理修改', '/smm/material/nameModify.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('080906', '名称管理删除', '/smm/material/nameDelete.do');

INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('7-09', '7', '物资项管理', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('7-09-01', '7-09', '类别管理新增', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('7-09-02', '7-09', '类别管理修改', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('7-09-03', '7-09', '类别管理删除', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('7-09-04', '7-09', '名称管理新增', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('7-09-05', '7-09', '名称管理修改', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('7-09-06', '7-09', '名称管理删除', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('7-09-07', '7-09', '物资项管理列表', NULL);

INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('7-09-01', '080901');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('7-09-02', '080902');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('7-09-03', '080903');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('7-09-04', '080904');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('7-09-05', '080905');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('7-09-06', '080906');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('7-09-07', '0809');

INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '7-09-07');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '7-09-01');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '7-09-02');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '7-09-03');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '7-09-04');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '7-09-05');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '7-09-06');

-- 加急处理 2017.6.26 王巍
alter table LIMS_TESTING_TASK add COLUMN IF_URGENT tinyint(1) COMMENT '0-不加急 1-加急';
alter table LIMS_TESTING_TASK add COLUMN URGENT_UPDATE_TIME datetime COMMENT '加急变更时间';
alter table LIMS_TESTING_TASK add COLUMN URGENT_NAME varchar(255) COMMENT '加急人';

alter table LIMS_ORDER add COLUMN IF_URGENT tinyint(1) COMMENT '0-不加急 1-加急';
alter table LIMS_ORDER add COLUMN URGENT_UPDATE_TIME datetime COMMENT '加急变更时间';
alter table LIMS_ORDER add COLUMN URGENT_NAME varchar(255) COMMENT '加急人';


---旧数据处理  宋健
update LIMS_ORDER set SCHEDULE_PAYMENT_STATUS = 0 where SCHEDULE_PAYMENT_STATUS is null;



DROP VIEW IF EXISTS `MY_SAMPLE_VIEW`;
CREATE VIEW `MY_SAMPLE_VIEW` AS
SELECT
    `O`.`CODE` AS `ORDER_NO`,
	`O`.`ID` AS `ORDER_ID`,
    `O`.`ORDER_TYPE` AS `ORDER_TYPE`,
    `O`.`TESTING_STATUS` AS `ORDER_STATUS`,
    `O`.`OWNER_ID` AS `OWNER_ID`,
    `PS`.`EXAMINEE_ID` AS `EXAMINEE_ID`,
    `E`.`NAME` AS `EXAMINEE_NAME`,
    NULL AS `FAMILY_RELATION`,
    `PS`.`ID` AS `SAMPLE_ID`,
    `PS`.`SAMPLE_CODE` AS `SAMPLE_CODE`,
    `MS`.`NAME` AS `SAMPLE_TYPE`,
    NULL AS `SAMPLE_NAME`,
    `PS`.`SAMPLE_PACKAGE_NO` AS `SAMPLE_PACKAGE_NO`,
    `PS`.`SAMPLE_PACKAGE_STATUS` AS `SAMPLE_PACKAGE_STATUS`,
    `PS`.`SAMPLE_ERROR_STATUS` AS `SAMPLE_ERROR_STATUS`,
    `PS`.`SAMPLE_ERROR_NEW_NO` AS `SAMPLE_ERROR_NEW_NO`,
    1 AS `SAMPLE_TABLE_TYPE`,
    `PS`.`SAMPLE_BACK_STATUS` AS `SAMPLE_BACK_STATUS`,
    `PS`.`SAMPLE_BACK_OPTION` AS `SAMPLE_BACK_OPTION`,
    `PS`.`SAMPLE_BACK_NO` AS `SAMPLE_BACK_NO`,
	`PS`.`ACCEPT_SAMPLE_TIME` AS `ACCEPT_SAMPLE_TIME`,
	`PS`.`ERROR_REASON` AS `ERROR_REASON`,
	`PS`.`ERROR_DEAL_REMARK` AS `ERROR_DEAL_REMARK`
FROM
    (
        (
            (
                `LIMS_ORDER_PRIMARY_SAMPLE` `PS`
                LEFT JOIN `LIMS_ORDER` `O` ON ((`PS`.`ORDER_ID` = `O`.`ID`)) 
            )
            LEFT JOIN `LIMS_ORDER_EXAMINEE` `E` ON (
                (
                    `PS`.`EXAMINEE_ID` = `E`.`ID`
                )
            )
        )
        LEFT JOIN `LIMS_METADATA_SAMPLE` `MS` ON (
            (
                `PS`.`SAMPLE_TYPE_ID` = `MS`.`ID`
            )
        )

    )
where `O`.DEL_FLAG ='0'

UNION ALL
    SELECT
        `O`.`CODE` AS `ORDER_NO`,
		`O`.`ID` AS `ORDER_ID`,
        `O`.`ORDER_TYPE` AS `ORDER_TYPE`,
        `O`.`TESTING_STATUS` AS `ORDER_STATUS`,
        `O`.`OWNER_ID` AS `OWNER_ID`,
        `SS`.`EXAMINEE_ID` AS `EXAMINEE_ID`,
        NULL AS `EXAMINEE_NAME`,
        `D`.`DICT_TEXT` AS `FAMILY_RELATION`,
        `SS`.`ID` AS `SAMPLE_ID`,
        `SS`.`SAMPLE_CODE` AS `SAMPLE_CODE`,
        `MS`.`NAME` AS `SAMPLE_TYPE`,
        NULL AS `SAMPLE_NAME`,
        `SS`.`SAMPLE_PACKAGE_NO` AS `SAMPLE_PACKAGE_NO`,
        `SS`.`SAMPLE_PACKAGE_STATUS` AS `SAMPLE_PACKAGE_STATUS`,
        `SS`.`SAMPLE_ERROR_STATUS` AS `SAMPLE_ERROR_STATUS`,
        `SS`.`SAMPLE_ERROR_NEW_NO` AS `SAMPLE_ERROR_NEW_NO`,
        2 AS `SAMPLE_TABLE_TYPE`,
        `SS`.`SAMPLE_BACK_STATUS` AS `SAMPLE_BACK_STATUS`,
        `SS`.`SAMPLE_BACK_OPTION` AS `SAMPLE_BACK_OPTION`,
        `SS`.`SAMPLE_BACK_NO` AS `SAMPLE_BACK_NO`,
		`SS`.`ACCEPT_SAMPLE_TIME` AS `ACCEPT_SAMPLE_TIME`,
		`SS`.`ERROR_REASON` AS `ERROR_REASON`,
		`SS`.`ERROR_DEAL_REMARK` AS `ERROR_DEAL_REMARK`
    FROM
        (
            (
                (
                    `LIMS_ORDER_SUBSIDIARY_SAMPLE` `SS`
                    LEFT JOIN `LIMS_ORDER` `O` ON ((`SS`.`ORDER_ID` = `O`.`ID`)) 
                )
                LEFT JOIN `LIMS_METADATA_SAMPLE` `MS` ON (
                    (
                        `SS`.`SAMPLE_TYPE_ID` = `MS`.`ID`
                    )
                )
            )
            LEFT JOIN `LIMS_DICT` `D` ON (
                (
                    (
                        `D`.`CATEGORY` = 'FAMILY_RELATION'
                    )
                    AND (
                        `D`.`DICT_VALUE` = `SS`.`FAMILY_RELATION`
                    )
                )
            )
        )
where `O`.DEL_FLAG ='0'

    UNION ALL
        SELECT
            `O`.`CODE` AS `ORDER_NO`,
			`O`.`ID` AS `ORDER_ID`,
            `O`.`ORDER_TYPE` AS `ORDER_TYPE`,
            `O`.`TESTING_STATUS` AS `ORDER_STATUS`,
            `O`.`OWNER_ID` AS `OWNER_ID`,
            NULL AS `EXAMINEE_ID`,
            NULL AS `EXAMINEE_NAME`,
            NULL AS `FAMILY_RELATION`,
            `RS`.`ID` AS `SAMPLE_ID`,
            `RS`.`SAMPLE_CODE` AS `SAMPLE_CODE`,
            `MS`.`NAME` AS `SAMPLE_TYPE`,
            `RS`.`SAMPLE_NAME` AS `SAMPLE_NAME`,
            `RS`.`SAMPLE_PACKAGE_NO` AS `SAMPLE_PACKAGE_NO`,
            `RS`.`SAMPLE_PACKAGE_STATUS` AS `SAMPLE_PACKAGE_STATUS`,
            `RS`.`SAMPLE_ERROR_STATUS` AS `SAMPLE_ERROR_STATUS`,
            `RS`.`SAMPLE_ERROR_NEW_NO` AS `SAMPLE_ERROR_NEW_NO`,
            3 AS `SAMPLE_TABLE_TYPE`,
            `RS`.`SAMPLE_BACK_STATUS` AS `SAMPLE_BACK_STATUS`,
            `RS`.`SAMPLE_BACK_OPTION` AS `SAMPLE_BACK_OPTION`,
            `RS`.`SAMPLE_BACK_NO` AS `SAMPLE_BACK_NO`,
			`RS`.`ACCEPT_SAMPLE_TIME` AS `ACCEPT_SAMPLE_TIME`,
			`RS`.`ERROR_REASON` AS `ERROR_REASON`,
			`RS`.`ERROR_DEAL_REMARK` AS `ERROR_DEAL_REMARK`
        FROM
            (
                (
                    `LIMS_ORDER_RESEARCH_SAMPLE` `RS`
                    LEFT JOIN `LIMS_ORDER` `O` ON ((`RS`.`ORDER_ID` = `O`.`ID`)) 
                )
                LEFT JOIN `LIMS_METADATA_SAMPLE` `MS` ON (
                    (
                        `RS`.`SAMPLE_TYPE_ID` = `MS`.`ID`
                    )
                )
            )  where `O`.DEL_FLAG ='0';

            
            
update  LIMS_ORDER SET TESTING_STATUS = '0' WHERE STATUS = '0';
update  LIMS_ORDER SET SHEDULE_STATUS = '0' WHERE SHEDULE_STATUS is null;
update LIMS_CONTRACT set UNRECONCILED_AMOUNT = 0 where UNRECONCILED_AMOUNT is null;


--报告处理      孔德成       2017年7月2日
DROP TABLE IF EXISTS `LIMS_TESTING_REPORT_SAMPLE`;
CREATE TABLE `LIMS_TESTING_REPORT_SAMPLE` (
  `ID` varchar(64) NOT NULL,
  `REPORT_ID` varchar(64) DEFAULT NULL,
  `SCHEDULE_ID` varchar(64) DEFAULT NULL,
  `METHOD_ID` varchar(64) DEFAULT NULL,
  `SAMPLE_ID` varchar(64) DEFAULT NULL,
  `PURPOSE` varchar(1) DEFAULT NULL COMMENT '1验证2检测',
  `QUALIFIED` tinyint(1) DEFAULT NULL COMMENT '是否合格',
  `UNQUALIFIED_REMARK` varchar(256) DEFAULT NULL,
  `UNQUALIFIED_STRATEGY` varchar(64) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `COMBINE_TYPE` varchar(64) DEFAULT NULL COMMENT '纯合/杂合',
  `MUTATION_SOURCE` varchar(64) DEFAULT NULL COMMENT '突变来源',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `LIMS_TESTING_REPORT`
ADD COLUMN `INSTRUCTION`  text NULL COMMENT '结果说明' AFTER `RESUBMIT_COUNT`,
ADD COLUMN `REPORT_TYPE`  varchar(2) NULL COMMENT '报告分类' AFTER `INSTRUCTION`,
ADD COLUMN `ANALY_RESULT`  varchar(2) NULL COMMENT '分析结果' AFTER `REPORT_TYPE`,
ADD COLUMN `PICTURES`  text NULL COMMENT '关联图片' AFTER `ANALY_RESULT`;



----2017年7月3日09:50:06  by 宋健  
update LIMS_ORDER set RECEIVED_SAMPLE_STATUS = 0 where RECEIVED_SAMPLE_STATUS is null;

---- 2017.7.6 物资项处理脚本   王巍
INSERT INTO `LIMS_MATERIAL` VALUES ('6129b9cc95234a39b10e32f88df744ae', '1', '常用物资', null, '', '2017-07-05 11:10:30', '0', '0', null);
INSERT INTO `LIMS_MATERIAL` VALUES ('19e8ad19def545db9cf7512019348a1b', '1', '耗材', null, '', '2017-07-05 11:10:31', '0', '0', null);
INSERT INTO `LIMS_MATERIAL` VALUES ('6129b9cc95234a39b10e32f88df744ad', '1', '宣传资料', null, '', '2017-07-05 11:10:32', '0', '0', null);

INSERT INTO `LIMS_MATERIAL` VALUES ('818f953d511341efa5223597cc624a09', '2', '手机', '6129b9cc95234a39b10e32f88df744ae', '', '2017-07-05 11:11:01', '0', '0', null);
INSERT INTO `LIMS_MATERIAL` VALUES ('f3dd18377b9a4a12a733d908d15e6854', '2', 'POS机', '6129b9cc95234a39b10e32f88df744ae', '', '2017-07-05 11:11:07', '0', '0', null);
INSERT INTO `LIMS_MATERIAL` VALUES ('63d9c05e79f742d39d3785e2c473514a', '2', 'POS纸', '6129b9cc95234a39b10e32f88df744ae', '', '2017-07-05 11:11:15', '0', '0', null);
INSERT INTO `LIMS_MATERIAL` VALUES ('4a8aa9f4b5fe42cc9ff8fef296d1081b', '2', '收据', '6129b9cc95234a39b10e32f88df744ae', '', '2017-07-05 11:11:30', '0', '0', null);
INSERT INTO `LIMS_MATERIAL` VALUES ('c52038a8ff0240f7aebba28d78be6eb1', '2', '基因分析申请单', '6129b9cc95234a39b10e32f88df744ae', '', '2017-07-05 11:11:40', '0', '0', null);
INSERT INTO `LIMS_MATERIAL` VALUES ('157b3d43540849b1b92b46ec7b027300', '2', 'LOGO塑料自封袋', '6129b9cc95234a39b10e32f88df744ae', '', '2017-07-05 11:11:45', '0', '0', null);
INSERT INTO `LIMS_MATERIAL` VALUES ('939ed1cebfea4910adf691a89f19a5f0', '2', '报告纸袋', '6129b9cc95234a39b10e32f88df744ae', '', '2017-07-05 11:11:50', '0', '0', null);
INSERT INTO `LIMS_MATERIAL` VALUES ('e10fcffd1725469683db49dc4e72a440', '2', '条码标签', '6129b9cc95234a39b10e32f88df744ae', '', '2017-07-05 11:11:55', '0', '0', null);

INSERT INTO `LIMS_MATERIAL` VALUES ('4c9e31e4878c47a796ab8e3b7dfa4636', '2', '唾液采集器', '19e8ad19def545db9cf7512019348a1b', '', '2017-07-05 11:12:15', '0', '0', null);
INSERT INTO `LIMS_MATERIAL` VALUES ('ba89fc8cb1014711b8dc1a00c0b4e013', '2', '组织保存管', '19e8ad19def545db9cf7512019348a1b', '', '2017-07-05 11:13:15', '0', '0', null);

INSERT INTO `LIMS_MATERIAL` VALUES ('5ea384c4fb0d4eba85497188d597f2a3', '2', '迈基诺医学目录', '6129b9cc95234a39b10e32f88df744ad', '', '2017-07-05 11:14:05', '0', '0', null);
INSERT INTO `LIMS_MATERIAL` VALUES ('8084355275384a81baf39b686a4be8bd', '2', '技术服务手册', '6129b9cc95234a39b10e32f88df744ad', '', '2017-07-05 11:15:15', '0', '0', null);
INSERT INTO `LIMS_MATERIAL` VALUES ('859b58d3b846409490a6c2f7deaae844', '2', '遗传性耳聋相关基因检测', '6129b9cc95234a39b10e32f88df744ad', '', '2017-07-05 11:15:30', '0', '0', null);
INSERT INTO `LIMS_MATERIAL` VALUES ('36c696819afd4952be6fd595bf2979d8', '2', 'CNW', '6129b9cc95234a39b10e32f88df744ad', '', '2017-07-05 11:15:45', '0', '0', null);
INSERT INTO `LIMS_MATERIAL` VALUES ('0a4411f0a78a40b98da5a5c8523e1b6d', '2', '眼科卡片', '6129b9cc95234a39b10e32f88df744ad', '', '2017-07-05 11:16:45', '0', '0', null);
INSERT INTO `LIMS_MATERIAL` VALUES ('339d57fa58774095aa0a3ecac5bd6c12', '2', '新生儿筛查', '6129b9cc95234a39b10e32f88df744ad', '', '2017-07-05 11:17:45', '0', '0', null);
INSERT INTO `LIMS_MATERIAL` VALUES ('56d14be1de3b4652a2641dd01ded0065', '2', '遗传性肿瘤', '6129b9cc95234a39b10e32f88df744ad', '', '2017-07-05 11:18:25', '0', '0', null);
INSERT INTO `LIMS_MATERIAL` VALUES ('42e67dc9f6f44b2588d3eae9bbb1ecd3', '2', '学术论文集', '6129b9cc95234a39b10e32f88df744ad', '', '2017-07-05 11:19:45', '0', '0', null);

-- 修改老数据
UPDATE LIMS_MATERIALS_APPLY_DETAIL SET MATERIALS_TYPE = '6129b9cc95234a39b10e32f88df744ae' WHERE MATERIALS_TYPE = '1';
UPDATE LIMS_MATERIALS_APPLY_DETAIL SET MATERIALS_TYPE = '19e8ad19def545db9cf7512019348a1b' WHERE MATERIALS_TYPE = '2';
UPDATE LIMS_MATERIALS_APPLY_DETAIL SET MATERIALS_TYPE = '6129b9cc95234a39b10e32f88df744ad' WHERE MATERIALS_TYPE = '3';

UPDATE LIMS_MATERIALS_APPLY_DETAIL SET MATERIALS_NAME = '818f953d511341efa5223597cc624a09' WHERE MATERIALS_NAME = '4';
UPDATE LIMS_MATERIALS_APPLY_DETAIL SET MATERIALS_NAME = 'f3dd18377b9a4a12a733d908d15e6854' WHERE MATERIALS_NAME = '5';
UPDATE LIMS_MATERIALS_APPLY_DETAIL SET MATERIALS_NAME = '63d9c05e79f742d39d3785e2c473514a' WHERE MATERIALS_NAME = '6';
UPDATE LIMS_MATERIALS_APPLY_DETAIL SET MATERIALS_NAME = '4a8aa9f4b5fe42cc9ff8fef296d1081b' WHERE MATERIALS_NAME = '7';
UPDATE LIMS_MATERIALS_APPLY_DETAIL SET MATERIALS_NAME = 'c52038a8ff0240f7aebba28d78be6eb1' WHERE MATERIALS_NAME = '8';
UPDATE LIMS_MATERIALS_APPLY_DETAIL SET MATERIALS_NAME = '157b3d43540849b1b92b46ec7b027300' WHERE MATERIALS_NAME = '9';
UPDATE LIMS_MATERIALS_APPLY_DETAIL SET MATERIALS_NAME = '939ed1cebfea4910adf691a89f19a5f0' WHERE MATERIALS_NAME = '10';
UPDATE LIMS_MATERIALS_APPLY_DETAIL SET MATERIALS_NAME = 'e10fcffd1725469683db49dc4e72a440' WHERE MATERIALS_NAME = '11';

UPDATE LIMS_MATERIALS_APPLY_DETAIL SET MATERIALS_NAME = '4c9e31e4878c47a796ab8e3b7dfa4636' WHERE MATERIALS_NAME = '12';
UPDATE LIMS_MATERIALS_APPLY_DETAIL SET MATERIALS_NAME = 'ba89fc8cb1014711b8dc1a00c0b4e013' WHERE MATERIALS_NAME = '13';

UPDATE LIMS_MATERIALS_APPLY_DETAIL SET MATERIALS_NAME = '5ea384c4fb0d4eba85497188d597f2a3' WHERE MATERIALS_NAME = '14';
UPDATE LIMS_MATERIALS_APPLY_DETAIL SET MATERIALS_NAME = '8084355275384a81baf39b686a4be8bd' WHERE MATERIALS_NAME = '15';
UPDATE LIMS_MATERIALS_APPLY_DETAIL SET MATERIALS_NAME = '859b58d3b846409490a6c2f7deaae844' WHERE MATERIALS_NAME = '16';
UPDATE LIMS_MATERIALS_APPLY_DETAIL SET MATERIALS_NAME = '36c696819afd4952be6fd595bf2979d8' WHERE MATERIALS_NAME = '17';
UPDATE LIMS_MATERIALS_APPLY_DETAIL SET MATERIALS_NAME = '0a4411f0a78a40b98da5a5c8523e1b6d' WHERE MATERIALS_NAME = '18';
UPDATE LIMS_MATERIALS_APPLY_DETAIL SET MATERIALS_NAME = '339d57fa58774095aa0a3ecac5bd6c12' WHERE MATERIALS_NAME = '19';
UPDATE LIMS_MATERIALS_APPLY_DETAIL SET MATERIALS_NAME = '56d14be1de3b4652a2641dd01ded0065' WHERE MATERIALS_NAME = '20';
UPDATE LIMS_MATERIALS_APPLY_DETAIL SET MATERIALS_NAME = '42e67dc9f6f44b2588d3eae9bbb1ecd3' WHERE MATERIALS_NAME = '21';


-- 2017.7.9 孔德成
ALTER TABLE `LIMS_TESTING_REPORT_SAMPLE`
ADD COLUMN `COMBINE_CODE`  varchar(64) NULL COMMENT '合并编号' AFTER `MUTATION_SOURCE`;


----发票修改应付款 记录表 宋健
DROP TABLE IF EXISTS `LIMS_INVOICE_ORDERUPDATE_RECORD`;
CREATE TABLE `LIMS_INVOICE_ORDERUPDATE_RECORD` (
  `ID` varchar(64) NOT NULL COMMENT '主键',
  `ORDER_ID` varchar(64) NOT NULL COMMENT '订单ID',
  `INVOICE_TASK_ID` varchar(64) DEFAULT NULL COMMENT '开票任务ID',
  `UPDATE_ID` varchar(64) NOT NULL COMMENT '更新人ID',
  `UPDATE_NAME` varchar(64) NOT NULL COMMENT '更新人姓名',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新日期',
  `ORDER_PRODUCT_ID` varchar(64) DEFAULT NULL COMMENT '订单产品ID',
  `ORDER_PRODUCT_AMOUNT` int(16) DEFAULT NULL COMMENT '订单产品金额',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务库-发票任务-修改订单费用记录表';


---增加退款信息 宋健
alter table LIMS_ORDER_REFUND add COLUMN BANK_TYPE VARCHAR(128) DEFAULT null COMMENT '银行名称';
alter table LIMS_ORDER_REFUND add COLUMN BANK_NO VARCHAR(128) DEFAULT null COMMENT '银行卡号';
alter table LIMS_ORDER_REFUND add COLUMN BANK_OWNER_NAME VARCHAR(128) DEFAULT null COMMENT '姓名';


--- 付款待确认表增加 pos参考号、收据单号 、转账卡号 宋健
alter table LIMS_ORDER_PAYMENT_CONFIRM  add COLUMN TRANSFER_NO VARCHAR(64) DEFAULT NULL COMMENT '转账确认卡号';
alter table LIMS_ORDER_PAYMENT_CONFIRM  add COLUMN POS_NO VARCHAR(128) DEFAULT NULL COMMENT 'POS机确认参考号';
alter table LIMS_ORDER_PAYMENT_CONFIRM  add COLUMN RECEIPT_ROLLS VARCHAR(128) DEFAULT NULL COMMENT 'POS机确认收据号';
alter table LIMS_ORDER_PAYMENT_CONFIRM  add COLUMN TRADE_NO VARCHAR(128) DEFAULT NULL COMMENT '支付宝支付流水号';
alter table LIMS_ORDER_PAYMENT_CONFIRM  add COLUMN TRADE_PARAMS varchar(4000) DEFAULT NULL COMMENT '支付宝响应参数';

---增加银行 数据字典   宋健
INSERT INTO `LIMS_DICT`(`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) 
VALUES ('d013a56cvc3443bbbef8he77b7a1b1c1', NULL, 'BANK_TYPE', '银行类型', '', '', '', '1');

INSERT INTO `LIMS_DICT`(`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) 
VALUES ('d013a56cvc3443bbbef8he77b7a1b1c2', 'd013a56cvc3443bbbef8he77b7a1b1c1', 'BANK_TYPE', '中国工商银行', '1', '1', '', '1');

INSERT INTO `LIMS_DICT`(`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) 
VALUES ('d013a56cvc3443bbbef8he77b7a1b1c3', 'd013a56cvc3443bbbef8he77b7a1b1c1', 'BANK_TYPE', '中国农业银行', '2', '2', '', '1');

INSERT INTO `LIMS_DICT`(`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) 
VALUES ('d013a56cvc3443bbbef8he77b7a1b1c4', 'd013a56cvc3443bbbef8he77b7a1b1c1', 'BANK_TYPE', '中国银行', '3', '3', '', '1');

INSERT INTO `LIMS_DICT`(`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) 
VALUES ('d013a56cvc3443bbbef8he77b7a1b1c5', 'd013a56cvc3443bbbef8he77b7a1b1c1', 'BANK_TYPE', '中国建设银行', '4', '4', '', '1');

INSERT INTO `LIMS_DICT`(`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) 
VALUES ('d013a56cvc3443bbbef8he77b7a1b1c6', 'd013a56cvc3443bbbef8he77b7a1b1c1', 'BANK_TYPE', '交通银行', '5', '5', '', '1');

INSERT INTO `LIMS_DICT`(`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) 
VALUES ('d013a56cvc3443bbbef8he77b7a1b1c7', 'd013a56cvc3443bbbef8he77b7a1b1c1', 'BANK_TYPE', '中国邮政储蓄银行', '6', '6', '', '1');

INSERT INTO `LIMS_DICT`(`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) 
VALUES ('d013a56cvc3443bbbef8he77b7a1b1c8', 'd013a56cvc3443bbbef8he77b7a1b1c1', 'BANK_TYPE', '招商银行', '7', '7', '', '1');

INSERT INTO `LIMS_DICT`(`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) 
VALUES ('d013a56cvc3443bbbef8he77b7a1b1c9', 'd013a56cvc3443bbbef8he77b7a1b1c1', 'BANK_TYPE', '兴业银行', '8', '8', '', '1');

INSERT INTO `LIMS_DICT`(`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) 
VALUES ('d013a56cvc3443bbbef8he77b7a1b1c10', 'd013a56cvc3443bbbef8he77b7a1b1c1', 'BANK_TYPE', '平安银行', '9', '9', '', '1');

INSERT INTO `LIMS_DICT`(`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) 
VALUES ('d013a56cvc3443bbbef8he77b7a1b1c11', 'd013a56cvc3443bbbef8he77b7a1b1c1', 'BANK_TYPE', '广发银行', '10', '10', '', '1');


-----退款记录表增加 退款卡号信息
alter table LIMS_ORDER_REFUND_RECORD add COLUMN BANK_TYPE VARCHAR(128) DEFAULT null COMMENT '银行名称';
alter table LIMS_ORDER_REFUND_RECORD add COLUMN BANK_NO VARCHAR(128) DEFAULT null COMMENT '银行卡号';
alter table LIMS_ORDER_REFUND_RECORD add COLUMN BANK_OWNER_NAME VARCHAR(128) DEFAULT null COMMENT '姓名';

----  2017.7.31  ww
DROP TABLE IF EXISTS `LIMS_TESTING_REPORT_SEND_CALLBACK`;
CREATE TABLE `LIMS_TESTING_REPORT_SEND_CALLBACK` (
  `ID` varchar(64) NOT NULL,
  `REPORT_ID` varchar(64) NOT NULL COMMENT '报告id',
  `STATUS` tinyint(1) NOT NULL COMMENT '状态 1处理中 2成功  3失败',
  `CREATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `LIMS_TESTING_REPORT_SEND_DETAILS`;
CREATE TABLE `LIMS_TESTING_REPORT_SEND_DETAILS` (
  `ID` varchar(64) NOT NULL,
  `CALLBACK_ID` varchar(64) NOT NULL COMMENT '发送回调Id',
  `SEMANTIC` varchar(64) NOT NULL COMMENT '语义（检测、图片）',
  `RECORD_ID` varchar(64) NOT NULL COMMENT '技术分析或数据分析或图片Id',
  `CLINICAL_JUDGMENT` varchar(64) DEFAULT NULL COMMENT '临床相关性判断',
  `MUTATION_SOURCE` varchar(64) DEFAULT NULL COMMENT '突变来源',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `LIMS_TESTING_REPORT_TEMP_SAVE`;
CREATE TABLE `LIMS_TESTING_REPORT_TEMP_SAVE` (
  `ID` varchar(64) NOT NULL,
  `REPORT_ID` varchar(64) NOT NULL COMMENT '报告Id',
  `SEMANTIC` varchar(64) NOT NULL COMMENT '语义（检测、图片）',
  `RECORD_ID` varchar(64) NOT NULL COMMENT '技术分析或数据分析或图片Id',
  `CLINICAL_JUDGMENT` varchar(64) DEFAULT NULL COMMENT '临床相关性判断',
  `MUTATION_SOURCE` varchar(64) DEFAULT NULL COMMENT '突变来源',
  `CREATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


alter table LIMS_TESTING_REPORT add COLUMN SEND_STATUS tinyint(1) COMMENT '发送状态:1处理中 2成功 3失败';
alter table LIMS_TESTING_REPORT add COLUMN OPERATE_REMARK varchar(255) COMMENT '备注';

INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('1408', '报告发送列表', '/bpm/report/reportSendList.do', '14', 'fa-tasks', '7', '0');

INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('1408', '报告发送列表', '/bpm/report/reportSendList.do');

INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('0108', '01', '报告发送', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('010801', '0108', '报告发送列表', NULL);

INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('010801', '1408');


INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '010801');

--- 2017.8.16 ww
alter table LIMS_TESTING_REPORT add COLUMN REPORT_TEMPLATE_TYPE varchar(128) COMMENT '报告模板';
alter table LIMS_TESTING_REPORT_SEND_CALLBACK add COLUMN REPORT_TEMPLATE_TYPE varchar(128) COMMENT '报告模板';
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '010801');

--- 2017.8.16 ww
alter table LIMS_TESTING_REPORT add COLUMN REPORT_TEMPLATE_TYPE varchar(128) COMMENT '报告模板';
alter table LIMS_TESTING_REPORT_SEND_CALLBACK add COLUMN REPORT_TEMPLATE_TYPE varchar(128) COMMENT '报告模板';

insert into LIMS_CONFIG(ID,CONFIG_KEY,CONFIG_VALUE,MAINTAIN_TYPE,CONFIG_NAME,VALUE_TYPE)
values('63c9b5e2fbba45c68dae2f3de8d115e7','URL_ADDRESS','http://10.27.222.200:8001',0,'发送/回调请求地址',2);

---2017年8月18日14:59:57  by songjian
ALTER  TABLE LIMS_PRODUCT add COLUMN SAMPLE_PURPOSE VARCHAR(64) DEFAULT '1,2,3,4' COMMENT '关联样本用途';


ALTER  TABLE LIMS_ORDER add COLUMN CANCEL_REMARK VARCHAR(256) DEFAULT '' COMMENT '订单取消备注';
ALTER  TABLE LIMS_ORDER add COLUMN CANCEL_ID VARCHAR(64) DEFAULT '' COMMENT '订单取消人Id';
ALTER  TABLE LIMS_ORDER add COLUMN CANCEL_NAME VARCHAR(64) DEFAULT '' COMMENT '订单取消人姓名';
ALTER  TABLE LIMS_ORDER add COLUMN CANCEL_TIME datetime DEFAULT NULL COMMENT '订单取消时间';

INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '010801');

--- 2017.8.16 ww
alter table LIMS_TESTING_REPORT add COLUMN REPORT_TEMPLATE_TYPE varchar(128) COMMENT '报告模板';
alter table LIMS_TESTING_REPORT_SEND_CALLBACK add COLUMN REPORT_TEMPLATE_TYPE varchar(128) COMMENT '报告模板';

insert into LIMS_CONFIG(ID,CONFIG_KEY,CONFIG_VALUE,MAINTAIN_TYPE,CONFIG_NAME,VALUE_TYPE)
values('63c9b5e2fbba45c68dae2f3de8d115e7','URL_ADDRESS','http://10.27.222.200:8001',0,'发送/回调请求地址',2);





DROP TABLE IF EXISTS `LIMS_PRODUCT_AMOUNT_RULE`;
CREATE TABLE `LIMS_PRODUCT_AMOUNT_RULE` (
	`ID` VARCHAR (64) NOT NULL COMMENT '主键',
	`PRODUCT_ID` VARCHAR (64) NOT NULL COMMENT '产品ID',
	`SAMPLE_COUNT` INT (11) NOT NULL COMMENT '样本数量',
	`EXTRA_AMOUNT` INT (11) NOT NULL COMMENT '额外价格',
	PRIMARY KEY (`ID`)
) ENGINE = INNODB DEFAULT CHARSET = UTF8 COMMENT = '业务库-产品-关联检测样本价格表';


--重新送样不送样记录
DROP TABLE IF EXISTS `LIMS_TESTING_RESAMPLE_NOSAMPLE_RECORD`;
CREATE TABLE `LIMS_TESTING_RESAMPLE_NOSAMPLE_RECORD` (
  `ID` varchar(64) NOT NULL,
  `SAMPLE_ID` varchar(64) NOT NULL,
  `ERROR_DEAL_REMARK` varchar(400) DEFAULT NULL COMMENT '备注',
  `SAMPLE_ERROR_STATUS` tinyint(1) DEFAULT NULL COMMENT '异常样本处理状态 1：新增样本处理；2：无送样处理',
  `ERROR_OPERATOR_ID` varchar(64) DEFAULT NULL COMMENT '异常处理人ID',
  `ERROR_OPERATOR_NAME` varchar(64) DEFAULT NULL COMMENT '异常处理人姓名',
  `ERROR_OPERATOR_TIME` datetime DEFAULT NULL COMMENT '异常处理人时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--项目监控   周期配置权限 
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('2-18-0-4', '2-18-0', '周期配置列表查看', NULL);

INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('2-18-0-4', '021801');

delete from LIMS_AUTHORITY_RESOURCE where AUTHORITY_ID = '2-18-0';

-- 任务管理
INSERT INTO `LIMS_METADATA_SAMPLE` VALUES ('40a69b8c48054935a10a64ec5fc955dd', '多重PCR产物', 'fa80fa8348f74720a4d43dd1045a7b24', '2', 'B2', 'ae812c1bf0da43f39a64dbfc6307e886', null, null, null, null, '30', '0');
INSERT INTO `LIMS_METADATA_SAMPLE` VALUES ('a1c748bc1dc74715a340872d460960fb', 'LONGPCR产物', 'fa80fa8348f74720a4d43dd1045a7b24', '2', 'B2', 'ae812c1bf0da43f39a64dbfc6307e886', null, null, null, null, '31', '0');

-- 权限配置  2017.9.4 ww
UPDATE LIMS_AUTHORITY SET name='列表' WHERE ID = '11-6-0';
UPDATE LIMS_AUTHORITY SET name='查看/会议响应' WHERE ID = '11-6-1';

INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('02180202', '查看', '/bmm/schedule/order.do');
update LIMS_AUTHORITY set name='设置/取消加急' where ID = '2-18-2-1';
delete from LIMS_AUTHORITY where id = '2-18-2-2';
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('2-18-2-0', '02180202');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('2-18-2-1', '02180201');

INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('021302', '查看', '/order/viewOrder.do');
update LIMS_AUTHORITY_RESOURCE set RESOURCE_ID = '021302' where AUTHORITY_ID = '2-13-1';

update LIMS_RESOURCE set name = '导出数据' where id = '13102';
update LIMS_RESOURCE set name = '导入数据' where id = '13103';
update LIMS_AUTHORITY set name='导出数据' where id = '12-10-03';
update LIMS_AUTHORITY set name='导入数据' where id = '12-10-04';

update LIMS_AUTHORITY set name='出库操作' where id='15-04-02';
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('15-04-03', '15-04', '入库操作', NULL);
update LIMS_RESOURCE set name='出库操作' where id = '150401';
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('150402', '入库操作', '/testingSheetSampleStorage/sampleIn_operate.do');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('15-04-03', '150402');

--合同订单管理（合同详情删除)  合同管理  2017.9.6
UPDATE LIMS_AUTHORITY SET name='详情（合同订单详情）' WHERE id='264';
delete from LIMS_AUTHORITY  where id ='291';
delete from LIMS_AUTHORITY_RESOURCE  where authority_id ='291';

---longpcr产物、多重pcr产物配置
UPDATE LIMS_TASK_CONFIG SET OUTPUT_SAMPLE = 'a1c748bc1dc74715a340872d460960fb' WHERE ID = 'pasdjue8dyhuoazii09g2hjkasdroq23';
UPDATE LIMS_TASK_CONFIG SET OUTPUT_SAMPLE = 'a1c748bc1dc74715a340872d460960fb' WHERE ID = '-09hiujdhsg76329834tguhoidrj093';
UPDATE LIMS_TASK_INPUT_CONFIG SET INPUT_SAMPLE = 'a1c748bc1dc74715a340872d460960fb' WHERE TASK_ID = '-09hiujdhsg76329834tguhoidrj093';

UPDATE LIMS_TASK_CONFIG SET OUTPUT_SAMPLE = '40a69b8c48054935a10a64ec5fc955dd' WHERE ID = 'mcb8943eohdsbk34ygwhjdsu234reds';
UPDATE LIMS_TASK_CONFIG SET OUTPUT_SAMPLE = '40a69b8c48054935a10a64ec5fc955dd' WHERE ID = 'oais0293whidhsrf90w2ghsisae89';
UPDATE LIMS_TASK_INPUT_CONFIG SET INPUT_SAMPLE = '40a69b8c48054935a10a64ec5fc955dd' WHERE TASK_ID = 'oais0293whidhsrf90w2ghsisae89';

-- 
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('2-13-3', '2-13-', '异常订单列表', 3);
update LIMS_AUTHORITY_RESOURCE set AUTHORITY_ID = '2-13-3' where RESOURCE_ID = '0213';

update LIMS_AUTHORITY set name='相对定量' where id = '38';

-- 物资物流
UPDATE LIMS_MATERIALS_APPLY_TRANSPORT_RELATION SET MATERIALS_NAME = '818f953d511341efa5223597cc624a09' WHERE MATERIALS_NAME = '4';
UPDATE LIMS_MATERIALS_APPLY_TRANSPORT_RELATION SET MATERIALS_NAME = 'f3dd18377b9a4a12a733d908d15e6854' WHERE MATERIALS_NAME = '5';
UPDATE LIMS_MATERIALS_APPLY_TRANSPORT_RELATION SET MATERIALS_NAME = '63d9c05e79f742d39d3785e2c473514a' WHERE MATERIALS_NAME = '6';
UPDATE LIMS_MATERIALS_APPLY_TRANSPORT_RELATION SET MATERIALS_NAME = '4a8aa9f4b5fe42cc9ff8fef296d1081b' WHERE MATERIALS_NAME = '7';
UPDATE LIMS_MATERIALS_APPLY_TRANSPORT_RELATION SET MATERIALS_NAME = 'c52038a8ff0240f7aebba28d78be6eb1' WHERE MATERIALS_NAME = '8';
UPDATE LIMS_MATERIALS_APPLY_TRANSPORT_RELATION SET MATERIALS_NAME = '157b3d43540849b1b92b46ec7b027300' WHERE MATERIALS_NAME = '9';
UPDATE LIMS_MATERIALS_APPLY_TRANSPORT_RELATION SET MATERIALS_NAME = '939ed1cebfea4910adf691a89f19a5f0' WHERE MATERIALS_NAME = '10';
UPDATE LIMS_MATERIALS_APPLY_TRANSPORT_RELATION SET MATERIALS_NAME = 'e10fcffd1725469683db49dc4e72a440' WHERE MATERIALS_NAME = '11';

UPDATE LIMS_MATERIALS_APPLY_TRANSPORT_RELATION SET MATERIALS_NAME = '4c9e31e4878c47a796ab8e3b7dfa4636' WHERE MATERIALS_NAME = '12';
UPDATE LIMS_MATERIALS_APPLY_TRANSPORT_RELATION SET MATERIALS_NAME = 'ba89fc8cb1014711b8dc1a00c0b4e013' WHERE MATERIALS_NAME = '13';

UPDATE LIMS_MATERIALS_APPLY_TRANSPORT_RELATION SET MATERIALS_NAME = '5ea384c4fb0d4eba85497188d597f2a3' WHERE MATERIALS_NAME = '14';
UPDATE LIMS_MATERIALS_APPLY_TRANSPORT_RELATION SET MATERIALS_NAME = '8084355275384a81baf39b686a4be8bd' WHERE MATERIALS_NAME = '15';
UPDATE LIMS_MATERIALS_APPLY_TRANSPORT_RELATION SET MATERIALS_NAME = '859b58d3b846409490a6c2f7deaae844' WHERE MATERIALS_NAME = '16';
UPDATE LIMS_MATERIALS_APPLY_TRANSPORT_RELATION SET MATERIALS_NAME = '36c696819afd4952be6fd595bf2979d8' WHERE MATERIALS_NAME = '17';
UPDATE LIMS_MATERIALS_APPLY_TRANSPORT_RELATION SET MATERIALS_NAME = '0a4411f0a78a40b98da5a5c8523e1b6d' WHERE MATERIALS_NAME = '18';
UPDATE LIMS_MATERIALS_APPLY_TRANSPORT_RELATION SET MATERIALS_NAME = '339d57fa58774095aa0a3ecac5bd6c12' WHERE MATERIALS_NAME = '19';
UPDATE LIMS_MATERIALS_APPLY_TRANSPORT_RELATION SET MATERIALS_NAME = '56d14be1de3b4652a2641dd01ded0065' WHERE MATERIALS_NAME = '20';
UPDATE LIMS_MATERIALS_APPLY_TRANSPORT_RELATION SET MATERIALS_NAME = '42e67dc9f6f44b2588d3eae9bbb1ecd3' WHERE MATERIALS_NAME = '21';

-- 报告一审、二审、已出权限
UPDATE LIMS_RESOURCE SET URI = '/bpm/report/completedReport/batchDownload.do' WHERE ID = '140534';
UPDATE LIMS_RESOURCE SET URI = '/bpm/report/firstReview/batchDownload.do' WHERE ID = '140515';
UPDATE LIMS_RESOURCE SET URI = '/bpm/report/firstReview/wordToHtml.do' WHERE ID = '140514';

INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('140524', '导出', '/bpm/report/secondReview/download.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('140525', '批量导出', '/bpm/report/secondReview/batchDownload.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('140526', '查看数据', '/bpm/report/secondReview/handleView.do');

INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('140535', '导出', '/bpm/report/completedReport/download.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('140536', '查看数据', '/bpm/report/completedReport/handleView.do');

UPDATE LIMS_AUTHORITY_RESOURCE SET RESOURCE_ID = '140515' WHERE AUTHORITY_ID = '010515';

UPDATE LIMS_AUTHORITY_RESOURCE SET RESOURCE_ID = '140524' WHERE AUTHORITY_ID = '010523';
UPDATE LIMS_AUTHORITY_RESOURCE SET RESOURCE_ID = '140525' WHERE AUTHORITY_ID = '010525';
UPDATE LIMS_AUTHORITY_RESOURCE SET RESOURCE_ID = '140526' WHERE AUTHORITY_ID = '010526';

UPDATE LIMS_AUTHORITY_RESOURCE SET RESOURCE_ID = '140535' WHERE AUTHORITY_ID = '010533';
UPDATE LIMS_AUTHORITY_RESOURCE SET RESOURCE_ID = '140534' WHERE AUTHORITY_ID = '010534';
UPDATE LIMS_AUTHORITY_RESOURCE SET RESOURCE_ID = '140536' WHERE AUTHORITY_ID = '010535';

--角色中业 务配套物资管理  处理权限 2017.9.7
UPDATE LIMS_AUTHORITY_RESOURCE SET resource_id='120403' WHERE authority_id ='11-4-3';
update LIMS_RESOURCE set name='业务配套物资查看' where id = '120402';
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('120403', '业务配套物资处理', '/materialsApply/deal.do');

-- 报告整合 ww 2017.9.8
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('140902', '待分配查看数据', '/bpm/report/waitAssignHandleView.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('141001', '已分配查看数据', '/bpm/report/assignedHandleView.do');

INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('011013', '01101', '查看数据', 3);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('011022', '01102', '查看数据', 2);

INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('011013', '140902');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('011022', '141001');

UPDATE LIMS_RESOURCE SET URI = '/bpm/report/assign.do' WHERE ID = '140901';

UPDATE LIMS_AUTHORITY SET `NAME` = '报告审核' WHERE ID = '0105';
UPDATE LIMS_AUTHORITY SET `NAME` = '处理' WHERE ID = '01031';
UPDATE LIMS_AUTHORITY SET `NAME` = '处理' WHERE ID = '01041';

-- 2017年9月18日09:53:05   by 宋健  跟新视图

DROP VIEW IF EXISTS `LIMS_ORDER_SAMPLE_VIEW`;
CREATE VIEW `LIMS_ORDER_SAMPLE_VIEW` (
	ID,
	ORDER_ID,
	SAMPLE_ID,
	SAMPLE_BTYPE,
	SAMPLE_TYPE,
	SAMPLE_CODE,
	SAMPLE_NAME,
	SAMPLE_SIZE,
	SAMPLE_PACKAGE_STATUS,
	SAMPLING_DATE,
	PURPOSE,
	TRANSPORT_STATUS,
	UPDATE_TIME,
	ERROR_DEAL_REMARK,
	SAMPLE_ERROR_STATUS,
	ERROR_REASON,
	ACCEPT_SAMPLE_TIME,
	SAMPLE_ERROR_NEW_NO,
	FAMILY_RELATION
) AS SELECT
	concat_ws('-', '1', `PS`.`ID`) AS `ID`,
	`PS`.`ORDER_ID` AS `ORDER_ID`,
	`PS`.`ID` AS `SAMPLE_ID`,
	1 AS `SAMPLE_BTYPE`,
	`PS`.`SAMPLE_TYPE_ID` AS `SAMPLE_TYPE`,
	`PS`.`SAMPLE_CODE` AS `SAMPLE_CODE`,
	`OE`.`NAME` AS `SAMPLE_NAME`,
	`PS`.`SAMPLE_SIZE` AS `SAMPLE_SIZE`,
	`PS`.`SAMPLE_PACKAGE_STATUS` AS `SAMPLE_PACKAGE_STATUS`,
	`PS`.`SAMPLING_DATE` AS `SAMPLING_DATE`,
	NULL AS `PURPOSE`,
	`PS`.`SAMPLE_PACKAGE_STATUS` AS `TRANSPORT_STATUS`,
	`PS`.`UPDATE_TIME` AS `UPDATE_TIME`,
	`PS`.`ERROR_DEAL_REMARK` AS `ERROR_DEAL_REMARK`,
	`PS`.`SAMPLE_ERROR_STATUS` AS `SAMPLE_ERROR_STATUS`,
	`PS`.`ERROR_REASON` AS `ERROR_REASON`,
	`PS`.`ACCEPT_SAMPLE_TIME` AS `ACCEPT_SAMPLE_TIME`,
	`PS`.`SAMPLE_ERROR_NEW_NO` AS `SAMPLE_ERROR_NEW_NO`,
	 NULL AS `FAMILY_RELATION`
FROM
	(
		`LIMS_ORDER_PRIMARY_SAMPLE` `PS`
		LEFT JOIN `LIMS_ORDER_EXAMINEE` `OE` ON (
			(
				`PS`.`EXAMINEE_ID` = `OE`.`ID`
			)
		)
	)
UNION ALL
	SELECT
		concat_ws('-', '2', `SS`.`ID`) AS `ID`,
		`SS`.`ORDER_ID` AS `ORDER_ID`,
		`SS`.`ID` AS `ID`,
		2 AS `2`,
		`SS`.`SAMPLE_TYPE_ID` AS `SAMPLE_TYPE`,
		`SS`.`SAMPLE_CODE` AS `SAMPLE_CODE`,
		`SS`.`OWNER_NAME` AS `SAMPLE_NAME`,
		`SS`.`SAMPLE_SIZE` AS `SAMPLE_SIZE`,
		`SS`.`SAMPLE_PACKAGE_STATUS` AS `SAMPLE_PACKAGE_STATUS`,
		`SS`.`SAMPLING_DATE` AS `SAMPLING_DATE`,
		`SS`.`PURPOSE` AS `PURPOSE`,
		`SS`.`SAMPLE_PACKAGE_STATUS` AS `SAMPLE_PACKAGE_STATUS`,
		`SS`.`UPDATE_TIME` AS `UPDATE_TIME`,
		`SS`.`ERROR_DEAL_REMARK` AS `ERROR_DEAL_REMARK`,
		`SS`.`SAMPLE_ERROR_STATUS` AS `SAMPLE_ERROR_STATUS`,
		`SS`.`ERROR_REASON` AS `ERROR_REASON`,
		`SS`.`ACCEPT_SAMPLE_TIME` AS `ACCEPT_SAMPLE_TIME`,
		`SS`.`SAMPLE_ERROR_NEW_NO` AS `SAMPLE_ERROR_NEW_NO`,
	    `SS`.`FAMILY_RELATION` AS `FAMILY_RELATION`
		
	FROM
		`LIMS_ORDER_SUBSIDIARY_SAMPLE` `SS`
	UNION ALL
		SELECT
			concat_ws('-', '3', `RS`.`ID`) AS `ID`,
			`RS`.`ORDER_ID` AS `ORDER_ID`,
			`RS`.`ID` AS `ID`,
			3 AS `3`,
			`RS`.`SAMPLE_TYPE_ID` AS `SAMPLE_TYPE`,
			`RS`.`SAMPLE_CODE` AS `SAMPLE_CODE`,
			`RS`.`SAMPLE_NAME` AS `SAMPLE_NAME`,
			`RS`.`SAMPLE_SIZE` AS `SAMPLE_SIZE`,
			`RS`.`SAMPLE_PACKAGE_STATUS` AS `SAMPLE_PACKAGE_STATUS`,
			`RS`.`SAMPLING_DATE` AS `SAMPLING_DATE`,
			NULL AS `NULL`,
			`RS`.`SAMPLE_PACKAGE_STATUS` AS `SAMPLE_PACKAGE_STATUS`,
			`RS`.`UPDATE_TIME` AS `UPDATE_TIME`,
			`RS`.`ERROR_DEAL_REMARK` AS `ERROR_DEAL_REMARK`,
			`RS`.`SAMPLE_ERROR_STATUS` AS `SAMPLE_ERROR_STATUS`,
			`RS`.`ERROR_REASON` AS `ERROR_REASON`,
			`RS`.`ACCEPT_SAMPLE_TIME` AS `ACCEPT_SAMPLE_TIME`,
			`RS`.`SAMPLE_ERROR_NEW_NO` AS `SAMPLE_ERROR_NEW_NO`,
			 NULL AS `FAMILY_RELATION`
		FROM
			`LIMS_ORDER_RESEARCH_SAMPLE` `RS`
			
			
			
DROP VIEW IF EXISTS `LIMS_ORDER_SAMPLE_DETAILS`;
CREATE VIEW `LIMS_ORDER_SAMPLE_DETAILS` (
	ID,
	ORDER_ID,
	ORDER_CODE,
	ORDER_TYPE,
	CUSTOMER_ID,	
	CUSTOMER_NAME,
	SALESMAN_ID,
	SALESMAN_NAME,
	SAMPLE_ID,
	SAMPLE_BTYPE,
	SAMPLE_TYPE_ID,
	SAMPLE_TYPE_NAME,
	SAMPLE_CODE,
	SAMPLE_NAME,
	SAMPLE_SIZE,
	SAMPLING_DATE,
	PURPOSE,
	TRANSPORT_STATUS,
	UPDATE_TIME,
	SAMPLE_ERROR_STATUS,
	ERROR_REASON,
	ACCEPT_SAMPLE_TIME,
	SAMPLE_ERROR_NEW_NO,
	ERROR_DEAL_REMARK
) AS 			
			
SELECT
	`S`.`ID` AS `ID`,
	`S`.`ORDER_ID` AS `ORDER_ID`,
	`O`.`CODE` AS `ORDER_CODE`,
	`O`.`ORDER_TYPE` AS `ORDER_TYPE`,
	
	`U`.`ID` AS `CUSTOMER_ID`,	
	`U`.`REAL_NAME` AS `CUSTOMER_NAME`,
	`B`.`ID` AS `SALESMAN_ID`,
	`B`.`REAL_NAME` AS `SALESMAN_NAME`,
	`S`.`SAMPLE_ID` AS `SAMPLE_ID`,
	`S`.`SAMPLE_BTYPE` AS `SAMPLE_BTYPE`,
	`S`.`SAMPLE_TYPE` AS `SAMPLE_TYPE_ID`,
	`MS`.`NAME` AS `SAMPLE_TYPE_NAME`,
	`S`.`SAMPLE_CODE` AS `SAMPLE_CODE`,
	`S`.`SAMPLE_NAME` AS `SAMPLE_NAME`,
	`S`.`SAMPLE_SIZE` AS `SAMPLE_SIZE`,
	`S`.`SAMPLING_DATE` AS `SAMPLING_DATE`,
	`S`.`PURPOSE` AS `PURPOSE`,
	`S`.`TRANSPORT_STATUS` AS `TRANSPORT_STATUS`,
	`S`.`UPDATE_TIME` AS `UPDATE_TIME`,
	`S`.`SAMPLE_ERROR_STATUS` AS `SAMPLE_ERROR_STATUS`,
	`S`.`ERROR_REASON` AS `ERROR_REASON`,
	`S`.`ACCEPT_SAMPLE_TIME` AS `ACCEPT_SAMPLE_TIME`,
	`S`.`SAMPLE_ERROR_NEW_NO` AS `SAMPLE_ERROR_NEW_NO`,
	`S`.`ERROR_DEAL_REMARK` AS `ERROR_DEAL_REMARK`
FROM
	(
		(
			(
				(
					`LIMS_ORDER_SAMPLE_VIEW` `S`
					LEFT JOIN `LIMS_METADATA_SAMPLE` `MS` ON (
						(
							`MS`.`ID` = `S`.`SAMPLE_TYPE`
						)
					)
				)
				LEFT JOIN `LIMS_ORDER` `O` ON ((`S`.`ORDER_ID` = `O`.`ID`))
			)
			LEFT JOIN `APP_USER_INFO` `U` ON ((`O`.`OWNER_ID` = `U`.`ID`))
		)
		LEFT JOIN `BUSINESS_INFO` `B` ON (
			(`O`.`CREATOR_ID` = `B`.`ID`)
		)
	)

	
--增加报表菜单栏
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('16', '报表统计', '', NULL, 'fa-cog', '13', '0');
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('1601', '财务报表', '/order/orderFinancialReport/main.do', '16', 'fa-book', '1', '0');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('160101', '报表统计查看', '/order/orderFinancialReport/main.do');

INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('16', NULL, '报表统计', '13');
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('16-01', '16', '财务报表', '1');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('16-01', '160101');
INSERT INTO `limsdep`.`LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('5ed4553af9ce4b97b242d34bc1fc076c', '16-01');

-- 报告管理 已处理列表
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('021703', '已处理列表', '/repors/reportEmail/handledList.do', '0217', 'fa-tasks', '3', '0');

INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('021703', '已处理列表', '/repors/reportEmail/handledList.do');

INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('2-17-3', '2-17', '已处理列表', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('2-17-3-0', '2-17-3', '已处理列表查看', NULL);

INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('2-17-3-0', '021703');

INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '2-17-3');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '2-17-3-0');

INSERT INTO LIMS_DATA_AUTHORITY(RESOURCE_KEY,RESOURCE_NAME,DEFAULT_CONFIG) VALUES('REPORT_EMAIL_HANDLED','报告寄送已处理列表',1);



update LIMS_CONTRACT_CONTENT set INVOICE_METHOD ='1' WHERE INVOICE_METHOD is NULL;

-- 在系统报表中增加 客户报表
INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('160303', '客户报表', '/systemReportForm/customerOrderReportForm.do', '1603', 'fa-tasks', '3', '0');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('160303', '客户报表查看', '/systemReportForm/customerOrderReportForm.do');
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('16-03-03', '16-03', '客户报表查看', 3);
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('16-03-03', '160303');

-- 合同付款查看修改
INSERT INTO `LIMS_RESOURCE`(`ID`, `NAME`, `URI`)  VALUES ('021004', '查看', '/order/view.do');
INSERT INTO `LIMS_AUTHORITY_RESOURCE`(`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('290103', '021004');
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('290103', '2901', '查看', 7);
INSERT INTO `LIMS_RESOURCE`(`ID`, `NAME`, `URI`)  VALUES ('021005', '款项记录', '/order/viewPaymentRecord.do');
INSERT INTO `LIMS_AUTHORITY_RESOURCE`(`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('290104', '021005');
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('290104', '2901', '款项记录', 8);
INSERT INTO `LIMS_RESOURCE`(`ID`, `NAME`, `URI`)  VALUES ('021006', '清单详情', '/order/viewContractBill.do');
INSERT INTO `LIMS_AUTHORITY_RESOURCE`(`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('290105', '021006');
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('290105', '2901', '清单详情', 9);

-- 合同付款菜单名修改
UPDATE LIMS_AUTHORITY SET NAME='合同付款管理列表查看' WHERE ID='290100';
UPDATE LIMS_RESOURCE SET NAME='合同付款管理列表查看' WHERE ID='021001';


---修改线上数据
update LIMS_CONTRACT_PARTY_B set TEST_INSTITUTION='1' where COMPANY_NAME like '%北京迈基诺基因科技股份有限公司%' and TEST_INSTITUTION ='';
update LIMS_CONTRACT_PARTY_B set TEST_INSTITUTION='0' where COMPANY_NAME like '%北京迈基诺基因科技有限责任公司医学检验所%' and TEST_INSTITUTION ='';


update lims_contract_party_b set TEST_INSTITUTION='1' where COMPANY_NAME like '%北京迈基诺基因科技股份有限公司%' and TEST_INSTITUTION ='';
update lims_contract_party_b set TEST_INSTITUTION='0' where COMPANY_NAME like '%北京迈基诺基因科技有限责任公司医学检验所%' and TEST_INSTITUTION ='';



-----订单成本统计
DROP PROCEDURE
IF EXISTS PROCEDURE_ORDER_PRODUCT_SAMPLE;

CREATE PROCEDURE PROCEDURE_ORDER_PRODUCT_SAMPLE (
	IN testing_status VARCHAR (64),
	IN starttime datetime,
	IN endtime datetime,
	IN taskId VARCHAR (64)
)
BEGIN

DECLARE flag int;
SET @TASKID=taskId;

START TRANSACTION ;
INSERT REPORT_ORDER (
	ID,
	ORDER_ID,
	TASK_ID,
	CODE,
	ORDER_TYPE,
	TESTING_STATUS,
	EXAMINEE_NAME,
	OWNER_NAME,
	CREATOR_NAME,
	COMPANY_NAME,
	SUBMIT_TIME,
	NEED_PAY,
	REDUCE_AMOUNT,
	INCOMING_AMOUNT
) SELECT
	REPLACE (UUID(), '-', ''),
	o.id,
	@TASKID,
	o.CODE,
	o.ORDER_TYPE,
	o.TESTING_STATUS,
	e.`NAME`,
	a.REAL_NAME,
	c.`NAME` COMPANY_NAME,
	o.CREATOR_NAME,
	o.SUBMIT_TIME,
	(
		o.AMOUNT + o.SUBSIDIARY_SAMPLE_AMOUNT - o.DISCOUNT_AMOUNT - CASE
		WHEN r.CHECK_AMOUNT IS NULL THEN
			0
		ELSE
			r.CHECK_AMOUNT
		END
	) NEED_PAY,
	CASE
WHEN r.CHECK_AMOUNT IS NULL THEN
	0
ELSE
	r.CHECK_AMOUNT
END REDUCE_AMOUNT,
 o.INCOMING_AMOUNT
FROM
	LIMS_ORDER o
LEFT JOIN LIMS_ORDER_EXAMINEE e ON o.id = e.ORDER_ID
LEFT JOIN APP_USER_INFO a ON a.ID = o.OWNER_ID
LEFT JOIN APP_COMPANY c ON c.ID = a.INSTITUTION_ID
LEFT JOIN LIMS_ORDER_REDUCE r ON r.ORDER_ID = o.ID
WHERE
	o.del_flag = '0'
	AND IF(ISNULL(testing_status),1=1,o.testing_status=testing_status)
	AND DATE_FORMAT(o.submit_time,"%Y-%m-%d") BETWEEN starttime and endtime;
COMMIT;


START TRANSACTION ;
INSERT REPORT_ORDER_SCHEDULE (
	ID,
	SCHEDULE_ID,
	ORDER_ID,
	TASK_ID,
	PRODUCT_NAME,
	REFUND_STATUS ,	
	SAMPLE_CODE,
	SAMPLE_TYPE_NAME,
	PURPOSE,
	VERIFY_CHR_POSITION,
	CHECK_NAME,
	TASK_NAME,	
	TESTER_NAME,	
	ASSIGN_TIME,
	END_TIME,
	CODE,
	DETAILS,
	STATUS,	
	END_TYPE,	
	PAUSE_NAME,	
	PAUSE_TIME,
	RESTART_NAME,	
	RESTART_TIME
) SELECT
	REPLACE(UUID(),'-',''),
	s.ID,
	s.ORDER_ID,
	@TASKID,
	op.`NAME`,
	lop.REFUND_STATUS,
	sd.SAMPLE_CODE,
	sd.SAMPLE_TYPE_NAME,
	sd.PURPOSE,
	tt.VERIFY_CHR_POSITION,
	tm.`NAME` CHECK_NAME,
	tt.`NAME` TASK_NAME,
	lts.TESTER_NAME,
	lts.ASSIGN_TIME,
	tt.END_TIME,
	lts.`CODE`,
	ttr.DETAILS,	
	tt.`STATUS`,
	tt.END_TYPE,	
	sh.PAUSE_NAME,
	sh.PAUSE_TIME,
	sh.RESTART_NAME,
	sh.RESTART_TIME
FROM
	LIMS_TESTING_SCHEDULE s
LEFT JOIN LIMS_PRODUCT op ON s.PRODUCT_ID = op.ID
LEFT JOIN LIMS_ORDER_PRODUCT lop ON lop.ORDER_ID = s.ORDER_ID
AND lop.PRODUCT_ID = s.PRODUCT_ID
LEFT JOIN LIMS_TESTING_SAMPLE ts ON s.SAMPLE_ID = ts.ID
LEFT JOIN LIMS_ORDER_SAMPLE_DETAILS sd ON ts.ORIGINAL_SAMPLE_ID = sd.SAMPLE_ID
LEFT JOIN LIMS_TESTING_METHOD tm ON tm.ID = s.METHOD_ID
LEFT JOIN LIMS_TESTING_SCHEDULE_HISTORY sh ON sh.SCHEDULE_ID = s.ID
LEFT JOIN LIMS_TESTING_TASK tt ON tt.ID = sh.TASK_ID
LEFT JOIN LIMS_TESTING_SHEET_TASK tst ON tst.TESTING_TASK_ID = tt.ID
LEFT JOIN LIMS_TESTING_SHEET lts ON lts.ID = tst.SHEET_ID
LEFT JOIN LIMS_TESTING_TASK_RESULT ttr ON ttr.TASK_ID = tt.ID
WHERE
	exists (select * from REPORT_ORDER ro where ro.ORDER_ID = s.ORDER_ID and ro.TASK_ID=taskId)
ORDER BY s.START_TIME ASC;
COMMIT;
SET flag = 1;
SELECT flag;


END;
	
CREATE TABLE `REPORT_ORDER_SCHEDULE` (
	`ID` VARCHAR (64) NOT NULL COMMENT '表id',
	`SCHEDULE_ID` VARCHAR (64) NOT NULL COMMENT '流程id',
	`ORDER_ID` VARCHAR (64) NOT NULL COMMENT '订单id',
	`TASK_ID` VARCHAR (64) NOT NULL COMMENT '任务id',
	`PRODUCT_NAME` VARCHAR (64) NOT NULL COMMENT '产品名称',
	`REFUND_STATUS` tinyint NOT NULL COMMENT '产品退款状态',	
	`SAMPLE_CODE` VARCHAR (64) DEFAULT NULL COMMENT '样本名称',	
	`SAMPLE_TYPE_NAME` VARCHAR (64) DEFAULT NULL COMMENT '样本类型',
	`PURPOSE` VARCHAR (64) DEFAULT NULL COMMENT '样本用途',
    `VERIFY_CHR_POSITION` text DEFAULT NULL COMMENT '验证位点',	
	`CHECK_NAME` VARCHAR (64) DEFAULT NULL COMMENT '检测/验证方法',
	`TASK_NAME` VARCHAR (64) DEFAULT NULL COMMENT '任务节点',	
	`TESTER_NAME` VARCHAR (64) DEFAULT NULL COMMENT '任务接收人',	
	`ASSIGN_TIME` datetime DEFAULT NULL COMMENT '下单时间',
	`END_TIME` datetime DEFAULT NULL COMMENT '结束时间',
	`CODE` VARCHAR (64) DEFAULT NULL COMMENT '任务单号',
	`DETAILS` text DEFAULT NULL COMMENT '详情，包含异常备注',
	`STATUS` tinyint DEFAULT NULL COMMENT '流程状态',	
	`END_TYPE` tinyint DEFAULT NULL COMMENT '结束类型',	
	`PAUSE_NAME` VARCHAR (64) DEFAULT NULL COMMENT '暂停人',	
	`PAUSE_TIME` datetime DEFAULT NULL COMMENT '暂停时间',
	`RESTART_NAME` VARCHAR (64) DEFAULT NULL COMMENT '重新启动人',	
	`RESTART_TIME` datetime DEFAULT NULL COMMENT '重启时间',
	PRIMARY KEY (`ID`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;


ALTER TABLE `REPORT_ORDER_SCHEDULE` ADD INDEX INDEX_TASK ( `TASK_ID` );
ALTER TABLE `REPORT_ORDER_SCHEDULE` ADD INDEX INDEX_ORDER ( `ORDER_ID` );


CREATE TABLE `REPORT_ORDER` (
	`ID` VARCHAR (64) NOT NULL COMMENT '表id',
	`ORDER_ID` VARCHAR (64) NOT NULL COMMENT '订单id',
	`TASK_ID` VARCHAR (64) NOT NULL COMMENT '任务id',
	`CODE` VARCHAR (64) NOT NULL COMMENT '订单编号',
	`ORDER_TYPE` VARCHAR (64) NOT NULL COMMENT '订单营销中心',
	`TESTING_STATUS` VARCHAR (64) DEFAULT NULL COMMENT '订单主状态',
	`EXAMINEE_NAME` VARCHAR (64) DEFAULT NULL COMMENT '受检人名称',
	`OWNER_NAME` VARCHAR (64) DEFAULT NULL COMMENT '客户名称',
	`CREATOR_NAME` VARCHAR (64) DEFAULT NULL COMMENT '业务员',
	`COMPANY_NAME` VARCHAR (64) DEFAULT NULL COMMENT '医院单位',
	`SUBMIT_TIME` datetime DEFAULT NULL COMMENT '下单时间',
	`NEED_PAY` INT (11) DEFAULT NULL COMMENT '应付款',
	`REDUCE_AMOUNT` INT (11) DEFAULT NULL COMMENT '减免金额',
	`INCOMING_AMOUNT` INT (11) DEFAULT NULL COMMENT '实收金额',
	PRIMARY KEY (`ID`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;

ALTER TABLE `REPORT_ORDER` ADD INDEX INDEX_TASK ( `TASK_ID` );
ALTER TABLE `REPORT_ORDER` ADD INDEX INDEX_ORDER ( `ORDER_ID` );



-- 订单财务报表
CREATE TABLE `REPORT_FINANCIAL_ORDER_INVOICE` (
	`ID` VARCHAR (64) NOT NULL COMMENT '表id',
	`ORDER_ID` VARCHAR (64) NOT NULL COMMENT '订单id',
	`TASK_ID` VARCHAR (64) NOT NULL COMMENT '任务id',
	`DRAWER_NO` VARCHAR (64) DEFAULT NULL COMMENT '发票号',
	`AMOUNT` INT (11) DEFAULT NULL COMMENT '开票金额',
	`NAME` VARCHAR (64) DEFAULT NULL COMMENT '开票人',	
	`INVOICE_TIME` datetime DEFAULT NULL COMMENT '开票时间',
	PRIMARY KEY (`ID`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;


ALTER TABLE `REPORT_FINANCIAL_ORDER_INVOICE` ADD INDEX INDEX_TASK (`TASK_ID`);
ALTER TABLE `REPORT_FINANCIAL_ORDER_INVOICE` ADD INDEX INDEX_ORDER (`ORDER_ID`);



CREATE TABLE `REPORT_FINANCIAL_ORDER` (
	`ID` VARCHAR (64) NOT NULL COMMENT '表id',
	`ORDER_ID` VARCHAR (64) NOT NULL COMMENT '订单id',
	`TASK_ID` VARCHAR (64) NOT NULL COMMENT '任务id',
	`SETTLEMENT_TYPE` VARCHAR (64) DEFAULT NULL COMMENT '结算模式',
	`CODE` VARCHAR (64) NOT NULL COMMENT '订单编号',
	`SUBMIT_TIME` datetime DEFAULT NULL COMMENT '下单时间',
	`OWNER_NAME` VARCHAR (64) DEFAULT NULL COMMENT '客户名称',
	`PRODUCT_NAMES` VARCHAR (256) DEFAULT NULL COMMENT '产品名称',
	`COMPANY_NAME` VARCHAR (64) DEFAULT NULL COMMENT '医院单位',
	`SAMPLING_AMOUNT` INT (11) DEFAULT NULL COMMENT '样本收样费用',
	`CREATOR_NAME` VARCHAR (64) DEFAULT NULL COMMENT '业务员',
	`EXAMINEE_NAME` VARCHAR (64) DEFAULT NULL COMMENT '受检人名称',
	`CHECK_TIME` datetime DEFAULT NULL COMMENT '交款时间',
	`ORDER_AMOUNT` INT (11) DEFAULT NULL COMMENT '订单金额',
	`REDUCE_AMOUNT` INT (11) DEFAULT NULL COMMENT '减免金额',
	`NEED_PAY` INT (11) DEFAULT NULL COMMENT '应付款',
	`INCOMING_AMOUNT` INT (11) DEFAULT NULL COMMENT '实收金额',
	`PAY_TYPE` VARCHAR (64) DEFAULT NULL COMMENT '到款方式',
	`POS_NO` VARCHAR (64) DEFAULT NULL COMMENT 'POS号',
	`PAYMENTER` VARCHAR (64) DEFAULT NULL COMMENT '联系人',
	`INVOICE_APPLY_TYPE` VARCHAR (64) DEFAULT NULL COMMENT '专票/普票',
	`INVOICE_TITLE` VARCHAR (64) DEFAULT NULL COMMENT '开票抬头',
	`INVOICE_CONTENT` VARCHAR (64) DEFAULT NULL COMMENT '开票内容',
	PRIMARY KEY (`ID`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;

ALTER TABLE `REPORT_FINANCIAL_ORDER` ADD INDEX INDEX_TASK (`TASK_ID`);
ALTER TABLE `REPORT_FINANCIAL_ORDER` ADD INDEX INDEX_ORDER (`ORDER_ID`);


DROP PROCEDURE
IF EXISTS PROCEDURE_FINANCIAL_ORDER;

CREATE PROCEDURE PROCEDURE_FINANCIAL_ORDER (
	IN STARTTIME DATETIME,
	IN ENDTIME DATETIME,
	IN TASKID VARCHAR (64)
)
BEGIN

DECLARE FLAG INT;
SET @TASKID=TASKID;

START TRANSACTION ;
INSERT REPORT_FINANCIAL_ORDER (
	ID,
	ORDER_ID,
	TASK_ID,
	SETTLEMENT_TYPE,
	CODE,
	SUBMIT_TIME,
	OWNER_NAME,
	PRODUCT_NAMES,
	COMPANY_NAME,
	SAMPLING_AMOUNT,
	CREATOR_NAME,
	EXAMINEE_NAME,
	CHECK_TIME,
	ORDER_AMOUNT,
	REDUCE_AMOUNT,
	NEED_PAY,
	INCOMING_AMOUNT,
	PAY_TYPE,
	POS_NO,
	PAYMENTER,
	INVOICE_APPLY_TYPE,
	INVOICE_TITLE,
	INVOICE_CONTENT
) SELECT
	REPLACE (UUID(), '-', ''),
	O.ID,
	@TASKID,
	IFNULL(CASE CC.SETTLEMENT_MODE
WHEN 1 THEN
	'一次性付款'
WHEN 2 THEN
	'分批付款'
WHEN 3 THEN
	'月结'
WHEN 4 THEN
	'一单一结'
END, '一单一结') SETTLEMENT_TYPE,
	O. CODE,
	O.SUBMIT_TIME,
	A.REAL_NAME,
	LOPS.PRODUCTNAMES,
	C.`NAME` COMPANY_NAME,
	O.SAMPLING_AMOUNT,
	O.CREATOR_NAME,
	E.`NAME`,
	LPCS.CHECK_TIME,
	(O.AMOUNT + O.SUBSIDIARY_SAMPLE_AMOUNT - O.DISCOUNT_AMOUNT)*0.01 ORDERAMOUNT,
	IFNULL(R.CHECK_AMOUNT*0.01, 0) REDUCEAMOUNT,
	(
		(O.AMOUNT + O.SUBSIDIARY_SAMPLE_AMOUNT - O.DISCOUNT_AMOUNT - CASE
		WHEN R.CHECK_AMOUNT IS NULL THEN
			0
		ELSE
			R.CHECK_AMOUNT
		END)*0.01
	) NEED_PAY,
	O.INCOMING_AMOUNT*0.01,
	CASE O.PAY_TYPE
WHEN 0 THEN
	'未付款'
WHEN 1 THEN
	'支付宝'
WHEN 2 THEN
	'支付宝扫码'
WHEN 3 THEN
	'POS机支付'
WHEN 4 THEN
	'转账支付'
ELSE
	''
END AS PAY_TYPE,
 IFNULL(LPCS.POS_NO, '') POS_NO,
 IFNULL(LPCS.PAYMENTER, '') PAYMENTER,
 CASE IA.INVOICE_TYPE
WHEN 1 THEN
	'普票'
WHEN 2 THEN
	'专票'
END AS INVOICE_APPLY_TYPE,
 IFNULL(
	IA.INVOICE_TITLE,
	O.INVOICE_TITLE
) INVOICE_TITLE,
 IFNULL(IA.INVOICE_CONTENT, '') INVOICE_CONTENT
FROM
	LIMS_ORDER O
LEFT JOIN LIMS_ORDER_EXAMINEE E ON O.ID = E.ORDER_ID
LEFT JOIN APP_USER_INFO A ON A.ID = O.OWNER_ID
LEFT JOIN LIMS_CONTRACT_CONTENT CC ON CC.CONTRACT_ID = O.CONTRACT_ID
LEFT JOIN APP_COMPANY C ON C.ID = A.INSTITUTION_ID
LEFT JOIN (
	SELECT
		LOP.ORDER_ID,
		GROUP_CONCAT(
			LOP.PRODUCT_NAME SEPARATOR ','
		) PRODUCTNAMES
	FROM
		LIMS_ORDER_PRODUCT LOP
	GROUP BY
		LOP.ORDER_ID
) LOPS ON LOPS.ORDER_ID = O.ID
LEFT JOIN LIMS_ORDER_REDUCE R ON R.ORDER_ID = O.ID
LEFT JOIN (
	SELECT
		LPC.ORDER_ID,
		GROUP_CONCAT(LPC.POS_NO SEPARATOR ' ') POS_NO,
		GROUP_CONCAT(LPC.PAYMENTER SEPARATOR ' ') PAYMENTER,
		LPC.CHECK_TIME
	FROM
		LIMS_ORDER_PAYMENT_CONFIRM LPC
	GROUP BY
		LPC.ORDER_ID
) LPCS ON LPCS.ORDER_ID = O.ID
LEFT JOIN LIMS_INVOICE_APPLY IA ON ORDER_IDS LIKE CONCAT('%' + O.ID + '%')
WHERE
	O.DEL_FLAG = '0'
	AND DATE_FORMAT(O.SUBMIT_TIME,"%Y-%m-%d") BETWEEN STARTTIME AND ENDTIME;
COMMIT;


START TRANSACTION ;
INSERT REPORT_FINANCIAL_ORDER_INVOICE (
	ID,
	ORDER_ID,
	TASK_ID,	
	DRAWER_NO,
	AMOUNT,	
	NAME,	
	INVOICE_TIME
) SELECT REPLACE(UUID(),'-','') ,TEMP_APPLY.ORDER_ID,@TASKID,TEMP_APPLY.DRAWER_NO,
	O.AMOUNT*0.01 AS AMOUNT,
	LIUM.`NAME`,
	DATE_FORMAT(TEMP_APPLY.INVOICE_TIME, '%Y-%m-%d') AS INVOICE_TIME
	FROM(
	SELECT A.INVOICE_TASK_ID, SUBSTRING_INDEX(SUBSTRING_INDEX(A.ORDER_IDS,',',B.AUTOINCREID),',',-1) AS ORDER_ID,
	A.DRAWER_NO,A.DRAWER_ID,A.INVOICE_TIME,A.RECEIVER_NAME
	FROM 
	LIMS_INVOICE_INFO A
	JOIN
	REPORT_INCRE_TABLE B
	ON B.AUTOINCREID <= (LENGTH(A.ORDER_IDS) - LENGTH(REPLACE(A.ORDER_IDS,',',''))+1)
	) TEMP_APPLY 
	LEFT JOIN LIMS_ORDER O ON O.ID = TEMP_APPLY.ORDER_ID
	LEFT JOIN LIMS_FINANCE_INVOICE_TASK LFIT ON LFIT.ID = TEMP_APPLY.INVOICE_TASK_ID
	LEFT JOIN LIMS_INVOICE_USER_MODEL LIUM ON LIUM.ID = TEMP_APPLY.DRAWER_ID	
   WHERE 
   EXISTS (SELECT * FROM REPORT_FINANCIAL_ORDER FO WHERE FO.ORDER_ID = TEMP_APPLY.ORDER_ID AND FO.TASK_ID=TASKID)
  
	UNION ALL
	-- 默认开票
	SELECT REPLACE(UUID(),'-','') ,LFIT.RECORD_KEY AS ORDER_ID,@TASKID,LII.DRAWER_NO,
	LII.INVOICE_AMOUNT,
	LIUM.`NAME`,
	DATE_FORMAT(LII.INVOICE_TIME, '%Y-%m-%d') AS INVOICE_TIME
	FROM LIMS_INVOICE_INFO LII
	LEFT JOIN LIMS_FINANCE_INVOICE_TASK LFIT ON LFIT.ID = LII.INVOICE_TASK_ID AND LFIT.CATEGORY = '1'
	LEFT JOIN LIMS_INVOICE_USER_MODEL LIUM ON LIUM.ID = LII.DRAWER_ID
	
	
WHERE
	EXISTS (SELECT * FROM REPORT_FINANCIAL_ORDER FO WHERE FO.ORDER_ID = LFIT.RECORD_KEY AND FO.TASK_ID=TASKID);

COMMIT;
SET FLAG = 1;
SELECT FLAG;

END;
	

---临床
CREATE TABLE `REPORT_CLINICAL_ORDER` (
	`ID` VARCHAR (64) NOT NULL COMMENT '表ID',
	`ORDER_ID` VARCHAR (64) NOT NULL COMMENT '订单ID',
	`TASK_ID` VARCHAR (64) NOT NULL COMMENT '任务ID',
	`CODE` VARCHAR (64) NOT NULL COMMENT '订单编号',
	`ORDER_TYPE` VARCHAR (64) NOT NULL COMMENT '订单营销中心',
	`TESTING_STATUS` VARCHAR (64) DEFAULT NULL COMMENT '订单主状态',
	`PAYMENT_STATUS` VARCHAR (64) DEFAULT NULL COMMENT '订单支付状态',
	`CREATOR_NAME` VARCHAR (64) DEFAULT NULL COMMENT '业务员-销售员',
	`INCOMING_AMOUNT` INT (11) DEFAULT NULL COMMENT '实收金额',
	`PRODUCT_NAMES` VARCHAR (256) DEFAULT NULL COMMENT '检测项目-产品',
	`COMPANY_NAME` VARCHAR (64) DEFAULT NULL COMMENT '医院名称',
	`EXAMINEE_NAME` VARCHAR (64) DEFAULT NULL COMMENT '患者姓名',
	`RECIPIENT_PHONE` VARCHAR (64) DEFAULT NULL COMMENT '联系方式',
	`RECIPIENT_ADDRESS` VARCHAR (64) DEFAULT NULL COMMENT '通讯地址',
	`REMARK` VARCHAR (256) DEFAULT NULL COMMENT '备注',
	`SUBMIT_TIME` DATETIME DEFAULT NULL COMMENT '下单时间',
	`INVOICE_CONTENT` VARCHAR (256) DEFAULT NULL COMMENT '开票内容',
	PRIMARY KEY (`ID`)
) ENGINE = INNODB DEFAULT CHARSET = UTF8;

ALTER TABLE `REPORT_CLINICAL_ORDER` ADD INDEX INDEX_TASK (`TASK_ID`);
ALTER TABLE `REPORT_CLINICAL_ORDER` ADD INDEX INDEX_ORDER (`ORDER_ID`);




CREATE TABLE `REPORT_CLINICAL_ORDER_INVOICE` (
	`ID` VARCHAR (64) NOT NULL COMMENT '表ID',
	`ORDER_ID` VARCHAR (64) NOT NULL COMMENT '订单ID',
	`TASK_ID` VARCHAR (64) NOT NULL COMMENT '任务ID',
	`DRAWER_NO` VARCHAR (64) DEFAULT NULL COMMENT '发票号',
	`AMOUNT` INT (11) DEFAULT NULL COMMENT '开票金额',
	`NAME` VARCHAR (64) DEFAULT NULL COMMENT '开票单位',	
	`INVOICE_TIME` DATETIME DEFAULT NULL COMMENT '开票时间',
	`TRACK_NUMBER` VARCHAR (64) DEFAULT NULL COMMENT '快递号',	
	PRIMARY KEY (`ID`)
) ENGINE = INNODB DEFAULT CHARSET = UTF8;

#添加索引
ALTER TABLE `REPORT_CLINICAL_ORDER_INVOICE` ADD INDEX INDEX_TASK (`TASK_ID`);
ALTER TABLE `REPORT_CLINICAL_ORDER_INVOICE` ADD INDEX INDEX_ORDER (`ORDER_ID`);

DROP PROCEDURE
IF EXISTS PROCEDURE_CLINICAL_ORDER;

CREATE PROCEDURE PROCEDURE_CLINICAL_ORDER (
	IN PAYMENT_STATUS VARCHAR (64),
	IN STARTTIME DATETIME,
	IN ENDTIME DATETIME,
	IN TASKID VARCHAR (64)
)
BEGIN

DECLARE FLAG INT;
SET @TASKID=TASKID;

START TRANSACTION ;
INSERT REPORT_CLINICAL_ORDER (
	ID,
	ORDER_ID,
	TASK_ID,
	CODE,
	ORDER_TYPE,
	TESTING_STATUS,
	PAYMENT_STATUS,
	CREATOR_NAME,
	INCOMING_AMOUNT,
	PRODUCT_NAMES,
	COMPANY_NAME,
	EXAMINEE_NAME,
	RECIPIENT_PHONE,
	RECIPIENT_ADDRESS,
	REMARK,
	SUBMIT_TIME,
	INVOICE_CONTENT
) SELECT
	REPLACE (UUID(), '-', ''),
	O.ID,
	@TASKID,
	O.CODE,
CASE O.ORDER_TYPE
WHEN 1 THEN
	'临床遗传'
WHEN 2 THEN
	'临床肿瘤'
WHEN 3 THEN
	'健康筛查'
WHEN 4 THEN
	'科技服务'
END AS ORDER_TYPE,
CASE O.TESTING_STATUS
WHEN 1 THEN
	'待检测下放'
WHEN 2 THEN
	'检测中'
WHEN 3 THEN
	'已暂停'
WHEN 4 THEN
	'已取消'
WHEN 5 THEN
	'已完成'
END AS TESTING_STATUS,
CASE O.PAYMENT_STATUS
WHEN 0 THEN
	'未支付'
WHEN 1 THEN
	'待付款确认'
WHEN 2 THEN
	'已付款'
END AS PAYMENT_STATUS,
	O.CREATOR_NAME,
	O.INCOMING_AMOUNT*0.01 INCOMING_AMOUNT,
	LOPS.PRODUCTNAMES,	
	C.`NAME` COMPANY_NAME,	
	E.`NAME`,
	O.RECIPIENT_PHONE,
	O.RECIPIENT_ADDRESS,
	OPCS.REMARKS REMARK,
	O.SUBMIT_TIME,
	TEMPTABLE.INVOICE_CONTENT
FROM
	LIMS_ORDER O
LEFT JOIN LIMS_ORDER_EXAMINEE E ON O.ID = E.ORDER_ID
LEFT JOIN APP_USER_INFO A ON A.ID = O.OWNER_ID
LEFT JOIN APP_COMPANY C ON C.ID = A.INSTITUTION_ID
LEFT JOIN LIMS_INVOICE_APPLY IA ON IA.ORDER_IDS = O.ID
LEFT JOIN (
	SELECT SUBSTRING_INDEX(SUBSTRING_INDEX(A.ORDER_IDS,',',B.AUTOINCREID),',',-1) AS ORDER_ID,A.INVOICE_CONTENT INVOICE_CONTENT
	FROM 
	LIMS_INVOICE_APPLY A
	JOIN
	REPORT_INCRE_TABLE B
	ON B.AUTOINCREID <= (LENGTH(A.ORDER_IDS) - LENGTH(REPLACE(A.ORDER_IDS,',',''))+1)
) TEMPTABLE ON TEMPTABLE.ORDER_ID=O.ID
LEFT JOIN 
(
	SELECT
		OPC.ORDER_ID,
		GROUP_CONCAT(
			OPC.REMARK SEPARATOR ' '
		) REMARKS
	FROM
		LIMS_ORDER_PAYMENT_CONFIRM OPC
	GROUP BY
		OPC.ORDER_ID
) OPCS ON OPCS.ORDER_ID = O.ID
LEFT JOIN (
	SELECT
		LOP.ORDER_ID,
		GROUP_CONCAT(
			LOP.PRODUCT_NAME SEPARATOR ','
		) PRODUCTNAMES
	FROM
		LIMS_ORDER_PRODUCT LOP
	GROUP BY
		LOP.ORDER_ID
) LOPS ON LOPS.ORDER_ID = O.ID

WHERE
	O.DEL_FLAG = '0'
	AND O.CONTRACT_ID IS NULL
	AND IF(ISNULL(PAYMENT_STATUS)|| PAYMENT_STATUS='',1=1,o.PAYMENT_STATUS=PAYMENT_STATUS)
	AND DATE_FORMAT(O.SUBMIT_TIME,"%Y-%m-%d") BETWEEN STARTTIME AND ENDTIME;
COMMIT;


START TRANSACTION ;


INSERT REPORT_CLINICAL_ORDER_INVOICE (
	ID,
	ORDER_ID,
	TASK_ID,	
	DRAWER_NO,
	AMOUNT,	
	NAME,	
	INVOICE_TIME,
	TRACK_NUMBER
) SELECT REPLACE(UUID(),'-','') ,TEMP_APPLY.ORDER_ID,@TASKID,TEMP_APPLY.DRAWER_NO,
	O.AMOUNT*0.01 AS AMOUNT,
	LIUM.`NAME`,
	DATE_FORMAT(TEMP_APPLY.INVOICE_TIME, '%Y-%m-%d') AS INVOICE_TIME,
	LIS.TRACK_NUMBER
	FROM(
	SELECT A.INVOICE_TASK_ID, SUBSTRING_INDEX(SUBSTRING_INDEX(A.ORDER_IDS,',',B.AUTOINCREID),',',-1) AS ORDER_ID,
	A.DRAWER_NO,A.DRAWER_ID,A.INVOICE_TIME,A.RECEIVER_NAME
	FROM 
	LIMS_INVOICE_INFO A
	JOIN
	REPORT_INCRE_TABLE B
	ON B.AUTOINCREID <= (LENGTH(A.ORDER_IDS) - LENGTH(REPLACE(A.ORDER_IDS,',',''))+1)
	) TEMP_APPLY 
	LEFT JOIN LIMS_ORDER O ON O.ID = TEMP_APPLY.ORDER_ID
	LEFT JOIN LIMS_FINANCE_INVOICE_TASK LFIT ON LFIT.ID = TEMP_APPLY.INVOICE_TASK_ID
	LEFT JOIN LIMS_INVOICE_USER_MODEL LIUM ON LIUM.ID = TEMP_APPLY.DRAWER_ID
	
	LEFT JOIN LIMS_INVOICE_SEND_RECORD_KEY LISR ON LISR.RECORD_KEY = LFIT.RECORD_KEY
	LEFT JOIN LIMS_INVOICE_SEND LIS ON LIS.ID = LISR.SEND_ID
   WHERE 
   EXISTS (SELECT * FROM REPORT_FINANCIAL_ORDER FO WHERE FO.ORDER_ID = TEMP_APPLY.ORDER_ID AND FO.TASK_ID=TASKID)
  
	UNION ALL
	-- 默认开票
	SELECT REPLACE(UUID(),'-','') ,LFIT.RECORD_KEY AS ORDER_ID,@TASKID,LII.DRAWER_NO,
	LII.INVOICE_AMOUNT,
	LIUM.`NAME`,
	DATE_FORMAT(LII.INVOICE_TIME, '%Y-%m-%d') AS INVOICE_TIME,
	LIS.TRACK_NUMBER
	FROM LIMS_INVOICE_INFO LII
	LEFT JOIN LIMS_FINANCE_INVOICE_TASK LFIT ON LFIT.ID = LII.INVOICE_TASK_ID AND LFIT.CATEGORY = '1'
	LEFT JOIN LIMS_INVOICE_USER_MODEL LIUM ON LIUM.ID = LII.DRAWER_ID
	LEFT JOIN LIMS_INVOICE_SEND_RECORD_KEY LISR ON LISR.RECORD_KEY = LFIT.RECORD_KEY
	LEFT JOIN LIMS_INVOICE_SEND LIS ON LIS.ID = LISR.SEND_ID
	
WHERE
	EXISTS (SELECT * FROM REPORT_CLINICAL_ORDER RCO WHERE RCO.ORDER_ID = LFIT.RECORD_KEY AND RCO.TASK_ID=TASKID);


COMMIT;
SET FLAG = 1;
SELECT FLAG;

END;

-- 系统报表-客户报表
CREATE TABLE REPORT_SYSTEM_CUSTOMER_INFO
(
ID VARCHAR(64) PRIMARY KEY,
TASK_ID VARCHAR(64) NOT NULL,
USER_ID VARCHAR(64) NOT NULL,
Customer_NAME VARCHAR(64),
SEX VARCHAR(32),
ACTIVATE_STATUS VARCHAR(64),
COMPANY_NAME VARCHAR(64),
DEPT VARCHAR(128),
EMAIL VARCHAR(128),
PHONE_NUM VARCHAR(16),
ROOM_NUM VARCHAR(128),
LANDLINE VARCHAR(32),
BIRTHDAY DATE,
POSITION VARCHAR(64),
COOPERATE VARCHAR(256),
ANALYSIS_TYPE VARCHAR(64),
ADDRESS VARCHAR(64),
CHARACTERISTIC VARCHAR(1024),
RESEARCH_FILED VARCHAR(1024),
INTRODUCTION VARCHAR(1024),
CountSubCustomer INT(11),
SubCustomer VARCHAR(1024),
CreatorName VARCHAR(64),
CurrentName VARCHAR(1024)
)

-- 客户报表存储过程
delimiter //
DROP PROCEDURE
IF EXISTS `export_customer_from_data`//

CREATE PROCEDURE export_customer_from_data (IN TASKID VARCHAR(64),IN Customer_Name VARCHAR(64),IN Company_Name VARCHAR(64),IN ACTIVATE_STATUS VARCHAR(64))
BEGIN

DECLARE FLAG INT;
SET @TASKID=TASKID;

START TRANSACTION ;
	INSERT REPORT_SYSTEM_CUSTOMER_INFO (
		ID,
 		TASK_ID,
		USER_ID,
		CUSTOMER_NAME,
		SEX,
		ACTIVATE_STATUS,
		Company_NAME,
		DEPT,
		EMAIL,
		PHONE_NUM,
		ROOM_NUM,
		LANDLINE,
		BIRTHDAY,
		POSITION,
		COOPERATE,
		ANALYSIS_TYPE,
		ADDRESS,
		CHARACTERISTIC,
		RESEARCH_FILED,
		INTRODUCTION,
		CountSubCustomer,
		SubCustomer,
		CreatorName,
		CurrentName
	)
SELECT
	REPLACE
	(UUID(),'-','') ,
	@TASKID,
	aui.ID,
	aui.REAL_NAME,
	sex.SEX AS SEX,
	CASE aui.ACTIVATE_STATUS
	WHEN 0 then '未激活'
	WHEN 1 then '已激活'
	ELSE ''
	END AS ACTIVATE_STATUS,
	ac.`NAME` AS Company,
	dept.DEPT,
	aui.EMAIL,
	aui.PHONE_NUM,
	aui.ROOM_NUM,
	aui.LANDLINE,
	aui.BIRTHDAY,
	position.POSITION,
	cooperate.COOPERATE,
	CASE aui.ANALYSIS_TYPE
	WHEN '0/0' then '不参与'
	WHEN '1/0' then '参与技术分析'
	WHEN '0/1' then '参与报告评审'
	WHEN '1/1' then '参与技术分析与报告评审'
	ELSE ''
	END AS ANALYSIS_TYPE,
	ac.ADDRESS AS ADDRESS,
	aui.CHARACTERISTIC,
	aui.RESEARCH_FILED,
	aui.INTRODUCTION,
	(SELECT COUNT(*) FROM APP_USER_INFO auin WHERE auin.PARENT_ID = aui.ID) AS CountSubCustomer,
	sub_c.SubCustomer,
	bi.REAL_NAME AS CreatorName,
	current.currentName
FROM APP_USER_INFO aui
LEFT JOIN APP_COMPANY ac on ac.ID = aui.INSTITUTION_ID
LEFT JOIN (SELECT GROUP_CONCAT(auin.REAL_NAME,'_',auin.PHONE_NUM SEPARATOR '、') AS SubCustomer,auin.PARENT_ID FROM APP_USER_INFO auin GROUP BY auin.PARENT_ID) sub_c ON sub_c.PARENT_ID = aui.ID
LEFT JOIN BUSINESS_INFO bi ON bi.ID = aui.CREATE_ID AND bi.DEL_FLAG = 0
LEFT JOIN
(
SELECT aur.BUSINESS_USER_ID,aur.USER_ID,GROUP_CONCAT(bus_info.REAL_NAME SEPARATOR '、') AS currentName
FROM APP_USER_RELATION aur,BUSINESS_INFO bus_info
WHERE aur.BUSINESS_USER_ID = bus_info.ID AND bus_info.DEL_FLAG = 0
GROUP BY aur.USER_ID
) current ON current.USER_ID = aui.ID
LEFT JOIN (SELECT ld.DICT_TEXT AS DEPT,ld.DICT_VALUE FROM LIMS_DICT ld WHERE ld.CATEGORY = 'BASE_DEPT') dept ON dept.DICT_VALUE = aui.DEPT
LEFT JOIN (SELECT ld.DICT_TEXT AS COOPERATE,ld.DICT_VALUE FROM LIMS_DICT ld WHERE ld.CATEGORY = 'COOPERATE') cooperate ON cooperate.DICT_VALUE = aui.COOPERATE
LEFT JOIN (SELECT ld.DICT_TEXT AS POSITION,ld.DICT_VALUE FROM LIMS_DICT ld WHERE ld.CATEGORY = 'POSITION') position ON position.DICT_VALUE = aui.POSITION
LEFT JOIN (SELECT ld.DICT_TEXT AS SEX,ld.DICT_VALUE FROM LIMS_DICT ld WHERE ld.CATEGORY = 'SEX') sex ON sex.DICT_VALUE = aui.SEX
WHERE
aui.DEL_FLAG = 0
AND IF(ISNULL(Customer_Name)||Customer_Name='',1=1,aui.REAL_NAME=Customer_Name)
AND IF(ISNULL(Company_Name)||Company_Name='',1=1,ac.`NAME`=Company_Name)
AND IF(ISNULL(ACTIVATE_STATUS)||ACTIVATE_STATUS='',1=1,aui.ACTIVATE_STATUS=ACTIVATE_STATUS)
ORDER BY aui.ID;
COMMIT ;
SET FLAG = 1;
SELECT FLAG;

END//

-- 字典 性别增加“未出生胎儿”选择项
INSERT INTO LIMS_DICT (ID,PARENT_ID,CATEGORY,DICT_TEXT,DICT_VALUE,SORT,DICT_DESC,EDITABLE) values('4f8ec4face9811e7','24790816161529856','SEX','未出生胎儿','2','3','性别','0');

-- 宋健
update LIMS_MENU set URI ='/payment/appPaymentConfirm.do' WHERE id='1301';
update LIMS_MENU set URI ='/payment/backPaymentList.do' WHERE id='1302';

update LIMS_RESOURCE set URI ='/payment/appPaymentConfirm.do' WHERE id='1301';
update LIMS_RESOURCE set URI ='/payment/backPaymentList.do' WHERE id='1302';

ALTER TABLE `LIMS_ORDER_PRIMARY_SAMPLE` ADD INDEX index_order (`ORDER_ID`);
ALTER TABLE `LIMS_ORDER_SUBSIDIARY_SAMPLE` ADD INDEX index_order (`ORDER_ID`);
ALTER TABLE `LIMS_ORDER_RESEARCH_SAMPLE` ADD INDEX index_order (`ORDER_ID`);

ALTER TABLE `LIMS_ORDER_RESEARCH_PRODUCT` ADD INDEX index_ors_id (`ORS_ID`); 
ALTER TABLE `LIMS_ORDER_DELAY` ADD INDEX index_order (`ORDER_ID`);


-- 宋健 begin
DROP VIEW IF EXISTS `MY_SAMPLE_VIEW`;
CREATE VIEW `MY_SAMPLE_VIEW` AS
SELECT
	`O`.`CODE` AS `ORDER_NO`,
	`O`.`ID` AS `ORDER_ID`,
	`O`.`ORDER_TYPE` AS `ORDER_TYPE`,
	`O`.`TESTING_STATUS` AS `ORDER_STATUS`,
	`O`.`OWNER_ID` AS `OWNER_ID`,
	`PS`.`EXAMINEE_ID` AS `EXAMINEE_ID`,
	`E`.`NAME` AS `EXAMINEE_NAME`,
	NULL AS `FAMILY_RELATION`,
	`PS`.`ID` AS `SAMPLE_ID`,
	`PS`.`SAMPLE_CODE` AS `SAMPLE_CODE`,
	`MS`.`NAME` AS `SAMPLE_TYPE`,
	NULL AS `SAMPLE_NAME`,
	`PS`.`SAMPLE_PACKAGE_NO` AS `SAMPLE_PACKAGE_NO`,
	`PS`.`SAMPLE_PACKAGE_STATUS` AS `SAMPLE_PACKAGE_STATUS`,
	`PS`.`SAMPLE_ERROR_STATUS` AS `SAMPLE_ERROR_STATUS`,
	`PS`.`SAMPLE_ERROR_NEW_NO` AS `SAMPLE_ERROR_NEW_NO`,
	1 AS `SAMPLE_TABLE_TYPE`,
	`PS`.`SAMPLE_BACK_STATUS` AS `SAMPLE_BACK_STATUS`,
	`PS`.`SAMPLE_BACK_OPTION` AS `SAMPLE_BACK_OPTION`,
	`PS`.`SAMPLE_BACK_NO` AS `SAMPLE_BACK_NO`,
	`PS`.`ACCEPT_SAMPLE_TIME` AS `ACCEPT_SAMPLE_TIME`,
	`PS`.`ERROR_REASON` AS `ERROR_REASON`,
	`PS`.`ERROR_DEAL_REMARK` AS `ERROR_DEAL_REMARK`
FROM
	(
		(
			(
				`LIMS_ORDER_PRIMARY_SAMPLE` `PS`
				LEFT JOIN `LIMS_ORDER` `O` ON ((`PS`.`ORDER_ID` = `O`.`ID`))
			)
			LEFT JOIN `LIMS_ORDER_EXAMINEE` `E` ON (
				(
					`PS`.`EXAMINEE_ID` = `E`.`ID`
				)
			)
		)
		LEFT JOIN `LIMS_METADATA_SAMPLE` `MS` ON (
			(
				`PS`.`SAMPLE_TYPE_ID` = `MS`.`ID`
			)
		)
	)
WHERE
	(`O`.`DEL_FLAG` = '0')
UNION ALL
	SELECT
		`O`.`CODE` AS `ORDER_NO`,
		`O`.`ID` AS `ORDER_ID`,
		`O`.`ORDER_TYPE` AS `ORDER_TYPE`,
		`O`.`TESTING_STATUS` AS `ORDER_STATUS`,
		`O`.`OWNER_ID` AS `OWNER_ID`,
		`SS`.`EXAMINEE_ID` AS `EXAMINEE_ID`,
		`E`.`NAME` AS `EXAMINEE_NAME`,
		`D`.`DICT_TEXT` AS `FAMILY_RELATION`,
		`SS`.`ID` AS `SAMPLE_ID`,
		`SS`.`SAMPLE_CODE` AS `SAMPLE_CODE`,
		`MS`.`NAME` AS `SAMPLE_TYPE`,
		NULL AS `SAMPLE_NAME`,
		`SS`.`SAMPLE_PACKAGE_NO` AS `SAMPLE_PACKAGE_NO`,
		`SS`.`SAMPLE_PACKAGE_STATUS` AS `SAMPLE_PACKAGE_STATUS`,
		`SS`.`SAMPLE_ERROR_STATUS` AS `SAMPLE_ERROR_STATUS`,
		`SS`.`SAMPLE_ERROR_NEW_NO` AS `SAMPLE_ERROR_NEW_NO`,
		2 AS `SAMPLE_TABLE_TYPE`,
		`SS`.`SAMPLE_BACK_STATUS` AS `SAMPLE_BACK_STATUS`,
		`SS`.`SAMPLE_BACK_OPTION` AS `SAMPLE_BACK_OPTION`,
		`SS`.`SAMPLE_BACK_NO` AS `SAMPLE_BACK_NO`,
		`SS`.`ACCEPT_SAMPLE_TIME` AS `ACCEPT_SAMPLE_TIME`,
		`SS`.`ERROR_REASON` AS `ERROR_REASON`,
		`SS`.`ERROR_DEAL_REMARK` AS `ERROR_DEAL_REMARK`
	FROM
		(
			(
				(
					`LIMS_ORDER_SUBSIDIARY_SAMPLE` `SS`
					LEFT JOIN `LIMS_ORDER` `O` ON ((`SS`.`ORDER_ID` = `O`.`ID`))
				)
				LEFT JOIN `LIMS_ORDER_EXAMINEE` `E` ON (
					(
						`SS`.`EXAMINEE_ID` = `E`.`ID`
					)
				)
				LEFT JOIN `LIMS_METADATA_SAMPLE` `MS` ON (
					(
						`SS`.`SAMPLE_TYPE_ID` = `MS`.`ID`
					)
				)
			)
			LEFT JOIN `LIMS_DICT` `D` ON (
				(
					(
						`D`.`CATEGORY` = 'FAMILY_RELATION'
					)
					AND (
						`D`.`DICT_VALUE` = `SS`.`FAMILY_RELATION`
					)
				)
			)
		)
	WHERE
		(`O`.`DEL_FLAG` = '0')
	UNION ALL
		SELECT
			`O`.`CODE` AS `ORDER_NO`,
			`O`.`ID` AS `ORDER_ID`,
			`O`.`ORDER_TYPE` AS `ORDER_TYPE`,
			`O`.`TESTING_STATUS` AS `ORDER_STATUS`,
			`O`.`OWNER_ID` AS `OWNER_ID`,
			NULL AS `EXAMINEE_ID`,
			NULL AS `EXAMINEE_NAME`,
			NULL AS `FAMILY_RELATION`,
			`RS`.`ID` AS `SAMPLE_ID`,
			`RS`.`SAMPLE_CODE` AS `SAMPLE_CODE`,
			`MS`.`NAME` AS `SAMPLE_TYPE`,
			`RS`.`SAMPLE_NAME` AS `SAMPLE_NAME`,
			`RS`.`SAMPLE_PACKAGE_NO` AS `SAMPLE_PACKAGE_NO`,
			`RS`.`SAMPLE_PACKAGE_STATUS` AS `SAMPLE_PACKAGE_STATUS`,
			`RS`.`SAMPLE_ERROR_STATUS` AS `SAMPLE_ERROR_STATUS`,
			`RS`.`SAMPLE_ERROR_NEW_NO` AS `SAMPLE_ERROR_NEW_NO`,
			3 AS `SAMPLE_TABLE_TYPE`,
			`RS`.`SAMPLE_BACK_STATUS` AS `SAMPLE_BACK_STATUS`,
			`RS`.`SAMPLE_BACK_OPTION` AS `SAMPLE_BACK_OPTION`,
			`RS`.`SAMPLE_BACK_NO` AS `SAMPLE_BACK_NO`,
			`RS`.`ACCEPT_SAMPLE_TIME` AS `ACCEPT_SAMPLE_TIME`,
			`RS`.`ERROR_REASON` AS `ERROR_REASON`,
			`RS`.`ERROR_DEAL_REMARK` AS `ERROR_DEAL_REMARK`
		FROM
			(
				(
					`LIMS_ORDER_RESEARCH_SAMPLE` `RS`
					LEFT JOIN `LIMS_ORDER` `O` ON ((`RS`.`ORDER_ID` = `O`.`ID`))
				)
				LEFT JOIN `LIMS_METADATA_SAMPLE` `MS` ON (
					(
						`RS`.`SAMPLE_TYPE_ID` = `MS`.`ID`
					)
				)
			)
		WHERE
			(`O`.`DEL_FLAG` = '0');
			
			
			
			
			
-- 宋健
DELETE FROM app_sample_transport_relation WHERE SAMPLE_ID ='613818dc9ff540c5aec4ff970a268b94' AND ID='170ce70a880b4293927a9155f9999369';
DELETE FROM app_sample_transport WHERE ID ='f89f2f5fa39b465fb0a9cf8c325b7b62';

DELETE FROM app_sample_transport_relation WHERE SAMPLE_ID ='65fdd91665a34a69948d358da73c35ff' AND ID='80a572866323412688cf441ec98b9fe7';
DELETE FROM app_sample_transport WHERE ID ='993898b8e410430e9c8b9642c009559a';

DELETE FROM app_sample_transport_relation WHERE SAMPLE_ID ='85caa0c156e74f40b084142fa68fb170' AND ID='2b30903cf95f4e078b96c58a8c56b208';
DELETE FROM app_sample_transport WHERE ID ='81f150fce41d471388a41509ead79686';

DELETE FROM app_sample_transport_relation WHERE SAMPLE_ID ='a0c18a2912214d6cb15626508623292c' AND ID='5f94bdda65614aebb007abefd97071c7';
DELETE FROM app_sample_transport WHERE ID ='993898b8e410430e9c8b9642c009559a';

-- 宋健 begin
ALTER TABLE LIMS_ORDER ADD COLUMN `PAY_TIME` DATETIME DEFAULT NULL COMMENT '支付时间';

ALTER TABLE `LIMS_SAMPLE_RECEIVING_DETAIL` ADD INDEX sample_record_index (`RECORD_ID`);

-- 初始化数据：http://localhost:8003/order/synchronizeOrderPayTime


ALTER TABLE LIMS_ORDER_PRIMARY_SAMPLE ADD COLUMN SYNCHRONIZED_STATUS TINYINT DEFAULT 0 COMMENT '是否同步过,0:未同步,1:已同步';
ALTER TABLE LIMS_ORDER_SUBSIDIARY_SAMPLE ADD COLUMN SYNCHRONIZED_STATUS TINYINT DEFAULT 0 COMMENT '是否同步过,0:未同步,1:已同步';
ALTER TABLE LIMS_ORDER_RESEARCH_SAMPLE ADD COLUMN SYNCHRONIZED_STATUS TINYINT DEFAULT 0 COMMENT '是否同步过,0:未同步,1:已同步';



DROP VIEW IF EXISTS `LIMS_ORDER_SAMPLE_VIEW`;
CREATE VIEW `LIMS_ORDER_SAMPLE_VIEW` (
	ID,
	ORDER_ID,
	SAMPLE_ID,
	SAMPLE_BTYPE,
	SAMPLE_TYPE,
	SAMPLE_CODE,
	SAMPLE_NAME,
	SAMPLE_SIZE,
	SAMPLE_PACKAGE_STATUS,
	SAMPLING_DATE,
	PURPOSE,
	TRANSPORT_STATUS,
	UPDATE_TIME,
	ERROR_DEAL_REMARK,
	SAMPLE_ERROR_STATUS,
	ERROR_REASON,
	ACCEPT_SAMPLE_TIME,
	SAMPLE_ERROR_NEW_NO,
	FAMILY_RELATION,
	SYNCHRONIZED_STATUS,
	OWNER_PHENOTYPE,
	OWNER_NAME,
	OWNER_AGE
) AS SELECT
	concat_ws('-', '1', `PS`.`ID`) AS `ID`,
	`PS`.`ORDER_ID` AS `ORDER_ID`,
	`PS`.`ID` AS `SAMPLE_ID`,
	1 AS `SAMPLE_BTYPE`,
	`PS`.`SAMPLE_TYPE_ID` AS `SAMPLE_TYPE`,
	`PS`.`SAMPLE_CODE` AS `SAMPLE_CODE`,
	`OE`.`NAME` AS `SAMPLE_NAME`,
	`PS`.`SAMPLE_SIZE` AS `SAMPLE_SIZE`,
	`PS`.`SAMPLE_PACKAGE_STATUS` AS `SAMPLE_PACKAGE_STATUS`,
	`PS`.`SAMPLING_DATE` AS `SAMPLING_DATE`,
	NULL AS `PURPOSE`,
	`PS`.`SAMPLE_PACKAGE_STATUS` AS `TRANSPORT_STATUS`,
	`PS`.`UPDATE_TIME` AS `UPDATE_TIME`,
	`PS`.`ERROR_DEAL_REMARK` AS `ERROR_DEAL_REMARK`,
	`PS`.`SAMPLE_ERROR_STATUS` AS `SAMPLE_ERROR_STATUS`,
	`PS`.`ERROR_REASON` AS `ERROR_REASON`,
	`PS`.`ACCEPT_SAMPLE_TIME` AS `ACCEPT_SAMPLE_TIME`,
	`PS`.`SAMPLE_ERROR_NEW_NO` AS `SAMPLE_ERROR_NEW_NO`,
	 NULL AS `FAMILY_RELATION`,
	 `PS`.`SYNCHRONIZED_STATUS` AS SYNCHRONIZED_STATUS,
	 NULL AS `OWNER_PHENOTYPE`,
	 NULL AS `OWNER_NAME`,
	 NULL AS `OWNER_AGE`
FROM
	(
		`LIMS_ORDER_PRIMARY_SAMPLE` `PS`
		LEFT JOIN `LIMS_ORDER_EXAMINEE` `OE` ON (
			(
				`PS`.`EXAMINEE_ID` = `OE`.`ID`
			)
		)
	)
UNION ALL
	SELECT
		concat_ws('-', '2', `SS`.`ID`) AS `ID`,
		`SS`.`ORDER_ID` AS `ORDER_ID`,
		`SS`.`ID` AS `ID`,
		2 AS `2`,
		`SS`.`SAMPLE_TYPE_ID` AS `SAMPLE_TYPE`,
		`SS`.`SAMPLE_CODE` AS `SAMPLE_CODE`,
		`SS`.`OWNER_NAME` AS `SAMPLE_NAME`,
		`SS`.`SAMPLE_SIZE` AS `SAMPLE_SIZE`,
		`SS`.`SAMPLE_PACKAGE_STATUS` AS `SAMPLE_PACKAGE_STATUS`,
		`SS`.`SAMPLING_DATE` AS `SAMPLING_DATE`,
		`SS`.`PURPOSE` AS `PURPOSE`,
		`SS`.`SAMPLE_PACKAGE_STATUS` AS `SAMPLE_PACKAGE_STATUS`,
		`SS`.`UPDATE_TIME` AS `UPDATE_TIME`,
		`SS`.`ERROR_DEAL_REMARK` AS `ERROR_DEAL_REMARK`,
		`SS`.`SAMPLE_ERROR_STATUS` AS `SAMPLE_ERROR_STATUS`,
		`SS`.`ERROR_REASON` AS `ERROR_REASON`,
		`SS`.`ACCEPT_SAMPLE_TIME` AS `ACCEPT_SAMPLE_TIME`,
		`SS`.`SAMPLE_ERROR_NEW_NO` AS `SAMPLE_ERROR_NEW_NO`,
	    `SS`.`FAMILY_RELATION` AS `FAMILY_RELATION`,
	    `SS`.`SYNCHRONIZED_STATUS` AS SYNCHRONIZED_STATUS,
	    `SS`.`OWNER_PHENOTYPE` AS OWNER_PHENOTYPE,
		`SS`.`OWNER_NAME` AS `OWNER_NAME`,
	    `SS`.`OWNER_AGE` AS `OWNER_AGE`
	FROM
		`LIMS_ORDER_SUBSIDIARY_SAMPLE` `SS`
	UNION ALL
		SELECT
			concat_ws('-', '3', `RS`.`ID`) AS `ID`,
			`RS`.`ORDER_ID` AS `ORDER_ID`,
			`RS`.`ID` AS `ID`,
			3 AS `3`,
			`RS`.`SAMPLE_TYPE_ID` AS `SAMPLE_TYPE`,
			`RS`.`SAMPLE_CODE` AS `SAMPLE_CODE`,
			`RS`.`SAMPLE_NAME` AS `SAMPLE_NAME`,
			`RS`.`SAMPLE_SIZE` AS `SAMPLE_SIZE`,
			`RS`.`SAMPLE_PACKAGE_STATUS` AS `SAMPLE_PACKAGE_STATUS`,
			`RS`.`SAMPLING_DATE` AS `SAMPLING_DATE`,
			NULL AS `NULL`,
			`RS`.`SAMPLE_PACKAGE_STATUS` AS `SAMPLE_PACKAGE_STATUS`,
			`RS`.`UPDATE_TIME` AS `UPDATE_TIME`,
			`RS`.`ERROR_DEAL_REMARK` AS `ERROR_DEAL_REMARK`,
			`RS`.`SAMPLE_ERROR_STATUS` AS `SAMPLE_ERROR_STATUS`,
			`RS`.`ERROR_REASON` AS `ERROR_REASON`,
			`RS`.`ACCEPT_SAMPLE_TIME` AS `ACCEPT_SAMPLE_TIME`,
			`RS`.`SAMPLE_ERROR_NEW_NO` AS `SAMPLE_ERROR_NEW_NO`,
			 NULL AS `FAMILY_RELATION`,
			 `RS`.`SYNCHRONIZED_STATUS` AS SYNCHRONIZED_STATUS,
	 		 NULL AS `OWNER_PHENOTYPE`,
			 NULL AS `OWNER_NAME`,
	         NULL AS `OWNER_AGE`
		FROM
			`LIMS_ORDER_RESEARCH_SAMPLE` `RS`;
			
DROP VIEW IF EXISTS `LIMS_ORDER_SAMPLE_DETAILS`;
CREATE VIEW `LIMS_ORDER_SAMPLE_DETAILS` (
	ID,
	ORDER_ID,
	ORDER_CODE,
	ORDER_TYPE,
	CUSTOMER_ID,	
	CUSTOMER_NAME,
	SALESMAN_ID,
	SALESMAN_NAME,
	SAMPLE_ID,
	SAMPLE_BTYPE,
	SAMPLE_TYPE_ID,
	SAMPLE_TYPE_NAME,
	SAMPLE_CODE,
	SAMPLE_NAME,
	SAMPLE_SIZE,
	SAMPLING_DATE,
	PURPOSE,
	TRANSPORT_STATUS,
	UPDATE_TIME,
	SAMPLE_ERROR_STATUS,
	ERROR_REASON,
	ACCEPT_SAMPLE_TIME,
	SAMPLE_ERROR_NEW_NO,
	ERROR_DEAL_REMARK,
	FAMILY_RELATION,
	SYNCHRONIZED_STATUS,
	OWNER_PHENOTYPE,
	OWNER_NAME,
	OWNER_AGE
) AS 			
			
SELECT
	`S`.`ID` AS `ID`,
	`S`.`ORDER_ID` AS `ORDER_ID`,
	`O`.`CODE` AS `ORDER_CODE`,
	`O`.`ORDER_TYPE` AS `ORDER_TYPE`,
	`U`.`ID` AS `CUSTOMER_ID`,	
	`U`.`REAL_NAME` AS `CUSTOMER_NAME`,
	`B`.`ID` AS `SALESMAN_ID`,
	`B`.`REAL_NAME` AS `SALESMAN_NAME`,
	`S`.`SAMPLE_ID` AS `SAMPLE_ID`,
	`S`.`SAMPLE_BTYPE` AS `SAMPLE_BTYPE`,
	`S`.`SAMPLE_TYPE` AS `SAMPLE_TYPE_ID`,
	`MS`.`NAME` AS `SAMPLE_TYPE_NAME`,
	`S`.`SAMPLE_CODE` AS `SAMPLE_CODE`,
	`S`.`SAMPLE_NAME` AS `SAMPLE_NAME`,
	`S`.`SAMPLE_SIZE` AS `SAMPLE_SIZE`,
	`S`.`SAMPLING_DATE` AS `SAMPLING_DATE`,
	`S`.`PURPOSE` AS `PURPOSE`,
	`S`.`TRANSPORT_STATUS` AS `TRANSPORT_STATUS`,
	`S`.`UPDATE_TIME` AS `UPDATE_TIME`,
	`S`.`SAMPLE_ERROR_STATUS` AS `SAMPLE_ERROR_STATUS`,
	`S`.`ERROR_REASON` AS `ERROR_REASON`,
	`S`.`ACCEPT_SAMPLE_TIME` AS `ACCEPT_SAMPLE_TIME`,
	`S`.`SAMPLE_ERROR_NEW_NO` AS `SAMPLE_ERROR_NEW_NO`,
	`S`.`ERROR_DEAL_REMARK` AS `ERROR_DEAL_REMARK`,
	`S`.`FAMILY_RELATION` AS `FAMILY_RELATION`,
	`S`.`SYNCHRONIZED_STATUS` AS `SYNCHRONIZED_STATUS`,
	`S`.`OWNER_PHENOTYPE` AS `OWNER_PHENOTYPE`,
	`S`.`OWNER_NAME` AS `OWNER_NAME`,
	`S`.`OWNER_AGE` AS `OWNER_AGE`
FROM
	(
		(
			(
				(
					`LIMS_ORDER_SAMPLE_VIEW` `S`
					LEFT JOIN `LIMS_METADATA_SAMPLE` `MS` ON (
						(
							`MS`.`ID` = `S`.`SAMPLE_TYPE`
						)
					)
				)
				LEFT JOIN `LIMS_ORDER` `O` ON ((`S`.`ORDER_ID` = `O`.`ID`))
			)
			LEFT JOIN `APP_USER_INFO` `U` ON ((`O`.`OWNER_ID` = `U`.`ID`))
		)
		LEFT JOIN `BUSINESS_INFO` `B` ON (
			(`O`.`CREATOR_ID` = `B`.`ID`)
		)
	)
	
-- 宋健 lims 新需求 待执行
CREATE TABLE `LIMS_BIOLOGY_DATA_TEMPLATE` (
`ID`  varchar(64)  NOT NULL COMMENT '主键' ,
`NAME`  varchar(128)  NOT NULL COMMENT '模板名称' ,
`DEL_FLAG`  tinyint(1) NOT NULL COMMENT '删除标记 0-未删除 1-已删除' ,
`PRI_FLAG`  tinyint(1) NULL DEFAULT NULL COMMENT '默认优先级 0-未默认 1-默认' ,
`CREATE_TIME`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`CREATE_ID`  varchar(64) DEFAULT NULL COMMENT '创建人id' ,
`CREATE_NAME`  varchar(64)  DEFAULT NULL COMMENT '创建人' ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci COMMENT='生信分析-数据模板-主表';

CREATE TABLE `LIMS_BIOLOGY_DATA_TEMPLATE_COLUMN` (
`ID`  varchar(64) NOT NULL COMMENT '主键' ,
`TEMPLATE_ID`  varchar(64)  NOT NULL COMMENT '模板ID' ,
`COLUMN_INDEX`  int(11)  DEFAULT NULL COMMENT '列索引' ,
`COLUMN_NAME`  varchar(128)  DEFAULT NULL COMMENT '列名描述' ,
`TYPE`  varchar(4)  DEFAULT NULL COMMENT '类型 1-复选，2-单选，3-区间' ,
`SEMANTIC`  varchar(64)  DEFAULT NULL COMMENT '特殊语义标识' ,
`GROUP_NAME`  varchar(64)  DEFAULT NULL COMMENT '分组名称' ,
PRIMARY KEY (`ID`),
INDEX `TEMPLATE_ID_INDEX` (`TEMPLATE_ID`) USING BTREE ,
INDEX `GROUP_NAME_INDEX` (`GROUP_NAME`) USING BTREE 
)
ENGINE=InnoDB DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci COMMENT='生信分析-数据模板-列定义';



CREATE TABLE `LIMS_CLAIM_TEMPLATE` (
`ID`  varchar(64)  NOT NULL COMMENT '主键' ,
`NAME`  varchar(128)  NOT NULL COMMENT '分析要求' ,
`EXPLANATION`  varchar(512) DEFAULT NULL COMMENT '解释' ,
`TEMPLATE_ID`  varchar(64)  NOT NULL COMMENT '数据模板id' ,
`DEL_FLAG`  tinyint(1) NOT NULL COMMENT '删除标记 0-未删除 1-已删除' ,
`CREATE_TIME`  datetime  DEFAULT NULL COMMENT '创建时间' ,
`CREATE_ID`  varchar(64)  DEFAULT NULL COMMENT '创建人id' ,
`CREATE_NAME`  varchar(64)  DEFAULT NULL COMMENT '创建人' ,
`PRI_FLAG`  tinyint(1)  DEFAULT NULL COMMENT '默认优先级 0-未默认 1-默认' ,
PRIMARY KEY (`ID`)
)ENGINE=InnoDB DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci COMMENT='分析要求配置-主表';

CREATE TABLE `LIMS_CLAIM_TEMPLATE_COLUMN` (
`ID`  varchar(64)  NOT NULL COMMENT '主键' ,
`TEMPLATE_ID`  varchar(64)  NOT NULL COMMENT '分析要求ID' ,
`DATA_COLUMN_ID`  varchar(64)  NOT NULL COMMENT '数据模板列ID' ,
`FILTER_NAME`  varchar(1024) DEFAULT NULL COMMENT '筛选项(id)' ,
`DEFAULT_VALUE`  varchar(1024)  DEFAULT NULL COMMENT '默认值(多选存id)' ,
`COLUMN_INDEX`  int(11)  DEFAULT NULL COMMENT '列索引' ,
PRIMARY KEY (`ID`)
)ENGINE=InnoDB DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci COMMENT='分析要求-列定义';

CREATE TABLE `LIMS_FILTER_ITEMS` (
`ID`  varchar(64)  NOT NULL COMMENT '主键' ,
`SEMANTIC`  varchar(64)  NOT NULL COMMENT '列(数据字典)' ,
`NAME`  varchar(64)  NOT NULL COMMENT '值' ,
`DEL_FLAG`  tinyint(1) NOT NULL COMMENT '删除标记 0-未删除 1-已删除' ,
PRIMARY KEY (`ID`)
)ENGINE=InnoDB DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci COMMENT='筛选项表';




INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('0412', '数据模板配置', '/biAnalysis/dataTemplate/list.do', '04', 'fa-life-ring', '12', '0');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('0412', '数据模板配置列表', '/biAnalysis/dataTemplate/list.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('0412-01', '数据模板配置新增', '/biAnalysis/dataTemplate/create.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('0412-02', '数据模板配置修改', '/biAnalysis/dataTemplate/update.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('0412-03', '数据模板配置删除', '/biAnalysis/dataTemplate/delete.do');
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('4-12', '4', '数据模板配置列表', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('4-12-01', '4-12', '数据模板配置新增', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('4-12-02', '4-12', '数据模板配置修改', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('4-12-03', '4-12', '数据模板配置删除', NULL);
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('4-12', '0412');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('4-12-01', '0412-01');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('4-12-02', '0412-02');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('4-12-03', '0412-03');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '4-12');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '4-12-01');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '4-12-02');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '4-12-03');



INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('0413', '分析内容配置', '/claimTemplate/list.do', '04', ' fa-cubes', '13', '0');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('0413', '分析内容配置列表', '/claimTemplate/list.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('0413-01', '分析内容配置新增', '/claimTemplate/create.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('0413-02', '分析内容配置修改', '/claimTemplate/update.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('0413-03', '分析内容板配置删除', '/claimTemplate/delete.do');
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('4-13', '4', '分析内容配置列表', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('4-13-01', '4-13', '分析内容配置新增', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('4-13-02', '4-13', '分析内容配置修改', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('4-13-03', '4-13', '分析内容配置删除', NULL);
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('4-13', '0413');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('4-13-01', '0413-01');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('4-13-02', '0413-02');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('4-13-03', '0413-03');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '4-13');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '4-13-01');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '4-13-02');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '4-13-03');

--
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b806c22c28bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'Mygeno_InterACMG', 'Mygeno_InterACMG', '2', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b81481dd28bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'Pathogenic_Analysis', 'Pathogenic_Analysis', '1', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b81ac67728bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', '标签', '标签', '3', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b821ff1b28bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', '遗传来源', '遗传来源', '4', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b8298d8328bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'Disease', 'Disease', '5', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b83227ba28bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'Inhert', 'Inhert', '6', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b83afe1d28bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'DiseasePhenotype', 'DiseasePhenotype', '7', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b843de6228bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'DiseaseInformation', 'DiseaseInformation', '8', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b84c894b28bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'ID', 'ID', '9', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b8560cdf28bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'Gene_Symbol', 'Gene_Symbol', '10', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b85edd1328bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'Ref_Transcript', 'Ref_Transcript', '11', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b86840fd28bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'Exon', 'Exon', '12', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b87159fc28bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'Nucleotide_Changes', 'Nucleotide_Changes', '13', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b879d27d28bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'Amino_Acid_Changes', 'Amino_Acid_Changes', '14', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b8827ebd28bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'EXOMISER', 'EXOMISER_GENE_COMBINED_SCORE', '15', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b88b9dc528bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'Phenolyzer', 'Phenolyzer_Score', '16', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b894382328bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'Total_Score', 'Total_Score', '17', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b89ce88a28bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'Gene_Type', 'Gene_Type', '18', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b8a604d428bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'Depth', 'Depth', '19', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b8afb36c28bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'MutRatio', 'MutRatio', '20', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b8b989ec28bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'Mutation_Type', 'Mutation_Type', '21', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b8c178f228bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'Effect', 'Effect', '22', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b8ca324e28bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', '1000g2015aug_all', '1000g2015aug_all', '23', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b8d2943228bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'ESP6500si', 'ESP6500si', '24', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b8dad20628bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'Inhouse', 'Inhouse', '25', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b8e349a328bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'ExAC_ALL', 'ExAC_ALL', '26', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b8eb853628bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'ExAC_EAS', 'ExAC_EAS', '27', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b8f4f86928bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'SIFT_Predict', 'SIFT_Predict', '28', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b8fd87d428bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'PolyPhen_2_Predict', 'PolyPhen_2_Predict', '29', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b906311128bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'MutationTaster_Predict', 'MutationTaster_Predict', '30', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b90ec8c128bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'GERP++_Predict', 'GERP++_Predict', '31', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b917502b28bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'MCAP_pred', 'MCAP_pred', '32', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b91fccea28bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'SPIDEX', 'SPIDEX', '33', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b928a43c28bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'dbsnp', 'dbsnp', '34', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b9309cbc28bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'Descr', 'Descr', '35', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b9394f5928bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'hgvs', 'hgvs', '36', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b941f0b528bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'report_disease', 'report_disease', '37', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b94a814628bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'Tag', 'Tag', '38', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b953357028bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'Publication', 'Publication', '39', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b95b816a28bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'clinvar', 'clinvar', '40', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b9644c4528bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'InterVar', 'InterVar', '41', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b96cb62028bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'MutInDatabase', 'MutInDatabase', '42', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b97590eb28bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'CytoBand', 'CytoBand', '43', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b97eb60828bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'dbNsfpInterPro', 'dbNsfpInterPro', '44', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b987e38c28bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'MutInNormal', 'MutInNormal', '45', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b991460428bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'AA_change', 'AA_change', '46', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b9992c8928bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'Highest-MAF', 'Highest-MAF', '47', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b9a1e40428bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'Gene_Region', 'Gene_Region', '48', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b9aa0b4328bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'gnomAD_exome_ALL', 'gnomAD_exome_ALL', '49', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b9b22c3428bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'gnomAD_exome_EAS', 'gnomAD_exome_EAS', '50', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b9bb333228bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'gnomAD_genome_ALL', 'gnomAD_genome_ALL', '51', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b9c46c2628bb11e88c880242ac110002', 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'gnomAD_genome_EAS', 'gnomAD_genome_EAS', '52', NULL, '0');
INSERT INTO `LIMS_DICT` (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `DICT_DESC`, `EDITABLE`) VALUES ('b9cd953b28bb11e88c880242ac110002', NULL, 'DATA_TEMPLATE', '数据模板语义标示', NULL, '44', NULL, '0');

INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('02c217796e464c3aa24d4382f64df2d2', '标签', '疑似多态', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('0316146d88d84bc5ae1b60735ec51d5d', '遗传来源', '父母', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('04a53bbfa6f34cc1b2b08a5cd57a5b89', 'MutInNormal', 'Y', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('0590471673244e7abb4a1e0df06a596c', 'Gene_Type', 'het', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('06b78a3401e94f5b912fb356424d0cf1', 'Effect', 'nonframeshift', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('0b28289b3ceb4197a7828fcea617750d', 'MCAP_pred', '#N/A', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('0e2cf9fcc2684f719bdea5ed01cf7e18', 'Pathogenic_Analysis', 'Likely benign', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('12f75662630f42058dd6ea45e694e55c', 'Mutation_Type', 'SNV', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('131d8f882f174d7dbbf714d9d8862a4e', 'Pathogenic_Analysis', 'Benign', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('1354e1c3d4974801ab79570e810e9c36', '标签', '潜在复合杂合', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('17ba21b68c29482eb13304cf8faca5dc', '标签', '复合杂合', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('18349a8ccf0e42078d29e2b55efd6c94', 'Tag', 'DM?', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('1eb5268feca74310b2ec887db383b570', 'Tag', 'DM', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('22113c220ef546ab936eae00f82a269b', 'Gene_Region', 'ncRNA_exonic', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('23328d49b96e46508a84803bb7e4a6ba', '标签', '杂合携带', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('265136b4263540fab0b16d58807e312c', 'MutationTaster_Predict', 'T', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('2c9d9900ce2049e9b32482edba31f906', 'Mutation_Type', 'Indel', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('31fe19e2080d4749bc487dd8b08784c1', 'MutInDatabase', 'Y', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('3a9ffeb61d6042aaa216706228f1ddc7', 'Effect', 'stopgain', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('3cc91f7dde7f4f6cbd846bf733db3088', 'MutationTaster_Predict', 'N', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('4365407b0f724ef38da6bc3a2ba98d6c', '标签', '非复合杂合', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('4ba08de9ca3d4f718988d4e3507ef284', '标签', '#N/A', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('4cb7958bf1854f93afc5b7abfbcbf73e', 'Tag', '#N/A', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('4d7f3bfc301f42ac8d690cb2b21865cc', 'MutationTaster_Predict', 'P', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('4dbd8fefbea845339c6029860d3619c1', 'Pathogenic_Analysis', 'Pathogenic', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('516c6106176b4d078f7ba199afdc4b97', '标签', '纯合变异', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('51e0a850614d49ab8d287ec92f735445', 'Pathogenic_Analysis', 'Likely pathogenic', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('5247780443b14cbc9ea2f3911a5435cf', 'Effect', 'splicing', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('52692ef8b931482098d890ed53eb39d9', 'SIFT_Predict', 'D', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('52eeeec6554b4578a768a15d4e431d35', 'MutationTaster_Predict', '#N/A', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('5668132692b04f5284939982e09800bb', 'MutInDatabase', 'N', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('5b0ec96d13a9435ba665eff21a534454', 'GERP++_Predict', 'T', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('5d31663fb13243bb93fdee0d3a07e736', 'MCAP_pred', 'D', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('66231beb5538437ab69ed3e3d22c98d3', 'Tag', 'FP', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('6f0705e1b2224a7885af936b80e2dc1b', '遗传来源', '自发', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('790a9287e21642f7a278630ce854d71d', 'Gene_Type', 'hemi', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('89407f0757014fb998cfb9e68376f8d3', 'Effect', 'unknown', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('91af1cdb190d4c0897858d3befe364a4', 'Tag', 'DPF', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('9215c3a67f3f468c8515422dc0bbc51f', 'MutationTaster_Predict', 'A', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('934d8fa39f16418eae25f61766ebc3ae', 'Gene_Type', 'hom', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('93cbe615722b4e469e8579e5d25d985b', 'PolyPhen_2_Predict', 'D', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('9e953e04c6824e7787e1c7179e9cb96c', 'PolyPhen_2_Predict', 'B', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('a53e960d121b4db4a0416d500573642b', '遗传来源', '未知', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('a598093a27f04fb19d62460cda7fc8e0', 'Gene_Region', 'intronic', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('a6393d1a5e4d414cb39e0efbb64bba8a', 'GERP++_Predict', 'D', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('ada3c20f6b2f4d67933d14508fdadd52', 'Gene_Region', 'splicing', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('b1695700b0804e6ab8d93591600b545b', 'Effect', 'stoploss', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('b31228bc9edc4105af2d382dff671124', 'MCAP_pred', 'T', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('b31717808c814aa39d89a13375ff6629', '标签', '稀有显性', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('bd3a05066e454145a509c296a5bb88a9', 'SIFT_Predict', '#N/A', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('bdb2af1848d047d883e63e48d6931c8b', '标签', '自发', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('beb16a99ff914c48b7260e48a3ea108a', 'Effect', 'frameshift', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('c35a04d9832e446e90c32e3d78a0237f', 'SIFT_Predict', 'T', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('cb73cd11076e44218a1d7863536377d8', 'GERP++_Predict', '#N/A', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('cb9d16dbeeeb47289a9d54761453d020', 'MutationTaster_Predict', 'D', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('cb9d81e013ae471182698352ab37b116', 'PolyPhen_2_Predict', '#N/A', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('da1e2510087e4641add09fb958068bdb', 'MutInNormal', 'N', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('e0aedcad95fd445eb4b2249093c17023', 'Effect', 'nonsynonymous', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('e1a2510bdb6e4383a0fadbcf23d66e5b', '遗传来源', '父亲', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('e2fe6f656ea14d5896ca65dadbb25497', 'Pathogenic_Analysis', 'Uncertain', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('e6e8d65cd5fc410283ebc19f63d175fb', 'PolyPhen_2_Predict', 'P', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('f1b93c0631ed427b88f6478d89e58c91', '标签', '高频显性', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('f28d4f7bfdc74f73a143d1616e5b6a6f', 'Tag', 'DP', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('f5dcb81883ca434ea6934f9f04927d5a', 'Effect', 'synonymous', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('f96af0f468e348449567d587f81d4719', 'Gene_Region', 'exonic', '0');
INSERT INTO LIMS_FILTER_ITEMS (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('f9f333b6fe1f4e5bbb1ed3cd45ca2d76', '遗传来源', '母亲', '0');


INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('34d90ac904d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'A1', 'Sample', '1', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('34dc883604d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'A2', 'Chr', '2', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('34e0909704d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'A3', 'Begin', '3', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('34e498a404d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'A4', 'End', '4', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('34e80c8b04d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'A5', 'Ref', '5', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('34ebb3f204d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'A6', 'Alt', '6', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('34ef285504d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'B1', 'Pathogenic_Analysis', '7', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('34f2f92104d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'B2', 'Mygeno_InterACMG', '8', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('34f6924004d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'B3', '标签', '9', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('34fa485604d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'B4', '遗传来源', '10', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('34ff2b3f04d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'B4A', '之父基因型', '11', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('3504fdca04d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'B4B', '之父测序深度', '12', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('350941c904d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'B4C', '之母基因型', '13', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('350d8d7204d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'B4D', '之母测序深度', '14', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('3511625e04d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'B5', 'Inhert', '15', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('3515731a04d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'B6', 'Disease', '16', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('3519a46804d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'B7', 'DiseasePhenotype', '17', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('351dee4604d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'B8', 'DiseaseInformation', '18', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('3521d7ea04d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'C1', 'ID', '19', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('35256a6c04d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'C2', 'Gene_Symbol', '20', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('35293abe04d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'C3', 'AA_change', '21', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('352cf99104d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'C3A', 'Ref_Transcript', '22', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('3531144a04d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'C3B', 'Exon', '23', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('3534d0aa04d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'C3C', 'Nucleotide_Changes', '24', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('3538563c04d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'C3D', 'Amino_Acid_Changes', '25', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('353ba41404d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'C3E', 'AA_change', '26', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('353f343904d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'C4', 'Total_Score', '27', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('354322d104d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'C4A', 'EXOMISER_GENE_COMBINED_SCORE', '28', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('3546cc6504d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'C4B', 'Phenolyzer_Score', '29', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('354a819604d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'C4C', 'Total_Score', '30', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('354e2c3204d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'D1', 'Gene_Type', '31', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('355205b404d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'D2', 'Depth', '32', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('3555d15b04d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'D3', 'MutRatio', '33', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('3559d3cf04d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'D4', 'Mutation_Type', '34', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('355d234104d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'D5', 'Effect', '35', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('3561222a04d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'D6', 'Gene_Region', '36', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('35647dcf04d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E1', 'Highest-MAF', '37', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('356876ac04d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E1A', '1000g2015aug_all', '38', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('356c112104d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E1B', 'ESP6500si', '39', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('356fdb3504d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E1C', 'Inhouse', '40', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('3574001304d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E1D', 'ExAC_ALL', '41', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('3578f75104d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E1E', 'ExAC_EAS', '42', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('357d44a804d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E1F', 'gnomAD_exome_ALL', '43', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('3581118004d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E1G', 'gnomAD_exome_EAS', '44', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('3585241c04d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E1H', 'gnomAD_genome_ALL', '45', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('35892b9e04d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E1I', 'gnomAD_genome_EAS', '46', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('358ce44a04d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E2', 'REVEL_score', '47', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('35906d7c04d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E2A', 'SIFT', '48', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('3594538704d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E2B', 'SIFT_Predict', '49', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('35983bb704d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E2C', 'PolyPhen_2', '50', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('359c0b7404d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E2D', 'PolyPhen_2_Predict', '51', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('35a0351e04d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E2E', 'MutationTaster', '52', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('35a4039704d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E2F', 'MutationTaster_Predict', '53', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('35a7eaf504d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E2G', 'GERP++', '54', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('35ab8d8304d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E2H', 'GERP++_Predict', '55', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('35af48ff04d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E2I', 'SPIDEX', '56', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('35b3080304d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E2J', 'MCAP_score', '57', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('35b6d2ad04d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E2K', 'MCAP_pred', '58', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('35ba4d5504d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E3', 'Tag', '59', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('35bdb2d204d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E3A', 'Descr', '60', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('35c1491b04d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E3B', 'hgvs', '61', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('35c51b6004d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E3C', 'report_disease', '62', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('35c8631d04d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E3D', 'Publication', '63', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('35cb6e6704d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E4', 'clinvar', '64', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('35cf694704d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E5', 'InterVar', '65', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('35d2b8d604d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'E6', 'MutInDatabase', '66', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('35d65b0904d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'F1', 'dbsnp', '67', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('35d974be04d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'F2', 'CytoBand', '68', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('35dd606604d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'F3', 'dbNsfpInterPro', '69', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('35e0d61004d711e88c880242ac110002', 'edda44bd025e11e88c880242ac110002', 'JSON_KEY', 'F4', 'MutInNormal', '70', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('edda44bd025e11e88c880242ac110002', NULL, 'JSON_KEY', 'json key对应excel列头', NULL, '3', '0');

ALTER TABLE LIMS_TESTING_REPORT add COLUMN SYNCHRONIZED_STATUS TINYINT DEFAULT 0  COMMENT '同步状态,0:未同步，1 已同步';
ALTER table LIMS_CLAIM_TEMPLATE add COLUMN SYMBOL VARCHAR(64) DEFAULT null COMMENT '名称，eg:	Regioncount,CNV,SV,SNP_INDEL';



-- 注释数据表  part2 待执行
CREATE TABLE `BIOLOGY_INFO_ANNOTATE` (
`ID`  varchar(64)  NOT NULL ,
`DATA_ID`  varchar(64)   DEFAULT '' COMMENT '数据样本ID' ,
`DATA_CODE`  varchar(64)  NOT NULL DEFAULT '' COMMENT '数据样本code' ,
`TASK_ID`  varchar(64)  DEFAULT NULL COMMENT '接口传回来的id' ,
`ANNOTATE_STATUS`  tinyint(1)  DEFAULT NULL COMMENT '注释状态 1.过滤，2.比对，3.call突变，4.注释，5.结束，6.出现错误，7.打分中' ,
`QUALIFY`  tinyint(1)  DEFAULT NULL ,
`CNV`  varchar(1024)  DEFAULT NULL ,
`SV`  varchar(1024)  DEFAULT NULL ,
`MAKE_CPTY_NUMBER_VARIATION` varchar(1024) DEFAULT NULL ,
`REGIONCOUNT_DMDEXON`  varchar(1024)  DEFAULT NULL ,
`REGIONCOUNT_DMDV2`  varchar(1024) DEFAULT NULL ,
`STATISTICS_DMDEXON`  varchar(1024)  DEFAULT NULL ,
`STATISTICS_DMDV2`  varchar(1024)  DEFAULT NULL ,
`VCF_DMDEXON`  varchar(1024)  DEFAULT NULL ,
`VCF_DMDV2`  varchar(1024)  DEFAULT NULL ,
`BAM_BAM`  varchar(1024)  DEFAULT NULL ,
`BAM_BAI`  varchar(1024)  DEFAULT NULL ,
`CREATE_TIME`  datetime  DEFAULT NULL ,
`CREATOR_NAME`  varchar(64)  DEFAULT NULL ,
`UPDATE_TIME`  datetime  DEFAULT NULL ,
`UPDATOR_NAME`  varchar(64)  DEFAULT NULL ,
`SNV_JSON`  varchar(255)  DEFAULT NULL ,
`SV_JSON`  varchar(255) DEFAULT NULL ,
`UPLOAD_DESC`  varchar(255)  DEFAULT NULL COMMENT '注释文件上传详情' ,
`STATISTICS_STATE`  tinyint(1)  DEFAULT 0 COMMENT '质控状态0、合格 1、不合格' ,
PRIMARY KEY (`ID`)
)ENGINE=InnoDB DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci ROW_FORMAT=DYNAMIC;



INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('031401', '数据复核', '/testing/technical_recheck_list.do', '03', 'fa-code-fork', '14', '0');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('0314', '数据复核列表', '/testing/technical_recheck_list.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('0314-01', '数据复核处理', '/testing/technical_recheck_handle.do');
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('3-14', '3', '数据复核列表', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('3-14-01', '3-14', '数据复核处理', NULL);
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('3-14', '0314');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('3-14-01', '0314-01');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '3-14');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '3-14-01');


CREATE TABLE `BIOLOGY_INFO_ANNOTATE_HPO_MONITOR` (
`ANNOTATE_ID`  varchar(64)  NOT NULL ,
`MONITOR_TASK_ID`  varchar(255)  NOT NULL ,
`STATUS`  tinyint(1) NOT NULL COMMENT '0 - 打分中 1 - 打分完成' ,
`CREATE_TIME`  datetime NOT NULL ,
`END_TIME`  datetime NULL DEFAULT NULL 
)ENGINE=InnoDB DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci;



INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES ('d1fec93d447711e88c880242ac110002', NULL, 'INTERACMG', '致病性分级', '', NULL, '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'BP1', 'BP1', '21', '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'BP2', 'BP2', '22', '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'BP3', 'BP3', '23', '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'BP4', 'BP4', '24', '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'BP5', 'BP5', '25', '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'BP6', 'BP6', '26', '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'BP7', 'BP7', '27', '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'BA1', 'BA1', '28', '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'PVS', 'PVS', '1', '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'PS1', 'PS1', '2', '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'PM6', 'PM6', '11', '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'PP2', 'PP2', '13', '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'PP3', 'PP3', '14', '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'PP4', 'PP4', '15', '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'PP5', 'PP5', '16', '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'BS1', 'BS1', '17', '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'BS2', 'BS2', '18', '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'BS3', 'BS3', '19', '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'BS4', 'BS4', '20', '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'PP1', 'PP1', '12', '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'PS2', 'PS2', '3', '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'PS3', 'PS3', '4', '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'PS4', 'PS4', '5', '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'PM1', 'PM1', '6', '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'PM2', 'PM2', '7', '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'PM3', 'PM3', '8', '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'PM4', 'PM4', '9', '1');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'd1fec93d447711e88c880242ac110002', 'INTERACMG', 'PM5', 'PM5', '10', '1');


--

UPDATE LIMS_DICT set DICT_TEXT='>=4X的覆盖度',DICT_VALUE='4' WHERE id='1b3294d92d9d11e88c880242ac110002';
UPDATE LIMS_DICT set DICT_TEXT='>=10X的覆盖度',DICT_VALUE='10' WHERE id='2ca51c822d9d11e88c880242ac110002';
UPDATE LIMS_DICT set DICT_TEXT='>=20X的覆盖度',DICT_VALUE='20' WHERE id='3a297c4d2d9d11e88c880242ac110002';
UPDATE LIMS_DICT set DICT_TEXT='>=50X的覆盖度',DICT_VALUE='50' WHERE id='4bf92f882d9d11e88c880242ac110002';
UPDATE LIMS_DICT set DICT_TEXT='>=100X的覆盖度',DICT_VALUE='100' WHERE id='5b796d392d9d11e88c880242ac110002';




INSERT INTO LIMS_BIOLOGY_DATA_TEMPLATE_COLUMN (`ID`, `TEMPLATE_ID`, `COLUMN_INDEX`, `COLUMN_NAME`, `TYPE`, `SEMANTIC`, `GROUP_NAME`) VALUES (REPLACE(UUID(),'-',''), '394f82c6e35746318cb846af1e2a8459', '24', 'Chr', '2', 'Chr', 'Chr');
INSERT INTO LIMS_BIOLOGY_DATA_TEMPLATE_COLUMN (`ID`, `TEMPLATE_ID`, `COLUMN_INDEX`, `COLUMN_NAME`, `TYPE`, `SEMANTIC`, `GROUP_NAME`) VALUES (REPLACE(UUID(),'-',''), '394f82c6e35746318cb846af1e2a8459', '25', 'Begin', '2', 'Begin', 'Begin');
INSERT INTO LIMS_BIOLOGY_DATA_TEMPLATE_COLUMN (`ID`, `TEMPLATE_ID`, `COLUMN_INDEX`, `COLUMN_NAME`, `TYPE`, `SEMANTIC`, `GROUP_NAME`) VALUES (REPLACE(UUID(),'-',''), '394f82c6e35746318cb846af1e2a8459', '26', 'End', '2', 'End', 'End');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'Chr', 'Chr', '53', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'Begin', 'Begin', '54', '0');
INSERT INTO LIMS_DICT (`ID`, `PARENT_ID`, `CATEGORY`, `DICT_TEXT`, `DICT_VALUE`, `SORT`, `EDITABLE`) VALUES (REPLACE(UUID(),'-',''), 'b9cd953b28bb11e88c880242ac110002', 'DATA_TEMPLATE', 'End', 'End', '55', '0');


UPDATE LIMS_CLAIM_TEMPLATE SET `NAME`='SV分析' , `EXPLANATION`=''  WHERE ID='f833a3f4a3ea4362a201dbf2ab34bd8a';
UPDATE LIMS_CLAIM_TEMPLATE SET `NAME`='CapCNV分析', `EXPLANATION`=''  WHERE ID='f79c108d6e344f298667c51bd6e90e0b';
UPDATE LIMS_CLAIM_TEMPLATE SET `NAME`='Regioncount分析', `EXPLANATION`=''  WHERE ID='431fb8a4e6e3434783acc5ffaaac22c3';


--- 付款待确认表 增加 王府井支付字段   sj
alter table LIMS_ORDER_PAYMENT_CONFIRM  add COLUMN MER_NUM VARCHAR(64) DEFAULT NULL COMMENT '商户号';
alter table LIMS_ORDER_PAYMENT_CONFIRM  add COLUMN TERM_ID VARCHAR(64) DEFAULT NULL COMMENT '终端号';
alter table LIMS_ORDER_PAYMENT_CONFIRM  add COLUMN TRAN_DATE VARCHAR(64) DEFAULT NULL COMMENT '交易日期';
alter table LIMS_ORDER_PAYMENT_CONFIRM  add COLUMN REFER_NO VARCHAR(64) DEFAULT NULL COMMENT '交易参考号';
alter table LIMS_ORDER_PAYMENT_CONFIRM  add COLUMN BATCH_NO VARCHAR(64) DEFAULT NULL COMMENT '批次号';
alter table LIMS_ORDER_PAYMENT_CONFIRM  add COLUMN SERIAL_NO VARCHAR(64) DEFAULT NULL COMMENT '流水号';
alter table LIMS_ORDER_PAYMENT_CONFIRM  add COLUMN O_BATCHNO varchar(64) DEFAULT NULL COMMENT '原批次号';
alter table LIMS_ORDER_PAYMENT_CONFIRM  add COLUMN O_SERIALNO VARCHAR(64) DEFAULT NULL COMMENT '原流水号';
alter table LIMS_ORDER_PAYMENT_CONFIRM  add COLUMN TRAN_TYPE VARCHAR(64) DEFAULT NULL COMMENT '交易类型';
alter table LIMS_ORDER_PAYMENT_CONFIRM  add COLUMN WANG_PAY_TYPE VARCHAR(64) DEFAULT NULL COMMENT '支付类型:1 刷卡4 微信5 支付宝6 银联二维码8 分期消费9 积分刷卡 A 外卡';
alter table LIMS_ORDER_PAYMENT_CONFIRM  add COLUMN ORDER_CODE varchar(64) DEFAULT NULL COMMENT '订单编号';
alter table LIMS_ORDER_PAYMENT_CONFIRM  add COLUMN EXT1 VARCHAR(64) DEFAULT NULL COMMENT '预留字段1';
alter table LIMS_ORDER_PAYMENT_CONFIRM  add COLUMN EXT2 varchar(64) DEFAULT NULL COMMENT '预留字段2';
alter table LIMS_ORDER_PAYMENT_CONFIRM  add COLUMN ADD_VAL varchar(500) DEFAULT NULL COMMENT '交易附加值';
alter table LIMS_ORDER_PAYMENT_CONFIRM  add COLUMN RECONCILIATION_STATUS tinyint(1)  COMMENT '对账状态 1 正确  , 0错误';

alter table LIMS_ORDER_PAYMENT_CONFIRM add unique index(TRAN_DATE,TRAN_TYPE,REFER_NO);


-- 对账明细记录表
CREATE TABLE `LIMS_ORDER_ACCOUNT_STATE_RECORD` (
`ID`  varchar(64)  NOT NULL ,
`SEQ_NO`   varchar(64)   DEFAULT '' COMMENT '序号' ,
`MER_NUM`  varchar(64)  DEFAULT ''  COMMENT '商户号' ,
`MER_NAME` varchar(64) DEFAULT ''   COMMENT '商户名' ,
`TERM_ID`  varchar(64)  DEFAULT ''  COMMENT '终端号' ,
`SETTLEMENT_DATE`  varchar(64)  DEFAULT NULL COMMENT '结算日期' ,
`TRANSACTION_DATE` varchar(64)  DEFAULT NULL COMMENT '交易日期',
`TRADING_TIME`  varchar(64)  DEFAULT NULL COMMENT '交易时间' ,
`ORDER_ID`  varchar(64)  DEFAULT NULL COMMENT '订单号' ,
`TRADING_TYPE`  varchar(64)  DEFAULT '' COMMENT '交易类型' ,
`TRADING_AMOUNT` decimal(18,2)  DEFAULT NULL COMMENT '交易金额',
`SERVICE_CHARGE` decimal(18,2)  DEFAULT NULL COMMENT '手续费' ,
`ENTER_AMOUNT`  decimal(18,2)  DEFAULT NULL COMMENT '入账金额' ,
`CARD_NUMBER` varchar(64)  DEFAULT NULL COMMENT '卡号',
`CARD_DOMAIN`  varchar(64)  DEFAULT NULL COMMENT '卡域' ,
`CARD_SPECIES`  varchar(64)  DEFAULT NULL COMMENT '卡种' ,
`REFER_NO`  varchar(64)  DEFAULT '' COMMENT '交易参考号' ,
`O_REFER_NO` varchar(64)  DEFAULT NULL COMMENT '原交易参考号',
`O_TRADING_TIME`  varchar(64)  DEFAULT NULL COMMENT '原交易时间' ,
`PAY_TYPE` varchar(64)  DEFAULT NULL COMMENT '支付方式',
`BANK_CODE`  varchar(64)  DEFAULT NULL COMMENT '银行代码' ,
PRIMARY KEY (`ID`)
)ENGINE=InnoDB DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci ROW_FORMAT=DYNAMIC;

alter table LIMS_ORDER_ACCOUNT_STATE_RECORD add unique index(MER_NUM,TERM_ID,REFER_NO,TRADING_TYPE);
ALTER TABLE `LIMS_ORDER_ACCOUNT_STATE_RECORD` ADD INDEX index_order_id ( `ORDER_ID`);

-- 自动对账任务表
drop table if exists LIMS_ORDER_ACCOUNT_CHECK_TASK;
CREATE TABLE `LIMS_ORDER_ACCOUNT_CHECK_TASK` (
`ID`  varchar(64)  NOT NULL ,
`TRADING_DATE`  varchar(64)  DEFAULT NULL COMMENT '交易日期' ,
`INTERFACE_CODE`  tinyint(1)  COMMENT '对账渠道，7：王府井' ,
`RECONCILIATION_DATE`  varchar(64)  DEFAULT NULL COMMENT '对账日期' ,
`RECONCILIATION_RESULT` tinyint(1) NULL COMMENT '对账结果',
`SETTLE_STATUS`  tinyint(1)  COMMENT '是否结账 ' ,
`RECONCILIATION_STATUS` tinyint(1)  COMMENT '是否对账' ,
`SOLVE_RESULT` tinyint(1)  COMMENT '处理结果',
PRIMARY KEY (`ID`)
)ENGINE=InnoDB DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci ROW_FORMAT=DYNAMIC;

-- 自动对账差表
drop table if exists LIMS_ORDER_ACCOUNT_CHECK_MISTAKE;
CREATE TABLE LIMS_ORDER_ACCOUNT_CHECK_MISTAKE
(
   `ID`                    VARCHAR(64) NOT NULL,
   `ACCOUNT_CHECK_TASK_ID` VARCHAR(50) NOT NULL,
   `BILL_DATE`             VARCHAR(64) NOT NULL,
   `BANK_TYPE`             VARCHAR(30) NOT NULL,
   `ORDER_NO`              VARCHAR(40),
   `TRADE_TIME`            VARCHAR(64),
   `TRX_NO`                VARCHAR(20),
   `ORDER_AMOUNT`          DECIMAL(20,2),
   `FEE`                   DECIMAL(20,2),
   `BANK_TRADE_TIME`       VARCHAR(64),
   `BANK_ORDER_NO`         VARCHAR(40),
   `BANK_TRX_NO`           VARCHAR(40),
   `BANK_AMOUNT`           DECIMAL(20,2),
   `BANK_FEE`              DECIMAL(20,2),
   `ERR_TYPE`              VARCHAR(30) NOT NULL,
   `HANDLE_STATUS`         VARCHAR(10) NOT NULL,
   `HANDLE_VALUE`          VARCHAR(1000),
   `HANDLE_REMARK`         VARCHAR(1000),
   `OPERATOR_NAME`         VARCHAR(100),
   `CREATE_TIME`           DATETIME NOT NULL,
   PRIMARY KEY (ID)
)ENGINE=INNODB DEFAULT CHARACTER SET=UTF8 COLLATE=UTF8_GENERAL_CI ROW_FORMAT=DYNAMIC;


-- 自动对账差错暂存池
drop table if exists LIMS_ORDER_ACCOUNT_CHECK_MISTAKE_SCRATCH_POOL;
CREATE TABLE `LIMS_ORDER_ACCOUNT_CHECK_MISTAKE_SCRATCH_POOL` (
  `ID`  VARCHAR(64)      NOT NULL ,
  `CREATE_TIME`          DATETIME NOT NULL,
  `PAYMENT_TIME`          DATETIME COMMENT '支付时间',
  `CHECK_TASK_ID`        VARCHAR(64)  COMMENT '对账任务Id',
  `ORDER_NO`    		 VARCHAR(64)  COMMENT '商户订单号',
  `REFER_NO`             VARCHAR(64)  COMMENT '支付流水号',
  `CHECK_AMOUNT`         DECIMAL(20,2) DEFAULT 0 COMMENT '订单金额',
  `PAY_TYPE`             TINYINT(1)  COMMENT '支付通道编号',
  `BATCH_NO`             VARCHAR(64) DEFAULT NULL ,
  `BILL_DATE`            VARCHAR(64) DEFAULT NULL ,
  `MER_NUM`          	 VARCHAR(64) DEFAULT NULL COMMENT '商户号',
  `TERM_ID`    		     VARCHAR(64)  COMMENT '终端号',
  `TRAN_DATE`            VARCHAR(64) DEFAULT NULL COMMENT '交易日期',
  `TRAN_TYPE`            VARCHAR(64) DEFAULT NULL COMMENT '交易类型',
  `O_SERIALNO`           VARCHAR(64) COMMENT '原流水号',
  `O_BATCHNO`            VARCHAR(64) COMMENT '原批次号',
  `SERIAL_NO`            VARCHAR(64) COMMENT '流水号' ,
  `WANG_PAY_TYPE`        VARCHAR(64) DEFAULT NULL COMMENT '支付类型:1 刷卡4 微信5 支付宝6 银联二维码8 分期消费9 积分刷卡 A 外卡',
  `EXT1`                 VARCHAR(64) DEFAULT NULL COMMENT '预留字段1',
  `EXT2`                 VARCHAR(64) DEFAULT NULL COMMENT '预留字段2',
  `ADD_VAL`              VARCHAR(500) DEFAULT NULL  COMMENT '交易附加值' ,
  PRIMARY KEY (`ID`)
)ENGINE=InnoDB DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci ROW_FORMAT=DYNAMIC;

update LIMS_MENU set NAME ='王府井对账' where id='1312' ;
update LIMS_RESOURCE set NAME ='王府井对账' where id='1312' ;
update LIMS_RESOURCE set NAME ='王府井对账处理' where id='1312-01' ;
update LIMS_AUTHORITY set NAME ='王府井对账' where id='12-12' ;
update LIMS_AUTHORITY set NAME ='王府井对账处理' where id='12-12-01' ;

INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('1312', '系统对账', '/reconciliation/task_list.do', '13', 'fa-code-fork', '15', '0');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('1312', '系统对账列表', '/reconciliation/task_list.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('1312-01', '系统对账处理', '/reconciliation/task_handle.do');
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('12-12', '12-', '系统对账', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('12-12-01', '12-12', '系统对账处理', NULL);
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('12-12', '1312');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('12-12-01', '1312-01');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '12-12');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '12-12-01');

--bug
update LIMS_CLAIM_TEMPLATE set EXPLANATION ='' ;

INSERT INTO `LIMS_FILTER_ITEMS` (`ID`, `SEMANTIC`, `NAME`, `DEL_FLAG`) VALUES ('oasdh09288ihqnajbdn9o213213', 'Gene_Region', 'UTR3', '0');



alter table LIMS_ORDER_ACCOUNT_CHECK_MISTAKE  add COLUMN PAYMENT_FULL_TIME DATETIME COMMENT '平台支付时间';
alter table LIMS_ORDER_ACCOUNT_CHECK_MISTAKE  add COLUMN BANK_PAYMENT_FULL_TIME DATETIME COMMENT '银行支付时间';






--- 云分析平台
ALTER table ganalysis_division_data_synchronize_record add column RECORD_TYPE TINYINT DEFAULT 0 COMMENT "0:拆分;1:注释";
--- lims
ALTER table BIOLOGY_INFO_ANNOTATE_HPO_MONITOR add column SAMPLE_TEST_ID varchar(64) DEFAULT '' COMMENT "对应的样本实验id" after ANNOTATE_ID;

--lims
ALTER TABLE LIMS_TESTING_TECHNICAL_ANALY_RECORD ADD INDEX INDEX_OBJECT_ID ( MUTATION_OBJECT_ID );
ALTER TABLE LIMS_TESTING_TECHNICAL_ANALY_RECORD ADD INDEX IDX_ANALYSIS_ID ( TECHNICAL_FAMILY_GROUP_ID );

--mogodb
db.getCollection('analysis-mutation').ensureIndex({analysisSampleId:1} );
db.getCollection('analysis-mutation').ensureIndex({pa_sort:1} );  

--注释数据同步状态标志位
ALTER TABLE BIOLOGY_INFO_ANNOTATE add COLUMN SYNCHRONIZED_STATUS TINYINT DEFAULT 0  COMMENT '同步状态,0:未同步，1 已同步';

--TECHNICAL_ANALYSIS_SHEET表
drop table if exists TECHNICAL_ANALYSIS_SHEET;
CREATE TABLE `TECHNICAL_ANALYSIS_SHEET` (
  `ID` varchar(64) NOT NULL,
  `CODE` varchar(64) DEFAULT NULL COMMENT '任务单号',
  `TASK_ID` varchar(64) DEFAULT NULL,
  `TESTER_ID` varchar(64) DEFAULT NULL COMMENT '操作人id',
  `TESTER_NAME` varchar(64) DEFAULT NULL COMMENT '操作人姓名',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- new
ALTER table LIMS_ORDER_REFUND ADD COLUMN CANCEL_PRODUCT  TINYINT DEFAULT 0  COMMENT '是否取消产品,0:不取消，1 取消';
ALTER table LIMS_ORDER_REFUND ADD COLUMN CANCEL_PRODUCT_REMARK  VARCHAR(1000) DEFAULT NULL  COMMENT '取消产品备注';

drop table if exists LIMS_PRODUCT_CANCEL_RECORD;
CREATE TABLE `LIMS_PRODUCT_CANCEL_RECORD` (
  `ID` varchar(64) NOT NULL,
  `ORDER_REFUND_ID` varchar(64) DEFAULT NULL COMMENT '退款申请ID',
  `ORDER_ID` varchar(64) DEFAULT NULL COMMENT '订单ID',
  `ORDER_CODE` varchar(64) DEFAULT NULL COMMENT '订单编号',
  `PRODUCT_ID` varchar(64) DEFAULT NULL COMMENT '产品ID',
  `PRODUCT_CODE` varchar(64) DEFAULT NULL COMMENT '产品编号',
  `ORDER_TYPE` tinyint DEFAULT NULL COMMENT '营销中心',
  `EXAMINEE_NAME` varchar(64) DEFAULT NULL COMMENT '受检人',
  `CUSTOMER_NAME` varchar(64) DEFAULT NULL COMMENT '客户',
  `SALESMAN_NAME` varchar(64) DEFAULT NULL COMMENT '业务员',
  `PRODUCT_STATUS` tinyint DEFAULT 0 COMMENT '订单产品状态',
  `STATUS` tinyint DEFAULT 0 COMMENT '处理状态',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '处理时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `LIMS_MENU` (`ID`, `NAME`, `URI`, `PARENT_ID`, `ICON`, `SORT`, `STATUS`) VALUES ('0219', '检测取消确认', '/order/app/cancelSechedulePaging.do', '02', 'fa-code-fork', '12', '0');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('021901', '检测取消确认列表', '/order/app/cancelSechedulePaging.do');
INSERT INTO `LIMS_RESOURCE` (`ID`, `NAME`, `URI`) VALUES ('021902', '检测取消确认处理', '/order/cancelSheduleByOrderProduct.do');
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('021901', '02', '检测取消确认列表', NULL);
INSERT INTO `LIMS_AUTHORITY` (`ID`, `PARENT_ID`, `NAME`, `SORT`) VALUES ('021902', '02', '检测取消确认处理', NULL);
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('021901', '021901');
INSERT INTO `LIMS_AUTHORITY_RESOURCE` (`AUTHORITY_ID`, `RESOURCE_ID`) VALUES ('021902', '021902');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '021901');
INSERT INTO `LIMS_ROLE_AUTHORITY` (`ROLE_ID`, `AUTHORITY_ID`) VALUES ('f5c6ad89615a4b44a971dc85d60f481c', '021902');

update LIMS_BIOLOGY_DATA_TEMPLATE_COLUMN SET COLUMN_INDEX = COLUMN_INDEX+4 WHERE COLUMN_INDEX>=12;

INSERT INTO `LIMS_BIOLOGY_DATA_TEMPLATE_COLUMN` (`ID`, `TEMPLATE_ID`, `COLUMN_INDEX`, `COLUMN_NAME`, `TYPE`, `SEMANTIC`, `GROUP_NAME`) VALUES (REPLACE(UUID(),'-',''), '394f82c6e35746318cb846af1e2a8459', '12', '之父测序深度', '2', '之父测序深度', '之父测序深度');
INSERT INTO `LIMS_BIOLOGY_DATA_TEMPLATE_COLUMN` (`ID`, `TEMPLATE_ID`, `COLUMN_INDEX`, `COLUMN_NAME`, `TYPE`, `SEMANTIC`, `GROUP_NAME`) VALUES (REPLACE(UUID(),'-',''), '394f82c6e35746318cb846af1e2a8459', '13', '之母测序深度', '2', '之母测序深度', '之母测序深度');
INSERT INTO `LIMS_BIOLOGY_DATA_TEMPLATE_COLUMN` (`ID`, `TEMPLATE_ID`, `COLUMN_INDEX`, `COLUMN_NAME`, `TYPE`, `SEMANTIC`, `GROUP_NAME`) VALUES (REPLACE(UUID(),'-',''), '394f82c6e35746318cb846af1e2a8459', '14', '之母基因型', '2', '之母基因型', '之母基因型');
INSERT INTO `LIMS_BIOLOGY_DATA_TEMPLATE_COLUMN` (`ID`, `TEMPLATE_ID`, `COLUMN_INDEX`, `COLUMN_NAME`, `TYPE`, `SEMANTIC`, `GROUP_NAME`) VALUES (REPLACE(UUID(),'-',''), '394f82c6e35746318cb846af1e2a8459', '15', '之父基因型', '2', '之父基因型', '之父基因型');


ALTER LIMS_SCHEDULE_PLAN_TASK MODIFY COLUMN task_name VARCHAR(256);


-- 宋健
ALTER TABLE LIMS_PRODUCT_CANCEL_RECORD ADD COLUMN UPDATOR_ID VARCHAR(64) DEFAULT NULL  COMMENT '确认人ID';
ALTER TABLE LIMS_PRODUCT_CANCEL_RECORD ADD COLUMN UPDATOR_NAME  VARCHAR(64) DEFAULT NULL  COMMENT '确认人名称';
UPDATE LIMS_DICT SET DICT_VALUE='之母测序深度' WHERE ID='350d8d7204d711e88c880242ac110002' AND DICT_VALUE='之母测试深度' AND CATEGORY='JSON_KEY';


-- 
alter table LIMS_TESTING_TECHNICAL_ANALY_RECORD ADD COLUMN TECHNICAL_TASK_ID VARCHAR(64) DEFAULT NULL  COMMENT '新技术分析ID';

-- NEW 迭代

ALTER TABLE `LIMS_ORDER` ADD
(

BILLING_TYPE   tinyint(1)  DEFAULT NULL COMMENT '开票形式: 0-不需要,1-电子,2-纸质',

HEADER_TYPE    tinyint(1)  DEFAULT NULL COMMENT '抬头类型: 0-个人,1-企业',

DUTY_PARAGRAPH VARCHAR(64) COLLATE utf8_general_ci DEFAULT NULL COMMENT '企业税号'

);


ALTER TABLE `LIMS_ORDER` ADD MAILBOX  VARCHAR(128)  COLLATE utf8_general_ci DEFAULT NULL COMMENT '电子发票邮箱';

/* 订单开票形式以前的数据默认纸质，发票抬头类型默认个人*/

update `LIMS_ORDER` set BILLING_TYPE  = 2,HEADER_TYPE = 0 ;

/* 增加字典 发票形式:0-不需要，1-电子，2-纸质 */
INSERT INTO `LIMS_DICT`(`ID`,`CATEGORY`,`DICT_TEXT`,`SORT`,`DICT_DESC`,`EDITABLE`)
VALUE('6d8fb86abb1511e88c880242ac110002','BILLING_TYPE','发票形式',45,'发票形式',0);

INSERT INTO`LIMS_DICT`(`ID`,`PARENT_ID`,`CATEGORY`,`DICT_TEXT`,`DICT_VALUE`,`SORT`,`DICT_DESC`,`EDITABLE`)
VALUE('817ad0cfbb1511e88c880242ac110002','6d8fb86abb1511e88c880242ac110002','BILLING_TYPE','不需要','0',1,'发票形式',0);

INSERT INTO`LIMS_DICT`(`ID`,`PARENT_ID`,`CATEGORY`,`DICT_TEXT`,`DICT_VALUE`,`SORT`,`DICT_DESC`,`EDITABLE`)
VALUE('cbc812febb1511e88c880242ac110002','6d8fb86abb1511e88c880242ac110002','BILLING_TYPE','电子','1',2,'发票形式',0);

INSERT INTO`LIMS_DICT`(`ID`,`PARENT_ID`,`CATEGORY`,`DICT_TEXT`,`DICT_VALUE`,`SORT`,`DICT_DESC`,`EDITABLE`)
VALUE('d519f1d8bb1511e88c880242ac110002','6d8fb86abb1511e88c880242ac110002','BILLING_TYPE','纸质','2',3,'发票形式',0);


/*增加字典 发票抬头类型： 0-个人，1-企业*/
INSERT INTO `LIMS_DICT`(`ID`,`CATEGORY`,`DICT_TEXT`,`SORT`,`DICT_DESC`,`EDITABLE`)
VALUE('9f0dee32bb1611e88c880242ac110002','HEADER_TYPE','发票抬头类型',46,'发票抬头类型',0);

INSERT INTO`LIMS_DICT`(`ID`,`PARENT_ID`,`CATEGORY`,`DICT_TEXT`,`DICT_VALUE`,`SORT`,`DICT_DESC`,`EDITABLE`)
VALUE('a85ed7afbb1611e88c880242ac110002','9f0dee32bb1611e88c880242ac110002','HEADER_TYPE','个人','0',1,'发票抬头类型',0);

INSERT INTO`LIMS_DICT`(`ID`,`PARENT_ID`,`CATEGORY`,`DICT_TEXT`,`DICT_VALUE`,`SORT`,`DICT_DESC`,`EDITABLE`)
VALUE('ada55989bb1611e88c880242ac110002','9f0dee32bb1611e88c880242ac110002','HEADER_TYPE','企业','1',2,'发票抬头类型',0);



ALTER TABLE `LIMS_INVOICE_APPLY` ADD
(

HEADER_TYPE    tinyint(1)  DEFAULT NULL COMMENT '抬头类型: 0-个人,1-企业',

DUTY_PARAGRAPH VARCHAR(64) COLLATE utf8_general_ci DEFAULT NULL COMMENT '企业税号',

MAILBOX VARCHAR(128)  COLLATE utf8_general_ci DEFAULT NULL COMMENT '电子发票邮箱'

);

update `LIMS_INVOICE_APPLY` set HEADER_TYPE = 0 ;

ALTER TABLE LIMS_ORDER_PRIMARY_SAMPLE MODIFY SAMPLE_TYPE_ID VARCHAR(64) NULL;
ALTER TABLE LIMS_ORDER_SUBSIDIARY_SAMPLE MODIFY SAMPLE_TYPE_ID VARCHAR(64) NULL;