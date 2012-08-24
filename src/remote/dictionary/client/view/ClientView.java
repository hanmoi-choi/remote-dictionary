package remote.dictionary.client.view;
/*
 * @Author : Hanmoi Choi 525910
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

public class ClientView extends JFrame{

	private static final int X = 100;
	private static final int Y = 100;
	private static final int WIDTH = 488;
	private static final int HEIGHT = 500;

	private JButton btnActivate;
	private JButton btnDeactivate;
	private JButton btnSearch;
	private JTextField tfIpAddress;
	private JTextField tfHostNumber;
	private JTextField tfSearch;
	private JTextPane tpDefinition;
	private JTextPane tpConsole;
	private JTextField tfWaitingTime;

	/**
	 * Create the application.
	 */
	public ClientView() {
		initialize();
	}

	public JButton activateButton() {
		return btnActivate;
	}

	public JButton deactivateButton() {
		return btnDeactivate;
	}

	public JTextField waitingTimeTextField() {
		return tfWaitingTime;
	}
	public JButton searchButton() {
		return btnSearch;
	}

	public JTextField ipTextField() {
		return tfIpAddress;
	}

	public JTextField portTextField() {
		return tfHostNumber;
	}

	public JTextField searchTestField() {
		return tfSearch;
	}

	public JTextPane definitionTextPane() {
		return tpDefinition;
	}

	public JTextPane consoleTextPane() {
		return tpConsole;
	}

	private void initialize() {
		initFrame();

		initNorthPanel();

		initCenterPanel();

		this.setVisible(true);
	}

	private void initCenterPanel() {
		JPanel centerPanel = new JPanel();
		this.getContentPane().add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));

		JPanel searchPanel = initSearchPanel(centerPanel);

		initNetworkSettingPanel(searchPanel);

		initMessagePanel(centerPanel);

		initMessagePanel(centerPanel);
	}

	private void initMessagePanel(JPanel centerPanel) {
		JPanel messagePanel = new JPanel();
		centerPanel.add(messagePanel, BorderLayout.CENTER);
		messagePanel.setLayout(new GridLayout(2, 0, 0, 5));

		JScrollPane definitionScroll = new JScrollPane();
		messagePanel.add(definitionScroll);

		JPanel definitionPanel = new JPanel();
		definitionScroll.setViewportView(definitionPanel);
		definitionPanel.setLayout(new BorderLayout(0, 0));

		tpDefinition = new JTextPane();
		tpDefinition.setForeground(Color.YELLOW);
		tpDefinition.setBackground(Color.LIGHT_GRAY);
		definitionPanel.add(tpDefinition, BorderLayout.CENTER);

		JLabel lblDefinition = new JLabel("Word Definition");
		lblDefinition.setBackground(Color.GRAY);
		lblDefinition.setForeground(Color.BLUE);
		definitionPanel.add(lblDefinition, BorderLayout.NORTH);

		JScrollPane consoleScroll = new JScrollPane();
		messagePanel.add(consoleScroll);

		JPanel consolePanel = new JPanel();
		consoleScroll.setViewportView(consolePanel);
		consolePanel.setLayout(new BorderLayout(0, 0));

		JLabel lblConsole = new JLabel("Message Console");
		lblConsole.setBackground(Color.GRAY);
		lblConsole.setForeground(Color.RED);
		consolePanel.add(lblConsole, BorderLayout.NORTH);

		tpConsole = new JTextPane();
		tpConsole.setForeground(Color.MAGENTA);
		tpConsole.setBackground(Color.LIGHT_GRAY);
		consolePanel.add(tpConsole, BorderLayout.CENTER);
	}

	private void initNetworkSettingPanel(JPanel searchPanel) {
		JPanel networkSettingPanel = new JPanel();
		searchPanel.add(networkSettingPanel, BorderLayout.NORTH);
		networkSettingPanel.setLayout(new GridLayout(1, 4, 10, 0));

		JLabel lblServerIp = new JLabel("Server IP");
		lblServerIp.setHorizontalAlignment(SwingConstants.CENTER);
		networkSettingPanel.add(lblServerIp);

		tfIpAddress = new JTextField();
		networkSettingPanel.add(tfIpAddress);
		tfIpAddress.setColumns(10);

		JLabel lblServerPortNumber = new JLabel("Port Number");
		lblServerPortNumber.setHorizontalAlignment(SwingConstants.CENTER);
		networkSettingPanel.add(lblServerPortNumber);

		tfHostNumber = new JTextField();
		networkSettingPanel.add(tfHostNumber);
		tfHostNumber.setColumns(10);
	}

	private JPanel initSearchPanel(JPanel centerPanel) {
		JPanel searchPanel = new JPanel();
		centerPanel.add(searchPanel, BorderLayout.NORTH);
		searchPanel.setLayout(new BorderLayout(0, 0));

		btnSearch = new JButton("Search");

		searchPanel.add(btnSearch, BorderLayout.EAST);

		tfSearch = new JTextField();
		searchPanel.add(tfSearch, BorderLayout.CENTER);
		tfSearch.setColumns(5);
		return searchPanel;
	}

	private void initNorthPanel() {
		JPanel northPanel = new JPanel();
		this.getContentPane().add(northPanel, BorderLayout.NORTH);
		GridBagLayout gbl_northPanel = new GridBagLayout();
		gbl_northPanel.columnWidths = new int[]{92, 95, 62, 134, 0};
		gbl_northPanel.rowHeights = new int[]{29, 0};
		gbl_northPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_northPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		northPanel.setLayout(gbl_northPanel);

		btnActivate = new JButton("Activate");
		GridBagConstraints gbc_btnActivate = new GridBagConstraints();
		gbc_btnActivate.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnActivate.insets = new Insets(0, 0, 0, 5);
		gbc_btnActivate.gridx = 0;
		gbc_btnActivate.gridy = 0;
		northPanel.add(btnActivate, gbc_btnActivate);

		btnDeactivate = new JButton("Deactivate");
		GridBagConstraints gbc_btnDeactivate = new GridBagConstraints();
		gbc_btnDeactivate.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnDeactivate.insets = new Insets(0, 0, 0, 5);
		gbc_btnDeactivate.gridx = 1;
		gbc_btnDeactivate.gridy = 0;
		northPanel.add(btnDeactivate, gbc_btnDeactivate);

		JLabel lblWaitingTime = new JLabel("Wait Time(Milli Sec)");
		GridBagConstraints gbc_lblWaitingTime = new GridBagConstraints();
		gbc_lblWaitingTime.insets = new Insets(0, 0, 0, 5);
		gbc_lblWaitingTime.gridx = 2;
		gbc_lblWaitingTime.gridy = 0;
		northPanel.add(lblWaitingTime, gbc_lblWaitingTime);
		lblWaitingTime.setHorizontalAlignment(SwingConstants.CENTER);

		tfWaitingTime = new JTextField();
		GridBagConstraints gbc_tfWaitingTime = new GridBagConstraints();
		gbc_tfWaitingTime.anchor = GridBagConstraints.NORTH;
		gbc_tfWaitingTime.gridx = 3;
		gbc_tfWaitingTime.gridy = 0;
		northPanel.add(tfWaitingTime, gbc_tfWaitingTime);
		tfWaitingTime.setColumns(10);
	}

	private void initFrame() {
		this.setTitle("Client - Remote Dictionary");
		this.setBounds(X, Y, WIDTH, HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		ClientView view = new ClientView();
		view.pack();
		view.setVisible(true);
	}

}
