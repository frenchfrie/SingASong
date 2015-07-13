package org.frenchfrie.chantons.songs;

import android.net.Uri;
import android.os.Environment;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.google.common.collect.Iterables.transform;

public class SongsImporter {

    private SongsDAO songsDAO;

    public SongsImporter(SongsDAO songsDAO) {
        this.songsDAO = songsDAO;
    }

    public void importSongs(Uri songsFileUri) {
        File songFile = new File(songsFileUri.getPath());
        Gson gson = new Gson();
        try {
            gson.fromJson(new FileReader(songFile), List.class);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void exportSongs() {
        File songFile = new File(Environment.getExternalStorageDirectory(), "songs_export.json");
        try {
            if (!songFile.exists()) {
                songFile.createNewFile();
            }
            Gson gson = new Gson();
            SongToSongDTO converter = new SongToSongDTO();
            Iterable<SongDTO> songsDtos = transform(songsDAO.findAll(), converter);
            gson.toJson(songsDtos, new FileWriter(songFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
