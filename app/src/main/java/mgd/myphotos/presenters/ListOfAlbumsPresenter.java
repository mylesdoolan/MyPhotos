package mgd.myphotos.presenters;


import org.json.JSONArray;

/**
 * Created by Myles Doolan on 24/07/2017.
 */

public interface ListOfAlbumsPresenter {

    void getListOfAlbums(JSONArray listOfAlbums);

    void showAlbums();
}