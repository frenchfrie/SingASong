package org.frenchfrie.chantons.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.frenchfrie.chantons.R;

import static android.content.DialogInterface.*;

public class ImportDialogFragment extends DialogFragment {

    public static final int REQUEST_CODE = 65436;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.dialog_import, null);
        EditText filePath = (EditText) view.findViewById(R.id.file_chooser);
        builder.setTitle(R.string.dialog_import_title)
                .setMessage(R.string.dialog_import_message)
                .setView(view)
                        // Add action buttons
                .setPositiveButton(
                        R.string.dialog_import_ok,
                        new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("file/*");
                                startActivityForResult(intent, REQUEST_CODE);
                            }
                        })
                .setNegativeButton(R.string.dialog_import_cancel, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getDialog().cancel();
                    }
                });


        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {

        }
    }
}
