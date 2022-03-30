package org.Clover.Utilities;

import org.Clover.Clover;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Episodes {

    private final Clover clover;
    Logging logging = new Logging();
    public HashMap<String, String> episodes = new HashMap<>();

    public Episodes(Clover clover, File file) {
        this.clover = clover;
    }

    JSONParser parser = new JSONParser();

    public void load() {
        try (FileReader reader = new FileReader("episodes.json")) {
            JSONObject obj = (JSONObject) parser.parse(reader);

            episodes.putAll(obj);

        } catch (IOException | ParseException exception) {
            logging.error(this.getClass(), exception.getMessage());
        }
    }

    public String get(String key) {
        return episodes.get(key);
    }

    public String getTitle(String key) {
        try {
            JSONObject whatever = (JSONObject) parser.parse(episodes.toString());
            JSONObject episode = (JSONObject) whatever.get(key);
            return episode.get("name").toString();
        } catch (ParseException e) {

        }
        return null;
    }

    // this is homophobic (: - Mykyta aka techtoolbox uhhhhhhhhhhh

    public String getUrl(String key) {
        try {
            JSONObject whatever = (JSONObject) parser.parse(episodes.toString());
            JSONObject episode = (JSONObject) whatever.get(key);
            return episode.get("link").toString();
        } catch (ParseException e) {

        }
        return null;
    }
}
