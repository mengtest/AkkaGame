package main;

import actors.http.HttpMain;
import akka.actor.ActorSystem;
import akka.http.javadsl.server.AllDirectives;

public class GameApp extends AllDirectives {

	private static final int PORT = 8080;
	
	public static void main(String[] args) throws Exception {
		ActorSystem system = ActorSystem.create("Game-App");

		HttpMain main = new HttpMain(system);
		
		main.start(PORT, "0");
	}
}
