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
-- Table structure for `parts_category`
-- ----------------------------
DROP TABLE IF EXISTS `parts_category`;
CREATE TABLE `parts_category` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `level` tinyint(4) DEFAULT NULL COMMENT '配件分类级别',
  `name` varchar(255) DEFAULT NULL COMMENT '配件分类名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '上级分类key值',
  `parts_id` int(11) DEFAULT NULL COMMENT '配件类别key值',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
*/
@Entity
@Table(name="op_parts_category")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class Parts_category extends OP_BaseEntity {

}
