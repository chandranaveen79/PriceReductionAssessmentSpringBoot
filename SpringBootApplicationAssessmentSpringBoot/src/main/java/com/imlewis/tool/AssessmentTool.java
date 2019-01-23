package com.imlewis.tool;

import java.awt.Color;

import org.apache.commons.lang3.StringUtils;
import org.beryx.awt.color.ColorFactory;

public class AssessmentTool {

	public static final String COLOR_APPENDER = "#";
	public static final String HEX_COLOR_APPENDER = "0";
	public static final String NOT_A_VALID_COLOR = "N/A";

	public static float getFloatValue(String value) {
		return StringUtils.isNotEmpty(value) ? Float.parseFloat(value) : 0.0f;
	}

	public static String getHexColor(String colorString) {
		StringBuffer colorResponse = new StringBuffer(COLOR_APPENDER);
		try {
			Color color = ColorFactory.valueOf(colorString.toLowerCase());
			colorResponse.append(getRGBCoodinates(color, ColorType.RED));
			colorResponse.append(getRGBCoodinates(color, ColorType.GREEN));
			colorResponse.append(getRGBCoodinates(color, ColorType.BLUE));
		} catch (IllegalArgumentException illegalArgumentException) {
			colorResponse = new StringBuffer(NOT_A_VALID_COLOR);
		}
		return colorResponse.toString();
	}

	public static String getRGBCoodinates(Color colorRGB, ColorType type) {
		int intColorValue = 0;

		switch (type) {
		case RED: {
			intColorValue = colorRGB.getRed();
			break;
		}
		case GREEN: {
			intColorValue = colorRGB.getGreen();
			break;
		}
		case BLUE: {
			intColorValue = colorRGB.getBlue();
			break;
		}
		}
		return Integer.toHexString(intColorValue).length() < 2 ? HEX_COLOR_APPENDER + Integer.toHexString(intColorValue)
				: Integer.toHexString(intColorValue);
	}
}

enum ColorType {
	RED, GREEN, BLUE;
}