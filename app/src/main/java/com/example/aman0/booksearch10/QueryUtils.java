package com.example.aman0.booksearch10;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by aman0 on 23-06-2017.
 */

public class QueryUtils {

    /** Tag for the log messages */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public QueryUtils(){}

    public static List<Getdata> fetchbooks(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<Getdata> books = extractfromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return books;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpsURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the book JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    public static List<Getdata> extractfromJson(String URL){
        if (TextUtils.isEmpty(URL)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Getdata> books = new ArrayList<>();

        try {
            ArrayList<String> temp = new ArrayList<String>();
            JSONObject jsonObject = new JSONObject(URL);
            JSONArray items = jsonObject.getJSONArray("items");
            String publisher="No Publisher";

            for(int i=0;i<items.length();i++){
                JSONObject book = items.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                if(volumeInfo.has("publisher"))
                    publisher = volumeInfo.getString("publisher");
                String url = volumeInfo.getString("infoLink");
                JSONArray authors = volumeInfo.getJSONArray("authors");
                for (int j=0;j<authors.length();j++)
                    temp.add(authors.getString(j));
                String au = TextUtils.join(",",temp);
                JSONObject image = volumeInfo.getJSONObject("imageLinks");
                String imgurl = image.getString("thumbnail");
                temp.clear();
                Getdata mbook= new Getdata(title,au,publisher,url,imgurl);
                books.add(mbook);
                publisher=null;
            }
        } catch (JSONException e) {
            Log.e("QueryUtils","Cannot retrieve json ", e);
        }
        return books;
    }
}
