package com.gson.bean.menu;

public class ComplexButton extends Button {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Button[] sub_button;

	public Button[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}
	
}