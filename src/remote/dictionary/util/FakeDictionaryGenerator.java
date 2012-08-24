package remote.dictionary.util;

/*
 * @Author : Hanmoi Choi 525910
 */
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class FakeDictionaryGenerator {

	private Properties dictionaryProperty;

	public FakeDictionaryGenerator(int howMany) {
		// TODO Auto-generated constructor stub
		dictionaryProperty = new Properties();
		int i = 0;

		for(; i < howMany ; i++) {
			dictionaryProperty.setProperty("FakeWord" + i, "Fake Definition" + i);
		}

		try {
			dictionaryProperty.storeToXML(new BufferedOutputStream(new FileOutputStream(new File("dictionaryDataWith_"+ i+ "_Word.xml"))), "Dictionary File");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		new FakeDictionaryGenerator(2000000);
	}
}
