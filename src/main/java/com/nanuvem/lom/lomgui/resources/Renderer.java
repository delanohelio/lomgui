package com.nanuvem.lom.lomgui.resources;

import java.util.HashMap;
import java.util.Map;

public class Renderer {

	private String name;
	private String filename;
	private Map<String, Widget> hooks;
	
	public Renderer(String name, String filename) {
		this.name = name;
		this.filename = filename;
		this.hooks = new HashMap<String, Widget>();
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
	
	public Widget getHook(String hook) {
		return this.hooks.get(hook);
	}
	
	public void addHook(String hook, Widget widget) {
		this.hooks.put(hook, widget);
	}
	
	public void deleteHook(String hook) {
		this.hooks.remove(hook);
	}
	
}
