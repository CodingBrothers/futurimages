package com.codingbrothers.appengine.testing;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.appengine.api.capabilities.CapabilityStatus;

@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface GAECapability {

	Capability capability();

	CapabilityStatus status();
}
