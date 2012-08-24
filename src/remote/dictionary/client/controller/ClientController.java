package remote.dictionary.client.controller;
/*
 * @Author : Hanmoi Choi 525910
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import remote.dictionary.client.view.ClientView;
import remote.dictionary.interfaces.CommunicationLayer;
import remote.dictionary.interfaces.Observer;
import remote.dictionary.util.Message;
import remote.dictionary.util.MessageFactory;
import remote.dictionary.util.MessageFactory.MessageType;
import remote.dictionary.util.PacketGenerator;
import remote.dictionary.util.StringChecker;

public class ClientController implements Observer {


	private final ClientView view;
	private final CommunicationLayer commLayer;
	private String ip;
	private int port = 9999;
	private CommunicationLayer.Status commStatus;
	private PacketGenerator packetGenerator;

	private Thread responseCheckThread;
	private long watingTime = 1000L;

	private static int sequenceNumber = 0;
	private Map<Integer, String> sequenceNumberMap = new HashMap<Integer, String>();

	public ClientController(ClientView view, CommunicationLayer commLayer) {
		this.view = view;
		this.commLayer = commLayer;
		this.commLayer.register(this);

		packetGenerator = new PacketGenerator();

		initActionListner();

		// Test
		//		view.ipTextField().setText("10.37.129.2");
		view.portTextField().setText("9999");

		disableSearching();
	}

	@Override
	public void update(DatagramPacket packet) {
		String messageInJSon = new String(packet.getData());
        string name = "1";


		System.out.println(messageInJSon);

		Message result = MessageFactory.covertToMessageFromJson(messageInJSon);

		if (sequenceNumberMap.get(result.getSequenceNumber()) != null) {
			if (responseCheckThread.isAlive()) {
				responseCheckThread.stop();
			}

			sequenceNumberMap.remove(result.getSequenceNumber());

			String[] displayMessage = result.getContent().split(":");
			if (result.getId() == MessageType.Success.id()) {
				displayWordDefinition("[" + displayMessage[0] + "] : "
						+ displayMessage[1]);
			} else {
				displayWordDefinition("[" + displayMessage[0] + "] : "
						+ "Not Found");
			}
		}
	}

	private void initActionListner() {
		view.activateButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				handleActivation(evt);
			}
		});

		view.deactivateButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				handleDeactivation(evt);
			}

		});

		view.searchButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				handleSearch(evt);
			}
		});
	}

	private void displayErrorMessage(String message) {
		try {
			Document doc = view.consoleTextPane().getDocument();
			doc.insertString(doc.getLength(), "\n"+message, null);
		} catch (BadLocationException exc) {
			exc.printStackTrace();
		}
	}

	private void displayWordDefinition(String message) {
		try {
			Document doc = view.definitionTextPane().getDocument();
			doc.insertString(doc.getLength(), "\n"+message, null);
		} catch (BadLocationException exc) {
			exc.printStackTrace();
		}
	}

	private void handleActivation(ActionEvent evt) {
		String tmpIp = view.ipTextField().getText();
		String tempPort = view.portTextField().getText();
		String tempWaiting = view.waitingTimeTextField().getText();

		if (StringChecker.isIp(tmpIp)
				&& StringChecker.isPortNumber(tempPort)
				&& StringChecker.isPortNumber(tempWaiting)) {
			ip = tmpIp;
			port = Integer.parseInt(tempPort);
			watingTime = Long.parseLong(tempWaiting);
			commStatus = CommunicationLayer.Status.Activate;
			commLayer.activate();
			watingTime = Integer.parseInt(view.waitingTimeTextField().getText());
			viewSettingEditable(false);
			enableSearching();
		} else {
			displayErrorMessage("Either IP or Port Number is Invalid");
		}
	}

	private void handleDeactivation(ActionEvent evt) {
		if (commStatus == CommunicationLayer.Status.Activate) {
			commLayer.deactivate();
			commStatus = CommunicationLayer.Status.Deactivate;
			viewSettingEditable(true);
			disableSearching();
		}
	}

	private void viewSettingEditable(boolean isEditable) {
		view.ipTextField().setEditable(isEditable);
		view.portTextField().setEditable(isEditable);
		view.waitingTimeTextField().setEditable(isEditable);
	}

	private void handleSearch(ActionEvent evt) {
		String wordToFind = view.searchTestField().getText();

		if (wordToFind == null) {
			displayErrorMessage("Please Enter Word To Find");
			return;
		}

		String message = MessageFactory.setMessageType(MessageType.Lookup)
				.setContent(wordToFind).setSequence(sequenceNumber)
				.generateInJson();

		sequenceNumberMap.put(sequenceNumber, "");
		++sequenceNumber;
		packetGenerator.setIp(ip);
		packetGenerator.setPort(port);
		try {
			DatagramPacket packet = packetGenerator.packet(message);
			commLayer.sendMessage(packet);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		responseCheckThread = new Thread(new Runnable() {


			@Override
			public void run() {
				try {
					Thread.sleep(watingTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				displayErrorMessage("There is no response From server");
			}
		});
		responseCheckThread.start();
	}

	private void disableSearching() {
		view.searchButton().setEnabled(false);
		view.deactivateButton().setEnabled(false);
		view.activateButton().setEnabled(true);
	}

	private void enableSearching() {
		view.searchButton().setEnabled(true);
		view.deactivateButton().setEnabled(true);
		view.activateButton().setEnabled(false);
	}

}
