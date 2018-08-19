package com.example.vidinalex.helpme.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.vidinalex.helpme.R;
import com.example.vidinalex.helpme.toolbar.LeftSideToolbarInitializator;
import com.mikepenz.materialdrawer.Drawer;

public class NewsPageActivity extends AppCompatActivity {

    private Drawer.Result drawerResult;
    private TextView date;
    private TextView head;
    private TextView internalBody;

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

        Bundle bundle = getIntent().getExtras();
        date.setText(bundle.getString("date"));
        head.setText(bundle.getString("head"));
        internalBody.setText(bundle.getString("internalBody"));
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
