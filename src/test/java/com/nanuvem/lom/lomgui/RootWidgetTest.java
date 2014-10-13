package com.nanuvem.lom.lomgui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.nanuvem.lom.lomgui.resources.ClassResource;
import com.nanuvem.lom.kernel.Class;

public class RootWidgetTest {

	private static final int DEFAULT_TIMEOUT = 10;
	private static WebDriver driver;

	private static ClassResource clazzResource;
	private static Class clazz;
	
	private final String idClasses = "classes";

	@BeforeClass
	public static void setUp() {
		driver = new FirefoxDriver();
		clazzResource = new ClassResource();

		clazz = new Class();
		clazz.setNamespace("test");
		clazz.setName("Client");
		clazzResource.post(clazz);
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

	/**
	 * TODO try to use resTest
	 */
	private void setRootWidget(String widgetName) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost("http://localhost:8080/lomgui/api/widget/root");
		JSONObject json = new JSONObject();
		try {
			json.put("name", widgetName);
			post.setEntity(new StringEntity(json.toString()));
			post.setHeader("Content-type", "application/json");
			client.execute(post);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}