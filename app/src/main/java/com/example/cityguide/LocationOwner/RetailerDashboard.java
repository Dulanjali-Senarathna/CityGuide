package com.example.cityguide.LocationOwner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.cityguide.Databases.SessionManager;
import com.example.cityguide.R;

import java.util.HashMap;

public class RetailerDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_dashboard);

        TextView textView = findViewById(R.id.textView);

        SessionManager sessionManager = new SessionManager(RetailerDashboard.this,SessionManager.SESSION_USERSESSION);
       HashMap<String,String> userDetails = sessionManager.getUserDetailFromSession();

       String fullName = userDetails.get(SessionManager.KEY_FULLNAME);
       String phoneNumber = userDetails.get(SessionManager.KEY_PHONENUMBER);

       textView.setText(fullName + "\n" +phoneNumber);
    }
}
