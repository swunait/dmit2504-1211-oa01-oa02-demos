package ca.nait.dmit.dmit2054.chatterapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MainActivity extends AppCompatActivity {

    private EditText mMessageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy
                    .Builder()
                    .permitAll()
                    .build();
            StrictMode.setThreadPolicy(policy);
        }

        mMessageEditText = findViewById(R.id.activity_main_message_edittext);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_activity_main_view_messages:
            {
                Intent openViewMessagesIntent = new Intent(this, ViewMessagesActivity.class);
                startActivity(openViewMessagesIntent);
                break;
            }
        }
        return true;
    }

    public void onSubmitClick(View view) {
        String message = mMessageEditText.getText().toString();
        if (message.isEmpty()) {
            Toast.makeText(this, getResources().getString(R.string.message_validation_error), Toast.LENGTH_SHORT).show();
        } else {

            HttpClient webClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost("http://www.youcode.ca/JitterServlet");
            List<NameValuePair> postParamters = new ArrayList<NameValuePair>();
            postParamters.add(new BasicNameValuePair("DATA", message));
            postParamters.add(new BasicNameValuePair("LOGIN_NAME","dmit2504swu"));
            try {
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParamters);
                postRequest.setEntity(formEntity);
                webClient.execute(postRequest);

                // Clear the EditText field
                mMessageEditText.setText("");
                Toast.makeText(this,"Send was successful", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this,"Send was not successful", Toast.LENGTH_SHORT).show();
//                Log.e("MainActivity",e.getMessage());
                e.printStackTrace();
            }

        }

    }
}