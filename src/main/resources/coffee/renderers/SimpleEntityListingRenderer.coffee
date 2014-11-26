class TableEntityListingRenderer

	init: (view, conf) ->
		LOM.getJSON "api/data/entity/#{conf.entityFullName}/instances", (instances) =>
			@buildTableBody(instances, view)

	buildTableBody: (instances, view) ->
		if(instances.length > 0)
			instances.forEach (instance) =>
				@buildTableLine(instance, view)
		else
			view.append "There are not instances"

	buildTableLine: (instance, view) ->
		par = $("<p>")
		par.attr "id", "instance_" + instance.id
		view.append par
		separator = ""
		instance.values.forEach (attributeValue) =>
			par.append separator 
			par.append attributeValue.value
			separator = ","
		par.click => 
			LOM.loadScript 'api/widget/entity/' + instance.entity.fullName + '/edit',
				entityFullName: instance.entity.fullName
				id: instance.id

return new TableEntityListingRenderer