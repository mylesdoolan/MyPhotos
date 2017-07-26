package mgd.myphotos.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import mgd.myphotos.R;
import mgd.myphotos.interactors.ListOfAlbumsInteractorLogic;
import mgd.myphotos.network.Connection;
import mgd.myphotos.presenters.ListOfAlbumsPresenter;
import mgd.myphotos.presenters.ListOfAlbumsPresenterLogic;
import mgd.myphotos.views.ListOfAlbumsView;

import static android.view.View.GONE;

public class ListOfAlbumsActivity extends AppCompatActivity implements ListOfAlbumsView {

    private final String TAG = "MainActivity";

    private ListOfAlbumsPresenter presenter;
    private ProgressBar spinner;
    private LinearLayout albumList;
    private RetainedFragment retainedFragment;
    final String ALBUM_ID = "albumId";
    AsyncTask networkAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);


        presenter = new ListOfAlbumsPresenterLogic(this, new ListOfAlbumsInteractorLogic());
        spinner = (ProgressBar) findViewById(R.id.progress_bar);
        albumList = (LinearLayout) findViewById(R.id.albums_layout);

        setupListOfAlbums();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (networkAsyncTask != null) {
            networkAsyncTask.cancel(true);
        }
    }

    private void setupListOfAlbums() {
        final String retainedFragmentTag = "RetainedFragmentTag";
        final String url = "https://jsonplaceholder.typicode.com/albums";

        // Find the RetainedFragment on Activity restarts
        FragmentManager fragmentManager = getSupportFragmentManager();
        // The RetainedFragment has no UI so we must reference it with a tag.
        retainedFragment = (RetainedFragment) fragmentManager.findFragmentByTag(retainedFragmentTag);

        // if Retained Fragment doesn't exist create and add it.
        if (retainedFragment == null) {
            retainedFragment = new RetainedFragment();
            // Add the fragment
            fragmentManager.beginTransaction().add(retainedFragment, retainedFragmentTag).commitAllowingStateLoss();
            networkAsyncTask = new LoadContentFromNetworkAsyncTask(url).execute();
            Log.d(TAG, "adding");
        } else {
            Log.d(TAG, "exists");
            // The Retained Fragment exists
            presenter.getListOfAlbums(retainedFragment.getData());
            presenter.showAlbums();
        }
    }

    @Override
    public void hideSpinner() {
        spinner.setVisibility(GONE);
    }

    @Override
    public void addAlbum(String name, final int albumId){
        View albumView = getLayoutInflater().inflate(R.layout.album_layout, null);
        TextView albumName = (TextView) albumView.findViewById(R.id.album_name);
        albumName.setText(name);
        albumView.setTag(albumId);
        albumList.addView(albumView);
        albumView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListOfAlbumsActivity.this, ListOfPhotosActivity.class);
                intent.putExtra(ALBUM_ID, (int) view.getTag());
                startActivity(intent);
            }
        });
    }

    protected void showData(String response) {
        try {
            retainedFragment.setData(new JSONArray(response));
        } catch (JSONException e) {
            Log.d(TAG, e.getLocalizedMessage());
        }
        presenter.getListOfAlbums(retainedFragment.getData());
        presenter.showAlbums();
    }

    public static class RetainedFragment extends Fragment {

        private JSONArray albums;

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
    }

    class LoadContentFromNetworkAsyncTask extends Connection {

        private final String TAG = "NetworkAsyncTask";
        String url;

        public LoadContentFromNetworkAsyncTask(String url) {
            this.url = url;
        }

        @Override
        protected String doInBackground(String... urls) {
            return connect(url);
        }

        @Override
        protected void onPostExecute(String response) {
            showData(response);
        }
    }
}
