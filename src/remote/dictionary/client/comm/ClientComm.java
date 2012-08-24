package remote.dictionary.client.comm;
/*
 * @Author : Hanmoi Choi 525910
 */
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

import remote.dictionary.interfaces.CommunicationLayer;
import remote.dictionary.interfaces.Observer;

public class ClientComm implements CommunicationLayer {
	private static final int BUFFER_SIZE = 1024;

	private DatagramSocket clientSocket;
	private DatagramPacket receivePacket;

	private byte[] buffer = new byte[BUFFER_SIZE];
	private Thread clientCommThread;
	private List<Observer> observerList = new ArrayList<Observer>();
	private boolean isRun = false;

	public ClientComm() {}

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
		clientCommThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					clientSocket = new DatagramSocket();

					while (isRun) {
						receivePacket = new DatagramPacket(buffer,
								buffer.length);
						clientSocket.receive(receivePacket);
						for (Observer observer : observerList) {
							observer.update(receivePacket);
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
			clientCommThread.stop();
			isRun = false;
		}

		if (!clientSocket.isClosed()) {
			clientSocket.close();
			clientSocket = null;
		}
	}

	//	@Override
	//	public void setDataRepository(DataRepository dataRepository) {}

}
