package mgd.myphotos.presenters;

import org.json.JSONArray;

import java.util.ArrayList;

import mgd.myphotos.entities.Photo;

/**
 * Created by Myles Doolan on 25/07/2017.
 */
public interface ListOfPhotosPresenter {

    ArrayList<Photo> getListOfPhotos(JSONArray listOfPhotos, int albumId);

    void showPhotos();

}
