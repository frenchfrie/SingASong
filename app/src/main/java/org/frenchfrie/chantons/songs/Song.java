package org.frenchfrie.chantons.songs;

import java.util.List;

public class Song {
    private Long id;

    private String title;

    private String description;

    private String author;

    /**
     * When we do not have the proper structure of the song. Should not be set if other lyrics fields are not null.
     */
    private String rawLyrics;

    private List<Couplet> couplets;

    private Couplet chorus;

    private String recordingFileName;

    public Song() {
    }

    public Song(String title, String description, String author, String rawLyrics, List<Couplet> couplets, Couplet chorus, String recordingFileName) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.rawLyrics = rawLyrics;
        this.couplets = couplets;
        this.chorus = chorus;
        this.recordingFileName = recordingFileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<Couplet> getCouplets() {
        return couplets;
    }

    public void setCouplets(List<Couplet> couplets) {
        this.couplets = couplets;
    }

    public Couplet getChorus() {
        return chorus;
    }

    public void setChorus(Couplet chorus) {
        this.chorus = chorus;
    }

    public String getRecordingFileName() {
        return recordingFileName;
    }

    public void setRecordingFileName(String recordingFileName) {
        this.recordingFileName = recordingFileName;
    }

}
