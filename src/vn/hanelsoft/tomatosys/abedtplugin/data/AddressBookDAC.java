package vn.hanelsoft.tomatosys.abedtplugin.data;

import model.*;
import model.impl.ModelPackageImpl;

import org.eclipse.emf.common.util.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreAdapterFactory;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import java.io.IOException;
import java.util.*;

public class AddressBookDAC {
	public static Group LoadRootGroup(String pathName) {
		// Initialize the model
		ModelPackage.eINSTANCE.eClass();
		// As of here we preparing to save the model content
		// Register the XMI resource factory for the .ab extension
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("addressbook", new XMIResourceFactoryImpl());
		// Obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();
		// create a resource
		Resource resource = resSet.getResource(URI.createFileURI(pathName),
				true);
		// Get the first model element and cast it to the right type, in my
		// example everything is hierarchical included in this first node
		Group group = (Group) resource.getContents().get(0);
		return group;
	}

	public static void SaveRootGroup(String pathName, Group group) {
		ModelPackage.eINSTANCE.eClass();
		// Retrieve the default factory singleton
		ModelFactory factory = ModelFactory.eINSTANCE;
		// create an instance of myWeb

		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("addressbook", new XMIResourceFactoryImpl());

		// Obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();

		// create a resource
		Resource resource = resSet.createResource(URI.createFileURI(pathName));
		// ClassLoader.getSystemResourceAsStream()
		// Get the first model element and cast it to the right type, in my
		// example everything is hierarchical included in this first node
		resource.getContents().add(group);
		try {
			resource.save(Collections.EMPTY_MAP);
			// OutputStream os = new FileOutputStream("c:/test.ab");
			// resource.save(os, m);
			// os.flush();
			// os.close();
			//
			// /**
			// * Closes this output stream and releases any system resources
			// * associated with this stream.
			// */

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Person> ListAllPersons() {
		List<Person> PersonList = new ArrayList<Person>();
		// Initialize the model
		ModelPackage.eINSTANCE.eClass();

		// As of here we preparing to save the model content

		// Register the XMI resource factory for the .ab extension

		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("addressbook", new XMIResourceFactoryImpl());

		// Obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();

		// create a resource
		Resource resource = resSet.getResource(
				URI.createURI("AddressBook/mine.ab"), true);
		// Get the first model element and cast it to the right type, in my
		// example everything is hierarchical included in this first node

		EList<EObject> list = resource.getContents();
		for (EObject temp : list) {

			Person person = (Person) temp;
			PersonList.add(person);
		}
		return PersonList;
	}

	public static void TestPersit(String pathName) {
		ModelPackage.eINSTANCE.eClass();

		// Retrieve the default factory singleton
		ModelFactory factory = ModelFactory.eINSTANCE;
		// create an instance of myWeb
		Group groupRootHSS = factory.createGroup();
		Group groupJava = factory.createGroup();
		Group groupMobile = factory.createGroup();
		groupMobile.setName("Mobile");
		Person personMinh = factory.createPerson();
		Person personThang = factory.createPerson();
		Person personLuong = factory.createPerson();
		personLuong.setName("Luong");
		personLuong.setEmail("luong@yahoo.com");
		personThang.setName("Thang");
		personThang.setEmail("thangma@gmail.com");
		groupRootHSS.setName("CMC");
		groupJava.setName("Java");
		groupJava.getEntries().add(personThang);
		groupMobile.getEntries().add(personLuong);
		groupJava.getEntries().add(groupMobile);
		personMinh.setName("Minh");
		personMinh.setEmail("minh@hss.com.vn");
		personMinh.setID(EcoreUtil.generateUUID());

		groupRootHSS.getEntries().add(groupJava);
		groupRootHSS.getEntries().add(personMinh);

		// As of here we preparing to save the model content

		// Register the XMI resource factory for the .website extension

		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("addressbook", new XMIResourceFactoryImpl());

		// Obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();

		// create a resource
		Resource resource = resSet.createResource(URI.createFileURI(pathName));
		// ClassLoader.getSystemResourceAsStream()
		// Get the first model element and cast it to the right type, in my
		// example everything is hierarchical included in this first node
		resource.getContents().add(groupRootHSS);
		try {
			resource.save(Collections.EMPTY_MAP);

			// OutputStream os = new FileOutputStream("c:/test.ab");
			// resource.save(os, m);
			// os.flush();
			// os.close();
			//
			// /**
			// * Closes this output stream and releases any system resources
			// * associated with this stream.
			// */

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
