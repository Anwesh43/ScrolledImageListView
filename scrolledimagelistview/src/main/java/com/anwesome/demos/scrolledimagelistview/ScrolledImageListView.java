package com.anwesome.demos.scrolledimagelistview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by anweshmishra on 24/03/17.
 */
public class ScrolledImageListView extends ViewGroup{
    private ImageView mImageView;
    private Bitmap bitmap;
    private int w=0,h=0;
    private boolean measured = false,laidOut = false;
    public ScrolledImageListView(Context context) {
        super(context);
        mImageView = new ImageView(context);
    }
    public void setBitmap(Bitmap bitmap) {
        if(bitmap!=null) {
            this.bitmap = bitmap;
            if(measured) {
                addImageView();
                requestLayout();
            }

        }
    }
    private void addImageView() {
        bitmap = Bitmap.createScaledBitmap(bitmap,w,h/4,true);
        mImageView.setImageBitmap(bitmap);
        addView(mImageView,new LayoutParams(w,h/4));
    }
    public void onMeasure(int wspec,int hspec) {
        Display display = getDisplay();
        Point size = new Point();
        display.getRealSize(size);
        w = size.x;
        h = size.y;
        if(!measured)  {
            if(bitmap!=null) {
                addView(mImageView,new LayoutParams(w,h/4));
                addImageView();
            }
            measured = true;
        }
        for(int i = 0;i<getChildCount();i++) {
            View view = getChildAt(i);
            measureChild(view,wspec,hspec);
        }
        setMeasuredDimension(w,h);
    }
    public void onLayout(boolean changed,int a,int b,int w,int h){

    }
}
