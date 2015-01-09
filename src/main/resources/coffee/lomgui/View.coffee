window.View = {}
window.LOM = {}

View.emptyPage = ->
	body  = $("body")
	body.empty()  
	page = $('<div>')
	body.append page
	page

LOM.loadScript = (url, view, conf) ->
	$.get url, (data, textStatus, jqxhr) ->
		x = eval data
		x.init view, conf
	, "text"

LOM.loadScriptInNewView = (url, conf) ->
	$.get url, (data, textStatus, jqxhr) ->
		x = eval data
		x.init LOM.emptyPage(), conf
	, "text"

$ -> 
	Controller.openApp View.emptyPage()