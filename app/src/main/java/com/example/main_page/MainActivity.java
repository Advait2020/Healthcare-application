package com.example.main_page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout =findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.navigationView);
        toolbar=findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);//1

        actionBarDrawerToggle= new ActionBarDrawerToggle(this,drawerLayout,R.string.menu_open,R.string.menu_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle.syncState();//2


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                if (item.getItemId() == R.id.register) {
                    Intent intentRegister= new Intent(MainActivity.this,register.class);
                    startActivity(intentRegister);
                }
                if (item.getItemId() == R.id.login) {
                    Intent intentLogin= new Intent(MainActivity.this,login.class);
                    startActivity(intentLogin);
                }

                else {

                    Toast.makeText(MainActivity.this,"Entered", Toast.LENGTH_LONG).show();

                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        } else {
                    super. onBackPressed();
        }
    }


}