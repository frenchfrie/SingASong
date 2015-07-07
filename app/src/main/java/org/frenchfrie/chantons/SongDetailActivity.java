package org.frenchfrie.chantons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.app.Fragment;


import android.view.MenuItem;

import roboguice.activity.RoboActivity;
import roboguice.activity.RoboFragmentActivity;


/**
 * An activity representing a single Song detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link SongListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link SongDetailFragment}.
 */
public class SongDetailActivity extends RoboFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(SongDetailFragment.ARG_ITEM_ID,
                    getIntent().getIntExtra(SongDetailFragment.ARG_ITEM_ID, -1));
            SongDetailFragment fragment = new SongDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.song_detail_container, fragment)
                    .commit();
        }
    }

}
