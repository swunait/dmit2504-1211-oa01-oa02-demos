package ca.nait.dmit.dmit2504;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {

    private List<Category> categoryList;

    public CategoryAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Category getItem(int i) {
        return categoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).getCategoryId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View spinnerItemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.spinner_item_category, viewGroup, false);

        TextView categoryNameText = spinnerItemView.findViewById(R.id.spinner_item_category_categoryname_textview);
        Category currentCategory = getItem(i);
        categoryNameText.setText(currentCategory.getCategoryName());



        return spinnerItemView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View spinnerDropDownItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.spinner_dropdown_item_category, parent, false);

        TextView categoryNameText = spinnerDropDownItemView.findViewById(R.id.spinner_dropdown_item_category_categoryname_textview);
        TextView categoryIdText = spinnerDropDownItemView.findViewById(R.id.spinner_dropdown_item_category_categoryid_textview);

        Category currentCategory = getItem(position);
        categoryNameText.setText(currentCategory.getCategoryName());
        categoryIdText.setText(String.valueOf(currentCategory.getCategoryId()));

        return spinnerDropDownItemView;
    }
}
