package com.nanuvem.lom.lomgui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nanuvem.lom.lomgui.resources.Widget;

public class WidgetStoreFacade {

	private static WidgetStoreFacade singleton;

	public static WidgetStoreFacade getInstance(){
		if(WidgetStoreFacade.singleton == null){
			WidgetStoreFacade.singleton = new WidgetStoreFacade();
		}
		return WidgetStoreFacade.singleton;
	}

	private Set<Widget> widgets;
	private Map<String, Widget> widgetsMapping;
	
	private WidgetStoreFacade() {
		widgets = new HashSet<Widget>();
		widgetsMapping = new HashMap<String, Widget>();
		mock();
	}
	
	private void mock() {
		Widget defaultRootWidget = new Widget("TableRootWidget", "TableRootWidget");
		addWidget(defaultRootWidget);
		Widget widgetRootUl = new Widget("UlRootWidget", "UlRootWidget");
		addWidget(widgetRootUl);
		Widget defaultClassWidget = new Widget("TableClassListingWidget", "TableClassListingWidget");
		addWidget(defaultClassWidget);
		
		setWidgetToTarget("root", defaultRootWidget);
		setWidgetToTarget("class", defaultClassWidget);
	}

	private void addWidget(Widget widget){
		widgets.add(widget);
	}
	
	public void removeWidget(Widget widget){
		widgets.remove(widget);
	}
	
	public List<Widget> getAllWidgets(){
		return new ArrayList<Widget>(widgets);
	}
	
	public Widget getWidgetFromName(String name){
		for(Widget widget : widgets){
			if(widget.getName().equals(name))
				return widget;
		}
		return null;
	}
	
	public Widget getWidgetFromTarget(String target) {
		return widgetsMapping.get(target);
	}
	
	public void setWidgetToTarget(String target, Widget widget) {
		widgetsMapping.put(target, widget);
	}

}