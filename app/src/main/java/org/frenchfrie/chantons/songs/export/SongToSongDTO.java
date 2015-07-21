package org.frenchfrie.chantons.songs.export;

import com.google.common.base.Function;

import org.frenchfrie.chantons.songs.Song;

import static com.google.common.collect.FluentIterable.from;

public class SongToSongDTO implements Function<Song, SongDTO> {

    private CoupletToCoupletDTO coupletReader = new CoupletToCoupletDTO();

    @Override
    public SongDTO apply(Song from) {
        SongDTO songDTO;
        if (from == null) {
            songDTO = null;
        } else {
            songDTO = new SongDTO(from.getTitle(), from.getDescription(), from.getAuthor(), from.getRawLyrics(),
                    from(from.getCouplets()).transform(coupletReader).toList(),
                    coupletReader.apply(from.getChorus()), from.getRecordingFileName());
        }
        return songDTO;
    }
}
