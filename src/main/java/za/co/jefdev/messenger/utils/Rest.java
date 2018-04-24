package za.co.jefdev.messenger.utils;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;

public class Rest {

//    public static String makeRequest(String uri) throws Exception {
//
//        HttpURLConnection urlConnection;
//        String result;
//        urlConnection = (HttpURLConnection) ((new URL(uri).openConnection()));
//        urlConnection.setRequestProperty("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
//        BufferedReader br;
//        if (urlConnection.getResponseCode() != 200) {
//            throw new ConnectException("Failed : HTTP error code : "
//                    + urlConnection.getResponseCode());
//        }
//        br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//        String line;
//        StringBuilder sb = new StringBuilder();
//        while ((line = br.readLine()) != null) {
//            sb.append(line);
//        }
//        br.close();
//        result = sb.toString();
//        return result;
//    }

    public static String makeEasyRequest(String uri) throws IOException, UnirestException {
        return Unirest.get(uri).asString().getBody();
    }
}
