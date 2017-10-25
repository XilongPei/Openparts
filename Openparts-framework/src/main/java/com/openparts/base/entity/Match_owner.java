package com.openparts.base.entity;

import com.cnpc.framework.annotation.ForeignShow;
import com.cnpc.framework.annotation.Header;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import com.openparts.base.entity.OP_BaseEntity;

/*
 * 匹配会发生多次，最终结果的owner才在这张表中
 */

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

    private static final long serialVersionUID = 5569761987303812150L;

    @Header(name = "标题")
    @Column(name = "author_id", length = 255)
    private String author_id;

    private Match_car_parts match_car_parts;

    /**
     * @ManyToOne：多对一,cascade：级联,请参考上一篇
      * fetch = FetchType.LAZY,延迟加载策略,如果不想延迟加载可以用FetchType.EAGER
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    public Match_car_parts getMatch_car_parts() {
        return match_car_parts;
    }

    public void setMatch_car_parts(Match_car_parts match_car_parts) {
        this.match_car_parts = match_car_parts;
    }

    public String getAuthor_id() {

        return author_id;
    }

    public void setAuthor_id(String author_id) {

        this.author_id = author_id;
    }

}
