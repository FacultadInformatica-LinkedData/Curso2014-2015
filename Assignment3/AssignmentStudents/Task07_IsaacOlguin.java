package ontologyapi;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.sparql.function.library.e;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/**
 * Task 07: Querying ontologies (RDFs)
 * @author Isaac Olguin
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
		System.out.println("\n\n----------TASK 7.1");
		OntClass person = model.createClass(ns + "Person");
		ExtendedIterator<Individual> extIter = model.listIndividuals();
		while(extIter.hasNext())
		{
			Individual ind = (Individual)extIter.next();
			System.out.println(ind.getURI() + " - " +ind.getLocalName());
		}
		
		
		// ** TASK 7.2: List all subclasses of "Person" **
		System.out.println("\n\n----------TASK 7.2");
		Iterator iter = person.listSubClasses();
		while(iter.hasNext())
		{
			OntClass subClase = (OntClass)iter.next(); 
			System.out.println(subClase.getURI() + " - " + subClase.getLocalName());
		}	
		
		
		// ** TASK 7.3: Make the necessary changes to get as well indirect instances and subclasses. TIP: you need some inference... **
		System.out.println("\n\n----------TASK 7.3");
		/*String inferenciaString = "[(?p rdfs:subClassOf ?o) -> ...]";
		List<Rule> rules = Rule.parseRules(inferenciaString);
		Reasoner reasoner = new GenericRuleReasoner(rules);	*/
		//inconcluso
	}
}
