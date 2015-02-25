package dev.dain;

import android.app.Application;

/**
 * Created by lunker on 15. 2. 25..
 */
public class GlobalVariable extends Application {


    private final String SERVERIP = "14.49.38.96";
    private final int SERVERPORT = 10000;


    public String getSERVERIP() {
        return SERVERIP;
    }

    public int getSERVERPORT() {
        return SERVERPORT;
    }


}
