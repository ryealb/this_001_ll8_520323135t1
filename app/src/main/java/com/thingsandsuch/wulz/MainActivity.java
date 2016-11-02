package com.thingsandsuch.wulz;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



public class MainActivity extends AppCompatActivity {


    // path variables to connect to internets
    // not sure - think its random
    private static final String STATE = "MY_RANDOM_STRING_1";
    // registerd reddit app id
    private static final String CLIENT_ID = "_8KdmArtAKAhrA";
    // fake url that ?intent goes to after ?completed - url set as intent catcher thing in manifest
    private static final String REDIRECT_URI = "http://www.wulz.com/my_redirect";
    // location of login token
    private static final String ACCESS_TOKEN_URL = "https://www.reddit.com/api/v1/access_token";
    // login page url
    private static final String AUTH_URL =
            "https://www.reddit.com/api/v1/authorize.compact?client_id=%s" +
                    "&response_type=code&state=%s&redirect_uri=%s&" +
                    "duration=permanent&scope=identity";



    // runs on appp start - android running intent?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    // build intent and pass out to android to do the thing
    public void start_sign_in(View view){
        String url = String.format(AUTH_URL, CLIENT_ID, STATE, REDIRECT_URI);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }


    // get login token from reddit app thingy
    // token valid for 1 hr
    private void getAccessToken(String code) {
        OkHttpClient client = new OkHttpClient();
        String authString = CLIENT_ID + ":";
        String encodedAuthString = Base64.encodeToString(authString.getBytes(),
                Base64.NO_WRAP);

        Request request = new Request.Builder()
                .addHeader("User-Agent", "Sample App")
                .addHeader("Authorization", "Basic " + encodedAuthString)
                .url(ACCESS_TOKEN_URL)
                .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),
                        "grant_type=authorization_code&code=" + code +
                                "&redirect_uri=" + REDIRECT_URI))
                .build();


        // put internet request in android thread queue
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            //     Log.e(TAG, "ERROR: " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();

                JSONObject data = null;
                try {
                    data = new JSONObject(json);
                    String accessToken = data.optString("access_token");
                    String refreshToken = data.optString("refresh_token");
                    //  Log.d(TAG, "Access Token = " + accessToken);
                    //  Log.d(TAG, "Refresh Token = " + refreshToken);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    // pickup on intent passed from android - tell android to run it in manifest
    @Override
    protected void onResume() {
        super.onResume();

        if(getIntent()!=null && getIntent().getAction().equals(Intent.ACTION_VIEW)) {
            // pick up returned data from the intent
            Uri uri = getIntent().getData();

            // check internal data for
            if(uri.getQueryParameter("error") != null) {
                String error = uri.getQueryParameter("error");
                // Log.e(TAG, "An error has occurred : " + error);
            } else {
                // sucess
                String state = uri.getQueryParameter("state");
                if(state.equals(STATE)) {
                    String code = uri.getQueryParameter("code");
                    getAccessToken(code);

                    Toast.makeText(getApplicationContext(), "login be doned", Toast.LENGTH_LONG).show();




                }
            }
        }
    }
}



//    public void do_the_thing(View view) {
//        EditText led_user_name = (EditText) findViewById(R.id.led_user_name);
//        String user_name = led_user_name.getText().toString();
//
//        EditText led_password = (EditText) findViewById(R.id.led_password);
//        String password = led_password.getText().toString();
//
//
//        // check connection to internet
//        ConnectivityManager connMgr = (ConnectivityManager)
//                getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected()) {
//            Toast.makeText(getApplicationContext(), "this this this "+ user_name + password, Toast.LENGTH_LONG).show();
//
//            // login to reddit
//
//
//        } else {
//            Toast.makeText(getApplicationContext(), "no internets", Toast.LENGTH_LONG).show();
//
//        }
//    }
//
