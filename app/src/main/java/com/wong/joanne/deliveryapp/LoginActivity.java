package com.wong.joanne.deliveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wong.joanne.deliveryapp.Customer.CustomerMainActivity;
import com.wong.joanne.deliveryapp.Driver.DriverMainActivity;
import com.wong.joanne.deliveryapp.Driver.test;
import com.wong.joanne.deliveryapp.Utility.LoginUser;

/**
 * Created by Sam on 10/20/2017.
 */

public class LoginActivity extends AppCompatActivity{

    private EditText editTextEmailText;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView txtSignupLink;
    private ProgressBar progressBar;

    private FirebaseAuth auth;
    private DatabaseReference userRef;
    private ValueEventListener valueEventListener;
    private LoginUser currentUser;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shared_login_layout);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        editTextEmailText = (EditText) findViewById(R.id.input_email);
        editTextPassword = (EditText) findViewById(R.id.input_password);
        buttonLogin = (Button) findViewById(R.id.btn_login);
        txtSignupLink = (TextView) findViewById(R.id.link_signup);

        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                login();
            }
        });

        txtSignupLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                register();
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();

        checkUserAuthentication();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        userRef.removeEventListener(valueEventListener);
    }


    private void checkUserAuthentication()
    {
        progressBar.setVisibility(View.VISIBLE);
        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if(firebaseUser != null){
            checkUserType(firebaseUser.getEmail());
        }
    }

    private boolean validateForm()
    {
        boolean valid = true;
        String email = editTextEmailText.getText().toString();
        String password = editTextPassword.getText().toString();

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmailText.setError("enter a valid email address");
            valid = false;
        }
        else{
            editTextEmailText.setError(null);
        }

        if(password.isEmpty() || password.length() <4 || password.length() > 12){
            editTextPassword.setError("between 4 and 12 alphanumeric characters");
            valid = false;
        }
        else{
            editTextPassword.setError(null);
        }

        return valid;
    }

    private void login(){
        if(validateForm())
        {
            buttonLogin.setEnabled(false);
            final String email = editTextEmailText.getText().toString();
            String password = editTextPassword.getText().toString();
            progressBar.setVisibility(View.VISIBLE);

            loginAuth(email, password);
        }
        else{
            loginFailed();
            return;
        }

    }

    private void test()
    {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    }

    private void loginAuth(final String email, String password)
    {
        auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(LoginActivity.this, "loginUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            buttonLogin.setEnabled(true);
                        }
                        else {
                            checkUserType(email);
                        }
                    }
                });
    }

    private void loginSuccess()
    {
        Toast.makeText(getBaseContext(), "Login Success", Toast.LENGTH_LONG).show();
        buttonLogin.setEnabled(true);
    }

    private void loginFailed()
    {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        buttonLogin.setEnabled(true);
    }

    private void register()
    {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        LoginActivity.this.startActivity(intent);
    }

    private void checkUserType(final String email)
    {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        valueEventListener = new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    LoginUser loginUser = snapshot.getValue(LoginUser.class);
                    if(loginUser.email.equals(email)){
                        currentUser = loginUser;
                        if(loginUser.account_type == 1){
                            loginSuccess();
                            isDriver();
                        }
                        else {
                            loginSuccess();
                            isCustomer();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        userRef.addListenerForSingleValueEvent(valueEventListener);
    }

    private void isDriver()
    {
        progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(LoginActivity.this, DriverMainActivity.class);
        intent.putExtra("user", currentUser);
        startActivity(intent);
        finish();
    }

    private void isCustomer()
    {
        progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(LoginActivity.this, CustomerMainActivity.class);
        intent.putExtra("user", currentUser);
        startActivity(intent);
        finish();
    }

}
