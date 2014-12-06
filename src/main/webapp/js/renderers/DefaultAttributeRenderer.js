(function() {
  var DefaultAttributeRenderer;

  DefaultAttributeRenderer = (function() {

    function DefaultAttributeRenderer() {}

    DefaultAttributeRenderer.prototype.init = function(view, conf) {
      return view.append(conf.data);
    };

    return DefaultAttributeRenderer;

  })();

  return new DefaultAttributeRenderer;

}).call(this);
