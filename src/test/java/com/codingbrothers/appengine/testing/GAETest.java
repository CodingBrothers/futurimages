package com.codingbrothers.appengine.testing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GAETest {

	Class<? extends GAETestConfigurator>[] configurators() default {};
}
