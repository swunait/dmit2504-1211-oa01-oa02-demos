package ca.nait.dmit.dmit2504;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import ca.nait.dmit.dmit2504.databinding.ActivitySpinnerDemoBinding;

public class SpinnerDemoActivity extends AppCompatActivity {

    private ActivitySpinnerDemoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_spinner_demo);

        binding = ActivitySpinnerDemoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Cursor categoriesQueryResultCursor = dbHelper.getCategoriesCursor();
        String[] fromColumnNames = {DatabaseContract.CategoryEntry.COLUMN_NAME_CATEGORYNAME};
        int[] toViewIds = {android.R.id.text1};
        int flags = 0;
        SimpleCursorAdapter categoryAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_spinner_item,
                categoriesQueryResultCursor,
                fromColumnNames,
                toViewIds,
                flags);
        binding.activitySpinnerDemoCategory1Spinner.setAdapter(categoryAdapter);

        List<Category> categoryList = dbHelper.getCategoriesList();
        CategoryAdapter spinnerCategoryAdapter = new CategoryAdapter(categoryList);
        binding.activitySpinnerDemoCategory2Spinner.setAdapter(spinnerCategoryAdapter);

        binding.activitySpinnerDemoShowcategoryButton.setOnClickListener(view -> {

//            long itemId = binding.activitySpinnerDemoCategory2Spinner.getSelectedItemId();
//            int position = binding.activitySpinnerDemoCategory2Spinner.getSelectedItemPosition();
            Category selectedCategory = (Category) binding.activitySpinnerDemoCategory2Spinner.getSelectedItem();
            String message = String.format("CategoryId: %s, CategoryName: %s",
                    selectedCategory.getCategoryId(),
                    selectedCategory.getCategoryName());
            Toast.makeText(SpinnerDemoActivity.this, message, Toast.LENGTH_SHORT).show();

        });

    }


}