package com.linkeddata.vktrsm.planesmadrid;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * Created by viktor on 12/12/14.
 */

//This class is designed to abstract local storage of plan's storage, now uses local files,
//in future this can be rewritten to work against some server or sync sys.

public class PlansStorage {

  private Context context;
  PlansStorage(Context context){
    this.context = context;
  }

  public boolean savePlans(ArrayList<Plan> plans){
    try {
      FileOutputStream fos = context.openFileOutput("plans", Context.MODE_PRIVATE);
      ObjectOutputStream os = new ObjectOutputStream(fos);
      os.writeObject(plans);
      os.close();
      return true;
    }catch (Exception e){
      return false;
    }
  }

  public ArrayList<Plan> readPlans(){
    ArrayList<Plan> result = new ArrayList<Plan>();
    try {
      FileInputStream fis = context.openFileInput("plans");
      ObjectInputStream is = new ObjectInputStream(fis);
      result = (ArrayList<Plan>)is.readObject();
      is.close();
    }catch (Exception e){}
    return result;
  }

}
