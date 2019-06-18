package com.ssh.service.practice.domain;

import com.ssh.service.practice.enums.Sex;
import com.ssh.service.practice.enums.StudentStatus;
import com.ssh.service.practice.validation.Key;
import com.ssh.service.practice.validation.New;
import com.ssh.service.practice.validation.TransParam;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "b9_student")
@DynamicInsert
@DynamicUpdate
@Data
public class Student {

	public Student() {
	}

	public Student(String name, Integer age, Integer tuition, StudentStatus status) {
		this.name = name;
		this.age = age;
		this.tuition = tuition;
		this.status = status;
	}

	@Id
	@GeneratedValue
	private Integer id;

	@NotBlank(groups = {TransParam.class, New.class},message = "姓名不能为空!")
	private String name;

	@Min(value = 0,groups = {TransParam.class, New.class},message = "年龄不能小于0")
	private Integer age;

	@Key(groups = {TransParam.class, New.class},message = "tuition 不能小于1")
	private Integer tuition;

	@NotNull(groups = {New.class},message = "性别不能为空")
	@Convert(converter = StudentStatus.AsValue.class)
	private StudentStatus status;

	@NotNull(groups = {TransParam.class, New.class},message = "性别不能为空")
	@Convert(converter = Sex.AsValue.class)
	private Sex sex;

	private LocalDate startDate;

	private BigDecimal amount;

	private Boolean enabled;

	private Integer enabledId;

	private LocalDateTime createTime;

	private LocalDateTime LastTime;

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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getTuition() {
		return tuition;
	}

	public void setTuition(Integer tuition) {
		this.tuition = tuition;
	}

	public StudentStatus getStatus() {
		return status;
	}

	public void setStatus(StudentStatus status) {
		this.status = status;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
		return LastTime;
	}

	public void setLastTime(LocalDateTime lastTime) {
		LastTime = lastTime;
	}
}
