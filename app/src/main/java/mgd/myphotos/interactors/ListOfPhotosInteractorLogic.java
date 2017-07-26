package mgd.myphotos.interactors;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import mgd.myphotos.entities.Photo;

/**
 * Created by Myles Doolan on 25/07/2017.
 */
public class ListOfPhotosInteractorLogic implements ListOfPhotosInteractor {

    private String TAG = "LOPInteractorLogic";
    private final String ALBUM_ID = "albumId";
    private final String PHOTO_ID = "id";
    private final String TITLE = "title";
    private final String URL = "url";
    private final String THUMBNAIL_URL = "thumbnailUrl";
    private ArrayList<Photo> photosArray;
    private int albumId;

    @Override
    public ArrayList<Photo> getPhotos() {
        return photosArray;
    }

    @Override
    public ArrayList<Photo> saveListOfPhotos(JSONArray arrayOfPhotos, int albumId) {
        this.albumId = albumId;
        photosArray = sortArray(buildArray(arrayOfPhotos));
        for (Photo photo : photosArray) {
            Log.d(TAG, photo.getTitle());
        }
        return photosArray;
    }

    public ArrayList<Photo> buildArray(JSONArray arrayOfPhotos) {
        ArrayList<Photo> albumsPhoto = new ArrayList<>();
        try {
            for (int i = 0; i < arrayOfPhotos.length(); i++) {
                JSONObject photoObject = arrayOfPhotos.getJSONObject(i);
                if (photoObject.getInt(ALBUM_ID) == albumId) {
                    Photo photo = new Photo(photoObject.getString(TITLE), photoObject.getString(URL), photoObject.getString(THUMBNAIL_URL));
                    albumsPhoto.add(photo);
                }
            }
        } catch (JSONException e) {
            Log.d(TAG, e.getLocalizedMessage());
        }
        return albumsPhoto;
    }

    public ArrayList<Photo> sortArray(ArrayList<Photo> albumsArray) {
        Collections.sort(albumsArray, new Comparator<Photo>() {
            public int compare(Photo firstPhoto, Photo secondPhoto) {
                return firstPhoto.getThumbnailUrl().compareTo(secondPhoto.getThumbnailUrl());
            }
        });
        return albumsArray;
    }
}
