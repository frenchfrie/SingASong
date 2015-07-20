package org.frenchfrie.chantons.songs;

import com.google.common.base.Function;

public class SongDTOToSong implements Function<SongDTO, Song> {

    @Override
    public Song apply(SongDTO from) {
        return new Song(from.getTitle(), from.getAuthor(), from.getRawLyrics());
    }
}
