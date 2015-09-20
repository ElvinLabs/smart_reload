package edu.sfsu.cs.orange.ocr;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class shareCredit extends Activity {

    Button share;
    EditText amout;
//    EditText number;

    Button buttonReadContact;
    EditText number;

    final int RQS_PICKCONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_credit);
        share = (Button) findViewById(R.id.share_btn);

        buttonReadContact = (Button)findViewById(R.id.contactbtn);
        number = (EditText)findViewById(R.id.number);

        buttonReadContact.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                //Start activity to get contact
                final Uri uriContact = ContactsContract.Contacts.CONTENT_URI;
                Intent intentPickContact = new Intent(Intent.ACTION_PICK, uriContact);
                startActivityForResult(intentPickContact, RQS_PICKCONTACT);
            }});



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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if(resultCode == RESULT_OK){
            if(requestCode == RQS_PICKCONTACT){
                Uri returnUri = data.getData();
                Cursor cursor = getContentResolver().query(returnUri, null, null, null, null);

                if(cursor.moveToNext()){
                    int columnIndex_ID = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                    String contactID = cursor.getString(columnIndex_ID);

                    int columnIndex_HASPHONENUMBER = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
                    String stringHasPhoneNumber = cursor.getString(columnIndex_HASPHONENUMBER);

                    if(stringHasPhoneNumber.equalsIgnoreCase("1")){
                        Cursor cursorNum = getContentResolver().query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactID,
                                null,
                                null);

                        //Get the first phone number
                        if(cursorNum.moveToNext()){
                            int columnIndex_number = cursorNum.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                            String stringNumber = cursorNum.getString(columnIndex_number);
                            number.setText(stringNumber);
                        }

                    }else{
                        number.setText("NO Phone Number");
                    }


                }else{
                    Toast.makeText(getApplicationContext(), "NO data!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}
