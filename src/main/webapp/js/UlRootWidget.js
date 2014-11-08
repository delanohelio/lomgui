(function() {
  var UlRootWidget;

  UlRootWidget = (function() {

    function UlRootWidget() {}

    UlRootWidget.prototype.init = function(view, conf) {
      var _this = this;
      this.page = view;
      return LOM.getJSON('api/data/class', function(jsonObj) {
        return _this.drawList(jsonObj);
      });
    };

    UlRootWidget.prototype.drawList = function(jsonObj) {
      var ul,
        _this = this;
      ul = $("<ul>");
      ul.attr("id", "classes");
      this.page.append(ul);
      return $.each(jsonObj, function(i, clazz) {
        return _this.drawLine(ul, clazz);
      });
    };

    UlRootWidget.prototype.drawLine = function(ul, clazz) {
      var li,
        _this = this;
      li = $("<li>" + clazz.name + "</li>");
      li.attr("id", "class_" + clazz.fullName);
      ul.append(li);
      return li.click(function() {
        return LOM.loadScriptInNewView('api/widget/class/' + clazz.fullName, {
          classFullName: clazz.fullName
        });
      });
    };

    return UlRootWidget;

  })();

  return new UlRootWidget;

}).call(this);
