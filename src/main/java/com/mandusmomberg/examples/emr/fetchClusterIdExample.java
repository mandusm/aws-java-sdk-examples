package com.mandusmomberg.examples.emr;
import java.io.*;
import java.net.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


/* 
 * This Example makes use of the EMR Configuration API which is available on every EMR Node
 * The configuration node exposes an HTTP endpoint on the loopback interface port 8321
 * http://localhost:8321/configuration
 * It contains configuration metadata of the speciefic node where the query is taking place.
 * To see the full output, on your cluster make a CURL request like this
 * http://localhost:8321/configuration
 * 
 */
public class fetchClusterIdExample {
	public static void main(String args[]) throws Exception{
		System.out.println("Example For Retriving Cluster ID from Cluster Metadata during Runtime");
		
		URL url = null;
		HttpURLConnection conn = null;
		try {
			url = new URL("http://localhost:8321/configuration");
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if ( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) {
			BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
 
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject json = (JSONObject)new JSONParser().parse(response.toString());
            System.out.println("Cluster ID: " + json.get("clusterId"));
            System.out.println("Node Instance Type: " + json.get("instanceType"));
            System.out.println("Node Role in Cluster: " + json.get("nodeType"));
		} else {
			System.out.println("Could not fetch Configuration Metadata from Endpoint");
			System.out.println(conn.getResponseCode());
		}
		
		
	}
}
