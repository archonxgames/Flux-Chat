//Liscensed MIT
package com.tolate.sysutil;

import java.io.*;
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;

public class HttpsUtil {
	String token;

	public void Init(String tokenString) {
		token = tokenString;
	}

	public void sendSecureGET(String GET_URL, String USER_AGENT) throws IOException {
		URL obj = new URL(GET_URL);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpsURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
		} else {
			System.out.println("[com.tolate.sysutil.httpsutil] GET request failed: "+responseCode);
		}

	}

	public void sendSecurePOST(String POST_URL, String USER_AGENT, String POST_PARAMS) throws IOException {
		URL obj = new URL(POST_URL);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.addRequestProperty("authorization", token);

		// For POST only - START
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		os.write(POST_PARAMS.getBytes());
		os.flush();
		os.close();
		// For POST only - END

		int responseCode = con.getResponseCode();
		System.out.println("POST Response Code :: " + responseCode);

		if (responseCode == HttpsURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
		} else {
			System.out.println("[com.tolate.sysutil.httpsutil] POST request failed: "+responseCode);
		}
	}

}
