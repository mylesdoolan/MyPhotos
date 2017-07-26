package mgd.myphotos.entities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Myles Doolan on 24/07/2017.
 *
 * Testing creating an album object
 */
public class AlbumTest {

    @Test
    public void createAnAlbumTest() {
        final int USER_ID = 1;
        final int ALBUM_ID = 2;
        final String ALBUM_NAME = "test album name";
        Album album = new Album(USER_ID, ALBUM_ID, ALBUM_NAME);

        assertEquals(USER_ID, album.getUserId());
        assertEquals(ALBUM_ID, album.getAlbumId());
        assertEquals(ALBUM_NAME, album.getName());
    }

}
