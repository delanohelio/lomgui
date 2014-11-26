package com.nanuvem.lom.lomgui.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.nanuvem.lom.api.Attribute;
import com.nanuvem.lom.api.AttributeValue;
import com.nanuvem.lom.api.Entity;
import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.Instance;
import com.nanuvem.lom.kernel.KernelFacade;
import com.nanuvem.lom.kernel.dao.MemoryDaoFactory;

public class LomBusinessFacade {

	private static LomBusinessFacade singleton;

	public static LomBusinessFacade getInstance() {
		if (LomBusinessFacade.singleton == null) {
			LomBusinessFacade.singleton = new LomBusinessFacade();
		}
		return LomBusinessFacade.singleton;
	}

	private Facade facade;

	private LomBusinessFacade() {
		facade = new KernelFacade(new MemoryDaoFactory());
	}

	public Entity addEntity(Entity clazz) {
		facade.create(clazz);
		return getEntity(clazz.getFullName());
	}

	public Entity getEntity(String fullname) {
		return facade.findEntityByFullName(fullname);
	}

	public Entity getEntity(Long id) {
		return facade.findEntityById(id);
	}

	public Collection<Entity> getAllEntityes() {
		return facade.listAllEntities();
	}

	public void removeEntity(Long id) {
		facade.deleteEntity(id);
	}

	public Attribute addAttribute(Attribute attribute) {
		facade.create(attribute);
		return facade.findAttributeByNameAndEntityFullName(
				attribute.getName(), attribute.getEntity().getFullName());
	}

	public Attribute getAttribute(Long id) {
		return facade.findAttributeById(id);
	}

	public List<Attribute> getAttributesByEntityId(Long classId) {
		return getEntity(classId).getAttributes();
	}
	
	public List<Attribute> getAttributesByEntityFullName(String classFullName) {
		return getEntity(classFullName).getAttributes();
	}
	
	public Attribute updateAttribute(Attribute attribute) {
		return facade.update(attribute);
	}

	public boolean removeAttribute(Long id) {
		//TODO waiting kernel method
		return false;
	}

	public Instance addInstance(Instance instance) {
		completeInstance(instance);
		return facade.create(instance);
	}

	public Instance getInstance(Long id) {
		return facade.findInstanceById(id);
	}

	public List<Instance> getInstancesByEntityID(Long classID) {
		return getEntity(classID).getInstances();
	}
	
	public List<Instance> getInstancesByEntityFullName(String classFullName) {
		return getEntity(classFullName).getInstances();
	}
	
	public Instance updateInstance(Instance instance) {
		//TODO waiting kernel method
		return null;
	}

	public boolean removeInstance(Long id) {
		//TODO waiting kernel method
		return false;
	}
	
	private void completeInstance(Instance instance) {
		List<Attribute> attributes = instance.getEntity().getAttributes();
		AttributeValue[] attributesValues = new AttributeValue[attributes.size()];
		for(AttributeValue attributeValue : instance.getValues()){
			attributes.remove(attributeValue.getAttribute());
			attributesValues[attributeValue.getAttribute().getSequence()-1] =  attributeValue;
		}
		for(Attribute attribute : attributes){
			AttributeValue attributeValue = new AttributeValue();
			attributeValue.setAttribute(attribute);
			attributesValues[attributeValue.getAttribute().getSequence()-1] =  attributeValue;
		}
		instance.setValues(new ArrayList<AttributeValue>(Arrays.asList(attributesValues)));
	}


}