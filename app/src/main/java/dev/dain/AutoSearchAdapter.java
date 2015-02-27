package dev.dain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidha on 2015. 2. 28..
 */
public class AutoSearchAdapter extends BaseAdapter {
    Context maincon;
    List<String> arSrc;
    LayoutInflater Inflater;
    int layout;
    String mText = "";
    int arrSize;

    AutoSearchAdapter(Context context, int alayout, List<String> aarSrc, String text) {
        maincon = context;
        Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        arSrc = aarSrc;
        layout = alayout;
        mText = text;
        arrSize = arSrc.size();
    }

    @Override
    public int getCount() {
        return arSrc.size();
    }

    @Override
    public String getItem(int position) {
        return arSrc.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = Inflater.inflate(layout, parent, false);
        }

        TextView txt = (TextView) convertView.findViewById(R.id.search_text);

        for(int i=0; i<arrSize; i++) {
            if (SoundSearcher.matchString(arSrc.get(position), mText)) {
                txt.setText(arSrc.get(position));

            } else {
                String tmp = arSrc.get(position);
                for (int j = position; j < arrSize - 1; j++) {
                    arSrc.set(j, arSrc.get(j + 1));
                }
                arSrc.set(arrSize - 1, tmp);
            }
        }
        return convertView;
    }

}

