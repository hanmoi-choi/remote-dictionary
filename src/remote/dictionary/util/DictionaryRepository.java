package remote.dictionary.util;

/*
 * @Author : Hanmoi Choi 525910
 */
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import remote.dictionary.interfaces.DataRepository;

public class DictionaryRepository implements DataRepository {

	private static final String DICT_FILE_DIR = "dictdata/";
	private Properties dictionaryProperty;
	private BufferedInputStream bis;

	public DictionaryRepository() {
		dictionaryProperty = new Properties();
	}

	@Override
	public boolean initDataWithFile(String fileName) throws InvalidPropertiesFormatException, IOException {
		bis = new BufferedInputStream(new FileInputStream(new File(DICT_FILE_DIR + fileName)));
		dictionaryProperty.loadFromXML(bis);
		bis.close();

		return true;
	}

	@Override
	public String getData(String key) {
		return dictionaryProperty.getProperty(key);
	}

}
