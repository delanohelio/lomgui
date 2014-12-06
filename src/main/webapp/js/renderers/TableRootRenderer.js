(function() {
  var TableRootRenderer,
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  TableRootRenderer = (function(_super) {

    __extends(TableRootRenderer, _super);

    function TableRootRenderer() {
      return TableRootRenderer.__super__.constructor.apply(this, arguments);
    }

    TableRootRenderer.prototype.accept = function(view, context) {
      var table,
        _this = this;
      table = $("<table>");
      view.append(table);
      return GUIManager.getWidget(this, "taskbar", function(widget) {
        widget.accept(table, context);
        return GUIManager.getWidget(_this, "menu", function(widget) {
          return widget.accept(table, context);
        });
      });
    };

    return TableRootRenderer;

  })(GUIElement);

  return new TableRootRenderer;

}).call(this);
