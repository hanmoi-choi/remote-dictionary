package remote.dictionary.server.view;
/*
 * @Author : Hanmoi Choi 525910
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

public class ServerView extends JFrame{

	private JTextField tfPortNumber;
	private JTextField tfIpAddress;
	private JTextField tfQueueSize;
	private JTextField tfPoolCoreSize;
	private JTextField tfPoolMaxSize;
	private JTextPane tpMessage;
	private JButton btnActivate;
	private JButton btnDeactivate;
	private JComboBox<String> cbDictFiles;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		ServerView window = new ServerView();
		window.setVisible(true);

	}

	public ServerView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		initFrame();
		initNorthPanel();
		initCenterPanel();
	}

	private void initNorthPanel() {
		JPanel northPanel = new JPanel();
		this.getContentPane().add(northPanel, BorderLayout.NORTH);

		btnActivate = new JButton("Activate");
		northPanel.add(btnActivate);

		btnDeactivate = new JButton("DeActivate");
		northPanel.add(btnDeactivate);

		cbDictFiles = new JComboBox<String>();
		cbDictFiles.setPreferredSize(new Dimension(240, 30));
		northPanel.add(cbDictFiles);
	}


	private void initCenterPanel() {
		JPanel centerPanel = initSettingPanel();

		initMessagePanel(centerPanel);
	}

	private void initMessagePanel(JPanel centerPanel) {
		JPanel messagePanel = new JPanel();
		centerPanel.add(messagePanel);
		messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.X_AXIS));

		JScrollPane scrollPane = new JScrollPane();
		messagePanel.add(scrollPane);

		tpMessage = new JTextPane();
		tpMessage.setForeground(Color.MAGENTA);
		tpMessage.setBackground(Color.LIGHT_GRAY);
		scrollPane.setViewportView(tpMessage);

		JLabel lblMessage = new JLabel("Message Console");
		lblMessage.setForeground(Color.RED);
		lblMessage.setBackground(Color.GRAY);
		scrollPane.setColumnHeaderView(lblMessage);
	}


	private JPanel initSettingPanel() {
		JPanel centerPanel = new JPanel();
		this.getContentPane().add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new GridLayout(2, 1));

		JPanel settingPanel = new JPanel();
		centerPanel.add(settingPanel);
		settingPanel.setLayout(new GridLayout(5, 2));

		JLabel lblIpAddress = new JLabel("IP Address");
		lblIpAddress.setHorizontalAlignment(SwingConstants.CENTER);
		settingPanel.add(lblIpAddress);

		tfIpAddress = new JTextField();
		settingPanel.add(tfIpAddress);
		tfIpAddress.setColumns(10);

		JLabel lblPortNumber = new JLabel("Port Number");
		lblPortNumber.setHorizontalAlignment(SwingConstants.CENTER);
		settingPanel.add(lblPortNumber);

		tfPortNumber = new JTextField();
		settingPanel.add(tfPortNumber);
		tfPortNumber.setColumns(10);

		JLabel lblQueueSize = new JLabel("Thread Queue Capacity");
		lblQueueSize.setHorizontalAlignment(SwingConstants.CENTER);
		settingPanel.add(lblQueueSize);

		tfQueueSize = new JTextField();
		settingPanel.add(tfQueueSize);
		tfQueueSize.setColumns(10);

		JLabel lblPoolCoreSize = new JLabel("Thread Pool Core Size");
		lblPoolCoreSize.setHorizontalAlignment(SwingConstants.CENTER);
		settingPanel.add(lblPoolCoreSize);

		tfPoolCoreSize = new JTextField();
		settingPanel.add(tfPoolCoreSize);
		tfPoolCoreSize.setColumns(10);

		JLabel lblPoolMaxSize = new JLabel("Thread Pool Max Size");
		lblPoolMaxSize.setHorizontalAlignment(SwingConstants.CENTER);
		settingPanel.add(lblPoolMaxSize);

		tfPoolMaxSize = new JTextField();
		settingPanel.add(tfPoolMaxSize);
		tfPoolMaxSize.setColumns(10);

		return centerPanel;
	}

	private void initFrame() {
		this.setTitle("Server - Remote Dictionary");
		this.setBounds(100, 100, 482, 460);
		getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	public JButton activationButton() {
		return btnActivate;
	}

	public JButton deActivationButton() {
		return btnDeactivate;
	}

	public JComboBox<String> dictionaryComboBox() {
		return cbDictFiles;
	}

	public JTextField portNumberField() {
		return tfPortNumber;
	}

	public JTextField ipAddressField() {
		return tfIpAddress;
	}

	public JTextField queueSizeField() {
		return tfQueueSize;
	}

	public JTextField poolCoreField() {
		return tfPoolCoreSize;
	}

	public JTextField poolMaxField() {
		return tfPoolMaxSize;
	}

	public JTextPane messagePane() {
		return tpMessage;
	}

}
