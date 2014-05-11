package com.tspoon.stylight.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import com.tspoon.stylight.R;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_root, FragmentProducts.newInstance())
                .commit();

        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setTitle("STYLIGHT");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
