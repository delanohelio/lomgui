(function() {

  window.DataManager = {};

  DataManager.loadData = function(url, callback) {
    var _this = this;
    return $.getJSON('api/data/' + url, function(json) {
      return callback(json);
    });
  };

  DataManager.getAllEntities = function(callback) {
    return DataManager.loadData('entity', callback);
  };

}).call(this);
