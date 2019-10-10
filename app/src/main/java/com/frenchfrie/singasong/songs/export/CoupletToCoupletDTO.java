package com.frenchfrie.singasong.songs.export;

import com.google.common.base.Function;

import com.frenchfrie.singasong.songs.Couplet;

public class CoupletToCoupletDTO implements Function<Couplet, CoupletDTO> {

    @Override
    public CoupletDTO apply(Couplet from) {
        CoupletDTO coupletDTO;
        if (from == null) {
            coupletDTO = null;
        } else {
            coupletDTO = new CoupletDTO(from.getVerses(), from.getImage());
        }
        return coupletDTO;
    }
}
