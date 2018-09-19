package com.ssh.service.practice.domain;

import com.ssh.service.practice.common.commonenums.CanWrite;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface WriteControl {
	CanWrite[] allow();
}
