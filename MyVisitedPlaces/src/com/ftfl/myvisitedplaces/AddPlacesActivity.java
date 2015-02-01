package com.ftfl.myvisitedplaces;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.ftfl.modelclass.VisitedPlace;
import com.ftfl.placesdatabase.PlacesDataSource;



public class AddPlacesActivity extends Activity implements OnClickListener {
	
	Button mSave_btn = null;
	Button mImage_btn=null;
	
	EditText mEtName = null;
	EditText mEtPurpose=null;
	EditText mEtAddress = null;
	EditText mEtLatitude = null;
	EditText mEtLongitude = null;
	EditText mEtStartDay = null;
	EditText mEtEndDay = null;
	EditText mEtNotes = null;
	
	String mName = "";
	String mPurpose="";
	String mAddress = "";
	double mLatitude;
	double mLongitude;
	String mStartDay = "";
	String mEndDay = "";
	String mNotes = "";
	ImageView mImageView;
	static File mMediaFile;
	static String mCurrentPhotoPath = "";
	
	String mProfileID = "";
	String mID = "";
	Long mLId ;
	PlacesDataSource mPlaceDataSource = null;
	VisitedPlace mUpdatePlace = null;
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
		setContentView(R.layout.add_places);
		
		//Set the color of ActionBar
		
		ActionBar ab = getActionBar(); 
		ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0066FF"));    
		ab.setBackgroundDrawable(colorDrawable);

		mEtName = (EditText) findViewById(R.id.addName);
		mEtPurpose=(EditText)findViewById(R.id.addPurpose);
		mEtAddress = (EditText) findViewById(R.id.addAddress);
		mEtLatitude = (EditText) findViewById(R.id.addLatitude);
		mEtLongitude = (EditText) findViewById(R.id.addLongitude);
		mEtStartDay = (EditText) findViewById(R.id.addStartDay);
		mEtEndDay = (EditText) findViewById(R.id.addEndDay);
		mEtNotes = (EditText) findViewById(R.id.addNotes);
		
		mSave_btn = (Button) findViewById(R.id.btnSave);
		mImage_btn = (Button) findViewById(R.id.btnImage);
	

		mSave_btn.setOnClickListener(this);
		mImage_btn.setOnClickListener(this);
		
		Intent mActivityIntent = getIntent();
		mID = mActivityIntent.getStringExtra("mId");

		if (mID != null) {
			mLId = Long.parseLong(mID);

			/*
			 * get the activity which include all data from database according
			 * profileId of the clicked item.
			 */
			mPlaceDataSource = new PlacesDataSource(this);
			mUpdatePlace = mPlaceDataSource.singlePlaceData(mLId);

			 mName = mUpdatePlace.getmName();
			 mPurpose=mUpdatePlace.getmPurpose();
			 mAddress = mUpdatePlace.getmAddress();
			 mLatitude = mUpdatePlace.getmLatitude();
			 mLongitude = mUpdatePlace.getmLongitude();
			 mStartDay = mUpdatePlace.getmStartDay();
			 mEndDay = mUpdatePlace.getmEndDay();
			 mNotes = mUpdatePlace.getmNotes();	

			// set the value of database to the text field.
			
			String latitude= Double.toString(mLatitude);
			String longitude= Double.toString(mLongitude);
			
			mEtName.setText(mName);
			mEtPurpose.setText(mPurpose);
			mEtAddress.setText(mAddress);
			
			mEtLatitude.setText(latitude);
			mEtLongitude.setText(longitude);
			
			mEtStartDay.setText(mStartDay);
			mEtEndDay.setText(mEndDay);
			mEtNotes.setText(mNotes);
			
			//change button mName
			
			mSave_btn.setText("Update");
		}
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Toast toast = null;
		switch (v.getId()) {

		case R.id.btnImage:
			
			captureImage();
			mCurrentPhotoPath = mMediaFile.getAbsolutePath();
		    break;

		case R.id.btnSave:

			mName = mEtName.getText().toString();
			mPurpose=mEtPurpose.getText().toString();
			mAddress = mEtAddress.getText().toString();		   
			mStartDay = mEtStartDay.getText().toString();
			mEndDay = mEtEndDay.getText().toString();			
			mNotes = mEtNotes.getText().toString();
			
			String	sLatitude=mEtLatitude.getText().toString();
			String	sLongitude=mEtLongitude.getText().toString();
            Double latitude=Double.parseDouble(sLatitude);
            Double longitude=Double.parseDouble(sLongitude);
			
            VisitedPlace place = new VisitedPlace();
				
                place.setmName(mName);
				place.setmPurpose(mPurpose);
				place.setmAddress(mAddress);
				place.setmLatitude(latitude);
				place.setmLongitude(longitude);
				place.setmStartDay(mStartDay);
				place.setmEndDay(mEndDay);
				place.setmNotes(mNotes);
				place.setmImage(mCurrentPhotoPath);
				

			/*
			 * if update is needed then update otherwise submit
			 */
			if (mID != null) {

				mLId = Long.parseLong(mID);

				mPlaceDataSource = new PlacesDataSource(this);

				if (mPlaceDataSource.updateData(mLId, place) == true) {
					toast = Toast.makeText(this, "Successfully Updated.",
							Toast.LENGTH_SHORT);
					toast.show();
					startActivity(new Intent(AddPlacesActivity.this,
							ListPlacesActivity.class));
					finish();
				} else {
					toast = Toast.makeText(this, "Not Updated.",
							Toast.LENGTH_LONG);
					toast.show();
				}
			} else {
				mPlaceDataSource = new PlacesDataSource(this);
				if (mPlaceDataSource.insert(place) == true) {
					toast = Toast.makeText(this, "Successfully Saved.",
							Toast.LENGTH_SHORT);
					toast.show();

					startActivity(new Intent(AddPlacesActivity.this,
							ListPlacesActivity.class));

					//finish();
				} else {
					toast = Toast.makeText(this, "Not Saved.",
							Toast.LENGTH_LONG);
					toast.show();
				}
			}
			break;
		}

	}
	
	/**
	 * Checking device has camera hardware or not
	 * */
	private boolean isDeviceSupportCamera() {
		if (getApplicationContext().getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			// this device has a camera
			return true;
		} else {
			// no camera on this device
			return false;
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


