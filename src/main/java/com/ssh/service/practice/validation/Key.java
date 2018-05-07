package com.ssh.service.practice.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NotNull
@Min(1)
@Target({METHOD,FIELD,ANNOTATION_TYPE,PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = { })
@Documented
public @interface Key {

	String message() default "值应非空，数值最小值为1";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
