package com.openparts.base.entity;

import com.cnpc.framework.annotation.ForeignShow;
import com.cnpc.framework.annotation.Header;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.openparts.base.entity.OP_BaseEntity;

/*
-- ----------------------------
-- Table structure for `match_log`
-- ----------------------------
DROP TABLE IF EXISTS `match_log`;
CREATE TABLE `match_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `match_id` int(11) DEFAULT NULL COMMENT '车型配件匹配key值',
  `type` int(4) NOT NULL COMMENT 'log类型：1-添加  2-校正  3-引用  4-其它',
  `content` text,
  `status` tinyint(4) DEFAULT NULL COMMENT '校正状态： 0-待定  1-成功  2-失败',
  `date_added` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
*/
@Entity
@Table(name="op_match_log")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class Match_log extends OP_BaseEntity {

    private Car car;

    /**
     * @ManyToOne：多对一,cascade：级联,请参考上一篇
      * fetch = FetchType.LAZY,延迟加载策略,如果不想延迟加载可以用FetchType.EAGER
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    public Car getCar() {
        return car;
    }
}
