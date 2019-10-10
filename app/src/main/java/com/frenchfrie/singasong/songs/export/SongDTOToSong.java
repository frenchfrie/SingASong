package com.frenchfrie.singasong.songs.export;

import com.google.common.base.Function;

import com.frenchfrie.singasong.songs.Song;

import static com.google.common.collect.FluentIterable.from;

public class SongDTOToSong implements Function<SongDTO, Song> {

    private CoupletDTOToCouplet coupletDTOReader = new CoupletDTOToCouplet();

    @Override
    public Song apply(SongDTO from) {
        Song song;
        if (from == null) {
            song = null;
        } else {
            song = new Song(from.getTitle(), from.getDescription(), from.getAuthor(), from.getRawLyrics(),
                    from(from.getCouplets()).transform(coupletDTOReader).toList(),
                    coupletDTOReader.apply(from.getChorus()), from.getRecordingFileName());
        }
        return song;
    }
}
