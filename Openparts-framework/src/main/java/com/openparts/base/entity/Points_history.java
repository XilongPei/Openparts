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
-- Table structure for `points_history`
-- ----------------------------
DROP TABLE IF EXISTS `points_history`;
CREATE TABLE `points_history` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `type` tinyint(2) DEFAULT NULL COMMENT '积分获取类型：1-上传数据  2-校正数据  3-完成悬赏任务 4-支付 5-其它',
  `points` int(11) DEFAULT NULL,
  `author_id` int(11) DEFAULT NULL COMMENT '积分拥有者key值',
  `date_added` date DEFAULT NULL COMMENT '积分操作时间',
  `description` text COMMENT '积分操作说明',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
*/
@Entity
@Table(name="op_points_history")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class Points_history extends OP_BaseEntity {

}
