package com.wong.joanne.deliveryapp.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.wong.joanne.deliveryapp.Driver.DeliveryListFragment;
import com.wong.joanne.deliveryapp.Driver.DriverMainActivity;
import com.wong.joanne.deliveryapp.LoginActivity;
import com.wong.joanne.deliveryapp.R;
import com.wong.joanne.deliveryapp.Utility.LoginUser;

/**
 * Created by Sam on 10/20/2017.
 */

public class CustomerMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private FirebaseAuth auth;
    LoginUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        currentUser = (LoginUser) intent.getSerializableExtra("user");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.customer_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.customer_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        onNavigationItemSelected(menu.getItem(0));
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.customer_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.create_delivery_item) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", currentUser);
            fragment = new OrderDeliveryFragment();
            fragment.setArguments(bundle);
        }
        else if (id == R.id.order_histories) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", currentUser);
            fragment = new DeliveryHistories();
            fragment.setArguments(bundle);
        }
        else if(id == R.id.pending_order){
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", currentUser);
            fragment = new PendingDeliveryFragment();
            fragment.setArguments(bundle);
        }
        else if (id == R.id.nav_logout) {
            logout();
        }else {
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", currentUser);
            fragment = new OrderDeliveryFragment();
            fragment.setArguments(bundle);
        }

        if(fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.customer_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout(){

        auth = FirebaseAuth.getInstance();
        auth.signOut();
        startActivity(new Intent(CustomerMainActivity.this, LoginActivity.class));
        finish();
    }
}
