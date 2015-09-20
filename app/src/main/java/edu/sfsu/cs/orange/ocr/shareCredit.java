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
    private TelephonyManager tm;
    private  String no;
    private  String no2;
    EditText amout;
    EditText number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_credit);
        share = (Button) findViewById(R.id.share_btn);
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        no = tm.getLine1Number();
        no2 = tm.getSimOperator();

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///code
                amout = (EditText) findViewById(R.id.amount);
                number = (EditText) findViewById(R.id.number);

                //shareCrdt(no2,amout.getText().toString(),number.getText().toString());


            }
        });
    }


    // balance check  function
    public void shareCrdt(String provider,String amout,String number){
        String ussdCode="";

        if (provider.equals(no2)) {
            ussdCode = "*" + "100" + Uri.encode("#");
        }
        startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + ussdCode)));
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
