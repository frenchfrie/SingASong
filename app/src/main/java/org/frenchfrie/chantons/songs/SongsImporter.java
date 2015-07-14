package org.frenchfrie.chantons.songs;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;

import static com.google.common.collect.Lists.transform;

public class SongsImporter {

    private SongsDAO songsDAO;

    public SongsImporter(SongsDAO songsDAO) {
        this.songsDAO = songsDAO;
    }

    @SuppressWarnings("unchecked")
    public List<Song> importSongs(Uri songsFileUri) throws IOException {
        File songFile = new File(songsFileUri.getPath());
        List<Song> songsRead;
        try (FileReader reader = new FileReader(songFile)) {
            Type jsonRootType = new TypeToken<List<SongDTO>>() {
            }.getType();
            List<SongDTO> readObjects = new Gson().fromJson(reader, jsonRootType);
            songsRead = transform(readObjects, new SongDTOToSong());
        } catch (JsonIOException | JsonSyntaxException jsonEx) {
            throw new IOException(jsonEx);
        }
        return songsRead;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void exportSongs() throws IOException {
        Log.d(SongsImporter.class.getName(), "Exporting all songs to file");
        File songFile = new File(Environment.getExternalStorageDirectory(), "songs_export.json");
        if (!songFile.exists()) {
            songFile.createNewFile();
        }
        SongToSongDTO converter = new SongToSongDTO();
        List<SongDTO> songsDTOs = transform(songsDAO.findAll(), converter);
        try (FileWriter fileWriter = new FileWriter(songFile)) {
            Gson gson = new Gson();
            gson.toJson(songsDTOs, fileWriter);
            fileWriter.flush();
        } catch (JsonIOException jsonEx) {
            throw new IOException(jsonEx);
        }
        Log.d(SongsImporter.class.getName(), "Successfully exported " + songsDTOs.size() + " songs");
    }

}
