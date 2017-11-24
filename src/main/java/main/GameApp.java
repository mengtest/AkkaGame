package main;

import actors.http.HttpMain;
import akka.actor.ActorSystem;
import akka.http.javadsl.server.AllDirectives;
import utils.ConfigValues;

public class GameApp extends AllDirectives {

	public static void main(String[] args) throws Exception {
		ActorSystem system = ActorSystem.create("Game-App");

		HttpMain main = new HttpMain(system);
		
		main.start(ConfigValues.PORT, "0");
	}
}
