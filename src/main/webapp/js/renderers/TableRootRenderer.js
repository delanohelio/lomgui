(function() {
  var TableRootWidget;

  TableRootWidget = (function() {

    function TableRootWidget() {}

    TableRootWidget.prototype.init = function(view, conf) {
      var _this = this;
      this.page = view;
      return LOM.getJSON('api/data/entity', function(jsonObj) {
        return _this.drawTable(jsonObj);
      });
    };

    TableRootWidget.prototype.drawTable = function(jsonObj) {
      var table, th,
        _this = this;
      table = $("<table>");
      th = $("<tr><th>Entities</th></tr>");
      th.attr("id", "entities");
      table.append(th);
      this.page.append(table);
      return jsonObj.forEach(function(entity) {
        return _this.drawLine(table, entity);
      });
    };

    TableRootWidget.prototype.drawLine = function(table, entity) {
      var tr,
        _this = this;
      tr = $("<tr><td>" + entity.name + "</td></tr>");
      tr.attr("id", "entity_" + entity.fullName);
      table.append(tr);
      return tr.click(function() {
        return LOM.loadScriptInNewView('api/widget/entity/' + entity.fullName, {
          entityFullName: entity.fullName
        });
      });
    };

    return TableRootWidget;

  })();

  return new TableRootWidget;

}).call(this);
