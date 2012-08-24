package remote.dictionary;
/*
 * @Author : Hanmoi Choi 525910
 */
import remote.dictionary.interfaces.CommunicationLayer;
import remote.dictionary.interfaces.DataRepository;
import remote.dictionary.server.comm.ServerComm;
import remote.dictionary.server.controller.ServerController;
import remote.dictionary.server.view.ServerView;
import remote.dictionary.util.DictionaryRepository;

public class ServerApp {

	public ServerApp() {
		ServerView window = new ServerView();
		window.pack();
		window.setVisible(true);
		CommunicationLayer commLayer = new ServerComm();
		DataRepository dataRepository = new DictionaryRepository();
		new ServerController(window, commLayer, dataRepository);
	}

	public static void main(String[] args) {
		new ServerApp();
	}
}
