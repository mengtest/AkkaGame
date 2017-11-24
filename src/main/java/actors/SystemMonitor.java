package actors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.mongodb.MongoClient;

import akka.actor.AbstractActor;
import akka.actor.Props;
import model.GameNotFoundException;
import model.SystemInfoProtos.SystemInfo;
import utils.ConfigValues;
import utils.ConversionUtils;
import utils.DbUtils;

public class SystemMonitor extends AbstractActor {

	static public Props props() {
		return Props.create(SystemMonitor.class, () -> new SystemMonitor());
	}

	public SystemMonitor() {
	}

	@Override
	public void preStart() throws Exception {
		scheduleSaveSystemInfo();
	}
	
	@Override
	public void postRestart(Throwable reason) throws Exception {
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder().match(GetSystemInfos.class, message -> {
			MongoClient client = new MongoClient();
			try {
				List<SystemInfo> systemInfos = getSystemInfos(client, message.amount);
				getSender().tell(new GetSystemInfosSucceeded(systemInfos), getSelf());
			} catch (Throwable th) {
				getSender().tell(new GetSystemInfosFailed(th), getSelf());
			} finally {
				client.close();
			}
		}).match(SaveSystemInfo.class, message -> {
			MongoClient client = new MongoClient();
			try {
				scheduleSaveSystemInfo();

				Runtime runtime = Runtime.getRuntime();
				
				SystemInfo systemInfo = SystemInfo.newBuilder()
						.setTimestamp(System.currentTimeMillis())
						.setFreeMemory(runtime.freeMemory())
						.setTotalMemory(runtime.totalMemory())
						.setMaxMemory(runtime.maxMemory())
						.build();
				DbUtils.systemInfos(client).insertOne(ConversionUtils.toDocument(systemInfo));
			} catch (Throwable th) {
				throw new RuntimeException("Something went wrong when trying to save system info.", th);
			} finally {
				client.close();
			}
		}).build();
	}
	
	private void scheduleSaveSystemInfo() {
		getContext().getSystem().scheduler().scheduleOnce(
				ConfigValues.SYSTEM_INFO_SAVE_PERIOD_TIME, getSelf(), new SaveSystemInfo(), 
				getContext().dispatcher(), null);	
	}

	private List<SystemInfo> getSystemInfos(MongoClient client, int amount) 
			throws InvalidProtocolBufferException, GameNotFoundException {
		Iterator<Document> cursor = DbUtils.systemInfos(client).find().sort(DbUtils.sort("timestamp").desc()).iterator();
		List<SystemInfo> systemInfos = new ArrayList<> ();
		for (int i = 0; cursor.hasNext() && i < amount; i++) {
			Document doc = cursor.next();
			SystemInfo.Builder systemInfoBuilder = SystemInfo.newBuilder();
			doc.remove("_id");
			JsonFormat.parser().merge(doc.toJson(), systemInfoBuilder);
			systemInfos.add(systemInfoBuilder.build());
		}
		return systemInfos;
	}

	static public class GetSystemInfos {
		
		private final int amount;

		public GetSystemInfos(int amount) {
			super();
			this.amount = amount;
		}
	}

	static public class GetSystemInfosFailed extends MessageFailed {

		public GetSystemInfosFailed(Throwable cause) {
			super(cause);
		}
	}

	static public class GetSystemInfosSucceeded {

		private final List<SystemInfo> systemInfos;

		public GetSystemInfosSucceeded(List<SystemInfo> systemInfos) {
			this.systemInfos = systemInfos;
		}

		public List<SystemInfo> getSystemInfos() {
			return systemInfos;
		}
	}

	static private class SaveSystemInfo {
	}
}
