(function() {
  var TaskBarWidget,
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  TaskBarWidget = (function(_super) {

    __extends(TaskBarWidget, _super);

    function TaskBarWidget() {
      return TaskBarWidget.__super__.constructor.apply(this, arguments);
    }

    TaskBarWidget.prototype.accept = function(view, context) {
      var th;
      th = $("<tr><th>Entities</th></tr>");
      th.attr("id", "entities");
      return view.append(th);
    };

    return TaskBarWidget;

  })(GUIElement);

  return new TaskBarWidget;

}).call(this);
