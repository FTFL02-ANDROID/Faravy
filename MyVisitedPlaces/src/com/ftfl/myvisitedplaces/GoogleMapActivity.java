package com.ftfl.myvisitedplaces;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import com.ftfl.modelclass.VisitedPlace;
import com.ftfl.placesdatabase.PlacesDataSource;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapActivity extends Activity {
	
	PlacesDataSource mDataSource = null;
	VisitedPlace mPlace = null;
	String mID = "";
	Long mLId ;

	// Google Map
    GoogleMap mGoogleMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		//Set the color of ActionBar
		
		ActionBar ab = getActionBar(); 
		ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF5226"));    
		ab.setBackgroundDrawable(colorDrawable);
		
		Intent mActivityIntent = getIntent();
		mID = mActivityIntent.getStringExtra("mId");
		
		if (mID != null) {
			mLId = Long.parseLong(mID);
			
			mDataSource = new PlacesDataSource(this);
			mPlace = mDataSource
					.singlePlaceData(mLId);
			double mLatitude = mPlace.getmLatitude();
			double mLongitude = mPlace.getmLongitude();
		
		try {
			// Loading map
			initilizeMap();

			// Changing map type
			mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		    mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
		    

			// Showing / hiding your current location
			mGoogleMap.setMyLocationEnabled(true);

			// Enable / Disable zooming controls
			mGoogleMap.getUiSettings().setZoomControlsEnabled(true);

			// Enable / Disable my location button
			mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);

			// Enable / Disable Compass icon
			mGoogleMap.getUiSettings().setCompassEnabled(true);

			// Enable / Disable Rotate gesture
			mGoogleMap.getUiSettings().setRotateGesturesEnabled(true);

			// Enable / Disable zooming functionality
			mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);		

			// Adding a marker
			MarkerOptions marker = new MarkerOptions().position(
					new LatLng(mLatitude, mLongitude));
				
			mGoogleMap.addMarker(marker);


				// Move the camera to last position with a zoom level
			 
			CameraPosition cameraPosition = new CameraPosition.Builder()
			 .target(new LatLng(mLatitude,mLongitude)).zoom(15).build();

			mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
				
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		}

	}


	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}

	/**
	 * function to load map If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (mGoogleMap == null) {
			mGoogleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			// check if map is created successfully or not
			if (mGoogleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

}
