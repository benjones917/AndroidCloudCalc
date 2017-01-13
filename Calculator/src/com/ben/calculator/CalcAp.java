package com.ben.calculator;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;


public class CalcAp extends Application {
	

@Override
public void onCreate() {
    super.onCreate();
    // Initialize Crash Reporting.
    ParseCrashReporting.enable(this);
    
    

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);
    
    Log.d("CalcAp","Application created!");
    
    // Add your initialization code here
    Parse.initialize(this, "3jfaborPzIIZHTHZKzw30zbd0sTzlxQ5nqAehrsr", "zJoMMGbMkdFItdUvFNQF0qwKkL9bozU9RDjp9vjT");


    ParseUser.enableAutomaticUser();
    ParseACL defaultACL = new ParseACL();
    // Optionally enable public read access.
    // defaultACL.setPublicReadAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);

    }
}