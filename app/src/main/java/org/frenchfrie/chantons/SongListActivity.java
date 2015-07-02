package org.frenchfrie.chantons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import javax.inject.Inject;

import roboguice.activity.RoboFragmentActivity;
import roboguice.activity.event.OnResumeEvent;
import roboguice.event.EventManager;
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

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @InjectView(R.id.song_detail_container)
    private View songDetailContainer;

    @Inject
    private EventManager eventManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (songDetailContainer != null) {
            mTwoPane = true;
            ((SongListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.song_list))
                    .setActivateOnItemClick(true);
        }
        // TODO: If exposing deep links into your app, handle intents here.
    }

    // TODO: maybe this method belong elsewhere...
    public void itemSelected(@Observes SongListFragment.OnSongSelected songSelected) {
        Ln.d("Song %s selected", songSelected.songId);
        int songId = songSelected.songId;
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(SongDetailFragment.ARG_ITEM_ID, songId);
            SongDetailFragment fragment = new SongDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.song_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, SongDetailActivity.class);
            detailIntent.putExtra(SongDetailFragment.ARG_ITEM_ID, songId);
            startActivity(detailIntent);
        }
    }
}
