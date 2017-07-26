package mgd.myphotos.entities;

/**
 * Created by Myles Doolan on 24/07/2017.
 * <p>
 * Assumption - All values are mandatory for an album
 */

public class Album {

    private int userId;
    private int albumId;
    private String name;

    public Album(int userId, int albumID, String name) {
        this.userId = userId;
        this.albumId = albumID;
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public String getName() {
        return name;
    }
}

