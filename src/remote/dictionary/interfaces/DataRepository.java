package remote.dictionary.interfaces;
/*
 * @Author : Hanmoi Choi 525910
 */
import java.io.FileNotFoundException;
import java.io.IOException;

public interface DataRepository {

	public boolean initDataWithFile(String fileName) throws FileNotFoundException, IOException;

	public String getData(String key);

}