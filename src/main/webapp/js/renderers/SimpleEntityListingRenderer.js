(function() {
  var TableEntityListingRenderer;

  TableEntityListingRenderer = (function() {

    function TableEntityListingRenderer() {}

    TableEntityListingRenderer.prototype.init = function(view, conf) {
      var _this = this;
      return LOM.getJSON("api/data/entity/" + conf.entityFullName + "/instances", function(instances) {
        return _this.buildTableBody(instances, view);
      });
    };

    TableEntityListingRenderer.prototype.buildTableBody = function(instances, view) {
      var _this = this;
      if (instances.length > 0) {
        return instances.forEach(function(instance) {
          return _this.buildTableLine(instance, view);
        });
      } else {
        return view.append("There are not instances");
      }
    };

    TableEntityListingRenderer.prototype.buildTableLine = function(instance, view) {
      var par, separator,
        _this = this;
      par = $("<p>");
      par.attr("id", "instance_" + instance.id);
      view.append(par);
      separator = "";
      instance.values.forEach(function(attributeValue) {
        par.append(separator);
        par.append(attributeValue.value);
        return separator = ",";
      });
      return par.click(function() {
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
