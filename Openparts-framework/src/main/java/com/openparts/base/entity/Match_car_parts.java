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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import com.openparts.base.entity.OP_BaseEntity;

/*
-- ----------------------------
-- Table structure for `match_car_parts`
-- ----------------------------
DROP TABLE IF EXISTS `match_car_parts`;
CREATE TABLE `match_car_parts` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `car_id` int(11) DEFAULT NULL COMMENT '车型key值',
  `parts_type_id` int(11) DEFAULT NULL COMMENT '配件型号key值',
  `oe_code` varchar(100) DEFAULT NULL COMMENT 'oe编码',
  `oe_name` varchar(100) DEFAULT NULL COMMENT 'oe名称',
  `count` tinyint(4) DEFAULT NULL COMMENT '配件数量',
  `status` tinyint(4) DEFAULT NULL COMMENT '匹配数据状态：0-待校正  1-已校正',
  `used_times` int(11) DEFAULT NULL COMMENT '被引用次数',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
*/
@Entity
@Table(name="op_match_car_parts")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class Match_car_parts extends OP_BaseEntity {

    @Header(name = "车型key值")
    @Column(name = "car_id", length = 255)
    private String car_id;

    @Header(name = "配件型号key值")
    @Column(name = "parts_type_id", length = 255)
    private String parts_type_id;

    @Header(name = "oe编码")
    @Column(name = "oe_code", length = 100)
    private String oe_code;

    @Header(name = "oe名称")
    @Column(name = "oe_name", length = 100)
    private String oe_name;

    @Header(name = "配件数量")
    @Column(name = "count", length = 3)
    private int count;

    @Header(name = "匹配数据状态")
    @Column(name = "status", length = 1)
    private int status;

    @Header(name = "被引用次数")
    @Column(name = "used_times", length = 11)
    private int used_times;

    private Car car;
    private Set<Parts_type> parts_type;

    /**
      * @ManyToOne：多对一,cascade：级联
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
    @JoinColumn(name = "car_id")
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "parts_type_id")
    public Set<Parts_type> getParts_type() {
        return parts_type;
    }

    public void setParts_type(Set<Parts_type> parts_type) {
        this.parts_type = parts_type;
    }

    public void setOe_code(String oe_code) {
        this.oe_code = oe_code;
    }

    public String getOe_code() {
        return oe_code;
    }

    public void setOe_name(String oe_name) {
        this.oe_code = oe_name;
    }

    public String getOe_name() {
        return oe_name;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setUsed_times(int used_times) {
        this.used_times = used_times;
    }

    public int getUsed_times() {
        return used_times;
    }
}
