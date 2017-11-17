package com.openparts.base.entity;

import com.cnpc.framework.annotation.ForeignShow;
import com.cnpc.framework.annotation.Header;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.cnpc.framework.base.entity.BaseEntity;

/*

CREATE TABLE IF NOT EXISTS `nodes` (
    `iid` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY UNIQUE,
    `id` VARCHAR(63) NOT NULL UNIQUE,
    `key` VARCHAR(63) NOT NULL,
    `app_iid` INTEGER UNSIGNED NOT NULL REFERENCES `apps`(`iid`),
    `locked` BIT NOT NULL DEFAULT 0,
    `name` VARCHAR(63) NOT NULL,
    `description` VARCHAR(127),
    `device_ver` VARCHAR(31),
    `device_id` VARCHAR(127),
    `real_device_id` VARCHAR(127),
    `device_type` VARCHAR(63),
    `manufacturer` VARCHAR(63),
    `model` VARCHAR(63),
    `os` VARCHAR(63),
    `os_version` VARCHAR(63),
    `register_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `last_modified` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX `idx_node_id` (`id`),
    INDEX `idx_node_device_id` (`device_id`)
) AUTO_INCREMENT=2048 ENGINE=InnoDB DEFAULT CHARSET=utf8;
*/

@Entity
@Table(name="whisper_nodes")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class Whisper_nodes extends BaseEntity {

    private static final long serialVersionUID = 5569761987303812150L;

    @Header(name = "name")
    @Column(name = "name", length = 63)
    private String name;

    @Header(name = "description")
    @Column(name = "description", length = 127)
    private String description;

    @Header(name = "device_ver")
    @Column(name = "device_ver", length = 31)
    private String device_ver;

    @Header(name = "device_id")
    @Column(name = "device_id", length = 127)
    private String device_id;

    @Header(name = "real_device_id")
    @Column(name = "real_device_id", length = 127)
    private String real_device_id;

    @Header(name = "device_type")
    @Column(name = "device_type", length = 63)
    private String device_type;

    @Header(name = "manufacturer")
    @Column(name = "manufacturer", length = 63)
    private String manufacturer;

    @Header(name = "model")
    @Column(name = "model", length = 63)
    private String model;

    @Header(name = "os")
    @Column(name = "os", length = 63)
    private String os;

    @Header(name = "os_version")
    @Column(name = "os_version", length = 63)
    private String os_version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDevice_ver() {
        return device_ver;
    }

    public void setDevice_ver(String device_ver) {
        this.device_ver = device_ver;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getReal_device_id() {
        return real_device_id;
    }

    public void setReal_device_id(String real_device_id) {
        this.real_device_id = real_device_id;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }
}
