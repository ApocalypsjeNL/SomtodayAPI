package com.apocalypsjenl.util;

import com.apocalypsjenl.entities.WebEntity;
import com.apocalypsjenl.exception.InvalidRequestTypeException;
import com.apocalypsjenl.exception.RequestException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class WebRequest {

    public static String baseUrl = "https://productie.somtoday.nl";
    public final static String clientId = "D50E0C06-32D1-4B41-A137-A9A850C892C2";
    public final static String secretId = "vDdWdKwPNaPCyhCDhaCnNeydyLxSGNJX";
    public final static String userAgent = "SomtodayAPI - made by ApocalypsjeNL";

    public static String postRequest(String url, HashMap<String, String> headers, HashMap<String, String> postParameters) throws RequestException {
        try {
            URL obj = new URL(baseUrl + url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");

            for(String key : headers.keySet()) {
                con.setRequestProperty(key, headers.get(key));
            }

            con.setRequestProperty("User-Agent", userAgent);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            StringJoiner sj = new StringJoiner("&");
            for(Map.Entry<String,String> entry : postParameters.entrySet())
                sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                        + URLEncoder.encode(entry.getValue(), "UTF-8"));

            byte[] postData = sj.toString().getBytes( StandardCharsets.UTF_8 );
            con.setDoOutput(true);
            con.setUseCaches(false);
            try(DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write( postData );
            }

            BufferedReader in;

            if(con.getResponseCode() == 200) {
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RequestException(e.getMessage());
        }
    }

    public static String getRequest(String url, HashMap<String, String> headers) throws RequestException {
        try {
            URL obj = new URL(baseUrl + url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");

            for(String key : headers.keySet()) {
                con.setRequestProperty(key, headers.get(key));
            }

            con.setRequestProperty("User-Agent", userAgent);

            BufferedReader in;

            if(con.getResponseCode() == 200) {
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RequestException(e.getMessage());
        }
    }

    public static void makeRequest(WebEntity entity) throws InvalidRequestTypeException {
        if(entity.getMethod().equals(requestTypes.POST)) {
            try {
                entity.handleResponse(postRequest(entity.getEndpoint(), entity.headers(), entity.parameters()));
            } catch (RequestException e) {
                e.printStackTrace();
            }
        } else if(entity.getMethod().equals(requestTypes.GET)) {
            try {
                entity.handleResponse(getRequest(entity.getEndpoint(), entity.headers()));
            } catch (RequestException e) {
                e.printStackTrace();
            }
        } else {
            throw new InvalidRequestTypeException("Request type " + entity.getMethod().toString() + " is not valid");
        }
    }

    public enum requestTypes {
        GET, POST
    }
}
