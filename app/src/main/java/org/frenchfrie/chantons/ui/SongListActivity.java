package org.frenchfrie.chantons.ui;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import org.frenchfrie.chantons.R;
import org.frenchfrie.chantons.songs.SongsService;

import java.io.IOException;


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

    public static final int REQUEST_CODE = 65436;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, @NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_song) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("file/*");
            startActivityForResult(intent, REQUEST_CODE);
        } else if (item.getItemId() == R.id.export_songs) {
            SongsService songsService = SongsService.getSongsService(this);
            try {
                songsService.exportToFile();
            } catch (IOException e) {
                new AlertDialog.Builder(this)
                        .setMessage("Error exporting file: " + e.getLocalizedMessage())
                        .show();
            }
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode < 0) {
            Uri fileToImportURI = data.getData();
            SongsService songsService = SongsService.getSongsService(this);
            try {
                songsService.importFile(fileToImportURI);
                recreate();
            } catch (IOException e) {
                new AlertDialog.Builder(this)
                        .setMessage("Error importing file: " + e.getLocalizedMessage())
                        .show();
            }
        }
    }

}
