package ca.nait.dmit.dmit2504;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;

// Step 2: extends RecyclerView.Adapter super class
public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder> {

    // Step 3: Defines fields for the data source
    private Context context;
    private List<Product> products;

    // Step 4: Create a parameterized constructor
    public ProductRecyclerViewAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }
    public ProductRecyclerViewAdapter(Context context) {
        this.context = context;
    }
    public void addItem(Product newProduct) {
        products.add(newProduct);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_item_product, parent, false);
        ProductViewHolder viewHolder = new ProductViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product currentProduct = products.get(position);
        holder.productIdTextView.setText("" + currentProduct.getProductId());
        holder.productNameTextView.setText(currentProduct.getProductName());
        holder.unitPriceTextView.setText("" + currentProduct.getUnitPrice());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    // Step 1: Create a ViewHolder class that define the views for a single item
    public class ProductViewHolder extends ViewHolder {

        public TextView productIdTextView;
        public TextView productNameTextView;
        public TextView unitPriceTextView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            productIdTextView = itemView.findViewById(R.id.list_item_product_productId_textview);
            productNameTextView = itemView.findViewById(R.id.list_item_product_productname_textview);
            unitPriceTextView = itemView.findViewById(R.id.list_item_product_unitprice_textview);

        }
    }
}
