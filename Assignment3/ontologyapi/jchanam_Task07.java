package ontologyapi;

import java.io.InputStream;
import java.util.Iterator;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.impl.OntClassImpl;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerFactory;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.sparql.function.Function;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

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
		ExtendedIterator<OntClass> clases = personClass.listSubClasses();
		while (clases.hasNext()){
			OntClass clase = clases.next();
			System.out.println(clase.getURI()+" es subclase de "+personClass.getURI());
		}
		
		// ** TASK 7.3: Make the necessary changes to get as well indirect instances and subclasses. TIP: you need some inference... **
		Reasoner razonador = ReasonerRegistry.getTransitiveReasoner();
		InfModel inferencia = ModelFactory.createInfModel(razonador, model);
		inferencia.prepare();
		ExtendedIterator<Resource> clases2 = inferencia.listSubjectsWithProperty(RDFS.subClassOf, ns+"Person");
		//OntClass personClass2 = model.getOntClass(ns+"Person");
		//ExtendedIterator<OntClass> clases2 = personClass2.listSubClasses();
		while (clases2.hasNext()){
			Resource clase = clases2.next();
			System.out.println(clase.getURI()+" es subclase de "+personClass.getURI());
		}
	}
}
