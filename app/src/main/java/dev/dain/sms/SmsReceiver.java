package dev.dain.sms;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.Date;

import dev.dain.MainActivity;
import dev.dain.R;

/**
 * Created by lunker on 15. 2. 22..
 */
public class SmsReceiver extends BroadcastReceiver {


    private final String TAG = "dain";
    private Context context = null;
    @Override
    public void onReceive(final Context context, Intent intent) {

        this.context = context;
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            // 부팅완료
        }

        if ("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
            // sms 수신

            Bundle bundle = intent.getExtras();
            Object messages[] = (Object[])bundle.get("pdus");
            SmsMessage smsMessage[] = new SmsMessage[messages.length];

            for(int i = 0; i < messages.length; i++) {
                // PDU 포맷으로 되어 있는 메시지를 복원합니다.
                smsMessage[i] = SmsMessage.createFromPdu((byte[])messages[i]);
            }

// SMS 수신 시간 확인
            Date curDate = new Date(smsMessage[0].getTimestampMillis());
            Log.d("문자 수신 시간", curDate.toString());

// SMS 발신 번호 확인
            String origNumber = smsMessage[0].getOriginatingAddress();

// SMS 메시지 확인
            String message = smsMessage[0].getMessageBody().toString();
            Log.v(TAG,"문자 내용"+ "발신자 : "+origNumber+", 내용 : " + message);

            if(message.contains("투썸") || message.contains("할리스")){

                /*
                Handler handle = new Handler();
                handle.postDelayed(new Runnable() {
                    @Override
                    public void run() {


//                        makeNotification();
                        Intent dialogIntent = new Intent(context, NotificationActivity.class);
                        context.startActivity(dialogIntent);

                    }
                }, 900);

                */
                Intent dialogIntent = new Intent(context, NotificationActivity.class);
                dialogIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(dialogIntent);
            }

        }
    }// end receive


    public void makeNotification(){
        Log.v(TAG,"in make noti");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ic_launcher);//required

//                mBuilder.setLargeIcon(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.dain), 72,72,false));
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.dain));
        mBuilder.setContentTitle("Dain");//required
        mBuilder.setContentText("평가해보세요 ! ");//required
        mBuilder.setTicker("커피를 구매하셨나요? ");//optional
        mBuilder.setNumber(10);//optional
        mBuilder.setAutoCancel(true);
        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }


}
