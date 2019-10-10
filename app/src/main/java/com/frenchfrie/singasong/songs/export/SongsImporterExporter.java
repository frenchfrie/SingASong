package com.frenchfrie.singasong.songs.export;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import com.frenchfrie.singasong.songs.Song;
import com.frenchfrie.singasong.songs.SongsDAO;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import static com.google.common.collect.Lists.transform;

public class SongsImporterExporter {

    public static final String EXPORT_DIR = "SingASong";
    private SongsDAO songsDAO;

    public SongsImporterExporter(SongsDAO songsDAO) {
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
        Log.d(SongsImporterExporter.class.getName(), "Exporting all songs to file");
        File directory = getExportDir();
        File songFile = new File(directory, "songs_export.json");

        if (!songFile.exists()) {
            try {
                songFile.createNewFile();
            } catch (SecurityException e) {
                throw new IOException("Not authorized to write file");
            }
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
        Log.d(SongsImporterExporter.class.getName(), "Successfully exported " + songsDTOs.size() + " songs");
    }

    private File getExportDir() throws IOException {
        // Get the directory for the user's public pictures directory.
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        if (!externalStoragePublicDirectory.mkdirs() || !externalStoragePublicDirectory.exists()) {
            throw new IOException("Download directory does not exists");
        }
        return externalStoragePublicDirectory;
    }
}
