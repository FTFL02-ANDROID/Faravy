package com.ftfl.myvisitedplaces;

import java.util.Timer;
import java.util.TimerTask;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.ftfl.modelclass.VisitedPlace;
import com.ftfl.placesdatabase.PlacesDataSource;

public class SplashActivity extends Activity {
	
	PlacesDataSource mDataSource = null;
	VisitedPlace mPlaces = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		//Set the color of ActionBar
		ActionBar ab = getActionBar(); 
		ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#000000"));    
		ab.setBackgroundDrawable(colorDrawable);
		mDataSource = new PlacesDataSource(this);
		
		new Timer().schedule(new TimerTask() {
			public void run() {
				SplashActivity.this.runOnUiThread(new Runnable() {
					public void run() {
						
						if(mDataSource.isEmpty())
						{
							startActivity(new Intent(SplashActivity.this,
									AddPlacesActivity.class));
						}
						else{
							startActivity(new Intent(SplashActivity.this,
									ListPlacesActivity.class));
							
						}

						finish();
					}
				});
			}
		}, 5000);
	}
}


