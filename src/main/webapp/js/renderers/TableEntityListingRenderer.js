(function() {
  var TableEntityListingRenderer,
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  TableEntityListingRenderer = (function(_super) {

    __extends(TableEntityListingRenderer, _super);

    function TableEntityListingRenderer() {
      return TableEntityListingRenderer.__super__.constructor.apply(this, arguments);
    }

    TableEntityListingRenderer.prototype.accept = function(view, context) {
      return this.drawTable(context, view);
    };

    TableEntityListingRenderer.prototype.drawTable = function(context, view) {
      var table;
      this.page = view;
      table = $("<table>");
      this.page.append(table);
      this.buildTableHead(context.entity.attributes, table);
      return this.buildTableBody(context, table);
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

    TableEntityListingRenderer.prototype.buildTableBody = function(context, table) {
      var tbody,
        _this = this;
      if (context.entity.instances.length > 0) {
        tbody = $("<tbody>");
        tbody.attr("id", "instances");
        table.append(tbody);
        return context.entity.instances.forEach(function(instance) {
          return _this.buildTableLine(instance, context, tbody);
        });
      } else {
        return table.append("There are not instances");
      }
    };

    TableEntityListingRenderer.prototype.buildTableLine = function(instance, context, tbody) {
      var entity, trbody,
        _this = this;
      entity = context.entity;
      trbody = $("<tr>");
      trbody.attr("id", "instance_" + instance.id);
      tbody.append(trbody);
      instance.values.forEach(function(attributeValue) {
        var td;
        td = $("<td>");
        td.attr("id", "instance_" + instance.id + "_attribute_" + attributeValue.attribute.id);
        context.attributeValue = attributeValue;
        return GUIManager.getRendererAttribute(entity, attributeValue.attribute, function(renderer) {
          renderer.accept(td, context);
          return trbody.append(td);
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

  })(GUIElement);

  return new TableEntityListingRenderer;

}).call(this);
