package dev.dain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by davidha on 2015. 2. 27..
 */
public class SearchViewAdapter extends BaseAdapter {
    Context maincon;
    ArrayList<SearchList> arSrc;
    LayoutInflater Inflater;
    int layout;

    SearchViewAdapter(Context context,int alayout, ArrayList<SearchList> aarSrc)
    {
        maincon=context;
        Inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        arSrc=aarSrc;
        layout=alayout;
    }

    @Override
    public int getCount() {
        return arSrc.size();
    }

    @Override
    public SearchList getItem(int position) {
        return arSrc.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos=position;
        if(convertView==null)
        {
            convertView= Inflater.inflate(layout,parent,false);
        }
        TextView txt=(TextView)convertView.findViewById(R.id.search_text);
        txt.setText(arSrc.get(position).Text);
        return convertView;
    }
}
