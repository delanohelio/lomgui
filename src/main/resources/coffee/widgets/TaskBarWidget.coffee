class TaskBarWidget extends GUIElement

	accept: (view, context) ->
		th = $("<tr><th>Entities</th></tr>")
		th.attr "id", "entities"
		view.append th

return new TaskBarWidget