class TableRootRenderer extends GUIElement

	accept: (view, context) ->
		table = $("<table>")
		view.append table
		GUIManager.getWidget this, "taskbar", (widget) =>
			widget.accept table, context
			GUIManager.getWidget this, "menu", (widget) =>
				widget.accept table, context

return new TableRootRenderer