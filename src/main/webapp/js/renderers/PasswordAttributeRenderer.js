(function() {
  var PasswordAttributeWidget;

  PasswordAttributeWidget = (function() {

    function PasswordAttributeWidget() {}

    PasswordAttributeWidget.prototype.init = function(view, conf) {};

    return PasswordAttributeWidget;

  })();

  view.append(Array(conf.data.length + 1).join('*'));

  return new PasswordAttributeWidget;

}).call(this);
