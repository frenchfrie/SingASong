package org.frenchfrie.chantons.songs;

class Couplet {

    private Long id;

    /**
     * Verses: a line feed is the verse delimiter.
     */
    private String verses;

    private String image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
