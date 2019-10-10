package com.frenchfrie.singasong.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.frenchfrie.singasong.R;
import com.frenchfrie.singasong.TasksService;
import com.frenchfrie.singasong.songs.Couplet;
import com.frenchfrie.singasong.songs.Song;
import com.frenchfrie.singasong.songs.SongsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;


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
        if (super.onMenuItemSelected(featureId, item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.add_song:
                handleImport();
                return true;
            case R.id.export_songs:
                handleExport();
                return true;
            case R.id.generate_songs:
                handleSongGeneration();
                return true;
            case R.id.app_bar_search:
                handleSearch();
                return true;
        }
        return false;
    }

    private void handleImport() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        startActivityForResult(intent, REQUEST_CODE);

    }

    private void handleExport() {
        final SongsService songsService = SongsService.getSongsService(this);
        TasksService.getInstance().submitTask(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                try {
                    songsService.exportToFile();
                    Toast.makeText(SongListActivity.this, "Export successful", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Log.e(SongListActivity.class.getName(), "Error exporting file", e);
                    Toast.makeText(SongListActivity.this, getString(R.string.export_error) + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
                return null;
            }
        });
    }

    private void handleSongGeneration() {
        TasksService.getInstance().submitTask(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                SongsService songsService = SongsService.getSongsService(SongListActivity.this);
                List<Song> songsToSave = new ArrayList<>(100);
                for (int i = 0; i < 100; i++) {
                    List<Couplet> couplets = Arrays.asList(
                            new Couplet("first couplet image", "first couplet verses"),
                            new Couplet("second couplet image", "second couplet verses"));
                    songsToSave.add(new Song("my title #" + i, "my description", "my author", "my raw lyrics", couplets,
                            new Couplet("my chorus image", "my chorus verses"), "my recording file name"));
                }
                songsService.save(songsToSave);
                return null;
            }
        });
    }

    private void handleSearch() {
        Toast.makeText(this, "Helo search", Toast.LENGTH_SHORT).show();
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
