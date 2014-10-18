package com.nanuvem.lom.lomgui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.nanuvem.lom.kernel.Attribute;
import com.nanuvem.lom.kernel.AttributeType;
import com.nanuvem.lom.kernel.Class;
import com.nanuvem.lom.lomgui.resources.AttributeResource;
import com.nanuvem.lom.lomgui.resources.ClassResource;

public class ClassWidgetTest {
	
	private static final int DEFAULT_TIMEOUT = 10;
	private static WebDriver driver;
	
	private static ClassResource clazzResource;
	private static Class clazz1;
	private static Class clazz2;
	
	private static AttributeResource attributeResource;
	
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
	}

	@AfterClass
	public static void tearDown() {
		driver.close();
	}
	
	//TODO test changing class attributes and changing class widgets
	//TODO test several class widget hooks 
	
	@Test
	public void scenarioCheckAddAttribute() {
		Attribute nameAttribute = createAndAddAttribute(clazz1, "name", AttributeType.TEXT);
		
		accessClassWidget(clazz1);
		
		String idAttributeName = "attribute_" + nameAttribute.getId();
		WebElement attributeElement = ElementHelper.waitAndFindElementById(driver,
				idAttributeName, DEFAULT_TIMEOUT);
		
		assertNotNull("Attribute not found: " + nameAttribute.getName(), attributeElement);
		assertEquals(nameAttribute.getName(), attributeElement.getText());
		
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
	public void scenarioChangeAttribute() {
		Attribute aAttribute = createAndAddAttribute(clazz1, "lastname", AttributeType.TEXT);
		accessClassWidget(clazz1);
		String idAttribute = "attribute_" + aAttribute.getId();
		WebElement attributeElement = ElementHelper.waitAndFindElementById(driver,
				idAttribute, DEFAULT_TIMEOUT);
		assertNotNull("Attribute not found: " + aAttribute.getName(), attributeElement);
		assertEquals(aAttribute.getName(), attributeElement.getText());
		
		aAttribute.setName("surname");
		attributeResource.put(clazz1.getFullName(), aAttribute.getId().toString(), aAttribute);
		accessClassWidget(clazz1);
		attributeElement = ElementHelper.waitAndFindElementById(driver,
				idAttribute, DEFAULT_TIMEOUT);
		assertNotNull("Attribute not found: " + aAttribute.getName(), attributeElement);
		assertEquals(aAttribute.getName(), attributeElement.getText());
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
			return attributeResource.toObject(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
