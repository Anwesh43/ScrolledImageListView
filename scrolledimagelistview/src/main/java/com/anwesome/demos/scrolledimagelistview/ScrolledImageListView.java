package com.anwesome.demos.scrolledimagelistview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by anweshmishra on 24/03/17.
 */
public class ScrolledImageListView extends ViewGroup{
    private ImageView mImageView;
    private Bitmap bitmap;
    private ListView listView;
    private boolean imageAdded = false;
    private OverlayView overlayView;
    private int w=0,h=0,initH = 0;
    private boolean measured = false,laidOut = false;
    private LinearLayout linearLayout;
    private GestureDetector gestureDetector;
    private ScrollGestureListener scrollGestureListener = new ScrollGestureListener();
    public ScrolledImageListView(Context context) {
        super(context);
        gestureDetector = new GestureDetector(context,scrollGestureListener);
        mImageView = new ImageView(context);
        overlayView = new OverlayView(context);
        listView = new ListView(context);
        linearLayout = new LinearLayout(context);
    }
    private void setBitmap(Bitmap bitmap) {
        if(this.bitmap == null) {
            this.bitmap = bitmap;
            if(measured && !imageAdded) {
                addImageView();
                requestLayout();
                imageAdded = true;
            }

        }
    }
    public static  void show(Activity activity,Bitmap bitmap) {
        ScrolledImageListView scrolledImageListView = new ScrolledImageListView(activity);
        scrolledImageListView.setBitmap(bitmap);
        activity.addContentView(scrolledImageListView,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
    }
    private void addImageView() {
        bitmap = Bitmap.createScaledBitmap(bitmap,w,h/3,true);
        mImageView.setImageBitmap(bitmap);
        addView(mImageView,new LayoutParams(w,h/3));
    }
    public void onMeasure(int wspec,int hspec) {
        Display display = getDisplay();
        Point size = new Point();
        display.getRealSize(size);
        w = size.x;
        h = size.y;

        if(!measured)  {
            addView(overlayView,new LayoutParams(w,h));
            if(bitmap!=null && !imageAdded) {
                addImageView();
                imageAdded = true;
            }
            addView(linearLayout,new LayoutParams(w,2*h/3));
            linearLayout.setBackgroundColor(Color.WHITE);
            linearLayout.addView(listView,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
            measured = true;
        }
        for(int i = 0;i<getChildCount();i++) {
            View view = getChildAt(i);
            measureChild(view,wspec,hspec);
        }
        setMeasuredDimension(w,h);
    }
    public void onLayout(boolean changed,int a,int b,int w,int h){
        int y = 0;
        for(int i=0;i<getChildCount();i++) {
            View view = getChildAt(i);
            if(view instanceof OverlayView) {
                view.layout(0,0,w,h);
                continue;
            }
            view.layout(0,y,w,y+view.getMeasuredHeight());
            y+=view.getMeasuredHeight();
        }
    }
    public void translateViews(float y) {
        float yGap = y-mImageView.getY();
        float yFinal = h-h/6;
        float scale = (y)/(yFinal);
        float sizeScale = 1-0.5f*scale;
        mImageView.setScaleX(sizeScale);
        mImageView.setScaleY(sizeScale);
        float offsetX = (1-sizeScale)*mImageView.getMeasuredWidth()/2,offsetY = (1-sizeScale)*mImageView.getMeasuredHeight()/2;
        mImageView.setX(w-(sizeScale*mImageView.getMeasuredWidth()+offsetX));
        mImageView.setY(linearLayout.getY()+yGap-(sizeScale*mImageView.getMeasuredHeight()+offsetY));
        for(int i=0;i<getChildCount();i++) {
            View view = getChildAt(i);
            if(!(view instanceof ImageView || view instanceof OverlayView)) {
                view.setY(view.getY()+yGap);
            }
        }
    }
    private class ScrollGestureListener extends GestureDetector.SimpleOnGestureListener {
        private float xGap,yGap;
        private boolean downAllowed = true;
        public boolean onDown(MotionEvent event) {
            float x = event.getX(),y = event.getY();
            if(x >mImageView.getX() && x<mImageView.getX()+mImageView.getMeasuredWidth() && y>mImageView.getY() && y<mImageView.getY()+mImageView.getMeasuredHeight()) {
                xGap = x-mImageView.getX();
                yGap = y-mImageView.getY();
            }
            return true;
        }
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
        public boolean onScroll(MotionEvent e1,MotionEvent e2,float velx,float vely) {
            if (Math.abs(vely) > Math.abs(velx)) {
                if(e2!=null) {
                    if(mImageView.getY()>=7*h/8 && downAllowed && e2.getY()>e1.getY()) {
                        return true;
                    }
                    if(mImageView.getY()<0 && !downAllowed && e2.getY()<e1.getY()) {
                        return false;
                    }
                    translateViews(e2.getY()-yGap);
                }
            }
            return true;
        }
    }
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}
