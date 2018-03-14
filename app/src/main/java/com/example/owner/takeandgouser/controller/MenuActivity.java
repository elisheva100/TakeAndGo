package com.example.owner.takeandgouser.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.owner.takeandgouser.R;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.mipmap.ic_exit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                exit_dialog();

            }
        });


        /*MyReceiver myReceiver = new MyReceiver(); //TODO check it!!
        IntentFilter filter = new IntentFilter("CHANGE_CAR_STATUS");
        registerReceiver(myReceiver,filter);
        startService(new Intent(this,MyIntentService.class));*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            aboutCompanyFragment();
        } else if (id == R.id.nav_gallery) {

            //availableCarsFragment();
            showBranchesFragment();
        } else if (id == R.id.nav_slideshow) {
           // showCarsFragment();
            availableCarsFragment();


        } else if (id == R.id.nav_manage) {
            myCarFragment();
        }

         else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



//    private NavigationView navView;
//    private Toolbar toolbar;
//    private FloatingActionButton fab;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-01-12 03:01:30 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
//    private void findViews() {
//        navView = (NavigationView) findViewById(R.id.nav_view);
//        toolbar = (Toolbar)findViewById( R.id.toolbar );
//        fab = (FloatingActionButton)findViewById( R.id.fab );
//
//        fab.setOnClickListener( this );
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        if ( v == fab ) {
//            // Handle clicks for fab
//        }
//    }
    private void aboutCompanyFragment() {
        	        AboutUsFragment fragment = new AboutUsFragment();
        	        FragmentManager manager = getSupportFragmentManager();
        	        manager.beginTransaction().replace(R.id.content_frame,fragment).addToBackStack("AboutUsFragment").commit();
            }

    private void availableCarsFragment() {
        AvailableCarsFragment fragment = new AvailableCarsFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_frame,fragment).commit();
    }

    private void showBranchesFragment() {
        BranchesFragment fragment = new BranchesFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_frame,fragment).addToBackStack("BranchesFragment").commit();
    }



    private void myCarFragment() {
        MyCarFragment fragment = new MyCarFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("MyCarFragment").commit();

}
    private void exitApp()
    {
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void exit_dialog()
    {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_menu_close_clear_cancel)
                .setTitle("Closing App")
                .setMessage("Are you sure you want to close this app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exitApp();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }



}
