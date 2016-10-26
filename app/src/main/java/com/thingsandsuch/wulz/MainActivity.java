package com.thingsandsuch.wulz;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void do_the_thing(View view) {
        EditText text = (EditText) findViewById(R.id.led_user_name);
        String text_value = text.getText().toString();

        EditText password = (EditText) findViewById(R.id.led_password);
        String password_value = password.getText().toString();

//        ConnectivityManager connMgr = (ConnectivityManager)
//                getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected()) {
        Toast.makeText(getApplicationContext(), "this this this "+text_value +password_value, Toast.LENGTH_LONG).show();

//        } else {
//            Toast.makeText(getApplicationContext(), "no internets", Toast.LENGTH_LONG).show();


        }

        // login to reddit





//    }


}
