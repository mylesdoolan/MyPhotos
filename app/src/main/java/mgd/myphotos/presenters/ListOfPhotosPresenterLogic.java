package mgd.myphotos.presenters;

import org.json.JSONArray;

import java.util.ArrayList;

import mgd.myphotos.entities.Album;
import mgd.myphotos.entities.Photo;
import mgd.myphotos.interactors.ListOfPhotosInteractor;
import mgd.myphotos.interactors.ListOfPhotosInteractorLogic;
import mgd.myphotos.views.ListOfPhotosView;

/**
 * Created by Myles Doolan on 25/07/2017.
 */

public class ListOfPhotosPresenterLogic implements ListOfPhotosPresenter {


    private ListOfPhotosView view;
    private ListOfPhotosInteractor interactor;

    public ListOfPhotosPresenterLogic(ListOfPhotosView integerView, ListOfPhotosInteractorLogic interactor) {
        this.view = integerView;
        this.interactor = interactor;
    }

    @Override
    public ArrayList<Photo> getListOfPhotos(JSONArray listOfAlbums, int albumId) {
        return interactor.saveListOfPhotos(listOfAlbums, albumId);
    }

    @Override
    public void showPhotos() {
        view.hideSpinner();
        view.addPhotos(interactor.getPhotos());

    }

}
