package com.appguru.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private Boolean mTabletMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(findViewById(R.id.fragment_detail_container)!= null){
            mTabletMode = true;
            if (savedInstanceState == null) {
                DetailActivityFragment detailActivityFragment = new DetailActivityFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_detail_container, detailActivityFragment).commit();
            }

        }

            }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void replaceFragment() {
        Log.i("tab", "replace");
        Bundle args = new Bundle();
        args.putString("ARGUMENTS", "Created from MainActivity");
        DetailActivityFragment detailActivityFragment = new DetailActivityFragment();
        detailActivityFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_detail_container, detailActivityFragment).commit();
    }
}
