package pawlowsky;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class AktuellerGoldpreis {
	public static void main(String args[]) {
		String[] yesNoOptions = { "Ja", "Nein"}; 
		int n = JOptionPane.showOptionDialog( null, 
		          "Mit TGM-Proxy ins Internet?",
		          "Proxy",           // title 
		          JOptionPane.YES_NO_OPTION, 
		          JOptionPane.QUESTION_MESSAGE,  // icon 
		          null, yesNoOptions,yesNoOptions[0] ); 
		 if ( n == JOptionPane.YES_OPTION )
				System.setProperty("http.proxyHost", "proxy.tgm.ac.at");
				System.setProperty("http.proxyPort", "3128");
				
		URL ourURL = null;
		HttpURLConnection huc = null;
		try {
			ourURL = new URL("http://www.goldkurse.org/");
			huc = (HttpURLConnection) ourURL.openConnection();
			huc.setRequestMethod("GET");
			try {
				huc.connect();
			} catch (Exception es) {
				es.printStackTrace();
				System.out.println("Exception " + es.getMessage());
				System.out.println("RESPONSE CODE" + huc.getResponseCode());
				Thread.sleep(2000);
				huc.connect();
			}
			String suche = "<strong>Goldkurs</strong></a>           in Dollar je              Feinunze</p>";
			String s = convertStreamToString(huc.getInputStream());
			int index = s.indexOf(suche);
			index += 266;
			String preis = "";
			for(int i = 0; i < 7; i++){
				preis += s.charAt(index);
				index++;
			}
			System.out.println("Der aktuelle Goldpreis in Dollar pro Feinunze beträgt im Moment:");
			System.out.println(preis);
		} catch (IOException ioe) {
			// ioe.printStackTrace();
		} catch (Exception e) {
			System.err.println("General Exception " + e);
			e.printStackTrace();
		}
	}
	public static String convertStreamToString(InputStream is)
            throws IOException {
        /*
         * To convert the InputStream to String we use the
         * Reader.read(char[] buffer) method. We iterate until the
         * Reader return -1 which means there's no more data to
         * read. We use the StringWriter class to produce the string.
         */
        if (is != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(
                        new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {       
            return "";
        }
 
	}
}

