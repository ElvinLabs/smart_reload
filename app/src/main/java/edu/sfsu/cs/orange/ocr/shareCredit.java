package edu.sfsu.cs.orange.ocr;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class shareCredit extends Activity {

    Button share;
    EditText amout;
    EditText number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_credit);
        share = (Button) findViewById(R.id.share_btn);


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///code
              TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
              String  no = tm.getLine1Number();
              String no2 = tm.getSimOperator();
              amout = (EditText) findViewById(R.id.amount);
              number = (EditText) findViewById(R.id.number);

              shareCrdt(no2,amout.getText().toString(),number.getText().toString());


            }
        });
    }


    // balance check  function
    public boolean shareCrdt(String provider,String amout,String number){
        String ussdCode="";

        // Mobitel
        if (provider.equals("41301")) {
            ussdCode = "*448*"+number+"*"+amout+Uri.encode("#");
//        }else if(no2.equals("41302")){
//            //Dialog
//            ussdCode = "*"+Uri.encode("#") + "456" + Uri.encode("#");
//        }else if(no2.equals("41303")){
//            //Etisalate
//            ussdCode = "*" + "134" + Uri.encode("#");
//        }else if(no2.equals("41305")){
//            //Airtel
//            ussdCode = "*" + "550" + Uri.encode("#");
//        }else if(no2.equals("41308")){
//            //Hutch
//            ussdCode = "*" + "344" + Uri.encode("#");
        }else{
            //Error
            return false;
        }

        startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + ussdCode)));
        return true;

    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_share_credit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
