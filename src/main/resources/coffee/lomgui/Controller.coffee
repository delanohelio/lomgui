window.Controller = {}

class Context
	constructor: (@entities) ->

Controller.openApp = (view) ->
	GUIManager.getMainRenderer (mr) =>
		DataManager.getAllEntities (allEntities) =>
			mr.accept view, new Context allEntities
	