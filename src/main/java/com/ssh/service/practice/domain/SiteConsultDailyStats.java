package com.ssh.service.practice.domain;

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
    private Integer id;

    private Integer tenantId;

    private Integer staffId;

    private LocalDate statsDate;

    private BigDecimal performance;

    private Integer change;

    private Integer rank;

    private BigDecimal ratio;  // 今日成交转化率？？

    private Integer ratioChange;

    private Integer ratioRank;

    private Integer clinchNum;

    private Integer dailyArrivalNum;

    private Integer dailyCallbackNum;

    private Integer dailyTotalCallbackNum;

    private Integer year;

    private Integer month;

    private Integer day;

    private Integer dayOfYear;

    private Integer dayOfWeek;

    private Integer weekOfYear;

    private Integer quarter;

    @JsonIgnore
    private Boolean enabled;

    @JsonIgnore
    private Integer enabledId;

    private LocalDateTime createTime;

    private LocalDateTime lastTime;

    @Version
    private Integer version;
}
