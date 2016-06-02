package com.avocado.myanmar_currency_rate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.*;

import com.mopub.mobileads.MoPubView;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by kyawmyintcho on 5/30/14.
 */
public class CalanderActivity extends Activity {
    MoPubView moPubView;
    public DatePicker calanderView;
   public Button cancel;
    public Button select;
    public Button monthUp;
    public Button monthDown;
    public int day;
    public int month;
    public int year;
    Calendar calendar;

    public void testToast(String date)
    {
        Toast.makeText(this,"Selected: "+date,Toast.LENGTH_LONG).show();
    }



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_view);
         calanderView = (DatePicker) findViewById(R.id.datePicker);
        cancel = (Button)findViewById(R.id.cancel);
        select = (Button)findViewById(R.id.select);
        monthDown = (Button) findViewById(R.id.monthDown);
        monthUp = (Button) findViewById(R.id.monthUp);
        calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        calanderView.setMaxDate(calendar.getTimeInMillis());
        day = calendar.get(Calendar.DAY_OF_MONTH);

        //adjust DatePicker size
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        //for 3.7 inches or smaller
        if(width <= 480 && height <= 800)
        {
            calanderView.setScaleX((float) 1.0);
            calanderView.setScaleY((float) 1.0);
        }
        //for 3.7 inches to 4.7 inches
        else if((width >= 481 && width <= 768 )  &&(height >= 801 && height <= 1280))
        {
            calanderView.setScaleX((float) 1.2);
            calanderView.setScaleY((float) 1.2);
        }
        //4.7 inches to 5 inches
        else if((width >= 769 && width <= 1080 )  &&(height >= 1281 && height <= 1920))
        {
            calanderView.setScaleX((float) 1.3);
            calanderView.setScaleY((float) 1.3);
        }
        else if((width >= 1081 && width <= 1200 )  &&(height >= 1281 && height <= 1920))
        {
            calanderView.setScaleX((float) 1.4);
            calanderView.setScaleY((float) 1.4);
        }
        else if((width >= 1201 && width <= 1600 )  &&(height >= 1921 && height <= 2560))
        {
            calanderView.setScaleX((float) 1.5);
            calanderView.setScaleY((float) 1.5);
        }




      //  Toast.makeText(this,"Screen Size"+ "width" +Integer.toString(width) + "height"+ Integer.toString(height), Toast.LENGTH_LONG).show();

      //  Log.d("Screen Sizes" , Integer.toString(width) + Integer.toString(height));




        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day = calanderView.getDayOfMonth();
                month = calanderView.getMonth() + 1;
                year = calanderView.getYear();
                if (!isNetworkOnline()) {
                    alertNetworkToast();
                }

                String selectedDateString = Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year);

              //  testToast(selectedDateString);
                Intent dateIntent = new Intent();
                dateIntent.putExtra("selectedDate", selectedDateString);
                setResult(RESULT_OK, dateIntent);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




        moPubView = (MoPubView) findViewById(R.id.adview);
        moPubView.setAdUnitId("62289a9c4fe6411f8cfc5e8901b2c856"); // Enter your Ad Unit ID from www.mopub.com
        moPubView.loadAd();


 
        monthDown = (Button) findViewById(R.id.monthDown);
        monthUp = (Button) findViewById(R.id.monthUp);



        monthDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calanderView.getYear();
                int month = calanderView.getMonth();
                if(month > 0)
                month--;
                else
                {
                    month = 11;
                    year--;
                }
                int day = calanderView.getDayOfMonth();

                calanderView.updateDate(year, month, day);


            }
        });
        monthUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calanderView.getYear();
                int month = calanderView.getMonth();
                if(month < 11)
                month++;
                else
                {
                    month = 0;
                    year++;
                }
                int day = calanderView.getDayOfMonth();

                calanderView.updateDate(year, month, day);
            }

        });

    }
    public boolean isNetworkOnline() {
        boolean status=false;
        try{
            ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
                status= true;
            }else {
                netInfo = cm.getNetworkInfo(1);
                if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
                    status= true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return status;

    }
    public void alertNetworkToast()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Network Disable");
        alertDialog.setMessage("Please turn on your Internet Connection");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.show();
    }
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        //Intent intent = new Intent("xper.activity.ACTIVITY_BAR_RESULT_INTENT");
        //intent.putExtra("codBar", "bar");
        //setResult(Activity.RESULT_CANCELED, intent);
        Intent dateIntent = new Intent();
        setResult(Activity.RESULT_CANCELED,dateIntent);
        finish();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        moPubView.destroy();
    }

}