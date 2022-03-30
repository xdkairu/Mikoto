package org.Clover.Utilities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Episodes {

    private static final Logging LOGGING = new Logging();
    private final List<Episode> episodes;

    public Episodes(File file) {
        this.episodes = new ArrayList<>();
        parseFile(file);
    }

    @Nullable
    public Episode get(int number) {
        --number; // zero-indexed

        if (episodes.size() < number) {
            return null;
        }

        return episodes.get(number);
    }

    @NotNull
    private Episode parseEpisode(JSONObject json) {
        var name = (String) json.get("name");
        var url = (String) json.get("link");
        return new Episode(name, url);
    }

    private void parseFile(File file) {
        try (FileReader reader = new FileReader(file)) {
            var parser = new JSONParser();
            var json = (JSONObject) parser.parse(reader);
            var array = (JSONArray) json.get("episodes");

            for (var episode : array) {
                episodes.add(parseEpisode((JSONObject) episode));
            }
        } catch (IOException | ParseException exception) {
            LOGGING.error(this.getClass(), exception.getMessage());
        }
    }

    // this is homophobic (: - Mykyta aka techtoolbox uhhhhhhhhhhh
}
