package org.frenchfrie.chantons.ui;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import org.frenchfrie.chantons.R;


/**
 * An activity representing a list of Songs. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link SongDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link SongListFragment} and the item details
 * (if present) is a {@link SongDetailFragment}.
 */
public class SongListActivity extends FragmentActivity {

    private static final String LOG_TAG = "SongListActivity";

    private FrameLayout songDetailContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
        ActionBar actionBar = getActionBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, @NonNull MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        ImportDialogFragment dialogFragment = new ImportDialogFragment();
        dialogFragment.show(getFragmentManager(), "import dialog");

        /*
        builder.setTitle(R.string.dialog_import_title)
                .setMessage(R.string.dialog_import_message)
                .setView(R.layout.dialog_import)
                .setPositiveButton(R.string.dialog_import_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // imoprt the file
                    }
                })
                .setNegativeButton(R.string.dialog_import_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog importDialog = builder.create();
        importDialog.show();
        */
        return super.onMenuItemSelected(featureId, item);
    }

}
