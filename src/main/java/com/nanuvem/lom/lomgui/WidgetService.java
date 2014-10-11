package com.nanuvem.lom.lomgui;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.google.gson.Gson;
import com.nanuvem.lom.lomgui.resources.Widget;

@Path("/widget")
public class WidgetService {

	@Context
	private HttpServletRequest servletRequest;

	@GET
	@Produces("text/plain; charset=utf-8")
	@Path("/root")
	public Response getRootWidget() {

		String widgetFilename = WidgetStoreFacade.getInstance()
				.getWidgetFromTarget("root").getFilename();
		String result = FileSystemUtil.getWidgetScript(servletRequest,
				widgetFilename);
		return Response.ok(result).build();
	}

	@POST
	@Path("/root")
	public Response setRootWidget(String json, @Context UriInfo uriInfo) {
		try {
			Gson gson = new Gson();
			Widget widget = gson.fromJson(json, Widget.class);
			widget = WidgetStoreFacade.getInstance().getWidgetFromName(
					widget.getName());
			saveWidgetByPath(uriInfo.getPath(), widget);
			return Response.ok().build();
		} catch (Exception e) {
			return Response.notAcceptable(null).build();
		}

	}

	@GET
	@Produces("text/plain; charset=utf-8")
	@Path("/class/{fullName}")
	public Response getClassWidget(@PathParam("fullName") String fullName) {
		Widget widget = WidgetStoreFacade.getInstance().getWidgetFromTarget(
				"class." + fullName);
		if (widget == null) {
			widget = WidgetStoreFacade.getInstance().getWidgetFromTarget(
					"class");
		}
		String result = FileSystemUtil.getWidgetScript(servletRequest,
				widget.getFilename());
		return Response.ok(result).build();
	}

	@POST
	@Path("/class")
	public Response setDefaultClassWidget(String widgetName) {
		return Response.ok().build();
	}

	@GET
	@Produces("text/plain; charset=utf-8")
	@Path("/class/{fullName}/{attributeName}")
	public Response getAttributeWidget(@PathParam("fullName") String fullName,
			@PathParam("attributeName") String attributeName) {
		Widget widget = WidgetStoreFacade.getInstance().getWidgetFromTarget(
				"class." + fullName + "." + attributeName);
		if (widget == null) {
			widget = WidgetStoreFacade.getInstance().getWidgetFromTarget(
					"attribute");
		}
		String result = FileSystemUtil.getWidgetScript(servletRequest,
				widget.getFilename());
		return Response.ok(result).build();
	}

	private void saveWidgetByPath(String path, Widget widget) {
		String[] pathComponents = path.split("/");
		if (pathComponents.length < 2) {
			return;
		}

		else if (pathComponents[1].equals("root")) {
			WidgetStoreFacade.getInstance().setWidgetToTarget("root", widget);
		}
		
		else if (pathComponents[1].equals("class")) {
			//TODO make to specific class (class/nameclass)
			WidgetStoreFacade.getInstance().setWidgetToTarget("class", widget);
		}
	}

}