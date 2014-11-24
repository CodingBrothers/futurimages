package com.codingbrothers.futurimages.apiv1;

import com.codingbrothers.futurimages.apiv1.SortByDir.SortByDirTransformer;
import com.codingbrothers.futurimages.apiv1.util.EnumLowerCaseStringTransformer;
import com.google.api.server.spi.config.ApiTransformer;

@ApiTransformer(SortByDirTransformer.class)
public enum SortByDir {
	ASC, DESC;

	public static class SortByDirTransformer extends EnumLowerCaseStringTransformer<SortByDir> {

		public SortByDirTransformer() {
			super(SortByDir.class);
		}

	}
}
