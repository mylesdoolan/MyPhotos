package mgd.myphotos.interactors;

import org.json.JSONArray;

import java.util.ArrayList;

import mgd.myphotos.entities.Photo;

/**
 * Created by Myles Doolan on 25/07/2017.
 */

public interface ListOfPhotosInteractor {

    ArrayList<Photo> saveListOfPhotos(JSONArray ArrayOfAlbums, int albumId);

    ArrayList<Photo> getPhotos();
}
