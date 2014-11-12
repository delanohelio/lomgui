class SimpleClassListingWidget

	init: (view, conf) ->
		LOM.getJSON "api/data/class/#{conf.classFullName}/instances", (instances) =>
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
			LOM.loadScript 'api/widget/class/' + instance.clazz.fullName + '/edit',
				classFullName: instance.clazz.fullName
				id: instance.id

return new SimpleClassListingWidget