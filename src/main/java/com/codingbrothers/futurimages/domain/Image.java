package com.codingbrothers.futurimages.domain;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Unindex;

@Entity
@Unindex
//@Cache(expirationSeconds=1)
public class Image {

	@Id private String id;
}
