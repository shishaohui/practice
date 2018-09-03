package com.ssh.service.practice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssh.service.practice.enums.TemplateType;
import java.time.LocalDateTime;
import javax.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "f2_template")
@DynamicUpdate
@DynamicInsert
public class Template {

    @Id
    @GeneratedValue

    private Integer id;

    private Integer tenantId;

    private String name ;

    private String code;

    private String description ;

    @Convert(converter = TemplateType.AsValue.class)
    private TemplateType type;

    @JsonIgnore
    private String bodyId;

    @Version
    private Integer version;

    @JsonIgnore
    private Boolean enabled;

    @JsonIgnore
    private Integer enabledId;

    private LocalDateTime createTime;

    private LocalDateTime lastTime;

    public Template() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TemplateType getType() {
        return type;
    }

    public void setType(TemplateType type) {
        this.type = type;
    }

    public String getBodyId() {
        return bodyId;
    }

    public void setBodyId(String bodyId) {
        this.bodyId = bodyId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getEnabledId() {
        return enabledId;
    }

    public void setEnabledId(Integer enabledId) {
        this.enabledId = enabledId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getLastTime() {
        return lastTime;
    }

    public void setLastTime(LocalDateTime lastTime) {
        this.lastTime = lastTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }
}
