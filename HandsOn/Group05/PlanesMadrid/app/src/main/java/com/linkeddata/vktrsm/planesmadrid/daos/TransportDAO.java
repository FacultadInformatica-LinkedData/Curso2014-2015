package com.linkeddata.vktrsm.planesmadrid.daos;

/**
 * Created by viktor on 15/12/14.
 */
public class TransportDAO {

  public int getPublicTransportTime(double[] origin, double[] destination){
    //TODO: Implement
    return (int) (Math.random()*59 + 1);
  }
  public int getPrivateTransportTime(double[] origin, double[] destination){
    //TODO: Implement
    return (int)(Math.random()*59 + 1);
  }
}
