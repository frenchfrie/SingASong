package com.frenchfrie.singasong.songs.export;

import com.google.common.base.Function;

import com.frenchfrie.singasong.songs.Couplet;

public class CoupletDTOToCouplet implements Function<CoupletDTO, Couplet> {

    @Override
    public Couplet apply(CoupletDTO from) {
        Couplet couplet;
        if (from == null) {
            couplet = null;
        } else {
            couplet = new Couplet(from.getImage(), from.getVerses());
        }
        return couplet;
    }
}
