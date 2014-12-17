package com.linkeddata.vktrsm.planesmadrid.daos;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.linkeddata.vktrsm.planesmadrid.ModelProvider;
import com.linkeddata.vktrsm.planesmadrid.dtos.PlaceDTO;

import java.util.ArrayList;

/**
 * Created by viktor on 15/12/14.
 *
 * This class is designed to abstract access to model data
 */

public class LibrariesDAO implements PlaceDAO{
  private Model model = ModelProvider.getInstance();
  private final String ns = "http://localhost:3333/mibarrio/";
  private final String schema = "http://schema.org/";
  private final String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
  private final String geo = "http://www.w3.org/2003/01/geo/wgs84_pos#";
  private final String owl = "http://www.w3.org/2002/07/owl#";
  private final String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
  private final String admingeo = "http://data.ordnancesurvey.co.uk/ontology/admingeo/";
  private final String mibarrio = "http://www.semanticweb.org/root/ontologies/2014/11/mibarrio#";

  @Override
  public ArrayList<PlaceDTO> getListByName(String nombre) {
    ArrayList<PlaceDTO> result = new ArrayList<>();
    Resource mibarrio_Library = model.createResource(mibarrio + "Library");
    ResIterator rIter = this.model.listSubjectsWithProperty(RDF.type,mibarrio_Library);
    while (rIter.hasNext() && result.size() < 10){
      Resource r = rIter.nextResource();
      Property schema_name = this.model.createProperty(schema+"name");
      Property schema_description = this.model.createProperty(schema+"description");
      if (r.hasProperty(schema_name)){
        String placeName = r.getRequiredProperty( schema_name ).getString();
        if (placeName.toLowerCase().contains(nombre.toLowerCase())){
          double[] location = this.getPlaceCoordinates(placeName.replace(" ",""));
          String placeDescription = "";
          if(r.hasProperty(schema_description)){
            placeDescription = r.getRequiredProperty( schema_description).getString();
          }
          PlaceDTO place = new PlaceDTO(placeName,location[0],location[1]);
          result.add(place);
        }
      }
    }
    return result;
  }
  public double[] getPlaceCoordinates(String place){
    double[] location = new double[2];
    location[0] = 0;
    location[1] = 0;
    Resource spatial = this.model.getResource(ns + "Spatial/" + place);
    Property geo_latitude = this.model.createProperty(geo + "lat");
    Property geo_longitude = this.model.createProperty(geo + "long");
    if (spatial.hasProperty(geo_latitude)){
      location[0] = spatial.getRequiredProperty( geo_latitude ).getDouble();
    }
    if (spatial.hasProperty(geo_longitude)){
      location[1] = spatial.getRequiredProperty( geo_longitude).getDouble();
    }
    return location;
  }

}
