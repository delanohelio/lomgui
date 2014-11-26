class UlRootRenderer

    init: (view, conf) ->
        @page = view
        LOM.getJSON 'api/data/entity', (jsonObj) =>
            @drawList(jsonObj)

    drawList: (jsonObj) ->
        ul = $("<ul>")
        ul.attr "id", "entities"
        @page.append ul
        jsonObj.forEach (entity) =>
            @drawLine(ul, entity)

    drawLine: (ul, entity) ->
        li = $("<li>#{entity.name}</li>")
        li.attr "id", "entity_" + entity.fullName
        ul.append li
        li.click => 
            LOM.loadScriptInNewView 'api/widget/entity/'+ entity.fullName,
                entityFullName: entity.fullName

return new UlRootRenderer