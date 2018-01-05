package com.ubb.mirko.concorde.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ubb.mirko.concorde.R;
import com.ubb.mirko.concorde.controller.UserController;
import com.ubb.mirko.concorde.model.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText editText_username;
    private EditText editText_password;
    private Button button_login;
    private UserController userController = UserController.getInstance();

    void showLoggedInUser(User user) {
        ((LinearLayout) editText_username.getParent()).removeView(editText_username);
        ((LinearLayout) editText_password.getParent()).removeView(editText_password);
        ((LinearLayout) button_login.getParent()).removeView(button_login);
        TextView loginForm_info = findViewById(R.id.loginForm_info);
        loginForm_info.setText(
                "Welcome, " + user.getUsername() + "!" + "\n" +
                        (user.isIbis() ? "You're an ibis." : "You're a sparrow."));
    }

    void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Get add texts and buttons from layout.
        editText_username = findViewById(R.id.loginForm_username);
        editText_password = findViewById(R.id.loginForm_password);
        button_login = findViewById(R.id.loginForm_button);

        User user = userController.getCurrentUser();
        if (user != null) {
            showLoggedInUser(user);

        } else {
            button_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String username = String.valueOf(editText_username.getText());
                    String password = String.valueOf(editText_password.getText());

                    UserController userController = UserController.getInstance();
                    User user = userController.authenticate(username, password);

                    if (user == null) {
                        showToast("Username doesn't exist or password doesn't match.");

                    } else {
                        showLoggedInUser(user);
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

        if (id == R.id.flights) {
            User user = userController.getCurrentUser();
            if (user != null) {
                Intent intent = new Intent(this, FlightsActivity.class);
                startActivity(intent);

            } else {
                showToast("You must log in.");
            }
        }

        if (id == R.id.manage_flights) {
            User user = userController.getCurrentUser();
            if (user != null && user.isIbis()) {
                Intent intent = new Intent(this, ManageFlightsActivity.class);
                startActivity(intent);

            } else {
                showToast("You must log in and be an ibis.");
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
