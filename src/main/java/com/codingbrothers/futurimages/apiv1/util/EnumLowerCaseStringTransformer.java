package com.codingbrothers.futurimages.apiv1.util;

import com.google.api.server.spi.config.Transformer;

public class EnumLowerCaseStringTransformer<T extends Enum<T>> implements Transformer<Enum<T>, String> {

	private final Class<T> enumType;
	
	public EnumLowerCaseStringTransformer(Class<T> enumType) {
		this.enumType = enumType;
	}

	@Override
	public Enum<T> transformFrom(String in) {
		try {
			return Enum.valueOf(enumType, in.toUpperCase());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String transformTo(Enum<T> in) {
		return in.name().toLowerCase();
	}

}
