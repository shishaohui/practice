package com.ssh.service.practice.domain;

import com.ssh.service.practice.enums.ElectiveCourseStatus;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "b9_elective_course")
@DynamicUpdate
@DynamicInsert
@Data
public class ElectiveCourse {

	@Id
	@GeneratedValue
	private Integer id;

	private Integer studentId;

	private Integer courseId;

	private Integer score;

	private ElectiveCourseStatus status;

	private LocalDateTime createTime;

	private LocalDateTime LastTime;
}
