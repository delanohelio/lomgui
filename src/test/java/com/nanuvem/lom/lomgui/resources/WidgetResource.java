package com.nanuvem.lom.lomgui.resources;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nanuvem.restest.TypedResource;

public class WidgetResource extends TypedResource<Widget> {

	private static final String ROOTURL = "http://localhost:8080/lomgui/api/renderer";
	private Gson gson;

	public static WidgetResource getWidgetResource(String resourceName) {
		return new WidgetResource(resourceName);
	}
	
	public WidgetResource(String resourceName) {
		super(ROOTURL + "/" + resourceName);
		gson = new Gson();
	}

	@Override
	protected String toJson(Widget widget) {
		return gson.toJson(widget);
	}

	@Override
	protected List<Widget> toList(String json) {
		Type collectionType = new TypeToken<List<Widget>>(){}.getType();
		return gson.fromJson(json, collectionType);
	}
	
	@Override
	protected Widget toObject(String json) {
		return gson.fromJson(json, Widget.class);
	}

}
