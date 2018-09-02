package io.github.yhdesai.udabakingapp;


import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import io.github.yhdesai.udabakingapp.data.StepsItem;


public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsHolder> {


    private final StepsClickListener mStepsClickListener;
    private StepsItem[] mSteps = null;


    public StepsAdapter(StepsItem[] steps, StepsClickListener stepsClickListener) {
        mSteps = steps;

        mStepsClickListener = stepsClickListener;
    }


    @NonNull
    @Override
    public StepsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_steps, parent, false);
        return new StepsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StepsHolder holder, int position) {


        final Handler handler = new Handler();
        final int positions = position;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                StepsItem stepsItem = mSteps[positions];

                if (stepsItem != null) {
                    String shortDescription = stepsItem.getShortDescription();
                    holder.shortDescriptionViewHolder.setText(shortDescription);

                } else {

                    Log.e("onBindViewHolder empty", "whoosh");
                }
            }
        }, 100);


    }

    @Override
    public int getItemCount() {
        return mSteps.length;
    }

    public interface StepsClickListener {
        void onClickSteps(int position);
    }

    class StepsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView shortDescriptionViewHolder;


        public StepsHolder(View itemView) {
            super(itemView);
            shortDescriptionViewHolder = itemView.findViewById(R.id.stepsShortDescription);
            shortDescriptionViewHolder.setOnClickListener(this);
      }

        @Override
        public void onClick(View view) {
            int clickPosition = getAdapterPosition();
            mStepsClickListener.onClickSteps(clickPosition);
        }
    }
}