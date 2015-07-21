package org.frenchfrie.chantons.songs.export;

import java.util.List;

/**
 * Song object to use for external exchanges as JSon import or export.
 */
public class SongDTO {
    private String title;
    private String description;
    private String author;
    private String rawLyrics;
    private List<CoupletDTO> couplets;
    private CoupletDTO chorus;
    private String recordingFileName;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CoupletDTO> getCouplets() {
        return couplets;
    }

    public void setCouplets(List<CoupletDTO> couplets) {
        this.couplets = couplets;
    }

    public CoupletDTO getChorus() {
        return chorus;
    }

    public void setChorus(CoupletDTO chorus) {
        this.chorus = chorus;
    }

    public String getRecordingFileName() {
        return recordingFileName;
    }

    public void setRecordingFileName(String recordingFileName) {
        this.recordingFileName = recordingFileName;
    }

    public SongDTO() {
    }

    public SongDTO(String title, String description, String author, String rawLyrics, List<CoupletDTO> couplets, CoupletDTO chorus, String recordingFileName) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.rawLyrics = rawLyrics;
        this.couplets = couplets;
        this.chorus = chorus;
        this.recordingFileName = recordingFileName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRawLyrics() {
        return rawLyrics;
    }

    public void setRawLyrics(String rawLyrics) {
        this.rawLyrics = rawLyrics;
    }

}
