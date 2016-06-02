package com.avocado.myanmar_currency_rate;

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.mopub.mobileads.MoPubView;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    String urlLatest = "http://forex.cbm.gov.mm/api/latest";
    String urlHistory = "http://forex.cbm.gov.mm/api/history/";
    JSONHandler jsonHandler = new JSONHandler(urlLatest);
    ArrayList<Currency> currencyArrayList = new ArrayList<Currency>();
    CurrencyNameTable currencyNameTable = new CurrencyNameTable();
    CustomList<Currency> arrayAdapter;
    ListView listView;
    Button dateButton;
    Button refresh;

    //For AsyncTask
    boolean downloading = false;


    private ProgressDialog progressDialog;

    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;




    //for advertisement
    private MoPubView moPubView;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        listView = (ListView)findViewById(R.id.listView);
        dateButton = (Button)findViewById(R.id.date);
        refresh = (Button) findViewById(R.id.refresh);
         setCurrentDate();




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   currencyConversionDialog(currencyArrayList.get(position).getName(),currencyArrayList.get(position).getValues());
            }
        }  );


        if(!isNetworkOnline())
        {
            alertNetworkToast();
        }
        else if(downloading == true)
        {

        }
        else
        {
            if(downloading == false)
            {
             downloading = true;

              jsonHandler.execute();

           }
        }

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!isNetworkOnline())
                {
                    alertNetworkToast();
                }
                else if(downloading == true)
                {

                }
                else
                {
                 setCurrentDate();
                 downloading = true;
                jsonHandler = null;
                jsonHandler = new JSONHandler(urlLatest);
                currencyArrayList = null;
                arrayAdapter = null;
                currencyArrayList = new ArrayList<Currency>();

                jsonHandler.execute();

                }
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent getSelectedDateIntent = new Intent(MyActivity.this, CalanderActivity.class);
                startActivityForResult(getSelectedDateIntent, 2);

            }
            });


        //For advertisement
        moPubView = (MoPubView) findViewById(R.id.adview);
        moPubView.setAdUnitId("62289a9c4fe6411f8cfc5e8901b2c856"); // Enter your Ad Unit ID from www.mopub.com
        moPubView.loadAd();




    }


     @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data)
     {
         super.onActivityResult(requestCode,resultCode,data);
         if(requestCode == 2)
         {
             if(resultCode == RESULT_OK)
             {
             String selectedDate = data.getStringExtra("selectedDate");
             dateButton.setText(selectedDate);

             String selectedDateWithDash = selectedDate.replace("/","-");

                 //make url with date
             String urlHistoryDate = urlHistory+selectedDateWithDash;

              //make HTTP request for selected Date


                 //download for selected Date
                 if(!isNetworkOnline())
                 {
                     alertNetworkToast();
                 }
                 else if(downloading == true)
                 {

                 }
                 else
                 {
                     downloading = true;
                     jsonHandler = null;
                     jsonHandler = new JSONHandler(urlHistoryDate);
                     currencyArrayList = null;
                     arrayAdapter = null;
                     currencyArrayList = new ArrayList<Currency>();

                     jsonHandler.execute();
                 }

             }
             else
             {
                 setCurrentDate();
             }
         }
     }



    public void currencyConversionDialog(final String foreginCurrencyName, final String foreignCurrencyValue) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MyActivity.this);
        alertDialog.setTitle("Calculate Currency Conversion");
        alertDialog.setMessage("Myanmar Kyats "+" \u21D4 "+ foreginCurrencyName + "?");


        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent converterIntent = new Intent(MyActivity.this, ConverterActivity.class);
                converterIntent.putExtra("FOREIGN_CURRENCY_NAME", foreginCurrencyName);
                converterIntent.putExtra("FOREIGN_CURRENCY_VALUE", foreignCurrencyValue );
                String date = (String) dateButton.getText();
                converterIntent.putExtra("DATE", date);


                startActivity(converterIntent);

            }
        });
        alertDialog.show();


    }

    /*  No need to use for timer, since the new dialog will automatically dimess
    public void launchRingDialog() {
        final ProgressDialog ringProgressDialog = ProgressDialog.show(MyActivity.this, "Please wait",
                "Updating latest currency exchange rate", true);
        ringProgressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep(3000);
                } catch (Exception e) {

                }
                ringProgressDialog.dismiss();
            }
        }).start();
    }

    public void launchRingDialogForSelectedDate(String date) {
        final ProgressDialog ringProgressDialog = ProgressDialog.show(MyActivity.this, "Please wait",	"Getting Currency Rate for "+ date, true);
        ringProgressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep(3000);
                } catch (Exception e) {

                }
                ringProgressDialog.dismiss();
            }
        }).start();
    }

     */
    public void setCurrentDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        dateButton.setText(sdf.format(cal.getTime()));
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
    public class JSONHandler extends AsyncTask<String, String, String> {
        String url;

        public JSONHandler(String webUrl)
        {
            url = webUrl;
        }

        public String rates = "rates";
        @Override
        protected void onPreExecute() {
           /* if(url.equals(urlLatest))
            launchRingDialog();
            else
              launchRingDialogForSelectedDate(dateButton.getText().toString());
            Log.d("urlHistory is ", "\n" +url);
            */

            progressDialog = new ProgressDialog(MyActivity.this);

            if(url.equals(urlLatest))
            {
            progressDialog.setIndeterminate(false);
           // progressDialog.setMax(100);
           // progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCancelable(true);
            progressDialog.setTitle("Please Wait...");
            progressDialog.setMessage("Updating latest currency exchange rate");
            }
            else
            {
                progressDialog.setIndeterminate(false);
                //progressDialog.setMax(100);
              //  progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setCancelable(true);
                progressDialog.setTitle("Please Wait...");
                progressDialog.setMessage("Getting Currency Rate for " + dateButton.getText().toString());
            }
            progressDialog.show();

            super.onPreExecute();
        }

        //do in background not to black UI
        protected String doInBackground(String... arg0) {


            DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());


            HttpGet httpGet = new HttpGet(url);
            InputStream inputStream = null;

            // Will hold the whole all the data gathered from the URL
            String result = null;






            try {


                //execute HTTP GET and get response
                HttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                inputStream = entity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                // Will store the data
                StringBuilder theStringBuilder = new StringBuilder();

                String line = null;


                while ((line = reader.readLine()) != null)
                {

                    // Add data from the buffer to the StringBuilder
                    theStringBuilder.append(line + "\n");
                }

                // Store the complete data in result
                result = theStringBuilder.toString();
                Log.d("Json result is/n/n", result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {

                // Close the InputStream when you're done with it
                try{if(inputStream != null)inputStream.close();}
                catch(Exception e){}
            }

            // Holds Key Value pairs from a JSON source
            JSONObject jsonObject;
            JSONObject jsonRates;
            String currencyRate;

            try {
                // Get the root JSONObject

                jsonObject = new JSONObject(result);

                currencyRate =  jsonObject.getString(rates);
                //currencyRate = jsonRates.getString(rates);
                Log.d("currencyRates is/n/n", currencyRate);
                char[] charArray = currencyRate.toCharArray();


                boolean isBegin = false;
                String tempName = "";
                String tempValue = "";
                String tempFullName = "";
                Currency tempCurrency;

                char previousChar = '"';

                //for manipulation
                for(char achar: charArray)
                {


                    if(Character.isLetter(achar))
                    {
                        tempName = tempName + achar;
                    }
                    if((Character.isDigit(achar)) || (achar == '.') )

                    {
                        tempValue = tempValue + achar;
                    }

                    if(achar == ',' && previousChar == '"')
                    {
                        tempFullName = currencyNameTable.get(tempName);
                        tempCurrency = new Currency(tempFullName,tempName,tempValue);
                        currencyArrayList.add(tempCurrency);
                        tempName = "";
                        tempValue = "";
                        tempFullName = "";

                    }
                    previousChar = achar;


                }
                 /*testing purpose
                for(Currency currency: currencyArrayList)
                {
                    Log.d("currency Array is ", "\n" +currency.toString());
                }
                 */

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch(NullPointerException e)
            {
                e.printStackTrace();
            } catch(RuntimeException e)
            {
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(String result){
            try{
            Collections.sort(currencyArrayList,new NameComperator());
                fixCurrencyArrayList();

            arrayAdapter = new CustomList<Currency>(MyActivity.this,currencyArrayList,imageId);
            listView.setAdapter(arrayAdapter);
                downloading = false;
                progressDialog.dismiss();


            }
            catch(NullPointerException e)
            {
                e.printStackTrace();
            }
        }



    }
    @Override
    protected void onPause()
    {
        super.onPause();

    }
    @Override
    protected  void onResume()
    {
       super.onResume();

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        moPubView.destroy();
    }


    //fix Laos,Vietnamese, Indonisa, Japane, Cambodia and Korea accurency issue
    public void fixCurrencyArrayList()
    {
      if(!currencyArrayList.isEmpty())
      {


        Currency temp;

          //to put U.S dollors value on the top
          temp = currencyArrayList.get(35);
          currencyArrayList.remove(35);
          currencyArrayList.add(0, temp);


          //for the rest, fix wrong value issue
          temp = currencyArrayList.get(4);
          temp = fixCurrencyError(temp);
          currencyArrayList.set(4,temp);


          temp = currencyArrayList.get(13);
          temp = fixCurrencyError(temp);
          currencyArrayList.set(13,temp);

          temp = currencyArrayList.get(15);
            temp = fixCurrencyError(temp);
            currencyArrayList.set(15,temp);

          temp = currencyArrayList.get(17);
          temp = fixCurrencyError(temp);
          currencyArrayList.set(17,temp);

          temp = currencyArrayList.get(19);
          temp = fixCurrencyError(temp);
          currencyArrayList.set(19,temp);


          //vietnamese
          temp = currencyArrayList.get(36);
          temp = fixCurrencyError(temp);
          currencyArrayList.set(36,temp);





       }
    }

    public Currency fixCurrencyError(Currency wrongCurrency)
    {
         Currency needToFix = wrongCurrency;
        String afterFixValue;
         double value = Double.parseDouble(needToFix.getValues());
         value = value / 100;
         afterFixValue = String.format("%.3f",value);
        needToFix.setValues(afterFixValue);
        return  needToFix;

    }
    Integer[] imageId = {
            R.drawable.usflag,
            R.drawable.australia,
            R.drawable.bangladesh,
            R.drawable.brazil,
            R.drawable.cambodia,
            R.drawable.canada,
            R.drawable.china,
            R.drawable.czech,
            R.drawable.denmark,
            R.drawable.egypt,
            R.drawable.europe,
            R.drawable.hong_kong,
            R.drawable.india,
            R.drawable.indonesia,
            R.drawable.israel,
            R.drawable.japan,
            R.drawable.kenya,
            R.drawable.korea,
            R.drawable.kuwait,
            R.drawable.laos,
            R.drawable.malaysia,
            R.drawable.nepal,
            R.drawable.new_zealand,
            R.drawable.norway,
            R.drawable.pakistan,
            R.drawable.phillippines,
            R.drawable.uk,
            R.drawable.russia,
            R.drawable.saudi_arabia,
            R.drawable.serbia,
            R.drawable.singapore,
            R.drawable.south_africa,
            R.drawable.sri_lanka,
            R.drawable.sweden,
            R.drawable.switzerland,
            R.drawable.thailand,
            R.drawable.vietnam,
            //remove Bruni because server remove it










    };

}
