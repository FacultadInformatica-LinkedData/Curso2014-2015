package com.linkeddata.vktrsm.planesmadrid;
//Adapter designed to paint plan's cardviews

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linkeddata.vktrsm.planesmadrid.dtos.PlaceDTO;

import java.util.ArrayList;

public class CardViewPlaceAdapter extends RecyclerView.Adapter<CardViewPlaceAdapter.ViewHolder> {
  public ArrayList<PlaceDTO> mDataset;

  public CardViewPlaceAdapter(ArrayList<PlaceDTO> myDataset) {
    mDataset = myDataset;
  }

  //Create new views
  @Override
  public CardViewPlaceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_place, null);
    ViewHolder viewHolder = new ViewHolder(itemLayoutView);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(ViewHolder viewHolder, int position) {

    viewHolder.PlaceName.setText(mDataset.get(position).getName());
  }

  @Override
  public int getItemCount() {

    return mDataset.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {

    public TextView PlaceName;

    public ViewHolder(View itemLayoutView) {
      super(itemLayoutView);
      PlaceName = (TextView) itemLayoutView.findViewById(R.id.place_name_text);
    }
  }

}