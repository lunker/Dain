package dev.dain;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by davidha on 2015. 2. 26..
 */
public class BackPressCloseHandler {

    long backKeyPressedTime=0;
    Toast toast;
    Activity activity;

    public BackPressCloseHandler(Activity context)
    {
        this.activity = context;
    }
    public void onBackPressed()
    {
        if(System.currentTimeMillis() > backKeyPressedTime +2000)
        {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if(System.currentTimeMillis() <= backKeyPressedTime +2000)
        {
            LoginActivity aActivity =(LoginActivity)LoginActivity.AActivity;
            aActivity.finish();
            activity.finish();
            toast.cancel();
        }
    }
    public void showGuide()
    {
        toast = Toast.makeText(activity, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.",Toast.LENGTH_SHORT);
        toast.show();
    }
}
