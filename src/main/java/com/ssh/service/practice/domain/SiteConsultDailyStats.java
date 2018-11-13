package com.digibig.service.datawarehouse.domain.stats;

import com.digibig.service.datawarehouse.enums.Change;
import com.digibig.spring.domain.CanWrite;
import com.digibig.spring.domain.WriteControl;
import com.digibig.spring.register.Join;
import com.digibig.spring.validation.IdOnly;
import com.digibig.spring.validation.Key;
import com.digibig.spring.validation.Update;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "e9_site_consult_daily_stats")
@DynamicInsert
@DynamicUpdate
@Data
public class SiteConsultDailyStats {

    @Id
    @GeneratedValue
    @Key(groups = {Update.class, IdOnly.class})
    private Integer id;

    @Join(targetClassName = "com.digibig.service.tenant.domain.Tenant", name = "_tenant")
    private Integer tenantId;

    @Join(targetClassName = "com.digibig.service.hr.domain.Staff", name = "_staff")
    private Integer staffId;

    private LocalDate statsDate;

    @ApiModelProperty(value = "每日业绩", example = "111100.50")
    private BigDecimal performance;

    @ApiModelProperty(value = "今日业绩上升下降，1：上升 2：持平 3：下降", example = "1")
    @Convert(converter = Change.AsValue.class)
    private Change change;

    @ApiModelProperty(value = "排名", example = "5")
    private Integer rank;

    @ApiModelProperty(value = "成交转换率", example = "80%")
    private BigDecimal ratio;  // 今日成交转化率？？

    @ApiModelProperty(value = "今日成交转化率变化上升|下降 1：上升 2：持平 3：下降", example = "2")
    @Convert(converter = Change.AsValue.class)
    private Change ratioChange;

    @ApiModelProperty(value = "今日成交转化率排名", example = "9")
    private Integer ratioRank;

    @ApiModelProperty(value = "今日成交人数", example = "7")
    private Integer clinchNum;

    @ApiModelProperty(value = "每日到店人数", example = "12")
    private Integer dailyArrivalNum;

    @ApiModelProperty(value = "每日已回访人数", example = "12")
    private Integer dailyCallbackNum;

    @ApiModelProperty(value = "每日安排的总回访人数", example = "20")
    private Integer dailyTotalCallbackNum;

    @ApiModelProperty(value = "年", example = "2018")
    private Integer year;

    @ApiModelProperty(value = "月", example = "10")
    private Integer month;

    @ApiModelProperty(value = "日", example = "20")
    private Integer day;

    private Integer dayOfYear;

    private Integer dayOfWeek;

    private Integer weekOfYear;

    private Integer quarter;

    @WriteControl(allow = {CanWrite.NONE})
    @JsonIgnore
    private Boolean enabled;

    @WriteControl(allow = {CanWrite.NONE})
    @JsonIgnore
    private Integer enabledId;

    @WriteControl(allow = {CanWrite.NONE})
    private LocalDateTime createTime;

    @WriteControl(allow = {CanWrite.NONE})
    private LocalDateTime lastTime;

    @Version
    private Integer version;
}
