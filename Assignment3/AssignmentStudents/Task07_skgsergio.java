package ontologyapi;

import java.io.InputStream;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.io.File;

/**
 * Task 07: Querying ontologies (RDFs)
 */
public class Task07 {

    public static String ns = "http://somewhere#";

    public static void main(String args[]) {
        String filename = "rdf examples" + File.separator + "example6.rdf";

        // Create an empty model
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);

        // Use the FileManager to find the input file
        InputStream in = FileManager.get().open(filename);

        if (in == null) {
            throw new IllegalArgumentException("File: " + filename + " not found");
        }

        // Read the RDF/XML file
        model.read(in, null);

        ExtendedIterator<? extends OntResource> it; // "? extends OntResource" Java go home you are drunk.
        OntClass person = model.getOntClass(ns + "Person");
        
        // ** TASK 7.1: List all individuals of "Person" **
        System.out.println("Person instances: ");
        it = person.listInstances(); 

        while (it.hasNext()) {
            System.out.println(it.next());
        }

	// ** TASK 7.2: List all subclasses of "Person" **
        System.out.println("Person subclasses: ");
        it = person.listSubClasses();
        
        while (it.hasNext()) {
            System.out.println(it.next());
        }

	// ** TASK 7.3: Make the necessary changes to get as well indirect instances and subclasses. TIP: you need some inference... **
        Reasoner reasoner = ReasonerRegistry.getRDFSReasoner();
        reasoner = reasoner.bindSchema(model);
        
        OntModelSpec ontModelSpec = OntModelSpec.RDFS_MEM;
        ontModelSpec.setReasoner(reasoner);
        
        OntModel ontModel = ModelFactory.createOntologyModel(ontModelSpec, model);
        OntClass infPerson = ontModel.getOntClass(ns + "Person");
        
        System.out.println("Person instances: ");
        it = infPerson.listInstances(); 

        while (it.hasNext()) {
            System.out.println(it.next());
        }

        System.out.println("Person subclasses: ");
        it = infPerson.listSubClasses();
        
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
