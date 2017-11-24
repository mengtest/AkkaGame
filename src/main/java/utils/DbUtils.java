package utils;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

public class DbUtils implements ConfigValues {

	private static final String ID = "id";
	
	public static MongoCollection<Document> users(MongoClient client) {
		return client.getDatabase(DB_NAME).getCollection(USER_COLLECTION);
	}

	public static MongoCollection<Document> games(MongoClient client) {
		return client.getDatabase(DB_NAME).getCollection(GAME_COLLECTION);
	}
	
	public static MongoCollection<Document> systemInfos(MongoClient client) {
		return client.getDatabase(DB_NAME).getCollection(SYSTEM_INFO_COLLECTION);
	}
	
	public static Bson has(String key, String value) {
		return new BasicDBObject().append(key, value);
	}
	
	public static Bson hasId(String value) {
		return new BasicDBObject().append(ID, value);
	}

	public static SortBuilder sort(String field) {
		return new SortBuilder(field);
	}
	
	public static class SortBuilder {
		
		private final String field;

		public SortBuilder(String field) {
			super();
			this.field = field;
		}
		
		public Bson asc() {
			return new Document(field, 1);
		}
		
		public Bson desc() {
			return new Document(field, -1);
		}
	}
}
