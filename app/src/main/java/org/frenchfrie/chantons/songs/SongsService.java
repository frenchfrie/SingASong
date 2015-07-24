package org.frenchfrie.chantons.songs;

import android.content.Context;
import android.net.Uri;

import org.frenchfrie.chantons.songs.export.SongsImporterExporter;

import java.io.IOException;
import java.util.List;

public class SongsService {

    private static SongsService applicationSongsService;

    public static SongsService getSongsService(Context context) {
        if (applicationSongsService == null) {
            applicationSongsService = new SongsService(context);
        }
        return applicationSongsService;
    }

    public SongsDAO getSongsDAO() {
        return songsDAO;
    }


    public CoupletsDAO getCoupletsDAO() {
        return songsDAO.getCoupletsDAO();
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

    public List<Song> findAll() {
        return songsDAO.findAll();
    }

    public void importFile(Uri uri) throws IOException {
        songsDAO.save(new SongsImporterExporter(songsDAO).importSongs(uri));
    }

    public void save(Song song) {
        songsDAO.save(song);
    }

    public void exportToFile() throws IOException {
        new SongsImporterExporter(songsDAO).exportSongs();
    }

}
