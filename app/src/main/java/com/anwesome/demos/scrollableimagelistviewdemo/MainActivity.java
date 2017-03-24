package com.anwesome.demos.scrollableimagelistviewdemo;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anwesome.demos.scrolledimagelistview.ScrolledImageListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScrolledImageListView.show(this, BitmapFactory.decodeResource(getResources(),R.drawable.gojira));
    }
}
