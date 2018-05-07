package com.ssh.service.practice.domain;

import com.ssh.service.practice.enums.Sex;
import com.ssh.service.practice.enums.StudentStatus;
import com.ssh.service.practice.validation.Key;
import com.ssh.service.practice.validation.New;
import com.ssh.service.practice.validation.TransParam;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "b9_student")
@DynamicInsert
@DynamicUpdate
@Data
public class Student {

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

	private LocalDateTime createTime;

	private LocalDateTime LastTime;

}
