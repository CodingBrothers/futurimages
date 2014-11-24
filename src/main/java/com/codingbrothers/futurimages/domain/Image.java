package com.codingbrothers.futurimages.domain;

import java.util.Date;

import javax.inject.Inject;

import com.codingbrothers.futurimages.util.TrueToNullTranslator;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Translate;
import com.googlecode.objectify.annotation.Unindex;
import com.googlecode.objectify.condition.IfFalse;
import com.googlecode.objectify.condition.IfNull;

@Entity(name = "Image")
@Unindex
// @Cache(expirationSeconds = 60)
public class Image {

	@Parent
	private Ref<User> user;

	@Id
	private Long id;

	@Index
	@IgnoreSave({IfNull.class, IfFalse.class})
	@Translate(early = true, value = TrueToNullTranslator.class)
	private Boolean p = Boolean.FALSE;

	@Index
	private Date c;

	private String blobKey;

	@Inject
	private Image() {}

	public String getBlobKey() {
		return blobKey;
	}

	// hashCode, equals, toString

	public static class Builder {

		private Image image;

		public Builder() {
			image = new Image();
		}

		public Image build() {
			return image;
		}

		public Builder fromImage(Image image) {
			this.image.user = image.user;
			this.image.id = image.id;
			this.image.p = image.p;
			this.image.c = image.c;
			this.image.blobKey = image.blobKey;
			return this;
		}

		public Builder setBlobKey(String blobKey) {
			image.blobKey = blobKey;
			return this;
		}
	}
}
