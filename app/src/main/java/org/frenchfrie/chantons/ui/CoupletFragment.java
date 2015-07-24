package org.frenchfrie.chantons.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.frenchfrie.chantons.R;
import org.frenchfrie.chantons.songs.Couplet;
import org.frenchfrie.chantons.songs.CoupletsDAO;
import org.frenchfrie.chantons.songs.Song;
import org.frenchfrie.chantons.songs.SongsService;

import java.util.List;

public class CoupletFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private Couplet representedCouplet;
    private CoupletsDAO coupletsDAO;

    public CoupletFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            representedCouplet = coupletsDAO.findOne(getArguments().getLong(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_couplet, container, false);
        if (representedCouplet != null) {
            ((TextView) rootView.findViewById(R.id.song_detail)).setText(representedCouplet.getVerses());
        }
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        coupletsDAO = SongsService.getSongsService(activity).getCoupletsDAO();
    }
}
