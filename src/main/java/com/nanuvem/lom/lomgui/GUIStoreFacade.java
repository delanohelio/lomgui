package com.nanuvem.lom.lomgui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nanuvem.lom.lomgui.resources.Renderer;
import com.nanuvem.lom.lomgui.resources.Widget;

public class GUIStoreFacade {

	private static GUIStoreFacade singleton;

	public static GUIStoreFacade getInstance(){
		if(GUIStoreFacade.singleton == null){
			GUIStoreFacade.singleton = new GUIStoreFacade();
		}
		return GUIStoreFacade.singleton;
	}

	private Set<Renderer> renderers;
	private Map<String, Renderer> renderersMapping;
	
	private GUIStoreFacade() {
		renderers = new HashSet<Renderer>();
		renderersMapping = new HashMap<String, Renderer>();
		mock();
	}
	
	private void mock() {
		Renderer defaultRootRenderer = new Renderer("TableRootRenderer", "TableRootRenderer");
		Widget menuWidget = new Widget("Menu", "MenuWidget");
		defaultRootRenderer.addHook("menu", menuWidget);
		Widget taskBarWidget = new Widget("TaskBar", "TaskBarWidget");
		defaultRootRenderer.addHook("taskbar", taskBarWidget);
		addRenderer(defaultRootRenderer);
		
		Renderer ulRootRenderer = new Renderer("UlRootRenderer", "UlRootRenderer");
		addRenderer(ulRootRenderer);
		
		Renderer defaultEntityListingRenderer = new Renderer("TableEntityListingRenderer", "TableEntityListingRenderer");
		addRenderer(defaultEntityListingRenderer);
		
		Renderer simpleEntityListingRenderer = new Renderer("SimpleEntityListingRenderer", "SimpleEntityListingRenderer");
		addRenderer(simpleEntityListingRenderer);
		
		Renderer defaultAttributeRenderer = new Renderer("DefaultAttributeRenderer", "DefaultAttributeRenderer");
		addRenderer(defaultAttributeRenderer);
		
		Renderer passwordAttributeRenderer = new Renderer("PasswordAttributeRenderer", "PasswordAttributeRenderer");
		addRenderer(passwordAttributeRenderer);
		
		setRendererToTarget("root", defaultRootRenderer);
		setRendererToTarget("entity", defaultEntityListingRenderer);
		setRendererToTarget("attribute", defaultAttributeRenderer);
	}

	private void addRenderer(Renderer renderer){
		renderers.add(renderer);
	}
	
	public void removeRenderer(Renderer renderer){
		renderers.remove(renderer);
	}
	
	public List<Renderer> getAllWidgets(){
		return new ArrayList<Renderer>(renderers);
	}
	
	public Renderer getRendererFromName(String name){
		for(Renderer renderer : renderers){
			if(renderer.getName().equals(name))
				return renderer;
		}
		return null;
	}
	
	public Renderer getRendererFromTarget(String target) {
		return renderersMapping.get(target);
	}
	
	public void setRendererToTarget(String target, Renderer renderer) {
		renderersMapping.put(target, renderer);
	}

}