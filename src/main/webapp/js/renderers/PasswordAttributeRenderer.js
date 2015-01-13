(function() {
  var PasswordAttributeRenderer,
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  PasswordAttributeRenderer = (function(_super) {

    __extends(PasswordAttributeRenderer, _super);

    function PasswordAttributeRenderer() {
      return PasswordAttributeRenderer.__super__.constructor.apply(this, arguments);
    }

    PasswordAttributeRenderer.prototype.accept = function(view, context) {
      return view.append(Array(context.attributeValue.value.length + 1).join('*'));
    };

    return PasswordAttributeRenderer;

  })(GUIElement);

  return new PasswordAttributeRenderer;

}).call(this);
