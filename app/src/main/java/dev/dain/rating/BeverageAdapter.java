package dev.dain.rating;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import dev.dain.R;

/**
 * Created by lunker on 15. 2. 25..
 */
public class BeverageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    static final String TAG = "dain";


    public static class BeverageViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView beverageImage;
        public RatingBar beverageRating;
        public TextView beverageName;
        public TextView beverageRatingMessage;
        public TextView beverageRatingGrade;

        public BeverageViewHolder(View v) {
            super(v);

            beverageImage =  (ImageView)v.findViewById(R.id.beverage_item_image);
            beverageRating = (RatingBar)v.findViewById(R.id.beverage_item_rating);
            beverageName = (TextView)v.findViewById(R.id.beverage_item_name);
            beverageRatingGrade = (TextView)v.findViewById(R.id.beverage_item_image_rating_grade);
            beverageRatingMessage = (TextView)v.findViewById(R.id.beverage_item_image_rating_message);

            beverageRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    Log.v(TAG, "rating bar changed : " + rating);
                }
            });

            beverageRating.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    Log.v(TAG,"rating bar toucehd~! : "+ event.getX());

                    float width = beverageRating.getWidth();
                    float oneStarWidth = width / (float)5.0;

                    AlphaAnimation fadeIn = new AlphaAnimation(0.0f , 1.0f ) ;
                    AlphaAnimation fadeOut = new AlphaAnimation( 1.0f , 0.0f ) ;
                    fadeIn.setDuration(400);
                    fadeIn.setFillAfter(true);
                    fadeOut.setDuration(400);
                    fadeOut.setFillAfter(true);
                    fadeOut.setStartOffset(600+fadeIn.getStartOffset());
                    switch(  (int)(event.getX() / oneStarWidth) ){

                        case 0 :
                            Log.v(TAG,"rating bar toucehd~ 0 ! ");

                            beverageRatingGrade.setText("1.0");
                            beverageRatingGrade.startAnimation(fadeIn);
                            beverageRatingGrade.startAnimation(fadeOut);

                            break;
                        case 1 :
                            Log.v(TAG,"rating bar toucehd~ 1 ! ");

                            beverageRatingGrade.setText("2.0");
                            beverageRatingGrade.startAnimation(fadeIn);
                            beverageRatingGrade.startAnimation(fadeOut);

                            break;
                        case 2 :
                            Log.v(TAG,"rating bar toucehd~ 2 ! ");

                            beverageRatingGrade.setText("3.0");
                            beverageRatingGrade.startAnimation(fadeIn);
                            beverageRatingGrade.startAnimation(fadeOut);
                            break;
                        case 3 :
                            Log.v(TAG,"rating bar toucehd~ 3 ! ");

                            beverageRatingGrade.setText("4.0");
                            beverageRatingGrade.startAnimation(fadeIn);
                            beverageRatingGrade.startAnimation(fadeOut);
                            break;
                        case 4 :
                            Log.v(TAG,"rating bar toucehd~ 4 ! ");

                            beverageRatingGrade.setText("5.0");
                            beverageRatingGrade.startAnimation(fadeIn);
                            beverageRatingGrade.startAnimation(fadeOut);
                            break;

                    }
                    return false;
                }
            });
        }
    }

    public BeverageAdapter(){
//        super();
    }

    @Override
    public BeverageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_beverage_list_item, parent, false);

        return new BeverageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // set the content of the view

        ((BeverageViewHolder)holder).beverageName.setText("position : " + position);
//        ((BeverageViewHolder)holder).


    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
