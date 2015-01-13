package com.nanuvem.lom.lomgui.resources;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nanuvem.restest.TypedResource;

public class RendererResource extends TypedResource<Renderer> {

	private static final String ROOTURL = "http://localhost:8080/lomgui/api/renderer";
	private Gson gson;

	public static RendererResource getRendererResource(String resourceName) {
		return new RendererResource(resourceName);
	}
	
	public RendererResource(String resourceName) {
		super(ROOTURL + "/" + resourceName);
		gson = new Gson();
	}

	@Override
	protected String toJson(Renderer widget) {
		return gson.toJson(widget);
	}

	@Override
	protected List<Renderer> toList(String json) {
		Type collectionType = new TypeToken<List<Widget>>(){}.getType();
		return gson.fromJson(json, collectionType);
	}
	
	@Override
	protected Renderer toObject(String json) {
		return gson.fromJson(json, Renderer.class);
	}

}
