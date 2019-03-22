package com.practice.olegtojgildin.viewpagerpractice_meet_25;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ImageGridAdapter.OnImageListener {

    public static final String URI = "Uri";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private GridLayoutManager mManager;
    private ArrayList<Uri> mImageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageList = new ArrayList<>();
        initViews();
    }

    public void initViews() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mManager);
        mImageList = loadImageFromStorage();
        mAdapter = new ImageGridAdapter( mImageList, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private ArrayList<Uri> loadImageFromStorage() {

        ArrayList<Uri> listImage = new ArrayList<>();
        ContentResolver contentResolver = getContentResolver();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{MediaStore.Images.Media._ID};
        Cursor cursor = MediaStore.Images.Media.query(contentResolver, uri, projection, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(projection[0]));
            Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
            listImage.add(imageUri);
            cursor.moveToNext();
        }
        cursor.close();
        return listImage;
    }

    @Override
    public void OnImageClick(int position) {
        Intent intent = new Intent(this, ImageActivity.class);
        intent.putParcelableArrayListExtra(URI, mImageList);
        intent.putExtra("Position", position);
        startActivity(intent);
    }
}
