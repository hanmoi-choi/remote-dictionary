package remote.dictionary.interfaces;
/*
 * @Author : Hanmoi Choi 525910
 */
import java.net.DatagramPacket;

public interface Observer {

	public void update(DatagramPacket packet);
}
