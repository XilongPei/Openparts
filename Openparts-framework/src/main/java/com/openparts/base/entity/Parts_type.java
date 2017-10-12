package com.openparts.base.entity;

import java.util.Set;
import com.cnpc.framework.annotation.ForeignShow;
import com.cnpc.framework.annotation.Header;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;

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

    @Header(name = "材料型号名称")
    @Column(name = "name", length = 255)
    private String name;

    /*
    @Header(name = "配件类别key值")
    @Column(name = "parts_id", length = 11)
    private int parts_id;
    */

    @Header(name = "配件生产编码iam")
    @Column(name = "iam", length = 100)
    private String iam;

    /*
    @Header(name = "配件品牌key值")
    @Column(name = "brand_id", length = 11)
    private int brand_id;
    */

    @Header(name = "配件单位key值")
    @Column(name = "format_id", length = 11)
    private int format_id;

    private Set<Parts> parts;
    private Parts_brand parts_brand;
    private Parts_format parts_format;

    /**
      * @OneToMany,cascade：级联,
      * fetch = FetchType.LAZY,延迟加载策略,如果不想延迟加载可以用FetchType.EAGER
      * cascade：级联,它可以有有五个值可选,分别是：
      * CascadeType.PERSIST：级联新建
      * CascadeType.REMOVE : 级联删除
      * CascadeType.REFRESH：级联刷新
      * CascadeType.MERGE  ： 级联更新
      * CascadeType.ALL    ： 以上全部四项
      * @JoinColumn:主表外键字段
      */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parts_id")
    public Set<Parts> getParts() {
        return parts;
    }

    public void setParts(Set<Parts> parts) {
        this.parts = parts;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_id")
    public Parts_brand getParts_brand() {
        return parts_brand;
    }

    public void setParts_brand(Parts_brand parts_brand) {
        this.parts_brand = parts_brand;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "format_id")
    public Parts_format getParts_format() {
        return parts_format;
    }

    public void setParts_format(Parts_format parts_format) {
        this.parts_format = parts_format;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /*
    public void setParts_id(int parts_id) {
        this.parts_id = parts_id;
    }

    public int getParts_id() {
        return parts_id;
    }
    */

    public void setIam(String iam) {
        this.iam = iam;
    }

    public String getIam() {
        return iam;
    }
}
