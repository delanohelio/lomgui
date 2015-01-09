window.Controller = {}

class Context
	constructor: (@entities, @entity, @method) ->

Controller.openApp = (view) ->
	GUIManager.getMainRenderer (mr) =>
		DataManager.getAllEntities (allEntities) =>
			mr.accept view, new Context allEntities

Controller.entityEvent = (view, entity, method) ->
	GUIManager.getRenderer entity, (er) =>
		er.accept view, new Context null, entity, method
