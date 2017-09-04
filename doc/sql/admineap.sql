/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50141
Source Host           : localhost:3306
Source Database       : admineap

Target Server Type    : MYSQL
Target Server Version : 50141
File Encoding         : 65001

Date: 2017-07-18 19:05:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('schedulerFactoryBean', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('schedulerFactoryBean', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('schedulerFactoryBean', 'billJiang1500374737587', '1500375918249', '20000');

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for query_config
-- ----------------------------
DROP TABLE IF EXISTS `query_config`;
CREATE TABLE `query_config` (
  `id` varchar(36) NOT NULL,
  `columnsName` longtext,
  `pageName` longtext,
  `queryId` varchar(255) DEFAULT NULL,
  `userid` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of query_config
-- ----------------------------

-- ----------------------------
-- Table structure for tbl_act_delegate_history
-- ----------------------------
DROP TABLE IF EXISTS `tbl_act_delegate_history`;
CREATE TABLE `tbl_act_delegate_history` (
  `id` varchar(36) NOT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `ASSIGNEE_` varchar(64) DEFAULT NULL,
  `ATTORNEY_` varchar(64) DEFAULT NULL,
  `DELEGATE_ID_` varchar(255) DEFAULT NULL,
  `DELEGATE_TIME_` datetime DEFAULT NULL,
  `END_TIME_` datetime DEFAULT NULL,
  `MODULE_ID_` varchar(255) DEFAULT NULL,
  `REASON_` varchar(255) DEFAULT NULL,
  `START_TIME_` datetime DEFAULT NULL,
  `TASK_ID_` varchar(255) DEFAULT NULL,
  `TASK_NAME_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_act_delegate_history
-- ----------------------------

-- ----------------------------
-- Table structure for tbl_act_delegate_info
-- ----------------------------
DROP TABLE IF EXISTS `tbl_act_delegate_info`;
CREATE TABLE `tbl_act_delegate_info` (
  `id` varchar(36) NOT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `ASSIGNEE_` varchar(64) DEFAULT NULL,
  `ATTORNEY_` varchar(64) DEFAULT NULL,
  `END_TIME_` datetime DEFAULT NULL,
  `MODULE_ID_` longtext,
  `REASON_` varchar(255) DEFAULT NULL,
  `START_TIME_` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_act_delegate_info
-- ----------------------------

-- ----------------------------
-- Table structure for tbl_act_module
-- ----------------------------
DROP TABLE IF EXISTS `tbl_act_module`;
CREATE TABLE `tbl_act_module` (
  `id` varchar(36) NOT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_act_module
-- ----------------------------

-- ----------------------------
-- Table structure for tbl_demo_generator
-- ----------------------------
DROP TABLE IF EXISTS `tbl_demo_generator`;
CREATE TABLE `tbl_demo_generator` (
  `id` varchar(36) NOT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `education` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `isEnable` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `dict_nation` tinyblob,
  `remark` longtext,
  `salary` double DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `enable` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_demo_generator
-- ----------------------------
INSERT INTO `tbl_demo_generator` VALUES ('402880ee5a3d1df3015a3d39608b0001', '2017-02-14 23:24:14', '0', '2017-02-25 10:43:09', '4', '2017-02-07 00:00:00', 'test', 'test', '402881f053cd72330153cd820b960004', '1', '\0', 'test', 0x3430323838316630353365353565373130313533653537313665373930303033, 'test', '2.3', '123@12.com', '');

-- ----------------------------
-- Table structure for tbl_dict
-- ----------------------------
DROP TABLE IF EXISTS `tbl_dict`;
CREATE TABLE `tbl_dict` (
  `id` varchar(36) NOT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `code` varchar(200) DEFAULT NULL,
  `levelCode` varchar(36) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `parent_id` varchar(200) DEFAULT NULL,
  `remark` longtext,
  `value` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_dict
-- ----------------------------
INSERT INTO `tbl_dict` VALUES ('402881ec54b7493f0154b7113e8f0008', null, '0', '2016-06-23 10:53:13', '1', 'INTENTION', '000029000002', '意向', '402881ec54b7493f0154b7545e8f0008', '', '');
INSERT INTO `tbl_dict` VALUES ('402881ec54b7493f0154b7115e8f0008', null, '0', '2016-06-23 10:57:06', '1', 'FORMAL', '000029000003', '正式', '402881ec54b7493f0154b7545e8f0008', '', '');
INSERT INTO `tbl_dict` VALUES ('402881ec54b7493f0154b7135e8f0008', null, '0', '2016-06-23 10:56:34', '1', 'CONSULTING', '000029000001', '咨询', '402881ec54b7493f0154b7545e8f0008', '', '');
INSERT INTO `tbl_dict` VALUES ('402881ec54b7493f0154b75082770007', null, '0', null, '0', 'PPSITION', '000028', '职务', null, '', '');
INSERT INTO `tbl_dict` VALUES ('402881ec54b7493f0154b7514e8f0008', null, '0', null, '2', 'SELL', '000028000001', '销售', '402881ec54b7493f0154b75082770007', '', '');
INSERT INTO `tbl_dict` VALUES ('402881ec54b7493f0154b7545e8f0008', null, '0', '2016-06-23 12:19:26', '4', 'STATES', '000029', '阶段', null, '', '');
INSERT INTO `tbl_dict` VALUES ('402881ef557b216701557b2f29cf0000', '2016-06-23 10:55:36', '0', '2016-06-23 10:55:36', '0', 'CLIENTLEVEL', '000080', '客户级别', null, null, null);
INSERT INTO `tbl_dict` VALUES ('402881ef557b216701557b3111bc0001', '2016-06-23 10:57:41', '0', '2016-06-23 10:57:41', '0', 'CLIENTLEVEL_1', '000080000001', '战略客户', '402881ef557b216701557b2f29cf0000', '', '');
INSERT INTO `tbl_dict` VALUES ('402881ef557b216701557b31931a0002', '2016-06-23 10:58:14', '0', '2016-06-23 10:58:14', '0', 'CLIENTLEVEL_3', '000080000002', '一般客户', '402881ef557b216701557b2f29cf0000', '', '');
INSERT INTO `tbl_dict` VALUES ('402881ef557b216701557b31fca00003', '2016-06-23 10:58:41', '0', '2016-06-23 10:58:41', '0', 'CLIENTLEVEL_3', '000080000003', '重点客户', '402881ef557b216701557b2f29cf0000', '', '');
INSERT INTO `tbl_dict` VALUES ('402881ef557b216701557b45fca00003', null, '0', null, '0', 'POSITIONS', '000033', '职位', null, null, null);
INSERT INTO `tbl_dict` VALUES ('402881ef557b216701565b45fca00003', null, '0', null, '0', 'MANAGER', '000033000001', '经理', '402881ef557b216701557b45fca00003', null, null);
INSERT INTO `tbl_dict` VALUES ('402881ef557b216701675b45fca00003', null, '0', null, '0', 'CUSTOMER', '000033000002', '客服', '402881ef557b216701557b45fca00003', null, null);
INSERT INTO `tbl_dict` VALUES ('402881f053b726080153b72893a60000', null, '0', '2016-08-24 20:54:20', '5', 'ACCOUNTCHECKTYPE', '000011', '对账类型', '', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053b726080153b7294a1d0001', null, '0', '2016-06-23 10:42:59', '4', 'MONTH_CHECK', '000011000001', '月结', '402881f053b726080153b72893a60000', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053b726080153b72986f80002', null, '0', '2016-06-23 10:31:30', '3', 'HQ_CHECK', '000011000002', '票到', '402881f053b726080153b72893a60000', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053b726080153b74586f80002', null, '0', '2016-06-23 10:42:48', '4', 'PRE_CHECK', '000011000003', '预付', '402881f053b726080153b72893a60000', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053cd670d0153cd6d6be40000', null, '0', null, '2', 'EDUCATION', '000017', '学历', null, '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053cd72330153cd78bacf0000', null, '0', null, '0', 'EDUCATIONCOLLEGE', '000017000001', '本科', '402881f053cd670d0153cd6d6be40000', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053cd72330153cd796ac90001', null, '0', null, '0', 'EDUCATIONHIGHSCHOOL', '000017000002', '高中', '402881f053cd670d0153cd6d6be40000', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053cd72330153cd80b46c0002', null, '0', null, '0', 'EDUCATIONPRIMARY', '000017000003', '小学', '402881f053cd670d0153cd6d6be40000', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053cd72330153cd8153450003', null, '0', null, '0', 'EDUCATIONJUNIOR', '000017000004', '初中', '402881f053cd670d0153cd6d6be40000', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053cd72330153cd820b960004', null, '0', null, '0', 'EDUCATIONDOCTOR', '000017000005', '博士', '402881f053cd670d0153cd6d6be40000', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053cd72330153cd82a79d0005', null, '0', null, '0', 'EDUCATIONMASTER', '000017000006', '硕士', '402881f053cd670d0153cd6d6be40000', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053cd72330153cd8384590006', null, '0', null, '0', 'EDUCATIONJUNIORCOLLEGE', '000017000007', '大专', '402881f053cd670d0153cd6d6be40000', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053d4a5260153d4a845920001', null, '0', null, '0', 'TAXPAYERTYPE', '000019', '纳税人', null, '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053d4a5260153d4a9d0f00002', null, '0', null, '0', 'GENERALTAXPAYER', '000019000001', '一般纳税人', '402881f053d4a5260153d4a845920001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053d4a5260153d4ab6b110003', null, '0', null, '0', 'SMALLSCALETAXPAYER', '000019000002', '小规模纳税人', '402881f053d4a5260153d4a845920001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053d4a5260153d4ab6b130003', null, '0', '2016-06-23 10:24:40', '1', 'GTAXPAYER', '000019000003', '个体工商户', '402881f053d4a5260153d4a845920001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053d4a5260153d4aceaf20004', null, '0', null, '0', 'CAPITALTYPE', '000020', '企业性质', null, '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053d4a5260153d4ae22ee0005', null, '1', null, '1', 'OVERSEASFUNDED', '000020000001', '外资企业', '402881f053d4a5260153d4aceaf20004', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053d4a5260153d4aeadd20006', null, '0', null, '0', 'STATEOWNED', '000020000002', '国营企业', '402881f053d4a5260153d4aceaf20004', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053d4a5260153d4af6f1a0007', null, '0', null, '0', 'JOINTVENTURE', '000020000003', '合资企业', '402881f053d4a5260153d4aceaf20004', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053d4a5260153d4b0a80c0008', null, '0', null, '1', 'PRIVATE', '000020000004', '民营企业', '402881f053d4a5260153d4aceaf20004', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053d4eccd0153d5117aac0001', null, '0', '2016-06-21 10:33:20', '2', 'SETTLEMENTTYPE', '000021', '结算方式', null, '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053d4eccd0153d512a8580002', null, '0', null, '1', 'HQ_SETTLE', '000021000001', '统一结算', '402881f053d4eccd0153d5117aac0001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053d4eccd0153d513a0260003', null, '0', null, '1', 'INDIVIDUAL_SETTLE', '000021000002', '独立结算', '402881f053d4eccd0153d5117aac0001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e570c0890001', null, '0', null, '0', 'NATION', '000023', '民族', null, '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e57116300002', null, '0', null, '0', 'NATION01', '000023000001', '汉族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e5716e790003', null, '0', null, '0', 'NATION02', '000023000002', '蒙古族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e571c0f60004', null, '0', null, '0', 'NATION03', '000023000003', '回族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e5723ada0005', null, '0', null, '0', 'NATION04', '000023000004', '藏族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e57269ca0006', null, '0', null, '0', 'NATION05', '000023000005', '维吾尔族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e57293c20007', null, '0', null, '0', 'NATION06', '000023000006', '苗族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e572e34c0008', null, '0', null, '0', 'NATION07', '000023000007', '彝族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e5732caf0009', null, '0', null, '0', 'NATION08', '000023000008', '壮族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e57366fb000a', null, '0', null, '0', 'NATION09', '000023000009', '布依族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e5739474000b', null, '0', null, '0', 'NATION10', '000023000010', '朝鲜族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e573cc35000c', null, '0', null, '0', 'NATION11', '000023000011', '满族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e5743c21000d', null, '0', null, '0', 'NATION12', '000023000012', '侗族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e5747f53000e', null, '0', null, '0', 'NATION13', '000023000013', '瑶族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e574b560000f', null, '0', null, '0', 'NATION14', '000023000014', '白族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e57575d20010', null, '0', null, '0', 'NATION15', '000023000015', '土家族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e5759d8f0011', null, '0', null, '0', 'NATION16', '000023000016', '哈尼族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e575d9640012', null, '0', null, '0', 'NATION17', '000023000017', '哈萨克族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e5761ee30013', null, '0', null, '0', 'NATION18', '000023000018', '傣族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e5764cfb0014', null, '0', null, '0', 'NATION18', '000023000019', '黎族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e5768eff0015', null, '0', null, '0', 'NATION20', '000023000020', '傈僳族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e576c05a0016', null, '0', null, '0', 'NATION21', '000023000021', '佤族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e5771fb10017', null, '0', null, '0', 'NATION22', '000023000022', '畲族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e577754f0018', null, '0', null, '0', 'NATION23', '000023000023', '高山族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e577d8220019', null, '0', null, '0', 'NATION24', '000023000024', '拉祜族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e57840d4001a', null, '0', null, '0', 'NATION25', '000023000025', '水族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e578beac001b', null, '0', null, '0', 'NATION26', '000023000026', '东乡族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e579cc50001c', null, '0', null, '0', 'NATION27', '000023000027', '纳西族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e57a4b49001d', null, '0', null, '0', 'NATION28', '000023000028', '景颇族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e57ab2ed001e', null, '0', null, '0', 'NATION29', '000023000029', '柯尔克孜族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e57aeb4a001f', null, '0', null, '0', 'NATION30', '000023000030', '土族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e57bb0c20020', null, '0', null, '0', 'NATION31', '000023000031', '达斡尔族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e57c1cb50021', null, '0', null, '0', 'NATION32', '000023000032', '仫佬族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e57c71630022', null, '0', null, '0', 'NATION33', '000023000033', '羌族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e57cb5aa0023', null, '0', null, '0', 'NATION34', '000023000034', '布朗族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e57d27c90024', null, '0', null, '0', '35', '000023000035', '撒拉族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e57df9a10025', null, '0', null, '0', 'NATION36', '000023000036', '毛难族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e57e48b80026', null, '0', null, '0', 'NATION37', '000023000037', '仡佬族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e57e8b080027', null, '0', null, '0', 'NATION38', '000023000038', '锡伯族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e57ebe990028', null, '0', null, '0', 'NATION', '000023000039', '阿昌族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e57ef9f50029', null, '0', null, '0', 'NATION40', '000023000040', '普米族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e57f6094002a', null, '0', null, '0', 'NATION41', '000023000041', '塔吉克族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e57fbe0b002b', null, '0', null, '0', 'NATION42', '000023000042', '怒族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e57ffd10002c', null, '0', null, '0', 'NATION43', '000023000043', '乌兹别克', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e5802506002d', null, '0', null, '0', 'NATION44', '000023000044', '俄罗斯族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e58066c6002e', null, '0', null, '0', 'NATION45', '000023000045', '鄂温克族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e580a981002f', null, '0', null, '0', 'NATION46', '000023000046', '崩龙族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e580e3540030', null, '0', null, '0', 'NATION47', '000023000047', '保安族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e5813d0a0031', null, '0', null, '0', 'NATION48', '000023000048', '裕固族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e581adce0032', null, '0', null, '0', 'NATION49', '000023000049', '京族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e581eb050033', null, '0', null, '0', 'NATION50', '000023000050', '塔塔尔族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e582223b0034', null, '0', null, '0', 'NATION51', '000023000051', '独龙族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e58269950035', null, '0', null, '0', 'NATION52', '000023000052', '鄂伦春族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e582c7b80036', null, '0', null, '0', 'NATION53', '000023000053', '赫哲族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e58307aa0037', null, '0', null, '0', 'NATION54', '000023000054', '门巴族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e583596a0038', null, '0', null, '0', 'NATION55', '000023000055', '珞巴族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053e55e710153e5838d9c0039', null, '0', null, '0', 'NATION56', '000023000056', '基诺族', '402881f053e55e710153e570c0890001', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053f983b00153f98597fd0000', null, '0', null, '0', 'MESSAGETYPE', '000024', '站内信类型', null, '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053f983b00153f986e77c0001', null, '0', '2016-07-31 08:40:32', '2', 'MESSAGEPRODUCT', '000024000001', '生产', '402881f053f983b00153f98597fd0000', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053f983b00153f98822340002', null, '0', null, '0', 'MESSAGEMARKET', '000024000002', '销售', '402881f053f983b00153f98597fd0000', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053f983b00153f988e3190003', null, '0', null, '0', 'MESSAGEDELIVERY', '000024000003', '送货', '402881f053f983b00153f98597fd0000', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f053f983b00153f989be130004', null, '0', null, '0', 'MESSAGEPURCHASE', '000024000004', '采购', '402881f053f983b00153f98597fd0000', '', '');
INSERT INTO `tbl_dict` VALUES ('402881f0540024cd0154003bae140006', null, '0', null, '0', 'INFORM', '000024000005', '通知', '402881f053f983b00153f98597fd0000', '', '');
INSERT INTO `tbl_dict` VALUES ('402882f453c561fa0153c5647ce00003', null, '0', null, '0', 'CHANNELTYPE', '000013', '来源', null, '', '');
INSERT INTO `tbl_dict` VALUES ('402882f453c561fa0153c56598ee0004', null, '0', '2016-08-27 23:55:23', '1', 'CHANNELTYPEACTIVITY', '000013000001', '推广活动', '402882f453c561fa0153c5647ce00003', '', '');
INSERT INTO `tbl_dict` VALUES ('402882f453c561fa0153c565dd6b0005', null, '0', null, '1', 'CHANNELTYPETEL', '000013000002', '电话咨询', '402882f453c561fa0153c5647ce00003', '', '');
INSERT INTO `tbl_dict` VALUES ('402882f453cf94730153cf96f8e40000', null, '0', null, '0', 'SUPPLIERLEVEL', '000018', '供应商级别', null, '', '');
INSERT INTO `tbl_dict` VALUES ('402882f453cf94730153cf97dbc30001', null, '0', '2016-06-23 10:18:12', '2', 'SUPPLIERLEVELSILVER', '000018000001', '一般供应商', '402882f453cf94730153cf96f8e40000', '', '');
INSERT INTO `tbl_dict` VALUES ('402882f453cf94730153cf9834bd0002', null, '0', '2016-06-23 10:18:00', '2', 'SUPPLIERLEVELGOLD', '000018000002', '重点供应商', '402882f453cf94730153cf96f8e40000', '', '');
INSERT INTO `tbl_dict` VALUES ('402882f453cf94730153cf98f3fa0003', null, '0', '2016-06-23 10:17:32', '2', 'SUPPLIERLEVELDIAMOND', '000018000003', '战略供应商', '402882f453cf94730153cf96f8e40000', '', '');
INSERT INTO `tbl_dict` VALUES ('8a2c43d853f918e30153f92417ab0004', null, '0', null, '0', 'CHANNELTYPEMEDIA', '000013000003', '媒体广告', '402882f453c561fa0153c5647ce00003', '', '');
INSERT INTO `tbl_dict` VALUES ('8a2c43d853f918e30153f92589ad0005', null, '0', null, '0', 'CHANNELTYPEPEINTRO', '000013000004', '熟人推荐', '402882f453c561fa0153c5647ce00003', '', '');
INSERT INTO `tbl_dict` VALUES ('8a2c43d853f918e30153f928e92e0006', null, '0', null, '0', 'CHANNELTYPEOTHERS', '000013000005', '其他', '402882f453c561fa0153c5647ce00003', '', '');
INSERT INTO `tbl_dict` VALUES ('bc4d163c5880ab4901588b5582360001', '2016-11-22 17:19:46', '0', '2016-11-22 17:19:46', '0', 'TEXT', '000024000006', '短信', '402881f053f983b00153f98597fd0000', '', '');
INSERT INTO `tbl_dict` VALUES ('bc4d163c5880ab4901588b5b49080002', '2016-11-22 17:26:05', '0', '2016-11-22 17:26:05', '0', 'HANZU', '000023000057', '汉族', '402881f053e55e710153e570c0890001', '', '');

-- ----------------------------
-- Table structure for tbl_file
-- ----------------------------
DROP TABLE IF EXISTS `tbl_file`;
CREATE TABLE `tbl_file` (
  `id` varchar(36) NOT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `fileName` varchar(255) DEFAULT NULL,
  `filePath` varchar(255) DEFAULT NULL,
  `fileSize` bigint(20) DEFAULT NULL,
  `form_ID` varchar(255) DEFAULT NULL,
  `savedName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_file
-- ----------------------------
INSERT INTO `tbl_file` VALUES ('402880ea5aae3edf015ab1a4a4ff0013', '2017-03-09 13:57:21', '0', '2017-03-09 13:57:21', '0', '402880e456223e8f015625231d8e0003', '中国石油勘探开发研究院简历（江日念）.doc', '\\uploadPath\\file\\20170309135721782_中国石油勘探开发研究院简历（江日念）.doc', '69120', null, '20170309135721782_中国石油勘探开发研究院简历（江日念）.doc');
INSERT INTO `tbl_file` VALUES ('402880ea5aae3edf015ab1a582830014', '2017-03-09 13:58:18', '0', '2017-03-09 13:58:18', '0', '402880e456223e8f015625231d8e0003', '010202新增工厂.png', '\\uploadPath\\file\\20170309135818497_010202新增工厂.png', '63321', null, '20170309135818497_010202新增工厂.png');
INSERT INTO `tbl_file` VALUES ('402880ea5aae3edf015ab1a6cdc80016', '2017-03-09 13:59:43', '0', '2017-03-09 13:59:43', '0', '402880e456223e8f015625231d8e0003', 'dist.rar', '\\uploadPath\\file\\20170309135943302_dist.rar', '494885', null, '20170309135943302_dist.rar');
INSERT INTO `tbl_file` VALUES ('402880ea5aae3edf015ab2527de10019', '2017-03-09 17:07:15', '0', '2017-03-09 17:07:15', '0', '402880e456223e8f015625231d8e0003', 'Penguins.jpg', '\\uploadPath\\file\\2017030917071536_Penguins.jpg', '777835', null, '2017030917071536_Penguins.jpg');
INSERT INTO `tbl_file` VALUES ('402880ea5aae3edf015ab2527de9001a', '2017-03-09 17:07:15', '0', '2017-03-09 17:07:15', '0', '402880e456223e8f015625231d8e0003', 'Desert.jpg', '\\uploadPath\\file\\2017030917071545_Desert.jpg', '845941', null, '2017030917071545_Desert.jpg');
INSERT INTO `tbl_file` VALUES ('402880ea5aae3edf015ab35f47fe001b', '2017-03-09 22:00:50', '0', '2017-03-09 22:00:50', '0', '402880e456223e8f015625231d8e0003', 'Koala.jpg', '\\uploadPath\\file\\20170309220050423_Koala.jpg', '780831', null, '20170309220050423_Koala.jpg');
INSERT INTO `tbl_file` VALUES ('402880ea5aae3edf015ab35f4806001c', '2017-03-09 22:00:50', '0', '2017-03-09 22:00:50', '0', '402880e456223e8f015625231d8e0003', 'Penguins.jpg', '\\uploadPath\\file\\20170309220050433_Penguins.jpg', '777835', null, '20170309220050433_Penguins.jpg');
INSERT INTO `tbl_file` VALUES ('402880ea5aae3edf015ab388c523001d', '2017-03-09 22:46:09', '0', '2017-03-09 22:46:09', '0', '402880e456223e8f015625231d8e0003', '石油大院社区app汇报.pptx', '\\uploadPath\\file\\20170309224609437_石油大院社区app汇报.pptx', '2956689', null, '20170309224609437_石油大院社区app汇报.pptx');
INSERT INTO `tbl_file` VALUES ('402880ea5aae3edf015ab388c52a001e', '2017-03-09 22:46:09', '0', '2017-03-09 22:46:09', '0', '402880e456223e8f015625231d8e0003', '送样单_地大武汉_Tmax_填.xls', '\\uploadPath\\file\\20170309224609446_送样单_地大武汉_Tmax_填.xls', '17408', null, '20170309224609446_送样单_地大武汉_Tmax_填.xls');
INSERT INTO `tbl_file` VALUES ('402880ed5abbe288015abc895fcc0003', '2017-03-11 16:43:23', '0', '2017-03-11 16:43:23', '0', '402880e456223e8f015625231d8e0003', '软件工程_钱乐秋_.rar', '\\uploadPath\\file\\20170311164323978_软件工程_钱乐秋_.rar', '3926542', null, '20170311164323978_软件工程_钱乐秋_.rar');
INSERT INTO `tbl_file` VALUES ('402880ed5abbe288015abc9ff9500004', '2017-03-11 17:08:05', '0', '2017-03-11 17:08:05', '0', '402880e456223e8f015625231d8e0003', 'AdminEAP_framework_1.0_SNAPSHOT.jar', '\\uploadPath\\file\\2017031117080570_AdminEAP_framework_1.0_SNAPSHOT.jar', '180562', null, '2017031117080570_AdminEAP_framework_1.0_SNAPSHOT.jar');
INSERT INTO `tbl_file` VALUES ('402880ed5abbe288015abca8159d0005', '2017-03-11 17:16:56', '0', '2017-03-11 17:16:56', '0', '402880e456223e8f015625231d8e0003', '中国石油勘探开发研究院简历（江日念）.doc', '\\uploadPath\\file\\20170311171656603_中国石油勘探开发研究院简历（江日念）.doc', '69120', null, '20170311171656603_中国石油勘探开发研究院简历（江日念）.doc');
INSERT INTO `tbl_file` VALUES ('402880ed5abbe288015abcb7f70f0006', '2017-03-11 17:34:17', '0', '2017-03-11 17:34:17', '0', '402880e456223e8f015625231d8e0003', '章真忠2.docx', '\\uploadPath\\file\\20170311173417357_章真忠2.docx', '64781', null, '20170311173417357_章真忠2.docx');
INSERT INTO `tbl_file` VALUES ('402880ed5abbe288015abcb9228a0007', '2017-03-11 17:35:34', '0', '2017-03-11 17:35:34', '0', '402880e456223e8f015625231d8e0003', '中国石油勘探开发研究院简历（江日念）.doc', '\\uploadPath\\file\\2017031117353423_中国石油勘探开发研究院简历（江日念）.doc', '69120', null, '2017031117353423_中国石油勘探开发研究院简历（江日念）.doc');
INSERT INTO `tbl_file` VALUES ('402880ed5abbe288015abcbb8e24000e', '2017-03-11 17:38:12', '0', '2017-03-11 17:38:12', '0', '402880e456223e8f015625231d8e0003', '234928_150R215404322.jpg', '\\uploadPath\\file\\20170311173812641_234928_150R215404322.jpg', '91256', null, '20170311173812641_234928_150R215404322.jpg');
INSERT INTO `tbl_file` VALUES ('402880ed5abbe288015abcbfc66f000f', '2017-03-11 17:42:49', '0', '2017-03-11 17:42:49', '0', '402880e456223e8f015625231d8e0003', '234928_150R215404322.jpg', '\\uploadPath\\file\\20170311174249197_234928_150R215404322.jpg', '91256', null, '20170311174249197_234928_150R215404322.jpg');
INSERT INTO `tbl_file` VALUES ('402880ed5abbe288015abcc037440012', '2017-03-11 17:43:18', '0', '2017-03-11 17:43:18', '0', '402880e456223e8f015625231d8e0003', '104039672.png', '\\uploadPath\\file\\2017031117431882_104039672.png', '138668', null, '2017031117431882_104039672.png');
INSERT INTO `tbl_file` VALUES ('402880ed5abbe288015abcc0430d0013', '2017-03-11 17:43:21', '0', '2017-03-11 17:43:21', '0', '402880e456223e8f015625231d8e0003', '104039672_副本.png', '\\uploadPath\\file\\2017031117432199_104039672_副本.png', '138668', null, '2017031117432199_104039672_副本.png');
INSERT INTO `tbl_file` VALUES ('402880ed5abbe288015abdcfc6b70014', '2017-03-11 22:39:55', '0', '2017-03-11 22:39:55', '0', '402880e456223e8f015625231d8e0003', '234928_150R215404322.jpg', '\\uploadPath\\file\\2017031122395561_234928_150R215404322.jpg', '91256', null, '2017031122395561_234928_150R215404322.jpg');
INSERT INTO `tbl_file` VALUES ('402880ed5abbe288015abdd3df0f0015', '2017-03-11 22:44:23', '0', '2017-03-11 22:44:23', '0', '402880e456223e8f015625231d8e0003', '1_146_.jpg', '\\uploadPath\\file\\20170311224423438_1_146_.jpg', '38230', null, '20170311224423438_1_146_.jpg');
INSERT INTO `tbl_file` VALUES ('402880ed5abbe288015abdd3e73c0016', '2017-03-11 22:44:25', '0', '2017-03-11 22:44:25', '0', '402880e456223e8f015625231d8e0003', '1.jpg', '\\uploadPath\\file\\20170311224425530_1.jpg', '23341', null, '20170311224425530_1.jpg');
INSERT INTO `tbl_file` VALUES ('402880ed5abbe288015abdd694eb0017', '2017-03-11 22:47:21', '0', '2017-03-11 22:47:21', '0', '402880e456223e8f015625231d8e0003', '5936056_135709981147_2.jpg', '\\uploadPath\\file\\2017031122472166_5936056_135709981147_2.jpg', '196305', null, '2017031122472166_5936056_135709981147_2.jpg');
INSERT INTO `tbl_file` VALUES ('402880ed5abbe288015abdd6b22e0018', '2017-03-11 22:47:28', '0', '2017-03-11 22:47:28', '0', '402880e456223e8f015625231d8e0003', '234928_150R215404322.jpg', '\\uploadPath\\file\\20170311224728557_234928_150R215404322.jpg', '91256', null, '20170311224728557_234928_150R215404322.jpg');
INSERT INTO `tbl_file` VALUES ('402880ed5abdfec1015ac02c71f10001', '2017-03-12 09:40:22', '0', '2017-03-12 09:40:22', '0', '402880e456223e8f015625231d8e0003', '234928_150R215404322.jpg', '\\uploadPath\\file\\20170312094022638_234928_150R215404322.jpg', '91256', null, '20170312094022638_234928_150R215404322.jpg');
INSERT INTO `tbl_file` VALUES ('402880ed5abdfec1015ac02c71f50002', '2017-03-12 09:40:22', '0', '2017-03-12 09:40:22', '0', '402880e456223e8f015625231d8e0003', '5936056_135709981147_2.jpg', '\\uploadPath\\file\\20170312094022644_5936056_135709981147_2.jpg', '196305', null, '20170312094022644_5936056_135709981147_2.jpg');
INSERT INTO `tbl_file` VALUES ('402880ed5abdfec1015ac02c71f80003', '2017-03-12 09:40:22', '0', '2017-03-12 09:40:22', '0', '402880e456223e8f015625231d8e0003', '5936056_154500108516_2.jpg', '\\uploadPath\\file\\20170312094022647_5936056_154500108516_2.jpg', '273616', null, '20170312094022647_5936056_154500108516_2.jpg');
INSERT INTO `tbl_file` VALUES ('8a8a84995ab60555015ab6098adf0000', '2017-03-10 10:26:03', '0', '2017-03-10 10:26:03', '0', '402880e456223e8f015625231d8e0003', '章真忠2.docx', '\\uploadPath\\file\\2017031010260396_章真忠2.docx', '64781', null, '2017031010260396_章真忠2.docx');
INSERT INTO `tbl_file` VALUES ('8a8a84995ab64a5c015ab64d26c90001', '2017-03-10 11:39:53', '0', '2017-03-10 11:39:53', '0', '402880e456223e8f015625231d8e0003', 'mm_facetoface_collect_qrcode_1487932173612.png', '\\uploadPath\\file\\20170310113953926_mm_facetoface_collect_qrcode_1487932173612.png', '85360', null, '20170310113953926_mm_facetoface_collect_qrcode_1487932173612.png');
INSERT INTO `tbl_file` VALUES ('8a8a84995ab64a5c015ab64d26ce0002', '2017-03-10 11:39:53', '0', '2017-03-10 11:39:53', '0', '402880e456223e8f015625231d8e0003', 'quoteServiceImpl.txt', '\\uploadPath\\file\\20170310113953932_quoteServiceImpl.txt', '17439', null, '20170310113953932_quoteServiceImpl.txt');
INSERT INTO `tbl_file` VALUES ('8a8a84995ab654d1015ab6571bfa0000', '2017-03-10 11:50:46', '0', '2017-03-10 11:50:46', '0', '402880e456223e8f015625231d8e0003', 'Hydrangeas.jpg', '\\uploadPath\\file\\20170310115046517_Hydrangeas.jpg', '595284', null, '20170310115046517_Hydrangeas.jpg');
INSERT INTO `tbl_file` VALUES ('8a8a84995ab654d1015ab6571c1b0001', '2017-03-10 11:50:46', '0', '2017-03-10 11:50:46', '0', '402880e456223e8f015625231d8e0003', 'Jellyfish.jpg', '\\uploadPath\\file\\20170310115046553_Jellyfish.jpg', '775702', null, '20170310115046553_Jellyfish.jpg');
INSERT INTO `tbl_file` VALUES ('8a8a84995ab654d1015ab659bb8e0002', '2017-03-10 11:53:38', '0', '2017-03-10 11:53:38', '0', '402880e456223e8f015625231d8e0003', 'Jellyfish.jpg', '\\uploadPath\\file\\20170310115338442_Jellyfish.jpg', '775702', null, '20170310115338442_Jellyfish.jpg');
INSERT INTO `tbl_file` VALUES ('8a8a84995ab654d1015ab659bb940003', '2017-03-10 11:53:38', '0', '2017-03-10 11:53:38', '0', '402880e456223e8f015625231d8e0003', 'Koala.jpg', '\\uploadPath\\file\\20170310115338450_Koala.jpg', '780831', null, '20170310115338450_Koala.jpg');
INSERT INTO `tbl_file` VALUES ('8a8a84995ab654d1015ab659bb990004', '2017-03-10 11:53:38', '0', '2017-03-10 11:53:38', '0', '402880e456223e8f015625231d8e0003', 'Lighthouse.jpg', '\\uploadPath\\file\\20170310115338455_Lighthouse.jpg', '561276', null, '20170310115338455_Lighthouse.jpg');
INSERT INTO `tbl_file` VALUES ('8a8a84995ab654d1015ab6749dfa0005', '2017-03-10 12:23:00', '0', '2017-03-10 12:23:00', '0', '402880e456223e8f015625231d8e0003', 'Koala.jpg', '\\uploadPath\\file\\20170310122300343_Koala.jpg', '780831', null, '20170310122300343_Koala.jpg');
INSERT INTO `tbl_file` VALUES ('8a8a84995ab654d1015ab6749e010006', '2017-03-10 12:23:00', '0', '2017-03-10 12:23:00', '0', '402880e456223e8f015625231d8e0003', 'Lighthouse.jpg', '\\uploadPath\\file\\20170310122300351_Lighthouse.jpg', '561276', null, '20170310122300351_Lighthouse.jpg');
INSERT INTO `tbl_file` VALUES ('8a8a84995ab654d1015ab6759e9e0008', '2017-03-10 12:24:06', '0', '2017-03-10 12:24:06', '0', '402880e456223e8f015625231d8e0003', 'Koala.jpg', '\\uploadPath\\file\\2017031012240643_Koala.jpg', '780831', null, '2017031012240643_Koala.jpg');
INSERT INTO `tbl_file` VALUES ('8a8a84995ab654d1015ab69a75c0000a', '2017-03-10 13:04:20', '0', '2017-03-10 13:04:20', '0', '402880e456223e8f015625231d8e0003', 'Desert.jpg', '\\uploadPath\\file\\20170310130420413_Desert.jpg', '845941', null, '20170310130420413_Desert.jpg');

-- ----------------------------
-- Table structure for tbl_function
-- ----------------------------
DROP TABLE IF EXISTS `tbl_function`;
CREATE TABLE `tbl_function` (
  `id` varchar(36) NOT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  `functype` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `levelCode` varchar(36) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  `remark` longtext,
  `url` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_function
-- ----------------------------
INSERT INTO `tbl_function` VALUES ('402880e958eb83230158eb85a30f0000', '2016-12-11 09:35:53', '0', '2016-12-11 09:35:53', '0', 'ARTICLE', '1', 'fa fa-edit', '000028000001', '我的文章', '402880eb58d9a6d40158d9a7b21f0000', '', '/markdown/list');
INSERT INTO `tbl_function` VALUES ('402880e95a72d8a4015a738980e30000', '2017-02-25 12:31:15', '0', '2017-02-25 12:31:15', '0', 'FORM', '1', 'fa fa-edit', '000004000002', '表单-form', 'ff80808159ea142c0159ea670fae0000', '表单组件base-form', '/markdown/preview/form');
INSERT INTO `tbl_function` VALUES ('402880e95a72d8a4015a738bb1990001', '2017-02-25 12:33:39', '0', '2017-02-25 12:33:57', '1', 'JOB', '1', 'fa fa-calendar-check-o', '000028000005', '任务管理器', '402880eb58d9a6d40158d9a7b21f0000', '基于Quartz的定时任务管理器', '/job/list');
INSERT INTO `tbl_function` VALUES ('402880ea5aae3edf015ab1b0ec9d0017', '2017-03-09 14:10:46', '0', '2017-03-09 14:10:46', '0', 'FILEINPUT', '1', 'fa fa-file-o', '000004000003', '附件上传-file', 'ff80808159ea142c0159ea670fae0000', '', '/markdown/preview/file');
INSERT INTO `tbl_function` VALUES ('402880eb56875b7f0156876338100000', '2016-08-14 12:50:41', '0', '2016-12-05 15:12:59', '3', 'DEMO', '0', 'fa fa-table', '000002', 'CURD DEMO', '', '', '');
INSERT INTO `tbl_function` VALUES ('402880eb56875b7f01568763ac630001', '2016-08-14 12:51:11', '0', '2016-12-05 16:22:21', '5', 'USER-DIALOG', '1', 'fa fa-user', '000002000001', '用户列表-Dailog', '402880eb56875b7f0156876338100000', '', '/user/list');
INSERT INTO `tbl_function` VALUES ('402880eb58d9a6d40158d9a7b21f0000', '2016-12-07 22:19:55', '0', '2016-12-07 22:19:55', '0', 'TOOL', '0', 'fa fa-wrench', '000028', '系统工具', '', '', '');
INSERT INTO `tbl_function` VALUES ('402880eb58d9a6d40158d9a995130001', '2016-12-07 22:21:59', '0', '2016-12-12 09:59:46', '3', 'MARKDOWN', '1', 'fa fa-pencil', '000028000002', 'Markdown编辑器', '402880eb58d9a6d40158d9a7b21f0000', '', '/markdown/edit');
INSERT INTO `tbl_function` VALUES ('40288182579e398f0157a25ca29a0002', '2016-10-08 11:36:02', '0', '2016-10-08 11:36:02', '0', 'USER-DEMO-TAB', '1', 'fa fa-male', '000002000002', '用户列表-Tab', '402880eb56875b7f0156876338100000', 'CURD-DEMO tab方式', '/user/tab/list');
INSERT INTO `tbl_function` VALUES ('4028818a567745ec0156786cd39e000b', '2016-08-11 15:06:53', '0', '2016-08-30 14:06:15', '5', 'TODO', '0', 'fa fa-calendar-check-o', '000001', '我的待办', '', '', '');
INSERT INTO `tbl_function` VALUES ('4028818a56d407950156d41352630000', '2016-08-29 10:14:11', '0', '2016-08-29 10:14:11', '0', 'SYSTEM', '0', 'fa fa-tv', '000030', '系统管理', '', '', '');
INSERT INTO `tbl_function` VALUES ('4028818a56d407950156d41435830001', '2016-08-29 10:15:09', '0', '2017-07-18 18:29:43', '1', 'USER', '1', 'fa fa-user', '000030000001', '用户管理', '4028818a56d407950156d41352630000', null, '/user/page/list');
INSERT INTO `tbl_function` VALUES ('4028818a56d407950156d4160e390002', '2016-08-29 10:17:10', '0', '2016-08-29 10:17:10', '0', 'DICTIONARY', '1', 'fa fa-book', '000030000002', '字典管理', '4028818a56d407950156d41352630000', '', '/dict/tree');
INSERT INTO `tbl_function` VALUES ('4028818a56d407950156d41d32ab0003', '2016-08-29 10:24:58', '0', '2017-01-03 16:51:49', '2', 'FUNCTION', '1', 'fa fa-cog', '000030000003', '功能管理', '4028818a56d407950156d41352630000', '', '/function/tree');
INSERT INTO `tbl_function` VALUES ('4028818a56d407950156d4ecfafa0004', '2016-08-29 14:11:55', '0', '2016-08-30 13:44:07', '1', 'Role', '1', 'fa fa-street-view', '000030000004', '角色管理', '4028818a56d407950156d41352630000', '', '/role/list');
INSERT INTO `tbl_function` VALUES ('402881f25681c52a015681d472ba0000', '2016-08-13 10:56:39', '0', '2016-12-05 09:58:40', '2', 'TEST', '1', 'fa fa-bicycle', '000001000001', 'TEST', '4028818a567745ec0156786cd39e000b', '', 'TEST');
INSERT INTO `tbl_function` VALUES ('8a8a801b58ce0f500158ce14d2c20000', '2016-12-05 16:23:40', '0', '2016-12-05 16:23:40', '0', 'USER-PAGE', '1', 'fa fa-user', '000002000003', '用户列表-Page', '402880eb56875b7f0156876338100000', '', '/user/page/list');
INSERT INTO `tbl_function` VALUES ('8a8a807a59634b2801596364e33d0001', '2017-01-03 16:14:33', '0', '2017-01-03 16:14:33', '0', 'ROLEFUNC', '1', 'fa fa-key', '000030000005', '角色授权', '4028818a56d407950156d41352630000', '', '/rolefunc/list');
INSERT INTO `tbl_function` VALUES ('8a8a81425a1b36e9015a1b42074b0000', '2017-02-08 09:06:36', '0', '2017-02-13 13:40:04', '1', 'CODEGENERATOR', '1', 'fa fa-list', '000028000003', '代码生成器', '402880eb58d9a6d40158d9a7b21f0000', '', '/generator/setting');
INSERT INTO `tbl_function` VALUES ('8a8a81d65a3598d9015a360092e60000', '2017-02-13 13:44:51', '1', '2017-03-01 11:01:46', '2', 'GENERATOR', '1', 'fa fa-edit', '000028000004', '代码生成器测试', '402880eb58d9a6d40158d9a7b21f0000', '本功能菜单为代码生成器生成，时间：2017-02-13 13:44:51', '/generator/list');
INSERT INTO `tbl_function` VALUES ('8a8a83295a83a81a015a843e397b0000', '2017-02-28 18:22:34', '0', '2017-02-28 18:22:34', '0', 'MESSAGE', '1', 'fa fa-list', '000028000006', '消息管理器', '402880eb58d9a6d40158d9a7b21f0000', '', '/message/list');
INSERT INTO `tbl_function` VALUES ('ff80808159ea142c0159ea670fae0000', '2017-01-29 21:25:39', '0', '2017-02-06 14:34:24', '2', 'Component', '0', 'fa fa-server', '000004', '组件使用说明', '', '', '');
INSERT INTO `tbl_function` VALUES ('ff80808159ea142c0159ea68d1e30001', '2017-01-29 21:27:35', '0', '2017-02-25 12:31:32', '3', 'MODALS', '1', 'fa fa-windows', '000004000001', '窗体-modals', 'ff80808159ea142c0159ea670fae0000', '', '/markdown/preview/modal');

-- ----------------------------
-- Table structure for tbl_function_filter
-- ----------------------------
DROP TABLE IF EXISTS `tbl_function_filter`;
CREATE TABLE `tbl_function_filter` (
  `id` varchar(36) NOT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `class_type` varchar(255) DEFAULT NULL,
  `key` varchar(255) DEFAULT NULL,
  `operator` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `functionId` varchar(255) DEFAULT NULL,
  `roleId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_function_filter
-- ----------------------------
INSERT INTO `tbl_function_filter` VALUES ('40288193596da8a301596dd926ed0001', '2017-01-05 16:57:44', '0', '2017-01-05 16:57:44', '0', 'java.lang.Double', 'test', 'not_null', '1', 'test', '402880eb58d9a6d40158d9a995130001', 'bc4d163c5880ab4901588b6681e50003');

-- ----------------------------
-- Table structure for tbl_markdown
-- ----------------------------
DROP TABLE IF EXISTS `tbl_markdown`;
CREATE TABLE `tbl_markdown` (
  `id` varchar(36) NOT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `content` longtext,
  `code` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `summary` longtext,
  `type_id` varchar(36) DEFAULT NULL,
  `type_name` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_markdown
-- ----------------------------
INSERT INTO `tbl_markdown` VALUES ('402880e958ecdff60158ed0c62ba0001', '2016-12-11 16:42:41', '0', '2017-02-06 11:18:28', '2', 'Markdown,关键字', '欢迎使用Markdown编辑器写文章', null, '# 欢迎使用Markdown编辑器写文章\n\n本Markdown编辑器使用**Editor.md**修改而来，用它写技术文章，将会带来全新的体验哦：\n\n- **Markdown和扩展Markdown简洁的语法**\n- **代码块高亮**\n- **图片链接和图片上传**\n- ***LaTex*数学公式**\n- **UML序列图和流程图**\n- **离线写文章**\n- **导入导出Markdown文件**\n- **丰富的快捷键**\n-------------------\n\n## 快捷键\n\n - 加粗     `Ctrl + B`\n - 斜体     `Ctrl + I`\n - 引用     `Ctrl + Q`\n - 插入链接    `Ctrl + L`\n - 插入代码    `Ctrl + K`\n - 插入图片    `Ctrl + G`\n - 提升标题    `Ctrl + H`\n - 有序列表    `Ctrl + O`\n - 无序列表    `Ctrl + U`\n - 横线    `Ctrl + R`\n - 撤销    `Ctrl + Z`\n - 重做    `Ctrl + Y`\n\n## Markdown及扩展\n\n> Markdown 是一种轻量级标记语言，它允许人们使用易读易写的纯文本格式编写文档，然后转换成格式丰富的HTML页面。    —— <a href=\"https://zh.wikipedia.org/wiki/Markdown\" target=\"_blank\"> [ 维基百科 ]\n\n使用简单的符号标识不同的标题，将某些文字标记为**粗体**或者*斜体*，创建一个[链接](http://www.csdn.net)等，详细语法参考帮助？。\n\n本编辑器支持 **Markdown Extra** , 　扩展了很多好用的功能。具体请参考[Github][2].\n\n### 表格\n\n**Markdown　Extra**　表格语法：\n\n项目     | 价格\n-------- | ---\nComputer | $1600\nPhone    | $12\nPipe     | $1\n\n可以使用冒号来定义对齐方式：\n\n| 项目      |    价格 | 数量  |\n| :-------- | --------:| :--: |\n| Computer  | 1600 元 |  5   |\n| Phone     |   12 元 |  12  |\n| Pipe      |    1 元 | 234  |\n\n\n### 代码块\n代码块语法遵循标准markdown代码，例如：\n``` python\n@requires_authorization\ndef somefunc(param1=\'\', param2=0):\n    \'\'\'A docstring\'\'\'\n    if param1 > param2: # interesting\n        print \'Greater\'\n    return (param2 - param1 + 1) or None\nclass SomeClass:\n    pass\n>>> message = \'\'\'interpreter\n... prompt\'\'\'\n```\n\n\n### 目录\n用 `[TOC]`来生成目录：\n\n[TOC]\n\n### 数学公式\n使用MathJax渲染*LaTex* 数学公式，详见[math.stackexchange.com][1].\n\n - 行内公式，数学公式为：$\\Gamma(n) = (n-1)!\\quad\\forall n\\in\\mathbb N$。\n - 块级公式：\n\n$$	x = \\dfrac{-b \\pm \\sqrt{b^2 - 4ac}}{2a} $$\n\n更多LaTex语法请参考 [这儿][3].\n\n### UML 图:\n\n可以渲染序列图：\n\n```sequence\n张三->李四: 嘿，小四儿, 写博客了没?\nNote right of 李四: 李四愣了一下，说：\n李四-->张三: 忙得吐血，哪有时间写。\n```\n\n或者流程图：\n\n```flow\nst=>start: 开始\ne=>end: 结束\nop=>operation: 我的操作\ncond=>condition: 确认？\n\nst->op->cond\ncond(yes)->e\ncond(no)->op\n```\n\n- 关于 **序列图** 语法，参考 [这儿][4],\n- 关于 **流程图** 语法，参考 [这儿][5].\n\n## 离线写博客\n\n即使用户在没有网络的情况下，也可以通过本编辑器离线写文章（直接在曾经使用过的浏览器中输入[write.blog.csdn.net/mdeditor](http://write.blog.csdn.net/mdeditor)即可。**Markdown编辑器**使用浏览器离线存储将内容保存在本地。\n\n用户写文章的过程中，内容实时保存在浏览器缓存中，在用户关闭浏览器或者其它异常情况下，内容不会丢失。用户再次打开浏览器时，会显示上次用户正在编辑的没有发表的内容。\n\n文章发表后，本地缓存将被删除。\n\n用户可以选择 <i class=\"icon-disk\"></i> 把正在写的文章保存到服务器草稿箱，即使换浏览器或者清除缓存，内容也不会丢失。\n\n> **注意：**虽然浏览器存储大部分时候都比较可靠，但为了您的数据安全，在联网后，**请务必及时发表或者保存到服务器草稿箱**', null, null, null, null, null);
INSERT INTO `tbl_markdown` VALUES ('402880ea5aae3edf015ab22e20740018', '2017-03-09 16:27:31', '0', '2017-03-09 17:29:53', '2', 'BaseFile,Bootstrap-fileinput,附件上传', '附件上传组件BaseFile使用说明', '402880e456223e8f015625231d8e0003', '##介绍\nBaseFile是AdminEAP框架中基于Bootstrap-fileinput的附件上传组件，它支持 支持多文件、在线预览、拖拽上传等功能，封装后BaseFile主要包括以下功能：\n\n- 弹出窗口的附件上传\n- 当前界面的附件上传\n- 显示附件明细\n- 可编辑的附件明细（删除、预览、不可新增）\n\n关于BaseFile的详细使用，请参照本人博客：[Bootstrap-fileinput组件封装及使用 ](blog.csdn.net/jrn1012/article/details/60963714)\n关于Bootstrap-fileinput的API文档可参考[http://plugins.krajee.com/file-input](http://plugins.krajee.com/file-input)\n\n##使用说明\n###1、初始化\n如果需要在当前界面使用附件上传功能（非弹窗方式）则需要在头部引入相关的css和js文件\n- css文件\n```\n<link rel=\"stylesheet\" href=\"./resources/common/libs/fileinput/css/fileinput.min.css\">\n```\n- js文件\n```\n<script src=\"./resources/common/libs/fileinput/js/fileinput.js\"></script>\n<script src=\"./resources/common/libs/fileinput/js/locales/zh.js\"></script>\n<!--BaseFile组件-->\n<script src=\"./resources/common/js/base-file.js\"></script>\n```\nform表单上还需要配置`enctype=\"multipart/form-data\"`属性\n\n###2、弹窗方式调用\n\nBaseFile支持弹窗方式打开一个附件上传窗口，点击附件上传后，弹出窗口，上传附件关闭窗口后，上传的附件在type=file的控件回填，配置如下：\n- html代码\n```\n          <input type=\"hidden\" name=\"fileIds\" id=\"fileIds\">\n          <div class=\"form-group\">\n                <div class=\"btn btn-default btn-file\" id=\"uploadFile\">\n                    <i class=\"fa fa-paperclip\"></i> 上传附件(Max. 10MB)\n                </div>\n            </div>\n            <div class=\"form-group\" id=\"file_container\">\n                <input type=\"file\" name=\"file\"  id=\"attachment\">\n            </div>    \n```\n- js代码\n```javascript\n$(\"#uploadFile\").file({\n            title: \"请上传附件\",\n            fileinput: {\n                maxFileSize: 10240,\n                maxFileCount:3\n            },\n            fileIdContainer:\"[name=\'fileIds\']\",\n            showContainer:\'#attachment\',\n            //显示文件类型 edit=可编辑  detail=明细 默认为明细\n            showType:\'edit\',\n            //弹出窗口 执行上传附件后的回调函数(window:false不调用此方法)\n			window:true,\n            callback:function(fileIds,oldfileIds){\n                //更新fileIds\n                this.showFiles({\n                    fileIds:fileIds\n                });\n            }\n        });\n```\n###3、本地界面调用\n本地界面调用附件上传，通过以下方式配置：\n\n- html代码\n```html\n<div class=\"form-group\" id=\"file_container\">\n      <input type=\"file\" name=\"file\"  id=\"attachment\">\n</div>\n```\n- js代码\n```javascript\n    $(\"#attachment\").file({\n            fileinput: {\n                maxFileSize: 10240,\n                maxFileCount:3\n            },\n            fileIdContainer:\"[name=\'fileIds\']\",\n            window:false\n        });\n```\n###4、参数说明\n- `window` 默认为true,弹窗方式打开\n\n- `title` window=true时配置，弹窗的标题，默认为“文件上传”\n\n- `width` window=true时配置，弹窗的宽度，默认900\n\n- `winId` window=true时配置，弹出的id，默认为fileWin\n\n- `fileinput` Bootstrap-fileinput的配置参数，会覆盖默认配置，比如允许上传哪种类型的附件`allowedFileTypes`，允许上传最大附件大小`maxFileSize`，允许上传附件的个数`maxFileCount`等，具体的配置参数可以查询Bootstrap-fileinput的API文档。\n\n- `fileIdContainer` 必须，上传后的附件id存储的位置，id以逗号分隔\n\n- `showContainer` window=true必须配置，文件上传后回填的区域，window=false时如不配置，则取base-file的初始对象\n\n- `showType` window=true配置，值为`edit`或者`detail`,`edit`表示回填后可对数据进行删除、预览，`detail`只能显示，不能删除\n\n- `callback` window=true配置，关闭附件上传的窗口后执行的回调函数（比如更新当前的文件列表），`fileIds`,`oldfileIds`两个参数分别是更新后文件ids和更新前的文件ids\n\n- BaseFile默认配置，BaseFile的更多实现，请查看BaseFile源码\n```javascript\nBaseFile.prototype.default = {\n        winId: \"fileWin\",\n        width: 900,\n        title: \"文件上传\",\n        //通用文件上传界面\n        url: basePath + \"/file/uploader\",\n        //默认支持多文件上传\n        multiple: true,\n        //默认弹出附件上传窗口\n        window:true,\n        showType:\"detail\",\n        fileinput: {\n            language: \'zh\',\n            uploadUrl: basePath + \"/file/uploadMultipleFile\",\n            deleteUrl:basePath+\"/file/delete\",\n            uploadAsync:false,\n            validateInitialCount:true,\n            overwriteInitial: false,\n            allowedPreviewTypes: [\'image\'],\n            previewFileIcon:\'<i class=\"fa fa-file-o\"></i>\',\n            previewFileIconSettings: null,\n            slugCallback: function (text) {\n                var newtext=(!text||text==\'\') ? \'\' : String(text).replace(/[\\-\\[\\]\\/\\{}:;#%=\\(\\)\\*\\+\\?\\\\\\^\\$\\|<>&\"\']/g, \'_\');\n                //去除空格\n                return newtext.replace(/(^\\s+)|(\\s+$)/g,\"\").replace(/\\s/g,\"\");\n            }\n        }\n    }\n```\n\n', 'file', null, null, null, null);
INSERT INTO `tbl_markdown` VALUES ('402880ed5abdfec1015abe20b0e90000', '2017-03-12 00:08:17', '0', '2017-03-12 00:08:17', '0', 'BaseFile,Bootstrap-fileinput,附件上传,附件下载', '附件上传组件BaseFile使用说明-附件下载实现', '402880e456223e8f015625231d8e0003', '##介绍\n在上一篇文章中介绍了Bootstrap-fileinput组件的封装及使用，这篇文章延续上一篇文章，介绍了基于封装后的组件BaseFile中下载功能实现，也就是Bootstrap-fileinput中otherActionButtons中扩展自定义按钮。如下图所示：\n\n![下载按钮](http://img.blog.csdn.net/20170311234606744?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvanJuMTAxMg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)\n\n##实现步骤\n\n###1、修改fileinput.js源码\n（fileinput.min.js也要相应修改），修改此处是为了在文件尚未上传时，下载按钮不显示，如下图所示：\n![fileinput.js代码修改](http://img.blog.csdn.net/20170311235027047?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvanJuMTAxMg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)\n\n###2、配置下载按钮\n在BaseFile文件中，配置下载otherActionButtons属性。\n![下载按钮配置](http://img.blog.csdn.net/20170311235325208?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvanJuMTAxMg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)\n```\notherActionButtons:\'<button type=\"button\" class=\"kv-file-down btn btn-xs btn-default\" {dataKey} title=\"下载附件\"><i class=\"fa fa-cloud-download\"></i></button>\',\n```\n###3、下载按钮事件绑定\n在文件显示、上传文件成功，批量上传文件成功后绑定下载事件，每次把先解绑click事件后在绑定，防止重复绑定。\n![绑定下载事件](http://img.blog.csdn.net/20170311235701260?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvanJuMTAxMg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)\n\n相关代码：\n\n```javascript\n   BaseFile.prototype.showFiles=function(options){\n        //此处省略一大堆代码\n        //console.log(\"config==========\"+JSON.stringify(config));\n        fileComponet.fileinput(\'destroy\');\n        fileComponet.fileinput(config).on(\"filedeleted\",function (event,key) {\n            var newfids=self.deleteFileIds(key,self.options.fileIds);\n            self.options.fileIds=newfids;\n            self.updateFileIds();\n        }).on(\"fileuploaded\",function(event,data,previewId,index){\n            var newfids=self.addFileIds(data.response.fileIds,self.options.fileIds);\n            self.options.fileIds=newfids;\n            self.updateFileIds();\n            //otherActionButtons绑定事件 下载按钮绑定\n            self.downloadHandler(this);\n        }).on(\"filebatchuploadsuccess\",function (event,data,previewId,index) {\n            var newfids=self.addFileIds(data.response.fileIds,self.options.fileIds);\n            self.options.fileIds=newfids;\n            self.updateFileIds();\n            //otherActionButtons绑定事件\n            self.downloadHandler(this);\n        }).on(\"filezoomhidden\", function(event, params) {\n            $(document.body).removeClass(\'modal-open\');\n            $(document.body).css(\"padding-right\",\"0px\");\n        })\n        this.downloadHandler(fileComponet);\n    }\n\n\n    /**\n     * 其他按钮（如下载）绑定时间\n     */\n    BaseFile.prototype.downloadHandler=function(fileobj){\n        if(!fileobj)\n            fileobj=$(this.options.showContainer);\n        var objs=$(fileobj).data(\'fileinput\').$preview.find(\".kv-preview-thumb .kv-file-down\");\n        objs.unbind(\"click\");\n        objs.on(\"click\",function(){\n           //点击下载\n            window.location.href=basePath+\"/file/download/\"+$(this).data(\"key\");\n        });\n    }\n```\n弹出窗口方式上传文件绑定下载事件类似，可在file_uploader.html中找到相关代码。\n\n###4、后台下载文件方法\n\n```java\n  @RequestMapping(value=\"/download/{id}\",method = RequestMethod.GET)\n    public void downloadFile(@PathVariable(\"id\") String id,HttpServletRequest request,HttpServletResponse response) throws IOException {\n        SysFile sysfile = uploaderService.get(SysFile.class, id);\n\n        InputStream is = null;\n        OutputStream os = null;\n        File file = null;\n        try {\n            // PrintWriter out = response.getWriter();\n            if (sysfile != null)\n                file = new File(request.getRealPath(\"/\")+sysfile.getFilePath());\n            if (file != null && file.exists() && file.isFile()) {\n                long filelength = file.length();\n                is = new FileInputStream(file);\n                // 设置输出的格式\n                os = response.getOutputStream();\n                response.setContentType(\"application/x-msdownload\");\n                response.setContentLength((int) filelength);\n                response.addHeader(\"Content-Disposition\", \"attachment; filename=\\\"\" + new String(sysfile.getFileName().getBytes(\"GBK\"),// 只有GBK才可以\n                        \"iso8859-1\") + \"\\\"\");\n\n                // 循环取出流中的数据\n                byte[] b = new byte[4096];\n                int len;\n                while ((len = is.read(b)) > 0) {\n                    os.write(b, 0, len);\n                }\n            } else {\n                response.getWriter().println(\"<script>\");\n                response.getWriter().println(\" modals.info(\'文件不存在!\');\");\n                response.getWriter().println(\"</script>\");\n            }\n\n        } catch (IOException e) {\n            // e.printStackTrace();\n        } finally {\n            if (is != null) {\n                is.close();\n            }\n            if (os != null) {\n                os.close();\n            }\n        }\n    }\n```\n\n', null, null, null, null, null);
INSERT INTO `tbl_markdown` VALUES ('402881935a5f60d3015a5f7d43900000', '2017-02-21 15:05:29', '0', '2017-02-21 15:08:45', '1', '代码生成器,AdminEAP,AdminLTE,freemarker', '代码生成器的使用说明', '402880e456223e8f015625231d8e0003', 'AdminEAP为是基于AdminLTE改造的后台管理框架，包含了组件集成、CURD增删改查、系统工具、工作流、系统权限与安全等基本的系统管理功能和各种交互demo，项目源码已经在Github上开源，并部署到阿里云。\n\n**Github** : https://github.com/bill1012/AdminEAP\n\n**AdminEAP 官网**: http://www.admineap.com\n\n本文介绍使用freemarker模板，在AdminEAP框架下实现代码生成器，以实现CURD功能的快速生成，借助于代码生成器，基础的开发可以节约大量的时间，同时保持了编码的一致性。\n\n![这里写图片描述](http://img.blog.csdn.net/20170221133333679?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvanJuMTAxMg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)\n\n#使用步骤\n\n下面以Generator实体为例，介绍代码生成器的使用\n\n##1、编写实体类\n\n编写实体类，通过自定义注解Header声明相关属性\n\n![编写实体类](http://img.blog.csdn.net/20170221142700625?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvanJuMTAxMg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)\n\nHeader为自定义注解，开发者在建立外键关系时，可通过ManyToOne，也通过joinClass指定关联的实体，dataSource指定了在编辑时，该属性的数据来源，当为字典时，指定为字典父编码，也可以指定一个url。\n\n##2、生成xml配置\n\n启动项目后，输入queryId，实体名，业务名，点击“加载配置“，弹出xml配置窗口，可在此配置窗口配置查询xml\n\n![生成xml](http://img.blog.csdn.net/20170221144007125?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvanJuMTAxMg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)\n\nAdminEAP的所有数据列表都是基于xml配置的，可支持反射接口查询、sql配置的查询、离线查询（hql）。\n\n##3、重启系统使xml配置生效\n\n如果是首次生成xml文件（注意是文件，如果在已有的xml配置内容，则不需要重新，刷新系统即可，系统会读取更新的xml）\n\n![配置xml](http://img.blog.csdn.net/20170221144603370?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvanJuMTAxMg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)\n\n##4、配置具体参数\n\n输入queryId，点击”加载配置“后，系统读取xml的配置，并回填所有的默认配置，包括要生成的文件，文件路径等等，开发者也可以根据自己的需要更改参数配置。在界面右侧是实体的属性列表，开发者可以勾选数据再编辑时使用的控件类型，也可以勾选是是否作为查询条件，是否在编辑界面中维护等等。\n\n![配置具体参数](http://img.blog.csdn.net/20170221145011918?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvanJuMTAxMg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)\n\n更具体配置说明，包含在今后的技术文档（付费）中\n\n##5、根据freemarker模板生成代码\n\n从界面上收集好以上配置参数后，代码生成器将这些参数给配置好的freemarker模板，生成相关的源代码。\n\n以下是控制器模板controller.html\n```\npackage ${nameSpace}.controller;\n\nimport java.util.Date;\n\nimport javax.annotation.Resource;\nimport javax.servlet.http.HttpServletRequest;\n\nimport com.alibaba.fastjson.JSON;\nimport com.cnpc.framework.base.entity.Dict;\nimport com.cnpc.framework.utils.StrUtil;\nimport org.springframework.beans.BeanUtils;\nimport org.springframework.stereotype.Controller;\nimport org.springframework.web.bind.annotation.PathVariable;\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport org.springframework.web.bind.annotation.RequestMethod;\nimport org.springframework.web.bind.annotation.ResponseBody;\n\nimport com.cnpc.framework.base.service.BaseService;\nimport com.cnpc.framework.annotation.RefreshCSRFToken;\nimport com.cnpc.framework.annotation.VerifyCSRFToken;\nimport com.cnpc.framework.base.pojo.Result;\nimport ${className};\n\n/**\n* ${modelName}管理控制器\n* @author jrn\n* ${curTime?string(\"yyyy-MM-dd HH:mm:ss\")}由代码生成器自动生成\n*/\n@Controller\n@RequestMapping(\"/${htmlPrefix}\")\npublic class ${javaPrefix}Controller {\n\n    @Resource\n    <#if javaTypes?contains(\"service\")>\n    private ${javaPrefix}Service ${htmlPrefix}Service;\n    <#else>\n    private BaseService baseService;\n    </#if>\n\n    @RequestMapping(value=\"/list\",method = RequestMethod.GET)\n    public String list(){\n        return \"${businessPackage}/${htmlPrefix}_list\";\n    }\n\n    @RefreshCSRFToken\n    @RequestMapping(value=\"/edit\",method = RequestMethod.GET)\n    public String edit(String id,HttpServletRequest request){\n        request.setAttribute(\"id\", id);\n        return \"${businessPackage}/${htmlPrefix}_edit\";\n    }\n\n    @RequestMapping(value=\"/detail\",method = RequestMethod.GET)\n    public String detail(String id,HttpServletRequest request){\n        request.setAttribute(\"id\", id);\n        return \"${businessPackage}/${htmlPrefix}_detail\";\n    }\n\n    @RequestMapping(value=\"/get/{id}\",method = RequestMethod.POST)\n    @ResponseBody\n    public ${javaPrefix} get(@PathVariable(\"id\") String id){\n        return <#if javaTypes?contains(\"service\")>${htmlPrefix}Service<#else>baseService</#if>.get(${javaPrefix}.class, id);\n    }\n\n    @VerifyCSRFToken\n    @RequestMapping(value=\"/save\")\n    @ResponseBody\n    <#assign isObj=1>\n    <#list fields as field>\n        <#if field.columnName?contains(\".\")>\n            <#assign isObj=0>\n        </#if>\n    </#list>\n    public Result save(<#if isObj=1>${javaPrefix} ${htmlPrefix}<#else>String obj</#if>){\n        <#if isObj=0>\n        ${javaPrefix} ${htmlPrefix}= JSON.parseObject(obj,${javaPrefix}.class);\n        <#list fields as field>\n        <#if field.columnName?contains(\".\")>\n            <#assign fieldName=field.columnName?substring(0,field.columnName?index_of(\".\"))>\n            <#assign fieldN=fieldName?substring(0,1)?upper_case+fieldName?substring(1)>\n        ${htmlPrefix}.set${fieldN}(<#if javaTypes?contains(\"service\")>${htmlPrefix}Service<#else>baseService</#if>.get(${field.type}.class,${htmlPrefix}.get${fieldN}().getId()));\n        </#if>\n        </#list>\n        </#if>\n        if(StrUtil.isEmpty(${htmlPrefix}.getId())){\n        <#if dateFields?exists&&(dateFields?size>0)>\n            <#list dateFields as date>\n            ${date};\n            </#list>\n        </#if>\n            <#if javaTypes?contains(\"service\")>${htmlPrefix}Service<#else>baseService</#if>.save(${htmlPrefix});\n        }\n        else{\n        <#if leftFields?exists&&(leftFields?size>0)>\n            ${javaPrefix} ${htmlPrefix}_old=<#if javaTypes?contains(\"service\")>${htmlPrefix}Service<#else>baseService</#if>.get(${javaPrefix}.class,${htmlPrefix}.getId());\n            BeanUtils.copyProperties(${htmlPrefix},${htmlPrefix}_old, <#list leftFields as str><#if str_index==0>\"${str}\"<#else>,\"${str}\"</#if></#list>);\n            <#if leftDates?exists&&(leftDates?size>0)>\n                <#list leftDates as ld>\n             ${ld};\n                </#list>\n            </#if>\n            ${htmlPrefix}_old.setUpdateDateTime(new Date());\n            <#if javaTypes?contains(\"service\")>${htmlPrefix}Service<#else>baseService</#if>.update(${htmlPrefix}_old);\n            <#else>\n            ${htmlPrefix}.setUpdateDateTime(new Date());\n            <#if javaTypes?contains(\"service\")>${htmlPrefix}Service<#else>baseService</#if>.update(${htmlPrefix});\n            </#if>\n        }\n        return new Result(true);\n    }\n    \n    @RequestMapping(value=\"/delete/{id}\",method = RequestMethod.POST)\n    @ResponseBody\n    public Result delete(@PathVariable(\"id\") String id){\n        ${javaPrefix} ${htmlPrefix}=this.get(id);\n        try{\n            baseService.delete(${htmlPrefix});\n            return new Result();\n        }\n        catch(Exception e){\n            return new Result(false,\"该数据已经被引用，不可删除\");\n        }\n    }\n\n}\n\n```\n\n##6、修改生成的代码并重启系统查看效果\n\n开发者根据需要修改生成的代码，并重启系统，查看效果，如果需要分配权限，请先将生成的菜单功能授权，才可有相应的菜单。\n\n![代码生成器-列表](http://img.blog.csdn.net/20170221145826165?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvanJuMTAxMg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)\n\n![代码生成-编辑](http://img.blog.csdn.net/20170221145859391?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvanJuMTAxMg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)\n\n通过代码生成生成了一个业务功能的CURD操作。\n\n##7、总结\n\n以上操作步骤，在开发环境使用，在项目中使用代码生成器的主要工作量在编写实体，两次重启系统（如果在之前xml追加内容，则是一次重启）上，其余采用默认配置即可。\n\n欢迎大家使用AdminEAP框架，如有意见建议请登录[AdminEAP官网](http://www.admineap.com)给我提意见。\n\n\n', 'code', null, null, null, null);
INSERT INTO `tbl_markdown` VALUES ('8a8a81425a118296015a118d0fda0000', '2017-02-06 11:52:21', '0', '2017-02-06 14:36:07', '3', 'Bootstrap modal,模态窗体', '模态窗体Modals使用说明', '402880e456223e8f015625231d8e0003', '##功能说明\n在AdminEAP框架中，封装了Bootstrap modal窗体，提供以下功能\n- info(提示)\n- correct(成功)\n- warn(警告)\n- error(错误)\n- confirm(确认)\n- popup(提示，无按钮)\n- openWin(模态窗体)\n\n##使用示例\n引入文件\n`<script type=\"text/javascript\" src=\"${basePath}/resources/common/js/base-modal.js\"></script>`\n###1.提示\n<div style=\"padding-bottom:20px;\">\n<button class=\'btn btn-info\' data-btn-type=\'info\' onclick=\'modals.info(\"提示信息\")\'>提示</button>\n</div>\n\n```\n<button class=\'btn btn-info\' data-btn-type=\'info\' onclick=\'info()\'>提示</button>\n\n<script>\n  function info(){\n     modals.info(\'提示信息\');\n  }\n</script>\n```\n###2.成功\n<div style=\"padding-bottom:20px;\">\n<button class=\'btn btn-success\' data-btn-type=\'success\' onclick=\'modals.correct(\"操作成功信息\")\'>成功</button>\n</div>\n\n```\n<button class=\'btn btn-success\' data-btn-type=\'success\' onclick=\'correct()\'>成功</button>\n\n<script>\n  function correct(){\n     modals.correct(\'操作成功信息\');\n  }\n</script>\n```\n###3.警告\n<div style=\"padding-bottom:20px;\">\n<button class=\'btn btn-warning\' data-btn-type=\'warn\' onclick=\'modals.warn(\"警告信息\")\'>警告</button>\n</div>\n\n```\n<button class=\'btn btn-warning\' data-btn-type=\'warn\' onclick=\'warn()\'>警告</button>\n\n<script>\n  function warn(){\n     modals.warn(\'警告信息\');\n  }\n</script>\n```\n###4.错误\n<div style=\"padding-bottom:20px;\">\n<button class=\'btn btn-danger\' data-btn-type=\'error\' onclick=\'modals.error(\"错误提示信息\")\'>错误</button>\n</div>\n\n```\n<button class=\'btn btn-danger\' data-btn-type=\'error\' onclick=\'error()\'>错误</button>\n\n<script>\n  function error(){\n     modals.error(\'错误提示信息\');\n  }\n</script>\n```\n###5.确认提示框\n<div style=\"padding-bottom:20px;\">\n<button class=\'btn btn-primary\' data-btn-type=\'confirm\' onclick=\'modals.confirm(\"错误提示信息\",function(){alert(\"do something\")})\'>确认提示框</button>\n</div>\n\n```\n<button class=\'btn btn-primary\' data-btn-type=\'confirm\' onclick=\'confirm()\'>确认提示框</button>\n\n<script>\n  function confirm(){\n     modals.confirm(\'错误提示信息\'，function(){\n	    alert(\'do something\');\n	 });\n  }\n</script>\n```\n###6.自定义模态窗口\n<div style=\"padding-bottom:20px;\">\n<button class=\'btn btn-info\' data-btn-type=\'window\' onclick=\'modals.openWin({winId:\"userWin\", title:\"新增用户\", width:\"900px\", url:basePath+\"/user/edit\"})\'>自定义模态窗口</button>\n</div>\n\n```\n<button class=\'btn btn-info\' data-btn-type=\'window\' onclick=\'openWin()\'>自定义模态窗口</button>\n\n<script>\n  function openWin(){\n     modals.openWin({\n	    winId:\'userWin\',\n        title:\'新增用户\',\n        width:\'900px\',\n        url:basePath+\"/user/edit\"\n	 });\n  }\n</script>\n```\n\n <button class=\"btn btn-primary\" id=\"btn_msg\" style=\"width: 100%\" onclick=\'modals.openWin({ \n                winId:\"messageWin\",\n                title:\"给【billJiang】留言\",\n                width:\"750px\",\n                url:basePath+\"/message/mail/edit\"\n            });\'><i class=\"fa fa-commenting\"></i>&nbsp;&nbsp;我是billJiang，如果有任何意见/建议/创意/问题/想法，点击该按钮给我留言</button>\n', 'modal', null, null, null, null);
INSERT INTO `tbl_markdown` VALUES ('8a8a826c5ac5e9a4015ac65cc9630000', '2017-03-13 14:30:54', '0', '2017-03-13 15:03:48', '1', 'CSRF,AdminEAP', 'AdminEAP框架防范CSRF', '402880e456223e8f015625231d8e0003', '##介绍\n\nCSRF（Cross-site request forgery跨站请求伪造，也被称为“One Click Attack”或者Session Riding，通常缩写为CSRF或者XSRF，是一种对网站的恶意利用。尽管听起来像跨站脚本（XSS），但它与XSS非常不同，并且攻击方式几乎相左。XSS利用站点内的信任用户，而CSRF则通过伪装来自受信任用户的请求来利用受信任的网站。与XSS攻击相比，CSRF攻击往往不大流行（因此对其进行防范的资源也相当稀少）和难以防范，所以被认为比XSS更具危险性。\n\n本文介绍在AdminEAP框架下，如何防范CSRF。\n\n##思路\n\n具体思路：\n\n1、跳转页面前生成随机token，并存放在session中\n\n2、form中将token放在隐藏域中，保存时将token放头部一起提交\n\n3、获取头部token，与session中的token比较，一致则通过，否则不予提交\n\n4、生成新的token，并传给前端\n\n##实现\n1、Spring MVC中配置拦截器：\n```xml\n<mvc:interceptors>\n	    <!-- csrf攻击防御 -->\n		<mvc:interceptor>\n			<!-- 需拦截的地址 -->\n			<mvc:mapping path=\"/**\" />\n			<!-- 需排除拦截的地址 -->\n			<mvc:exclude-mapping path=\"/resources/**\" />\n			<mvc:exclude-mapping path=\"/uploadPath/**\" />\n			<!-- <mvc:exclude-mapping path=\"/user/user_login\" />\n			<mvc:exclude-mapping path=\"/user/login\" /> -->\n			<bean class=\"com.cnpc.framework.interceptor.CSRFInterceptor\" />\n		</mvc:interceptor>\n	</mvc:interceptors>\n```\n2、拦截器的具体实现：(具体实现代码详见CSRFInterceptor.java)\n\n1）进入方法之前，校验CSRF，校验失败，刷新csrftoken，向前端抛出错误信息\n>在preHandler方法上读取verifyCSRFToken注解配置，如果未配置直接通过验证，如果配置了，为GET页面跳转请求（不含@ResponseBody）则刷新session中的csrftoken,并向前端弹出错误信息。如果为ajax请求，刷新session中csrftoken，并将新的csrftoken写入responseBody,并在前端$.ajax方法中success方法中进行处理和提示(在success处理token不一致)\n\n```javascript\n$.ajax({\n		type : \'post\',\n		async : false,\n		url : url,\n		data : params,\n		dataType : \'json\',\n		headers:headers,\n		success : function(data, status) {\n			result = data;\n			if(data&&data.code&&data.code==\'101\'){\n				modals.error(\"操作失败，请刷新重试，具体错误：\"+data.message);\n				return false;\n			}\n			if (callback) { \n				callback.call(this, data, status);\n			}\n		},\n		error : function(err, err1, err2) {\n			console.log(err.responseText);\n		    if(err && err.readyState && err.readyState == \'4\'){\n                var responseBody = err.responseText;\n                if(responseBody){   \n                	 responseBody = \"{\'retData\':\"+responseBody;\n                     var resJson = eval(\'(\' + responseBody + \')\');\n                     $(\"#csrftoken\").val(resJson.csrf.CSRFToken);\n                     this.success(resJson.retData, 200);\n                }\n                return ;\n            } 		    \n			modals.error({\n				text : JSON.stringify(err) + \'<br/>err1:\' + JSON.stringify(err1) + \'<br/>err2:\' + JSON.stringify(err2),\n				large : true\n			});\n		}\n	});\n```\n2）校验CSRF成功后，在postHandler中刷新csrftoken\n>读取RefreshCSRFToken注解，如果配置了该注解刷新session中的csrftoken，如果配置了VerifyCSRFToken注解，刷新前端csrftoken(防止重复提交)，在ajaxPost的error中处理刷新后的csrftoken，回填到页面。\n\n##实例\n用户编辑，用户编辑前刷新csrftoken,提交时校验，如果多次提交，则弹出错误提示：\n`CSRF ERROR:无效的token，或者token过期`\n```java \n    @RefreshCSRFToken\n    @RequestMapping(method = RequestMethod.GET, value = \"/edit\")\n    private String edit(String id, HttpServletRequest request) {\n\n        request.setAttribute(\"id\", id);\n        return \"base/user/user_edit\";\n    }\n\n    @VerifyCSRFToken\n    @RequestMapping(method = RequestMethod.POST, value = \"/save\")\n    @ResponseBody\n    private Result saveUser(User user, HttpServletRequest request) {\n        if (StrUtil.isEmpty(user.getId())) {\n            String userId = userService.save(user).toString();\n            //头像和用户管理\n            userService.updateUserAvatar(user, request.getRealPath(\"/\"));\n        } else {\n            user.setUpdateDateTime(new Date());\n            userService.update(user);\n        }\n        return new Result(true);\n    }\n\n```\n', 'csrf', null, null, null, null);
INSERT INTO `tbl_markdown` VALUES ('8a8a826c5ac5e9a4015ac67d02e00001', '2017-03-13 15:06:05', '0', '2017-03-13 15:58:37', '2', 'render,fnRender,tooltip', 'AdminEAP框架数据列表render的五种方式', '402880e456223e8f015625231d8e0003', '##介绍\n在AdminEAP框架中，数据列表使用Bootstrap-DataTables组件（基于Jquery-DataTables）,在AdminEAP框架中进行二次封装，其中有些数据列需要在原来的数据基础上进行二次渲染，分别支持以下几种方式：\n- render （type=eq） 固定值的\'翻译\'\n- render (type=window) 弹出窗体\n- render (type=link)  超链接\n- tooltip 鼠标悬停提示\n- fnRender 渲染回调函数\n\n##xml配置示例\n以下是以上几个属性在xml配置中的示例和部分说明。\n```xml\n <query id=\"message_draft\" key=\"id\" tableName=\"我的草稿箱\" className=\"com.cnpc.tool.message.entity.Message\" widthType=\"px\">\n        <column key=\"rowIndex\" header=\"序号\" width=\"80\"/>\n        <column key=\"id\" header=\"id\" hidden=\"true\"/>\n        <column key=\"sendUser\" header=\"发送人\" hidden=\"true\" classType=\"String\" allowSort=\"false\" width=\"150\"/>\n        <!--enableTootip(是否显示) maxLen(最大长度默认10，可在dataTables.js修改默认值) tooltip（指定tooltip来源，可不配，缺省为本身） 长文本鼠标悬停显示-->\n        <!--弹窗方式render=\"type=window\"使用;所需参数同modals.openWin的参数-->\n        <!--<column key=\"sendSubject\" header=\"主题\" classType=\"String\" align=\"left\" render=\"type=window,winId=messageEditWin,url=/message/edit?id=[id],title=弹窗测试[sendSubject]\" enableTooltip=\"true\" maxLen=\"20\" allowSort=\"true\" width=\"150\"/>-->\n        <!--超链接方式 render=\"type=link\",method方法名称，params方法参数,分号;分隔，如不需要参数params为可为空，默认注入rowId-->\n        <!--<column key=\"sendSubject\" header=\"主题\" classType=\"String\" align=\"left\" render=\"type=link,method=loadPage,params=\'/message/edit?id=[id]\';\'#contentBody\'\" enableTooltip=\"true\" maxLen=\"20\" allowSort=\"true\" width=\"150\"/>-->\n        <column key=\"sendSubject\" header=\"主题\" classType=\"String\" align=\"left\" render=\"type=link,method=showDetail\" enableTooltip=\"true\" maxLen=\"20\" allowSort=\"true\" width=\"150\"/>    \n        <column key=\"sendTime\" header=\"发送时间\" classType=\"Date\" allowSort=\"true\" dateFormat=\"yyyy-mm-dd hh:ii\"  operator=\"between\" width=\"150\"/>\n        <column key=\"messageType\" header=\"类型\" classType=\"String\" align=\"left\" width=\"50\" fnRender=\"fnRenderMessageType\"/>\n        <!--isServerCondition=true value=0注入服务器端查询条件，也可在前端以type=hidden隐藏域的方式注入-->\n        <column key=\"messageStatus\" header=\"状态\" isServerCondition=\"true\" value=\"0\" classType=\"String\" allowSort=\"false\" width=\"50\" fnRender=\"fnRenderMessageStatus\"/>\n        <column key=\"messageFlag\" header=\"标记\" classType=\"String\" allowSort=\"true\" width=\"150\" fnRender=\"fnRenderMessageFlag\"/>\n        <column key=\"fileIds\" header=\"附件\" classType=\"String\" width=\"150\" fnRender=\"fnRenderFileIds\"/>     \n		  <!--<column key=\"deleted\" header=\"是否删除\" classType=\"String\" allowSort=\"true\" width=\"150\" render=\"0=可用,1=禁用\"/>-->\n    </query>\n```\n\n## 使用说明\n\n### render(type=eq) \n示例：\n(1) render=\"type=eq,0=可用，1=禁用\" (其中type=eq可缺省)\n(2) render=\"1=禁用,else=可用\"\n配置以后，组件会把相应值翻译为对应值。\n\n`type=eq` eq类型的render,可缺省\n\n### render(type=window)\n配置后，点击该列值，会弹出一个模态窗体，将配置的所有参数传递给modals.openWin方法。\n示例：\n(1) render=\"type=window,winId=messageEditWin,url=/message/edit?id=[id],title=弹窗测试[sendSubject]\n\n其中[id]，[sendSubject]会被替换为当前行对象的该列的值，是指定列（column key=\'id\'和column key=\'sendSubject\'）的值\n\n`type=window`:窗体render,必配\n`winId`:窗体id,必配\n`url`:url路径,必配\n\n其他参数通modals.openWin参数，如width等等\n\n###render(type=link)\n配置某列的超链接。配置形式是调用onclick方法，建议比较复杂的超链接采用这种配置。\n示例：\n(1) render=\"type=link,method=loadPage,params=\'/message/edit?id=[id]\';\'#contentBody\'\"\n(2) render=\"type=link,method=showDetail\"\n\n其中[id]会被替换为当前行对象的该列的值，是指定列（column key=\'id\'）的值\n\n`type=link`:超链接render,必配\n`method`:超链接执行方法名称，必配\n`params`：方法执行参数，分号(`;`)分隔,若缺省，会自动将当前行`id`注入方法，及query key执行的值\n\n`需要注意的是`，前台需要相对应的js方法，比如，以上两个实例需要前端由loadPage(url)和shoWDetail(rowId)方法。\n\n###fnRender\n更复杂的前端展示和业务逻辑，需要在前端编写相应的业务逻辑\n示例：\n（1）：fnRender=\"fnRenderMessageStatus\"\n\n对应前端脚本\n```\nfunction fnRenderMessageStatus(value, type, rowObj，oSetting) {\n    if (value == 0) {\n        return \'<span class=\"label label-warning\">草稿</span>\'\n    } else if (value == 4) {\n        return \'<span class=\"label label-success\">已发送</span>\';\n    }\n    return value;\n}\n```\n指定的fnRender方法中会通过Jquery-DataTables接口自动注入四个参数\n\n`value`: 当前单元格的值\n`type`:单元格样式，一般为display\n`rowObj`:当前行对象，可通过rowObj.name等形式获取其他列的值\n`oSetting`:表格全局配置对象\n\n###tooltip\n在表格中显示长文本的时候，有时候需要截断，然后鼠标悬停时显示，可采用该配置\n\n示例：\n（1）enableTooltip=\"true\" maxLen=\"20\" tooltip=\"id\"\n（`后续将修改成 render=\"type=tooltip,maxLen=20,tooltip=[id]\"`）\n\n`enableTooltip`:默认为false，只有配置true才生效，才会执行截断\n`maxLen`:截断长度，如果不配置，默认为20\n`tooltip`:鼠标悬停的数据来源，如不配默认为当前数据本身\n\n\n\n\n\n\n\n\n\n\n      ', 'render', null, null, null, null);
INSERT INTO `tbl_markdown` VALUES ('8a8a83295a8300ff015a839845890000', '2017-02-28 15:21:19', '0', '2017-02-28 15:21:19', '0', 'quartz,自动任务,支持集群', '基于Quartz的自动任务调度器（支持集群）', '402880e456223e8f015625231d8e0003', '本文介绍在AdminEAP框架中，集成Quartz，来进行自动任务管理，以便随时监控系统中当前的任务，并能在线新增任务、暂停任务、恢复任务、删除任务、立即运行任务。\n\n在quartz中一个trigger可对应一个job，job可以有多个trigger，trigger分为CronTrigger与SimpleTrigger；CronTrigger通过Cron表达式配置定时任务，SimpleTrigger则配置一些简单重复的定时任务。\n\nquartz分为基于内存的方式和基于数据库的方式，在本文中使用了基于数据库的方式（支持集群）。这样提高了系统的可靠性，在配置时，请保持应用服务器的时钟同步。\n\n关于具体集成quartz的步骤，可参考AdminEAP源码，也可参考本人之前的博客：\n\n-  [ Spring+监听器+Quartz集群（1）——基本配置 ](http://blog.csdn.net/jrn1012/article/details/45582985)\n- [ Spring+监听器+Quartz集群（2）——quartz2.2.1创建表结构（oracle） ](http://blog.csdn.net/jrn1012/article/details/45583731)\n- [ Spring+监听器+Quartz集群（3）——任务管理 ](http://blog.csdn.net/jrn1012/article/details/45583801)\n\n以下为实现的任务调度管理器：\n\n![任务调度器](http://img.blog.csdn.net/20170228145717110?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvanJuMTAxMg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)\n\n##使用说明\n\nAdminEAP框架使用mysql数据库，所以要在mysql中创建quartz所需要的表。\n###1、mysql创建表结构\n\n```\n    --  \n    -- A hint submitted by a user: Oracle DB MUST be created as \"shared\" and the   \n    -- job_queue_processes parameter  must be greater than 2  \n    -- However, these settings are pretty much standard after any  \n    -- Oracle install, so most users need not worry about this.  \n    --  \n    -- Many other users (including the primary author of Quartz) have had success  \n    -- runing in dedicated mode, so only consider the above as a hint ;-)  \n    --  \n      \n    delete from qrtz_fired_triggers;  \n    delete from qrtz_simple_triggers;  \n    delete from qrtz_simprop_triggers;  \n    delete from qrtz_cron_triggers;  \n    delete from qrtz_blob_triggers;  \n    delete from qrtz_triggers;  \n    delete from qrtz_job_details;  \n    delete from qrtz_calendars;  \n    delete from qrtz_paused_trigger_grps;  \n    delete from qrtz_locks;  \n    delete from qrtz_scheduler_state;  \n      \n    drop table qrtz_calendars;  \n    drop table qrtz_fired_triggers;  \n    drop table qrtz_blob_triggers;  \n    drop table qrtz_cron_triggers;  \n    drop table qrtz_simple_triggers;  \n    drop table qrtz_simprop_triggers;  \n    drop table qrtz_triggers;  \n    drop table qrtz_job_details;  \n    drop table qrtz_paused_trigger_grps;  \n    drop table qrtz_locks;  \n    drop table qrtz_scheduler_state;  \n      \n    -- 存储每一个已配置的 Job 的详细信息  \n    CREATE TABLE qrtz_job_details  \n      (  \n        SCHED_NAME VARCHAR2(120) NOT NULL,  \n        JOB_NAME  VARCHAR2(200) NOT NULL,  \n        JOB_GROUP VARCHAR2(200) NOT NULL,  \n        DESCRIPTION VARCHAR2(250) NULL,  \n        JOB_CLASS_NAME   VARCHAR2(250) NOT NULL,   \n        IS_DURABLE VARCHAR2(1) NOT NULL,  \n        IS_NONCONCURRENT VARCHAR2(1) NOT NULL,  \n        IS_UPDATE_DATA VARCHAR2(1) NOT NULL,  \n        REQUESTS_RECOVERY VARCHAR2(1) NOT NULL,  \n        JOB_DATA BLOB NULL,  \n        CONSTRAINT QRTZ_JOB_DETAILS_PK PRIMARY KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)  \n    );  \n    --  存储已配置的 Trigger 的信息  \n    CREATE TABLE qrtz_triggers  \n      (  \n        SCHED_NAME VARCHAR2(120) NOT NULL,  \n        TRIGGER_NAME VARCHAR2(200) NOT NULL,  \n        TRIGGER_GROUP VARCHAR2(200) NOT NULL,  \n        JOB_NAME  VARCHAR2(200) NOT NULL,   \n        JOB_GROUP VARCHAR2(200) NOT NULL,  \n        DESCRIPTION VARCHAR2(250) NULL,  \n        NEXT_FIRE_TIME NUMBER(13) NULL,  \n        PREV_FIRE_TIME NUMBER(13) NULL,  \n        PRIORITY NUMBER(13) NULL,  \n        TRIGGER_STATE VARCHAR2(16) NOT NULL,  \n        TRIGGER_TYPE VARCHAR2(8) NOT NULL,  \n        START_TIME NUMBER(13) NOT NULL,  \n        END_TIME NUMBER(13) NULL,  \n        CALENDAR_NAME VARCHAR2(200) NULL,  \n        MISFIRE_INSTR NUMBER(2) NULL,  \n        JOB_DATA BLOB NULL,  \n        CONSTRAINT QRTZ_TRIGGERS_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),  \n        CONSTRAINT QRTZ_TRIGGER_TO_JOBS_FK FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)   \n          REFERENCES QRTZ_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP)   \n    );  \n    -- 存储简单的 Trigger，包括重复次数，间隔，以及已触的次数  \n    CREATE TABLE qrtz_simple_triggers  \n      (  \n        SCHED_NAME VARCHAR2(120) NOT NULL,  \n        TRIGGER_NAME VARCHAR2(200) NOT NULL,  \n        TRIGGER_GROUP VARCHAR2(200) NOT NULL,  \n        REPEAT_COUNT NUMBER(7) NOT NULL,  \n        REPEAT_INTERVAL NUMBER(12) NOT NULL,  \n        TIMES_TRIGGERED NUMBER(10) NOT NULL,  \n        CONSTRAINT QRTZ_SIMPLE_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),  \n        CONSTRAINT QRTZ_SIMPLE_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)   \n        REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)  \n    );  \n    -- 存储 Cron Trigger，包括 Cron 表达式和时区信息  \n    CREATE TABLE qrtz_cron_triggers  \n      (  \n        SCHED_NAME VARCHAR2(120) NOT NULL,  \n        TRIGGER_NAME VARCHAR2(200) NOT NULL,  \n        TRIGGER_GROUP VARCHAR2(200) NOT NULL,  \n        CRON_EXPRESSION VARCHAR2(120) NOT NULL,  \n        TIME_ZONE_ID VARCHAR2(80),  \n        CONSTRAINT QRTZ_CRON_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),  \n        CONSTRAINT QRTZ_CRON_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)   \n          REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)  \n    );  \n    CREATE TABLE qrtz_simprop_triggers  \n      (            \n        SCHED_NAME VARCHAR2(120) NOT NULL,  \n        TRIGGER_NAME VARCHAR2(200) NOT NULL,  \n        TRIGGER_GROUP VARCHAR2(200) NOT NULL,  \n        STR_PROP_1 VARCHAR2(512) NULL,  \n        STR_PROP_2 VARCHAR2(512) NULL,  \n        STR_PROP_3 VARCHAR2(512) NULL,  \n        INT_PROP_1 NUMBER(10) NULL,  \n        INT_PROP_2 NUMBER(10) NULL,  \n        LONG_PROP_1 NUMBER(13) NULL,  \n        LONG_PROP_2 NUMBER(13) NULL,  \n        DEC_PROP_1 NUMERIC(13,4) NULL,  \n        DEC_PROP_2 NUMERIC(13,4) NULL,  \n        BOOL_PROP_1 VARCHAR2(1) NULL,  \n        BOOL_PROP_2 VARCHAR2(1) NULL,  \n        CONSTRAINT QRTZ_SIMPROP_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),  \n        CONSTRAINT QRTZ_SIMPROP_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)   \n          REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)  \n    );  \n    -- Trigger 作为 Blob 类型存储(用于 Quartz 用户用 JDBC 创建他们自己定制的 Trigger 类型，<span style=\"color:#800080;\">JobStore</span> 并不知道如何存储实例的时候)  \n    CREATE TABLE qrtz_blob_triggers  \n      (  \n        SCHED_NAME VARCHAR2(120) NOT NULL,  \n        TRIGGER_NAME VARCHAR2(200) NOT NULL,  \n        TRIGGER_GROUP VARCHAR2(200) NOT NULL,  \n        BLOB_DATA BLOB NULL,  \n        CONSTRAINT QRTZ_BLOB_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),  \n        CONSTRAINT QRTZ_BLOB_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)   \n            REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)  \n    );  \n    -- 以 Blob 类型存储 Quartz 的 Calendar 信息  \n    CREATE TABLE qrtz_calendars  \n      (  \n        SCHED_NAME VARCHAR2(120) NOT NULL,  \n        CALENDAR_NAME  VARCHAR2(200) NOT NULL,   \n        CALENDAR BLOB NOT NULL,  \n        CONSTRAINT QRTZ_CALENDARS_PK PRIMARY KEY (SCHED_NAME,CALENDAR_NAME)  \n    );  \n    -- 存储已暂停的 Trigger 组的信息   \n    CREATE TABLE qrtz_paused_trigger_grps  \n      (  \n        SCHED_NAME VARCHAR2(120) NOT NULL,  \n        TRIGGER_GROUP  VARCHAR2(200) NOT NULL,   \n        CONSTRAINT QRTZ_PAUSED_TRIG_GRPS_PK PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP)  \n    );  \n    -- 存储与已触发的 Trigger 相关的状态信息，以及相联 Job 的执行信息  \n    CREATE TABLE qrtz_fired_triggers   \n      (  \n        SCHED_NAME VARCHAR2(120) NOT NULL,  \n        ENTRY_ID VARCHAR2(95) NOT NULL,  \n        TRIGGER_NAME VARCHAR2(200) NOT NULL,  \n        TRIGGER_GROUP VARCHAR2(200) NOT NULL,  \n        INSTANCE_NAME VARCHAR2(200) NOT NULL,  \n        FIRED_TIME NUMBER(13) NOT NULL,  \n        PRIORITY NUMBER(13) NOT NULL,  \n        STATE VARCHAR2(16) NOT NULL,  \n        JOB_NAME VARCHAR2(200) NULL,  \n        JOB_GROUP VARCHAR2(200) NULL,  \n        IS_NONCONCURRENT VARCHAR2(1) NULL,  \n        REQUESTS_RECOVERY VARCHAR2(1) NULL,  \n        CONSTRAINT QRTZ_FIRED_TRIGGER_PK PRIMARY KEY (SCHED_NAME,ENTRY_ID)  \n    );  \n    -- 存储少量的有关 Scheduler 的状态信息，和别的 Scheduler 实例(假如是用于一个集群中)  \n    CREATE TABLE qrtz_scheduler_state   \n      (  \n        SCHED_NAME VARCHAR2(120) NOT NULL,  \n        INSTANCE_NAME VARCHAR2(200) NOT NULL,  \n        LAST_CHECKIN_TIME NUMBER(13) NOT NULL,  \n        CHECKIN_INTERVAL NUMBER(13) NOT NULL,  \n        CONSTRAINT QRTZ_SCHEDULER_STATE_PK PRIMARY KEY (SCHED_NAME,INSTANCE_NAME)  \n    );  \n    -- 存储程序的悲观锁的信息(假如使用了悲观锁)  \n    CREATE TABLE qrtz_locks  \n      (  \n        SCHED_NAME VARCHAR2(120) NOT NULL,  \n        LOCK_NAME  VARCHAR2(40) NOT NULL,   \n        CONSTRAINT QRTZ_LOCKS_PK PRIMARY KEY (SCHED_NAME,LOCK_NAME)  \n    );  \n      \n    create index idx_qrtz_j_req_recovery on qrtz_job_details(SCHED_NAME,REQUESTS_RECOVERY);  \n    create index idx_qrtz_j_grp on qrtz_job_details(SCHED_NAME,JOB_GROUP);  \n      \n    create index idx_qrtz_t_j on qrtz_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP);  \n    create index idx_qrtz_t_jg on qrtz_triggers(SCHED_NAME,JOB_GROUP);  \n    create index idx_qrtz_t_c on qrtz_triggers(SCHED_NAME,CALENDAR_NAME);  \n    create index idx_qrtz_t_g on qrtz_triggers(SCHED_NAME,TRIGGER_GROUP);  \n    create index idx_qrtz_t_state on qrtz_triggers(SCHED_NAME,TRIGGER_STATE);  \n    create index idx_qrtz_t_n_state on qrtz_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE);  \n    create index idx_qrtz_t_n_g_state on qrtz_triggers(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE);  \n    create index idx_qrtz_t_next_fire_time on qrtz_triggers(SCHED_NAME,NEXT_FIRE_TIME);  \n    create index idx_qrtz_t_nft_st on qrtz_triggers(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME);  \n    create index idx_qrtz_t_nft_misfire on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME);  \n    create index idx_qrtz_t_nft_st_misfire on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE);  \n    create index idx_qrtz_t_nft_st_misfire_grp on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE);  \n      \n    create index idx_qrtz_ft_trig_inst_name on qrtz_fired_triggers(SCHED_NAME,INSTANCE_NAME);  \n    create index idx_qrtz_ft_inst_job_req_rcvry on qrtz_fired_triggers(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY);  \n    create index idx_qrtz_ft_j_g on qrtz_fired_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP);  \n    create index idx_qrtz_ft_jg on qrtz_fired_triggers(SCHED_NAME,JOB_GROUP);  \n    create index idx_qrtz_ft_t_g on qrtz_fired_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP);  \n    create index idx_qrtz_ft_tg on qrtz_fired_triggers(SCHED_NAME,TRIGGER_GROUP);  \n```\n###2、创建SimpleTrigger任务\n![SimpleTrigger任务](http://img.blog.csdn.net/20170228145913674?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvanJuMTAxMg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)\n\n创建SimpleTrigger任务不用指定Cron表达式，可根据重复类型填写相应的重复次数与重复间隔（可以任务重复次数repeatCount与重复间隔repeatInterval是为重复类型指定参数）。\n\n###3、创建CronTrigger任务\n![CronTrigger任务](http://img.blog.csdn.net/20170228150302971?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvanJuMTAxMg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)\n\nCronTrigger任务则是通过Cron表达式创建的任务，只需要在界面配置必填选项和Cron表达式即可。\n\n###4、修改任务\n点击修改任务后，可进入到任务编辑界面，修改任务相关参数（任务名和任务组不可修改）。系统会通过rescheduleJob方法重新调度。\n\n```\n     // trigger已经存在，更新相应的定时设置\n     TriggerBuilder triggerBuilder = trigger.getTriggerBuilder();\n     trigger = setTriggerParam(job, triggerKey, triggerBuilder, trigger);\n     // 按新的trigger重新设置job执行\n     scheduler.rescheduleJob(triggerKey, trigger);\n```\n\n###5、暂停任务\n点击暂停任务，任务将挂起，任务状态由\"NORMAL\"变为“PAUSE”\n\n```java\n      /**\n     * 暂停\n     */\n    public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {\n        Scheduler scheduler = schedulerFactoryBean.getScheduler();\n        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());\n        scheduler.pauseJob(jobKey);\n    }\n\n```\n\n###6、恢复任务\n点击回复任务，任务将变为可执行状态，任务状态由\"PAUSE\"变为“NORMAL”\n\n```\n  /**\n     * 恢复运行\n     */\n    public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {\n        Scheduler scheduler = schedulerFactoryBean.getScheduler();\n        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());\n        scheduler.resumeJob(jobKey);\n    }\n\n```\n\n###7、删除任务\n从数据库中删除任务\n\n```\n    /**\n     * 删除\n     */\n    public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {\n        Scheduler scheduler = schedulerFactoryBean.getScheduler();\n        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());\n        scheduler.deleteJob(jobKey);\n    }\n```\n    \n\n###8、立即运行一次\n点击“立即运行一次”，任务则马上执行一次\n```\n    /**\n     * 运行一次\n     */\n    public void triggerJob(ScheduleJob scheduleJob) throws SchedulerException {\n        Scheduler scheduler = schedulerFactoryBean.getScheduler();\n        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());\n        scheduler.triggerJob(jobKey);\n    }\n\n```\n\n\n\n\n', 'quartz', null, null, null, null);

-- ----------------------------
-- Table structure for tbl_message
-- ----------------------------
DROP TABLE IF EXISTS `tbl_message`;
CREATE TABLE `tbl_message` (
  `id` varchar(36) NOT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `messageFlag` varchar(255) DEFAULT NULL,
  `messageStatus` varchar(255) DEFAULT NULL,
  `messageType` varchar(255) DEFAULT NULL,
  `receiveUsers` longtext,
  `remark` longtext,
  `sendContent` longtext,
  `sendSubject` varchar(255) DEFAULT NULL,
  `sendTime` datetime DEFAULT NULL,
  `sendUser` varchar(255) DEFAULT NULL,
  `sendUserID` varchar(255) DEFAULT NULL,
  `fileIds` longtext,
  `receiverIds` longtext,
  `receiverType` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_message
-- ----------------------------
INSERT INTO `tbl_message` VALUES ('8a8a84995ab64a5c015ab64b8af10000', '2017-03-10 11:38:08', '0', '2017-03-12 09:40:28', '2', '0', '4', '0,1', null, null, '<p>test2<br></p>', '商办唱主角北京房地产市场新格局商办唱主角北京房地产市场新格局AdminEAP框架消息管理系统', '2017-03-12 09:40:28', 'billJiang-test', '402880e456223e8f015625231d8e0003', '402880ed5abbe288015abc3e1c3c0002,402880ed5abdfec1015ac02c71f10001,402880ed5abdfec1015ac02c71f50002,402880ed5abdfec1015ac02c71f80003', '4028818458d40f4f0158d4169f9d0007,402880e65a7facdb015a7fca61830000,402880e456223e8f0156225736a40001,402880e456223e8f0156223feee10000,402880e456223e8f015625231d8e0003', '1');
INSERT INTO `tbl_message` VALUES ('8a8a84995ab654d1015ab675cf830009', '2017-03-10 12:24:18', '0', '2017-03-10 12:24:18', '0', '0', '0', '0,1', null, null, '<p>\n<h1>北京市局全力服务城市副中心建设</h1>\n\n<br>\n</p><p>据了解，市工商局党组成员此前多次到北京城市副中心开展调查研究，深入一线指导基层工商部门的工作，现场解决基层遇到的难点和问题。据悉，市工商局制定的措施包括，加强城市副中心执法机构设置，从组织上推进基层执法力量落实到乡镇；充分利用工商数据资源，为服务副中心发展和市场监管提供数据支撑，提供副中心市场主体发展报告；在实行城六区产业禁限目录的基础上，支持出台更加严格的产业禁限政策，提升副中心市场主体质量，推动副中心产业转型升级；充分发挥京津冀三地工商和市场监管协作平台作用，加强三地基层工商和市场监管部门情报交流、信息共享和执法协作。\n\n<br></p>', '北京市局全力服务城市副中心建设', null, 'billJiang-test', '402880e456223e8f015625231d8e0003', '8a8a84995ab654d1015ab6759e960007,8a8a84995ab654d1015ab6759e9e0008', '402880e55a998466015a998e208d0003', '0');
INSERT INTO `tbl_message` VALUES ('8a8a84995ab654d1015ab69aa7e8000c', '2017-03-10 13:04:33', '0', '2017-03-10 13:04:33', '0', '1', '0', '0,1', null, null, '<p>\n<p>在北京新房供应锐减、价格不断攀升的情况下，商住两用楼盘成为一年多来北京楼市的焦点，对于购房者来说，低总价的商住两用俨然已成为先“上车”的无奈之选。数据显示，2月北京商品房成交中，近六成为商办类楼盘，入市的商办类楼盘均表现较好。实际上，自2016年二季度开始，关于商住全面限购的传闻不断，自此商住市场成交也进入快车道。业内认为，限购政策、大量购房需求的存在、北京新房市场中低价位产品的匮乏以及调控预期的刺激是商办类市场成交火热的主要原因。</p>\n<p>&nbsp; &nbsp; <strong>商办成楼市主角</strong></p>\n<p>&nbsp; &nbsp; 2014年夏天，小郭从北京某高校毕业后选择留京工作，进入一家互联网公司，开始了“北漂”的日子。能在北京安个家，是小郭一直的心愿。对于他来说，安家就需要买房。2016年9月，毕业两年后，小郭有了点积蓄，再加上家里能拿出一部分钱，小郭开始筹备购房的事情。</p>\n<p>&nbsp; &nbsp; 当时，恰逢“9·30”新政出台，房地产市场成交开始放缓，小郭就决定等2016年底或者2017年再说。数月时间过去了，在小郭看来，房地产市场虽然成交放缓了，但价格还是很稳定的，再加上之前联系的销售人员都在说房地产价格不会出现下跌，想到从自己读书开始到现在，北京的房价涨了几倍，小郭突然有了一种想法：“如果再不买房的话，可能再也上不了车了。”</p>\n<p>&nbsp; &nbsp; 春节过后，小郭下定了买房的决心。因为北京的限购政策，小郭暂时不具备购买70年住宅的资质，因此，市场中存在的商住房成为小郭惟一的选择。2月，在市场中兜兜转转看了许多个在售商住项目后，小郭在大兴区某商住项目购买了一套LOFT房屋。</p>\n<p>&nbsp; &nbsp; 北京商报记者在走访市场时了解到，类似小郭这种情况的购房者并不在少数，在经过一段时间观望后，购房者的集中入市，导致2月下旬北京房地产市场阶段性回温。值得注意的是，根据统计数据显示，2月北京商品房成交量中，近六成为商办类楼盘，去年同期的商办成交占比还不到四成。</p>\n<p>&nbsp; &nbsp; 实际上，2016年是北京商住市场成交最热的一年。据统计，2016年北京商住市场共计成交63703套，这一成交总量超过了之前三年的总和。有数据显示，2010-2015年期间，北京商住公寓项目的成交量相对平稳；除2014年因市场降温而下滑至1.3万套以外，其余几年的成交套数均维持在2万-3万套之间。</p>\n<p>&nbsp; &nbsp; 有业内人士分析认为，一直以来，商住房都是作为房地产市场的一种补充产品存在着，不过在2016年上半年，北京商住房成交量首次超过住宅，随后商住房市场经历过几次起伏，但是，在北京纯住宅供应较少的情况下，商住房开始超过住宅产品成为北京楼市的主角，这也成为目前北京楼市的特点之一。</p>\n<p>&nbsp; &nbsp; <strong>多因素叠加效应</strong></p>\n<p>&nbsp; &nbsp; “北京商住房的‘上位’并非偶然”，一位从事房地产营销工作近十年的业内人士认为，北京市对纯住宅产品的限购政策、大量购房需求的存在、新房市场中低价位产品的匮乏以及调控预期的刺激是商住类市场成交火热的几个主要原因。</p>\n<p>&nbsp; &nbsp; 第一太平戴维斯华北区住宅销售部助理董事梁爽在接受北京商报记者采访时表示，商办类产品受使用成本高、土地年限短等条件限制，相比于住宅并没有优势，增值速度也低于普通住宅，然而受限购政策限制及北京楼市供给不足的影响，导致有自住及投资需求的客户都需要这种资产配置。因此，针对普通住宅的限购政策是商办成交火热的主要原因。</p>\n<p>&nbsp; &nbsp; 业内人士认为，除了针对普通住宅的限购政策之外，北京拥有中国最顶级的教育、医疗等资源，包括首次置业以及改善型置业的购房需求均较为旺盛。不仅如此，房地产是目前资产保值增值的一种重要工具，因此，北京房地产市场也存在一定规模的投资需求。同时，受限购政策影响，暂不具备购房资质的投资者就会流入商住房市场，这部分需求支撑着商住房市场的成交热度。</p>\n<p>&nbsp; &nbsp; 此外，北京新房市场中低价位产品的匮乏也是导致商住房走热的原因之一。近年来，北京市土地供应逐渐减少，2016年北京土地市场仅成交经营性用地（不包含3宗棚户区改造）28宗，备受关注的住宅类用地，仅成交15宗，其中可售纯商品住宅用地只有7宗，规划总建筑面积仅39万平方米。同时，土地成交价格不断攀升，地价高企导致北京新房市场豪宅化。因此，受到购房承受能力限制，低总价的商办类产品开始受到刚需购房者的青睐。</p>\n<p>&nbsp; &nbsp; 还有一个不得不提的原因是，关于商住房将全面限购的几度传闻。自2016年5月通州商住限购政策实施后，全市商住房将限购的消息几度传出，尽管政府部门曾辟谣，但开发商利用传言作为营销噱头，还是加速购房者涌入商住房市场。</p>\n\n<br></p>', '商办唱主角 北京房地产市场新格局商办唱主角 北京房地产市场新格局', null, 'billJiang-test', '402880e456223e8f015625231d8e0003', '8a8a84995ab654d1015ab69a75c0000a,8a8a84995ab654d1015ab69a75c7000b', '402880e55a998466015a998e208d0003', '0');
INSERT INTO `tbl_message` VALUES ('8a8a84995ab654d1015ab69bebc7000f', '2017-03-10 13:05:56', '0', '2017-03-10 13:05:56', '0', '1', '0', '0', null, null, '<p>\n<p>在北京新房供应锐减、价格不断攀升的情况下，商住两用楼盘成为一年多来北京楼市的焦点，对于购房者来说，低总价的商住两用俨然已成为先“上车”的无奈之选。数据显示，2月北京商品房成交中，近六成为商办类楼盘，入市的商办类楼盘均表现较好。实际上，自2016年二季度开始，关于商住全面限购的传闻不断，自此商住市场成交也进入快车道。业内认为，限购政策、大量购房需求的存在、北京新房市场中低价位产品的匮乏以及调控预期的刺激是商办类市场成交火热的主要原因。</p>\n<p>&nbsp; &nbsp; <strong>商办成楼市主角</strong></p>\n<p>&nbsp; &nbsp; 2014年夏天，小郭从北京某高校毕业后选择留京工作，进入一家互联网公司，开始了“北漂”的日子。能在北京安个家，是小郭一直的心愿。对于他来说，安家就需要买房。2016年9月，毕业两年后，小郭有了点积蓄，再加上家里能拿出一部分钱，小郭开始筹备购房的事情。</p>\n<p>&nbsp; &nbsp; 当时，恰逢“9·30”新政出台，房地产市场成交开始放缓，小郭就决定等2016年底或者2017年再说。数月时间过去了，在小郭看来，房地产市场虽然成交放缓了，但价格还是很稳定的，再加上之前联系的销售人员都在说房地产价格不会出现下跌，想到从自己读书开始到现在，北京的房价涨了几倍，小郭突然有了一种想法：“如果再不买房的话，可能再也上不了车了。”</p>\n<p>&nbsp; &nbsp; 春节过后，小郭下定了买房的决心。因为北京的限购政策，小郭暂时不具备购买70年住宅的资质，因此，市场中存在的商住房成为小郭惟一的选择。2月，在市场中兜兜转转看了许多个在售商住项目后，小郭在大兴区某商住项目购买了一套LOFT房屋。</p>\n<p>&nbsp; &nbsp; 北京商报记者在走访市场时了解到，类似小郭这种情况的购房者并不在少数，在经过一段时间观望后，购房者的集中入市，导致2月下旬北京房地产市场阶段性回温。值得注意的是，根据统计数据显示，2月北京商品房成交量中，近六成为商办类楼盘，去年同期的商办成交占比还不到四成。</p>\n<p>&nbsp; &nbsp; 实际上，2016年是北京商住市场成交最热的一年。据统计，2016年北京商住市场共计成交63703套，这一成交总量超过了之前三年的总和。有数据显示，2010-2015年期间，北京商住公寓项目的成交量相对平稳；除2014年因市场降温而下滑至1.3万套以外，其余几年的成交套数均维持在2万-3万套之间。</p>\n<p>&nbsp; &nbsp; 有业内人士分析认为，一直以来，商住房都是作为房地产市场的一种补充产品存在着，不过在2016年上半年，北京商住房成交量首次超过住宅，随后商住房市场经历过几次起伏，但是，在北京纯住宅供应较少的情况下，商住房开始超过住宅产品成为北京楼市的主角，这也成为目前北京楼市的特点之一。</p>\n<p>&nbsp; &nbsp; <strong>多因素叠加效应</strong></p>\n<p>&nbsp; &nbsp; “北京商住房的‘上位’并非偶然”，一位从事房地产营销工作近十年的业内人士认为，北京市对纯住宅产品的限购政策、大量购房需求的存在、新房市场中低价位产品的匮乏以及调控预期的刺激是商住类市场成交火热的几个主要原因。</p>\n<p>&nbsp; &nbsp; 第一太平戴维斯华北区住宅销售部助理董事梁爽在接受北京商报记者采访时表示，商办类产品受使用成本高、土地年限短等条件限制，相比于住宅并没有优势，增值速度也低于普通住宅，然而受限购政策限制及北京楼市供给不足的影响，导致有自住及投资需求的客户都需要这种资产配置。因此，针对普通住宅的限购政策是商办成交火热的主要原因。</p>\n<p>&nbsp; &nbsp; 业内人士认为，除了针对普通住宅的限购政策之外，北京拥有中国最顶级的教育、医疗等资源，包括首次置业以及改善型置业的购房需求均较为旺盛。不仅如此，房地产是目前资产保值增值的一种重要工具，因此，北京房地产市场也存在一定规模的投资需求。同时，受限购政策影响，暂不具备购房资质的投资者就会流入商住房市场，这部分需求支撑着商住房市场的成交热度。</p>\n<p>&nbsp; &nbsp; 此外，北京新房市场中低价位产品的匮乏也是导致商住房走热的原因之一。近年来，北京市土地供应逐渐减少，2016年北京土地市场仅成交经营性用地（不包含3宗棚户区改造）28宗，备受关注的住宅类用地，仅成交15宗，其中可售纯商品住宅用地只有7宗，规划总建筑面积仅39万平方米。同时，土地成交价格不断攀升，地价高企导致北京新房市场豪宅化。因此，受到购房承受能力限制，低总价的商办类产品开始受到刚需购房者的青睐。</p>\n<p>&nbsp; &nbsp; 还有一个不得不提的原因是，关于商住房将全面限购的几度传闻。自2016年5月通州商住限购政策实施后，全市商住房将限购的消息几度传出，尽管政府部门曾辟谣，但开发商利用传言作为营销噱头，还是加速购房者涌入商住房市场。</p>\n\n<br></p>', '商办唱主角 北京房地产市场新格局 商办唱主角 北京房地产市场新格局 商办唱主角 北京房地产市场新格局 商办唱主角 北京房地产市场新格局 ', null, 'billJiang-test', '402880e456223e8f015625231d8e0003', '8a8a84995ab654d1015ab69be272000d,8a8a84995ab654d1015ab69be276000e', '402880e55a998466015a998e208d0003', '0');
INSERT INTO `tbl_message` VALUES ('8a8a84995ab76f13015abbc8b8a00000', '2017-03-11 13:12:58', '0', '2017-03-11 13:42:56', '1', '0', '4', '0,1', null, null, '<p>\n</p><h1>北京市局全力服务城市副中心建设</h1>\n\n<br>\n<p></p><p>据了解，市工商局党组成员此前多次到北京城市副中心开展调查研究，深入一线指导基层工商部门的工作，现场解决基层遇到的难点和问题。据悉，市工商局制定的措施包括，加强城市副中心执法机构设置，从组织上推进基层执法力量落实到乡镇；充分利用工商数据资源，为服务副中心发展和市场监管提供数据支撑，提供副中心市场主体发展报告；在实行城六区产业禁限目录的基础上，支持出台更加严格的产业禁限政策，提升副中心市场主体质量，推动副中心产业转型升级；充分发挥京津冀三地工商和市场监管协作平台作用，加强三地基层工商和市场监管部门情报交流、信息共享和执法协作。\n\n<br></p>', '北京市局全力服务城市副中心建设test', '2017-03-11 13:42:56', 'billJiang-test', '402880e456223e8f015625231d8e0003', '8a8a84995ab654d1015ab6759e960007,8a8a84995ab654d1015ab6759e9e0008', '402880e55a998466015a998e208d0003', '0');

-- ----------------------------
-- Table structure for tbl_message_attachment
-- ----------------------------
DROP TABLE IF EXISTS `tbl_message_attachment`;
CREATE TABLE `tbl_message_attachment` (
  `id` varchar(36) NOT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `messageid` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_mcncia3i9t8e3sakiasxap8gw` (`messageid`),
  CONSTRAINT `FK_mcncia3i9t8e3sakiasxap8gw` FOREIGN KEY (`messageid`) REFERENCES `tbl_message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_message_attachment
-- ----------------------------

-- ----------------------------
-- Table structure for tbl_message_group
-- ----------------------------
DROP TABLE IF EXISTS `tbl_message_group`;
CREATE TABLE `tbl_message_group` (
  `id` varchar(36) NOT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `remark` longtext,
  `sort` int(11) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_message_group
-- ----------------------------
INSERT INTO `tbl_message_group` VALUES ('402880e456223e8f015625231d8e0001', '2017-03-03 14:38:36', '0', '2017-03-03 14:38:41', '0', '好友', 'test', '1', '402880e456223e8f015625231d8e0003');
INSERT INTO `tbl_message_group` VALUES ('402880e456223e8f015625231d8e0002', '2017-03-03 14:38:38', '0', '2017-03-03 14:38:44', '0', '同事', 'test', '2', '402880e456223e8f015625231d8e0003');
INSERT INTO `tbl_message_group` VALUES ('402880e456223e8f015625231d8e0003', '2017-03-03 14:38:55', '0', '2017-03-03 14:38:59', '0', '项目组', 'test', '3', '402880e456223e8f015625231d8e0003');
INSERT INTO `tbl_message_group` VALUES ('402880e45a9c67f5015a9c6a26d10000', '2017-03-05 11:01:26', '0', '2017-03-05 11:01:26', '0', 'test1', 'test1', '5', '402880e456223e8f015625231d8e0003');
INSERT INTO `tbl_message_group` VALUES ('402880e55a998466015a99894e020000', '2017-03-04 21:36:36', null, '2017-03-04 21:36:36', '0', '12', null, null, null);
INSERT INTO `tbl_message_group` VALUES ('402880e55a998466015a998e208d0003', '2017-03-04 21:41:52', null, '2017-03-04 21:41:52', '0', '33', null, null, '402880e456223e8f015625231d8e0003');
INSERT INTO `tbl_message_group` VALUES ('402880e55a99936a015a999463000000', '2017-03-04 21:48:43', '0', '2017-03-04 21:48:43', '0', 'test', 'test', '5', '402880e456223e8f015625231d8e0003');

-- ----------------------------
-- Table structure for tbl_message_group_user
-- ----------------------------
DROP TABLE IF EXISTS `tbl_message_group_user`;
CREATE TABLE `tbl_message_group_user` (
  `id` varchar(36) NOT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `group_id` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_message_group_user
-- ----------------------------
INSERT INTO `tbl_message_group_user` VALUES ('1', null, '0', null, '0', '402880e456223e8f015625231d8e0001', '1', '4028818458d40f4f0158d4108eb60001');
INSERT INTO `tbl_message_group_user` VALUES ('2', null, '0', null, '0', '402880e456223e8f015625231d8e0002', '1', '402880e456223e8f015625231d8e0003');
INSERT INTO `tbl_message_group_user` VALUES ('3', null, '0', null, '0', '402880e456223e8f015625231d8e0003', '1', '402880e456223e8f015625231d8e0003');
INSERT INTO `tbl_message_group_user` VALUES ('402880e45a9c67f5015a9c6a26e20001', '2017-03-05 11:01:26', '0', '2017-03-05 11:01:26', '0', '402880e45a9c67f5015a9c6a26d10000', null, '4028818458d40f4f0158d4108eb60001');
INSERT INTO `tbl_message_group_user` VALUES ('402880e45a9c67f5015a9c6a26f40002', '2017-03-05 11:01:26', '0', '2017-03-05 11:01:26', '0', '402880e45a9c67f5015a9c6a26d10000', null, '4028818458d3f33d0158d40197dd0004');
INSERT INTO `tbl_message_group_user` VALUES ('402880e45a9c67f5015a9c6a26f40003', '2017-03-05 11:01:26', '0', '2017-03-05 11:01:26', '0', '402880e45a9c67f5015a9c6a26d10000', null, '402880e65a7facdb015a7fca61830000');
INSERT INTO `tbl_message_group_user` VALUES ('402880e45a9c67f5015a9c6a26f40004', '2017-03-05 11:01:26', '0', '2017-03-05 11:01:26', '0', '402880e45a9c67f5015a9c6a26d10000', null, '4028818458d40f4f0158d4169f9d0007');
INSERT INTO `tbl_message_group_user` VALUES ('402880e55a998466015a99894e0a0001', '2017-03-04 21:36:36', '0', '2017-03-04 21:36:36', '0', '402880e55a998466015a99894e020000', null, '4028818458d40f4f0158d4108eb60001');
INSERT INTO `tbl_message_group_user` VALUES ('402880e55a998466015a99894e170002', '2017-03-04 21:36:36', '0', '2017-03-04 21:36:36', '0', '402880e55a998466015a99894e020000', null, '4028818458d3f33d0158d40197dd0004');
INSERT INTO `tbl_message_group_user` VALUES ('402880e55a998466015a998e208e0004', '2017-03-04 21:41:52', '0', '2017-03-04 21:41:52', '0', '402880e55a998466015a998e208d0003', null, '4028818458d40f4f0158d4108eb60001');
INSERT INTO `tbl_message_group_user` VALUES ('402880e55a998466015a998e20900005', '2017-03-04 21:41:52', '0', '2017-03-04 21:41:52', '0', '402880e55a998466015a998e208d0003', null, '4028818458d3f33d0158d40197dd0004');
INSERT INTO `tbl_message_group_user` VALUES ('402880e55a998466015a998e20900006', '2017-03-04 21:41:52', '0', '2017-03-04 21:41:52', '0', '402880e55a998466015a998e208d0003', null, '402880e65a7facdb015a7fca61830000');
INSERT INTO `tbl_message_group_user` VALUES ('402880e55a99936a015a9994630d0001', '2017-03-04 21:48:43', '0', '2017-03-04 21:48:43', '0', '402880e55a99936a015a999463000000', null, '4028818458d40f4f0158d4108eb60001');
INSERT INTO `tbl_message_group_user` VALUES ('402880e55a99936a015a9994631b0002', '2017-03-04 21:48:43', '0', '2017-03-04 21:48:43', '0', '402880e55a99936a015a999463000000', null, '4028818458d3f33d0158d40197dd0004');

-- ----------------------------
-- Table structure for tbl_message_receiver
-- ----------------------------
DROP TABLE IF EXISTS `tbl_message_receiver`;
CREATE TABLE `tbl_message_receiver` (
  `id` varchar(36) NOT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `isRead` varchar(255) DEFAULT NULL,
  `messageFlag` varchar(255) DEFAULT NULL,
  `messageType` varchar(255) DEFAULT NULL,
  `readTime` datetime DEFAULT NULL,
  `receiveAddress` varchar(255) DEFAULT NULL,
  `receiveUserID` varchar(255) DEFAULT NULL,
  `remark` longtext,
  `message` varchar(36) DEFAULT NULL,
  `readYet` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_4opt20g1s1vej8lay6pb31sdp` (`message`),
  CONSTRAINT `FK_4opt20g1s1vej8lay6pb31sdp` FOREIGN KEY (`message`) REFERENCES `tbl_message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_message_receiver
-- ----------------------------
INSERT INTO `tbl_message_receiver` VALUES ('402880ed5abdfec1015ac02c89850004', '2017-03-12 09:40:28', '0', '2017-03-12 09:40:28', '0', '0', '0', '0,1', null, 'test@163.com', '402880e456223e8f0156223feee10000', null, '8a8a84995ab64a5c015ab64b8af10000', '0');
INSERT INTO `tbl_message_receiver` VALUES ('402880ed5abdfec1015ac02c89890005', '2017-03-12 09:40:28', '0', '2017-03-12 09:40:28', '0', '0', '0', '0,1', null, 'test@163.com', '402880e456223e8f0156225736a40001', null, '8a8a84995ab64a5c015ab64b8af10000', '0');
INSERT INTO `tbl_message_receiver` VALUES ('402880ed5abdfec1015ac02c898a0006', '2017-03-12 09:40:28', '1', '2017-03-12 09:40:28', '11', '0', '0', '0,1', null, 'test@163.com', '402880e456223e8f015625231d8e0003', null, '8a8a84995ab64a5c015ab64b8af10000', '0');
INSERT INTO `tbl_message_receiver` VALUES ('402880ed5abdfec1015ac02c898a0007', '2017-03-12 09:40:28', '0', '2017-03-12 09:40:28', '0', '0', '0', '0,1', null, 'test@163.com', '402880e65a7facdb015a7fca61830000', null, '8a8a84995ab64a5c015ab64b8af10000', '0');
INSERT INTO `tbl_message_receiver` VALUES ('402880ed5abdfec1015ac02c898a0008', '2017-03-12 09:40:28', '0', '2017-03-12 09:40:28', '0', '0', '0', '0,1', null, 'test@163.com', '4028818458d40f4f0158d4169f9d0007', null, '8a8a84995ab64a5c015ab64b8af10000', '0');

-- ----------------------------
-- Table structure for tbl_org
-- ----------------------------
DROP TABLE IF EXISTS `tbl_org`;
CREATE TABLE `tbl_org` (
  `id` varchar(36) NOT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  `levelCode` varchar(36) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `org_type` varchar(255) DEFAULT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  `remark` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_org
-- ----------------------------

-- ----------------------------
-- Table structure for tbl_role
-- ----------------------------
DROP TABLE IF EXISTS `tbl_role`;
CREATE TABLE `tbl_role` (
  `id` varchar(36) NOT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `remark` longtext,
  `sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_role
-- ----------------------------
INSERT INTO `tbl_role` VALUES ('4028818a56fd502f015700209b040000', '2016-09-06 23:31:59', '0', '2017-01-04 09:25:43', '42', '1232', 'bill jiang', 'bill jiang \'s test', '2');
INSERT INTO `tbl_role` VALUES ('bc4d163c5880ab4901588b6681e50003', '2016-11-22 17:38:20', '0', '2016-11-22 17:38:20', '0', 'ADMIN', '管理员', '', '1');

-- ----------------------------
-- Table structure for tbl_role_function
-- ----------------------------
DROP TABLE IF EXISTS `tbl_role_function`;
CREATE TABLE `tbl_role_function` (
  `id` varchar(36) NOT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `functionId` varchar(36) DEFAULT NULL,
  `roleId` varchar(36) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_role_function
-- ----------------------------
INSERT INTO `tbl_role_function` VALUES ('8a8a83295a8e4242015a8f49d1380000', '2017-03-02 21:51:04', null, '2017-03-02 21:51:04', '0', '402880eb56875b7f0156876338100000', 'bc4d163c5880ab4901588b6681e50003', null);
INSERT INTO `tbl_role_function` VALUES ('8a8a83295a8e4242015a8f49d1610001', '2017-03-02 21:51:04', null, '2017-03-02 21:51:04', '0', '402880eb56875b7f01568763ac630001', 'bc4d163c5880ab4901588b6681e50003', null);
INSERT INTO `tbl_role_function` VALUES ('8a8a83295a8e4242015a8f49d1620002', '2017-03-02 21:51:04', null, '2017-03-02 21:51:04', '0', '40288182579e398f0157a25ca29a0002', 'bc4d163c5880ab4901588b6681e50003', null);

-- ----------------------------
-- Table structure for tbl_simple_mail
-- ----------------------------
DROP TABLE IF EXISTS `tbl_simple_mail`;
CREATE TABLE `tbl_simple_mail` (
  `id` varchar(36) NOT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `content` longtext,
  `mail_type` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `fromUser` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_simple_mail
-- ----------------------------
INSERT INTO `tbl_simple_mail` VALUES ('40288193593f158301593f7d51500001', '2016-12-27 16:54:54', '0', '2016-12-27 16:54:54', '0', '<p>辅导费<br></p>', 'bug反馈', '243232', null);
INSERT INTO `tbl_simple_mail` VALUES ('40288193593f158301593f7fcec10002', '2016-12-27 16:57:37', '0', '2016-12-27 16:57:37', '0', '<p>tttt<br></p>', '意见/建议', 'test', null);
INSERT INTO `tbl_simple_mail` VALUES ('40288193593f158301593f9787440003', '2016-12-27 17:23:32', '0', '2016-12-27 17:23:32', '0', '<p>test<br></p>', '意见/建议', 'test', null);
INSERT INTO `tbl_simple_mail` VALUES ('40288193593f158301593f980e700004', '2016-12-27 17:24:06', '0', '2016-12-27 17:24:06', '0', '<p>bug test<br></p>', 'bug反馈', 'test', null);
INSERT INTO `tbl_simple_mail` VALUES ('40288193593f158301593f9d09300005', '2016-12-27 17:29:33', '0', '2016-12-27 17:29:33', '0', '<p>ds<br></p>', '意见/建议', 'dsd', null);
INSERT INTO `tbl_simple_mail` VALUES ('40288193593f158301593f9d31b90006', '2016-12-27 17:29:43', '0', '2016-12-27 17:29:43', '0', '<p>ds<br></p>', '意见/建议', 'dsd', null);
INSERT INTO `tbl_simple_mail` VALUES ('40288193593f158301593f9e4dcb0007', '2016-12-27 17:30:56', '0', '2016-12-27 17:30:56', '0', '<p>ttt<br></p>', 'bug反馈', 'ttt', null);
INSERT INTO `tbl_simple_mail` VALUES ('40288193593f158301593fa018700008', '2016-12-27 17:32:53', '0', '2016-12-27 17:32:53', '0', '<p>test<br></p>', '意见/建议', '243232', null);
INSERT INTO `tbl_simple_mail` VALUES ('40288193593f158301593fa18e900009', '2016-12-27 17:34:29', '0', '2016-12-27 17:34:29', '0', '<p>3980-<br></p>', '创意/想法', 'dsd', null);
INSERT INTO `tbl_simple_mail` VALUES ('40288193593f158301593fa2ec27000a', '2016-12-27 17:35:59', '0', '2016-12-27 17:35:59', '0', '<p>test1234567<br></p>', '意见/建议', 'test1234567', null);
INSERT INTO `tbl_simple_mail` VALUES ('40288193593f158301593fa5a57c000b', '2016-12-27 17:38:57', '0', '2016-12-27 17:38:57', '0', '<p>fdfd<br></p>', 'bug反馈', 'fdsfd', null);
INSERT INTO `tbl_simple_mail` VALUES ('8a8a807a59633d0a0159634675a90000', '2017-01-03 15:41:19', '0', '2017-01-03 15:41:19', '0', '<p>bill jiang的测试</p>', 'bug反馈', '我要测试', null);
INSERT INTO `tbl_simple_mail` VALUES ('8a8a807a59633d0a01596346807a0001', '2017-01-03 15:41:21', '0', '2017-01-03 15:41:21', '0', '<p>bill jiang的测试</p>', 'bug反馈', '我要测试', null);
INSERT INTO `tbl_simple_mail` VALUES ('8a8a807a59633d0a0159634941b00002', '2017-01-03 15:44:22', '0', '2017-01-03 15:44:22', '0', '<p>\n<div>\n					<h2>2016，我们努力打造更丰富、更易用、更低成本的云数据库，让您获得更高性价比：</h2>\n					<ul>\n						\n							<li><div>239个<br>功能点</div><div>2016年，数据库发布了<br>239个新功能及功能<br>优化</div></li>\n						\n							<li><div>5款<br>新产品</div><div>2016年，数据库发布了<br>5款新产品包括云数据库<br>MongoDB版等</div></li>\n						\n							<li><div>69%<br>最高降幅</div><div>2016年，数据库进行了<br>2次降价平均降幅39%，<br>最高降幅69%</div></li>\n						\n					</ul>\n					<div>\n						<div>他们都信任和选择阿里云数据库产品：</div>\n						<div>\n							\n								<div><img alt=\"\" src=\"https://img.alicdn.com/tps/TB1DkRYKXXXXXb0XpXXXXXXXXXX-140-140.png\"></div>\n							\n								<div><img alt=\"\" src=\"https://img.alicdn.com/tps/TB126T6KXXXXXc8XFXXXXXXXXXX-140-140.jpg\"></div>\n							\n								<div><img alt=\"\" src=\"https://img.alicdn.com/tps/TB12gchKXXXXXafXpXXXXXXXXXX-140-140.jpg\"></div>\n							\n								<div><img alt=\"\" src=\"http://pic.paopaoche.net/up/2015-3/201532141132.png\"></div>\n							\n								<div><img alt=\"\" src=\"https://img.alicdn.com/tps/TB1L6K8OVXXXXXoXpXXXXXXXXXX-256-256.png\"></div>\n							\n						</div>\n					</div>\n				</div>\n\n</p>', '创意/想法', '我了个天', null);
INSERT INTO `tbl_simple_mail` VALUES ('8a8a807a59633d0a0159634a98c50003', '2017-01-03 15:45:50', '0', '2017-01-03 15:45:50', '0', '<p>test</p>', 'bug反馈', 'bill jiang的测试', null);
INSERT INTO `tbl_simple_mail` VALUES ('8a8a807a59634b280159634c1eb20000', '2017-01-03 15:47:30', '0', '2017-01-03 15:47:30', '0', '<p>2017-01-03 15:46:50,132 [freemarker.cache]-[DEBUG] Could not find template in cache, creating new one; id=[\"homePage.html\"[\"zh_CN\",utf-8,parsed] ]<br>2017-01-03 15:46:50,134 [freemarker.cache]-[DEBUG] Compiling FreeMarker template \"homePage.html\"[\"zh_CN\",utf-8,parsed]  from \"C:\\\\work\\\\github\\\\AdminEAP\\\\AdminEAP-web\\\\src\\\\main\\\\webapp\\\\WEB-INF\\\\views\\\\homePage.html\"<br>2017-01-03 15:46:50,162 [freemarker.cache]-[DEBUG] \"homePage.html\"[\"zh_CN\",utf-8,parsed]  cached copy not yet stale; using cached.<br><a href=\"http://127.0.0.1:8080/AdminEAP/message/mail/edit\">http://127.0.0.1:8080/AdminEAP/message/mail/edit</a>﻿<br></p>', '创意/想法', 'bill jiang的测试', 'jrn1012@petrochina.com.cn');
INSERT INTO `tbl_simple_mail` VALUES ('8a8a80a0593fa68f01593fb4374b0000', '2016-12-27 17:54:52', '0', '2016-12-27 17:54:52', '0', '<p> \n<h1>1. 计费方法</h1><p>邮件推送产品（Direct Mail）计费方法是按照邮件发送量计费，目前有两种付费方式：资源包、按量付费。</p>\n<ul>\n<li><p>邮件发送量：指邮件推送为用户发送的邮件总量。</p>\n</li><li><p>一封邮件的定义为：发送至一个电子邮件地址的一次电子邮件通信。发送给多个收件人的一次电子邮件通信将视为分别向每个收件人发送一封邮件。</p>\n</li></ul>\n<h2><a target=\"_blank\" rel=\"nofollow\"></a>1.1 资源包</h2><ol>\n<li>资源包是预付费方式，支付费用购买成功后，资源包立即生效。</li><li>购买时长（即有效期）：6个月。每个资源包的有效期独立计算，多个资源包有效期不会叠加。</li><li>扣减方式：购买的资源包在有效期内，优先抵扣资源包内的邮件量，多个资源包按有效期先结束的优先扣减，当所有资源包扣减完毕后，自动转为按量计费的方式扣减。</li><li><strong>资源包使用后或资源包到期后，剩余流量不支持退订、不支持退款。</strong></li></ol>\n\n<br></p>', '意见/建议', 'test1234567', null);
INSERT INTO `tbl_simple_mail` VALUES ('8a8a81d65a3ba9a5015a3c23fe7d0000', '2017-02-14 18:21:16', '0', '2017-02-14 18:21:16', '0', '<p>test<br></p>', 'bug反馈', 'test', 'test');
INSERT INTO `tbl_simple_mail` VALUES ('8a8a81d65a3c252f015a3c25e0350000', '2017-02-14 18:23:19', '0', '2017-02-14 18:23:19', '0', '<p>test<br></p>', '意见/建议', 'test1', 'test');
INSERT INTO `tbl_simple_mail` VALUES ('8a8a81d65a3c285d015a3c28e0c80000', '2017-02-14 18:26:36', '0', '2017-02-14 18:26:36', '0', '<p>ffff<br></p>', '意见/建议', 'test1233434534', 'test1');

-- ----------------------------
-- Table structure for tbl_user
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user` (
  `id` varchar(36) NOT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `login_name` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `open_account` varchar(5) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `qq` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `telphone` varchar(255) DEFAULT NULL,
  `wechat` varchar(255) DEFAULT NULL,
  `isSuperAdmin` varchar(255) DEFAULT NULL,
  `dept_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_user
-- ----------------------------
INSERT INTO `tbl_user` VALUES ('1f150ec4321411e6a46d10a0f3df85fa', '2016-06-14 17:41:14', '0', '2016-06-14 17:41:14', '1', null, '2016-06-14 17:41:14', 'test@163.com', 'tester', '13813888888', 'tester3', '1', '070501020f040600050c050a0d0b020902070501', null, '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('23c87eb0321411e6a46d10a0f3df85fa', '2016-06-14 17:41:22', '0', '2016-06-14 17:41:22', '1', null, '2016-06-14 17:41:22', 'test@163.com', 'tester', '13813888888', 'tester5', '1', '070501020f040600050c050a0d0b020902070501', null, '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('23e6e45d321411e6a46d10a0f3df85fa', '2016-06-14 17:41:23', '0', '2016-12-05 19:35:29', '13', null, '2016-06-14 00:00:00', 'test@163.com', 'tester', '13813888888', 'tester6', null, '070501020f040600050c050a0d0b020902070501', '', '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('2405dc64321411e6a46d10a0f3df85fa', '2016-06-14 17:41:23', '0', '2016-06-14 17:41:23', '1', null, '2016-06-14 17:41:23', 'test@163.com', 'tester', '13813888888', 'tester7', '1', '070501020f040600050c050a0d0b020902070501', null, '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('24961408321411e6a46d10a0f3df85fa', '2016-06-14 17:41:24', '0', '2016-07-26 22:24:16', '2', null, '2016-06-14 00:00:00', 'test@163.com', 'tester', '13813888888', 'tester9', null, '070501020f040600050c050a0d0b020902070501', '', '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('24b7164a321411e6a46d10a0f3df85fa', '2016-06-14 17:41:24', '0', '2016-06-14 17:41:24', '1', null, '2016-06-14 17:41:24', 'test@163.com', 'tester', '13813888888', 'tester10', '1', '070501020f040600050c050a0d0b020902070501', null, '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('24f40c7b321411e6a46d10a0f3df85fa', '2016-06-14 17:41:24', '0', '2016-12-05 19:51:00', '2', null, '2016-06-14 00:00:00', 'test@163.com', 'tester', '13813888888', 'tester12', null, '070501020f040600050c050a0d0b020902070501', '', '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('2510c36e321411e6a46d10a0f3df85fa', '2016-06-14 17:41:24', '0', '2016-06-14 17:41:24', '1', null, '2016-06-14 17:41:24', 'test@163.com', 'tester', '13813888888', 'tester13', '1', '070501020f040600050c050a0d0b020902070501', null, '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('252caeaf321411e6a46d10a0f3df85fa', '2016-06-14 17:41:25', '0', '2016-06-14 17:41:25', '1', null, '2016-06-14 17:41:25', 'test@163.com', 'tester', '13813888888', 'tester14', '1', '070501020f040600050c050a0d0b020902070501', null, '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('254f9b64321411e6a46d10a0f3df85fa', '2016-06-14 17:41:25', '0', '2016-12-05 19:44:37', '13', null, '2016-06-14 00:00:00', 'test@163.com', 'tester', '13813888888', 'tester15', null, '070501020f040600050c050a0d0b020902070501', '', '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('25713641321411e6a46d10a0f3df85fa', '2016-06-14 17:41:25', '0', '2016-06-14 17:41:25', '1', null, '2016-06-14 17:41:25', 'test@163.com', 'tester', '13813888888', 'tester16', '1', '070501020f040600050c050a0d0b020902070501', null, '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('25924d86321411e6a46d10a0f3df85fa', '2016-06-14 17:41:25', '0', '2016-12-05 21:27:23', '2', null, '2016-06-14 00:00:00', 'test@163.com', 'tester', '13813888888', 'tester17', null, '070501020f040600050c050a0d0b020902070501', '', '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('25b24324321411e6a46d10a0f3df85fa', '2016-06-14 17:41:26', '0', '2016-06-14 17:41:26', '1', null, '2016-06-14 17:41:26', 'test@163.com', 'tester', '13813888888', 'tester18', '1', '070501020f040600050c050a0d0b020902070501', null, '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('25eff07c321411e6a46d10a0f3df85fa', '2016-06-14 17:41:26', '0', '2016-06-14 17:41:26', '1', null, '2016-06-14 17:41:26', 'test@163.com', 'tester', '13813888888', 'tester20', '1', '070501020f040600050c050a0d0b020902070501', null, '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('262a3774321411e6a46d10a0f3df85fa', '2016-06-14 17:41:26', '0', '2016-06-14 17:41:26', '1', null, '2016-06-14 17:41:26', 'test@163.com', 'tester', '13813888888', 'tester21', '1', '070501020f040600050c050a0d0b020902070501', null, '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('266ce1a2321411e6a46d10a0f3df85fa', '2016-06-14 17:41:27', '0', '2016-06-14 17:41:27', '1', null, '2016-06-14 17:41:27', 'test@163.com', 'tester', '13813888888', 'tester23', '1', '070501020f040600050c050a0d0b020902070501', null, '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('268d44bf321411e6a46d10a0f3df85fa', '2016-06-14 17:41:27', '0', '2016-06-14 17:41:27', '1', null, '2016-06-14 17:41:27', 'test@163.com', 'tester', '13813888888', 'tester24', '1', '070501020f040600050c050a0d0b020902070501', null, '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('26abb5d0321411e6a46d10a0f3df85fa', '2016-06-14 17:41:27', '0', '2016-06-14 17:41:27', '1', null, '2016-06-14 17:41:27', 'test@163.com', 'tester', '13813888888', 'tester25', '1', '070501020f040600050c050a0d0b020902070501', null, '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('26cabdc3321411e6a46d10a0f3df85fa', '2016-06-14 17:41:27', '0', '2016-06-14 17:41:27', '1', null, '2016-06-14 17:41:27', 'test@163.com', 'tester', '13813888888', 'tester26', '1', '070501020f040600050c050a0d0b020902070501', null, '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('275a01e2321411e6a46d10a0f3df85fa', '2016-06-14 17:41:28', '0', '2016-12-05 19:47:31', '5', null, '2016-06-14 00:00:00', 'test@163.com', 'tester', '13813888888', 'tester31fdsfdsf', null, '070501020f040600050c050a0d0b020902070501', '', '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('27773eda321411e6a46d10a0f3df85fa', '2016-06-14 17:41:29', '0', '2016-12-05 19:47:21', '6', null, '2016-06-14 00:00:00', 'test@163.com', 'tester', '13813888888', 'tester32', null, '070501020f040600050c050a0d0b020902070501', '', '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('2793c5eb321411e6a46d10a0f3df85fa', '2016-06-14 17:41:29', '0', '2016-07-29 12:30:52', '4', null, '2016-06-14 00:00:00', 'test@163.com', 'tester', '13813888888', '243567890', null, '070501020f040600050c050a0d0b020902070501', '', '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('27b24306321411e6a46d10a0f3df85fa', '2016-06-14 17:41:29', '0', '2016-06-14 17:41:29', '1', null, '2016-06-14 17:41:29', 'test@163.com', 'tester', '13813888888', 'tester34', '1', '070501020f040600050c050a0d0b020902070501', null, '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('27cc52be321411e6a46d10a0f3df85fa', '2016-06-14 17:41:29', '0', '2016-06-14 17:41:29', '1', null, '2016-06-14 17:41:29', 'test@163.com', 'tester', '13813888888', 'tester35', '1', '070501020f040600050c050a0d0b020902070501', null, '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('27ea2ce3321411e6a46d10a0f3df85fa', '2016-06-14 17:41:29', '0', '2016-06-14 17:41:29', '1', null, '2016-06-14 17:41:29', 'test@163.com', 'tester', '13813888888', 'tester36', '1', '070501020f040600050c050a0d0b020902070501', null, '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('28268c27321411e6a46d10a0f3df85fa', '2016-06-14 17:41:30', '0', '2016-06-14 17:41:30', '1', null, '2016-06-14 17:41:30', 'test@163.com', 'tester', '13813888888', 'tester38', '1', '070501020f040600050c050a0d0b020902070501', null, '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('2d7e7f00321411e6a46d10a0f3df85fa', '2016-06-14 17:41:39', '0', '2016-07-26 22:15:37', '5', null, '2016-06-14 00:00:00', 'test@163.com', 'tester', '13813888888', 'tester39fdsfsdfds', null, '070501020f040600050c050a0d0b020902070501', '', '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('2d9b6d58321411e6a46d10a0f3df85fa', '2016-06-14 17:41:39', '0', '2016-06-14 17:41:39', '1', null, '2016-06-14 17:41:39', 'test@163.com', 'tester', '13813888888', 'tester40', '1', '070501020f040600050c050a0d0b020902070501', null, '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('2db9a18c321411e6a46d10a0f3df85fa', '2016-06-14 17:41:39', '0', '2016-06-14 17:41:39', '1', null, '2016-06-14 17:41:39', 'test@163.com', 'tester', '13813888888', 'tester41', '1', '070501020f040600050c050a0d0b020902070501', null, '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('2dd55c4e321411e6a46d10a0f3df85fa', '2016-06-14 17:41:39', '0', '2016-10-07 17:20:11', '10', null, '2016-06-14 00:00:00', 'test@163.com', 'tester', '13813888888', 'tester42', null, '070501020f040600050c050a0d0b020902070501', '', '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('2df1e46d321411e6a46d10a0f3df85fa', '2016-06-14 17:41:39', '0', '2016-07-26 22:05:08', '3', null, '2016-06-14 00:00:00', 'test@163.com', 'tester', '13813888888', 'tester43', null, '070501020f040600050c050a0d0b020902070501', '', '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('2e5f797a321411e6a46d10a0f3df85fa', '2016-06-14 17:41:40', '0', '2016-07-26 22:24:27', '4', null, '2016-06-14 00:00:00', 'test@163.com', 'tester', '13813888888', 'tester45', null, '070501020f040600050c050a0d0b020902070501', '', '1', '13601047561', null, '1', null);
INSERT INTO `tbl_user` VALUES ('402880e456223e8f0156223feee10000', '2016-07-25 21:30:30', '0', '2016-10-07 17:19:19', '3', null, '2016-07-06 00:00:00', 'test@163.com', 'jrn', '13813888888', 'billJiang', null, '070501020f040600050c050a0d0b020902070501', '475572229', '1', '01083595052', null, '', null);
INSERT INTO `tbl_user` VALUES ('402880e456223e8f0156225736a40001', '2016-07-25 21:55:56', '0', '2016-12-05 19:47:14', '9', null, '2016-07-06 00:00:00', 'test@163.com', 'jrn', '13813888888', 'billJiang', null, '070501020f040600050c050a0d0b020902070501', '475572229', '1', '', null, '', null);
INSERT INTO `tbl_user` VALUES ('402880e456223e8f015625231d8e0003', '2016-07-26 19:02:18', null, '2016-07-26 18:59:59', '2', null, '2016-07-22 00:00:00', 'test@163.com', 'test', '13813888888', 'billJiang-test', null, '070501020f040600050c050a0d0b020902070501', '475572229', '1', '01083595052', null, '', null);
INSERT INTO `tbl_user` VALUES ('402880e65a7facdb015a7fca61830000', '2017-02-27 21:37:34', '0', '2017-03-13 14:37:43', '1', null, '2017-02-16 00:00:00', 'test@163.com', 'test', '', '12345678', null, null, '', '1', '', null, '', null);
INSERT INTO `tbl_user` VALUES ('4028818458d281780158d31c9d940000', '2016-12-06 15:50:17', '0', '2016-12-06 15:51:30', '1', null, '2016-12-18 00:00:00', 'test@163.com', 'fd', '13813888888', '12345678', null, '070501020f040600050c050a0d0b020902070501', '', '1', '01083595052', null, '', null);
INSERT INTO `tbl_user` VALUES ('4028818458d3f33d0158d3f5ca120001', '2016-12-06 19:47:30', '0', '2016-12-06 19:47:30', '0', null, '2016-12-19 00:00:00', 'test@163.com', 'tester', '13813888888', '232323', null, '070501020f040600050c050a0d0b020902070501', '', '1', '01083595052', null, '', null);
INSERT INTO `tbl_user` VALUES ('4028818458d3f33d0158d400a71c0003', '2016-12-06 19:59:22', '0', '2016-12-06 19:59:22', '0', null, '2016-12-05 00:00:00', 'test@163.com', 'tester', '13813888888', 'fdfs', null, '070501020f040600050c050a0d0b020902070501', '', '1', 'test', null, '', null);
INSERT INTO `tbl_user` VALUES ('4028818458d3f33d0158d40197dd0004', '2016-12-06 20:00:24', '0', '2017-03-13 14:34:45', '8', null, '2017-02-28 00:00:00', 'test@163.com', 'tester', '13813888888', 'test', null, null, '', '1', 'test', null, '', null);
INSERT INTO `tbl_user` VALUES ('4028818458d3f33d0158d407b7de0005', '2016-12-06 20:07:05', '0', '2016-12-06 20:07:05', '0', null, '2016-12-01 00:00:00', 'jrn1012@petrochina.com.cn', 'tester', '13813888888', 'TEST1243433', null, '070501020f040600050c050a0d0b020902070501', '', '1', '13601047561', null, '', null);
INSERT INTO `tbl_user` VALUES ('4028818458d40f4f0158d4108eb60001', '2016-12-06 20:16:44', '0', '2017-03-13 14:41:14', '6', null, '2017-02-15 00:00:00', '517208243@qq.com', '43', '13813888888', '1234567765432', null, null, '475572229', '1', '01083595052', null, '', null);
INSERT INTO `tbl_user` VALUES ('4028818458d40f4f0158d4169f9d0007', '2016-12-06 20:23:22', '0', '2017-02-20 14:59:54', '3', null, '2017-02-23 00:00:00', 'test@163.com', '2', '2', '1323232', null, null, '', '1', '2', null, '', null);
INSERT INTO `tbl_user` VALUES ('8a8a801b58c829670158c86deb5a0000', '2016-12-04 14:03:16', null, '2016-12-04 14:03:16', '0', null, '2016-11-29 00:00:00', 'test@163.com', '11', '13813888888', 'fdfs', null, '070501020f040600050c050a0d0b020902070501', '', '1', '01083595052', null, '', null);
INSERT INTO `tbl_user` VALUES ('8a8a801b58ce0f500158ce7999690001', '2016-12-05 18:13:45', '0', '2016-12-05 19:45:29', '2', null, '2016-12-01 00:00:00', 'test@163.com', 'tester', '13813888888', 'hello world', null, '070501020f040600050c050a0d0b020902070501', '475572229', '1', '13601047561', null, '', null);
INSERT INTO `tbl_user` VALUES ('8afa96f6564a787601564a79fd970000', '2016-08-02 16:58:44', '0', '2016-12-05 19:44:54', '2', null, '2016-02-29 00:00:00', 'test@163.com', 'tt', '13813888888', 'test', null, '070501020f040600050c050a0d0b020902070501', '', '0', 'test', null, '', null);
INSERT INTO `tbl_user` VALUES ('8afa96f6564a787601564a818bf80002', '2016-08-02 17:06:59', '0', '2016-09-02 22:26:01', '1', null, '2016-03-03 00:00:00', 'test@163.com', 'qqqfr', '13813888888', 'zzz', null, '070501020f040600050c050a0d0b020902070501', '10599w2e3de', '1', '', null, '', null);

-- ----------------------------
-- Table structure for tbl_user_avatar
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user_avatar`;
CREATE TABLE `tbl_user_avatar` (
  `id` varchar(36) NOT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `src` varchar(255) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_user_avatar
-- ----------------------------
INSERT INTO `tbl_user_avatar` VALUES ('4028818458d40f4f0158d41667830006', '2016-12-06 20:23:07', null, '2016-12-11 16:42:17', '3', '1323232_1481445737480.jpg', '\\uploadPath\\avatar\\1323232_1481445737480.jpg', '4028818458d40f4f0158d4169f9d0007');
INSERT INTO `tbl_user_avatar` VALUES ('8a8a83295a845220015a847d3e3c0000', '2017-02-28 19:31:24', null, '2017-02-28 19:31:24', '0', '1234567765432_1488281484853.jpg', '\\uploadPath\\avatar\\1234567765432_1488281484853.jpg', '4028818458d40f4f0158d4108eb60001');
INSERT INTO `tbl_user_avatar` VALUES ('8afa96f658f1071d0158f10892a80000', '2016-12-12 11:17:00', null, '2016-12-12 11:17:00', '0', 'new_1481512619938.jpg', '\\uploadPath\\avatar\\new_1481512619938.jpg', '0');
INSERT INTO `tbl_user_avatar` VALUES ('8afa96f658f1071d0158f109333f0001', '2016-12-12 11:17:41', null, '2016-12-12 11:17:41', '0', 'new_1481512661820.jpg', '\\uploadPath\\avatar\\new_1481512661820.jpg', '0');

-- ----------------------------
-- Table structure for tbl_user_oauth
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user_oauth`;
CREATE TABLE `tbl_user_oauth` (
  `id` varchar(36) NOT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `oauth_id` varchar(255) DEFAULT NULL,
  `oauth_type` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_user_oauth
-- ----------------------------
INSERT INTO `tbl_user_oauth` VALUES ('402880e459a7cb440159a7d0793b0001', '2017-01-16 23:06:14', null, '2017-01-16 23:06:14', '0', '7886696', 'github', '402880e459a7cb440159a7d079220000', null);

-- ----------------------------
-- Table structure for tbl_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user_role`;
CREATE TABLE `tbl_user_role` (
  `id` varchar(36) NOT NULL,
  `create_date_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `update_date_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `roleId` varchar(36) DEFAULT NULL,
  `userId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ds15bcoaufaq7shnl3445nc3o` (`userId`) USING BTREE,
  CONSTRAINT `tbl_user_role_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `tbl_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_user_role
-- ----------------------------
INSERT INTO `tbl_user_role` VALUES ('4028819359637b580159638f46a00001', '2017-01-03 17:00:51', null, '2017-01-03 17:00:51', '0', 'bc4d163c5880ab4901588b6681e50003', '4028818458d40f4f0158d4108eb60001');
INSERT INTO `tbl_user_role` VALUES ('4028819359637b580159638f46a10002', '2017-01-03 17:00:51', null, '2017-01-03 17:00:51', '0', 'bc4d163c5880ab4901588b6681e50003', '4028818458d3f33d0158d400a71c0003');
INSERT INTO `tbl_user_role` VALUES ('4028819359637b580159638f46a10003', '2017-01-03 17:00:51', null, '2017-01-03 17:00:51', '0', 'bc4d163c5880ab4901588b6681e50003', '4028818458d3f33d0158d3f5ca120001');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927b94030001', '2017-01-12 19:41:29', null, '2017-01-12 19:41:29', '0', 'bc4d163c5880ab4901588b6681e50003', '402880e456223e8f015625231d8e0003');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927bb1d40002', '2017-01-12 19:41:37', null, '2017-01-12 19:41:37', '0', 'bc4d163c5880ab4901588b6681e50003', '2e5f797a321411e6a46d10a0f3df85fa');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927bb1d50003', '2017-01-12 19:41:37', null, '2017-01-12 19:41:37', '0', 'bc4d163c5880ab4901588b6681e50003', '27ea2ce3321411e6a46d10a0f3df85fa');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927bbef30004', '2017-01-12 19:41:40', null, '2017-01-12 19:41:40', '0', 'bc4d163c5880ab4901588b6681e50003', '24961408321411e6a46d10a0f3df85fa');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927bbef40005', '2017-01-12 19:41:40', null, '2017-01-12 19:41:40', '0', 'bc4d163c5880ab4901588b6681e50003', '268d44bf321411e6a46d10a0f3df85fa');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927bdbcf0007', '2017-01-12 19:41:47', null, '2017-01-12 19:41:47', '0', 'bc4d163c5880ab4901588b6681e50003', '2df1e46d321411e6a46d10a0f3df85fa');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927bdbcf0008', '2017-01-12 19:41:47', null, '2017-01-12 19:41:47', '0', 'bc4d163c5880ab4901588b6681e50003', '2d9b6d58321411e6a46d10a0f3df85fa');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927bdbcf0009', '2017-01-12 19:41:47', null, '2017-01-12 19:41:47', '0', 'bc4d163c5880ab4901588b6681e50003', '2db9a18c321411e6a46d10a0f3df85fa');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927bdbcf000a', '2017-01-12 19:41:47', null, '2017-01-12 19:41:47', '0', 'bc4d163c5880ab4901588b6681e50003', '28268c27321411e6a46d10a0f3df85fa');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927bdbcf000b', '2017-01-12 19:41:47', null, '2017-01-12 19:41:47', '0', 'bc4d163c5880ab4901588b6681e50003', '27b24306321411e6a46d10a0f3df85fa');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927bdbcf000c', '2017-01-12 19:41:47', null, '2017-01-12 19:41:47', '0', 'bc4d163c5880ab4901588b6681e50003', '27cc52be321411e6a46d10a0f3df85fa');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927bdbcf000d', '2017-01-12 19:41:47', null, '2017-01-12 19:41:47', '0', 'bc4d163c5880ab4901588b6681e50003', '266ce1a2321411e6a46d10a0f3df85fa');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927bdbcf000e', '2017-01-12 19:41:47', null, '2017-01-12 19:41:47', '0', 'bc4d163c5880ab4901588b6681e50003', '26abb5d0321411e6a46d10a0f3df85fa');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927bdbcf000f', '2017-01-12 19:41:47', null, '2017-01-12 19:41:47', '0', 'bc4d163c5880ab4901588b6681e50003', '26cabdc3321411e6a46d10a0f3df85fa');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927bf6ae0010', '2017-01-12 19:41:54', null, '2017-01-12 19:41:54', '0', 'bc4d163c5880ab4901588b6681e50003', '25b24324321411e6a46d10a0f3df85fa');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927bf6b00011', '2017-01-12 19:41:54', null, '2017-01-12 19:41:54', '0', 'bc4d163c5880ab4901588b6681e50003', '25eff07c321411e6a46d10a0f3df85fa');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927bf6b00012', '2017-01-12 19:41:54', null, '2017-01-12 19:41:54', '0', 'bc4d163c5880ab4901588b6681e50003', '262a3774321411e6a46d10a0f3df85fa');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927bf6b00013', '2017-01-12 19:41:54', null, '2017-01-12 19:41:54', '0', 'bc4d163c5880ab4901588b6681e50003', '252caeaf321411e6a46d10a0f3df85fa');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927bf6b00014', '2017-01-12 19:41:54', null, '2017-01-12 19:41:54', '0', 'bc4d163c5880ab4901588b6681e50003', '25713641321411e6a46d10a0f3df85fa');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927bf6b00015', '2017-01-12 19:41:54', null, '2017-01-12 19:41:54', '0', 'bc4d163c5880ab4901588b6681e50003', '24b7164a321411e6a46d10a0f3df85fa');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927bf6b00016', '2017-01-12 19:41:54', null, '2017-01-12 19:41:54', '0', 'bc4d163c5880ab4901588b6681e50003', '2510c36e321411e6a46d10a0f3df85fa');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927bf6b10017', '2017-01-12 19:41:54', null, '2017-01-12 19:41:54', '0', 'bc4d163c5880ab4901588b6681e50003', '2405dc64321411e6a46d10a0f3df85fa');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927bf6b10018', '2017-01-12 19:41:54', null, '2017-01-12 19:41:54', '0', 'bc4d163c5880ab4901588b6681e50003', '23c87eb0321411e6a46d10a0f3df85fa');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927bf6b10019', '2017-01-12 19:41:54', null, '2017-01-12 19:41:54', '0', 'bc4d163c5880ab4901588b6681e50003', '1f150ec4321411e6a46d10a0f3df85fa');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927c2a95001a', '2017-01-12 19:42:08', null, '2017-01-12 19:42:08', '0', 'bc4d163c5880ab4901588b6681e50003', '4028818458d3f33d0158d407b7de0005');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927c2a96001b', '2017-01-12 19:42:08', null, '2017-01-12 19:42:08', '0', 'bc4d163c5880ab4901588b6681e50003', '4028818458d3f33d0158d40197dd0004');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927c2a96001c', '2017-01-12 19:42:08', null, '2017-01-12 19:42:08', '0', 'bc4d163c5880ab4901588b6681e50003', '25924d86321411e6a46d10a0f3df85fa');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927c2a96001d', '2017-01-12 19:42:08', null, '2017-01-12 19:42:08', '0', 'bc4d163c5880ab4901588b6681e50003', '24f40c7b321411e6a46d10a0f3df85fa');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927c2a96001e', '2017-01-12 19:42:08', null, '2017-01-12 19:42:08', '0', 'bc4d163c5880ab4901588b6681e50003', '275a01e2321411e6a46d10a0f3df85fa');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927c2a960020', '2017-01-12 19:42:08', null, '2017-01-12 19:42:08', '0', 'bc4d163c5880ab4901588b6681e50003', '8afa96f6564a787601564a79fd970000');
INSERT INTO `tbl_user_role` VALUES ('8a8a859359927a930159927c2a960021', '2017-01-12 19:42:08', null, '2017-01-12 19:42:08', '0', 'bc4d163c5880ab4901588b6681e50003', '254f9b64321411e6a46d10a0f3df85fa');
