class TableClassListingWidget

	init: (view, conf) ->
		LOM.getJSON "api/data/class/#{conf.classFullName}/attributes", (attributes) =>
			@drawTable(attributes, conf.classFullName, view)

	drawTable: (attributes, classFullName, view) ->
		@page = view
		table = $("<table>")
		@page.append table
		@buildTableHead(attributes, table);
		LOM.getJSON "api/data/class/#{classFullName}/instances", (instances) =>
			@buildTableBody(instances, table)

	buildTableHead: (attributes, table) ->
		thead = $("<thead>");
		table.append thead
		trHead = $("<tr>");
		trHead.attr "id", "attributes"
		thead.append trHead
		attributes.forEach (attribute) ->
			thHead = $("<th>#{attribute.name}</th>")
			thHead.attr "id", "attribute_" + attribute.id
			trHead.append thHead

	buildTableBody: (instances, table) ->
		if(instances.length > 0)
			tbody = $("<tbody>");
			tbody.attr "id", "instances"
			table.append tbody
			instances.forEach (instance) =>
				@buildTableLine(instance, tbody)
		else
			table.append "There are not instances"

	buildTableLine: (instance, tbody) ->
		trbody = $("<tr>")
		trbody.attr "id", "instance_" + instance.id
		tbody.append trbody
		instance.values.forEach (attributeValue) =>
			td  = $("<td>");
			td.attr "id", "instance_" + instance.id + "_attribute_" + attributeValue.attribute.id
			td.append attributeValue.value
			trbody.append td
		trbody.click => 
			LOM.loadScript 'api/widget/class/' + instance.clazz.fullName + '/edit',
				classFullName: instance.clazz.fullName
				id: instance.id

return new TableClassListingWidget