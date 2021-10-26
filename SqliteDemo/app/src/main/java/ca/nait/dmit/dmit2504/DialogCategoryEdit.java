package ca.nait.dmit.dmit2504;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DialogCategoryEdit extends DialogFragment {

    private Category editCategory;
    private MainActivity mainActivity;

    public DialogCategoryEdit(final Category editCategory, final MainActivity mainActivity) {
        this.editCategory = editCategory;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.dialog_category_edit, null);

        EditText categoryNameEditText = dialogView.findViewById(R.id.dialog_category_edit_categoryname_edittext);
        categoryNameEditText.setText(editCategory.getCategoryName());
        categoryNameEditText.requestFocus();

        Button cancelButton = dialogView.findViewById(R.id.dialog_category_edit_cancel_button);
        Button saveButton = dialogView.findViewById(R.id.dialog_category_edit_save_button);

        cancelButton.setOnClickListener(view -> {
            Toast.makeText(getActivity(),"Cancel edit", Toast.LENGTH_SHORT).show();
            dismiss();
        });

        saveButton.setOnClickListener(view -> {
            String categoryName = categoryNameEditText.getText().toString();
            if (categoryName.isEmpty()) {
                Toast.makeText(getActivity(), "CategoryName is required", Toast.LENGTH_SHORT).show();
            } else {
                editCategory.setCategoryName(categoryName);
                mainActivity.updateCategory(editCategory.getCategoryId(), editCategory);
                dismiss();
            }
        });

        builder.setView(dialogView).setTitle("Edit Category");

        return builder.create();
    }
}
