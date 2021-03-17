package packA;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class HttpTestDemo {

	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		HttpClient client = HttpClient.newHttpClient();

		String url = "http://www.redbus.in/info/aboutus";

		HttpRequest req = HttpRequest.newBuilder(new URI(url))
									 .version(Version.HTTP_2)
									 .timeout(Duration.ofMillis(10000))
									 .GET()
									 .build();

		HttpResponse<String> resp = client.send(req, BodyHandlers.ofString());
		System.out.println("Status Code : " + resp.statusCode());
		System.out.println("Response Body :" + resp.body());

		HttpHeaders header = resp.headers();

		Map<String, List<String>> map = header.map();

		System.out.println("Response Headers");

		map.forEach((k, v) -> System.out.println("\t" + k + ":" + v));

	}

}
