(function() {
  var PasswordAttributeRenderer;

  PasswordAttributeRenderer = (function() {

    function PasswordAttributeRenderer() {}

    PasswordAttributeRenderer.prototype.init = function(view, conf) {};

    return PasswordAttributeRenderer;

  })();

  view.append(Array(conf.data.length + 1).join('*'));

  return new PasswordAttributeRenderer;

}).call(this);
