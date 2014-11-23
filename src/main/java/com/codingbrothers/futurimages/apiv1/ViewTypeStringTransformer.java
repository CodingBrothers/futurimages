package com.codingbrothers.futurimages.apiv1;

import com.google.api.server.spi.config.Transformer;


public class ViewTypeStringTransformer implements Transformer<ViewType, String> {

	@Override
	public ViewType transformFrom(String in) {
		ViewType viewType = new ViewType();
		if(in != null && in.charAt(0) == 's') {
			in = in.substring(1);
			if(in.endsWith("-c")) {
				in = in.substring(0, in.length() - 2);
				viewType.setCrop(true);
			}
			try {
				viewType.setImageSize(Integer.parseInt(in));
			} catch (NumberFormatException e) {
			}
		}
		return viewType;
	}

	@Override
	public String transformTo(ViewType in) {
		return "s" + in.getImageSize() + (in.getCrop() ? "-c" : "");
	}

}
