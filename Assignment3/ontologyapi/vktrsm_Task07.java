package ontologyapi;

import java.io.InputStream;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/**
 * Task 07: Querying ontologies (RDFs)
 * @author elozano
 *
 */
public class Task07
{
	public static String ns = "http://somewhere#";
	
	public static void main(String args[])
	{
		String filename = "rdf examples/example6.rdf";
		
		// Create an empty model
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);
		
		// Use the FileManager to find the input file
		InputStream in = FileManager.get().open(filename);
	
		if (in == null)
			throw new IllegalArgumentException("File: "+filename+" not found");
	
		// Read the RDF/XML file
		model.read(in, null);
		
		
		// ** TASK 7.1: List all individuals of "Person" **
		System.out.println("TASK 7.1 List all individuals of \"Person\"");
		OntClass person = model.getOntClass(ns+"Person");
		ExtendedIterator<? extends OntResource> itr = person.listInstances();
		
		while (itr.hasNext()){
			Individual individual = (Individual) itr.next();
			System.out.println(individual);
		}
		
		// ** TASK 7.2: List all subclasses of "Person" **
		System.out.println("TASK 7.2 List all subclasses of \"Person\"");
		itr = person.listSubClasses();
		
		while (itr.hasNext()) {
			OntClass ontclass = (OntClass) itr.next();
			System.out.println("Subclass: " + ontclass);
		}
		
		// ** TASK 7.3: Make the necessary changes to get as well indirect instances and subclasses. TIP: you need some inference... **
		System.out.println("TASK 7.3: Make the necessary changes to get as well indirect instances and subclasses.");
		/* Not sure of how do it using inference, list methods accepts false to get indirect instances/subclasses*/
		itr = person.listSubClasses(false);
		ExtendedIterator<? extends OntResource> sitr;
		while (itr.hasNext()) {
			OntClass ontclass = (OntClass) itr.next();
			System.out.println("Subclass: " + ontclass);
			sitr = ontclass.listInstances(false);
			while(sitr.hasNext()){
				Individual individual = (Individual) sitr.next();
				System.out.println("  -> " + individual);
			}
		}
	
	}
}
