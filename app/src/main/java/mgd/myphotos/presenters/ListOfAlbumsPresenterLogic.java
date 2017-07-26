package mgd.myphotos.presenters;

import org.json.JSONArray;

import mgd.myphotos.entities.Album;
import mgd.myphotos.interactors.ListOfAlbumsInteractor;
import mgd.myphotos.views.ListOfAlbumsView;

/**
 * Created by Myles Doolan on 24/07/2017.
 */
public class ListOfAlbumsPresenterLogic  implements ListOfAlbumsPresenter {

    private ListOfAlbumsView view;
    private ListOfAlbumsInteractor interactor;

    public ListOfAlbumsPresenterLogic(ListOfAlbumsView integerView, ListOfAlbumsInteractor interactor) {
        this.view = integerView;
        this.interactor = interactor;
    }

    @Override
    public void getListOfAlbums(JSONArray listOfAlbums) {
        interactor.saveListOfAlbums(listOfAlbums);
    }

    @Override
    public void showAlbums() {
        view.hideSpinner();
        for (Album album : interactor.getAlbums()) {
            view.addAlbum(album.getName(), album.getAlbumId());
        }
    }


}
