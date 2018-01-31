package hsi.pruebas;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Prueba {

	public static void main(String[] args) throws IOException {
		String destinationFile;
		URL url = new URL("http://wow.zamimg.com/images/hearthstone/cards/enus/original/EX1_572.png");
		
		File file = new File(url.getFile());
		destinationFile = file.getName();
		
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(new File("10.0.0.22611/images", destinationFile));

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	 
	}

}
