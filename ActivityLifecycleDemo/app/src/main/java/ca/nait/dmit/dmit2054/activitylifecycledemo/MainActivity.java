package ca.nait.dmit.dmit2054.activitylifecycledemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG,"onCreate called");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(TAG,"onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(TAG,"onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(TAG,"onPause called");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(TAG,"onStop called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i(TAG,"onRestart called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG,"onDestroy called");
    }


}