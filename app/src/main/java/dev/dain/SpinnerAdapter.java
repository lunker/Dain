package dev.dain;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by davidha on 2015. 3. 7..
 */
public class SpinnerAdapter extends ArrayAdapter<String> {
    Context mContext;
    String[] mItems=new String[]{};

    public SpinnerAdapter(final Context context,final int txtresourceId,final String[] items)
    {
        super(context,txtresourceId,items);
        mContext=context;
        mItems=items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView=inflater.inflate(android.R.layout.simple_spinner_item,parent,false);
        }
        TextView txt=(TextView)convertView.findViewById(R.id.sp_item);
        txt.setText(mItems[position]);
        txt.setTextColor(Color.BLACK);

        return convertView;
    }
}
