//Alberto Minguito - s100095
package ontologyapi;

import java.io.InputStream;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/**
 * Task 07: Querying ontologies (RDFs)
 * @author elozano
 *
 */
public class Task07_AlbertoMinguito
{
	public static String ns = "http://somewhere#";
	
	public static void main(String args[])
	{
		String filename = "src/examples/example6.rdf";
		
		// Create an empty model
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);
		
		// Use the FileManager to find the input file
		InputStream in = FileManager.get().open(filename);
	
		if (in == null)
			throw new IllegalArgumentException("File: "+filename+" not found");
	
		// Read the RDF/XML file
		model.read(in, null);
		
		
		// ** TASK 7.1: List all individuals of "Person" **
		ExtendedIterator<Individual> personIterator = model.listIndividuals(model.getOntClass(ns+"Person"));
		while(personIterator.hasNext()){
			Individual individual = personIterator.next();
			System.out.println("Individual: "+individual);
		}
		
		// ** TASK 7.2: List all subclasses of "Person" **
		ExtendedIterator<OntClass> subclassIterator = model.getOntClass(ns+"Person").listSubClasses();
		while(subclassIterator.hasNext()){
			OntClass subclass = subclassIterator.next();
			System.out.println("Subclass: "+subclass);
		}
		
		// ** TASK 7.3: Make the necessary changes to get as well indirect instances and subclasses. TIP: you need some inference... **
		
	
	}
}

