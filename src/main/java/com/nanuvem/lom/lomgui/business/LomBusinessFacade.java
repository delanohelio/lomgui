package com.nanuvem.lom.lomgui.business;

import java.util.Collection;
import java.util.List;

import com.nanuvem.lom.kernel.Attribute;
import com.nanuvem.lom.kernel.AttributeServiceImpl;
import com.nanuvem.lom.kernel.AttributeType;
import com.nanuvem.lom.kernel.Class;
import com.nanuvem.lom.kernel.ClassServiceImpl;
import com.nanuvem.lom.kernel.Instance;
import com.nanuvem.lom.kernel.InstanceServiceImpl;
import com.nanuvem.lom.kernel.ServiceFactory;
import com.nanuvem.lom.kernel.dao.memory.MemoryDaoFactory;

public class LomBusinessFacade {

	private static LomBusinessFacade singleton;

	public static LomBusinessFacade getInstance() {
		if (LomBusinessFacade.singleton == null) {
			LomBusinessFacade.singleton = new LomBusinessFacade();
		}
		return LomBusinessFacade.singleton;
	}

	private ClassServiceImpl classService;
	private AttributeServiceImpl attributeService;
	private InstanceServiceImpl instanceService;

	private LomBusinessFacade() {
		ServiceFactory serviceFactory = new ServiceFactory(
				new MemoryDaoFactory());
		classService = serviceFactory.getClassService();
		attributeService = serviceFactory.getAttributeService();
		instanceService = serviceFactory.getInstanceService();
		mock();
	}

	private void mock() {
		Class aClass = new Class();
		aClass.setName("Product");
		addClass(aClass);

		Attribute attributeName = new Attribute();
		attributeName.setClazz(aClass);
		attributeName.setName("name");
		attributeName.setType(AttributeType.TEXT);
		addAttribute(attributeName);

	}

	public Class addClass(Class clazz) {
		classService.create(clazz);
		return getClass(clazz.getFullName());
	}

	public Class getClass(String fullname) {
		return classService.readClass(fullname);
	}

	public Class getClass(Long id) {
		return classService.findClassById(id);
	}

	public Collection<Class> getAllClasses() {
		return classService.listAll();
	}

	public boolean removeClass(String fullname) {
		Class clazz = getClass(fullname);
		if (clazz != null) {
			classService.delete(clazz);
			return true;
		}
		return false;
	}

	public boolean removeClass(Long id) {
		Class clazz = getClass(id);
		if (clazz != null) {
			classService.delete(clazz);
			return true;
		}
		return false;
	}

	public Attribute addAttribute(Attribute attribute) {
		attributeService.create(attribute);
		return attributeService.findAttributeByNameAndClassFullName(
				attribute.getName(), attribute.getClazz().getFullName());
	}

	public Attribute getAttribute(Long id) {
		return attributeService.findAttributeById(id);
	}

	public List<Attribute> getAttributesByClassId(Long classId) {
		return getClass(classId).getAttributes();
	}
	
	public List<Attribute> getAttributesByClassFullName(String classFullName) {
		return getClass(classFullName).getAttributes();
	}

	public boolean removeAttribute(Long id) {
		//TODO waiting kernel method
		return false;
	}

	public Instance addInstance(Instance instance) {
		instanceService.create(instance);
		return instance;
	}

	public Instance getInstance(Long id) {
		return instanceService.findInstanceById(id);
	}

	public List<Instance> getInstancesByClassID(Long classID) {
		return getClass(classID).getInstances();
	}
	
	public List<Instance> getInstancesByClassFullName(String classFullName) {
		return getClass(classFullName).getInstances();
	}

	public boolean removeInstance(Long id) {
		//TODO waiting kernel method
		return false;
	}

}