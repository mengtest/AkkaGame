package utils;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

public class DbUtils {

	private static final String DB_NAME = "tav";
	private static final String USER = "user";
	private static final String GAME = "game";
	private static final String ID = "id";
	
	public static MongoCollection<Document> users(MongoClient client) {
		return client.getDatabase(DB_NAME).getCollection(USER);
	}

	public static MongoCollection<Document> games(MongoClient client) {
		return client.getDatabase(DB_NAME).getCollection(GAME);
	}
	
	public static Bson has(String key, String value) {
		return new BasicDBObject().append(key, value);
	}
	
	public static Bson hasId(String value) {
		return new BasicDBObject().append(ID, value);
	}
}
