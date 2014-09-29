package rdfapi;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.VCARD;

import java.io.File;
import java.io.InputStream;

/**
 * Task 03: Statement-based query
 *
 * @author elozano
 */
public class Task03 {
    public static String ns = "http://somewhere#";
    public static String foafNS = "http://xmlns.com/foaf/0.1#";
    public static String foafEmailURI = foafNS + "email";
    public static String foafKnowsURI = foafNS + "knows";

    public static void main(String args[]) {
        String filename = "example3.rdf";

        // Create an empty model
        Model model = ModelFactory.createDefaultModel();

        // Use the FileManager to find the input file
        InputStream in = FileManager.get().open("rdf examples" + File.separator + filename);

        if (in == null)
            throw new IllegalArgumentException("File: " + filename + " not found");

        // Read the RDF/XML file
        model.read(in, null);

        // List all the resources with the property "vcard:FN"
        ResIterator rIter = model.listSubjectsWithProperty(VCARD.FN);

        while (rIter.hasNext()) {
            Resource r = rIter.nextResource();
            System.out.println("Subject: " + r.getURI());
        }

        // ** TASK 3.1: List all the resources with the property "vcard:FN" and their full names **
        rIter = model.listSubjectsWithProperty(VCARD.FN);

        while (rIter.hasNext()) {
            Resource r = rIter.nextResource();
            System.out.println("fullmanes: " + r.getProperty(VCARD.FN).getString());
        }

        // ** TASK 3.2: Query all the resources with the family name "Smith" **

        rIter = model.listSubjectsWithProperty(VCARD.Family, "Smith");

        while (rIter.hasNext()) {
            Resource r = rIter.nextResource();
            System.out.println("Subject with name Smith: " + r.getURI());
        }

        // ** TASK 3.3: Query all the resources with an email  **

        rIter = model.listSubjectsWithProperty(VCARD.EMAIL);

        while (rIter.hasNext()) {
            Resource r = rIter.nextResource();
            System.out.println("Subject with name email: " + r.getURI() + " and their email is:" + r.getProperty(VCARD.EMAIL).getString());
        }

        // ** TASK 3.4 (advanced): Query all the subjects knowing "Jane Smith" and list their given names  **

        rIter = model.listSubjectsWithProperty(VCARD.FN, "Jane Smith");

        while (rIter.hasNext()) {
            Resource r = rIter.nextResource();

            ResIterator rIter2 = model.listSubjectsWithProperty(FOAF.knows, r);

            while (rIter2.hasNext()) {
                Resource r2 = rIter2.nextResource();

                System.out.println("Subject who knows Jane Smith: " + r2.getURI() + " and their mane was " + r2.getProperty(VCARD.FN).getString());
            }
        }
    }
}