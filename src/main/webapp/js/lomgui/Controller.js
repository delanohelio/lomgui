(function() {
  var Context;

  window.Controller = {};

  Context = (function() {

    function Context(entities, method) {
      this.entities = entities;
      this.method = method;
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

  Controller.entityEvent = function(view, entity, method) {
    var _this = this;
    return GUIManager.getRenderer(entity, function(er) {
      return er.accept(view, new Context(entity));
    });
  };

}).call(this);
