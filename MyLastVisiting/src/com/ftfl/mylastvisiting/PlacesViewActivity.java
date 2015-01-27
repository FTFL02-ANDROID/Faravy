package com.ftfl.mylastvisiting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;



public class PlacesViewActivity extends Activity {
	
	TextView tvName = null;	
	TextView tvPurpose = null;
	TextView tvAddress = null;
	TextView tvLatitude = null;
	TextView tvLongitude = null;
	TextView tvStartDay = null;
	TextView tvEndDay = null;
	TextView tvNotes = null;
	
	PlacesDataSource dataSource = null;
	Places place = null;
	
	String mID = "";
	Long mLId ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.places_view);
		
		tvName = (TextView) findViewById(R.id.viewName);
		tvPurpose=(TextView)findViewById(R.id.viewPurpose);
		tvAddress = (TextView) findViewById(R.id.viewAddress);
		tvLatitude = (TextView) findViewById(R.id.viewLatitude);
		tvLongitude = (TextView) findViewById(R.id.viewLongitude);
		tvStartDay = (TextView) findViewById(R.id.viewStartDay);
		tvEndDay = (TextView) findViewById(R.id.viewEndDay);
		tvNotes = (TextView) findViewById(R.id.viewNotes);
		
		Intent mActivityIntent = getIntent();
		mID = mActivityIntent.getStringExtra("mId");

		if (mID != null) {
			mLId = Long.parseLong(mID);

			/*
			 * get the activity which include all data from database according
			 * profileId of the clicked item.
			 */
			dataSource = new PlacesDataSource(this);
			place = dataSource
					.singlePlaceData(mLId);

			String mName = place.getmName();
			String mPurpose = place.getmPurpose();
			String mAddress = place.getmAddress();
			String mLatitude = place.getmLatitude();
			String mLongitude = place.getmLongitude();
			String mStartDay = place.getmStartDay();
			String mEndDay = place.getmEndDay();
			String mNotes = place.getmNotes();
	

			// set the value of database to the text field.
	
			tvName.setText(mName);
			tvPurpose.setText(mPurpose);
			tvAddress.setText(mAddress);
			tvLatitude.setText(mLatitude);
			tvLongitude.setText(mLongitude);
			tvStartDay.setText(mStartDay);
			tvEndDay.setText(mEndDay);
			tvNotes.setText(mNotes);

		}
	}

	
}
