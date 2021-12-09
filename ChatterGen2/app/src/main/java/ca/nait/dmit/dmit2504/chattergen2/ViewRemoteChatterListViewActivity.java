package ca.nait.dmit.dmit2504.chattergen2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import ca.nait.dmit.dmit2504.chattergen2.databinding.ActivityViewRemoteChatterListViewBinding;

public class ViewRemoteChatterListViewActivity extends AppCompatActivity {

    private ChatterListViewAdapter chatterAdapter = new ChatterListViewAdapter();
    private ActivityViewRemoteChatterListViewBinding binding;

    private final String KEY_RESPONSE_BODY = "ca.nait.dmit.dmit2504.chattergen2.RESPONSE_BODY";
    private Handler getDataHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);

            Bundle bundle = msg.getData();
            String responseString = bundle.getString(KEY_RESPONSE_BODY);
            try {
                BufferedReader reader = new BufferedReader(new StringReader(responseString));
                String line;
                String newLineCharacter = System.getProperty("line.separator");
                while ((line = reader.readLine()) != null) {
                    Chatter currentChatter = new Chatter();
                    currentChatter.setLoginName(line);
                    line = reader.readLine();
                    currentChatter.setMessage(line);
                    line= reader.readLine();
                    currentChatter.setDate(line);

                    chatterAdapter.addItem(currentChatter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_remote_chatter_list_view);

        binding = ActivityViewRemoteChatterListViewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.activityViewRemoteChatterListViewListview.setAdapter(chatterAdapter);

        NetworkAPI networkAPI = new NetworkAPI();
        final String urlString = "https://capstone1.app.dmitcapstone.ca/api/jitters/JitterServlet";
        CompletableFuture<Void> getDataFuture = CompletableFuture.runAsync(() -> {
            try {
                String responseBody = networkAPI.getResponseString(urlString);
                Message msg = getDataHandler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString(KEY_RESPONSE_BODY, responseBody);
                msg.setData(bundle);
                getDataHandler.sendMessage(msg);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu to add items to the action bar if present
        getMenuInflater().inflate(R.menu.menu_activity_viewchatters, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Handle action bar items clicks here.
        switch (item.getItemId()) {
            case R.id.menu_activity_viewchatters_mainactivity: {
                Intent startMainActivityIntent = new Intent(this, MainActivity.class);
                startActivity(startMainActivityIntent);
                return true;
            }
            case R.id.menu_activity_viewchatters_settings: {
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
        binding.activityViewRemoteChatterListViewTitleTextview.setTextSize(titleFontSize);
        chatterAdapter.notifyDataSetChanged();
    }

}