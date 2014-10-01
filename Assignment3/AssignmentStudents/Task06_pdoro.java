package ontologyapi;

import java.io.InputStream;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.VCARD;

public class Task06 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String filename = "example6.rdf";
		String namespace = "www.example.org/resources#";
		
		// Create an empty model
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);

        // Use the FileManager to find the input file
        InputStream in = FileManager.get().open(filename);

        if (in == null)
            throw new IllegalArgumentException("File: " + filename + " not found");

        // Read the RDF/XML file
        model.read(in, null);
        
        // Task 6.1: Create a new class named "Researcher"
        //			 Create a new class named "University"
        OntClass researcher = model.createClass(namespace + "Researcher");
        OntClass university = model.createClass(namespace + "University");
        
        // Task 6.2: Add "Researcher" as a subclass of "Person"
        model.getOntClass(namespace + "Person").addSubClass(researcher);
        
        // Task 6.3: Create a new property named "worksIn"
        Property worksIn = model.createProperty(namespace + "worksIn");
        
        // Task 6.4: Create a new individual of Researcher named "Jane Smith"
        Individual janeSmith = researcher.createIndividual(namespace + "JaneSmith");
        
        // Task 6.5: Add to the individual JaneSmith the fullName, given and family names
        janeSmith.addProperty(VCARD.FN, 	"Jane Smith")
        		 .addProperty(VCARD.Given, 	"Smith")
        		 .addProperty(VCARD.Family, "Smith");
        
        // Task 6.6: Add UPM as the university where John Smith works
        Individual UPM = university.createIndividual(namespace + "UPM");
        model.getIndividual(namespace + "JohnSmith").addProperty(worksIn, UPM);
        
        // Print model to stdout
        model.write(System.out, "RDF/XML-ABBREV");
	}
}
