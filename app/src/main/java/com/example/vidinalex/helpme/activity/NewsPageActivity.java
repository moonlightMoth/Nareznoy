package com.example.vidinalex.helpme.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.vidinalex.helpme.R;
import com.example.vidinalex.helpme.toolbar.LeftSideToolbarInitializator;
import com.example.vidinalex.helpme.uifragments.NewsPagerAdapter;
import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;

public class NewsPageActivity extends AppCompatActivity {

    private Drawer.Result drawerResult;
    private TextView date;
    private TextView head;
    private TextView internalBody;
    private ArrayList<String> imagesArrayList;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_page);

        init();
    }

    private void init() {
        drawerResult = LeftSideToolbarInitializator.initNewToolbar(this);

        date = findViewById(R.id.date);
        head = findViewById(R.id.head);
        internalBody = findViewById(R.id.internalBody);
        viewPager = findViewById(R.id.viewPager);

        Bundle bundle = getIntent().getExtras();
        date.setText(bundle.getString("date"));
        head.setText(bundle.getString("head"));
        internalBody.setText(bundle.getString("internalBody"));
        imagesArrayList = bundle.getStringArrayList("imagesArrayList");

        viewPager.setAdapter(new NewsPagerAdapter(imagesArrayList, this));
//        viewPager.setPageTransformer(false, new NewsInternalImagesPageTransformer());
    }


    public void onBackPressed() {
        // Закрываем Navigation Drawer по нажатию системной кнопки "Назад" если он открыт
        if (drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
