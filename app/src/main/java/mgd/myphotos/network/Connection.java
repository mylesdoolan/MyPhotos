package mgd.myphotos.network;

/**
 * Created by Myles Doolan on 24/07/2017.
 */

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by Myles Doolan on 10/07/2017
 *
 * Async Task for requesting the JSON response
 */
public abstract class Connection extends AsyncTask<String, Void, String> {

    private final String TAG = "Connection";

    public String connect(String urlString) {
        String responseBody = null;
        try {
            URL url = new URL(urlString);
            Log.d(TAG, "\n" + url);
            // using http instead of https because image assets are returned as http. Would normally use https for all network requests / response's
            HttpURLConnection httpConnection = buildRequestHeader((HttpURLConnection) url.openConnection());

            int statusCode =  httpConnection.getResponseCode();
            responseBody = receiveResponse(statusCode, httpConnection);
        } catch (MalformedURLException e) {
            Log.d(TAG, "MalformedURLException\n" + e);
        } catch (IOException e) {
            Log.d(TAG, "IOException\n" + e);
        } catch (JSONException e) {
            Log.d(TAG, "JSONException\n" + e);
        }
        return responseBody;
    }

    /**
     * Builds the HTTP request header
     *
     * @param httpConnection The connection being created for the Vordel request
     */
    @NonNull
    private HttpURLConnection buildRequestHeader(HttpURLConnection httpConnection) throws IOException {
        final int TIMEOUT_CONNECTION = 10000;
        httpConnection.setReadTimeout(TIMEOUT_CONNECTION);
        httpConnection.setConnectTimeout(TIMEOUT_CONNECTION);
        httpConnection.setRequestMethod("GET");
        httpConnection.setDoInput(true);

        return httpConnection;
    }


    /**
     * Reads the response from Vordell and converts it into a JSON Object
     *
     * @param statusCode      - the http response code confirming if success or error
     * @param httpConnection - the connection between app and vordel
     **/
    private String receiveResponse(int statusCode, HttpURLConnection httpConnection) throws JSONException, IOException {
        final String UTF = "UTF-8";
        BufferedReader reader;
        if (statusCode < 300) {
            reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), UTF));
        } else {
            reader = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream(), UTF));
        }
        StringBuilder builder = new StringBuilder();
        for (String line; (line = reader.readLine()) != null; ) {
            builder.append(line).append("\n");
        }
        Log.d(TAG, "Status - \n" + statusCode);
        return builder.toString();
    }
}