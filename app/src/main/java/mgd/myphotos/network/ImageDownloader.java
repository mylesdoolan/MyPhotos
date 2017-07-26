package mgd.myphotos.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import mgd.myphotos.entities.Photo;


/**
 * Created by Myles Doolan on 10/07/2017
 *
 * Async Task for requesting the JSON response
 */
public abstract class ImageDownloader extends AsyncTask<ArrayList<Photo>, Void, ArrayList<Photo>> {

    private final String TAG = "Connection";

    protected Bitmap connect(String urlString) {
        byte[] imageBytes;
        Bitmap image = null;
        try {
            URL url = new URL(urlString);
            Log.d(TAG, "\n" + url);
            // using http instead of https because image assets are returned as http. Would normally use https for all network requests / response's
            HttpURLConnection httpConnection = buildRequestHeader((HttpURLConnection) url.openConnection());
            imageBytes = receiveResponse(httpConnection);
            if (imageBytes != null) {
                image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            }
        } catch (MalformedURLException e) {
            Log.d(TAG, "MalformedURLException\n" + e);
        } catch (IOException e) {
            Log.d(TAG, "IOException\n" + e);
        } catch (JSONException e) {
            Log.d(TAG, "JSONException\n" + e);
        }
        return image;
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
     * @param httpConnection - the connection between app and vordel
     **/
    private byte[] receiveResponse(HttpURLConnection httpConnection) throws JSONException, IOException {
        try {
            InputStream is = (InputStream) httpConnection.getContent();
            byte[] buffer = new byte[8192];
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            while ((bytesRead = is.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            return output.toByteArray();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}