package mgd.myphotos.interactors;

import android.util.Log;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import mgd.myphotos.BuildConfig;
import mgd.myphotos.activities.ListOfAlbumsActivity;
import mgd.myphotos.entities.Album;


/**
 * Created by Myles Doolan on 25/07/2017.
 *
 * Sample Mockito tests for testing the interactor logic of the albums activity
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, packageName = "mgd.myphotos")
public class ListOfAlbumsInteractorTest extends TestCase {

    private final String TAG = "LOAInteractorTest";
    private ListOfAlbumsActivity activity;

    private ListOfAlbumsInteractorLogic interactorLogic;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        activity =  Robolectric.buildActivity(ListOfAlbumsActivity.class).create().start().visible().get();
        interactorLogic = new ListOfAlbumsInteractorLogic();
    }

    @Test
    public void testBuildArray() {
        try {
            JSONArray jsonArrayOfAlbums = new JSONArray("[ { \"userId\": 1, \"id\": 1, \"title\": \"quidem molestiae enim\" },{\"userId\": 1,\"id\": 2,\"title\": \"sunt qui excepturi placeat culpa\"},{\"userId\": 1,\"id\": 3,\"title\": \"omnis laborum odio\"}]");
            ArrayList<Album> albumArray = interactorLogic.saveListOfAlbums(jsonArrayOfAlbums);
            assertEquals(albumArray.size(), 3);

        } catch (JSONException e) {
            Log.d(TAG, e.getLocalizedMessage());
            assertTrue(false);
        }
    }

    @Test
    public void sortArray() {
        try {
            JSONArray jsonArrayOfAlbums = new JSONArray("[ { \"userId\": 1, \"id\": 1, \"title\": \"quidem molestiae enim\" },{\"userId\": 1,\"id\": 2,\"title\": \"sunt qui excepturi placeat culpa\"},{\"userId\": 1,\"id\": 3,\"title\": \"omnis laborum odio\"}]");
            ArrayList<Album> albumArray = interactorLogic.saveListOfAlbums(jsonArrayOfAlbums);

            albumArray =  interactorLogic.sortArray(albumArray);

            assertEquals(albumArray.get(0).getAlbumId(), 3);

        } catch (JSONException e) {
            Log.d(TAG, e.getLocalizedMessage());
            assertTrue(false);
        }
    }
}
