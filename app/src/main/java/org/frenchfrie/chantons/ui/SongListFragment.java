package org.frenchfrie.chantons.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.frenchfrie.chantons.R;
import org.frenchfrie.chantons.songs.Song;
import org.frenchfrie.chantons.songs.SongsService;

import java.util.List;

/**
 * A list fragment representing a list of Songs. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link SongDetailFragment}.
 */
public class SongListFragment extends ListFragment {

    private static final String LOG_TAG = "SongListFragment";

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    private SongsService songsService;

    private List<Song> songsDisplayed;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    private Context context;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SongListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        songsDisplayed = songsService.findAll();
        // TODO: replace with a real list adapter.
        setListAdapter(new SongListAdaptor(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                songsDisplayed));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        songsService = SongsService.getSongsService(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
//        eventManager.fire(new OnSongSelected(songsDisplayed.get(position).getId()));
        itemSelected(new OnSongSelected(songsDisplayed.get(position).getId()));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    private static class SongListAdaptor extends ArrayAdapter<Song> {

        private Context context;
        private LayoutInflater mInflater;

        public SongListAdaptor(Context context, int resource, int textViewResourceId, List<Song> objects) {
            super(context, resource, textViewResourceId, objects);
            this.context = context;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                view = mInflater.inflate(R.layout.fragment_song_list_element, parent, false);
            } else {
                view = convertView;
            }

            TextView titleText = (TextView) view.findViewById(R.id.line_one);
            TextView authorText = (TextView) view.findViewById(R.id.line_two);

            Song song = getItem(position);
            authorText.setText(song.getAuthor());
            titleText.setText(song.getTitle());

            return view;
        }
    }

    public static class OnSongSelected {
        public final long songId;

        public OnSongSelected(long songId) {
            this.songId = songId;
        }
    }

    public void itemSelected(SongListFragment.OnSongSelected songSelected) {
        Log.d(LOG_TAG, "Item selected");
        long songId = songSelected.songId;
        Intent detailIntent = new Intent(context, SongDetailActivity.class);
        detailIntent.putExtra(SongDetailFragment.ARG_ITEM_ID, songId);
        startActivity(detailIntent);
    }
}
