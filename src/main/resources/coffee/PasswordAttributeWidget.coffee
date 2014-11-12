class PasswordAttributeWidget

    init: (view, conf) ->
		view.append Array(conf.data.length+1).join '*'
        
return new PasswordAttributeWidget