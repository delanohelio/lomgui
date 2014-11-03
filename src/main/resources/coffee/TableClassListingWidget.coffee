class TableClassListingWidget

	init: (conf) ->
		LOM.getJSON "api/data/class/#{conf.classFullName}/attributes", (attributes) =>
			@drawTable(attributes, conf.classFullName)

	drawTable: (attributesJson, classFullName) ->
		@page = LOM.emptyPage()
		table = $("<table>")
		@page.append table
		@buildTableHead(attributesJson, table);
		LOM.getJSON "api/data/class/#{classFullName}/instances", (instances) =>
			@buildTableBody(instances, attributesJson, table)

	buildTableHead: (attributesJson, table) ->
		thead = $("<thead>");
		table.append thead
		trHead = $("<tr>");
		trHead.attr "id", "attributes"
		thead.append trHead
		attributesJson.forEach (attribute) ->
			thHead = $("<th>#{attribute.name}</th>")
			thHead.attr "id", "attribute_" + attribute.id
			trHead.append thHead

	buildTableBody: (instancesJson, attributes, table) ->
		if(instancesJson.length > 0)
			tbody = $("<tbody>");
			tbody.attr "id", "instances"
			table.append tbody
			instancesJson.forEach (instance) =>
				@buildTableLine(instance, attributes, tbody)
		else
			table.append "There are not instances"

	buildTableLine: (instance, attributes, tbody) ->
		trbody = $("<tr>")
		trbody.attr "id", "instance_" + instance.id
		tbody.append trbody
		i = 0
		attributes.forEach (attribute) =>
			td  = $("<td>");
			td.attr "id", "instance_" + instance.id + "_attribute_" + attribute.id
			if(i < instance.values.length)
				v = instance.values[i]
				if(attribute.id == v.attribute.id) 
					td.append v.value
					i++
			trbody.append td
				
		trbody.click => 
			LOM.loadScript 'api/widget/class/' + instance.clazz.fullName + '/edit',
				classFullName: classFullName
				id: instance.id

return new TableClassListingWidget