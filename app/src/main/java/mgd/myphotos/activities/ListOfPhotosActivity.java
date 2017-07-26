package mgd.myphotos.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import mgd.myphotos.R;
import mgd.myphotos.adapters.ThumbnailsAdapter;
import mgd.myphotos.entities.Photo;
import mgd.myphotos.interactors.ListOfPhotosInteractorLogic;
import mgd.myphotos.network.Connection;
import mgd.myphotos.network.ImageDownloader;
import mgd.myphotos.presenters.ListOfPhotosPresenter;
import mgd.myphotos.presenters.ListOfPhotosPresenterLogic;
import mgd.myphotos.views.ListOfPhotosView;

import static android.view.View.GONE;

/**
 * Created by Myles Doolan on 25/07/2017.
 * <p>
 * Shows the list of photos returned from the album
 */

public class ListOfPhotosActivity extends AppCompatActivity implements ListOfPhotosView {

    private final String TAG = "ListOfPhotosActivity";
    private RetainedPhotosFragment retainedFragment;
    private ListOfPhotosPresenter presenter;

    private ProgressBar spinner;
    private GridView photoGrid;
    private int albumId;
    final String ALBUM_ID = "albumId";
    AsyncTask networkAsyncTask;
    AsyncTask bitmapDownloadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        presenter = new ListOfPhotosPresenterLogic(this, new ListOfPhotosInteractorLogic());
        spinner = (ProgressBar) findViewById(R.id.progress_bar);
        photoGrid = (GridView) findViewById(R.id.grid_view);

        setupListOfPhotos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void hideSpinner() {
        spinner.setVisibility(GONE);
    }

    @Override
    public void addPhotos(ArrayList<Photo> photoArrayList) {
        ThumbnailsAdapter thumbnailsAdapter = new ThumbnailsAdapter(this, retainedFragment.getPhotoArrayList());
        photoGrid.setAdapter(thumbnailsAdapter);
    }

    public static class RetainedPhotosFragment extends Fragment {

        private JSONArray albums;
        private ArrayList<Photo> photoArrayList;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // The key to making data survive runtime configuration changes.
            setRetainInstance(true);
        }

        public JSONArray getData() {
            return this.albums;
        }

        public void setData(JSONArray albums) {
            this.albums = albums;
        }


        public ArrayList<Photo> getPhotoArrayList() {
            return this.photoArrayList;
        }

        public void setPhotoArrayList(ArrayList<Photo> photoArrayList) {
            this.photoArrayList = photoArrayList;
        }

    }

    private void setupListOfPhotos() {
        final String retainedFragmentTag = "RetainedFragmentTag";
        albumId = getIntent().getIntExtra(ALBUM_ID, -1);
        final String url = "https://jsonplaceholder.typicode.com/photos";

        Log.d(TAG, "albumId = " + albumId);
        // Find the RetainedFragment on Activity restarts
        FragmentManager fragmentManager = getSupportFragmentManager();
        // The RetainedFragment has no UI so we must reference it with a tag.
        retainedFragment = (RetainedPhotosFragment) fragmentManager.findFragmentByTag(retainedFragmentTag);

        // if Retained Fragment doesn't exist create and add it.
        if (retainedFragment == null) {
            retainedFragment = new RetainedPhotosFragment();
            // Add the fragment
            fragmentManager.beginTransaction().add(retainedFragment, retainedFragmentTag).commitAllowingStateLoss();
            networkAsyncTask = new LoadContentFromNetworkAsyncTask(url).execute();
        } else {
            // The Retained Fragment exists
            presenter.getListOfPhotos(retainedFragment.getData(), albumId);
            presenter.showPhotos();
        }
    }

    protected void showData() {
        presenter.showPhotos();
    }

    private class LoadContentFromNetworkAsyncTask extends Connection {

        private final String TAG = "NetworkAsyncTask";
        String url;

        LoadContentFromNetworkAsyncTask(String url) {
            this.url = url;
        }

        @Override
        protected String doInBackground(String... urls) {
            return connect(url);
        }

        @Override
        protected void onPostExecute(String response) {
            if (!this.isCancelled()) {
                try {
                    retainedFragment.setData(new JSONArray(response));
                } catch (JSONException e) {
                    Log.d(TAG, e.getLocalizedMessage());
                }
                retainedFragment.setPhotoArrayList(presenter.getListOfPhotos(retainedFragment.getData(), albumId));
                download(retainedFragment.getPhotoArrayList());
            }
        }
    }

    /**
     * The actual AsyncTask that will asynchronously download the image.
     */
    private class BitmapDownloaderTask extends ImageDownloader {
        private ArrayList<Photo> photoArrayList;

        BitmapDownloaderTask(ArrayList<Photo> photoArrayList) {
            this.photoArrayList = photoArrayList;
        }

        /**
         * Actual download method.
         */
        @Override
        protected ArrayList<Photo> doInBackground(ArrayList<Photo>... params) {
            for (Photo photo : photoArrayList) {
                if (!this.isCancelled()) {
                    photo.setBitmap(connect(photo.getThumbnailUrl()));
                }
            }
            return photoArrayList;
        }

        /**
         * Once the image is downloaded, associates it to the imageView
         */
        @Override
        protected void onPostExecute(ArrayList<Photo> photoArrayList) {
            if (!isCancelled()) {
                showData();
            }
        }
    }

    /**
     * Download the specified image from the Internet and binds it to the provided ImageView. The
     * binding is immediate if the image is found in the cache and will be done asynchronously
     * otherwise. A null bitmap will be associated to the ImageView if an error occurs.
     *
     * @param listOfPhotos The Arraylist of photos
     */
    public void download(ArrayList<Photo> listOfPhotos) {
        //Bitmap bitmap = getBitmapFromCache(photo.getThumbnailUrl());
        Log.d(TAG, "bitmap is null");
        bitmapDownloadTask = new BitmapDownloaderTask(listOfPhotos).execute();
    }
}