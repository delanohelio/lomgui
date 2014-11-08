class TableRootWidget

    init: (view, conf) ->
        @page = view
        LOM.getJSON 'api/data/class', (jsonObj) =>
            @drawTable(jsonObj)

    drawTable: (jsonObj) ->
        table = $("<table>")
        th = $("<tr><th>Classes</th></tr>")
        th.attr "id", "classes"
        table.append th
        @page.append table
        $.each jsonObj, (i, clazz) =>
            @drawLine(table, clazz)

    drawLine: (table, clazz) ->
        tr = $("<tr><td>#{clazz.name}</td></tr>")
        tr.attr "id", "class_" + clazz.fullName
        table.append tr
        tr.click => 
            LOM.loadScriptInNewView 'api/widget/class/'+ clazz.fullName,
                classFullName: clazz.fullName

return new TableRootWidget