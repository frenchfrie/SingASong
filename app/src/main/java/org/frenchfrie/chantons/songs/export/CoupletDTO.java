package org.frenchfrie.chantons.songs.export;

public class CoupletDTO {
    private String verses;
    private String image;

    public CoupletDTO() {
    }

    public CoupletDTO(String verses, String image) {
        this.verses = verses;
        this.image = image;
    }

    public String getVerses() {
        return verses;
    }

    public void setVerses(String verses) {
        this.verses = verses;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
