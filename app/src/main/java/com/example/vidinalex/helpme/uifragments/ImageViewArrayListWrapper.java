package com.example.vidinalex.helpme.uifragments;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.vidinalex.helpme.utils.GlobalVars;
import com.example.vidinalex.helpme.utils.OnSwipeTouchListener;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class ImageViewArrayListWrapper extends AppCompatImageView {

    private boolean isInited=false;
    private ArrayList<String> imagesArrayList;
    private int position = 0;
    private OnSwipeTouchListener listener;
    private static StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("newsImages/");
    private ImageViewArrayListWrapper this_ = this;
    private Context context;

    public ImageViewArrayListWrapper(Context context)
    {
        super(context);
    }

    public ImageViewArrayListWrapper(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
        this.context = context;
    }

    public ImageViewArrayListWrapper(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public void init_(ArrayList<String> as)
    {
        isInited=true;
        imagesArrayList = as;
        Glide.with(GlobalVars.getContext())
                .using(new FirebaseImageLoader())
                .load(storageRef.child(imagesArrayList.get(position)))
                .into(this_);
        setOnSwipeTouchListener();
    }

    public void setOnSwipeTouchListener()
    {
        listener = new OnSwipeTouchListener(context) {
            @Override
            public void onSwipeRight() {
                position++;
                Log.d("ImageViewArrayListWrap", String.valueOf(position));
                Glide.with(context)
                        .using(new FirebaseImageLoader())
                        .load(storageRef.child(imagesArrayList.get(position)))
                        .into(this_);
            }

            @Override
            public void onSwipeLeft() {
                position--;
                Log.d("ImageViewArrayListWrap", String.valueOf(position));
                Glide.with(context)
                        .using(new FirebaseImageLoader())
                        .load(storageRef.child(imagesArrayList.get(position)))
                        .into(this_);
            }

            @Override
            public void onSwipeTop() {

            }

            @Override
            public void onSwipeBottom() {

            }
        };
    }
}
