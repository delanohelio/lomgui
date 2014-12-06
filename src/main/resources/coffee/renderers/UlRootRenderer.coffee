class UlRootRenderer extends GUIElement

    accept: (view, context) ->
        @page = view
        @drawList(context.entities)

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