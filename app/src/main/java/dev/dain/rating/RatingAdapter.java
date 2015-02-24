package dev.dain.rating;

/**
 * Created by lunker on 15. 2. 22..
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import dev.dain.R;

/**
 * Created by lunker on 15. 2. 22..
 */
public class RatingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = "dain";
//    private boolean[] mDataSet;
    private ArrayList<Boolean> mDataSet;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    private CardView childCardView = null;
    private Context context;


    /*
        parent
     */
    public static class ParentViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public CardView cardView ;

        public ParentViewHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.card_view_parent);
            mTextView = (TextView) v.findViewById(R.id.txtview_rating_item_parent_title);

        }
    }
    /*
        child
     */

    public static class ChildViewHolder extends  RecyclerView.ViewHolder {

        public ChildViewHolder(View v){
                super(v);
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public RatingAdapter(ArrayList<Boolean> myDataset, Context context) {
        mDataSet = myDataset;
        childCardView = (CardView) ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).
                inflate(R.layout.layout_fragment_rating_double_item, null);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view

        Log.v(TAG, "view type???: " + viewType);


        // set the view's size, margins, paddings and layout parameters

        switch (viewType){
            case 1 :
                // child

                View childView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_fragment_rating_double_item, parent, false);


                return new ChildViewHolder(childView);
            case 0 :
                // parent
                View parentView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_fragment_rating_item, parent, false);
                return new ParentViewHolder(parentView);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {

        if(mDataSet.get(position)){
            return 1; //expandable

        }
        else
            return 0;
//        return super.getItemViewType(position);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        holder.mTextView.setText(position + "" );

        switch(holder.getItemViewType()){
            case 1:

                break;

            case 0:
                ((ParentViewHolder)holder).mTextView.setText("parent : " + position);
                break;

        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void addItem(int position, boolean data){

        Log.v(TAG,"[Rating] insert item at " + position);
        mDataSet.add(position, data);
        notifyItemInserted(position);
    }
    public void removeItem(int position, boolean data){

        Log.v(TAG,"[Rating] delete item at " + position);
        mDataSet.remove(position);
        notifyItemRemoved(position);

    }
}