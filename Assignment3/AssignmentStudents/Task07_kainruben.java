package ontologyapi;

import java.io.InputStream;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.VCARD;

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
		String filename = "example6.rdf";
		
		// Create an empty model
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);
		
		// Use the FileManager to find the input file
		InputStream in = FileManager.get().open(filename);
	
		if (in == null)
			throw new IllegalArgumentException("File: "+filename+" not found");
	
		// Read the RDF/XML file
		model.read(in, null);
		
		
		// ** TASK 7.1: List all individuals of "Person" **
		System.out.println("TASK 7.1");
		
		
		OntClass person = model.getOntClass(ns + "Person");
		ExtendedIterator<? extends OntResource> eIter = person.listInstances();
				
		while (eIter.hasNext()) 
		{
			Individual i = (Individual) eIter.next();
			System.out.println("Individual: " +i);
		}
		
		System.out.println("");
		
		
		
		// ** TASK 7.2: List all subclasses of "Person" **
		System.out.println("TASK 7.2");
		OntClass person2 = model.getOntClass(ns + "Person");
		ExtendedIterator<OntClass> eIter2=person.listSubClasses();
		
		while (eIter2.hasNext()) 
		{
			Resource s = eIter2.next();
			System.out.println("Subclass: " +s);
		}
				
		System.out.println("");
		
		
		
		// ** TASK 7.3: Make the necessary changes to get as well indirect instances and subclasses. TIP: you need some inference... **
		System.out.println("TASK 7.3");
		//no yet
	}
}
