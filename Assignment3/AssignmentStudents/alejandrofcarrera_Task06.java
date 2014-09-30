
import java.io.*;

import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.*;
import com.hp.hpl.jena.vocabulary.*;

/** @author alejandrofcarrera */

public class alejandrofcarrera_Task06
{
	private static String ns = "http://somewhere#";
	private static String path = "docs/example5.rdf"; // fine for this Task
	
	public static void main(String args[])
	{
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);
		InputStream in = FileManager.get().open(path);
		if (in == null)
		{
			throw new IllegalArgumentException("File: "+path+" not found or ilegible");
		}	
		model.read(in, null);
		//model.write(System.out, "Turtle");
		
		// 6.1
		System.out.println(" * TASK 6.1:");
		System.out.print(" * Create class \"University\": ");
		try
		{
			model.createClass(ns+"University");
			System.out.println("Done");
		}
		catch(Exception e)
		{
			System.out.println("Failed");
		}
		
		// 6.2
		System.out.println(" * TASK 6.2:");
		System.out.print(" * Create subclass \"Researcher\" to \"Person\": ");
		try
		{
			model.createClass(ns+"Researcher");
			model.getOntClass(ns+"Person").addSubClass(model.
					createClass(ns+"Researcher"));
			System.out.println("Done");
		}
		catch(Exception e)
		{
			System.out.println("Failed");
		}
			
		// 6.3
		System.out.println(" * TASK 6.3:");
		System.out.print(" * Create property \"worksIn\": ");
		try
		{
			model.createProperty(ns + "worksIn");
			System.out.println("Done");
		}
		catch(Exception e)
		{
			System.out.println("Failed");
		}
		
		// 6.4
		System.out.println(" * TASK 6.4:");
		System.out.print(" * Create individual to \"Researcher\" named \"JaneSmith\": ");
		try
		{
			model.getOntClass(ns+"Researcher").createIndividual(ns + "JaneSmith");
			System.out.println("Done");
		}
		catch(Exception e)
		{
			System.out.println("Failed");
		}
		
		// 6.5
		System.out.println(" * TASK 6.5:");
		System.out.print(" * Fill \"JaneSmith\" properties: ");
		try
		{
			model.getIndividual(ns + "JaneSmith").addProperty(VCARD.FN, "Jane Smith");
			model.getIndividual(ns + "JaneSmith").addProperty(VCARD.Given, "Jane");
			model.getIndividual(ns + "JaneSmith").addProperty(VCARD.Family, "Smith");
			System.out.println("Done");
		}
		catch(Exception e)
		{
			System.out.println("Failed");
		}
		
		// 6.6
		System.out.println(" * TASK 6.6:");
		System.out.print(" * Create worksIn property for \"JohnSmith\": ");
		try
		{
			model.getIndividual(ns + "JohnSmith").addProperty(model.
					getProperty(ns+"worksIn"),
					model.getOntClass(ns+"University").createIndividual(ns+"UPM"));
			System.out.println("Done");
		}
		catch(Exception e)
		{
			System.out.println("Failed");
		}
	}
}
