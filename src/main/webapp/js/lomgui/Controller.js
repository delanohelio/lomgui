(function() {
  var Context;

  window.Controller = {};

  Context = (function() {

    function Context(entities, entity, attributeValue, method) {
      this.entities = entities;
      this.entity = entity;
      this.attributeValue = attributeValue;
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
    return GUIManager.getRendererEntity(entity, function(er) {
      return er.accept(view, new Context(null, entity, null, method));
    });
  };

}).call(this);
