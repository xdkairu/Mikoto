package org.Clover.Utilities;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import net.dv8tion.jda.api.entities.Guild;
import org.Clover.Clover;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;

public class GuildConfig {

    private final Clover clover;
    private static Logging logging = new Logging();

    public GuildConfig(Clover clover){
        this.clover = clover;
    }

    public HashMap<String, HashMap<String, String>> configuration = new HashMap<String, HashMap<String, String>>();

    public void load(){

        MongoCollection<Document> guilds =  clover.getDatabase().getCollection("guild");

        FindIterable<Document> iterable = guilds.find();
        MongoCursor<Document> cursor = iterable.iterator();

        try{
            while(cursor.hasNext()){
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(cursor.next().toJson());

                HashMap<String, String> data = new HashMap<String, String>();
                logging.info(this.getClass(), obj.toString());
                data.put("logChannel", obj.get("logChannelID").toString());

                configuration.put(obj.get("guildID").toString(), data);

            }
        } catch (ParseException ex){
            logging.error(this.getClass(), ex.toString());
        } finally {
            configuration.forEach((k,v) -> {
                logging.info(this.getClass(), String.format("Key: %s, Value: %s", k, v));
            });
            cursor.close();
        }


    }

    public String get(String key){
        return configuration.get("916886277587079210").get(key).toString();
    }
}
