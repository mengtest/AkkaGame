package utils;

import org.bson.Document;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;

public class ConversionUtils {

	public static String toString(MessageOrBuilder proto) throws InvalidProtocolBufferException {
		return JsonFormat.printer().print(proto);
	}
	
	public static Document toDocument(MessageOrBuilder proto) throws InvalidProtocolBufferException {
		return Document.parse(toString(proto));
	}
}
