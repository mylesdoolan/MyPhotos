package mgd.myphotos.activities;

import android.widget.Button;
import android.widget.ProgressBar;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import mgd.myphotos.BuildConfig;
import mgd.myphotos.R;

/**
 * Created by Myles Doolan on 25/07/2017.
 *
 * Sample Roboelectric tests checking existing activity values to see if they exist.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, packageName = "mgd.myphotos")
public class ListOfAlbumsActivityTest extends TestCase {

    private ListOfAlbumsActivity activity;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        activity =  Robolectric.buildActivity(ListOfAlbumsActivity.class).create().start().visible().get();

    }

    @Test
    public void activityShouldNotBeNull(){
        assertNotNull(activity);
    }

    @Test
    public void validateTitle() {
        final String TITLE = "My Albums";
        String title = activity.getTitle().toString();
        assertEquals(title, TITLE);
    }

    @Test
    public void validateSpinner() {
        ProgressBar spinner = (ProgressBar) activity.findViewById(R.id.progress_bar);
        assertNotNull(spinner);
    }

}
