package remote.dictionary.client.test;

/*
 * @Author : Hanmoi Choi 525910
 */
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import remote.dictionary.interfaces.CommunicationLayer;
import remote.dictionary.interfaces.Observer;
import remote.dictionary.util.Message;
import remote.dictionary.util.MessageFactory;
import remote.dictionary.util.MessageFactory.MessageType;
import remote.dictionary.util.PacketGenerator;

public class TestClientComm implements CommunicationLayer {
	private static final int BUFFER_SIZE = 1024;

	private DatagramSocket clientSocket;
	private DatagramPacket receivePacket;

	private byte[] buffer = new byte[BUFFER_SIZE];
	private Thread clientCommThread;
	private List<Observer> observerList = new ArrayList<Observer>();
	private boolean isRun = false;
	static long startT = System.currentTimeMillis();

	public TestClientComm() {}

	@Override
	public void register(Observer observer) {
		observerList.add(observer);
	}

	@Override
	public void sendMessage(DatagramPacket packet) {
		try {
			clientSocket.send(packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void activate() {
		isRun = true;
		try {
			clientSocket = new DatagramSocket();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		clientCommThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					while (isRun) {
						receivePacket = new DatagramPacket(buffer,
								buffer.length);
						clientSocket.receive(receivePacket);

						Message m = MessageFactory.covertToMessageFromJson(new String(receivePacket.getData()));

						if(m.getId() == MessageType.Success.id()) {
							long endT = System.currentTimeMillis() -startT;
							System.out.println("Success: "+ m.getSequenceNumber()+" :: "+endT);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		clientCommThread.start();
	}

	@Override
	public void deactivate() {
		if (clientCommThread.isAlive()) {
			isRun = false;
			try {
				clientCommThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (!clientSocket.isClosed()) {
			clientSocket.close();
			clientSocket = null;
		}
	}

	public static void main(String[] args) {

		int howManyTime = 1000;

		TestClientComm[] client = new TestClientComm[10];
		for(int i = 0 ; i < 10; i++) {

			client[i] = new TestClientComm();
			client[i].activate();
		}

		long startT = System.currentTimeMillis();
		for(int j = 0 ; j < howManyTime; j++) {
			for(int i = 0 ; i < 10; i++) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				String messageInJSon = remote.dictionary.util.MessageFactory.setMessageType(MessageType.Lookup)
						.setSequence(i)
						.setContent("FakeWord1")
						.generateInJson();

				PacketGenerator gen = new PacketGenerator();
				gen.setIp("192.168.1.6");
				gen.setPort(9999);
				try {
					DatagramPacket packet = gen.packet(messageInJSon);
					client[i].sendMessage(packet);
				} catch (SocketException | UnknownHostException e) {
					e.printStackTrace();
				}
			}
		}

	}
}

