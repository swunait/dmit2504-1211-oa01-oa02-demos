package ca.nait.dmit.dmit2054.spinnerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner mCategory1Spinner;
    private Spinner mCategory2Spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCategory1Spinner = findViewById(R.id.category1Spinner);
        mCategory1Spinner.setOnItemSelectedListener(this);

        mCategory2Spinner = findViewById(R.id.category2Spinner);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String[] categories = getResources().getStringArray(R.array.category_entries);
        String selectedCategory = categories[i];
        Toast.makeText(this, selectedCategory, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void onGoButtonClick(View view) {
        String selectedCategory = (String) mCategory2Spinner.getSelectedItem();
        Toast.makeText(this, selectedCategory, Toast.LENGTH_SHORT).show();
    }
}