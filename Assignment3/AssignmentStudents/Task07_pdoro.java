package ontologyapi;

import java.io.InputStream;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class Task07 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String filename = "example7.rdf";
		String namespace = "www.example.org/resources#";
		
		// Create an empty model
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);

        // Use the FileManager to find the input file
        InputStream in = FileManager.get().open(filename);

        if (in == null)
            throw new IllegalArgumentException("File: " + filename + " not found");

        // Read the RDF/XML file
        model.read(in, null);
		
		// Task 7.1: List all individuals of "Person"
		OntClass personClass = model.getOntClass(namespace + "Person");
		
		ExtendedIterator<? extends OntResource> personInstances = personClass.listInstances();
		OntResource person;
		while(personInstances.hasNext()) {
			person = personInstances.next();
			System.out.println("Person Instance: " + person.getURI());
		}
		
		// Task 7.1: List all subclasses of "Person"
		ExtendedIterator<? extends OntResource> subclassesOfPerson = personClass.listSubClasses();
		OntResource personSubclass;
		while(subclassesOfPerson.hasNext()) {
			personSubclass = subclassesOfPerson.next();
			System.out.println("Person Subclasse: " + personSubclass.getURI());
		}
		
		// Task 7.2: Make the necessary changes to get as well indirect instances and subclasses
		ExtendedIterator<? extends OntResource> indirectPersons = personClass.listInstances(false);
		OntResource indirectPerson;
		
		while(indirectPersons.hasNext()) {
			indirectPerson = indirectPersons.next();
			if( !indirectPerson.hasRDFType(personClass, true) )
				System.out.println("Person Indirect Intance: " + indirectPerson.getURI());
		}
		
		/* ------------------------------------------------ */
		
		ExtendedIterator<? extends OntResource> indirectSubclassesOfPerson = personClass.listSubClasses(false);
		OntResource indirectPersonSubclass;
		
		while(indirectSubclassesOfPerson.hasNext()) {
			indirectPersonSubclass = indirectSubclassesOfPerson.next();
			if( !indirectPersonSubclass.asClass().isSameAs(personClass) )
				System.out.println("Person Indirect Subclass: " + indirectPersonSubclass.getURI());
		}
	}
}
