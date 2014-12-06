(function() {
  var MenuWidget,
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  MenuWidget = (function(_super) {

    __extends(MenuWidget, _super);

    function MenuWidget() {
      return MenuWidget.__super__.constructor.apply(this, arguments);
    }

    MenuWidget.prototype.accept = function(view, context) {
      var _this = this;
      return context.entities.forEach(function(entity) {
        return _this.drawLine(view, entity);
      });
    };

    MenuWidget.prototype.drawLine = function(view, entity) {
      var tr,
        _this = this;
      tr = $("<tr><td>" + entity.name + "</td></tr>");
      tr.attr("id", "entity_" + entity.fullName);
      view.append(tr);
      return tr.click(function() {
        return LOM.loadScriptInNewView('api/widget/entity/' + entity.fullName, {
          entityFullName: entity.fullName
        });
      });
    };

    return MenuWidget;

  })(GUIElement);

  return new MenuWidget;

}).call(this);
