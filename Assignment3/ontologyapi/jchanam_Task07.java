package ontologyapi;

import java.io.InputStream;
import java.util.Iterator;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.function.Function;
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
		String filename = "src/rdf examples/example6.rdf";
		
		// Create an empty model
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);
		
		// Use the FileManager to find the input file
		InputStream in = FileManager.get().open(filename);
	
		if (in == null)
			throw new IllegalArgumentException("File: "+filename+" not found");
	
		// Read the RDF/XML file
		model.read(in, null);
		
		
		// ** TASK 7.1: List all individuals of "Person" **
		Resource person = model.getResource(ns+"Person");
		ExtendedIterator<Individual> personas = model.listIndividuals(person);
		while (personas.hasNext()){
			System.out.println("Persona: " + personas.next().getURI());
		}
		
		// ** TASK 7.2: List all subclasses of "Person" **
		OntClass personClass = model.getOntClass(ns+"Person");
		ExtendedIterator<OntClass> clases = model.listClasses();
		while (clases.hasNext()){
			OntClass clase = clases.next();
			OntClass superclase = clase.getSuperClass();
			if (superclase != null && superclase.getURI().equals(personClass.getURI())){
				System.out.println(clase.getURI()+" es subclase de "+personClass.getURI());
			}
		}
		
		// ** TASK 7.3: Make the necessary changes to get as well indirect instances and subclasses. TIP: you need some inference... **
		
	}
}
