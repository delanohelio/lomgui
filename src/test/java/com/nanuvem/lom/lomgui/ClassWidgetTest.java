package com.nanuvem.lom.lomgui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
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

import com.google.common.collect.ImmutableMap;
import com.nanuvem.lom.kernel.Attribute;
import com.nanuvem.lom.kernel.AttributeType;
import com.nanuvem.lom.kernel.AttributeValue;
import com.nanuvem.lom.kernel.Class;
import com.nanuvem.lom.kernel.Instance;
import com.nanuvem.lom.lomgui.resources.AttributeResource;
import com.nanuvem.lom.lomgui.resources.ClassResource;
import com.nanuvem.lom.lomgui.resources.InstanceResource;

public class ClassWidgetTest {
	
	private static final int DEFAULT_TIMEOUT = 10;
	private static WebDriver driver;
	
	private static ClassResource clazzResource;
	private static Class clazz1;
	private static Class clazz2;
	
	private static AttributeResource attributeResource;
	private static InstanceResource instanceResource;
	
	@BeforeClass
	public static void setUp() throws ParseException, IOException {
		driver = new FirefoxDriver();
		
		clazzResource = new ClassResource();
		clazz1 = new Class();
		clazz1.setNamespace("test");
		clazz1.setName("Client");
		clazzResource.post(clazz1);

		clazz2 = new Class();
		clazz2.setNamespace("test");
		clazz2.setName("Product");
		clazzResource.post(clazz2);
		
		attributeResource = new AttributeResource();
		instanceResource = new InstanceResource();
	}

	@AfterClass
	public static void tearDown() {
		driver.close();
	}
	
	//TODO test changing class attributes and changing class widgets
	//TODO test several class widget hooks 
	
	@Test
	public void scenarioCheckAddAttributeAndInstance() {
		//Attribute
		Attribute nameAttribute = createAndAddAttribute(clazz1, "name", AttributeType.TEXT);
		
		accessClassWidget(clazz1);
		
		String idAttributeName = "attribute_" + nameAttribute.getId();
		WebElement attributeElement = ElementHelper.waitAndFindElementById(driver,
				idAttributeName, DEFAULT_TIMEOUT);
		
		assertNotNull("Attribute not found: " + nameAttribute.getName(), attributeElement);
		assertEquals(nameAttribute.getName(), attributeElement.getText());
		
		//Instance
		
		String nameValue = "Delano";
		Map<Attribute, Object> attributesValuesMap = ImmutableMap.<Attribute, Object>builder()
				.put(nameAttribute, nameValue)
				.build();
		Instance nameInstance = createAndAddInstance(clazz1, attributesValuesMap);
		
		accessClassWidget(clazz1);
		
		String idAttributeValueName = "instance_" + nameInstance.getId() + "_attribute_" + nameAttribute.getId();
		WebElement attributeValueElement = ElementHelper.waitAndFindElementById(driver,
				idAttributeValueName, DEFAULT_TIMEOUT);
		
		assertNotNull("AttributeValue not found: " + nameAttribute.getName(), attributeValueElement);
		assertEquals(nameValue, attributeValueElement.getText());
		
	}
	
	@Test
	public void scenarioChangeAttributeWithWrongClass() throws ParseException, IOException {
		String attributeName = "quantity";
		Attribute aAttribute = createAndAddAttribute(clazz2, attributeName, AttributeType.INTEGER);
		accessClassWidget(clazz2);
		String idAttribute = "attribute_" + aAttribute.getId();
		WebElement attributeElement = ElementHelper.waitAndFindElementById(driver,
				idAttribute, DEFAULT_TIMEOUT);
		assertNotNull("Attribute not found: " + aAttribute.getName(), attributeElement);
		assertEquals(aAttribute.getName(), attributeElement.getText());
		
		String editedAttributeName = "quant";
		aAttribute.setName(editedAttributeName);
		Class wrongClass = clazz1;
		attributeResource.put(wrongClass.getFullName(), aAttribute.getId().toString(), aAttribute);
		accessClassWidget(clazz2);
		attributeElement = ElementHelper.waitAndFindElementById(driver,
				idAttribute, DEFAULT_TIMEOUT);
		assertNotNull("Attribute not found: " + aAttribute.getName(), attributeElement);
		assertEquals(attributeName, attributeElement.getText());
		
	}
	
	@Test
	public void scenarioChangeAttributeAndValue() throws ParseException, IOException {
		//Attribute
		Attribute aAttribute = createAndAddAttribute(clazz1, "surname", AttributeType.TEXT);
		accessClassWidget(clazz1);
		String idAttribute = "attribute_" + aAttribute.getId();
		WebElement attributeElement = ElementHelper.waitAndFindElementById(driver,
				idAttribute, DEFAULT_TIMEOUT);
		assertNotNull("Attribute not found: " + aAttribute.getName(), attributeElement);
		assertEquals(aAttribute.getName(), attributeElement.getText());
		
		aAttribute.setName("lastname");
		aAttribute = attributeResource.toObject(attributeResource.put(clazz1.getFullName(), aAttribute.getId().toString(), aAttribute));
		accessClassWidget(clazz1);
		attributeElement = ElementHelper.waitAndFindElementById(driver,
				idAttribute, DEFAULT_TIMEOUT);
		assertNotNull("Attribute not found: " + aAttribute.getName(), attributeElement);
		assertEquals(aAttribute.getName(), attributeElement.getText());
		
		//Instance
		
	}
	
	private void accessClassWidget(Class clazz) {
		driver.get("http://localhost:8080/lomgui/");

		String idClass = "class_" + clazz.getFullName();
		WebElement clientLi = ElementHelper.waitAndFindElementById(driver,
				idClass, DEFAULT_TIMEOUT);
		clientLi.click();
	}
	
	private Attribute createAndAddAttribute(Class clazz, String name, AttributeType type) {
		Attribute attribute = new Attribute();
		attribute.setName(name);
		attribute.setType(type);
		try {
			HttpResponse response = attributeResource.post(clazz.getFullName(), attribute);
			assertEquals(201, response.getStatusLine().getStatusCode());
			return attributeResource.toObject(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Instance createAndAddInstance(Class clazz, Map<Attribute, Object> attributesValuesMap) {
		Instance instance = new Instance();
		instance.setClazz(clazz);
		instance.setValues(attributesValuesFromMap(attributesValuesMap, instance));
		try {
			HttpResponse response = instanceResource.post(clazz.getFullName(), instance);
			assertEquals(201, response.getStatusLine().getStatusCode());
			return instanceResource.toObject(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private List<AttributeValue> attributesValuesFromMap(Map<Attribute, Object> attributesValuesMap, Instance instance) {
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

}
