package ca.nait.dmit.dmit2054.chatterapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ViewMessagesActivity extends AppCompatActivity {

    private ListView mChatterListView;
    private ChatterListViewAdapter mChatterAdapter;
    private List<ChatterMessage> mMessageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_messages);

//        if (Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy
//                    .Builder()
//                    .permitAll()
//                    .build();
//            StrictMode.setThreadPolicy(policy);
//        }

        fetchChatterMessages();;

        mChatterListView = findViewById(R.id.activity_view_messages_listview);
        mChatterListView.setAdapter(mChatterAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_view_messages, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_activity_views_messsages_home:
            {
                finish();
                break;
            }
        }
        return true;
    }

    private void fetchChatterMessages() {
        mMessageList.clear();

        HttpClient webClient = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet();
        try {
            getRequest.setURI(new URI("http://www.youcode.ca/JitterServlet"));
            HttpResponse response = webClient.execute(getRequest);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line;
            String newLineCharacter = System.getProperty("line.separator");
            while ( (line = reader.readLine()) != null) {
                ChatterMessage currentChatterMessage = new ChatterMessage();
                currentChatterMessage.loginName = line;

                line = reader.readLine();
                currentChatterMessage.message = line;

                line = reader.readLine();
                currentChatterMessage.date = line;

                mMessageList.add(currentChatterMessage);
            }
            reader.close();

        } catch (Exception e) {
            Toast.makeText(this, "Error getting data fom server", Toast.LENGTH_SHORT).show();
        }

        mChatterAdapter = new ChatterListViewAdapter(mMessageList);
    }
}