package com.ssh.service.practice.domain;

import com.ssh.service.practice.enums.PayStatus;
import com.ssh.service.practice.enums.UpExaminationStatus;
import com.ssh.service.practice.validation.Key;
import com.ssh.service.practice.validation.New;
import com.ssh.service.practice.validation.TransParam;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "b9_up_examination")
@DynamicInsert
@DynamicUpdate
@Data
public class UpExamination {

	@Id
	@GeneratedValue
	private Integer id;

	@Key(groups = {New.class},message = "studentId不能为空")
	private Integer studentId;

	@Key(groups = {TransParam.class,New.class},message = "studentId不能为空")
	private Integer electiveCourseId;

	private Integer score;

	@Convert(converter = UpExaminationStatus.AsValue.class)
	private UpExaminationStatus status;

	@Convert(converter = PayStatus.AsValue.class)
	private PayStatus payStatus;

	private LocalDateTime createTime;

	private LocalDateTime LastTime;
}
