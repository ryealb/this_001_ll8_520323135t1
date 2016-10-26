package com.thingsandsuch.wulz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void do_the_thing() {

        Toast.makeText(getApplicationContext(), "this this this", Toast.LENGTH_LONG).show();
    }


}
