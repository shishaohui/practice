package com.ssh.service.practice.domain;

import com.ssh.service.practice.enums.Sex;
import com.ssh.service.practice.enums.TeacherStatus;
import com.ssh.service.practice.validation.Key;
import com.ssh.service.practice.validation.New;
import com.ssh.service.practice.validation.TransParam;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "b9_teacher")
@DynamicInsert
@DynamicUpdate
@Data
public class Teacher {

	@Id
	@GeneratedValue
	private Integer id;

	@NotBlank(groups = {TransParam.class, New.class},message = "姓名 不能为空")
	private String name;

	@Convert(converter = Sex.AsValue.class)
	private Sex sex;

	@NotBlank(groups = {TransParam.class, New.class},message = "身份证号 不能为空")
	private String idNo;

	@Key(groups = {TransParam.class, New.class},message = "电话号 不能为空")
	private Integer telephone;

	@Convert(converter = TeacherStatus.AsValue.class)
	private TeacherStatus status;

	private LocalDateTime createTime;

	private LocalDateTime LastTime;
}
