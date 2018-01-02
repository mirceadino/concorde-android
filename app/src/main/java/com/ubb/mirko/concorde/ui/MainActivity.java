package com.ubb.mirko.concorde.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ubb.mirko.concorde.R;
import com.ubb.mirko.concorde.controller.UserController;
import com.ubb.mirko.concorde.model.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText editText_username;
    private EditText editText_password;
    private Button button_login;

    void showLoggedInUser(User user) {
        ((LinearLayout) editText_username.getParent()).removeView(editText_username);
        ((LinearLayout) editText_password.getParent()).removeView(editText_password);
        ((LinearLayout) button_login.getParent()).removeView(button_login);
        TextView loginForm_info = (TextView) findViewById(R.id.loginForm_info);
        loginForm_info.setText(
                "Welcome, " + user.getUsername() + "!" + "\n" +
                        (user.is_ibis() ? "You're an ibis." : "You're a sparrow."));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This doesn't do anything yet", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Get add texts and buttons from layout.
        editText_username = (EditText) findViewById(R.id.loginForm_username);
        editText_password = (EditText) findViewById(R.id.loginForm_password);
        button_login = (Button) findViewById(R.id.loginForm_button);

        UserController userController = UserController.getInstance();
        User user = userController.getCurrentUser();
        if (user != null) {
            showLoggedInUser(user);

        } else {
            // Fill the layout with the information from intent.
            button_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String username = String.valueOf(editText_username.getText());
                    String password = String.valueOf(editText_password.getText());

                    UserController userController = UserController.getInstance();
                    User user = userController.authenticate(username, password);

                    if (user == null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setCancelable(true);
                        builder.setMessage("Username doesn't exist or password is invalid.")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    } else {
                        showLoggedInUser(user);
                    }
                }
            });
        }
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

        if (id == R.id.flights) {
            Intent intent = new Intent(this, FlightsActivity.class);
            startActivity(intent);
        }

        if (id == R.id.manage_flights) {
            Intent intent = new Intent(this, ManageFlightsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
