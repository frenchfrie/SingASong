package org.frenchfrie.chantons;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import roboguice.activity.RoboFragmentActivity;
import roboguice.event.Observes;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import roboguice.util.Ln;


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
@ContentView(R.layout.activity_song_list)
public class SongListActivity extends RoboFragmentActivity {

    @InjectView(R.id.song_detail_container)
    private View songDetailContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: If exposing deep links into your app, handle intents here.
    }

    // TODO: maybe this method belong elsewhere...
    public void itemSelected(@Observes SongListFragment.OnSongSelected songSelected) {
        Ln.d("Song %s selected", songSelected.songId);
        int songId = songSelected.songId;
        // In single-pane mode, simply start the detail activity
        // for the selected item ID.
        Intent detailIntent = new Intent(this, SongDetailActivity.class);
        detailIntent.putExtra(SongDetailFragment.ARG_ITEM_ID, songId);
        startActivity(detailIntent);
    }
}
