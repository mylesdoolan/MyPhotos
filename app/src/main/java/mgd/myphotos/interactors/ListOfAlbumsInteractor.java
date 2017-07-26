package mgd.myphotos.interactors;

import org.json.JSONArray;

import java.util.ArrayList;

import mgd.myphotos.entities.Album;


/**
 * Created by Myles Doolan on 24/07/2017.
 */

public interface ListOfAlbumsInteractor {

    ArrayList<Album> saveListOfAlbums(JSONArray ArrayOfAlbums);
    ArrayList<Album> getAlbums();

}
