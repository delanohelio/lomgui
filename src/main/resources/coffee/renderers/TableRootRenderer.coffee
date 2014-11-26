class TableRootRenderer

    init: (view, conf) ->
        @page = view
        LOM.getJSON 'api/data/entity', (jsonObj) =>
            @drawTable(jsonObj)

    drawTable: (jsonObj) ->
        table = $("<table>")
        th = $("<tr><th>Entities</th></tr>")
        th.attr "id", "entities"
        table.append th
        @page.append table
        jsonObj.forEach (entity) =>
            @drawLine(table, entity)

    drawLine: (table, entity) ->
        tr = $("<tr><td>#{entity.name}</td></tr>")
        tr.attr "id", "entity_" + entity.fullName
        table.append tr
        tr.click => 
            LOM.loadScriptInNewView 'api/widget/entity/'+ entity.fullName,
                entityFullName: entity.fullName

return new TableRootRenderer