package com.ftfl.photowithcurrentlocation;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LocationActivity extends Activity {
	EditText mEtLati;
	EditText mEtLongi;
	EditText mEtRemarks;
	Button mSave_btn = null;
	Button mImage_btn=null;
	ImageView mImageView;
	static File mMediaFile;
	static String mCurrentPhotoPath = "";
	String mRemarks = "";
	String mDate="";
	double mLatitude;
	double mLongitude;
	String mProfileID = "";
	String mID = "";
	Long mLId ;
	PhotoDataSource mPhotoDataSource = null;
    Photo mUpdatePhoto = null;
	String mActivityProfileId = "";
	
	// Activity request codes
		private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
		public static final int MEDIA_TYPE_IMAGE = 1;

		// directory name to store captured images and videos
		private static final String IMAGE_DIRECTORY_NAME = "Places_List";

		private Uri fileUri; // file url to store image

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		
		mEtLati=(EditText)findViewById(R.id.addLatitude);
		mEtLongi=(EditText)findViewById(R.id.addLongitude);
		mEtRemarks=(EditText)findViewById(R.id.addRemarks);
		mSave_btn = (Button) findViewById(R.id.btnSave);
		mImage_btn = (Button) findViewById(R.id.btnImage);
		
   /* Use the LocationManager class to obtain GPS locations */
      LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

      LocationListener mlocListener = new MyLocationListener();
      mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
      mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,0,mlocListener);
     /* mSave_btn.setOnClickListener(this);
	  mImage_btn.setOnClickListener(this);*/
		  
		  Intent mActivityIntent = getIntent();
			mID = mActivityIntent.getStringExtra("mId");

			if (mID != null) {
				mLId = Long.parseLong(mID);

				
				// * get the activity which include all data from database according
				 //* profileId of the clicked item.
				 
				mPhotoDataSource = new PhotoDataSource(this);
				mUpdatePhoto = mPhotoDataSource.singlePhotoData(mLId);
				
				 mRemarks = mUpdatePhoto.getmDescription();

				mEtRemarks.setText(mRemarks);
				mSave_btn.setText("Update");

			}
		  
	}
	
	

	    // Class My Location Listener 
	    public class MyLocationListener implements LocationListener
	    {

	      @Override
	      public void onLocationChanged(Location loc)
	      {

	        double latitude=loc.getLatitude();
	        double longitude=loc.getLongitude();
	        mEtLati.setText (Double.toString(latitude));
	        mEtLongi.setText (Double.toString(longitude));
	      }

	      @Override
	      public void onProviderDisabled(String provider)
	      {
	        Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
	      }

	      @Override
	      public void onProviderEnabled(String provider)
	      {
	        Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
	      }

	      @Override
	      public void onStatusChanged(String provider, int status, Bundle extras)
	      {

	      }
	    }

		 public void takePhoto(View v) {
						
			 Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

				fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

				intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

				// start the image capture Intent
				startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
			mCurrentPhotoPath = mMediaFile.getAbsolutePath();
		 }
			    

		public void save(View v1){
				mRemarks = mEtRemarks.getText().toString();
				String	sLatitude=mEtLati.getText().toString();
				String	sLongitude=mEtLongi.getText().toString();
	            Double latitude=Double.parseDouble(sLatitude);
	            Double longitude=Double.parseDouble(sLongitude);
	            
                Photo  ePhoto= new Photo();
				
              
				ePhoto.setmLatitude(latitude);
				ePhoto.setmLongitude(longitude);
				ePhoto.setmDescription(mRemarks);
				ePhoto.setmDate(mDate);
				ePhoto.setmImage(mCurrentPhotoPath);
				
				if (mID != null) {

					mLId = Long.parseLong(mID);

					mPhotoDataSource = new PhotoDataSource(this);

					if (mPhotoDataSource.updateData(mLId, ePhoto) == true) {
					Toast	toast = Toast.makeText(this, "Successfully Updated.",
								Toast.LENGTH_SHORT);
						toast.show();
						startActivity(new Intent(LocationActivity.this,
								MainActivity.class));
						finish();
					} 
					else {
						Toast toast = Toast.makeText(this, "Not Updated.",
								Toast.LENGTH_LONG);
						toast.show();
					}
					}
				
				else {
					mPhotoDataSource = new PhotoDataSource(this);
					if (mPhotoDataSource.insert(ePhoto) == true) {
						Toast toast = Toast.makeText(this, "Successfully Saved.",
								Toast.LENGTH_SHORT);
						toast.show();

						startActivity(new Intent(LocationActivity.this,
								MainActivity.class));

						//finish();
					} else {
						Toast toast = Toast.makeText(this, "Not Saved.",
								Toast.LENGTH_LONG);
						toast.show();
					}
				}
				
			}

		
		

		private void captureImage() {
			
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

			intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

			// start the image capture Intent
			startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
		}
		
		/**
		 * Here we store the file url as it will be null after returning from camera
		 * app
		 */
		@Override
		protected void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);

			// save file url in bundle as it will be null on scren orientation
			// changes
			outState.putParcelable("file_uri", fileUri);
		}
		
		@Override
		protected void onRestoreInstanceState(Bundle savedInstanceState) {
			super.onRestoreInstanceState(savedInstanceState);

			// get the file url
			fileUri = savedInstanceState.getParcelable("file_uri");
		}

		/**
		 * Receiving activity result method will be called after closing the camera
		 * */
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// if the result is capturing Image
			if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
				 if (resultCode == RESULT_OK) {
		                // successfully captured the image
					 Toast.makeText(getApplicationContext(),
								"image captured", Toast.LENGTH_SHORT)
								.show();
				} else if (resultCode == RESULT_CANCELED) {
					// user cancelled Image capture
					Toast.makeText(getApplicationContext(),
							"User cancelled image capture", Toast.LENGTH_SHORT)
							.show();
				} else {
					// failed to capture image
					Toast.makeText(getApplicationContext(),
							"Sorry! Failed to capture image", Toast.LENGTH_SHORT)
							.show();
				}
			}
		}
		
		
		 // ------------ Helper Methods ----------------------

		
		// Creating file uri to store image
		 
		public Uri getOutputMediaFileUri(int type) { 
		Uri mUri= Uri.fromFile(getOutputMediaFile(type));
		return mUri;			
		}
		
		/**
		 * returning image
		 */
		private static File getOutputMediaFile(int type) {

			// External sdcard location
			File mediaStorageDir = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
					IMAGE_DIRECTORY_NAME);

			// Create the storage directory if it does not exist
			if (!mediaStorageDir.exists()) {
				if (!mediaStorageDir.mkdirs()) {
					Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
							+ IMAGE_DIRECTORY_NAME + " directory");
					return null;
				}
			}
			// Create a media file name
					String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
							Locale.getDefault()).format(new Date());
					
					if (type == MEDIA_TYPE_IMAGE) {
						mMediaFile = new File(mediaStorageDir.getPath() + File.separator
								+ "IMG_" + timeStamp + ".jpg");
					} else {
						return null;
					}
					

					return mMediaFile;
				}
			

				
			
		
	    
	    
	}

	

