package remote.dictionary.util;
/*
 * @Author : Hanmoi Choi 525910
 */
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class PacketGenerator {

	private String ip;
	private int portNumber;

	public PacketGenerator() {
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		portNumber = port;
	}


	public DatagramPacket packet(String message) throws SocketException, UnknownHostException {


		return new DatagramPacket(message.getBytes(),
				message.getBytes().length,
				InetAddress.getByName(ip),
				portNumber);
	}

}
