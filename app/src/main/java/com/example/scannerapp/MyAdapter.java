package com.example.scannerapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    public ArrayList<String> mDataset;
    public TextView straat, priority, stad;
    public ImageView delete_but;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        mDataset.get(getAdapterPosition());
                        mDataset.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), mDataset.size());
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };
        public MyViewHolder(View v) {
            super(v);
            straat = (TextView) v.findViewById(R.id.straat);
            priority = (TextView) v.findViewById(R.id.priority);
            stad = (TextView) v.findViewById(R.id.stad);
            delete_but = v.findViewById(R.id.delivery_button);
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();

                    return true;
                }
            });
        }
    }

    public ArrayList<String> jsonStringToStringArray(String s) {
       ArrayList<String> newArr = new ArrayList<>();
       ArrayList<String> ss = new ArrayList<>();

       String r = s.substring(2, s.length()- 2);
       for(String w: r.split(",")) {
           for (String q: w.split(":")) {
               ss.add(q);
           }
       }
       for (int x = 0; x < 23; x++) {
           newArr.add(ss.get((x*2)+1));
       }
       return newArr;
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<String> myDataset) {
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
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ArrayList<String> newArr;
        newArr = jsonStringToStringArray(mDataset.get(position));
        straat.setText(subString(newArr.get(8)) + " " + subString(newArr.get(9)));
        priority.setText(subString(newArr.get(20)));
        stad.setText(subString(newArr.get(11)));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
    public String subString(String s) {
        s = s.substring(1,s.length()-1);
        return s;
    }
}
