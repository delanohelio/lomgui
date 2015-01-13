class PasswordAttributeRenderer extends GUIElement

	accept: (view, context) ->
		view.append Array(context.attributeValue.value.length+1).join '*'

return new PasswordAttributeRenderer