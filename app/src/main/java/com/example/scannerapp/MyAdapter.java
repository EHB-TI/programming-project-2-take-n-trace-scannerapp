package com.example.scannerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private String[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView straat, priority, stad, postc;
        public MyViewHolder(View v) {
            super(v);
            straat = (TextView) v.findViewById(R.id.straat);
            priority = (TextView) v.findViewById(R.id.priority);
            stad = (TextView) v.findViewById(R.id.stad);
            postc = (TextView) v.findViewById(R.id.postcode);
        }
    }
    public String[] jsonStringToStringArray(String s) {
       String[] newArr = new String[23];
       ArrayList<String> ss = new ArrayList<>();

       s = s.substring(2, s.length()- 2);
       for(String w: s.split(",")) {
           for (String q: w.split(":")) {
               ss.add(q);
           }
       }
       for (int x = 0; x < 23; x++) {
           newArr[x] = ss.get((x*2)+1);
       }
       /*
       for(int x = 0; x < 46; x++) {
           if ( x == 1 || x % 2 != 0) {
               newArr[x / 2] = ss.get(x);
           }
       }*/
       return newArr;
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String[] newArr = new String[23];
        newArr = jsonStringToStringArray(mDataset[position]);
        holder.straat.setText(newArr[8] + " " + newArr[9]);
        holder.priority.setText(newArr[20]);
        holder.postc.setText(newArr[10]);
        holder.stad.setText(newArr[11]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
