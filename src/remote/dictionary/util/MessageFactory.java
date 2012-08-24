package remote.dictionary.util;
/*
 * @Author : Hanmoi Choi 525910
 */
import java.io.IOException;
import java.io.StringReader;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class MessageFactory {

	public enum MessageType {

		Lookup(1), Success(2), Fail(3);

		private int messageId;
		private MessageType(int messageId) {
			this.messageId = messageId;
		}

		public int id() {
			return messageId;
		}
	}

	private static MessageType messageType;
	private static int sequenceNumber;
	private static String 	messageContent;
	private final static Gson gson = new Gson();
	private static MessageFactory facotry;

	static {
		facotry = new MessageFactory();
	}

	private MessageFactory() { }

	public String inJson() {
		return gson.toJson(this);
	}

	public static MessageFactory setMessageType(MessageType type) {
		messageType = type;
		return facotry;
	}

	public static MessageFactory setSequence(int sequence) {
		sequenceNumber = sequence;
		return facotry;
	}

	public static MessageFactory setContent(String content) {
		messageContent = content;
		return facotry;
	}

	public static String generateInJson() {
		Message message = new Message();
		message.setId(messageType.id());
		message.setSequenceNumber(sequenceNumber);
		message.setContent(messageContent);

		return gson.toJson(message);
	}

	public static Message covertToMessageFromJson(String messageInJSon) {

		JsonReader reader = new JsonReader(new StringReader(messageInJSon));
		reader.setLenient(true);

		Message message = gson.fromJson(reader, Message.class);
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return message;
	}
}
