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

import com.nanuvem.lom.kernel.Class;
import com.nanuvem.lom.lomgui.resources.ClassResource;
import com.nanuvem.lom.lomgui.resources.Widget;
import com.nanuvem.lom.lomgui.resources.WidgetResource;

public class RootWidgetTest {

	private static final int DEFAULT_TIMEOUT = 10;
	private static WebDriver driver;

	private static ClassResource clazzResource;
	private static Class clazz;
	private static WidgetResource rootWidgetResource;
	
	private final String idClasses = "classes";

	@BeforeClass
	public static void setUp() throws ParseException, IOException {
		driver = new FirefoxDriver();
		clazzResource = new ClassResource();
		rootWidgetResource = new WidgetResource("root");

		clazz = new Class();
		clazz.setNamespace("test");
		clazz.setName("Client");
		clazz = clazzResource.toObject(clazzResource.post(clazz));
	}

	@AfterClass
	public static void tearDown() {
		driver.close();
		clazzResource.delete(clazz.getId().toString());
	}

	@Test
	public void scenarioTableRootWidget() throws Exception {
		setRootWidget("TableRootWidget");
		driver.get("http://localhost:8080/lomgui/");
		
		WebElement tableElement = ElementHelper.waitAndFindElementById(driver,
				idClasses, DEFAULT_TIMEOUT);
		assertNotNull("Table Element not found", tableElement);
		assertEquals("tr", tableElement.getTagName());
		
		String idName = "class_" + clazz.getFullName();
		WebElement client = ElementHelper.waitAndFindElementById(driver,
				idName, DEFAULT_TIMEOUT);
		assertEquals(clazz.getName(), client.getText());
	}

	@Test
	public void scenarioUlRootWidget() throws Exception {
		setRootWidget("UlRootWidget");

		driver.get("http://localhost:8080/lomgui/");
		WebElement ulElement = ElementHelper.waitAndFindElementById(driver,
				idClasses, DEFAULT_TIMEOUT);

		assertNotNull("Ul Element not found", ulElement);
		assertEquals("ul", ulElement.getTagName());
		
		String idName = "class_" + clazz.getFullName();
		WebElement client = ElementHelper.waitAndFindElementById(driver,
				idName, DEFAULT_TIMEOUT);
		assertEquals(clazz.getName(), client.getText());
	}

	private void setRootWidget(String widgetName) {
		Widget rootWidget = new Widget();
		rootWidget.setName(widgetName);
		rootWidgetResource.post(rootWidget);
	}

}