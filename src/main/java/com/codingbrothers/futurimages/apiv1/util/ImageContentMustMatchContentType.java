package com.codingbrothers.futurimages.apiv1.util;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = ImageContentMustMatchContentTypeValidator.class)
@Documented
public @interface ImageContentMustMatchContentType {

	String message() default "{ImageContentMustMatchContentType.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
