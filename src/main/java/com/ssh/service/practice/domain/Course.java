package com.ssh.service.practice.domain;

import com.ssh.service.practice.validation.Key;
import com.ssh.service.practice.validation.New;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "b9_course")
@Data
@DynamicUpdate
@DynamicInsert
public class Course {

	@Id
	@GeneratedValue
	private Integer id;

	@NotBlank(groups = {New.class},message = "选修课名称不能为空")
	private String name;

	@Key(groups = {New.class},message = "选修课老师不能为空")
	private Integer teacherId;

	private String address;

	@Key(groups = {New.class},message = "学分不能为空")
	private Integer credit;

	@Key(groups = {New.class},message = "星期不能为空")
	private Integer week;

	@Key(groups = {New.class},message = "开始时间不能为空")
	private Integer startTime;

	@Key(groups = {New.class},message = "结束时间不能为空")
	private Integer endTime;

	@Min(value = 0, groups = {New.class}, message = "amount不能为负")
	//补考金额
	private BigDecimal amount;

	private LocalDateTime createTime;

	private LocalDateTime LastTime;
}
