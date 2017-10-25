package com.openparts.base.entity;

import com.cnpc.framework.annotation.ForeignShow;
import com.cnpc.framework.annotation.Header;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Lob;
import javax.persistence.Basic;
import javax.persistence.FetchType;
import org.hibernate.annotations.Type;
import com.openparts.base.entity.OP_BaseEntity;

/*
-- ----------------------------
-- Table structure for `reward_task_response`
-- ----------------------------
DROP TABLE IF EXISTS `reward_task_response`;
CREATE TABLE `reward_task_response` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `reward_task_id` int(11) DEFAULT NULL COMMENT '悬赏单key值',
  `author_id` int(11) DEFAULT NULL COMMENT '接单人key值',
  `description` text COMMENT '接单说明',
  `date_added` date DEFAULT NULL COMMENT '接单时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
*/
@Entity
@Table(name="op_reward_task_response")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class Reward_task_response extends OP_BaseEntity {

    private static final long serialVersionUID = 5569761987303812150L;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Type(type="text")
    @Header(name = "接单说明")
    @Column(name = "description", length = 3000, nullable=true)
    private String description;
}
