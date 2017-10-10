package com.openparts.base.entity;

import com.cnpc.framework.annotation.Header;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.openparts.base.entity.OP_BaseEntity;

/*
-- ----------------------------
-- Table structure for `car_brand_alias`
-- ----------------------------
DROP TABLE IF EXISTS `car_brand_alias`;
CREATE TABLE `car_brand_alias` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `brand_id` int(11) DEFAULT NULL COMMENT '车辆品牌名称的key值',
  `alias` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
*/

@Entity
@Table(name="op_car_brand_alias")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class Car_brand_alias extends OP_BaseEntity {

    /*
    @Header(name = "车辆品牌key值")
    @Column(name = "brand_id", length = 11)
    private String brand_id;
    */

    @Header(name = "汽车品牌别名")
    @Column(name = "alias", length = 255)
    private String alias;

    private Car_brand car_brand;
    /**
      * @ManyToOne：多对一,cascade：级联,请参考上一篇
      * fetch = FetchType.LAZY,延迟加载策略,如果不想延迟加载可以用FetchType.EAGER
      * cascade：级联,它可以有有五个值可选,分别是：
      * CascadeType.PERSIST：级联新建
      * CascadeType.REMOVE : 级联删除
      * CascadeType.REFRESH：级联刷新
      * CascadeType.MERGE  ： 级联更新
      * CascadeType.ALL    ： 以上全部四项
      * @JoinColumn:主表外键字段
      */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    public Car_brand getCar_brand() {
        return car_brand;
    }

    public void setCar_brand(Car_brand car_brand) {
        this.car_brand = car_brand;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
