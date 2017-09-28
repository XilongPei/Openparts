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
-- Table structure for `parts_type`
-- ----------------------------
DROP TABLE IF EXISTS `parts_type`;
CREATE TABLE `parts_type` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '材料型号名称',
  `parts_id` int(11) DEFAULT NULL COMMENT '配件类别key值',
  `iam` varchar(100) DEFAULT NULL COMMENT '配件生产编码iam',
  `brand_id` int(11) DEFAULT NULL COMMENT '配件品牌key值',
  `format_id` int(11) DEFAULT NULL COMMENT '配件单位key值',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
*/
@Entity
@Table(name="op_parts_type")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class Parts_type extends OP_BaseEntity {

}
