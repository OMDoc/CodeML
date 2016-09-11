/* RawCode -- CodeML <rawcode> markup */

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class RawCode {
	private String fileName = null;
	
	public RawCode(String fn) {
		fileName = fn;
	}

	public String getMarkup() throws Exception {
		String line = null;
		StringBuffer result = new StringBuffer();
		BufferedReader br = new BufferedReader(new FileReader(fileName));
	
		try {
			line = new String();
			while ((line = br.readLine()) != null) {
				result.append(line);
				result.append("\n");
			}
		} catch (IOException e) {
			System.out.println("Error reading from file");
			e.printStackTrace();
		}

		result.insert(0, "<rawcode format=\"java\"><![CDATA[\n");
		result.append("]]></rawcode>");

		return result.toString();
	}
}
