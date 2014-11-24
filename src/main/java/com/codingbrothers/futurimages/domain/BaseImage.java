package com.codingbrothers.futurimages.domain;

import java.util.Date;

import com.codingbrothers.futurimages.util.TrueToNullTranslator;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Translate;
import com.googlecode.objectify.annotation.Unindex;
import com.googlecode.objectify.condition.IfFalse;
import com.googlecode.objectify.condition.IfNull;

@Unindex
public abstract class BaseImage<P> {

	@Parent
	Ref<P> parent;

	@Id
	Long id;

	@Index
	@IgnoreSave({IfNull.class, IfFalse.class})
	@Translate(early = true, value = TrueToNullTranslator.class)
	Boolean p = Boolean.FALSE;

	@Index
	Date c;

	String blobKey;

	BaseImage() {}

	void setParent(Ref<P> parent) {
		this.parent = parent;
	}

	public String getBlobKey() {
		return blobKey;
	}

	// hashCode, equals, toString
}
