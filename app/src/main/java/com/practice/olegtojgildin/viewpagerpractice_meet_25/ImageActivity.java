package com.practice.olegtojgildin.viewpagerpractice_meet_25;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olegtojgildin on 21/03/2019.
 */

public class ImageActivity extends AppCompatActivity {
    private ViewPager mPager;
    private PagerAdapter mPageAdapter;
    private ArrayList<Uri> mListImage;
    private Integer position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mListImage = getIntent().getParcelableArrayListExtra("Uri");
        position=getIntent().getIntExtra("Position",1);
        initViewPager();
    }

    public void initViewPager() {
        mPager = (ViewPager) findViewById(R.id.view_pager);
        mPageAdapter = new ImagePagerAdapter(getApplicationContext(), mListImage);
        mPager.setAdapter(mPageAdapter);
        mPager.setCurrentItem(position);
        mPager.setPageMargin(60);
    }

    private class ImagePagerAdapter extends PagerAdapter {

        private Context mContext;
        private List<Uri> mImageItem;
        ImageView mImageView;

        public ImagePagerAdapter(Context mContext, List<Uri> mImageItem) {
            this.mContext = mContext;
            this.mImageItem = mImageItem;
        }

        @Override
        public int getCount() {
            return mImageItem.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        private ScaleGestureDetector mScaleGestureDetector;
        private float mScaleFactor = 1.0f;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            mImageView = new ImageView(mContext);
            mScaleGestureDetector = new ScaleGestureDetector(mContext, new ScaleListener());
            // Обрабатываем onTouch для картинки через ScaleGestureDetector, чтобы отловить жест зума
            mImageView.setOnTouchListener((v, event) -> mScaleGestureDetector.onTouchEvent(event));

            Picasso.get()
                    .load(mImageItem.get(position))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(mImageView);
            container.addView(mImageView);

            return mImageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                mScaleFactor *= mScaleGestureDetector.getScaleFactor();
                mScaleFactor = Math.max(1.0f, Math.min(mScaleFactor, 2.0f));
                mImageView.setScaleX(mScaleFactor);
                mImageView.setScaleY(mScaleFactor);
                return true;
            }
        }
    }

}
