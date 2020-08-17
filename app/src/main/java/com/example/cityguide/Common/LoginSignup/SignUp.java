package com.example.cityguide.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cityguide.R;
import com.google.android.material.textfield.TextInputLayout;

public class SignUp extends AppCompatActivity {

    //Variables
    ImageView backBtn;
    Button next,login;
    TextView titleText;

    //Get Data variables
    TextInputLayout fullName,username,email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_sign_up);

        //Hooks for animation
        backBtn = findViewById(R.id.signup_back_button);
        next = findViewById(R.id.signup_next_button);
        login = findViewById(R.id.signup_login_button);
        titleText = findViewById(R.id.signup_title_text);

        //Hooks for getting data
        fullName = findViewById(R.id.signup_fullname);
        username = findViewById(R.id.signup_username);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);


    }

    public void callNextSignupScreen(View view){

        if(!validateUserName() | !validateFullName() | !validateEmail() | !validatePassword()){
            return;
        }

        Intent intent = new Intent(getApplicationContext(),SignUp2ndClass.class);

        //Add transition
        Pair[] pairs = new Pair[4];

        pairs[0] = new Pair<View,String>(backBtn,"transition_back_arrow_btn");
        pairs[1] = new Pair<View,String>(next,"transition_next_btn");
        pairs[2] = new Pair<View,String>(login,"transition_login_btn");
        pairs[3] = new Pair<View,String>(titleText,"transition_title_text");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this,pairs);
            startActivity(intent,options.toBundle());
        }else {
            startActivity(intent);
        }
    }

    //Validation Functions
    private boolean validateFullName(){
        String val = fullName.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            fullName.setError("Field can not empty");
            return false;
        }
        else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUserName(){
        String val = username.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z";

        if(val.isEmpty()){
            username.setError("Field can not empty");
            return false;
        }else if(val.length()>20){
            username.setError("Username is too large");
            return false;
        }else if(!val.matches(checkspaces)){
            username.setError("No white spaces are allowed!");
            return false;
        }

        else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail(){
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty()){
            email.setError("Field can not empty");
            return false;
        }else if(!val.matches(checkEmail)){
            email.setError("No white spaces are allowed!");
            return false;
        }

        else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword(){
        String val = password.getEditText().getText().toString().trim();
        String checkPassword = "^" + "(?=.*[a-zA-Z])" +
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$)" +
                ".{4,}" +
                "$";

        if(val.isEmpty()){
            password.setError("Field can not empty");
            return false;
        }else if(!val.matches(checkPassword)){
            password.setError("Password should contain four characters!");
            return false;
        }

        else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }
}
