package remote.dictionary.server.comm;
/*
 * @Author : Hanmoi Choi 525910
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import remote.dictionary.interfaces.CommunicationLayer;
import remote.dictionary.interfaces.Observer;

public class ServerComm implements CommunicationLayer {

	private static int BUFFER_SIZE = 1024;



	private DatagramSocket serverSocket;


	private final byte[] messageBuffer = new byte[BUFFER_SIZE];

	private boolean isRun;

	private Thread serverCommThread;
	private DatagramPacket receivePacket;

	private List<Observer> observerList = new ArrayList<Observer>();


	private int portNumber = 9999;

	public ServerComm() {
	}


	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}

	@Override
	public void sendMessage(DatagramPacket packet) {
		try {
			serverSocket.send(packet);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void register(Observer observer) {
		observerList.add(observer);
	}

	@Override
	public void activate() {
		isRun = true;


		serverCommThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					serverSocket = new DatagramSocket(portNumber,
							InetAddress.getLocalHost());

					while (isRun) {
						receivePacket = new DatagramPacket(messageBuffer,
								messageBuffer.length);
						serverSocket.receive(receivePacket);

						for (Observer observer : observerList) {
							observer.update(receivePacket);
						}


					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		serverCommThread.start();
	}

	@Override
	public void deactivate() {

		if (serverCommThread.isAlive()) {
			isRun = false;
			serverCommThread.stop();
		}

		if (!serverSocket.isClosed()) {
			serverSocket.close();
			serverSocket = null;
		}
	}

}
