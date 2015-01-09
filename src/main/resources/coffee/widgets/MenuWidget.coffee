class MenuWidget extends GUIElement

	accept: (view, context) ->
		context.entities.forEach (entity) =>
			@drawLine(view, entity)

	drawLine: (view, entity) ->
		tr = $("<tr><td>#{entity.name}</td></tr>")
		tr.attr "id", "entity_" + entity.fullName
		view.append tr
		tr.click => 
			Controller.entityEvent View.emptyPage(), entity, 'GETALL'

return new MenuWidget