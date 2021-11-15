package ca.nait.dmit.dmit2504;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;

import java.util.List;

import ca.nait.dmit.dmit2504.databinding.ActivityProductBinding;

public class ProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ActivityProductBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_product);

        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Cursor categoryQueryResultCursor = dbHelper.getCategoriesCursor();
        String[] fromColumnNames = {DatabaseContract.CategoryEntry.COLUMN_NAME_CATEGORYNAME};
        int[] toViewIds = {android.R.id.text1};
        int flags = 0;
        SimpleCursorAdapter categoryAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_item,
                categoryQueryResultCursor,
                fromColumnNames,
                toViewIds,
                flags);
        binding.activityProductCategorySpinner.setAdapter(categoryAdapter);

        binding.activityProductCategorySpinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        int categoryId = i == 0 ? 1 : 3;
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Category selectedCategory = dbHelper.getCategoriesList().get(i);
        int categoryId = selectedCategory.getCategoryId();

//        Cursor categoryProductQueryResultCursor = dbHelper.getProductsByCategoryId(categoryId);
//        String[] fromColumnNames = {DatabaseContract.ProductEntry.COLUMN_NAME_PRODUCTNAME};
//        int[] toViewIds = {android.R.id.text1};
//        int flags = 0;
//        SimpleCursorAdapter categoryProductAdapter = new SimpleCursorAdapter(this,
//                android.R.layout.simple_list_item_1,
//                categoryProductQueryResultCursor,
//                fromColumnNames,
//                toViewIds,
//                flags);
//        binding.activityProductListview.setAdapter(categoryProductAdapter);

        List<Product> productsByCategory = dbHelper.getProductsListByCategoryId(categoryId);
        ProductRecyclerViewAdapter recyclerViewAdapter = new ProductRecyclerViewAdapter(this,productsByCategory);
        binding.activityProductRecyclerview.setAdapter(recyclerViewAdapter);
        binding.activityProductRecyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}