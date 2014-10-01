package ontologyapi;

import java.io.InputStream;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/**
 * Task 07: Querying ontologies (RDFs)
 * @author Nestor Lopez Rivas
 *
 */
public class Task07_Nestor_Lopez
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
		System.out.println("\nTASK 7.1 - List all individuals of Person\n");
		OntClass person = model.getOntClass(ns+"Person");
		ExtendedIterator<? extends OntResource> itr = person.listInstances();
		while(itr.hasNext()){
			Individual persons = (Individual) itr.next();
			System.out.println("Person : " + persons);	
		}
		// ** TASK 7.2: List all subclasses of "Person" **
		System.out.println("\nTASK 7.2 - List all subclasses of Person\n");
		itr = person.listSubClasses(false);
		while (itr.hasNext()) {
			OntClass ontClass = (OntClass) itr.next();
			System.out.println("Subclass : " + ontClass);
		}

		// ** TASK 7.3: Make the necessary changes to get as well indirect instances and subclasses. ** 
		// ** TIP: you need some inference... **
		System.out.println("\nTASK 7.3 - Make the necessary changes to get as well indirect instances and subclasses\n");
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		reasoner = reasoner.bindSchema(model);
		reasoner.setDerivationLogging(true);
		InfModel inf = ModelFactory.createInfModel(reasoner, model);
		StmtIterator i = inf.listStatements(person, null, (RDFNode) null);
		while(i.hasNext()){
			Statement s = i.nextStatement();
			System.out.println("Statement: \n" + s.asTriple()); //turtle format
		}
	}
}