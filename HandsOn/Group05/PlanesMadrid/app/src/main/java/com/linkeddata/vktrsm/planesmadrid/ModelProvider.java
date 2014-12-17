package com.linkeddata.vktrsm.planesmadrid;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by viktor on 16/12/14.
 */
public class ModelProvider {
  private static Model model;

  public static Model getInstance() {  return model; }

  ModelProvider(Context context) {
      Log.d("debug", "leyendo modelo");
      InputStream instream = context.getResources().openRawResource(R.raw.rdf_completo);
      model = ModelFactory.createDefaultModel();
      model.read(instream, null);
      Log.d("debug", "modelo leido");

  }
}
