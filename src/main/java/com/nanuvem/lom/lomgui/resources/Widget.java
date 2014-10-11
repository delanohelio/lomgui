package com.nanuvem.lom.lomgui.resources;


/**
 * This class represent a Widget
 * @author delano
 *
 */

public class Widget{

	private String name;
	private String filename;

	public Widget() {
		super();
	}

	public Widget(String name, String filename) {
		super();
		this.name = name;
		this.filename = filename;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Widget)
			return name.equals(((Widget) obj).getName());
		return false;
	}

}
