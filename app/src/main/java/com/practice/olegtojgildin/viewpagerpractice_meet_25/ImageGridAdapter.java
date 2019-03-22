package com.practice.olegtojgildin.viewpagerpractice_meet_25;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by olegtojgildin on 21/03/2019.
 */

public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.ViewHolder> {

    private List<Uri> mImageList;
    private OnImageListener onImageListener;

    public ImageGridAdapter(List<Uri> listImage, OnImageListener onImageListener) {
        mImageList = listImage;
        this.onImageListener=onImageListener;
    }

    public void setImageList(List<Uri> listImage){
        mImageList=listImage;
    }

    @Override
    public ImageGridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view,onImageListener);
    }

    @Override
    public void onBindViewHolder(ImageGridAdapter.ViewHolder holder, int position) {
        Picasso.get()
                .load(mImageList.get(position))
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mImageView;
        OnImageListener onImageListener;

        public ViewHolder(View itemView, OnImageListener onImageListener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            this.onImageListener=onImageListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onImageListener.OnImageClick(getAdapterPosition());

        }
    }
    public interface  OnImageListener{
        void OnImageClick(int position);
    }
}