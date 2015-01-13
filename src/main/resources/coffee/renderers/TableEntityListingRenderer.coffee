class TableEntityListingRenderer extends GUIElement

	accept: (view, context) ->
		@drawTable(context, view)

	drawTable: (context, view) ->
		@page = view
		table = $("<table>")
		@page.append table
		@buildTableHead(context.entity.attributes, table);
		@buildTableBody(context, table)

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

	buildTableBody: (context, table) ->
		if(context.entity.instances.length > 0)
			tbody = $("<tbody>");
			tbody.attr "id", "instances"
			table.append tbody
			context.entity.instances.forEach (instance) =>
				@buildTableLine(instance, context, tbody)
		else
			table.append "There are not instances"

	buildTableLine: (instance, context, tbody) ->
		entity = context.entity
		trbody = $("<tr>")
		trbody.attr "id", "instance_" + instance.id
		tbody.append trbody
		instance.values.forEach (attributeValue) =>
			td  = $("<td>");
			td.attr "id", "instance_" + instance.id + "_attribute_" + attributeValue.attribute.id
			context.attributeValue = attributeValue
			GUIManager.getRendererAttribute entity, attributeValue.attribute, (renderer) =>
				renderer.accept td, context
				trbody.append td
		trbody.click => 
			LOM.loadScript 'api/widget/entity/' + instance.entity.fullName + '/edit',
				entityFullName: instance.entity.fullName
				id: instance.id

return new TableEntityListingRenderer