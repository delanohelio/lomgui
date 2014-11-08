(function() {
  var TableRootWidget;

  TableRootWidget = (function() {

    function TableRootWidget() {}

    TableRootWidget.prototype.init = function(view, conf) {
      var _this = this;
      this.page = view;
      return LOM.getJSON('api/data/class', function(jsonObj) {
        return _this.drawTable(jsonObj);
      });
    };

    TableRootWidget.prototype.drawTable = function(jsonObj) {
      var table, th,
        _this = this;
      table = $("<table>");
      th = $("<tr><th>Classes</th></tr>");
      th.attr("id", "classes");
      table.append(th);
      this.page.append(table);
      return $.each(jsonObj, function(i, clazz) {
        return _this.drawLine(table, clazz);
      });
    };

    TableRootWidget.prototype.drawLine = function(table, clazz) {
      var tr,
        _this = this;
      tr = $("<tr><td>" + clazz.name + "</td></tr>");
      tr.attr("id", "class_" + clazz.fullName);
      table.append(tr);
      return tr.click(function() {
        return LOM.loadScriptInNewView('api/widget/class/' + clazz.fullName, {
          classFullName: clazz.fullName
        });
      });
    };

    return TableRootWidget;

  })();

  return new TableRootWidget;

}).call(this);
