package com.example.cityguide.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.cityguide.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class Login extends AppCompatActivity {

    //Variables
    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_retailer_login);

        //hooks
        countryCodePicker = findViewById(R.id.login_country_code_picker);
        phoneNumber = findViewById(R.id.login_phone_number);
        password = findViewById(R.id.login_password);

    }

    public void letTheUserLoggedIn(View view){

        if(!validateFields()){
            return;
        }

        //get data
        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();
        final String _password = password.getEditText().getText().toString().trim();

        if(_phoneNumber.charAt(0)=='0'){
            _phoneNumber = _phoneNumber.substring(1);
        }

        final String _completePhoneNumber = "+"+countryCodePicker.getFullNumber()+_phoneNumber;

        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_completePhoneNumber);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    phoneNumber.setError(null);
                    phoneNumber.setErrorEnabled(false);

                    String systemPassword = dataSnapshot.child(_completePhoneNumber).child("password").getValue(String.class);
                    if(systemPassword.equals(_password)){
                        password.setError(null);
                        password.setErrorEnabled(false);

                        String _fullName = dataSnapshot.child(_completePhoneNumber).child("fullName").getValue(String.class);
                        String _email = dataSnapshot.child(_completePhoneNumber).child("email").getValue(String.class);
                        String _phoneNo = dataSnapshot.child(_completePhoneNumber).child("phoneNo").getValue(String.class);
                        String _dateOfBirth = dataSnapshot.child(_completePhoneNumber).child("date").getValue(String.class);

                        Toast.makeText(Login.this, _fullName+"\n"+_email+"\n"+_phoneNo+"\n"+_dateOfBirth, Toast.LENGTH_SHORT).show();
                    }
                    else {

                        Toast.makeText(Login.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    }
                }
                else {

                    Toast.makeText(Login.this, "No such user exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(Login.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean validateFields() {
        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();

        if(_phoneNumber.isEmpty()){
            phoneNumber.setError("Phone number can't be empty");
            phoneNumber.requestFocus();
            return false;
        }else if(_password.isEmpty()){
            password.setError("Password can't be empty");
            password.requestFocus();
            return false;
        }else {
            return true;
        }
    }
}
