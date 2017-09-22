/*
Navicat MySQL Data Transfer

Source Server         : mysql-local
Source Server Version : 50619
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50619
File Encoding         : 65001

Date: 2017-09-21 18:59:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `car`
-- ----------------------------
DROP TABLE IF EXISTS `car`;
CREATE TABLE `car` (
  `id` int(11) NOT NULL DEFAULT '0' COMMENT '车型key值',
  `ckey` varchar(20) DEFAULT NULL COMMENT '车型唯一编码',
  `brand_id` int(4) DEFAULT NULL COMMENT '车辆品牌key值',
  `name` varchar(100) DEFAULT NULL COMMENT '车型名称',
  `sales_name` varchar(100) DEFAULT NULL COMMENT '销售名称',
  `sales_version` varchar(100) DEFAULT NULL COMMENT '销售版本  eg：经典版、豪华型、减配版',
  `vehicle_of_year` varchar(4) DEFAULT NULL COMMENT '车型年款  eg：2013、2014',
  `emission_standards` varchar(10) DEFAULT NULL COMMENT '排放标准  eg：欧3、欧4',
  `vehicle_type` varchar(10) DEFAULT NULL COMMENT '车辆类型  eg：轿车、卡车',
  `vehicle level` varchar(10) DEFAULT NULL COMMENT '车辆级别  eg：小型车、大型车',
  `guide_price` varchar(10) DEFAULT NULL COMMENT '指导价',
  `launch year` varchar(4) DEFAULT NULL COMMENT '上市年份',
  `launch month` varchar(2) DEFAULT NULL COMMENT '上市月份',
  `year_of_production` varchar(4) DEFAULT NULL COMMENT '生产年份',
  `eop_year` varchar(4) DEFAULT NULL COMMENT '停产年份',
  `production_status` tinyint(1) DEFAULT NULL COMMENT '生产状态 eg：停产',
  `sales_status` tinyint(1) DEFAULT NULL COMMENT '销售状态   eg：在售、停售',
  `country` varchar(50) DEFAULT NULL COMMENT '国家',
  `imported` tinyint(1) DEFAULT NULL COMMENT '国内合资进口：1-国内  2-合资  3-进口',
  `vin` varchar(17) DEFAULT NULL COMMENT '车型vin码',
  `engine_code` varchar(50) DEFAULT NULL COMMENT '发动机型号',
  `capacity` int(4) DEFAULT NULL COMMENT '气缸容量（ml）',
  `air_intake_form` varchar(10) DEFAULT NULL COMMENT '进气方式',
  `fuel_type` varchar(10) DEFAULT NULL COMMENT '燃油类型   eg：汽油、柴油',
  `fuel_label` varchar(10) DEFAULT NULL COMMENT '燃油标号   eg：92#、0#',
  `hp` int(4) DEFAULT NULL COMMENT '功率-马力',
  `kw` int(4) DEFAULT NULL COMMENT '功率-千瓦',
  `maximum_power_speed` varchar(20) DEFAULT NULL COMMENT 'Maximum power speed最大功率转速',
  `peak_torque` int(4) DEFAULT NULL COMMENT 'Peak torque (N, m)  最大扭矩',
  `maximum_torque_speed` varchar(20) DEFAULT NULL COMMENT 'Maximum torque speed最大扭矩转速',
  `cylinder_arrangement` varchar(20) DEFAULT NULL COMMENT '气缸排列形式',
  `number_of_cylinder` tinyint(2) DEFAULT NULL COMMENT '气缸数量',
  `compression_ratio` varchar(10) DEFAULT NULL COMMENT '空气压缩比',
  `injection_type` varchar(10) DEFAULT NULL COMMENT '点火类型  eg：多点电喷',
  `fuel_consumption` varchar(10) DEFAULT NULL COMMENT 'MiIT integrated fuel consumption平均油耗',
  `city_fuel_consumption` varchar(10) DEFAULT NULL COMMENT 'city fuel consumption   城市道路油耗',
  `suburban_district_fuel_consumption` varchar(10) DEFAULT NULL COMMENT 'Suburban district Fuel consumption  郊区油耗',
  `acceleration_time` tinyint(2) DEFAULT NULL COMMENT '百公里加速时间',
  `top_speed` int(4) DEFAULT NULL COMMENT '最高转速',
  `engine_technology` varchar(50) DEFAULT NULL COMMENT '发动机技术类型',
  `three_yuan_catalysts` tinyint(1) DEFAULT NULL COMMENT '三元催化',
  `cooling_way` varchar(10) DEFAULT NULL COMMENT '冷却方式  eg：水冷',
  `cylinder_diameter` varchar(10) DEFAULT NULL COMMENT '气缸直径',
  `stroke` varchar(10) DEFAULT NULL COMMENT '行程',
  `engine_description` varchar(100) DEFAULT NULL COMMENT '发动机名称 eg：3.0L L6 258PS 多点电喷汽油发动机',
  `transmission_type` varchar(10) DEFAULT NULL COMMENT '变速器类型  eg:自动',
  `transmission_description` varchar(100) DEFAULT NULL COMMENT '变速器名称  eg:手自一体变速器(AMT)',
  `number_of_gear` tinyint(2) DEFAULT NULL COMMENT '挡位数量',
  `front_brake_type` varchar(50) DEFAULT NULL COMMENT '前制动类型  eg：通风盘式',
  `after_brake_type` varchar(50) DEFAULT NULL COMMENT '后制动类型  eg：通风盘式',
  `front_suspension_type` varchar(100) DEFAULT NULL COMMENT '前悬挂类型 eg:轻质双球节弹簧减震支柱前桥',
  `after_suspension_type` varchar(100) DEFAULT NULL COMMENT '后悬挂类型 eg:多连杆式整体铝制后桥悬挂',
  `engine_location` varchar(10) DEFAULT NULL COMMENT '发动机位置  eg：前置',
  `drive_type` varchar(20) DEFAULT NULL COMMENT '动力输出类型  eg：后轮驱动',
  `drive_form` varchar(20) DEFAULT NULL COMMENT '动力输出形式  eg:前置后驱',
  `body_type` varchar(20) DEFAULT NULL COMMENT '车身类型 eg:三厢',
  `long` int(4) DEFAULT NULL COMMENT '车长',
  `wide` int(4) DEFAULT NULL COMMENT '车宽',
  `high` int(4) DEFAULT NULL COMMENT '车高',
  `wheelbase` int(4) DEFAULT NULL COMMENT '轴距 eg:3128',
  `door_number` tinyint(2) DEFAULT NULL COMMENT 'The door number',
  `seating` tinyint(2) DEFAULT NULL COMMENT '座位数',
  `front_tire_type` varchar(50) DEFAULT NULL COMMENT '前轮胎类型',
  `after_tire_type` varchar(50) DEFAULT NULL COMMENT '后轮胎类型',
  `spare_tire_type` varchar(50) DEFAULT NULL COMMENT '备胎类型',
  `factory_id` int(11) DEFAULT NULL COMMENT '生产厂家key值',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='车型表';

-- ----------------------------
-- Records of car
-- ----------------------------

-- ----------------------------
-- Table structure for `car_brand`
-- ----------------------------
DROP TABLE IF EXISTS `car_brand`;
CREATE TABLE `car_brand` (
  `id` int(11) NOT NULL DEFAULT '0',
  `level` tinyint(4) DEFAULT NULL COMMENT '品牌级别： 0-品牌   1-车系',
  `name` varchar(50) DEFAULT NULL COMMENT '汽车品牌名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '上级品牌key值',
  `image` varchar(255) DEFAULT NULL COMMENT '汽车品牌logo图片地址',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of car_brand
-- ----------------------------

-- ----------------------------
-- Table structure for `car_brand_alias`
-- ----------------------------
DROP TABLE IF EXISTS `car_brand_alias`;
CREATE TABLE `car_brand_alias` (
  `id` int(11) NOT NULL DEFAULT '0',
  `brand_id` int(11) DEFAULT NULL COMMENT '车辆品牌名称的key值',
  `alias` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of car_brand_alias
-- ----------------------------

-- ----------------------------
-- Table structure for `car_factory`
-- ----------------------------
DROP TABLE IF EXISTS `car_factory`;
CREATE TABLE `car_factory` (
  `id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(255) DEFAULT NULL COMMENT '配件厂家名称',
  `address` varchar(255) DEFAULT NULL COMMENT '配件厂家地址',
  `description` text COMMENT '配件厂家介绍',
  `date_added` int(11) DEFAULT NULL COMMENT '添加日期',
  `update_time` int(11) DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of car_factory
-- ----------------------------

-- ----------------------------
-- Table structure for `match_car_parts`
-- ----------------------------
DROP TABLE IF EXISTS `match_car_parts`;
CREATE TABLE `match_car_parts` (
  `id` int(11) NOT NULL DEFAULT '0',
  `car_id` int(11) DEFAULT NULL COMMENT '车型key值',
  `parts_type_id` int(11) DEFAULT NULL COMMENT '配件型号key值',
  `oe_code` varchar(100) DEFAULT NULL COMMENT 'oe编码',
  `oe_name` varchar(100) DEFAULT NULL COMMENT 'oe名称',
  `count` tinyint(4) DEFAULT NULL COMMENT '配件数量',
  `status` tinyint(4) DEFAULT NULL COMMENT '匹配数据状态：0-待校正  1-已校正',
  `used_times` int(11) DEFAULT NULL COMMENT '被引用次数',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of match_car_parts
-- ----------------------------

-- ----------------------------
-- Table structure for `match_log`
-- ----------------------------
DROP TABLE IF EXISTS `match_log`;
CREATE TABLE `match_log` (
  `id` int(11) NOT NULL DEFAULT '0',
  `match_id` int(11) DEFAULT NULL COMMENT '车型配件匹配key值',
  `type` int(4) NOT NULL COMMENT 'log类型：1-添加  2-校正  3-引用  4-其它',
  `content` text,
  `status` tinyint(4) DEFAULT NULL COMMENT '校正状态： 0-待定  1-成功  2-失败',
  `date_added` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of match_log
-- ----------------------------

-- ----------------------------
-- Table structure for `match_owner`
-- ----------------------------
DROP TABLE IF EXISTS `match_owner`;
CREATE TABLE `match_owner` (
  `id` int(11) NOT NULL DEFAULT '0',
  `author_id` int(11) DEFAULT NULL COMMENT '注册用户key值',
  `match_id` int(11) DEFAULT NULL COMMENT '车型与配件匹配数据key值',
  PRIMARY KEY (`id`),
  KEY `author_id` (`author_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of match_owner
-- ----------------------------

-- ----------------------------
-- Table structure for `parts`
-- ----------------------------
DROP TABLE IF EXISTS `parts`;
CREATE TABLE `parts` (
  `id` int(11) NOT NULL DEFAULT '0' COMMENT '配件类别key值',
  `name` varchar(100) DEFAULT NULL COMMENT '材料类别名称',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='配件类别表';

-- ----------------------------
-- Records of parts
-- ----------------------------

-- ----------------------------
-- Table structure for `parts_alias`
-- ----------------------------
DROP TABLE IF EXISTS `parts_alias`;
CREATE TABLE `parts_alias` (
  `id` int(11) NOT NULL DEFAULT '0',
  `parts_id` int(11) DEFAULT NULL COMMENT '配件类别名称的key值',
  `alias` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of parts_alias
-- ----------------------------

-- ----------------------------
-- Table structure for `parts_brand`
-- ----------------------------
DROP TABLE IF EXISTS `parts_brand`;
CREATE TABLE `parts_brand` (
  `id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(255) DEFAULT NULL COMMENT '配件品牌名称',
  `cn_name` varchar(50) DEFAULT NULL COMMENT '中文名称',
  `en_name` varchar(50) DEFAULT NULL COMMENT '英文名称',
  `factory_id` int(11) DEFAULT NULL COMMENT '配件生产厂家key值',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of parts_brand
-- ----------------------------

-- ----------------------------
-- Table structure for `parts_category`
-- ----------------------------
DROP TABLE IF EXISTS `parts_category`;
CREATE TABLE `parts_category` (
  `id` int(11) NOT NULL DEFAULT '0',
  `level` tinyint(4) DEFAULT NULL COMMENT '配件分类级别',
  `name` varchar(255) DEFAULT NULL COMMENT '配件分类名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '上级分类key值',
  `parts_id` int(11) DEFAULT NULL COMMENT '配件类别key值',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of parts_category
-- ----------------------------

-- ----------------------------
-- Table structure for `parts_factory`
-- ----------------------------
DROP TABLE IF EXISTS `parts_factory`;
CREATE TABLE `parts_factory` (
  `id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(255) DEFAULT NULL COMMENT '配件厂家名称',
  `address` varchar(255) DEFAULT NULL COMMENT '配件厂家地址',
  `description` text COMMENT '配件厂家介绍',
  `date_added` int(11) DEFAULT NULL COMMENT '添加日期',
  `update_time` int(11) DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of parts_factory
-- ----------------------------

-- ----------------------------
-- Table structure for `parts_format`
-- ----------------------------
DROP TABLE IF EXISTS `parts_format`;
CREATE TABLE `parts_format` (
  `id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(20) DEFAULT NULL COMMENT '配件单位名称',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of parts_format
-- ----------------------------

-- ----------------------------
-- Table structure for `parts_type`
-- ----------------------------
DROP TABLE IF EXISTS `parts_type`;
CREATE TABLE `parts_type` (
  `id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(255) DEFAULT NULL COMMENT '材料型号名称',
  `parts_id` int(11) DEFAULT NULL COMMENT '配件类别key值',
  `iam` varchar(100) DEFAULT NULL COMMENT '配件生产编码iam',
  `brand_id` int(11) DEFAULT NULL COMMENT '配件品牌key值',
  `format_id` int(11) DEFAULT NULL COMMENT '配件单位key值',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of parts_type
-- ----------------------------

-- ----------------------------
-- Table structure for `points_history`
-- ----------------------------
DROP TABLE IF EXISTS `points_history`;
CREATE TABLE `points_history` (
  `id` int(11) NOT NULL DEFAULT '0',
  `type` tinyint(2) DEFAULT NULL COMMENT '积分获取类型：1-上传数据  2-校正数据  3-完成悬赏任务 4-支付 5-其它',
  `points` int(11) DEFAULT NULL,
  `author_id` int(11) DEFAULT NULL COMMENT '积分拥有者key值',
  `date_added` date DEFAULT NULL COMMENT '积分操作时间',
  `description` text COMMENT '积分操作说明',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of points_history
-- ----------------------------

-- ----------------------------
-- Table structure for `reward_task`
-- ----------------------------
DROP TABLE IF EXISTS `reward_task`;
CREATE TABLE `reward_task` (
  `id` int(11) NOT NULL DEFAULT '0',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `reward` int(11) DEFAULT NULL COMMENT '悬赏积分值',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态： 1-已发布  2-结束',
  `deadline` date DEFAULT NULL COMMENT '悬赏到期时间',
  `author_id` int(11) DEFAULT NULL COMMENT '悬赏发布者',
  `date_added` date DEFAULT NULL COMMENT '悬赏发布时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reward_task
-- ----------------------------

-- ----------------------------
-- Table structure for `reward_task_response`
-- ----------------------------
DROP TABLE IF EXISTS `reward_task_response`;
CREATE TABLE `reward_task_response` (
  `id` int(11) NOT NULL DEFAULT '0',
  `reward_task_id` int(11) DEFAULT NULL COMMENT '悬赏单key值',
  `author_id` int(11) DEFAULT NULL COMMENT '接单人key值',
  `description` text COMMENT '接单说明',
  `date_added` date DEFAULT NULL COMMENT '接单时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reward_task_response
-- ----------------------------
