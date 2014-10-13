package com.nanuvem.lom.lomgui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

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
	private static Class clazz;
	
	private static AttributeResource attributeResource;
	private static Attribute nameAttribute;
	
	@BeforeClass
	public static void setUp() throws ParseException, IOException {
		driver = new FirefoxDriver();
		
		clazzResource = new ClassResource();
		clazz = new Class();
		clazz.setNamespace("test");
		clazz.setName("Client");
		clazzResource.post(clazz);
		
		attributeResource = new AttributeResource();
		nameAttribute = new Attribute();
		nameAttribute.setName("name");
		nameAttribute.setType(AttributeType.TEXT);
		nameAttribute = attributeResource.postEntity(clazz.getFullName(), nameAttribute);
	}

	@AfterClass
	public static void tearDown() {
		driver.close();
	}
	
	//TODO test changing class attributes and changing class widgets
	//TODO test several class widget hooks 
	
	@Test
	public void scenarioClassWidget() {
		driver.get("http://localhost:8080/lomgui/");

		String idClass = "class_" + clazz.getFullName();
		WebElement clientLi = ElementHelper.waitAndFindElementById(driver,
				idClass, DEFAULT_TIMEOUT);
		clientLi.click();
		
		String idAttributeName = "attribute_" + nameAttribute.getId();
		WebElement attributeElement = ElementHelper.waitAndFindElementById(driver,
				idAttributeName, DEFAULT_TIMEOUT);
		
		assertNotNull("Name Attribute not found", clientLi);
		assertEquals(nameAttribute.getName(), attributeElement.getText());
		
	}

}
