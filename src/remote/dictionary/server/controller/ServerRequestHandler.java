package remote.dictionary.server.controller;

/*
 * @Author : Hanmoi Choi 525910
 */
import java.net.DatagramPacket;

import remote.dictionary.interfaces.CommunicationLayer;
import remote.dictionary.interfaces.DataRepository;
import remote.dictionary.util.Message;
import remote.dictionary.util.MessageFactory;
import remote.dictionary.util.MessageFactory.MessageType;

import com.google.gson.Gson;

public class ServerRequestHandler implements Runnable {

	private final CommunicationLayer communicationLayer;
	private final DatagramPacket packetFromClient;
	private Gson gson;
	private Message messageInJson;
	private DataRepository dataRepository;

	public ServerRequestHandler(CommunicationLayer communicationLayer,
			DataRepository dataRepository,
			DatagramPacket packetFromClient) {
		this.communicationLayer = communicationLayer;
		this.packetFromClient = packetFromClient;
		this.dataRepository = dataRepository;
	}

	@Override
	public void run() {
		String data = new String(packetFromClient.getData());
		Message messageFromClient = MessageFactory.covertToMessageFromJson(data);
		String messageToClient = null;
		String definition = dataRepository.getData(messageFromClient.getContent());

		if(definition == null) {
			messageToClient = MessageFactory.setMessageType(MessageType.Fail)
					.setSequence(messageFromClient.getSequenceNumber())
					.setContent(messageFromClient.getContent()+":")
					.generateInJson();
		}
		else {
			messageToClient = MessageFactory.setMessageType(MessageType.Success)
					.setSequence(messageFromClient.getSequenceNumber())
					.setContent(messageFromClient.getContent()+":"+definition)
					.generateInJson();
		}
		System.out.println(messageToClient);

		DatagramPacket packetToClient = new DatagramPacket(messageToClient.getBytes(),
				messageToClient.getBytes().length,
				packetFromClient.getAddress(),
				packetFromClient.getPort());

		communicationLayer.sendMessage(packetToClient);
	}


}
