package remote.dictionary.server.controller;

/*
 * @Author : Hanmoi Choi 525910
 */
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import remote.dictionary.interfaces.CommunicationLayer;
import remote.dictionary.interfaces.DataRepository;
import remote.dictionary.interfaces.Observer;
import remote.dictionary.server.comm.ServerComm;
import remote.dictionary.server.view.ServerView;
import remote.dictionary.util.StringChecker;

public class ServerController implements Observer{

	private int threadQueueCapacity = 10;
	private int aliveTimeInMin = 1;
	private int threadPoolCoreSize = 10;
	private int threadPoolMaxSize = 20;


	private CommunicationLayer commLayer;
	private ServerView view;
	private CommunicationLayer.Status commStatus;

	private BlockingQueue<Runnable> threadQueue;
	private ThreadPoolExecutor executor;

	String[] dictFileList = new String[] {
			"dictData_10_Word.xml",
			"dictData_100_Word.xml",
			"dictData_1000_Word.xml",
			"dictData_10000_Word.xml",
	};
	private DataRepository dataRepository;

	public ServerController(ServerView view , CommunicationLayer commLayer, DataRepository dataRepository) {
		this.view = view;
		this.commLayer = commLayer;
		this.dataRepository = dataRepository;

		initActionListener();
		initViewWithDefaultValue(view);

		this.commLayer.register(this);

	}

	private boolean initDictData() {
		boolean isSucceed= false;
		try {
			int selectedIndex = view.dictionaryComboBox().getSelectedIndex();
			String fileName = view.dictionaryComboBox().getItemAt(selectedIndex);
			isSucceed = dataRepository.initDataWithFile(fileName);

		} catch (FileNotFoundException e) {
			displayErrorMessage("File does not exist!");
			e.printStackTrace();
		} catch (IOException e) {
			displayErrorMessage("Please Do Reactivate Again!");
			e.printStackTrace();
		}

		return isSucceed;
	}

	private void initViewWithDefaultValue(ServerView view) {
		try {
			view.ipAddressField().setText(InetAddress.getLocalHost().getHostAddress());
			view.portNumberField().setText("9999");
			view.queueSizeField().setText("10");
			view.poolCoreField().setText("10");
			view.poolMaxField().setText("20");
			view.ipAddressField().setEditable(false);

			for(String dictFile : dictFileList) {
				view.dictionaryComboBox().addItem(dictFile);
			}

			view.deActivationButton().setEnabled(false);
		} catch (UnknownHostException e) {
			displayErrorMessage("Host is Unknown! Please Check your Setting Again");
		}
	}

	private void initActionListener() {
		view.activationButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				handleActivation();
			}

		});
		view.deActivationButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				handleDeActivation();
			}
		});

	}
	private void initThreadPool() {
		threadQueue = new ArrayBlockingQueue<Runnable>(threadQueueCapacity,
				true);
		executor = new ThreadPoolExecutor(threadPoolCoreSize, // core size
				threadPoolMaxSize, // max size
				aliveTimeInMin, // keep alive time
				TimeUnit.MINUTES, // keep alive time units
				threadQueue // the queue to use
				);
	}

	private void handleActivation() {
		String tmpPort = view.portNumberField().getText();
		String tmpQueueSize = view.queueSizeField().getText();
		String tmpPoolCore = view.poolCoreField().getText();
		String tmpPoolMax = view.poolMaxField().getText();

		if(!initDictData()) {
			displayErrorMessage("Activation Failed due to dictionary file reading failure");
			return;
		}

		ServerComm serverComm = ((ServerComm)commLayer);

		if(StringChecker.isPortNumber(tmpPort)
				&& StringChecker.isNumber(tmpQueueSize)
				&& StringChecker.isNumber(tmpPoolCore)
				&& StringChecker.isNumber(tmpPoolMax)) {
			serverComm.setPortNumber(Integer.parseInt(tmpPort));

			commStatus = CommunicationLayer.Status.Activate;
			threadQueueCapacity = Integer.parseInt(tmpQueueSize);
			threadPoolCoreSize = Integer.parseInt(tmpPoolCore);
			threadPoolMaxSize = Integer.parseInt(tmpPoolMax);
			commLayer.activate();
			initThreadPool();
			viewSettingEditable(false);
			switchUiStatusInActivation();
		}
		else {
			displayErrorMessage("Socket Activation Failed, Check your Setting!");
		}
	}

	private void handleDeActivation() {

		if(commStatus == CommunicationLayer.Status.Activate) {
			commStatus = CommunicationLayer.Status.Deactivate;
			commLayer.deactivate();
			viewSettingEditable(true);
			switchUiStatusInDeactivation();
		}
	}

	private void switchUiStatusInActivation() {
		view.activationButton().setEnabled(false);
		view.deActivationButton().setEnabled(true);
		view.dictionaryComboBox().setEnabled(false);
	}

	private void switchUiStatusInDeactivation() {
		view.activationButton().setEnabled(true);
		view.deActivationButton().setEnabled(false);
		view.dictionaryComboBox().setEnabled(true);
	}

	private void displayErrorMessage(String message) {
		view.messagePane().setText(message);
		view.messagePane().setForeground(Color.RED);
	}

	private void viewSettingEditable(boolean isEditable) {
		view.portNumberField().setEditable(isEditable);
		view.queueSizeField().setEditable(isEditable);
		view.poolCoreField().setEditable(isEditable);
		view.poolMaxField().setEditable(isEditable);
	}

	@Override
	public void update(DatagramPacket receivePacket) {
		executor.execute(new ServerRequestHandler(commLayer,
				dataRepository, receivePacket));
	}
}
