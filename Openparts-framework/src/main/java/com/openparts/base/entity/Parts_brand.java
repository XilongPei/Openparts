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
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

import com.openparts.base.entity.OP_BaseEntity;

/*
-- ----------------------------
-- Table structure for `parts_brand`
-- ----------------------------
DROP TABLE IF EXISTS `parts_brand`;
CREATE TABLE `parts_brand` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '配件品牌名称',
  `cn_name` varchar(50) DEFAULT NULL COMMENT '中文名称',
  `en_name` varchar(50) DEFAULT NULL COMMENT '英文名称',
  `factory_id` int(11) DEFAULT NULL COMMENT '配件生产厂家key值',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
*/
@Entity
@Table(name="op_parts_brand")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class Parts_brand extends OP_BaseEntity {
    @Header(name = "类别名称")
    @Column(name = "name", length = 100)
    private String name;

    @Header(name = "中文名称")
    @Column(name = "cn_name", length = 100)
    private String cn_name;

    @Header(name = "英文名称")
    @Column(name = "en_name", length = 100)
    private String en_name;

    private Parts_factory parts_factory;

    /**
      * @OneToOne：一对一关联
      * cascade：级联,它可以有有五个值可选,分别是：
      * CascadeType.PERSIST：级联新建
      * CascadeType.REMOVE : 级联删除
      * CascadeType.REFRESH：级联刷新
      * CascadeType.MERGE  ： 级联更新
      * CascadeType.ALL    ： 以上全部四项
      * @JoinColumn:主表外键字段
      */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "factory_id")
    public Parts_factory getParts_factory() {
        return parts_factory;
    }

    public void setParts_factory(Parts_factory parts_factory) {
        this.parts_factory = parts_factory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCn_name(String cn_name) {
        this.cn_name = cn_name;
    }

    public String getCn_name() {
        return cn_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public String getEn_name() {
        return en_name;
    }
}
