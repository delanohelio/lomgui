(function() {
  var UlRootRenderer,
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  UlRootRenderer = (function(_super) {

    __extends(UlRootRenderer, _super);

    function UlRootRenderer() {
      return UlRootRenderer.__super__.constructor.apply(this, arguments);
    }

    UlRootRenderer.prototype.accept = function(view, context) {
      this.page = view;
      return this.drawList(context.entities);
    };

    UlRootRenderer.prototype.drawList = function(jsonObj) {
      var ul,
        _this = this;
      ul = $("<ul>");
      ul.attr("id", "entities");
      this.page.append(ul);
      return jsonObj.forEach(function(entity) {
        return _this.drawLine(ul, entity);
      });
    };

    UlRootRenderer.prototype.drawLine = function(ul, entity) {
      var li,
        _this = this;
      li = $("<li>" + entity.name + "</li>");
      li.attr("id", "entity_" + entity.fullName);
      ul.append(li);
      return li.click(function() {
        return LOM.loadScriptInNewView('api/widget/entity/' + entity.fullName, {
          entityFullName: entity.fullName
        });
      });
    };

    return UlRootRenderer;

  })(GUIElement);

  return new UlRootRenderer;

}).call(this);
