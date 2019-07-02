package com.fo0.advancedtokenfield.data.enums;

import java.awt.Color;

import lombok.Getter;

public enum ETokenStyle {
	Red(new Color(255, 255, 255), new Color(255, 66, 56)),
	Pink(new Color(255, 255, 255), new Color(233, 30, 99)),
	Purple(new Color(255, 255, 255), new Color(156, 39, 176)),
	DeepPurple(new Color(255, 255, 255), new Color(103, 58, 183)),
	Indigo(new Color(255, 255, 255), new Color(63, 81, 181)),
	Blue(new Color(255, 255, 255), new Color(22, 118, 243)),
	LightBlue(new Color(44, 38, 22), new Color(3, 169, 244)),
	Cyan(new Color(44, 38, 22), new Color(0, 188, 212)),
	Teal(new Color(255, 255, 255), new Color(0, 150, 136)),
	Green(new Color(255, 255, 255), new Color(21, 193, 93)),
	LightGreen(new Color(255, 255, 255), new Color(139, 195, 74)),
	Lime(new Color(44, 38, 22), new Color(205, 220, 57)),
	Yellow(new Color(44, 38, 22), new Color(255, 235, 59)),
	Amber(new Color(44, 38, 22), new Color(255, 193, 7)),
	Orange(new Color(255, 255, 255), new Color(255, 152, 0)),
	DeepOrange(new Color(255, 255, 255), new Color(255, 87, 34)),
	Brown(new Color(255, 255, 255), new Color(121, 85, 72)),
	Gray(new Color(255, 255, 255), new Color(158, 158, 158)),
	BlueGray(new Color(255, 255, 255), new Color(96, 125, 139)),
	Black(new Color(255, 255, 255), new Color(44, 38, 22)),
	White(new Color(44, 38, 22), new Color(255, 255, 255));
	
	@Getter
	private Color fontColor;
	@Getter
	private Color backgroundColor;
	
	private ETokenStyle(Color fontColor, Color backgroundColor) {
		this.fontColor = fontColor;
		this.backgroundColor = backgroundColor;
	}
	
	public String getStyle() {
		return getFontStyle() + getBackgroundStyle();
	}
	
	public String getFontStyle() {
		return "color: rgba("
				+ fontColor.getRed() + ", "
				+ fontColor.getGreen() + ", "
				+ fontColor.getBlue() + ", "
				+ fontColor.getAlpha() + ");";
	}
	
	public String getBackgroundStyle() {
		return "background-color: rgba("
				+ backgroundColor.getRed() + ", "
				+ backgroundColor.getGreen() + ", "
				+ backgroundColor.getBlue() + ", "
				+ backgroundColor.getAlpha() + ");";
	}
}