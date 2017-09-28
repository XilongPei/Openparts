package com.openparts.base.entity;

import com.cnpc.framework.annotation.ForeignShow;
import com.cnpc.framework.annotation.Header;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.openparts.base.entity.OP_BaseEntity;

/*
-- ----------------------------
-- Table structure for `parts_factory`
-- ----------------------------
DROP TABLE IF EXISTS `parts_factory`;
CREATE TABLE `parts_factory` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '配件厂家名称',
  `address` varchar(255) DEFAULT NULL COMMENT '配件厂家地址',
  `description` text COMMENT '配件厂家介绍',
  `date_added` int(11) DEFAULT NULL COMMENT '添加日期',
  `update_time` int(11) DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
*/
@Entity
@Table(name="op_parts_factory")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class Parts_factory extends OP_BaseEntity {

}
