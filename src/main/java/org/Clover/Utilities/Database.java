package org.Clover.Utilities;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.Clover.Clover;
import org.bson.Document;

public class Database {

    private final Clover clover;
    private final MongoClientURI clientURI;
    private MongoClient client;
    private MongoDatabase db;

    public Database(Clover clover){
        clientURI = new MongoClientURI(clover.getConfig().get("dbURI"));
        this.clover = clover;
    }

    public void connect() {
        client = new MongoClient(clientURI);
        db = client.getDatabase("Clover");
    }

    public MongoCollection<Document> getCollection(String collection) {
        return db.getCollection(collection);
    }

    public void close() {
        client.close();
    }
}
