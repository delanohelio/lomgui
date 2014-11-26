(function() {

  window.LOM = {};

  LOM.emptyPage = function() {
    var body, page;
    body = $("body");
    body.empty();
    page = $('<div>');
    body.append(page);
    return page;
  };

  LOM.loadScript = function(url, view, conf) {
    return $.get(url, function(data, textStatus, jqxhr) {
      var x;
      x = eval(data);
      return x.init(view, conf);
    }, "text");
  };

  LOM.loadScriptInNewView = function(url, conf) {
    return $.get(url, function(data, textStatus, jqxhr) {
      var x;
      x = eval(data);
      return x.init(LOM.emptyPage(), conf);
    }, "text");
  };

  LOM.getJSON = function(url, callback) {
    var _this = this;
    return $.getJSON(url, function(jsonObj) {
      return callback(jsonObj);
    });
  };

  $(function() {
    return LOM.loadScript('api/widget/root', LOM.emptyPage(), {});
  });

}).call(this);
