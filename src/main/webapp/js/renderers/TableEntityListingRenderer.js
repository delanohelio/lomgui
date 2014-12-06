(function() {
  var TableEntityListingRenderer;

  TableEntityListingRenderer = (function() {

    function TableEntityListingRenderer() {}

    TableEntityListingRenderer.prototype.init = function(view, conf) {
      var _this = this;
      return LOM.getJSON("api/data/entity/" + conf.entityFullName + "/attributes", function(attributes) {
        return _this.drawTable(attributes, conf.entityFullName, view);
      });
    };

    TableEntityListingRenderer.prototype.drawTable = function(attributes, entityFullName, view) {
      var table,
        _this = this;
      this.page = view;
      table = $("<table>");
      this.page.append(table);
      this.buildTableHead(attributes, table);
      return LOM.getJSON("api/data/entity/" + entityFullName + "/instances", function(instances) {
        return _this.buildTableBody(instances, table);
      });
    };

    TableEntityListingRenderer.prototype.buildTableHead = function(attributes, table) {
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

    TableEntityListingRenderer.prototype.buildTableBody = function(instances, table) {
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

    TableEntityListingRenderer.prototype.buildTableLine = function(instance, tbody) {
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
        return LOM.loadScript('api/widget/entity/' + instance.entity.fullName + '/' + attributeValue.attribute.name, td, {
          data: attributeValue.value
        });
      });
      return trbody.click(function() {
        return LOM.loadScript('api/widget/entity/' + instance.entity.fullName + '/edit', {
          entityFullName: instance.entity.fullName,
          id: instance.id
        });
      });
    };

    return TableEntityListingRenderer;

  })();

  return new TableEntityListingRenderer;

}).call(this);
