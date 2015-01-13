(function() {
  var SimpleEntityListingRenderer,
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  SimpleEntityListingRenderer = (function(_super) {

    __extends(SimpleEntityListingRenderer, _super);

    function SimpleEntityListingRenderer() {
      return SimpleEntityListingRenderer.__super__.constructor.apply(this, arguments);
    }

    SimpleEntityListingRenderer.prototype.accept = function(view, context) {
      return this.buildTableBody(context.entity, view);
    };

    SimpleEntityListingRenderer.prototype.buildTableBody = function(entity, view) {
      var _this = this;
      if (entity.instances.length > 0) {
        return entity.instances.forEach(function(instance) {
          return _this.buildTableLine(instance, entity, view);
        });
      } else {
        return view.append("There are not instances");
      }
    };

    SimpleEntityListingRenderer.prototype.buildTableLine = function(instance, entity, view) {
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
        return LOM.loadScript('api/widget/entity/' + entity.fullName + '/edit', {
          entityFullName: entity.fullName,
          id: instance.id
        });
      });
    };

    return SimpleEntityListingRenderer;

  })(GUIElement);

  return new SimpleEntityListingRenderer;

}).call(this);
