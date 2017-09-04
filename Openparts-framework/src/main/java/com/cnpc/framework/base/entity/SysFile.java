package com.cnpc.framework.base.entity;

import com.cnpc.framework.annotation.Header;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by billJiang on 2017/3/5.
 * e-mail:475572229@qq.com  qq:475572229
 * 附件上传实体类
 */
@Entity
@Table(name="tbl_file")
public class SysFile extends BaseEntity {

    @Header(name = "实际名称")
    @Column(name = "fileName")
    private String fileName;
    @Header(name = "保存的名称")
    @Column(name = "savedName")
    private String savedName;
    @Header(name = "存储地址")
    @Column(name = "filePath")
    private String filePath;
    @Header(name = "附件大小")
    @Column(name = "fileSize")
    private Long fileSize;

    @Header(name = "创建者id")
    @Column(name = "create_user_id")
    private String createUserId;

    /**
     * 业务表单ID
     */
    @Column(name = "form_ID")
    private String formId;

    public String getSavedName() {
        return savedName;
    }

    public void setSavedName(String savedName) {
        this.savedName = savedName;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

}