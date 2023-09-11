import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class Main {
	public static void main(String[] args) {
		System.out.println("Hello world!");

		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}
			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		}
		};

		// Install the all-trusting trust manager
		SSLContext sc;
		try {
			sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			e.printStackTrace();
		}

		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = (hostname, session) -> true;
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

		// Install the all-trusting host verifier
		try {
			getPage("https://dancs.org/nye/2022-23/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void getPage (String page_url) throws IOException {
		System.out.println("page url: " + page_url);
		var d = Jsoup.connect(page_url).ignoreHttpErrors(true).get();
		var elements = d.getElementsByClass("odd");
		elements.addAll(d.getElementsByClass("even"));
		for (Element e : elements) {
			var link = e.getElementsByTag("a").get(0);
			var url = link.attr("href");
			System.out.println("href: " + url);
			if (url.contains("/")) {
				getPage(page_url + url);
				System.out.println("directory: " + page_url + url);
			}
			else {
				System.out.println("file: " + page_url + url);
				downloadFile(page_url + url);
			}
		}
	}

	public static void downloadFile (String url) throws IOException {
		System.out.println("dowlnoad: " + url);
		var location = url.substring(url.indexOf('/', 8) + 1);
		if (Files.exists(Path.of(location))) {
			System.out.println("a fájl már létezik");
			return;
		}
		var stream = new URL(url).openStream();
		var bytes = stream.readAllBytes();
		stream.close();
		Files.createDirectories(Path.of(location.substring(0, location.lastIndexOf('/'))));
		Files.write(Path.of(location), bytes);
	}
}
