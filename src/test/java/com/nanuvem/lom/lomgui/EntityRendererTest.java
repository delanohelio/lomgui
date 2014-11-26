package com.nanuvem.lom.lomgui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.nanuvem.lom.api.Attribute;
import com.nanuvem.lom.api.AttributeType;
import com.nanuvem.lom.api.AttributeValue;
import com.nanuvem.lom.api.Entity;
import com.nanuvem.lom.api.Instance;
import com.nanuvem.lom.lomgui.resources.AttributeResource;
import com.nanuvem.lom.lomgui.resources.EntityResource;
import com.nanuvem.lom.lomgui.resources.InstanceResource;
import com.nanuvem.lom.lomgui.resources.Widget;
import com.nanuvem.lom.lomgui.resources.WidgetResource;

public class EntityRendererTest {

	private static final int DEFAULT_TIMEOUT = 10;
	private static WebDriver driver;

	private static EntityResource entityResource;
	private static AttributeResource attributeResource;
	private static InstanceResource instanceResource;

	private static List<Entity> entities;

	@BeforeClass
	public static void setUp() throws ParseException, IOException {
		driver = new FirefoxDriver();

		entityResource = new EntityResource();
		attributeResource = new AttributeResource();
		instanceResource = new InstanceResource();

		entities = new ArrayList<Entity>();
	}

	@AfterClass
	public static void tearDown() {
		driver.close();

		for (Entity entity : entities) {
			entityResource.delete(entity.getId().toString());
		}

	}


	@Test
	public void scenarioCheckAddAttributeAndInstance() {
		Entity entity = createAndAddEntity("test", "Client");
		// Attribute
		Attribute nameAttribute = createAndAddAttribute(entity, "name",
				AttributeType.TEXT);

		accessEntityWidget(entity);

		String idAttributeName = "attribute_" + nameAttribute.getId();
		WebElement attributeElement = ElementHelper.waitAndFindElementById(
				driver, idAttributeName, DEFAULT_TIMEOUT);

		assertNotNull("Attribute not found: " + nameAttribute.getName(),
				attributeElement);
		assertEquals(nameAttribute.getName(), attributeElement.getText());

		// Instance

		String nameValue = "Delano";
		Map<Attribute, String> attributesValuesMap = ImmutableMap
				.<Attribute, String> builder().put(nameAttribute, nameValue)
				.build();
		Instance nameInstance = createAndAddInstance(entity, attributesValuesMap);

		accessEntityWidget(entity);

		String idAttributeValueName = "instance_" + nameInstance.getId()
				+ "_attribute_" + nameAttribute.getId();
		WebElement attributeValueElement = ElementHelper
				.waitAndFindElementById(driver, idAttributeValueName,
						DEFAULT_TIMEOUT);

		assertNotNull("AttributeValue not found: " + nameAttribute.getName(),
				attributeValueElement);
		assertEquals(nameValue, attributeValueElement.getText());

	}

	@Test
	public void scenarioChangeAttributeAndValue() throws ParseException,
			IOException {
		Entity entity = createAndAddEntity("test", "Employee");
		// Attribute
		Attribute aAttribute = createAndAddAttribute(entity, "surname",
				AttributeType.TEXT);
		accessEntityWidget(entity);
		String idAttribute = "attribute_" + aAttribute.getId();
		WebElement attributeElement = ElementHelper.waitAndFindElementById(
				driver, idAttribute, DEFAULT_TIMEOUT);
		assertNotNull("Attribute not found: " + aAttribute.getName(),
				attributeElement);
		assertEquals(aAttribute.getName(), attributeElement.getText());

		aAttribute.setName("lastname");
		aAttribute = attributeResource
				.toObject(attributeResource.put(entity.getFullName(), aAttribute
						.getId().toString(), aAttribute));
		accessEntityWidget(entity);
		attributeElement = ElementHelper.waitAndFindElementById(driver,
				idAttribute, DEFAULT_TIMEOUT);
		assertNotNull("Attribute not found: " + aAttribute.getName(),
				attributeElement);
		assertEquals(aAttribute.getName(), attributeElement.getText());

		// Instance

	}

	@Test
	public void scenarioChangeAttributeWithWrongEntity() throws ParseException,
			IOException {
		Entity entity = createAndAddEntity("test", "Product");

		String attributeName = "quantity";
		Attribute aAttribute = createAndAddAttribute(entity, attributeName,
				AttributeType.INTEGER);
		accessEntityWidget(entity);
		String idAttribute = "attribute_" + aAttribute.getId();
		WebElement attributeElement = ElementHelper.waitAndFindElementById(
				driver, idAttribute, DEFAULT_TIMEOUT);
		assertNotNull("Attribute not found: " + aAttribute.getName(),
				attributeElement);
		assertEquals(aAttribute.getName(), attributeElement.getText());

		String editedAttributeName = "quant";
		aAttribute.setName(editedAttributeName);
		Entity wrongEntity = createAndAddEntity("test", "Order");
		attributeResource.put(wrongEntity.getFullName(), aAttribute.getId()
				.toString(), aAttribute);
		accessEntityWidget(entity);
		attributeElement = ElementHelper.waitAndFindElementById(driver,
				idAttribute, DEFAULT_TIMEOUT);
		assertNotNull("Attribute not found: " + aAttribute.getName(),
				attributeElement);
		assertEquals(attributeName, attributeElement.getText());

	}

	@Test
	public void scenarioChangeEntityWidget() {
		Entity entity = createAndAddEntity("test", "Foo");

		setWidget("SimpleEntityListingRenderer", "entity", entity.getFullName());

		Attribute attribute = createAndAddAttribute(entity, "FooAttribute",
				AttributeType.TEXT);
		Map<Attribute, String> attributesValuesMap = ImmutableMap
				.<Attribute, String> builder().put(attribute, "FooValue")
				.build();
		Instance instance = createAndAddInstance(entity, attributesValuesMap);

		String idInstance = "instance_" + instance.getId();
		accessEntityWidget(entity);
		WebElement instanceElement = ElementHelper.waitAndFindElementById(
				driver, idInstance, DEFAULT_TIMEOUT);
		assertNotNull("Instance not found: " + instance.getId(),
				instanceElement);
		assertEquals("p", instanceElement.getTagName());

		setWidget("TableEntityListingRenderer", "entity", entity.getFullName());

		accessEntityWidget(entity);
		instanceElement = ElementHelper.waitAndFindElementById(driver,
				idInstance, DEFAULT_TIMEOUT);
		assertNotNull("Instance not found: " + instance.getId(),
				instanceElement);
		assertEquals("tr", instanceElement.getTagName());

	}

	@Test
	public void scenarioChangeAttributeWidget() {
		Entity entity = createAndAddEntity("test", "User");

		Attribute attribute = createAndAddAttribute(entity, "Password",
				AttributeType.PASSWORD);
		
		String attributeValue = "123456";
		Map<Attribute, String> attributesValuesMap = ImmutableMap
				.<Attribute, String> builder().put(attribute, attributeValue)
				.build();
		Instance instance = createAndAddInstance(entity, attributesValuesMap);

		String idAttributeValueName = "instance_" + instance.getId()
				+ "_attribute_" + attribute.getId();
		
		setWidget("PasswordAttributeRenderer", "entity/"+entity.getFullName(), attribute.getName());
		accessEntityWidget(entity);
		WebElement attributeValueElement = ElementHelper
				.waitAndFindElementById(driver, idAttributeValueName,
						DEFAULT_TIMEOUT);
		assertNotNull("AttributeValue not found: " + attribute.getName(),
				attributeValueElement);
		assertEquals(Strings.repeat("*", attributeValue.length()), attributeValueElement.getText());
		
	}

	private void accessEntityWidget(Entity entity) {
		driver.get("http://localhost:8080/lomgui/");

		String idEntity = "entity_" + entity.getFullName();
		WebElement clientLi = ElementHelper.waitAndFindElementById(driver,
				idEntity, DEFAULT_TIMEOUT);
		clientLi.click();
	}

	private Entity createAndAddEntity(String nameSpace, String name) {
		Entity entity = new Entity();
		entity.setNamespace(nameSpace);
		entity.setName(name);
		try {
			HttpResponse response = entityResource.post(entity);
			assertEquals(201, response.getStatusLine().getStatusCode());
			entity = entityResource.toObject(response);
			entities.add(entity);
			return entity;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Attribute createAndAddAttribute(Entity entity, String name,
			AttributeType type) {
		Attribute attribute = new Attribute();
		attribute.setName(name);
		attribute.setType(type);
		try {
			HttpResponse response = attributeResource.post(entity.getFullName(),
					attribute);
			assertEquals(201, response.getStatusLine().getStatusCode());
			return attributeResource.toObject(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Instance createAndAddInstance(Entity entity,
			Map<Attribute, String> attributesValuesMap) {
		Instance instance = new Instance();
		instance.setEntity(entity);
		instance.setValues(attributesValuesFromMap(attributesValuesMap,
				instance));
		try {
			HttpResponse response = instanceResource.post(entity.getFullName(),
					instance);
			assertEquals(201, response.getStatusLine().getStatusCode());
			return instanceResource.toObject(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<AttributeValue> attributesValuesFromMap(
			Map<Attribute, String> attributesValuesMap, Instance instance) {
		List<AttributeValue> attributesValues = new LinkedList<AttributeValue>();
		for (Attribute attribute : attributesValuesMap.keySet()) {
			AttributeValue attributeValue = new AttributeValue();
			attributeValue.setInstance(instance);
			attributeValue.setAttribute(attribute);
			attributeValue.setValue(attributesValuesMap.get(attribute));
			attributesValues.add(attributeValue);
		}
		return attributesValues;
	}

	private void setWidget(String widgetName, String target, String qualifier) {
		String widgetResourseURL = target;
		if (qualifier != null)
			widgetResourseURL += "/" + qualifier;
		Widget widget = new Widget();
		widget.setName(widgetName);
		HttpResponse response = WidgetResource.getWidgetResource(
				widgetResourseURL).post(widget);
		assertEquals(200, response.getStatusLine().getStatusCode());
	}

}
