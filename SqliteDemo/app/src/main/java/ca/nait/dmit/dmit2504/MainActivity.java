package ca.nait.dmit.dmit2504;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import ca.nait.dmit.dmit2504.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // View Binding Step 2
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // View Binding Step 3
//        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        rebindRecyclerView();
    }

    private void rebindRecyclerView() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<Category> categories = dbHelper.getCategoriesList();
        CategoryRecyclerViewAdapter recyclerViewAdapter = new CategoryRecyclerViewAdapter(this,categories);
        binding.activityMainCategoriesRecyclerview.setAdapter(recyclerViewAdapter);
        binding.activityMainCategoriesRecyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onSaveButtonClick(View view) {
        String categoryName = binding.activityMainCategorynameEdittext.getText().toString();
        if (categoryName.isEmpty()) {
            Toast.makeText(this, "Category Name is required.", Toast.LENGTH_SHORT).show();
        } else {
            Category newCategory = new Category();
            newCategory.setCategoryName(categoryName);
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            long categoryId = dbHelper.addCategory(newCategory);
            String message = String.format("Save successful with id %s", categoryId);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            binding.activityMainCategorynameEdittext.setText("");
            rebindRecyclerView();
        }

    }

    public static final String INTENT_ACTION_CATEGORY_DELETE = "ca.nait.dmit.dmit2504.CATEGORY_DELETE";
    public static final String EXTRA_CATEGORY_CATEGORYID = "ca.nait.dmit.dmit2504.CATEGORY_ID";

    class DeleteCategoryBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {
            if (intent.getAction().equals(INTENT_ACTION_CATEGORY_DELETE)) {
                int categoryId = intent.getIntExtra(EXTRA_CATEGORY_CATEGORYID, 0);
                if (categoryId > 0) {

                    // Create a delete confirmation dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Delete Confirmation")
                            .setMessage("Are you sure want to delete item " + categoryId + "?")
                            .setPositiveButton("Yes I am sure", (dialogInterface, i) -> {
                                DatabaseHelper dbHelper = new DatabaseHelper(context);
                                dbHelper.deleteCategory(categoryId);
                                rebindRecyclerView();
                                Toast.makeText(context,"Delete was successful", Toast.LENGTH_SHORT).show();
                            })
                            .setNegativeButton("No", (dialogInterface, i) -> {
                                Toast.makeText(context,"Delete cancelled", Toast.LENGTH_SHORT).show();
                            })
                            ;
                    builder.show();

                }
            }
        }
    }

    private DeleteCategoryBroadcastReceiver currentDeleteCategoryBroadcastReceiver = new DeleteCategoryBroadcastReceiver();

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter categoryDeleteIntentFilter = new IntentFilter();
        categoryDeleteIntentFilter.addAction(INTENT_ACTION_CATEGORY_DELETE);
        registerReceiver(currentDeleteCategoryBroadcastReceiver, categoryDeleteIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(currentDeleteCategoryBroadcastReceiver);
    }
}