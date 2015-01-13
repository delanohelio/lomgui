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

import com.nanuvem.lom.api.Entity;
import com.nanuvem.lom.lomgui.resources.EntityResource;
import com.nanuvem.lom.lomgui.resources.Renderer;
import com.nanuvem.lom.lomgui.resources.RendererResource;

public class RootRenderersTest {

	private static final int DEFAULT_TIMEOUT = 10;
	private static WebDriver driver;

	private static EntityResource entityResource;
	private static Entity entity;
	private static RendererResource rootRendererResource;
	
	private final String idEntities = "entities";

	@BeforeClass
	public static void setUp() throws ParseException, IOException {
		driver = new FirefoxDriver();
		entityResource = new EntityResource();
		rootRendererResource = new RendererResource("root");

		entity = new Entity();
		entity.setNamespace("test");
		entity.setName("Client");
		entity = entityResource.toObject(entityResource.post(entity));
	}

	@AfterClass
	public static void tearDown() {
		driver.close();
		entityResource.delete(entity.getId().toString());
	}

	@Test
	public void scenarioTableRootWidget() throws Exception {
		setRootWidget("TableRootRenderer");
		driver.get("http://localhost:8080/lomgui/");
		
		WebElement tableElement = ElementHelper.waitAndFindElementById(driver,
				idEntities, DEFAULT_TIMEOUT);
		assertNotNull("Table Element not found", tableElement);
		assertEquals("tr", tableElement.getTagName());
		
		String idName = "entity_" + entity.getFullName();
		WebElement client = ElementHelper.waitAndFindElementById(driver,
				idName, DEFAULT_TIMEOUT);
		assertEquals(entity.getName(), client.getText());
	}

	@Test
	public void scenarioUlRootWidget() throws Exception {
		setRootWidget("UlRootRenderer");

		driver.get("http://localhost:8080/lomgui/");
		WebElement ulElement = ElementHelper.waitAndFindElementById(driver,
				idEntities, DEFAULT_TIMEOUT);

		assertNotNull("Ul Element not found", ulElement);
		assertEquals("ul", ulElement.getTagName());
		
		String idName = "entity_" + entity.getFullName();
		WebElement client = ElementHelper.waitAndFindElementById(driver,
				idName, DEFAULT_TIMEOUT);
		assertEquals(entity.getName(), client.getText());
		setRootWidget("TableRootRenderer");
	}

	private void setRootWidget(String rendererName) {
		Renderer rootWidget = new Renderer(rendererName, rendererName);
		rootRendererResource.post(rootWidget);
	}

}