package com.anwesome.demos.scrolledimagelistview;

import android.content.Context;
import android.graphics.*;
import android.view.View;

/**
 * Created by anweshmishra on 24/03/17.
 */
public class OverlayView extends View{
    public OverlayView(Context context) {
        super(context);
    }
    public void onDraw(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#66000000"));
        canvas.drawRect(new RectF(0,0,canvas.getWidth(),canvas.getHeight()),paint);
    }
}
