package mgd.myphotos.views;

import java.util.ArrayList;

import mgd.myphotos.entities.Photo;

/**
 * Created by Myles Doolan on 25/07/2017.
 */

public interface ListOfPhotosView {

    void hideSpinner();

    void addPhotos(ArrayList<Photo> photoArrayList);

}
