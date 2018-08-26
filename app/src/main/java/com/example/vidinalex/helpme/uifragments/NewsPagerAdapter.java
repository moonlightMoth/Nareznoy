package com.example.vidinalex.helpme.uifragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.vidinalex.helpme.utils.GlobalVars;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class NewsPagerAdapter extends PagerAdapter {

    private ArrayList<String> imagesArrayList;
    private Context context;

    public NewsPagerAdapter(ArrayList<String> imagesArrayList, Context context)
    {
        super();
        this.imagesArrayList = imagesArrayList;
        this.context = context;
        Log.d("NewsPagerAdapete", Integer.toString(imagesArrayList.size()));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {


        ImageView imageView = new ImageView(container.getContext());

        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        Log.d("Adapter", "instantiateItemmmmmmmmmmmmmmmmmmmmm");

        Glide.with(GlobalVars.getContext()).
                using(new FirebaseImageLoader()).
                load(FirebaseStorage.getInstance().getReference().child("newsImages/" + imagesArrayList.get(position))).
                into(imageView);


        container.addView(imageView);

        return imageView;
    }
    


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

    @Override
    public int getCount() {
        return this.imagesArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


}
