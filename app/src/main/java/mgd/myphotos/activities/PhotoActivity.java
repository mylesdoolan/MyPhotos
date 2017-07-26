package mgd.myphotos.activities;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import mgd.myphotos.R;
import mgd.myphotos.network.PhotoDownloader;

import static android.view.View.GONE;

/**
 * Created by Myles Doolan on 25/07/2017.
 */
public class PhotoActivity extends AppCompatActivity {

    private final String TAG = "PhotoActivity";
    private final String PHOTO_URL = "photoUrl";
    private final String PHOTO_TITLE = "photoTitle";
    private RetainedPhotosFragment retainedFragment;
    private AsyncTask networkAsyncTask;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        spinner = (ProgressBar) findViewById(R.id.progress_bar);
        Log.d(TAG, "" + getIntent().getStringExtra(PHOTO_URL));

        final String retainedFragmentTag = "RetainedFragmentTag";
        // Find the RetainedFragment on Activity restarts
        FragmentManager fragmentManager = getSupportFragmentManager();
        // The RetainedFragment has no UI so we must reference it with a tag.
        retainedFragment = (RetainedPhotosFragment) fragmentManager.findFragmentByTag(retainedFragmentTag);

        // if Retained Fragment doesn't exist create and add it.
        if (retainedFragment == null) {
            retainedFragment = new RetainedPhotosFragment();
            retainedFragment.setTitle(getIntent().getStringExtra(PHOTO_TITLE));
            retainedFragment.setUrl(getIntent().getStringExtra(PHOTO_URL));
            // Add the fragment
            fragmentManager.beginTransaction().add(retainedFragment, retainedFragmentTag).commitAllowingStateLoss();
            networkAsyncTask = new BitmapDownloaderTask(retainedFragment.getUrl()).execute();
            Log.d(TAG, "adding");
        } else {
            Log.d(TAG, "exists");
            showData();
        }
    }

    private void showData() {
        ImageView photo = (ImageView) findViewById(R.id.photo);
        TextView title = (TextView) findViewById(R.id.title);
        spinner.setVisibility(GONE);
        photo.setImageBitmap(retainedFragment.getPhoto());
        title.setText(getIntent().getStringExtra(PHOTO_TITLE));
    }

    public static class RetainedPhotosFragment extends Fragment {

        private Bitmap photo;
        private String title;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // The key to making data survive runtime configuration changes.
            setRetainInstance(true);
        }

        public Bitmap getPhoto() {
            return this.photo;
        }

        public void setPhoto(Bitmap photo) {
            this.photo = photo;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return this.title;
        }

        public void setUrl(String title) {
            this.title = title;
        }


    }

    /**
     * The actual AsyncTask that will asynchronously download the image.
     */
    private class BitmapDownloaderTask extends PhotoDownloader {

        private String url;

        BitmapDownloaderTask(String url ) {
            this.url = url;
        }

        /**
         * Actual download method.
         */
        @Override
        protected Bitmap doInBackground(Void... params) {
            return (connect(url));
        }

        /**
         * Once the image is downloaded, associates it to the imageView
         */
        @Override
        protected void onPostExecute(Bitmap photo) {
            if (!isCancelled()) {
                retainedFragment.setPhoto(photo);
                showData();
            }
        }
    }
}
