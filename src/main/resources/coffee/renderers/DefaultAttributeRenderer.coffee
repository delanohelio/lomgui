class DefaultAttributeRenderer extends GUIElement

	accept: (view, context) ->
		view.append context.attributeValue.value

return new DefaultAttributeRenderer