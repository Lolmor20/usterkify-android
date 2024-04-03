package nimm.usterkify.activity.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import nimm.usterkify.R;

public class UsterkaDetailsDialogFragment extends DialogFragment {

    private EditText titleEditText;
    private EditText descriptionEditText;

    public void setOnSubmitClickListener(OnSubmitClickListener onSubmitClickListener) {
        this.onSubmitClickListener = onSubmitClickListener;
    }

    private OnSubmitClickListener onSubmitClickListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_layout, null);

        titleEditText = dialogView.findViewById(R.id.titleEditText);
        descriptionEditText = dialogView.findViewById(R.id.descriptionEditText);

        builder.setView(dialogView)
                .setTitle("Enter Title and Description")
                .setPositiveButton("OK", (dialog, which) -> {
                    String title = titleEditText.getText().toString();
                    String description = descriptionEditText.getText().toString();
                    if (onSubmitClickListener != null) {
                        onSubmitClickListener.onSubmit(title, description);
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        return builder.create();
    }

    public interface OnSubmitClickListener {
        void onSubmit(String title, String description);
    }
}
