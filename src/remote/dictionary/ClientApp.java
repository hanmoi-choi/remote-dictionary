package remote.dictionary;
/*
 * @Author : Hanmoi Choi 525910
 */
import remote.dictionary.client.comm.ClientComm;
import remote.dictionary.client.controller.ClientController;
import remote.dictionary.client.view.ClientView;
import remote.dictionary.interfaces.CommunicationLayer;

public class ClientApp {

	public ClientApp() {
		ClientView window = new ClientView();
		CommunicationLayer commLayer = new ClientComm();
		new ClientController(window, commLayer);
	}

	public static void main(String[] args) {
		new ClientApp();
	}
}
