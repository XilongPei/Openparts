package com.openparts.base.entity;

import com.cnpc.framework.annotation.ForeignShow;
import com.cnpc.framework.annotation.Header;
import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class OP_BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ForeignShow
    @Header(name = "id值")
    @Column(name = "id", length = 11)
    private Integer id;

    /**
      * @Id 映射主键属性
      * @GeneratedValue —— 注解声明了主键的生成策略。该注解有如下属性
      * strategy 指定生成的策略,默认是GenerationType. AUTO
      * GenerationType.AUTO 主键由程序控制
      * GenerationType.TABLE 使用一个特定的数据库表格来保存主键
      * GenerationType.IDENTITY 主键由数据库自动生成,主要是自动增长类型
      * GenerationType.SEQUENCE 根据底层数据库的序列来生成主键，条件是数据库支持序列
      * generator 指定生成主键使用的生成器
      */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
