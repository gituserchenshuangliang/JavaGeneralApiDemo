package com.general.api.demo;

public class Clothes {
	private String color;
	private char Size;
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public char getSize() {
		return Size;
	}
	public void setSize(char size) {
		Size = size;
	}
	public Clothes(String color, char size) {
		super();
		this.color = color;
		Size = size;
	}
}
