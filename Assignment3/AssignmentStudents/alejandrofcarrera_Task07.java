import java.io.*;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.reasoner.*;
import com.hp.hpl.jena.reasoner.rulesys.*;
import com.hp.hpl.jena.util.*;
import com.hp.hpl.jena.util.iterator.*;
import com.hp.hpl.jena.vocabulary.RDFS;

/** @author alejandrofcarrera */

public class alejandrofcarrera_Task07
{
	private static final Resource A = null;
	private static final Property pr = null;
	private static final RDFNode D = null;
	private static String ns = "http://somewhere#";
	private static String path = "docs/example6.rdf"; // fine for this Task
	private static OntClass p;
	
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
		
		// 7.1
		System.out.println(" * TASK 7.1:");
		System.out.println(" * List all individuals of \"Person\":");
		p = model.getOntClass(ns+"Person");
		if(p == null)
		{
			System.out.println("   - No individuals found\n");
		}
		else
		{
			printInstances(p, true);
		}
		System.out.println();
		
		// 7.2
		System.out.println(" * TASK 7.2:");
		System.out.println(" * List all subclasses of \"Person\":");
		if(p == null)
		{
			System.out.println("   - No individuals found, so no subclasses found\n");
		}
		else
		{
			printSubclass(p, true);
		}
		System.out.println();
		
		// 7.3
		System.out.println(" * TASK 7.3:");
		System.out.println(" * Get as well indirect instances and subclasses:");
		if(p == null)
		{
			System.out.println("   - No individuals found, so instances or subclasses found\n");
		}
		else
		{
			printSubclassAndInstances(p, false);
			printSubclassAndInstancesInference(model);
		}
		System.out.println();
	}

	private static void printSubclassAndInstancesInference(OntModel m) {
		System.out.println(" * Inference");
		String r = "[r1: (?B rdfs:subClassOf ?A), (?C rdf:type ?B) -> (?C rdf:type ?A)]";
		Reasoner re = new GenericRuleReasoner(Rule.parseRules(r));
		InfModel i = ModelFactory.createInfModel(re, m);
		StmtIterator it = i.listStatements(A, pr, D);
		while(it.hasNext())
		{
			Statement s = it.nextStatement();
			if(s.getObject().asNode().hasURI(ns+"Person"))
			{
				if(s.getPredicate().hasURI(RDFS.subClassOf.getURI()))
				{
					System.out.println("   s: "+s.getSubject());
				}
				else
				{
					System.out.println("   i: "+s.getSubject());
				}
			}
		}
	}

	private static void printSubclassAndInstances(OntClass o, boolean b) {
		System.out.println(" * Subclass iterator");
		ExtendedIterator<OntClass> c = p.listSubClasses(b);
		while(c.hasNext())
		{
			OntClass cl = c.next();
			System.out.println("   s: " + cl);
			printInstances((OntClass) cl, false);
			cl = null;
		}
		printInstances(o, false);
		c = null;
	}

	private static void printInstances(OntClass o, boolean b) {
		ExtendedIterator<? extends OntResource> r = o.listInstances(b);
		while(r.hasNext())
		{
			System.out.println("   i: " + r.next());
		}
		r = null;
	}

	private static void printSubclass(OntClass o, boolean b) {
		ExtendedIterator<OntClass> c = p.listSubClasses(b);
		while(c.hasNext())
		{
			System.out.println("   s: " + c.next());
		}
		c = null;
	}
}
