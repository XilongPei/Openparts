package com.cnpc.framework.base.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author bin
 *
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID自动生成策略
     */
    @Id
    @GenericGenerator(name = "id", strategy = "uuid")
    @GeneratedValue(generator = "id")
    @Column(name = "id", length = 36)
    protected String id;


    /**
     * 版本号
     */
    @Version
    @Column(name = "version")
    protected Integer version;

    /**
     * 创建时间
     */
    @Column(name = "create_date_time")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    protected Date createDateTime;

    /**
     * 最后修改时间
     */
    @Column(name = "update_date_time")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    protected Date updateDateTime;

    /**
     * 删除标记(0启用，1禁用)
     */
    @Column(name = "deleted")
    private Integer deleted;

    public Integer getVersion() {

        return version;
    }

    public void setVersion(Integer version) {

        this.version = version;
    }

    public Date getCreateDateTime() {

        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {

        this.createDateTime = createDateTime;
    }

    public Date getUpdateDateTime() {

        return updateDateTime;
    }

    public void setUpdateDateTime(Date updateDateTime) {

        this.updateDateTime = updateDateTime;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public Integer getDeleted() {

        return deleted == null ? 0 : deleted;
    }

    public void setDeleted(Integer deleted) {
        deleted=deleted==null?0:deleted;
        this.deleted = deleted;
    }

}
