(function() {
  var DefaultAttributeRenderer,
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  DefaultAttributeRenderer = (function(_super) {

    __extends(DefaultAttributeRenderer, _super);

    function DefaultAttributeRenderer() {
      return DefaultAttributeRenderer.__super__.constructor.apply(this, arguments);
    }

    DefaultAttributeRenderer.prototype.accept = function(view, context) {
      return view.append(context.attributeValue.value);
    };

    return DefaultAttributeRenderer;

  })(GUIElement);

  return new DefaultAttributeRenderer;

}).call(this);
