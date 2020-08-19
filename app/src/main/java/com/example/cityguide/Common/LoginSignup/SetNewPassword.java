package com.example.cityguide.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cityguide.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetNewPassword extends AppCompatActivity {

    TextInputLayout newPw,confirmPw;
    Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

        newPw = findViewById(R.id.new_password);
        confirmPw = findViewById(R.id.confirm_password);
        nextBtn = findViewById(R.id.set_new_password_button);
    }

    public void setNewPasswordBtn(View view){
        if(!validatePassword() | !validateConfirmPassword()){
            return;
        }

        //Get data from fields
        String _newPassword = newPw.getEditText().getText().toString().trim();
        String _phoneNumber = getIntent().getStringExtra("phoneNo");

        //Update data in firebase and in sessions
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(_phoneNumber).child("password").setValue(_newPassword);

        startActivity(new Intent(getApplicationContext(),ForgetPasswordSuccessMessage.class));
        finish();
    }

    private boolean validatePassword() {
        String _newPassword = newPw.getEditText().getText().toString().trim();

        if(_newPassword.isEmpty()){
            newPw.setError("Phone number can't be empty");
            newPw.requestFocus();
            return false;
        } else  {
            return true;
        }
    }

    private boolean validateConfirmPassword(){

        String _confirmPassword = confirmPw.getEditText().getText().toString().trim();

        if(_confirmPassword.isEmpty()){
            confirmPw.setError("Confirm Password can't be empty");
            confirmPw.requestFocus();
            return false;
        }else {
            return true;
        }
    }
}
