package external;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;


public class GitHubClient {
	// predefine the URL of API.
	private static final String URL_TEMPLATE = "https://jobs.github.com/positions.json?description=%s&lat=%s&long=%s";
	//if the description is null, we use developer as parameter, description = developer.
	private static final String DEFAULT_KEYWORD = "developer";
	
	public JSONArray search(double lat, double lon, String keyword) {
		//Prepare HTTP request parameter
		if(keyword == null) {
			keyword = DEFAULT_KEYWORD;
		}
		try {
			keyword = URLEncoder.encode(keyword, "UTF-8");// Rick Sun -> Rick+Sun
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();// print out the exception.
		}
		String url = String.format(URL_TEMPLATE, keyword, lat, lon);
		//Send HTTP request
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//use execute method to send request.
		try {
			//HttpGet(URL) return a get type request.
			CloseableHttpResponse response = httpClient.execute(new HttpGet(url));
			//Get HTTP response body
			if(response.getStatusLine().getStatusCode() != 200) {
				return new JSONArray();
			}
			HttpEntity entity = response.getEntity();
			if(entity == null) {
				return new JSONArray();
			}
			//read data for line by line.
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			StringBuilder responseBody = new StringBuilder();
			String line = null;
			while((line = reader.readLine()) != null) {
				responseBody.append(line);
			}
			return new JSONArray(responseBody.toString());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new JSONArray();
	}
	


}
