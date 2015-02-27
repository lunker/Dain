package dev.dain;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
        ViewHolder holder;


        if(convertView==null)
        {
            holder=new ViewHolder();

            convertView= Inflater.inflate(layout,parent,false);
           holder.iteName=(TextView) convertView.findViewById(R.id.search_text);
            convertView.setTag(holder);
        }else
        {
            holder=(ViewHolder) convertView.getTag();
        }

        String stringItem = arSrc.get(position).Text;

        if(stringItem!=null)
        {
            if(holder.iteName!=null)
            {
                holder.iteName.setText(stringItem);
            }
        }

        return convertView;
    }
    private static class ViewHolder{
        protected TextView iteName;
    }
}
