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
		
		OntClass person = model.getOntClass(ns+"Person");
		ExtendedIterator<Individual> indIter = model.listIndividuals(person);
		while (indIter.hasNext()) {
			Individual individual = indIter.next();
			System.out.println("Individual: "+individual.getURI());
		}
		
		// ** TASK 7.2: List all subclasses of "Person" **
		
		ExtendedIterator<OntClass> ontIter = person.listSubClasses();
		while (ontIter.hasNext()) {
			OntClass ontclass = ontIter.next();
			System.out.println("Subclass: "+ontclass.getURI());
		}
		
		// ** TASK 7.3: Make the necessary changes to get as well indirect instances and subclasses. TIP: you need some inference... **
		
		ExtendedIterator<OntClass> ontIter2 = person.listSubClasses();
		while (ontIter2.hasNext()) {
			OntClass ontclass = ontIter2.next();
			ExtendedIterator<? extends OntResource> ontIter3 = ontclass.listInstances();
			while (ontIter3.hasNext())
			{
				OntClass individual = (OntClass) ontIter3.next();
				System.out.println("Indirect intance: "+ individual.getURI());
			}
		}
	

	
	}
}
