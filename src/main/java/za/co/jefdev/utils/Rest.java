package za.co.jefdev.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Rest {

    public static String makeRequest(String uri) throws IOException {

        HttpURLConnection urlConnection;
        String result;
        urlConnection = (HttpURLConnection) ((new URL(uri).openConnection()));
        urlConnection.setRequestProperty("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        BufferedReader br;
        if (200 <= urlConnection.getResponseCode() && urlConnection.getResponseCode() <= 299) {
            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
        }
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        result = sb.toString();
        return result;
    }
}
