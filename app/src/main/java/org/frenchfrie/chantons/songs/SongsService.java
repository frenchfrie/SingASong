package org.frenchfrie.chantons.songs;

import android.content.Context;
import android.net.Uri;

import java.io.IOException;

public class SongsService {

    private static SongsService applicationSongsService;

    public static SongsService getSongsService(Context context) {
        if (applicationSongsService == null) {
            applicationSongsService = new SongsService(context);
        }
        return applicationSongsService;
    }

    private SongsDAO songsDAO;

    private SongsService(Context context) {
        songsDAO = new SongsDAO(context);
    }

    private SongsService(SongsDAO songsDAO) {
        this.songsDAO = songsDAO;
    }

    public Song findOne(Long key) {
        return songsDAO.findOne(key);
    }

    public Iterable<Song> findAll() {
        return songsDAO.findAll();
    }

    public void importFile(Uri uri) throws IOException {
        songsDAO.save(new SongsImporter(songsDAO).importSongs(uri));
    }

    public void exportToFile() throws IOException {
        new SongsImporter(songsDAO).exportSongs();
    }

}
