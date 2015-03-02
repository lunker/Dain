package dev.dain;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import java.util.ArrayList;

import dev.dain.rating.RatingAdapter;

/**
 * Created by davidha on 2015. 2. 28..
 */
public class NoticeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Boolean> mDataSet;
    private int expandedLocaition = -1;
    private boolean isExpandedAll = false;

    public static class NoticeParentViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextcontent;
        public TextView mTextdate;
        public CardView mCardView;

        public NoticeParentViewHolder(View v) {
            super(v);
            mCardView = (CardView) v.findViewById(R.id.notice_card_view_parent);
            mTextcontent = (TextView) v.findViewById(R.id.txt_notice_item_parent_title);
            mTextdate = (TextView) v.findViewById(R.id.txt_notice_date_item_parent_title);
        }
    }

    public class NoticeChildViewHolder extends RecyclerView.ViewHolder {
        public NoticeChildViewHolder(View v) {
            super(v);
            TextView child_content = (TextView) v.findViewById(R.id.txt_notice_child);
        }
    }

    public NoticeAdapter(ArrayList<Boolean> myDataset) {
        mDataSet = myDataset;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View parentView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_notice_item, parent, false);
                return new NoticeParentViewHolder(parentView);

            case 1:
                View childView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notice_child_item, parent, false);
                return new NoticeChildViewHolder(childView);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataSet.get(position))
            return 1;
        else
            return 0;
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void addItem(int position, boolean data) {

        mDataSet.add(position, data);
        notifyItemInserted(position);
    }

    public void removeItem(int position, boolean data) {

        mDataSet.remove(position);
        notifyItemRemoved(position);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case 0:
                final NoticeParentViewHolder pHolder = (NoticeParentViewHolder) holder;
                ((NoticeParentViewHolder) holder).mCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isExpandedAll) {
                            if (position == (expandedLocaition - 1)) {
                                removeItem(expandedLocaition, true);
                                expandedLocaition = -1;
                                isExpandedAll = false;
                            } else if ((position + 1) == expandedLocaition)
                                return;
                            else {
                                if (expandedLocaition < position) {
                                    removeItem(expandedLocaition, true);
                                    addItem(position, true);
                                    expandedLocaition = position;

                                } else {
                                    removeItem(expandedLocaition, true);
                                    addItem(position + 1, true);
                                    expandedLocaition = position + 1;
                                }
                            }

                        } else {
                            addItem(position + 1, true);
                            expandedLocaition = position + 1;
                            isExpandedAll = true;
                        }
                    }
                });
                break;

            case 1:

        }

    }
}




