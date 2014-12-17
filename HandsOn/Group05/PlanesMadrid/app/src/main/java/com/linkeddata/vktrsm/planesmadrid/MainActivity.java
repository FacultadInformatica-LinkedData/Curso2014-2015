package com.linkeddata.vktrsm.planesmadrid;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;
import com.shamanland.fab.FloatingActionButton;
import com.shamanland.fab.ShowHideOnScroll;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;


public class MainActivity extends ActionBarActivity {

  private Toolbar toolbar;

  private RecyclerView mRecyclerView;
  private RecyclerView.Adapter mAdapter;
  private RecyclerView.LayoutManager mLayoutManager;
  private ArrayList<Integer> selected = new ArrayList<Integer>();
  private ArrayList<Plan> myDataset;
  private MenuItem mDeleteItem;
  private PlansStorage storage;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    toolbar = (Toolbar) findViewById(R.id.toolbar);

    final ProgressDialog progress = ProgressDialog.show(this, "Cargando modelo RDF",
            "Espera por favor...", true);

    new Thread(new Runnable() {
      @Override
      public void run()
      {
        ModelProvider modelProvider = new ModelProvider(getApplicationContext());

        runOnUiThread(new Runnable() {
          @Override
          public void run()
          {
            progress.dismiss();
          }
        });
      }
    }).start();


    if (toolbar != null) {
      setSupportActionBar(toolbar);
      getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff5c6bc0));
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        getWindow().setStatusBarColor(0xff3f51b5);
    }
    //Initializate storage & read plas
    storage = new PlansStorage(getApplicationContext());
    myDataset = storage.readPlans();

    mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

    final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //Show animation and start AddPlan activity
        YoYo.with(Techniques.Tada).duration(100).playOn(fab);
        Intent addPlan = new Intent(v.getContext(), AddPlanActivity.class);
        startActivityForResult(addPlan,1);
      }
    });


    mRecyclerView.setOnTouchListener(new ShowHideOnScroll(fab));

    getSupportActionBar().setTitle("Tus planes");

    mRecyclerView.setHasFixedSize(true);

    mLayoutManager = new LinearLayoutManager(this);
    mRecyclerView.setLayoutManager(mLayoutManager);

    mAdapter = new CardViewPlanAdapter(myDataset);
    mRecyclerView.setAdapter(mAdapter);

    mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener()
    {
      @Override
      public void onItemClick(View view, int position)
      {
        YoYo.with(Techniques.Shake).duration(100).playOn(view);
        Intent modifyPlan = new Intent(view.getContext(), AddPlanActivity.class);
        modifyPlan.putExtra("Plan", myDataset.get(position));
        modifyPlan.putExtra("position", position);
        startActivityForResult(modifyPlan,2);
      }

      @Override
      public void onItemLongClick(View view, int position)
      {
        final ImageView sel = (ImageView)view.findViewById(R.id.plan_selected);
        if(!selected.contains(position)){
          sel.setVisibility(View.VISIBLE);
          selected.add(position);
          YoYo.with(Techniques.FlipInX).duration(300).playOn(sel);
        }else{
          selected.remove(selected.indexOf(position));
          YoYo.with(Techniques.FlipOutX)
                  .duration(300)
                  .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {}

                    @Override
                    public void onAnimationEnd(Animator animation) {
                      sel.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {}

                    @Override
                    public void onAnimationRepeat(Animator animation) {}
                  })
                  .playOn(sel);
        }

        if(selected.size() > 0)
          mDeleteItem.setVisible(true);
        else
          mDeleteItem.setVisible(false);

      }
    }));

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    mDeleteItem = menu.findItem(R.id.action_delete);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      Toast.makeText(getApplicationContext(), "Settings Clicked",
              Toast.LENGTH_SHORT).show();
      return true;
    } else if (id == R.id.action_search) {
      Toast.makeText(getApplicationContext(), "Search Clicked",
              Toast.LENGTH_SHORT).show();
      return true;

    } else if (id == R.id.action_delete) {
      Collections.sort(selected, Collections.reverseOrder());
      for (int i : selected) {
        myDataset.remove(i);
        mAdapter.notifyItemRemoved(i);
      }

      selected.clear();
      mDeleteItem.setVisible(false);

      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == 1) { //New plan
      // Make sure the request was successful
      if (resultCode == RESULT_OK) {
        Bundle res = data.getExtras();
        Plan newPlan = (Plan) res.getSerializable("Plan");
        myDataset.add(newPlan);
        mAdapter.notifyDataSetChanged();
      }
    }

    if (requestCode == 2) { //Modify plan
      // Make sure the request was successful
      if (resultCode == RESULT_OK) {
        Bundle res = data.getExtras();
        Plan planModified = (Plan) res.getSerializable("Plan");
        int position = res.getInt("position");
        myDataset.set(position, planModified);
        mAdapter.notifyDataSetChanged();
      }
    }

  }

  @Override
  public void onStop() {
    super.onStop();
    storage.savePlans(myDataset);
  }
}
