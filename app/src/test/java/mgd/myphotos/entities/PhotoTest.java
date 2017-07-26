package mgd.myphotos.entities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Myles Doolan on 25/07/2017.
 */

public class PhotoTest {


    @Test
    public void createAPhotoObjectTest() {
        final String TITLE = "Test title";
        final String URL = "http://testurl.com";
        final String THUMBNAIL_URL = "http://testThumbnailUrl.com";
        Photo photo = new Photo(TITLE, URL, THUMBNAIL_URL);

        assertEquals(TITLE, photo.getTitle());
        assertEquals(URL, photo.getUrl());
        assertEquals(THUMBNAIL_URL, photo.getThumbnailUrl());
    }

    @Test
    public void addABitmapTest() {
        final String TITLE = "Test title";
        final String URL = "http://testurl.com";
        final String THUMBNAIL_URL = "http://testThumbnailUrl.com";
        Photo photo = new Photo(TITLE, URL, THUMBNAIL_URL);

        assertEquals(TITLE, photo.getTitle());
        assertEquals(URL, photo.getUrl());
        assertEquals(THUMBNAIL_URL, photo.getThumbnailUrl());
    }

}
