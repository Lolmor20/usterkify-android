package nimm.usterkify;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class UsterkaDetailsDialogFragment extends DialogFragment {

    private EditText titleEditText;
    private EditText descriptionEditText;

    public void setOnSubmitClickListener(OnSubmitClickListener onSubmitClickListener) {
        this.onSubmitClickListener = onSubmitClickListener;
    }

    private OnSubmitClickListener onSubmitClickListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_layout, null);

        titleEditText = dialogView.findViewById(R.id.titleEditText);
        descriptionEditText = dialogView.findViewById(R.id.descriptionEditText);

        builder.setView(dialogView)
                .setTitle("Enter Title and Description")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = titleEditText.getText().toString();
                        String description = descriptionEditText.getText().toString();
                        if (onSubmitClickListener != null) {
                            onSubmitClickListener.onSubmit(title, description);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }

    public interface OnSubmitClickListener {
        void onSubmit(String title, String description);
    }
}
