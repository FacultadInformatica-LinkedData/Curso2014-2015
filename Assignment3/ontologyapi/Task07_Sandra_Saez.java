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
public class Task07_Sandra_Saez
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
		OntClass person = model.getOntClass(ns + "Person");
		ExtendedIterator<Individual> iterator = model.listIndividuals();
		
		while (iterator.hasNext()) {
			Individual indv = iterator.next();
			System.out.println("Person" + indv.getLocalName());
		}
		
		// ** TASK 7.2: List all subclasses of "Person" **
		ExtendedIterator<OntClass> iterator2 = person.listSubClasses();
		
		while (iterator2.hasNext()) {
			OntClass i = iterator2.next();
			System.out.println(i.getLocalName());
		}
		
		// ** TASK 7.3: Make the necessary changes to get as well indirect instances and subclasses. TIP: you need some inference... **
		ExtendedIterator<OntClass> iterator3 = person.listSubClasses();
		
		while (iterator3.hasNext()) {
			OntClass subclass = iterator3.next();
			ExtendedIterator<Individual> instances = (ExtendedIterator<Individual>) subclass.listInstances();
			
			while (instances.hasNext()) {
				Individual i = instances.next();
				System.out.println("Instance: "+ i);
			}
		}
	}
}
