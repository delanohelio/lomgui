(function() {
  var Context;

  window.Controller = {};

  Context = (function() {

    function Context(entities) {
      this.entities = entities;
    }

    return Context;

  })();

  Controller.openApp = function(view) {
    var _this = this;
    return GUIManager.getMainRenderer(function(mr) {
      return DataManager.getAllEntities(function(allEntities) {
        return mr.accept(view, new Context(allEntities));
      });
    });
  };

}).call(this);
