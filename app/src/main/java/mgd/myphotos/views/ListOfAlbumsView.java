package mgd.myphotos.views;

/**
 * Created by Myles Doolan on 24/07/2017.
 */

public interface ListOfAlbumsView {

    void hideSpinner();

    void addAlbum(String albumName,  int albumId);

}
