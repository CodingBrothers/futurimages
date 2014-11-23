package com.codingbrothers.futurimages.util;

import com.google.appengine.api.images.Image.Format;

public class Utils {

	public static String getImageMediaType(Format imageFormat) {
		if (imageFormat == null) {
			return null;
		}

		switch (imageFormat) {
		case BMP:
			return "image/bmp";
		case GIF:
			return "image/gif";
		case ICO:
			return "image/x-icon";
		case JPEG:
			return "image/jpeg";
		case PNG:
			return "image/png";
		case TIFF:
			return "image/tiff";
		case WEBP:
			return "image/webp";
		default:
			return null;
		}
	}

	public static Format getImageFormat(String imageMediaType) {
		if (imageMediaType == null) {
			return null;
		}
		
		switch (imageMediaType) {
		case "image/bmp":
		case "image/x-bmp":
			return Format.BMP;
		case "image/gif":
			return Format.GIF;
		case "image/x-icon":
			return Format.ICO;
		case "image/jpeg":
			return Format.JPEG;
		case "image/png":
			return Format.PNG;
		case "image/tiff":
			return Format.TIFF;
		case "image/webp":
			return Format.WEBP;
		default:
			return null;
		}
	}
}
