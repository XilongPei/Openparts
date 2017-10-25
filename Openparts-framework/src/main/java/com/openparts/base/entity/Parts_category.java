package com.openparts.base.entity;

import java.util.Set;
import com.cnpc.framework.annotation.ForeignShow;
import com.cnpc.framework.annotation.Header;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;

import com.openparts.base.entity.OP_BaseEntity;

/*
-- ----------------------------
-- Table structure for `parts_category`
-- ----------------------------
DROP TABLE IF EXISTS `parts_category`;
CREATE TABLE `parts_category` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `level` tinyint(4) DEFAULT NULL COMMENT '配件分类级别',
  `name` varchar(255) DEFAULT NULL COMMENT '配件分类名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '上级分类key值',
  `parts_id` int(11) DEFAULT NULL COMMENT '配件类别key值',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
*/
@Entity
@Table(name="op_parts_category")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class Parts_category extends OP_BaseEntity {

    private static final long serialVersionUID = 5569761987303812150L;

    @Header(name = "配件分类级别")
    @Column(name = "level", length = 4)
    private int level;

    @Header(name = "类别名称")
    @Column(name = "name", length = 100)
    private String name;

    @Header(name = "上级分类key值")
    @Column(name = "parent_id", length = 11)
    private int parent_id;

    @Header(name = "配件类别key值")
    @Column(name = "parts_id", length = 11)
    private int parts_id;

    private Set<Parts> parts;


    /**
      * @OneToMany：一对多,cascade：级联,
      * fetch = FetchType.LAZY,延迟加载策略,如果不想延迟加载可以用FetchType.EAGER
      * cascade：级联,它可以有有五个值可选,分别是：
      * CascadeType.PERSIST：级联新建
      * CascadeType.REMOVE : 级联删除
      * CascadeType.REFRESH：级联刷新
      * CascadeType.MERGE  ： 级联更新
      * CascadeType.ALL    ： 以上全部四项
      * @JoinColumn:主表外键字段
      */
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "parts_id")
    public Set<Parts> getParts() {
        return parts;
    }

    public void setParts(Set<Parts> parts) {
        this.parts = parts;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParts_id(int parts_id) {
        this.parts_id = parts_id;
    }

    public int getParts_id() {
        return parts_id;
    }
}
