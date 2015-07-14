package org.frenchfrie.chantons.songs;

import java.util.List;

class Couplet {

    private Long id;

    private List<String> verses;

    private String image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getVerses() {
        return verses;
    }

    public void setVerses(List<String> verses) {
        this.verses = verses;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
