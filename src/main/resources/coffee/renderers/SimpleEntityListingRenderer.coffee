class SimpleEntityListingRenderer extends GUIElement

	accept: (view, context) ->
		@buildTableBody(context.entity, view)

	buildTableBody: (entity, view) ->
		if(entity.instances.length > 0)
			entity.instances.forEach (instance) =>
				@buildTableLine(instance, entity, view)
		else
			view.append "There are not instances"

	buildTableLine: (instance, entity, view) ->
		par = $("<p>")
		par.attr "id", "instance_" + instance.id
		view.append par
		separator = ""
		instance.values.forEach (attributeValue) =>
			par.append separator 
			par.append attributeValue.value
			separator = ","
		par.click => 
			LOM.loadScript 'api/widget/entity/' + entity.fullName + '/edit',
				entityFullName: entity.fullName
				id: instance.id

return new SimpleEntityListingRenderer