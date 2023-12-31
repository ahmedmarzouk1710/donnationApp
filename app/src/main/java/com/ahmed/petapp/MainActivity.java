package com.ahmed.petapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ahmed.petapp.Activity.addPost;
import com.ahmed.petapp.Fragments.AdoptionFragment;
import com.ahmed.petapp.Fragments.CommunityFragment;
import com.ahmed.petapp.Fragments.DonationListFragment;
import com.ahmed.petapp.Fragments.DonnationsFragment;
import com.ahmed.petapp.Fragments.HomeFragment;
import com.ahmed.petapp.Fragments.MarketplaceFragment;
import com.ahmed.petapp.Fragments.MedicalFragment;
import com.ahmed.petapp.Fragments.ProfileFragment;
import com.ahmed.petapp.Fragments.ShortsFragment;
import com.example.petapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    FloatingActionButton fab;
    DrawerLayout drawerlayout;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        fab= findViewById(R.id.fab);
        drawerlayout=findViewById(R.id.drawer_layout);
        NavigationView navigationview =findViewById(R.id.nav_view);
        navigationview.setNavigationItemSelectedListener(this);
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerlayout,toolbar,R.string.open_nav,R.string.close_nav);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new HomeFragment()).commit();
            navigationview.setCheckedItem(R.id.nav_home);
        }

        replaceFragment(new HomeFragment());

        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {


            if(item.getItemId() == R.id.home){
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.shorts) {
                replaceFragment(new CommunityFragment());
            }else if (item.getItemId() == R.id.adoption) {
                replaceFragment(new AdoptionFragment());
            }else if (item.getItemId() == R.id.marketplace) {
                replaceFragment(new MarketplaceFragment());
            }

            return true;
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog();
            }
        });
    }
    private  void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void showBottomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        LinearLayout videoLayout = dialog.findViewById(R.id.layoutVideo);
        LinearLayout shortsLayout = dialog.findViewById(R.id.layoutShorts);
        LinearLayout liveLayout = dialog.findViewById(R.id.layoutLive);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        videoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Toast.makeText(MainActivity.this,"Upload a Video is clicked",Toast.LENGTH_SHORT).show();

            }
        });

        shortsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Toast.makeText(MainActivity.this,"Create a short is Clicked",Toast.LENGTH_SHORT).show();

            }
        });

        liveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Toast.makeText(MainActivity.this,"Go live is Clicked",Toast.LENGTH_SHORT).show();

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    @Override
    public  void onBackPressed(){
        if(drawerlayout.isDrawerOpen(GravityCompat.START)){
            drawerlayout.close();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int itemId=item.getItemId();
        if(itemId == R.id.nav_home){
            replaceFragment(new HomeFragment());
        } else if (itemId== R.id.nav_adoption) {
            replaceFragment(new AdoptionFragment());
        }else if (itemId== R.id.nav_market) {
            replaceFragment(new MarketplaceFragment());
        }else if (itemId== R.id.nav_profile) {
            replaceFragment(new ProfileFragment());
        }else if (itemId== R.id.nav_donnation) {
            replaceFragment(new DonnationsFragment());
        }else if (itemId== R.id.nav_medical) {
            replaceFragment(new MedicalFragment());
        }else if (itemId== R.id.nav_forum) {
            replaceFragment(new CommunityFragment());
        }
        else if (itemId == R.id.nav_donation_list) {
            replaceFragment(new DonationListFragment()); // Add this block for Donation List
        }
        drawerlayout.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onAddPostClick(View view) {
        // Open the activity_addd_post.xml layout when the "Add post" item is clicked
        startActivity(new Intent(this, addPost.class));
    }


}