package com.openparts.base.entity;

import org.springframework.format.annotation.DateTimeFormat;
import com.cnpc.framework.annotation.ForeignShow;
import com.cnpc.framework.annotation.Header;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.openparts.base.entity.OP_BaseEntity;
import java.util.Date;

/*
-- ----------------------------
-- Table structure for `wh_mobile_hints`
-- ----------------------------
DROP TABLE IF EXISTS `wh_mobile_hints`;
CREATE TABLE `wh_mobile_hints` (
  `id` int(11) NOT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `verify_str` varchar(20) DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
*/

@Entity
@Table(name="wh_mobile_hints")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class Mobile_hints extends OP_BaseEntity {

    private static final long serialVersionUID = 5569761987303812150L;

    @Header(name = "Mobile")
    @Column(name = "mobile", length = 20)
    private String mobile;

    @Header(name = "verify_str")
    @Column(name = "verify_str", length = 20)
    private String verify_str;

    @Header(name = "最后更新时间")
    @Column(name = "update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date update_time;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerify_str() {
        return verify_str;
    }

    public void setVerify_str(String verify_str) {
        this.verify_str = verify_str;
    }

    public Date getUpdate_time() {

        return update_time;
    }

    public void setUpdate_time(Date update_time) {

        this.update_time = update_time;
    }
}
