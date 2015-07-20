package org.frenchfrie.chantons.songs;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class VersesDBSerializer {

    public static String serializeVerses(List<String> verses) {
        return new Gson().toJson(verses, new TypeToken<List<String>>() {
        }.getType());
    }

    public static List<String> deSerializeVerses(String verses) {
        return new Gson().fromJson(verses, new TypeToken<List<String>>() {
        }.getType());
    }

}
