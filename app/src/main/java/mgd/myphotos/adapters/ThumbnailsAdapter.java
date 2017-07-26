package mgd.myphotos.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mgd.myphotos.R;
import mgd.myphotos.activities.ListOfPhotosActivity;
import mgd.myphotos.activities.PhotoActivity;
import mgd.myphotos.entities.Photo;

/**
 * Created by Myles Doolan on 25/07/2017.
 */
public class ThumbnailsAdapter extends BaseAdapter {

    private final String TAG = "ThumbnailsAdapter";
    private final Context mContext;
    private final ArrayList<Photo> listOfPhotos;

    public ThumbnailsAdapter(Context context, ArrayList<Photo>  listOfPhotos) {
        this.mContext = context;
        this.listOfPhotos = listOfPhotos;
    }

    @Override
    public int getCount() {
        return listOfPhotos.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "array size " + listOfPhotos.size());
        Log.d(TAG, "position " + position);
        final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(R.layout.thumbnail_layout, null);
        convertView.setTag(listOfPhotos.get(position));
        ImageView photo = (ImageView) convertView.findViewById(R.id.thumbnail);
        photo.setImageBitmap(listOfPhotos.get(position).getBitmap());
        Log.d(TAG, listOfPhotos.get(position).getThumbnailUrl());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String PHOTO_URL = "photoUrl";
                final String PHOTO_TITLE = "photoTitle";
                Intent intent = new Intent(mContext, PhotoActivity.class);
                intent.putExtra(PHOTO_URL, ((Photo) view.getTag()).getUrl());
                intent.putExtra(PHOTO_TITLE, ((Photo) view.getTag()).getTitle());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }
}
