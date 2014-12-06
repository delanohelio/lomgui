window.DataManager = {}

DataManager.loadData = (url, callback) ->
	$.getJSON 'api/data/' + url, (json) =>
			callback(json)

DataManager.getAllEntities = (callback) ->
	DataManager.loadData 'entity', callback