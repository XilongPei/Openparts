package com.openparts.entity;

import com.cnpc.framework.annotation.ForeignShow;
import com.cnpc.framework.annotation.Header;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.openparts.entity.OP_BaseEntity;

/*
-- ----------------------------
-- Table structure for `parts_format`
-- ----------------------------
DROP TABLE IF EXISTS `parts_format`;
CREATE TABLE `parts_format` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL COMMENT '配件单位名称',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
*/
@Entity
@Table(name="op_parts_format")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class Parts_format extends OP_BaseEntity {

}
