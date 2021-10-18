package ca.nait.dmit.dmit2504.chattergen2;

import android.net.Uri;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class NetworkAPI {

    public byte[] getResponseBytes(String urlString) throws Exception {
        // Step 1: Create a URL object
        URL getUrl = new URL(urlString);
        // Step 2: Open a connection
        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
        // Step 3: Set the request method
        connection.setRequestMethod("GET");
        // Step 4: Read the response from the input stream
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = connection.getInputStream();

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            String message = String.format("Error code %d\nError message %s", responseCode, connection.getResponseMessage());
            throw new Exception(message);
        }

        int bytesRead = 0;
        byte[] buffer = new byte[1024];
        while ( (bytesRead = in.read(buffer)) > 0) {
            out.write(buffer, 0, bytesRead);
        }
        out.close();

        return out.toByteArray();
    }

    public String getResponseString(String urlString) throws Exception {
        byte[] responseBytes = getResponseBytes(urlString);
        String responseString = new String(responseBytes);
        return responseString;
    }

    public int postFormData(String urlString, Map<String, String> requestFormDataMap) {
        int responseCode = HttpURLConnection.HTTP_BAD_REQUEST;

        try {
            // Step 1: Create a URL object
            URL postUrl = new URL(urlString);
            // Step 2: Open a connection
            HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
            // Step 3: Set the request method
            connection.setRequestMethod("POST");
            // Step 4: Set the request content-type header parameter
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            // Step 5: Enable the connection to send output
            connection.setDoOutput(true);
            // Step 6: Create the request body
            // Convert they key-value pairs from the requestFormDataMap to a query string in the format
            //  "paramName1=param1Value&paramName2&param2Value"
            StringBuilder requestBodyBuilder = new StringBuilder();
            requestFormDataMap.forEach((key, value) -> {
                if (requestBodyBuilder.length() > 0) {
                    requestBodyBuilder.append("&");
                }
                requestBodyBuilder.append(String.format("%s=%s", key, Uri.encode(value,"utf-8")));
            });
            String requestBody = requestBodyBuilder.toString();

            OutputStream out = new BufferedOutputStream(connection.getOutputStream());
            out.write(requestBody.getBytes(StandardCharsets.UTF_8));
            out.close();

            responseCode = connection.getResponseCode();

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseCode;
    }
}
