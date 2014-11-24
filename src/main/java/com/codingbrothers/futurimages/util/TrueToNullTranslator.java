package com.codingbrothers.futurimages.util;

import java.util.logging.Logger;

import com.google.appengine.api.datastore.RawValue;
import com.googlecode.objectify.impl.Path;
import com.googlecode.objectify.impl.translate.CreateContext;
import com.googlecode.objectify.impl.translate.LoadContext;
import com.googlecode.objectify.impl.translate.SaveContext;
import com.googlecode.objectify.impl.translate.SkipException;
import com.googlecode.objectify.impl.translate.Translator;
import com.googlecode.objectify.impl.translate.TranslatorFactory;
import com.googlecode.objectify.impl.translate.TypeKey;
import com.googlecode.objectify.repackaged.gentyref.GenericTypeReflector;

public class TrueToNullTranslator implements TranslatorFactory<Boolean, Object> {

	private static final Logger log = Logger.getLogger(TrueToNullTranslator.class.getName());

	@Override
	public Translator<Boolean, Object> create(TypeKey<Boolean> tk, CreateContext ctx, Path path) {
		if (Boolean.class.isAssignableFrom(GenericTypeReflector.erase(tk.getType()))) {
			return createTranslator(tk, ctx, path);
		} else {
			return null;
		}
	}

	protected Translator<Boolean, Object> createTranslator(TypeKey<Boolean> tk, CreateContext ctx, Path path) {
		return new Translator<Boolean, Object>() {

			@Override
			public Boolean load(Object node, LoadContext ctx, Path path) throws SkipException {
				if (node == null) {
					return Boolean.TRUE;
				} else {
					if (node instanceof RawValue) {
						return (Boolean) ((RawValue) node).asType(Boolean.class);
					}
					return (Boolean) node;
				}
			}

			@Override
			public Boolean save(Boolean pojo, boolean index, SaveContext ctx, Path path) throws SkipException {
				return Boolean.TRUE.equals(pojo) ? null : pojo;
			}
		};
	}
}
