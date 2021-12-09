package ca.nait.dmit.dmit2504.chattergen2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import ca.nait.dmit.dmit2504.chattergen2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private final String KEY_RESPONSE_CODE = "ca.nait.dmit.dmit2504.chattergen2.RESPONSE_CODE";

    // View Binding Step 2
    private ActivityMainBinding binding;

    private Handler postDataHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);

            Bundle bundle = msg.getData();
            int responseCode = bundle.getInt(KEY_RESPONSE_CODE);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Toast.makeText(MainActivity.this, "Send Message was successful", Toast.LENGTH_SHORT).show();
                binding.activityMainMessageEdittext.setText("");
            } else {
                String message = String.format("Error sending with code %d", responseCode);
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        // View Binding Step 3
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    public void onSendClick(View view) {
        Map<String, String> requestFormDataMap = new HashMap<>();
        requestFormDataMap.put("DATA",binding.activityMainMessageEdittext.getText().toString());
        requestFormDataMap.put("LOGIN_NAME","dmit2504swu");
        NetworkAPI networkAPI = new NetworkAPI();
        final String urlString = "https://capstone1.app.dmitcapstone.ca/api/jitters/JitterServlet";

        CompletableFuture<Void> postDataFuture = CompletableFuture.runAsync(() -> {
            int responseCode = networkAPI.postFormData(urlString, requestFormDataMap);

            Message msg = postDataHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_RESPONSE_CODE, responseCode);
            msg.setData(bundle);
            postDataHandler.sendMessage(msg);

        });

//        int responseCode = networkAPI.postFormData(urlString, requestFormDataMap);
//        if (responseCode == HttpURLConnection.HTTP_OK) {
//            Toast.makeText(this, "Send Message was successful", Toast.LENGTH_SHORT).show();
//            binding.activityMainMessageEdittext.setText("");
//        } else {
//            String message = String.format("Error sending with code %d", responseCode);
//            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu to add items to the action bar if present
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Handle action bar items clicks here.
        switch (item.getItemId()) {
            case R.id.menu_activity_main_viewchatters: {
                // Close the current activity instead of starting a new activity
                finish();

                return true;
            }
            case R.id.menu_activity_main_settings: {
                Intent startSettingsActivityIntent = new Intent(this, SettingsActivity.class);
                startActivity(startSettingsActivityIntent);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        int titleFontSize = Integer.parseInt(settings.getString("preference_fontsize_title","28"));
        int contentAreaFontSize = Integer.parseInt(settings.getString("preference_fontsize_contentarea","12"));
        binding.activityMainTitleTextview.setTextSize(titleFontSize);
        binding.activityMainMessageEdittext.setTextSize(contentAreaFontSize);
        binding.activityMainSendButton.setTextSize(contentAreaFontSize);
    }
}