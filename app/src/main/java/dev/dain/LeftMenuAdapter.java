package dev.dain;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import static android.support.v4.app.ActivityCompat.startActivityForResult;


/**
 * Created by davidha on 2015. 2. 25..
 */
public class LeftMenuAdapter extends BaseAdapter {
    Context maincon;
    LayoutInflater Inflater;
    int layout;
    View profileView;
    String Facebook_id;
    String Facebook_name;
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;


    public LeftMenuAdapter(Context context, int alayout, String facebookId, String facebookName) {
        maincon = context;
        Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = alayout;
        Facebook_id = facebookId;
        Facebook_name = facebookName;

    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public String getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = Inflater.inflate(layout, parent, false);

        TextView pf_name = (TextView) convertView.findViewById(R.id.pf_name);
        TextView pf_id = (TextView) convertView.findViewById(R.id.pf_id);
        ImageView pf_img = (ImageView) convertView.findViewById(R.id.pf_img);
        String Facebook_pf_url="https://graph.facebook.com/dhha22/picture?type=normal";

        pf_id.setText(Facebook_id);
        pf_name.setText(Facebook_name);


        Bitmap imgBitmap = GetImageFromURL(Facebook_pf_url);
        if(imgBitmap!=null) {
            pf_img.setImageBitmap(imgBitmap);
        }
        //pf_img.setImageResource(R.drawable.dain);
        //이미지 둥글게
       // BitmapDrawable bImage = (BitmapDrawable)(pf_img).getDrawable();
        //pf_img.setImageDrawable(new RoundedAvatarDrawable(bImage.getBitmap()));
        //URL을 이용한 이미지 다운
       // Bitmap imgBitmap = GetImageFromURL(Facebook_pf_url);
    /*
        if(imgBitmap!=null) {
            pf_img.setImageBitmap(imgBitmap);
        }

        else
        {
            pf_img.setImageResource(R.drawable.dain);
        }
        */

       return convertView;
    }
    private Bitmap GetImageFromURL(String strImageURL)
    {
        Bitmap imgBitmap=null;
        try
        {
            URL url = new URL(strImageURL);
            URLConnection conn = url.openConnection();
            conn.connect();
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            imgBitmap=BitmapFactory.decodeStream(bis);
            bis.close();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return imgBitmap;
    }

}
