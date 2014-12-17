package com.linkeddata.vktrsm.planesmadrid.daos;

import com.linkeddata.vktrsm.planesmadrid.dtos.PlaceDTO;

import java.util.ArrayList;

/**
 * Created by viktor on 15/12/14.
 */
public interface PlaceDAO {
  public ArrayList<PlaceDTO> getListByName(String name);
}
