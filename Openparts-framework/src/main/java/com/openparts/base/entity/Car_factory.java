package com.openparts.entity;

import java.util.Date;
import com.cnpc.framework.annotation.ForeignShow;
import com.cnpc.framework.annotation.Header;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.openparts.entity.OP_BaseEntity;

/*
-- ----------------------------
-- Table structure for `car_factory`
-- ----------------------------
DROP TABLE IF EXISTS `car_factory`;
CREATE TABLE `car_factory` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '配件厂家名称',
  `address` varchar(255) DEFAULT NULL COMMENT '配件厂家地址',
  `description` text COMMENT '配件厂家介绍',
  `date_added` int(11) DEFAULT NULL COMMENT '添加日期',
  `update_time` int(11) DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
*/
@Entity
@Table(name="op_car_factory")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class Car_factory extends OP_BaseEntity {

    @Header(name = "厂家名称")
    @Column(name = "name", length = 255)
    private String name;

    @Header(name = "厂家地址")
    @Column(name = "address", length = 255)
    private String address;

    @Header(name = "厂家介绍")
    @Column(name = "description", length = 3000)
    private String description;

    @Header(name = "添加日期")
    @Column(name = "date_added")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date_added;

    @Header(name = "最后更新时间")
    @Column(name = "update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date update_time;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate_added() {

        return date_added;
    }

    public void setDate_added(Date date_added) {

        this.date_added = date_added;
    }

    public Date getUpdate_time() {

        return update_time;
    }

    public void setUpdate_time(Date update_time) {

        this.update_time = update_time;
    }
}
