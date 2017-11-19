package actors.http;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CompletionStage;

import akka.actor.ActorSystem;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.IncomingConnection;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

public abstract class HttpInterface extends AllDirectives {

	static final int MAX_TIMEOUT = 5 * 1000;
	static final String HOST = "localhost";
	
	final ActorSystem system;
	final LoggingAdapter log;
	
	public HttpInterface(ActorSystem system) {
		this.system = system;
		this.log = Logging.getLogger(system, this);
	}
	
	public boolean start(int port, String quitWord) {
		if (!isAvailable(port)) {
			return false;
		}
		final Http http = Http.get(system);
		final ActorMaterializer materializer = ActorMaterializer.create(system);
		
		final Source<IncomingConnection, CompletionStage<ServerBinding>> serverSource = http
				.bind(ConnectHttp.toHost(HOST, port), materializer);

		final CompletionStage<ServerBinding> binding = serverSource.to(Sink.foreach(connection -> {
			connection.handleWith(routes().flow(system, materializer), materializer);
		})).run(materializer);
		
		System.out.println(String.format("Server online at http://localhost:%d/", port));
		
		if (quitWord != null) {
			System.out.println(String.format("Enter %s to stop...", quitWord));
			Scanner sc = new Scanner(System.in);
			while (sc.hasNext()) {
				String word = sc.next();
				if (quitWord.equals(word.trim())) {
					binding.thenCompose(ServerBinding::unbind).thenAccept(unbound -> system.terminate());
					break;
				}
			}
			sc.close();
		}
		
		return true;
	}
	
	private static boolean isAvailable(int port) {
	    try (Socket ignored = new Socket(HOST, port)) {
	        return false;
	    } catch (IOException ignored) {
	        return true;
	    }
	}
	
	abstract Route routes();
}
