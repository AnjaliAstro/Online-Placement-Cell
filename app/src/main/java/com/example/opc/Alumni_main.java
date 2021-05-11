package com.example.opc;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.opc.ui.send.SendFragment;
import com.example.opc.ui.share.ShareFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

/*This activity is for alumni page which is navigation drawer activity
* drawer contains nav header which displays username and email id
* drawer have 5 navigation items 1.Home 2.discussion 3.experience 4.acc details
* 5.logOut
* on clicking this items respective fragments will open.
* */

public class Alumni_main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //declaring respective ui elements
    private DrawerLayout drawer;
    private TextView navEmailDisp,username_navheader;
    //variable for firebase authentication
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting layout as of activity alumni in layout directory
        setContentView(R.layout.activity_alumni_main);

        //finding ui componenet by thier assigned id's.
        Toolbar toolbar = findViewById(R.id.alumni_toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.alumni_drawer_layout);

        //getting firebase auth instance
        mAuth = FirebaseAuth.getInstance();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        //finding nav view
        NavigationView navigationView=findViewById(R.id.alumni_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toggle.syncState();
//<<<<<<< HEAD
        //setting Home fragment as default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.alumni_fragment_container,new fragment_stats()).commit();
        //setting add tpo item in nav view as default selected item
        navigationView.setCheckedItem(R.id.nav_alumni_home);

        //finding elements in header
        View header = navigationView.getHeaderView(0);
        navEmailDisp = header.findViewById(R.id.navheaderemailAlumni);
        username_navheader = header.findViewById(R.id.nav_header_usernameAlumni);


        //finding username and email of currentuser and displaying it in nav header

        FirebaseUser currentUser = mAuth.getCurrentUser();
        String Useremail = currentUser.getEmail();
        int index = Useremail.indexOf('@');
        String node = Useremail.substring(0,index);

        username_navheader.setText(node);


        Log.d("check",Useremail);
        navEmailDisp.setText(Useremail);


        username_navheader.setText(node);


        Log.d("check",Useremail);
        navEmailDisp.setText(Useremail);


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        //replacing current fragment with fragment on the basis of nav item clicked by user
        switch(menuItem.getItemId()){
            case R.id.nav_alumni_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.alumni_fragment_container,new fragment_stats()).commit();
                break;
            case R.id.nav_alumni_discussion:
                getSupportFragmentManager().beginTransaction().replace(R.id.alumni_fragment_container,new ShareFragment()).commit();
                break;

            case R.id.nav_alumni_experience:
                getSupportFragmentManager().beginTransaction().replace(R.id.alumni_fragment_container,new Alumni_Discussion_Fragment()).commit();
                break;

            case R.id.nav_alumni_account:
                getSupportFragmentManager().beginTransaction().replace(R.id.alumni_fragment_container,new Alumni_Account_Fragment()).commit();
                break;

            case R.id.nav_alumni_logout:
                getSupportFragmentManager().beginTransaction().replace(R.id.alumni_fragment_container,new SendFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /*action to be taken on back button of mobile is pressed
    if drawer is open close the drawer
    else go to the prev activity.
    * */
    public void onBackPressed(){

            if(drawer.isDrawerOpen(GravityCompat.START)){
                drawer.closeDrawer(GravityCompat.START);
            }
            else {
                super.onBackPressed();
            }
        }


}
