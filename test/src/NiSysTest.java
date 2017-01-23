import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by andrew on 1/22/17.
 */
public class NiSysTest {
	public static void main(String args[]) throws Exception {
		URL nisys = new URL("http://71.248.165.187/untitled.xml");
		HttpURLConnection c = (HttpURLConnection) nisys.openConnection();

		c.setUseCaches(false);
		c.setDoOutput(true);

		InputStream is = c.getInputStream();

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(is);
		System.out.println(document);
	}
}
