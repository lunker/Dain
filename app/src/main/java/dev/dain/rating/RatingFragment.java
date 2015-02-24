package dev.dain.rating;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import dev.dain.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RatingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RatingFragment extends Fragment {

    private final String TAG = "dain";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    private View view = null;
    private RecyclerView mRecyclerView = null;
    private LinearLayoutManager mLayoutManager = null;
    private RatingAdapter mAdapter = null;
    private int expandedLocaition = -1;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RatingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RatingFragment newInstance(String param1, String param2) {
        RatingFragment fragment = new RatingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public RatingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if(view == null){
            view =  inflater.inflate(R.layout.layout_fragment_rating, container, false);
            mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
//            mRecyclerView.setHasFixedSize(true);
        }

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Boolean> myDataset = new ArrayList<Boolean>();
        myDataset.add(false);
        myDataset.add(false);
        myDataset.add(false);
        myDataset.add(false);
        myDataset.add(false);
        myDataset.add(false);

        mLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new RatingAdapter(myDataset, getActivity().getBaseContext());
        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
                        Log.v(TAG, "cardview selected! in add on item ");


                        // click the child view.
                        if( position == expandedLocaition){
//                            mAdapter.removeItem(position,true);
                            //?
                        }
                        else if( (expandedLocaition-1) == position ){
                            mAdapter.removeItem(position,true);
                        }
                        else if(expandedLocaition == -1){
                            mAdapter.addItem(position+1, true);
                            expandedLocaition = position+1;
                        }
                        else if( (position+1) == expandedLocaition ){
                            return ;
                        }
                        else{

                            if(expandedLocaition < position ){
                                mAdapter.removeItem(expandedLocaition,true);
                                mAdapter.addItem(position,true);
                                expandedLocaition = position;

                            }
                            else{
                                mAdapter.removeItem(expandedLocaition, true);
                                mAdapter.addItem(position+1,true);
                                expandedLocaition = position +1;
                            }

                        }//end else

//                        expandedLocaition = position

                    }
                })
        );




    }

}
