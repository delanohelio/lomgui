class UlRootWidget

    init: (view, conf) ->
        @page = view
        LOM.getJSON 'api/data/class', (jsonObj) =>
            @drawList(jsonObj)

    drawList: (jsonObj) ->
        ul = $("<ul>")
        ul.attr "id", "classes"
        @page.append ul
        $.each jsonObj, (i, clazz) =>
            @drawLine(ul, clazz)

    drawLine: (ul, clazz) ->
        li = $("<li>#{clazz.name}</li>")
        li.attr "id", "class_" + clazz.fullName
        ul.append li
        li.click => 
            LOM.loadScriptInNewView 'api/widget/class/'+ clazz.fullName,
                classFullName: clazz.fullName

return new UlRootWidget