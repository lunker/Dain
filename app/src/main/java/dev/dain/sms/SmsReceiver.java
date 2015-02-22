package dev.dain.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by lunker on 15. 2. 22..
 */
public class SmsReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        if ("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
            // sms 수신
        }
    }
}
