package com.premiernoobs.rakhbokoi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;

import com.premiernoobs.rakhbokoi.R;
import com.premiernoobs.rakhbokoi.Room.MainDatabase;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // change to night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        // check if any user is already logged in from ROOM DATABASE
        try{
            MainDatabase mainDatabase;
            mainDatabase = MainDatabase.getInstance(getApplicationContext());

            if(mainDatabase.userDao().getUserList().size() != 0){
                //homePage();
            }else{
                logInPage();
            }

        }catch (Exception e){
            logInPage();
        }

    }

    private void logInPage() {

        // go to the page activity after splash screen
        Intent intent = new Intent(getApplicationContext(), PageActivity.class);
        intent.putExtra("Fragment", String.valueOf(R.string.LOGIN_FRAGMENT));
        startActivity(intent);
        overridePendingTransition(0, 0); //intent soft animation
        finish();

    }

}