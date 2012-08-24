package remote.dictionary.interfaces;
/*
 * @Author : Hanmoi Choi 525910
 */
import java.net.DatagramPacket;

public interface CommunicationLayer extends Observerable{
	enum Status {
		Activate, Deactivate
	}

	public void sendMessage(DatagramPacket packet);
	public void activate();
	public void deactivate();
}
