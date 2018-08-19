package com.example.vidinalex.helpme.toolbar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.vidinalex.helpme.R;
import com.mikepenz.materialdrawer.Drawer;

public class LeftSideToolbarInitializator {

    public static Drawer.Result initNewToolbar(AppCompatActivity activity){
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Drawer.Result drawerResult= null;

        drawerResult = new LeftSideToolbarDrawer(activity, toolbar, drawerResult).build();
        return drawerResult;
    }
}
