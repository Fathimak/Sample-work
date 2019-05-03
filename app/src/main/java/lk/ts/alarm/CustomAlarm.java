package lk.ts.alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;


import com.bumptech.glide.Glide;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomAlarm extends Activity {

    AlarmManager am;
    TextView tx;
    ArrayList<PendingIntent> intentArray = new ArrayList<>();
    @BindView(R.id.layout_main)
    RelativeLayout mainLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.main);
        ButterKnife.bind(this);
        tx = findViewById(R.id.textView);
        tx.setText(Long.toString(System.currentTimeMillis()));

        am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
      //  Calendar calendar = getScheduleTime();
      //  setOneTimeAlarm(dateTimeInMillis());
        setOneTimeAlarm();
        hide();
        buildUI();
    }

    public void setOneTimeAlarm() {

        Intent intent = new Intent(this, TimeAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+(5*1000), pendingIntent);
    }

/**
    public void setOneTimeAlarm(ArrayList<Long> list) {

        for(int i=0; i<list.size(); i++){
            Intent intent = new Intent(this, TimeAlarm.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, i, intent, 0);

            am.setExact(AlarmManager.RTC_WAKEUP, list.get(i), pendingIntent);
            intentArray.add(pendingIntent);

        }
    }*/
    public void setRepeatingAlarm() {
        Intent intent = new Intent(this, TimeAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (5 * 1000), pendingIntent);
    }

    public Calendar getScheduleTime() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 15);
        calendar.set(Calendar.SECOND, 00);
    return calendar;
    }


    public ArrayList<Long> dateTimeInMillis(){
        ArrayList<Long> timeL = new ArrayList<>();
        ArrayList<String> timeList = new ArrayList<>();
        String givenDateString1 = "04 01 12:43:00 GMT+05:30 2019";
        String givenDateString2 = "04 02 12:39:00 GMT+05:30 2019";
        String givenDateString3 = "04 01 12:43:00 GMT+05:30 2019";
        timeList.add(givenDateString1);
        timeList.add(givenDateString2);
        timeList.add(givenDateString3);
        for(int i=0; i<timeList.size(); i++){
            SimpleDateFormat sdf = new SimpleDateFormat("MM dd HH:mm:ss z yyyy");
            try {
                Date mDate = sdf.parse(timeList.get(i));
                long timeInMilliseconds = mDate.getTime();
                timeL.add(timeInMilliseconds);
                System.out.println("Date in milli :: " + timeInMilliseconds);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return timeL;
    }

    public void buildUI(){

        String[] images = {"882.jpg","884.jpg"};
        imageSliderView(800, 200, 500, 300, Arrays.asList(images));
    }

    //image slider using ViewFlipper
    private void imageSliderView( int x, int y, int width, int height, List<String> images) {
        ViewFlipper viewFlipper = new ViewFlipper(this);
        viewFlipper.setLayoutParams(layoutParams(x, y, width, height));
        viewFlipper.setBackgroundColor(Color.WHITE);

        for (String image : images) {
            int imgID = images.indexOf(image);

            flipImage(image, viewFlipper, 5000);
            //onSlideLeftIn();
        }

        mainLayout.addView(viewFlipper);
    }

    private void flipImage(String fileName, ViewFlipper viewFlipper, int previewTime) {
        ImageView imageView = new ImageView(this);
        File file = new File(Environment.getExternalStorageDirectory(),Environment.DIRECTORY_DOWNLOADS+getFilePath(fileName,"image"));

imageView.setImageResource(R.drawable.bell);
    /**   Glide.with(this)
                .load(new File(Environment.getExternalStorageDirectory(),
                        Environment.DIRECTORY_DOWNLOADS
                                + getFilePath(fileName, "image")))
                .centerCrop()
                .into(imageView);*/
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(previewTime);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }
	private RelativeLayout.LayoutParams layoutParams(int x, int y, int width, int height) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
        layoutParams.setMargins(x, y, 0, 0);
        return layoutParams;

    }

    private void hide() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mainLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        } else {
            mainLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }

    public static String getFilePath(String name, String type) {
        return "/signage/" + type + "/" + name;
    }

}