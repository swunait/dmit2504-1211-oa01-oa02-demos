package ca.nait.dmit.dmit2504;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

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
        CategoryDbHelper dbHelper = new CategoryDbHelper(this);
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
            CategoryDbHelper dbHelper = new CategoryDbHelper(this);
            long categoryId = dbHelper.addCategory(newCategory);
            String message = String.format("Save successful with id %s", categoryId);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            binding.activityMainCategorynameEdittext.setText("");
            rebindRecyclerView();
        }

    }
}