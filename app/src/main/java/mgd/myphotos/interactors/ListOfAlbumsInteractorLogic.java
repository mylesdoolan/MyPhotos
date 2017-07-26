package mgd.myphotos.interactors;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import mgd.myphotos.entities.Album;

/**
 * Created by Myles Doolan on 24/07/2017.
 */

public class ListOfAlbumsInteractorLogic implements ListOfAlbumsInteractor {

    private String TAG = "LOAInteractorLogic";
    private final String USER_ID = "userId";
    private final String ALBUM_ID = "id";
    private final String NAME = "title";
    private ArrayList<Album> albumsArray;

    @Override
    public ArrayList<Album> getAlbums() {
        return albumsArray;
    }

    @Override
    public ArrayList<Album> saveListOfAlbums(JSONArray arrayOfAlbums){
        albumsArray = sortArray(buildArray(arrayOfAlbums));
        return albumsArray;
    }

    public ArrayList<Album> buildArray(JSONArray arrayOfAlbums) {
        ArrayList<Album> albumsArray = new ArrayList<>();
        try {
            for (int i = 0; i < arrayOfAlbums.length(); i++) {
                JSONObject albumObject = arrayOfAlbums.getJSONObject(i);
                Album album = new Album(albumObject.getInt(USER_ID), albumObject.getInt(ALBUM_ID), albumObject.getString(NAME));
                albumsArray.add(album);
            }
        } catch (JSONException e) {
            Log.d(TAG, e.getLocalizedMessage());
        }
        return albumsArray;
    }

    public ArrayList<Album> sortArray(ArrayList<Album> albumsArray) {
        Collections.sort(albumsArray, new Comparator<Album>() {
            public int compare(Album firstAlbum, Album secondAlbum) {
                return firstAlbum.getName().compareTo(secondAlbum.getName());
            }
        });
        return albumsArray;
    }




}
