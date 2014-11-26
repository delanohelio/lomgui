class PasswordAttributeRenderer

    init: (view, conf) ->
		view.append Array(conf.data.length+1).join '*'
        
return new PasswordAttributeRenderer