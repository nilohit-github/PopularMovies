package com.appguru.android.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.Callback {
    private Boolean mTabletMode = false;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (findViewById(R.id.fragment_detail_container) != null) {
            mTabletMode = true;
            if (savedInstanceState == null) {
                DetailActivityFragment detailActivityFragment = new DetailActivityFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_detail_container, detailActivityFragment).commit();
            }

        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

    @Override
    public void onItemSelected(PopularMovie popularMovie) {
        if(mTabletMode){

            Bundle bundle = new Bundle();
            bundle.putParcelable("myMovies", popularMovie);

            DetailActivityFragment fragment = new DetailActivityFragment();
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_detail_container, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        }
        else{
            Intent movieIntent = new Intent(this, DetailActivity.class);
             movieIntent.putExtra(Intent.EXTRA_TEXT, (Parcelable) popularMovie);
            startActivity(movieIntent);
        }
    }

  

}
