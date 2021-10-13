package ca.nait.dmit.dmit2054.threadingdemo;

// View Binding Setup Step 2
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import ca.nait.dmit.dmit2054.threadingdemo.databinding.ActivityMainBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.concurrent.CompletableFuture;

public class MainActivity extends AppCompatActivity {

    // View Binding Setup Step 3
    private ActivityMainBinding binding;

    private Handler mainThreadHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);

            Bundle bundle = msg.getData();
            String result = bundle.getString("RESULT_KEY");
            binding.resultTextView.setText(result);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // View Binding Setup Step 4
//        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

    }

    private String fetchFromServer() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Data returned from server";
    }

    private String processData(String data) {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data.toUpperCase();
    }

    private String calculateFirstResult(String data) {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String message = String.format("Length of data is %d", data.length());
        return message;
    }

    private String calculateSecondResult(String data) {
        try {
            Thread.sleep(4000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data.replaceAll("Data","Information");
    }

    public void onSubmitClick(View view) {

        CompletableFuture<Void> fetchDataFuture = CompletableFuture.supplyAsync(() -> {
            String serverData = fetchFromServer();
            String processedData = processData(serverData);
            String firstResult = calculateFirstResult(processedData);
            String secondResult = calculateSecondResult(processedData);
            String resultSummary = String.format("First result: %s\nSecond result: %s", firstResult, secondResult);
            return resultSummary;
        }).thenAccept(result -> {
//            binding.resultTextView.setText(result);

//            binding.resultTextView.post(() -> {
//                binding.resultTextView.setText(result);
//            });

            Message msg = mainThreadHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("RESULT_KEY", result);
            msg.setData(bundle);
            mainThreadHandler.sendMessage(msg);


        });

    }
}