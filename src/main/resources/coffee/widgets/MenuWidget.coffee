class MenuWidget extends GUIElement

	accept: (view, context) ->
		context.entities.forEach (entity) =>
			@drawLine(view, entity)

	drawLine: (view, entity) ->
		tr = $("<tr><td>#{entity.name}</td></tr>")
		tr.attr "id", "entity_" + entity.fullName
		view.append tr
		tr.click => 
			LOM.loadScriptInNewView 'api/widget/entity/'+ entity.fullName,
			entityFullName: entity.fullName

return new MenuWidget