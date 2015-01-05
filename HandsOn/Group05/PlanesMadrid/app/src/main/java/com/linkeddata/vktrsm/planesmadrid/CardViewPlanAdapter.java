package com.linkeddata.vktrsm.planesmadrid;
//Adapter designed to paint plan's cardviews

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CardViewPlanAdapter extends RecyclerView.Adapter<CardViewPlanAdapter.ViewHolder> {
  public ArrayList<Plan> mDataset;

  public CardViewPlanAdapter(ArrayList<Plan> myDataset) {
    mDataset = myDataset;
  }

  //Create new views
  @Override
  public CardViewPlanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
    View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
            R.layout.cardview_plan, null);

    ViewHolder viewHolder = new ViewHolder(itemLayoutView);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(ViewHolder viewHolder, int position) {

    viewHolder.planName.setText(mDataset.get(position).getName());


    String minuteStart = (mDataset.get(position).getStart()[1] < 10) ?
            "0"+mDataset.get(position).getStart()[1]:""+mDataset.get(position).getStart()[1];
    String minuteEnd = (mDataset.get(position).calculateEnd()[1] < 10) ?
            "0"+mDataset.get(position).calculateEnd()[1]:""+mDataset.get(position).calculateEnd()[1];

    viewHolder.planTime.setText(mDataset.get(position).getStart()[0] + ":" + minuteStart + " - "
            + mDataset.get(position).calculateEnd()[0]+":"  + minuteEnd);
    viewHolder.planSelected.setVisibility(View.GONE);
  }

  @Override
  public int getItemCount() {

    return mDataset.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {

    public TextView planName, planTime;
    public ImageView planSelected;

    public ViewHolder(View itemLayoutView) {
      super(itemLayoutView);
      planName = (TextView) itemLayoutView.findViewById(R.id.plan_name_text);
      planTime = (TextView) itemLayoutView.findViewById(R.id.tvTime);
      planSelected = (ImageView) itemLayoutView.findViewById(R.id.plan_selected);

    }
  }

}