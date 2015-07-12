package org.frenchfrie.chantons.songs;

import com.google.common.base.Function;

public class SongToSongDTO implements Function<Song, SongDTO> {

    @Override
    public SongDTO apply(Song from) {
        return new SongDTO(from.getTitle(), from.getAuthor(), from.getLyrics());
    }
}
