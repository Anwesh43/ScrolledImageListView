package com.anwesome.demos.scrolledimagelistview;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by anweshmishra on 24/03/17.
 */
public class ScrolledImageListView extends ViewGroup{
    private ImageView mImageView;
    public ScrolledImageListView(Context context) {
        super(context);
        mImageView = new ImageView(context);
    }
    public void setBitmap(Bitmap bitmap) {
        mImageView.setImageBitmap(bitmap);
    }
    public void onMeasure(int wspec,int hspec) {

    }
    public void onLayout(boolean changed,int a,int b,int w,int h){

    }
}
