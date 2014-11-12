(function() {
  var TableClassListingWidget;

  TableClassListingWidget = (function() {

    function TableClassListingWidget() {}

    TableClassListingWidget.prototype.init = function(view, conf) {
      var _this = this;
      return LOM.getJSON("api/data/class/" + conf.classFullName + "/attributes", function(attributes) {
        return _this.drawTable(attributes, conf.classFullName, view);
      });
    };

    TableClassListingWidget.prototype.drawTable = function(attributes, classFullName, view) {
      var table,
        _this = this;
      this.page = view;
      table = $("<table>");
      this.page.append(table);
      this.buildTableHead(attributes, table);
      return LOM.getJSON("api/data/class/" + classFullName + "/instances", function(instances) {
        return _this.buildTableBody(instances, table);
      });
    };

    TableClassListingWidget.prototype.buildTableHead = function(attributes, table) {
      var thead, trHead;
      thead = $("<thead>");
      table.append(thead);
      trHead = $("<tr>");
      trHead.attr("id", "attributes");
      thead.append(trHead);
      return attributes.forEach(function(attribute) {
        var thHead;
        thHead = $("<th>" + attribute.name + "</th>");
        thHead.attr("id", "attribute_" + attribute.id);
        return trHead.append(thHead);
      });
    };

    TableClassListingWidget.prototype.buildTableBody = function(instances, table) {
      var tbody,
        _this = this;
      if (instances.length > 0) {
        tbody = $("<tbody>");
        tbody.attr("id", "instances");
        table.append(tbody);
        return instances.forEach(function(instance) {
          return _this.buildTableLine(instance, tbody);
        });
      } else {
        return table.append("There are not instances");
      }
    };

    TableClassListingWidget.prototype.buildTableLine = function(instance, tbody) {
      var trbody,
        _this = this;
      trbody = $("<tr>");
      trbody.attr("id", "instance_" + instance.id);
      tbody.append(trbody);
      instance.values.forEach(function(attributeValue) {
        var td;
        td = $("<td>");
        td.attr("id", "instance_" + instance.id + "_attribute_" + attributeValue.attribute.id);
        trbody.append(td);
        return LOM.loadScript('api/widget/class/' + instance.clazz.fullName + '/' + attributeValue.attribute.name, td, {
          data: attributeValue.value
        });
      });
      return trbody.click(function() {
        return LOM.loadScript('api/widget/class/' + instance.clazz.fullName + '/edit', {
          classFullName: instance.clazz.fullName,
          id: instance.id
        });
      });
    };

    return TableClassListingWidget;

  })();

  return new TableClassListingWidget;

}).call(this);
