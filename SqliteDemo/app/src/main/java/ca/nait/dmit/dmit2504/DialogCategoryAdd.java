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

public class DialogCategoryAdd extends DialogFragment {

    private MainActivity mainActivity;

    public DialogCategoryAdd(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_category_edit, null);

        EditText categoryNameEditText = dialogView.findViewById(R.id.dialog_category_edit_categoryname_edittext);
        categoryNameEditText.requestFocus();

        Button cancelButton = dialogView.findViewById(R.id.dialog_category_edit_cancel_button);
        Button saveButton = dialogView.findViewById(R.id.dialog_category_edit_save_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        saveButton.setOnClickListener(view -> {
            String categoryName = categoryNameEditText.getText().toString();
            if (categoryName.isEmpty()) {
                Toast.makeText(getActivity(), "CategoryName is required", Toast.LENGTH_SHORT).show();
            } else {
                Category newCategory = new Category();
                newCategory.setCategoryName(categoryName);
                mainActivity.addCategory(newCategory);
                dismiss();
            }
        });

        builder.setView(dialogView).setTitle("New Category");
        return builder.create();
    }
}
