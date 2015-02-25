package dev.dain.rating;

/**
 * Created by lunker on 15. 2. 22..
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
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
    private int expandedLocaition = -1;
    private boolean isExpandedAll = false;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    private Context context;
    private GridView grid = null;

    /*
        parent
     */
    public static class ParentViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public CardView cardView ;
        public boolean isExpanded = false;

        public ParentViewHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.card_view_parent);
            mTextView = (TextView) v.findViewById(R.id.txtview_rating_item_parent_title);

        }
    }
    /*
        child
     */

    public class ChildViewHolder extends  RecyclerView.ViewHolder {

        public GridView gridView ;
        public ChildViewHolder(View v){
                super(v);

            gridView = (GridView) v.findViewById(R.id.grid_child_item);
            gridView.setAdapter(new BaseAdapter() {


                @Override
                public int getCount() {
                    return 4;
                }

                @Override
                public Object getItem(int position) {
                    return null;
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {

                    if(convertView == null){
                        convertView = ( (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_grid_item, parent, false);
                    }

                    TextView gridItemTitle = (TextView) convertView.findViewById(R.id.grid_item_title);

                    switch (position){
                        case 0:
                            gridItemTitle.setText("Drink");
                            break;
                        case 1:
                            gridItemTitle.setText("Dessert");
                            break;
                        case 2:
                            gridItemTitle.setText("Best 10");
                            break;
                        case 3:
                            gridItemTitle.setText("All");
                            break;
                    }

                    return convertView;
                }
            });


            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    Log.v(TAG,"[GridView] " + position +" clicked in constructor ");

                    switch (position){
                        case 0:
                            Intent beverageIntent = new Intent(context,  BeverageActivity.class);
                            beverageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(beverageIntent);
                            break;
                        case 1: break;
                        case 2: break;
                        case 3: break;

                    }
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RatingAdapter(ArrayList<Boolean> myDataset, final Context context) {
        mDataSet = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view

        Log.v(TAG, "view type???: " + viewType);

        switch (viewType){
            case 1 :
                // child
              View childView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_gridview, parent, false);
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        switch(holder.getItemViewType()){
            case 1:
                // set child view content

                ((ChildViewHolder)holder).gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                        Log.v(TAG,"[GridView] " + position +" clicked! ");

                        switch (position){
                            case 0:
                                Intent beverageIntent = new Intent(context,  BeverageActivity.class);
                                beverageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(beverageIntent);
                                break;
                            case 1: break;
                            case 2: break;
                            case 3: break;

                        }
                    }
                });

                break;

            case 0:
                // set parent view content
                final ParentViewHolder pHolder = (ParentViewHolder) holder;
                ((ParentViewHolder)holder).mTextView.setText("parent : " + position);

                ((ParentViewHolder)holder).cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(isExpandedAll){
                            if(position == (expandedLocaition -1 )){

                                removeItem(expandedLocaition,true);
                                expandedLocaition = -1;
                                isExpandedAll = false;

                            }
                            else if( (position+1) == expandedLocaition ){
                                /*
                                    click the child view
                                 */
                                return ;
                            }
                            else{
                                if(expandedLocaition < position ){
                                    removeItem(expandedLocaition,true);
                                    addItem(position,true);
                                    expandedLocaition = position;

                                }
                                else{
                                    removeItem(expandedLocaition, true);
                                    addItem(position+1,true);
                                    expandedLocaition = position +1;
                                }
                            }//end else

                        }
                        else{
                            addItem(position+1, true);
                            expandedLocaition = position+1;
                            isExpandedAll = true;
                        }
                    }
                });
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