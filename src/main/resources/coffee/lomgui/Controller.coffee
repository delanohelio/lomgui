window.Controller = {}

class Context
	constructor: (@entities, @entity, @attributeValue, @method) ->

Controller.openApp = (view) ->
	GUIManager.getMainRenderer (mr) =>
		DataManager.getAllEntities (allEntities) =>
			mr.accept view, new Context allEntities

Controller.entityEvent = (view, entity, method) ->
	GUIManager.getRendererEntity entity, (er) =>
		er.accept view, new Context null, entity, null, method
