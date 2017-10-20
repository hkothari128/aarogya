package com.example.huzaifa.myapplication;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;

public class homepage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView name,email,calin,calout;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2;
    FragmentPagerAdapter adapterViewPager;
    public static DrawerLayout drawer;
    public  Toolbar toolbar;
    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return water.newInstance();
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return ActivityList.newInstance();
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Intent stepi=new Intent(getApplicationContext(),MyService.class);
        startService(stepi);

        Intent pop = new Intent(homepage.this, popup.class);
        startActivity(pop);

        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.log_foodbtn);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.log_activitybtn);
        ViewPager vpPager = (ViewPager) findViewById(R.id.pager);
        adapterViewPager=new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                materialDesignFAM.toggle(false);
                food fragment = new food();
                fragment.show(getFragmentManager(), "");
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                materialDesignFAM.toggle(false);
                activities fragment=new activities();
                fragment.show(getFragmentManager(),"");

            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setLogo(R.drawable.logo);

        toolbar.setTitle(R.string.app_name);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar));

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        name=(TextView)hView.findViewById(R.id.name);
        email=(TextView)hView.findViewById(R.id.email);
        calin=(TextView)findViewById(R.id.calin);
        calout=(TextView)findViewById(R.id.calout);
        FDBAccess.dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                calin.setText("Calories In: "+String.valueOf(FDBAccess.data.child("CaloriesIn").getValue(Float.class))+" cal");
                calout.setText("Calories Out: "+String.valueOf(FDBAccess.data.child("CaloriesOut").getValue(Float.class))+" cal");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        name.setText(FDBAccess.data.child("Name").getValue(String.class));
        email.setText(FDBAccess.user.getEmail().toString());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {
            Intent i = new Intent(this, ProfileActivity.class);
            startActivity(i);
        }
         else if (id == R.id.progress) {
            Intent i=new Intent(this,LineChartTime.class);
            startActivity(i);
        } else if (id == R.id.history) {
            Intent stepshistory=new Intent(this,stephistory.class);
            startActivity(stepshistory);
        } else if (id == R.id.settings) {
            Intent i=new Intent(this,settings.class);
            startActivity(i);
        } else if (id == R.id.logout) {
            FirebaseAuth mAuth=FirebaseAuth.getInstance();
            mAuth.signOut();
            Intent i=new Intent(getBaseContext(),MainActivity.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
