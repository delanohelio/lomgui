package com.nanuvem.lom.lomgui;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/widget")
public class WidgetService {

	@Context
	private HttpServletRequest servletRequest;

	@GET
	@Produces("text/plain; charset=utf-8")
	@Path("/root")
	public Response getRootWidget() {
		String widgetName = WidgetStoreFacade.getInstance().getWidget("root");
		String result = FileSystemUtil.getWidgetScript(servletRequest,
				widgetName);
		return Response.ok(result).build();
	}

	@POST
	@Path("/root")
	public Response setRootWidget(String widgetName) {
		WidgetStoreFacade.getInstance().setWidget("root", widgetName);
		return Response.ok().build();
	}

}