package org.frenchfrie.chantons.songs;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.frenchfrie.chantons.songs.export.SongsImporterExporter;

import java.io.IOException;
import java.util.List;

public class SongsService extends Service {

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

    public SongsDAO getSongsDAO() {
        return songsDAO;
    }

    public CoupletsDAO getCoupletsDAO() {
        return songsDAO.getCoupletsDAO();
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

    public Song save(Song song) {
        return songsDAO.save(song);
    }

    public List<Song> save(Iterable<Song> songs) {
        return songsDAO.save(songs);
    }

    public void exportToFile() throws IOException {
        new SongsImporterExporter(songsDAO).exportSongs();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
