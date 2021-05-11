package com.example.opc;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/*This activity is for admin page which is navigation drawer activity
 * drawer contains nav header which displays username and email id
 * drawer have 6 navigation items 1.view Notifications 2.view prev year papers 3.upcoming companies
 * 4.discussion 5.account details 6.logout
 * on clicking this items respective fragments will open.
 * */


public class student_main extends AppCompatActivity {
    //variable for firebase uder
    private FirebaseUser user;
    private String em;

    //textview elements in ui
    TextView tv,username_navheader;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting layout file as activity_student_main in layout dorectory
        setContentView(R.layout.activity_student_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //finding drawrer and nav view
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        //finding header
        View header = navigationView.getHeaderView(0);

        //finding elelments insode header
        tv = header.findViewById(R.id.navheaderemail);
        username_navheader = header.findViewById(R.id.nav_header_username);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        /*ssssssssssssssssssssssssssssssssssssssssssssssssssss*/

        //finding current user
        user= FirebaseAuth.getInstance().getCurrentUser();
        //if current user exist getting his email and id
        if(user!=null){
            em=user.getEmail().toString();
            int index = em.indexOf('@');
            String node = em.substring(0,index);

            tv.setText(em);
            username_navheader.setText(node);

            Log.d("idfromemail",em);
        }


    }



        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.student_main, menu);
            return true;
        }

        @Override
        public boolean onSupportNavigateUp () {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                    || super.onSupportNavigateUp();
        }

        /*
        public boolean onOptionsItemSelected (MenuItem item){
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

        public boolean onNavigationItemSelected (MenuItem item){
            int id = item.getItemId();
            Log.d("idname", "" + id);
            if (id == R.id.nav_home) {
                Toast.makeText(this, "account", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(student_main.this, AccountActivity.class);
                startActivity(intent);
            }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }*/
    }



