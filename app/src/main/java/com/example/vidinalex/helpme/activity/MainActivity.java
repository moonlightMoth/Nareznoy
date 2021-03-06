package com.example.vidinalex.helpme.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.vidinalex.helpme.R;
import com.example.vidinalex.helpme.datatypes.NewsDateFormat;
import com.example.vidinalex.helpme.managers.DatabaseManager;
import com.example.vidinalex.helpme.managers.FileManager;
import com.example.vidinalex.helpme.managers.PermissionManager;
import com.example.vidinalex.helpme.toolbar.LeftSideToolbarInitializator;
import com.example.vidinalex.helpme.uifragments.NewsUnitRecyclerAdapter;
import com.example.vidinalex.helpme.utils.GlobalVars;
import com.google.firebase.database.FirebaseDatabase;
import com.mikepenz.materialdrawer.Drawer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private Drawer.Result drawerResult = null;
    private String ACTION_ARRAY_LIST_READY = "asReady";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {
        try{
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            FirebaseDatabase.getInstance().getReference().keepSynced(true);
        }catch (Exception e)
        {
            Log.d("MainActivity", "FirebaseDatabase.getInstance().setPersistenceEnabled(true);");
        }

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateNews();
                BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                };
                registerReceiver(broadcastReceiver, new IntentFilter(NewsUnitRecyclerAdapter.ACTION_NEWS_REFRESHED));
            }
        });

        PermissionManager.checkPermissionsAndRequest(this, PermissionManager.DEFAULT_PERMISSION_PACK);

        GlobalVars.setFileSavingPath(getApplicationContext().getFilesDir().toString() + File.separator);

        GlobalVars.setContext(getApplicationContext());

        updateNews();

        drawerResult = LeftSideToolbarInitializator.initNewToolbar(this);
    }

    @Override
    public void onBackPressed() {
        // Закрываем Navigation Drawer по нажатию системной кнопки "Назад" если он открыт
        if (drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    private void updateNews()
    {
        final ArrayList<NewsDateFormat> as = FileManager.getCachedNewsDatesList();
        final ArrayList<NewsDateFormat> as2 = DatabaseManager.getNewsDatesListFromCloud();

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(as.size() != 0)
                {
                    Log.d("ArrayList", "ArrayList not empty");
                    int date = Integer.parseInt(as.get(as.size()-1).getDate());
                    int date2;

                    for (int i = as2.size()-1; i >= 0; i++) {
                        date2 = Integer.parseInt(as2.get(i).getDate());
                        if(date2>date)
                        {
                            as.add(as.size(),as2.get(i));
                        }
                        else
                            break;
                    }
                }
                else
                {
                    Log.d("ArrayList", "ArrayList empty");
                    as.addAll(as2);
                }

                Log.d("ArrayList", "AS size is " + String.valueOf(as.size()));

                for (int i = 0; i < as.size(); i++) {
                    Log.d("ArrayList", as.get(i).getDate() + " " + as.get(i).getLoadFrom());
                }

                Collections.sort(as);


                unregisterReceiver(this);
                initRecyclerView(as);
            }
        }, new IntentFilter(ACTION_ARRAY_LIST_READY));
    }


    private void initRecyclerView(final ArrayList<NewsDateFormat> arrayList)
    {
        for (int i = 0; i < arrayList.size(); i++) {
            Log.d("initRecyclerView AS", arrayList.get(i).getDate() + " " + arrayList.get(i).getLoadFrom());
        }

        final RecyclerView feedLayout = findViewById(R.id.list);

        feedLayout.setBackgroundResource(R.drawable.phone_login_icon);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);

        feedLayout.setLayoutManager(layoutManager);

        feedLayout.setAdapter(new NewsUnitRecyclerAdapter(arrayList));

    }




}