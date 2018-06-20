package com.example.samar.backingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samar on 20/06/2018.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsAdapterViewHolder>{

private StepsAdapter.StepAdapterOnClickHandler mClickHandler;
        int Position ;
private List<Step_Item> mSteps;
private Context mContex;


public interface StepAdapterOnClickHandler {
    void onClick(Step_Item step);
}

    public StepsAdapter(Context context,StepsAdapter.StepAdapterOnClickHandler mClickHandler, List<Step_Item> stepsArrayList) {
        this.mClickHandler = mClickHandler;
        this.mContex=context;
        this.mSteps=stepsArrayList;
    }

public class StepsAdapterViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {


    public TextView mStepsDetailTextView;


    public StepsAdapterViewHolder(View view) {
        super(view);
        mStepsDetailTextView = (TextView) view.findViewById(R.id.step_item);
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Position = getAdapterPosition();
        Step_Item step=mSteps.get(Position);
        mClickHandler.onClick(step);
    }
}

    @Override
    public StepsAdapter.StepsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.step_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new StepsAdapter.StepsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsAdapter.StepsAdapterViewHolder holder, int position) {
        Step_Item step=mSteps.get(position);
        holder.mStepsDetailTextView.setText("Step "+step.getID()+" : "+step.getshortDescription());

    }


    @Override
    public int getItemCount() {
        if (null == mSteps)
            return 0;
        return mSteps.size();
    }



}