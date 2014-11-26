(function() {
  var UlRootWidget;

  UlRootWidget = (function() {

    function UlRootWidget() {}

    UlRootWidget.prototype.init = function(view, conf) {
      var _this = this;
      this.page = view;
      return LOM.getJSON('api/data/entity', function(jsonObj) {
        return _this.drawList(jsonObj);
      });
    };

    UlRootWidget.prototype.drawList = function(jsonObj) {
      var ul,
        _this = this;
      ul = $("<ul>");
      ul.attr("id", "entities");
      this.page.append(ul);
      return jsonObj.forEach(function(entity) {
        return _this.drawLine(ul, entity);
      });
    };

    UlRootWidget.prototype.drawLine = function(ul, entity) {
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

    return UlRootWidget;

  })();

  return new UlRootWidget;

}).call(this);
