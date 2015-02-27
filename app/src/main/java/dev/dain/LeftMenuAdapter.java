package dev.dain;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceActivity;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    ImageView pf_img;



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
        pf_img = (ImageView) convertView.findViewById(R.id.pf_img);
        SharedPreferencesActivity pref = new SharedPreferencesActivity(maincon);
        String Imagepath = pref.getPreferences("imagepath","");
        int value=pref.getPreferences("value",1);

        pf_id.setText(Facebook_id);
        pf_name.setText(Facebook_name);


          if(value==0) {
              (new DownThread("https://graph.facebook.com/" + Facebook_id + "/picture?type=normal")).start();
          }
          if(value==1)
            {

                if(Imagepath.equals("")){
                    pf_img.setImageResource(R.drawable.dain);
                }
                else {

                    Bitmap bit = BitmapFactory.decodeFile(Imagepath);
                    bit = Bitmap.createScaledBitmap(bit, 75, 75, true);
                    pf_img.setImageBitmap(bit);
                    BitmapDrawable bImage = (BitmapDrawable) (pf_img).getDrawable();
                    pf_img.setImageDrawable(new RoundedAvatarDrawable(bImage.getBitmap()));

                }
            }
        //이미지 둥글게
       //BitmapDrawable bImage = (BitmapDrawable)(pf_img).getDrawable();
        //pf_img.setImageDrawable(new RoundedAvatarDrawable(bImage.getBitmap()));


       return convertView;
    }
    class DownThread extends Thread
    {
        String mAddr;
        DownThread(String addr)
        {
            mAddr=addr;
        }

        @Override
        public void run() {
            try{
                InputStream is = new URL(mAddr).openStream();
                Bitmap bit = BitmapFactory.decodeStream(is);
                is.close();
                Message message =mAfterDown.obtainMessage();
                message.obj=bit;
                mAfterDown.sendMessage(message);

            }catch(Exception e){;}
        }
    }
    Handler mAfterDown = new Handler()
    {
        public void handleMessage(Message msg)
        {
            Bitmap bit = (Bitmap)msg.obj;
            bit=Bitmap.createScaledBitmap(bit,75,75,true);
            if(bit==null)
            {

            }else
            {
                pf_img.setImageBitmap(bit);
                BitmapDrawable bImage = (BitmapDrawable)(pf_img).getDrawable();
                pf_img.setImageDrawable(new RoundedAvatarDrawable(bImage.getBitmap()));


            }
        }
    };

}
