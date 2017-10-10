package com.openparts.base.entity;

import java.util.Set;
import com.cnpc.framework.annotation.Header;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import com.openparts.base.entity.OP_BaseEntity;
/*
-- ----------------------------
-- Table structure for `car`
-- ----------------------------
DROP TABLE IF EXISTS `car`;
CREATE TABLE `car` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '车型key值',
  `ckey` varchar(20) DEFAULT NULL COMMENT '车型唯一编码',
  `brand_id` int(4) DEFAULT NULL COMMENT '车辆品牌key值',
  `name` varchar(100) DEFAULT NULL COMMENT '车型名称',
  `sales_name` varchar(100) DEFAULT NULL COMMENT '销售名称',
  `sales_version` varchar(100) DEFAULT NULL COMMENT '销售版本  eg：经典版、豪华型、减配版',
  `vehicle_of_year` varchar(4) DEFAULT NULL COMMENT '车型年款  eg：2013、2014',
  `emission_standards` varchar(10) DEFAULT NULL COMMENT '排放标准  eg：欧3、欧4',
  `vehicle_type` varchar(10) DEFAULT NULL COMMENT '车辆类型  eg：轿车、卡车',
  `vehicle level` varchar(10) DEFAULT NULL COMMENT '车辆级别  eg：小型车、大型车',
  `guide_price` varchar(10) DEFAULT NULL COMMENT '指导价',
  `launch year` varchar(4) DEFAULT NULL COMMENT '上市年份',
  `launch month` varchar(2) DEFAULT NULL COMMENT '上市月份',
  `year_of_production` varchar(4) DEFAULT NULL COMMENT '生产年份',
  `eop_year` varchar(4) DEFAULT NULL COMMENT '停产年份',
  `production_status` tinyint(1) DEFAULT NULL COMMENT '生产状态 eg：停产',
  `sales_status` tinyint(1) DEFAULT NULL COMMENT '销售状态   eg：在售、停售',
  `country` varchar(50) DEFAULT NULL COMMENT '国家',
  `imported` tinyint(1) DEFAULT NULL COMMENT '国内合资进口：1-国内  2-合资  3-进口',
  `vin` varchar(17) DEFAULT NULL COMMENT '车型vin码',
  `engine_code` varchar(50) DEFAULT NULL COMMENT '发动机型号',
  `capacity` int(4) DEFAULT NULL COMMENT '气缸容量（ml）',
  `air_intake_form` varchar(10) DEFAULT NULL COMMENT '进气方式',
  `fuel_type` varchar(10) DEFAULT NULL COMMENT '燃油类型   eg：汽油、柴油',
  `fuel_label` varchar(10) DEFAULT NULL COMMENT '燃油标号   eg：92#、0#',
  `hp` int(4) DEFAULT NULL COMMENT '功率-马力',
  `kw` int(4) DEFAULT NULL COMMENT '功率-千瓦',
  `maximum_power_speed` varchar(20) DEFAULT NULL COMMENT 'Maximum power speed最大功率转速',
  `peak_torque` int(4) DEFAULT NULL COMMENT 'Peak torque (N, m)  最大扭矩',
  `maximum_torque_speed` varchar(20) DEFAULT NULL COMMENT 'Maximum torque speed最大扭矩转速',
  `cylinder_arrangement` varchar(20) DEFAULT NULL COMMENT '气缸排列形式',
  `number_of_cylinder` tinyint(2) DEFAULT NULL COMMENT '气缸数量',
  `compression_ratio` varchar(10) DEFAULT NULL COMMENT '空气压缩比',
  `injection_type` varchar(10) DEFAULT NULL COMMENT '点火类型  eg：多点电喷',
  `fuel_consumption` varchar(10) DEFAULT NULL COMMENT 'MiIT integrated fuel consumption平均油耗',
  `city_fuel_consumption` varchar(10) DEFAULT NULL COMMENT 'city fuel consumption   城市道路油耗',
  `suburban_district_fuel_consumption` varchar(10) DEFAULT NULL COMMENT 'Suburban district Fuel consumption  郊区油耗',
  `acceleration_time` tinyint(2) DEFAULT NULL COMMENT '百公里加速时间',
  `top_speed` int(4) DEFAULT NULL COMMENT '最高转速',
  `engine_technology` varchar(50) DEFAULT NULL COMMENT '发动机技术类型',
  `three_yuan_catalysts` tinyint(1) DEFAULT NULL COMMENT '三元催化',
  `cooling_way` varchar(10) DEFAULT NULL COMMENT '冷却方式  eg：水冷',
  `cylinder_diameter` varchar(10) DEFAULT NULL COMMENT '气缸直径',
  `stroke` varchar(10) DEFAULT NULL COMMENT '行程',
  `engine_description` varchar(100) DEFAULT NULL COMMENT '发动机名称 eg：3.0L L6 258PS 多点电喷汽油发动机',
  `transmission_type` varchar(10) DEFAULT NULL COMMENT '变速器类型  eg:自动',
  `transmission_description` varchar(100) DEFAULT NULL COMMENT '变速器名称  eg:手自一体变速器(AMT)',
  `number_of_gear` tinyint(2) DEFAULT NULL COMMENT '挡位数量',
  `front_brake_type` varchar(50) DEFAULT NULL COMMENT '前制动类型  eg：通风盘式',
  `after_brake_type` varchar(50) DEFAULT NULL COMMENT '后制动类型  eg：通风盘式',
  `front_suspension_type` varchar(100) DEFAULT NULL COMMENT '前悬挂类型 eg:轻质双球节弹簧减震支柱前桥',
  `after_suspension_type` varchar(100) DEFAULT NULL COMMENT '后悬挂类型 eg:多连杆式整体铝制后桥悬挂',
  `engine_location` varchar(10) DEFAULT NULL COMMENT '发动机位置  eg：前置',
  `drive_type` varchar(20) DEFAULT NULL COMMENT '动力输出类型  eg：后轮驱动',
  `drive_form` varchar(20) DEFAULT NULL COMMENT '动力输出形式  eg:前置后驱',
  `body_type` varchar(20) DEFAULT NULL COMMENT '车身类型 eg:三厢',
  `long` int(4) DEFAULT NULL COMMENT '车长',
  `wide` int(4) DEFAULT NULL COMMENT '车宽',
  `high` int(4) DEFAULT NULL COMMENT '车高',
  `wheelbase` int(4) DEFAULT NULL COMMENT '轴距 eg:3128',
  `door_number` tinyint(2) DEFAULT NULL COMMENT 'The door number',
  `seating` tinyint(2) DEFAULT NULL COMMENT '座位数',
  `front_tire_type` varchar(50) DEFAULT NULL COMMENT '前轮胎类型',
  `after_tire_type` varchar(50) DEFAULT NULL COMMENT '后轮胎类型',
  `spare_tire_type` varchar(50) DEFAULT NULL COMMENT '备胎类型',
  `factory_id` int(11) DEFAULT NULL COMMENT '生产厂家key值',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='车型表';
*/

@Entity
@Table(name="op_car")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class Car extends OP_BaseEntity {

    @Header(name = "车型唯一编码")
    @Column(name = "ckey", length = 20)
    private String ckey;

    @Header(name = "车辆品牌key值")
    @Column(name = "brand_id", length = 4)
    private String brand_id;

    @Header(name = "车型名称")
    @Column(name = "name", length = 100)
    private String name;

    @Header(name = "销售名称")
    @Column(name = "sales_name", length = 100)
    private String sales_name;

    private Set<Car_brand> car_brand;
    private Set<Match_car_parts> match_car_parts;
    private Car_factory car_factory;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    public Set<Car_brand> getCar_brand() {
        return car_brand;
    }

    public void setCar_brand(Set<Car_brand> car_brand) {
        this.car_brand = car_brand;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    public Set<Match_car_parts> getMatch_car_parts() {
        return match_car_parts;
    }

    public void setMatch_car_parts(Set<Match_car_parts> match_car_parts) {
        this.match_car_parts = match_car_parts;
    }

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
    public Car_factory getCar_factory() {
        return car_factory;
    }

    public void setCar_factory(Car_factory car_factory) {
        this.car_factory = car_factory;
    }

    public String getCkey() {
        return ckey;
    }

    public void setCkey(String ckey) {
        this.ckey = ckey;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSales_name() {
        return sales_name;
    }

    public void setSales_name(String sales_name) {
        this.sales_name = sales_name;
    }
}
