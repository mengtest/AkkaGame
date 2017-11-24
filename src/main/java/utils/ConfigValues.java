package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

public interface ConfigValues {
	
	static final Props props = new Props();
	
	static final String HOST = props.get("host");
	static final int PORT = props.getInt("port");

	static final int MAX_TIMEOUT = props.getInt("max_timeout");
	
	static final FiniteDuration SYSTEM_INFO_SAVE_PERIOD_TIME = 
			Duration.create(props.getInt("save_system_info_period_time"), TimeUnit.SECONDS);
	
	static final String DB_NAME = props.get("db_name");
	static final String USER_COLLECTION = props.get("user_collection");
	static final String GAME_COLLECTION = props.get("game_collection");
	static final String SYSTEM_INFO_COLLECTION = props.get("system_info_collection");
	
	class Props {
		
		private final Properties props; 

		public Props () {
			props = new Properties();
			try {
				props.load(Files.newInputStream(Paths.get("config.properties")));
			} catch (IOException e) {
				throw new RuntimeException("Couldn't load config file", e);
			}
		}
		
		String get(String key) {
			return props.getProperty(key);
		}
		
		Integer getInt(String key) {
			return Integer.parseInt(get(key));
		}
		
		Long getLong(String key) {
			return Long.parseLong(get(key));
		}
	}
}
