(function() {
  var DefaultAttributeWidget;

  DefaultAttributeWidget = (function() {

    function DefaultAttributeWidget() {}

    DefaultAttributeWidget.prototype.init = function(view, conf) {
      return view.append(conf.data);
    };

    return DefaultAttributeWidget;

  })();

  return new DefaultAttributeWidget;

}).call(this);
