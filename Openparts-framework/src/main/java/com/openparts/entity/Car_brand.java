package com.openparts.entity;

import com.cnpc.framework.annotation.ForeignShow;
import com.cnpc.framework.annotation.Header;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.openparts.entity.OP_BaseEntity;
import com.openparts.entity.OP_BaseEntity;

/*
-- ----------------------------
-- Table structure for `car_brand`
-- ----------------------------
DROP TABLE IF EXISTS `car_brand`;
CREATE TABLE `car_brand` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `level` tinyint(4) DEFAULT NULL COMMENT '品牌级别： 0-品牌   1-车系',
  `name` varchar(50) DEFAULT NULL COMMENT '汽车品牌名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '上级品牌key值',
  `image` varchar(255) DEFAULT NULL COMMENT '汽车品牌logo图片地址',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
*/

@Entity
@Table(name="op_car_brand")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class Car_brand extends OP_BaseEntity {
    @Header(name = "品牌级别")
    @Column(name = "level", length = 20)
    private String level;

    @Header(name = "汽车品牌名称")
    @Column(name = "name", length = 20)
    private String name;

    @Header(name = "上级品牌key值")
    @Column(name = "parent_id", length = 20)
    private String parent_id;

    @Header(name = "汽车品牌logo图片地址")
    @Column(name = "image", length = 20)
    private String image;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
