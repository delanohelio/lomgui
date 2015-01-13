(function() {
  var GUIElement;

  window.GUIManager = {};

  GUIElement = (function() {

    function GUIElement(id) {
      this.id = id;
    }

    return GUIElement;

  })();

  GUIManager.loadScript = function(type, url, callback) {
    var _this = this;
    return $.getJSON('api/' + type + '/' + url, function(json) {
      var guiElement;
      guiElement = eval(json.script);
      guiElement.id = json.id;
      return callback(guiElement);
    });
  };

  GUIManager.loadRenderer = function(url, callback) {
    return GUIManager.loadScript('renderer', url, callback);
  };

  GUIManager.loadWidget = function(url, callback) {
    return GUIManager.loadScript('widget', url, callback);
  };

  GUIManager.getMainRenderer = function(callback) {
    return GUIManager.loadRenderer('root', callback);
  };

  GUIManager.getRendererEntity = function(entity, callback) {
    return GUIManager.loadRenderer('entity/' + entity.fullName, callback);
  };

  GUIManager.getRendererAttribute = function(entity, attribute, callback) {
    return GUIManager.loadRenderer('entity/' + entity.fullName + '/' + attribute.name, callback);
  };

  GUIManager.getWidget = function(renderer, hook, callback) {
    return GUIManager.loadWidget(renderer.id + '/' + hook, callback);
  };

}).call(this);
