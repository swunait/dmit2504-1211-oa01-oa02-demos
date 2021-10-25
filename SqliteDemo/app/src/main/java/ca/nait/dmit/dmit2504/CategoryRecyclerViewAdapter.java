package ca.nait.dmit.dmit2504;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;

// Step 2: extends RecyclerView.Adapter super class
public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.CategoryViewHolder> {

    // Step 3: Defines fields for the data source
    private Context context;
    private List<Category> categories;

    // Step 4: Create a parameterized constructor
    public CategoryRecyclerViewAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }
    public CategoryRecyclerViewAdapter(Context context) {
        this.context = context;
    }
    public void addItem(Category newCategory) {
        categories.add(newCategory);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_item_category, parent, false);
        CategoryViewHolder viewHolder = new CategoryViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category currentCategory = categories.get(position);
        holder.categoryIdTextView.setText("" + currentCategory.getCategoryId());
        holder.categoryNameTextView.setText(currentCategory.getCategoryName());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    // Step 1: Create a ViewHolder class that define the views for a single item
    public class CategoryViewHolder extends ViewHolder {

        public TextView categoryIdTextView;
        public TextView categoryNameTextView;
        public ImageButton deleteButton;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryIdTextView = itemView.findViewById(R.id.list_item_category_id_textview);
            categoryNameTextView = itemView.findViewById(R.id.list_item_category_categoyrname_textview);
            deleteButton = itemView.findViewById(R.id.list_item_category_delete_button);

            deleteButton.setOnClickListener(view -> {
                int position = getAdapterPosition();
//                Toast.makeText(itemView.getContext(),"Delete catgeory at index " + position, Toast.LENGTH_SHORT).show();

                Category currentCategory = categories.get(position);
                Intent deleteCategoryIntent = new Intent();
                deleteCategoryIntent.setAction(MainActivity.INTENT_ACTION_CATEGORY_DELETE);
                deleteCategoryIntent.putExtra(MainActivity.EXTRA_CATEGORY_CATEGORYID, currentCategory.getCategoryId());
                itemView.getContext().sendBroadcast(deleteCategoryIntent);

            });

        }
    }
}
