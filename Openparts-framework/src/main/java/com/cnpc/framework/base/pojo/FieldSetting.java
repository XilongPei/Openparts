package com.cnpc.framework.base.pojo;

import com.cnpc.framework.annotation.Header;
import com.cnpc.framework.base.entity.Dict;
import com.cnpc.framework.utils.PingYinUtil;
import com.cnpc.framework.utils.StrUtil;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;

public class FieldSetting {
    private Integer rowIndex;
    private String columnName;
    private String labelName;
    private String tagType;
    private String type;
    private String validateType;
    //字典编码，或者url路径
    private String dictCode;
    private String isSelected;
    private String isCondition;


    //为FieldSetting赋值
    public void setFieldParam(Field field) {
        String remark = field.getAnnotation(Header.class).name();
        this.setColumnName(field.getName());
        String dataSource = field.getAnnotation(Header.class).dataSource();
        if (!StrUtil.isEmpty(dataSource)) {
            this.setDictCode(dataSource);
            if (field.getAnnotation(Header.class).joinClass().equals(Dict.class)) {
                this.setTagType("dictSelector");
            }
        } else if (field.getAnnotation(Header.class).joinClass().equals(Dict.class)) {
            this.setDictCode(PingYinUtil.getFirstSpell(field.getName()).toUpperCase());
            this.setTagType("dictSelector");
        }
        if (field.getType() == String.class) {
            if (StrUtil.isEmpty(this.getTagType())) {
                if (field.getAnnotation(Column.class).length() > 255) {
                    this.setTagType("textarea");
                } else {
                    this.setTagType(field.getName().equals("id") ? "hidden" : "text");
                }
            }
            this.setType("String");
            this.setValidateType(field.getName().toLowerCase().endsWith("id") ? null : "notEmpty:{message:'" + remark + "不能为空'}");
            if (this.getColumnName().toLowerCase().contains("email")) {
                this.setValidateType(this.getValidateType() + (StrUtil.isEmpty(this.getValidateType()) ? "" : ",\r\n") + "emailAddress:{message:'请填写合法的邮件格式'}");
            }
        } else if (field.getType() == Date.class) {
            this.setTagType("datepicker");
            this.setType("Date");
            this.setValidateType("notEmpty:{message:'" + remark + "不能为空'},\r\ndate:{format:$(this).data('format'),message:'请输入有效" + remark + "'}");
        } else if (field.getType() == Integer.class) {
            this.setTagType("text");
            this.setType("Integer");
            this.setValidateType("notEmpty:{message:'" + remark + "不能为空'},\r\ninteger:{message:'请填写整数'}");
        } else if (field.getType() == Double.class) {
            this.setTagType("number");
            this.setType("Double");
            this.setValidateType("notEmpty:{message:'" + remark + "不能为空'},\r\nnumeric:{message:'" + remark + "是数字类型'}");
        } else if (field.getType() == Float.class) {
            this.setTagType("number");
            this.setType("Float");
            this.setValidateType("notEmpty:{message:'" + remark + "不能为空'},\r\nnumeric:{message:'" + remark + "是数字类型'}");
        } else if (field.getType() == BigDecimal.class) {
            this.setTagType("number");
            this.setType("BigDecimal");
            this.setValidateType("notEmpty:{message:'" + remark + "不能为空'},\r\nnumeric:{message:'" + remark + "是数字类型'}");
        } else if (field.getType() == Boolean.class) {
            this.setTagType("icheck-radio");
            this.setType("Boolean");
            this.setValidateType("notEmpty:{message:'" + remark + "不能为空'}");
        } else if (field.getType().getName().startsWith("com.cnpc")) {
            this.setColumnName(field.getName() + ".id");
            if (field.getType().getName().contains("Dict")) {
                this.setTagType("dictSelector");
                if (StrUtil.isEmpty(this.getDictCode()))
                    this.setDictCode(PingYinUtil.getFirstSpell(field.getName()).toUpperCase());
            } else if (field.getType().getName().contains("Org"))
                this.setTagType("orgSelector");
            else
                this.setTagType("hidden");
            this.setType(field.getType().getSimpleName());
            this.setValidateType("notEmpty:{message:'" + remark + "不能为空'}");
        }
    }

    public Integer getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(Integer rowIndex) {
        this.rowIndex = rowIndex;
    }

    public String getIsCondition() {
        return isCondition;
    }

    public void setIsCondition(String isCondition) {
        this.isCondition = isCondition;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getValidateType() {
        return validateType;
    }

    public void setValidateType(String validateType) {
        this.validateType = validateType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }


}
