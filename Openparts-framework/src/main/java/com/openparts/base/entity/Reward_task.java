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
-- Table structure for `reward_task`
-- ----------------------------
DROP TABLE IF EXISTS `reward_task`;
CREATE TABLE `reward_task` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `reward` int(11) DEFAULT NULL COMMENT '悬赏积分值',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态： 1-已发布  2-结束',
  `deadline` date DEFAULT NULL COMMENT '悬赏到期时间',
  `author_id` int(11) DEFAULT NULL COMMENT '悬赏发布者',
  `date_added` date DEFAULT NULL COMMENT '悬赏发布时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
*/
@Entity
@Table(name="op_reward_task")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class Reward_task extends OP_BaseEntity {

}
