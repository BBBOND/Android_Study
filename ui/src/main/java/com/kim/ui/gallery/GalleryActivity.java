package com.kim.ui.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.kim.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KIM on 2015/8/14.
 */
public class GalleryActivity extends Activity implements AdapterView.OnItemClickListener {

    private Gallery gallery;
    private List<Integer> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);

        gallery = (Gallery) findViewById(R.id.gallery);
        list = new ArrayList<>();
        list.add(R.drawable.emo_1);
        list.add(R.drawable.emo_2);
        list.add(R.drawable.emo_3);
        list.add(R.drawable.emo_4);
        list.add(R.drawable.emo_5);
        list.add(R.drawable.emo_6);
        list.add(R.drawable.emo_7);
        list.add(R.drawable.emo_8);
        list.add(R.drawable.emo_9);
        list.add(R.drawable.emo_10);
        list.add(R.drawable.emo_11);
        list.add(R.drawable.emo_12);
        list.add(R.drawable.emo_13);
        list.add(R.drawable.emo_14);
        list.add(R.drawable.emo_15);
        list.add(R.drawable.emo_16);
        list.add(R.drawable.emo_17);

        ImageAdapter imageAdapter = new ImageAdapter(this);
        gallery.setAdapter(imageAdapter);
        gallery.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(GalleryActivity.this, "你点击了" + position % list.size(), Toast.LENGTH_SHORT).show();
    }

    public class ImageAdapter extends BaseAdapter {

        private Context context;
        private int myGalleryItemBackground;

        public ImageAdapter(Context context) {
            this.context = context;
            TypedArray typedArray = obtainStyledAttributes(R.styleable.Galley);
            myGalleryItemBackground = typedArray.getResourceId(R.styleable.Galley_android_galleryItemBackground, 0);
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(list.get(position % list.size()));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(new Gallery.LayoutParams(480, 480));
            imageView.setBackgroundResource(myGalleryItemBackground);
            return imageView;
        }
    }
}
