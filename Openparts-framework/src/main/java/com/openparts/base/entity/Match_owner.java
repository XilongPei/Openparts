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
-- Table structure for `match_owner`
-- ----------------------------
DROP TABLE IF EXISTS `match_owner`;
CREATE TABLE `match_owner` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `author_id` int(11) DEFAULT NULL COMMENT '注册用户key值',
  `match_id` int(11) DEFAULT NULL COMMENT '车型与配件匹配数据key值',
  PRIMARY KEY (`id`),
  KEY `author_id` (`author_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
*/
@Entity
@Table(name="op_match_owner")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class Match_owner extends OP_BaseEntity {

}
