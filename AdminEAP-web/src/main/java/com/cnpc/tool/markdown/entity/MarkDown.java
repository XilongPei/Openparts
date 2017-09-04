package com.cnpc.tool.markdown.entity;

import com.cnpc.framework.annotation.Header;
import com.cnpc.framework.base.entity.BaseEntity;
import com.cnpc.framework.base.entity.Dict;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "tbl_markdown")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class MarkDown extends BaseEntity {

    private static final long serialVersionUID = 7823893456789085L;


    @Header(name="标题")
    @Column(name="title")
    private String title;

    @Header(name="关键字")
    @Column(name="keywords")
    private String keywords;

    @Header(name="内容")
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Type(type="text")
    @Column(name="content")
    private String content;

    @Header(name="用户")
    @Column(name="user_id")
    private String userId;

    @Header(name="文章类别",joinClass = Dict.class)
    @Column(name="type_id",length = 36)
    private String typeId;

    //冗余字段
    @Header(name="文章列别名称")
    @Column(name="type_name",length = 36)
    private String typeName;

    //摘要
    @Header(name="文章摘要")
    @Column(name="summary",length = 1000)
    private String summary;

    @Header(name="作者")
    @Column(name="author")
    private String author;

    @Header(name="编码")
    @Column(name="code")
    private String code;


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
