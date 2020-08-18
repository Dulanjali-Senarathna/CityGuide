package com.example.cityguide.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.cityguide.R;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class SignUp3rdClass extends AppCompatActivity {

    //Variables
    ScrollView scrollView;
    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up3rd_class);

        //Hooks
        scrollView = findViewById(R.id.signup_3rd_screen_scroll_view);
        countryCodePicker = findViewById(R.id.country_code_picker);
        phoneNumber = findViewById(R.id.signup_phone_number);
    }

    public void callVerifyOTPScreen(View view){

        //Validate fields
        if(!validatePhoneNumber()){
            return;
        }//Validation succeeded and now move to next screen to verify phone number and save data

        //Get all values passed from previous screen to verify phone number & save data
        String _fullName = getIntent().getStringExtra("fullName");
        String _email = getIntent().getStringExtra("email");
        String _username = getIntent().getStringExtra("username");
        String _password = getIntent().getStringExtra("password");
        String _date = getIntent().getStringExtra("date");
        String _gender = getIntent().getStringExtra("gender");

        String _getUserEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim();
        String _phoneNo = "+"+countryCodePicker.getFullNumber()+_getUserEnteredPhoneNumber;

        Intent intent = new Intent(getApplicationContext(),VerifyOTP.class);

        //Pass all fields to the next activity
        intent.putExtra("fullName",_fullName);
        intent.putExtra("email",_email);
        intent.putExtra("username",_username);
        intent.putExtra("password",_password);
        intent.putExtra("date",_date);
        intent.putExtra("gender",_gender);
        intent.putExtra("phoneNo",_phoneNo);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair <View,String>(scrollView,"transition_OTP_screen");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp3rdClass.this,pairs);
            startActivity(intent,options.toBundle());
        }else {
            startActivity(intent);
        }



    }

    private boolean validatePhoneNumber() {
        String val = phoneNumber.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            phoneNumber.setError("Field can not empty");
            return false;
        }
        else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }
}
