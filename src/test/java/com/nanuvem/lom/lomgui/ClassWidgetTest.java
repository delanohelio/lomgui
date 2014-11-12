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
import com.nanuvem.lom.kernel.Attribute;
import com.nanuvem.lom.kernel.AttributeType;
import com.nanuvem.lom.kernel.AttributeValue;
import com.nanuvem.lom.kernel.Class;
import com.nanuvem.lom.kernel.Instance;
import com.nanuvem.lom.lomgui.resources.AttributeResource;
import com.nanuvem.lom.lomgui.resources.ClassResource;
import com.nanuvem.lom.lomgui.resources.InstanceResource;
import com.nanuvem.lom.lomgui.resources.Widget;
import com.nanuvem.lom.lomgui.resources.WidgetResource;

public class ClassWidgetTest {

	private static final int DEFAULT_TIMEOUT = 10;
	private static WebDriver driver;

	private static ClassResource clazzResource;
	private static AttributeResource attributeResource;
	private static InstanceResource instanceResource;

	private static List<Class> classes;

	@BeforeClass
	public static void setUp() throws ParseException, IOException {
		driver = new FirefoxDriver();

		clazzResource = new ClassResource();
		attributeResource = new AttributeResource();
		instanceResource = new InstanceResource();

		classes = new ArrayList<Class>();
	}

	@AfterClass
	public static void tearDown() {
		driver.close();

		for (Class clazz : classes) {
			clazzResource.delete(clazz.getId().toString());
		}

	}


	@Test
	public void scenarioCheckAddAttributeAndInstance() {
		Class clazz = createAndAddClass("test", "Client");
		// Attribute
		Attribute nameAttribute = createAndAddAttribute(clazz, "name",
				AttributeType.TEXT);

		accessClassWidget(clazz);

		String idAttributeName = "attribute_" + nameAttribute.getId();
		WebElement attributeElement = ElementHelper.waitAndFindElementById(
				driver, idAttributeName, DEFAULT_TIMEOUT);

		assertNotNull("Attribute not found: " + nameAttribute.getName(),
				attributeElement);
		assertEquals(nameAttribute.getName(), attributeElement.getText());

		// Instance

		String nameValue = "Delano";
		Map<Attribute, Object> attributesValuesMap = ImmutableMap
				.<Attribute, Object> builder().put(nameAttribute, nameValue)
				.build();
		Instance nameInstance = createAndAddInstance(clazz, attributesValuesMap);

		accessClassWidget(clazz);

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
		Class clazz = createAndAddClass("test", "Employee");
		// Attribute
		Attribute aAttribute = createAndAddAttribute(clazz, "surname",
				AttributeType.TEXT);
		accessClassWidget(clazz);
		String idAttribute = "attribute_" + aAttribute.getId();
		WebElement attributeElement = ElementHelper.waitAndFindElementById(
				driver, idAttribute, DEFAULT_TIMEOUT);
		assertNotNull("Attribute not found: " + aAttribute.getName(),
				attributeElement);
		assertEquals(aAttribute.getName(), attributeElement.getText());

		aAttribute.setName("lastname");
		aAttribute = attributeResource
				.toObject(attributeResource.put(clazz.getFullName(), aAttribute
						.getId().toString(), aAttribute));
		accessClassWidget(clazz);
		attributeElement = ElementHelper.waitAndFindElementById(driver,
				idAttribute, DEFAULT_TIMEOUT);
		assertNotNull("Attribute not found: " + aAttribute.getName(),
				attributeElement);
		assertEquals(aAttribute.getName(), attributeElement.getText());

		// Instance

	}

	@Test
	public void scenarioChangeAttributeWithWrongClass() throws ParseException,
			IOException {
		Class clazz = createAndAddClass("test", "Product");

		String attributeName = "quantity";
		Attribute aAttribute = createAndAddAttribute(clazz, attributeName,
				AttributeType.INTEGER);
		accessClassWidget(clazz);
		String idAttribute = "attribute_" + aAttribute.getId();
		WebElement attributeElement = ElementHelper.waitAndFindElementById(
				driver, idAttribute, DEFAULT_TIMEOUT);
		assertNotNull("Attribute not found: " + aAttribute.getName(),
				attributeElement);
		assertEquals(aAttribute.getName(), attributeElement.getText());

		String editedAttributeName = "quant";
		aAttribute.setName(editedAttributeName);
		Class wrongClass = createAndAddClass("test", "Order");
		attributeResource.put(wrongClass.getFullName(), aAttribute.getId()
				.toString(), aAttribute);
		accessClassWidget(clazz);
		attributeElement = ElementHelper.waitAndFindElementById(driver,
				idAttribute, DEFAULT_TIMEOUT);
		assertNotNull("Attribute not found: " + aAttribute.getName(),
				attributeElement);
		assertEquals(attributeName, attributeElement.getText());

	}

	@Test
	public void scenarioChangeClassWidget() {
		Class clazz = createAndAddClass("test", "Foo");

		setWidget("SimpleClassListingWidget", "class", clazz.getFullName());

		Attribute attribute = createAndAddAttribute(clazz, "FooAttribute",
				AttributeType.TEXT);
		Map<Attribute, Object> attributesValuesMap = ImmutableMap
				.<Attribute, Object> builder().put(attribute, "FooValue")
				.build();
		Instance instance = createAndAddInstance(clazz, attributesValuesMap);

		String idInstance = "instance_" + instance.getId();
		accessClassWidget(clazz);
		WebElement instanceElement = ElementHelper.waitAndFindElementById(
				driver, idInstance, DEFAULT_TIMEOUT);
		assertNotNull("Instance not found: " + instance.getId(),
				instanceElement);
		assertEquals("p", instanceElement.getTagName());

		setWidget("TableClassListingWidget", "class", clazz.getFullName());

		accessClassWidget(clazz);
		instanceElement = ElementHelper.waitAndFindElementById(driver,
				idInstance, DEFAULT_TIMEOUT);
		assertNotNull("Instance not found: " + instance.getId(),
				instanceElement);
		assertEquals("tr", instanceElement.getTagName());

	}

	@Test
	public void scenarioChangeAttributeWidget() {
		Class clazz = createAndAddClass("test", "User");

		Attribute attribute = createAndAddAttribute(clazz, "Password",
				AttributeType.PASSWORD);
		
		String attributeValue = "123456";
		Map<Attribute, Object> attributesValuesMap = ImmutableMap
				.<Attribute, Object> builder().put(attribute, attributeValue)
				.build();
		Instance instance = createAndAddInstance(clazz, attributesValuesMap);

		String idAttributeValueName = "instance_" + instance.getId()
				+ "_attribute_" + attribute.getId();
		
		setWidget("PasswordAttributeWidget", "class/"+clazz.getFullName(), attribute.getName());
		accessClassWidget(clazz);
		WebElement attributeValueElement = ElementHelper
				.waitAndFindElementById(driver, idAttributeValueName,
						DEFAULT_TIMEOUT);
		assertNotNull("AttributeValue not found: " + attribute.getName(),
				attributeValueElement);
		assertEquals(Strings.repeat("*", attributeValue.length()), attributeValueElement.getText());
		
	}

	private void accessClassWidget(Class clazz) {
		driver.get("http://localhost:8080/lomgui/");

		String idClass = "class_" + clazz.getFullName();
		WebElement clientLi = ElementHelper.waitAndFindElementById(driver,
				idClass, DEFAULT_TIMEOUT);
		clientLi.click();
	}

	private Class createAndAddClass(String nameSpace, String name) {
		Class clazz = new Class();
		clazz.setNamespace(nameSpace);
		clazz.setName(name);
		try {
			HttpResponse response = clazzResource.post(clazz);
			assertEquals(201, response.getStatusLine().getStatusCode());
			clazz = clazzResource.toObject(response);
			classes.add(clazz);
			return clazz;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Attribute createAndAddAttribute(Class clazz, String name,
			AttributeType type) {
		Attribute attribute = new Attribute();
		attribute.setName(name);
		attribute.setType(type);
		try {
			HttpResponse response = attributeResource.post(clazz.getFullName(),
					attribute);
			assertEquals(201, response.getStatusLine().getStatusCode());
			return attributeResource.toObject(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Instance createAndAddInstance(Class clazz,
			Map<Attribute, Object> attributesValuesMap) {
		Instance instance = new Instance();
		instance.setClazz(clazz);
		instance.setValues(attributesValuesFromMap(attributesValuesMap,
				instance));
		try {
			HttpResponse response = instanceResource.post(clazz.getFullName(),
					instance);
			assertEquals(201, response.getStatusLine().getStatusCode());
			return instanceResource.toObject(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<AttributeValue> attributesValuesFromMap(
			Map<Attribute, Object> attributesValuesMap, Instance instance) {
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
