package com.example.mariokarttournamentorganizer;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

/**
 * Sets up the clickable, changing view on the home page that shows current and past
 * ACTs.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private String[] localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private Button inspectACTButton;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) itemView.findViewById(R.id.contact_name);
            inspectACTButton = (Button) itemView.findViewById(R.id.inspect_ACT_Button);
            inspectACTButton.setOnClickListener(v->{
//                Log.v("Hey", "YO");
//                Intent currACT = new Intent(homeScreenActivity.class, CreateACTActivity.class)
            });
        }

        public TextView getTextView() {
            return textView;
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView
     */
    public RecyclerViewAdapter(String[] dataSet) {
        localDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.text_row_item_homepage, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder viewHolder, int position) {

        //viewHolder.getTextView().setText(localDataSet[position]);

        TextView textView = viewHolder.textView;
        textView.setText(localDataSet[position]);

        Button inspectACT = viewHolder.inspectACTButton;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }

    public void addData(String[] newData) {
        int oldLength = localDataSet.length;
        localDataSet = Arrays.copyOf(localDataSet, oldLength + newData.length);
        System.arraycopy(newData, 0, localDataSet, oldLength, newData.length);
    }

}