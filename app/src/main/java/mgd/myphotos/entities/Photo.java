package mgd.myphotos.entities;

import android.graphics.Bitmap;

/**
 * Created by Myles Doolan on 25/07/2017.
 * Photo object, storing all the information about a photo
 */
public class Photo {

    private String title;
    private String url;
    private String thumbnailUrl;
    private Bitmap bitmap;

    public Photo(String title, String url, String thumbnailUrl) {
        this.title = title;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
