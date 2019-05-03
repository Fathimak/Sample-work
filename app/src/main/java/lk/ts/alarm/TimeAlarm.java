package lk.ts.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class TimeAlarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

System.out.println("@@@@@@@@Received@@@@@@@@@@");
        Toast.makeText(context, "OnReceive alarm test", Toast.LENGTH_SHORT).show();


    }
}