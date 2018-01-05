package com.ubb.mirko.concorde.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.ubb.mirko.concorde.R;
import com.ubb.mirko.concorde.controller.UserController;
import com.ubb.mirko.concorde.model.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText editText_username;
    private EditText editText_password;
    private TextView loginForm_info;
    private Button button_login;
    private Button button_loginWithGoogle;
    private Button button_logout;
    private UserController userController = UserController.getInstance();
    private GoogleSignInClient GSIClient;

    void showLoggedInUser(User user) {
        editText_username.setVisibility(View.INVISIBLE);
        editText_password.setVisibility(View.INVISIBLE);
        button_login.setVisibility(View.INVISIBLE);
        button_loginWithGoogle.setVisibility(View.INVISIBLE);
        button_logout.setVisibility(View.VISIBLE);
        loginForm_info.setText(
                "Welcome, " + user.getUsername() + "!" + "\n" +
                        (user.isIbis() ? "You're an ibis." : "You're a sparrow."));
    }

    void showLoggedOutUser() {
        editText_username.setVisibility(View.VISIBLE);
        editText_password.setVisibility(View.VISIBLE);
        button_login.setVisibility(View.VISIBLE);
        button_loginWithGoogle.setVisibility(View.VISIBLE);
        button_logout.setVisibility(View.INVISIBLE);
        loginForm_info.setText("");
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
        button_loginWithGoogle = findViewById(R.id.loginForm_buttonWithGoogle);
        button_logout = findViewById(R.id.logout_button);
        loginForm_info = findViewById(R.id.loginForm_info);

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

        button_loginWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = GSIClient.getSignInIntent();
                startActivityForResult(signInIntent, 0);
            }
        });

        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userController.logout();
                showLoggedOutUser();
            }
        });

        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
        GSIClient = GoogleSignIn.getClient(this, gso);

        User user = userController.getCurrentUser();
        if (user != null) {
            showLoggedInUser(user);

        } else {
            showLoggedOutUser();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                System.out.println(data);
                GoogleSignInAccount account = task.getResult(ApiException.class);
                User user = userController.authenticateWithGoogle(account);

                if (user == null) {
                    showToast("Username doesn't exist or password doesn't match.");

                } else {
                    showLoggedInUser(user);
                }

            } catch (ApiException e) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                System.out.println("Sign in failed: " + e.getStatusCode() + " - " + e.getMessage());
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            GSIClient.signOut();
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
