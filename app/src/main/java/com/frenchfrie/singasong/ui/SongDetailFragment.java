package com.frenchfrie.singasong.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frenchfrie.singasong.R;
import com.frenchfrie.singasong.songs.Couplet;
import com.frenchfrie.singasong.songs.Song;
import com.frenchfrie.singasong.songs.SongsService;

import java.util.List;

/**
 * A fragment representing a single Song detail screen.
 * This fragment is either contained in a {@link SongListActivity}
 * in two-pane mode (on tablets) or a {@link SongDetailActivity}
 * on handsets.
 */
public class SongDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Song representedSong;
    private SongsService songsService;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SongDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            representedSong = songsService.findOne(getArguments().getLong(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_song_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (representedSong != null) {
            StringBuilder lyrics = getSongLyricsAsString(representedSong);
            ((TextView) rootView.findViewById(R.id.song_detail)).setText(lyrics);
        }
        return rootView;
    }

    private static StringBuilder getSongLyricsAsString(Song representedSong) {
        StringBuilder lyrics;
        List<Couplet> couplets = representedSong.getCouplets();
        if (couplets != null && !couplets.isEmpty()) {

            lyrics = new StringBuilder(couplets.get(0).getVerses());
            Couplet chorus = representedSong.getChorus();
            if (chorus != null) {
                lyrics.append("\n").append(chorus.getVerses());
            }
            for (int coupletId = 1; coupletId < couplets.size(); coupletId++) {
                lyrics.append("\n").append(couplets.get(coupletId).getVerses());
            }
        } else {
            lyrics = new StringBuilder(representedSong.getRawLyrics());
        }
        return lyrics;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        songsService = SongsService.getSongsService(activity);
    }
}
