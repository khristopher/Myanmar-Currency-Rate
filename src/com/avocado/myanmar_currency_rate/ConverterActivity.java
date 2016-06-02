package com.avocado.myanmar_currency_rate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.mopub.mobileads.MoPubView;

import java.util.ArrayList;

/**
 * Created by kyawmyintcho on 5/27/14.
 */



public class ConverterActivity extends Activity implements AdapterView.OnItemSelectedListener {
    MoPubView moPubView;
    TextView dateView;
    TextView fromView;
    TextView toView;
    int counter = 0;



    String foreignCurrencyName = "";
    double foreignCurrencyValue = 0;
    double answer = 0;


    Spinner fromSpinner;
    Spinner toSpinner;

    ArrayList<CurrencyForSpinner> fromList;
    ArrayList<CurrencyForSpinner> toList;
    ArrayAdapter<CurrencyForSpinner> fromAdapter;
    ArrayAdapter<CurrencyForSpinner> toAdapter;

    //button
    Button no1;
    Button no2;
    Button no3;
    Button no4;
    Button no5;
    Button no6;
    Button no7;
    Button no8;
    Button no9;
    Button no0;
    Button btnDecimal;
    Button btnClear;
    Button btnConvert;
    Button backSpace;
    Button goBack;



    //variable for calcuating currency conversion
    double textViewmmk = 0;
    double textViewForeignCurrency = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversion);

        fromView = (TextView)findViewById(R.id.from);
        toView = (TextView) findViewById(R.id.to);

        no1 = (Button) findViewById(R.id.no1);
        no2 = (Button) findViewById(R.id.no2);
        no3 = (Button) findViewById(R.id.no3);
        no4 = (Button) findViewById(R.id.no4);
        no5 = (Button) findViewById(R.id.no5);
        no6 = (Button) findViewById(R.id.no6);
        no7 = (Button) findViewById(R.id.no7);
        no8 = (Button) findViewById(R.id.no8);
        no9 = (Button) findViewById(R.id.no9);
        no0 = (Button) findViewById(R.id.no0);
        btnDecimal = (Button) findViewById(R.id.decimal);
        btnClear = (Button) findViewById(R.id.clear);
        btnConvert = (Button) findViewById(R.id.convert);
        backSpace = (Button) findViewById(R.id.backArrow);
        goBack = (Button) findViewById(R.id.goBack);







        fromSpinner = (Spinner) findViewById(R.id.fromSpinner);
        toSpinner = (Spinner) findViewById(R.id.toSpinner);

        fromList = new ArrayList<CurrencyForSpinner>();
        toList = new ArrayList<CurrencyForSpinner>();



        dateView = (TextView) findViewById(R.id.dateView);

        //for Spinner Selection




        Intent convertIntent = getIntent();
        Bundle extras = convertIntent.getExtras();
        if(extras != null)
        {
           foreignCurrencyName = extras.getString("FOREIGN_CURRENCY_NAME");
            String value = extras.getString("FOREIGN_CURRENCY_VALUE");
            foreignCurrencyValue = Double.parseDouble(value);
            String date = extras.getString("DATE");
            dateView.setText(date);




        CurrencyForSpinner mmcurrency = new CurrencyForSpinner("Myanmar Kyats",1);
        CurrencyForSpinner foreigncurrency = new CurrencyForSpinner(foreignCurrencyName,foreignCurrencyValue);



        fromList.add(foreigncurrency);
        fromList.add(mmcurrency);

        toList.add(foreigncurrency);
        toList.add(mmcurrency);
        }
        fromAdapter = new ArrayAdapter<CurrencyForSpinner>(this,android.R.layout.simple_list_item_1, fromList);
        toAdapter = new ArrayAdapter<CurrencyForSpinner>(this,android.R.layout.simple_list_item_1,toList);

        fromSpinner.setAdapter(fromAdapter);
        toSpinner.setAdapter(toAdapter);

        fromSpinner.setOnItemSelectedListener(this);
        toSpinner.setOnItemSelectedListener(this);


        //For advertisement
        moPubView = (MoPubView) findViewById(R.id.adview);
        moPubView.setAdUnitId("62289a9c4fe6411f8cfc5e8901b2c856"); // Enter your Ad Unit ID from www.mopub.com
        moPubView.loadAd();

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //calculation
        no1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter <= 10)
                {
                 fromView.append("1");
                 counter++;
                }
            }
        });
        no2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter <= 10)
                {
                    fromView.append("2");
                    counter++;
                }
            }
        });
        no3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter <= 10)
                {
                    fromView.append("3");
                    counter++;
                }
            }
        });
        no4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter <= 10)
                {
                    fromView.append("4");
                    counter++;
                }
            }
        });no5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter <= 10)
                {
                    fromView.append("5");
                    counter++;
                }
            }
        });no6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter <= 10)
                {
                    fromView.append("6");
                    counter++;
                }
            }
        });no7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter <= 10)
                {
                    fromView.append("7");
                    counter++;
                }
            }
        });no8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter <= 10)
                {
                    fromView.append("8");
                    counter++;
                }
            }
        });
        no9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter <= 10)
                {
                    fromView.append("9");
                    counter++;
                }
            }
        });
        no0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter <= 10)
                {
                    fromView.append("0");
                    counter++;
                }
            }
        });
        btnDecimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter <= 10)
                {
                    fromView.append(".");
                    counter++;
                }
            }
        });
        backSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String oldString = fromView.getText().toString();
                String newString = "";
                if(oldString.length() != 0)
                {

                    newString = oldString.substring(0,oldString.length()-1);
                    counter--;
                    fromView.setText(newString);
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                fromView.setText(null);
                counter = 0;
                toView.setText(null);
            }
        });

        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   calculateConversion();
            }
        }
        );




    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
           if(parent.equals(fromSpinner))
           {
               if(position == 1)
               {
                   toSpinner.setSelection(0);
               }
               else
               {
                   toSpinner.setSelection(1);

               }
               swipeValueFromTextView();
           }
        else
           {
               if(position == 1)
               {
                   fromSpinner.setSelection(0);
                   swipeValueFromTextView();

               }
               else
               {
                   fromSpinner.setSelection(1);
                   swipeValueFromTextView();

               }
               swipeValueFromTextView();
           }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void swipeValueFromTextView()
    {
        String from = fromView.getText().toString();
        String to = toView.getText().toString();
        counter = to.length();
        fromView.setText(to);
        toView.setText(from);

    }

    public boolean checkValid(String aString)
    {
        char[] aCharArray = aString.toCharArray();
        int numberOfDecimal = 0;
        for(int i = 0; i < aCharArray.length; i++)
        {
            if(aCharArray[i] == '.')
            {
                numberOfDecimal++;
            }

        }
        if(numberOfDecimal > 1)
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    private void alertMoreThanOneDecimal() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Cannot Calculate");
        alertDialog.setMessage("Invalid format. Please Check your currency input");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.show();
    }


    public void calculateConversion()
    {
       String temp = fromView.getText().toString();
        if(checkValid(temp) != true)
        {
            alertMoreThanOneDecimal();
        }

        else if(temp == null || temp.isEmpty() || temp.equals("") || temp.equals("."))
        {}
        else
        {

          if(fromSpinner.getSelectedItemPosition() == 0)
          {  // Toast.makeText(this,"Now selecting foreign",Toast.LENGTH_LONG).show();

              textViewForeignCurrency = Double.parseDouble(temp);
              answer = textViewForeignCurrency * foreignCurrencyValue ;

              toView.setText(String.format("%.2f", answer));
             // toView.setText(Double.toString(answer));

          }
        else
          {
               textViewmmk = Double.parseDouble(temp);
              answer =  textViewmmk / foreignCurrencyValue;
             // toView.setText(Double.toString(answer));
              toView.setText( String.format( "%.2f", answer ) );
          }
        }

    }

    //Go back to mainScreen
    public void onBackPressed()
    {
        //this.startActivity(new Intent(ConverterActivity.this,MyActivity.class));
        super.onBackPressed();
        finish();

    }


    public class CurrencyForSpinner
    {
        String name;
        double value;

        public CurrencyForSpinner(String n, double d)
        {
            name = n;
            value = d;
        }

        public String getName()
        {
            return name;
        }
        public double getValue()
        {
            return value;
        }
        public String toString()
        {
            return name;
        }
    }
}