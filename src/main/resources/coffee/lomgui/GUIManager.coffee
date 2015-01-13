window.GUIManager = {}

class GUIElement
	constructor: (@id) ->

GUIManager.loadScript = (type, url, callback) ->
	$.getJSON 'api/' + type + '/' + url, (json) =>
		guiElement = eval json.script
		guiElement.id = json.id
		callback(guiElement)

GUIManager.loadRenderer = (url, callback) ->
	GUIManager.loadScript('renderer', url, callback);

GUIManager.loadWidget = (url, callback) ->
	GUIManager.loadScript('widget', url, callback);

GUIManager.getMainRenderer = (callback) ->
	GUIManager.loadRenderer 'root', callback

GUIManager.getRendererEntity = (entity, callback) ->
	GUIManager.loadRenderer 'entity/' + entity.fullName, callback

GUIManager.getRendererAttribute = (entity, attribute, callback) ->
	GUIManager.loadRenderer 'entity/' + entity.fullName + '/' + attribute.name, callback

GUIManager.getWidget = (renderer, hook, callback) ->
	GUIManager.loadWidget renderer.id + '/' + hook , callback

