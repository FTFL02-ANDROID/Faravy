package com.ftfl.myvisitedplaces;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftfl.modelclass.VisitedPlace;
import com.ftfl.placesdatabase.PlacesDataSource;


public class ViewPlaceActivity extends Activity {
	
	TextView mTvName = null;	
	TextView mTvPurpose = null;
	TextView mTvAddress = null;
	TextView mTvLatitude = null;
	TextView mTvLongitude = null;
	TextView mTvStartDay = null;
	TextView mTvEndDay = null;
	TextView mTvNotes = null;
	ImageView mShowImage = null;
	PlacesDataSource mDataSource = null;
	VisitedPlace mPlace = null;
	String mPhotoPath = "";
	Bitmap mBitmap = null;	
	String mID = "";
	Long mLId ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.places_view);
		
		//Set the color of ActionBar
       
		ActionBar ab = getActionBar(); 
	    ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0066FF"));    
	    ab.setBackgroundDrawable(colorDrawable);
		
		mTvName = (TextView) findViewById(R.id.viewName);
		mTvPurpose=(TextView)findViewById(R.id.viewPurpose);
		mTvAddress = (TextView) findViewById(R.id.viewAddress);
		mTvLatitude = (TextView) findViewById(R.id.viewLatitude);
		mTvLongitude = (TextView) findViewById(R.id.viewLongitude);
		mTvStartDay = (TextView) findViewById(R.id.viewStartDay);
		mTvEndDay = (TextView) findViewById(R.id.viewEndDay);
		mTvNotes = (TextView) findViewById(R.id.viewNotes);
		mShowImage = (ImageView)findViewById(R.id.ivPhoto);
		
		Intent mActivityIntent = getIntent();
		mID = mActivityIntent.getStringExtra("mId");

		if (mID != null) {
			mLId = Long.parseLong(mID);

			/*
			 * get the activity which include all data from database according
			 * profileId of the clicked item.
			 */
			mDataSource = new PlacesDataSource(this);
			mPlace = mDataSource
					.singlePlaceData(mLId);

			String mName = mPlace.getmName();
			String mPurpose = mPlace.getmPurpose();
			String mAddress = mPlace.getmAddress();
			double mLatitude = mPlace.getmLatitude();
			double mLongitude = mPlace.getmLongitude();
			String mStartDay = mPlace.getmStartDay();
			String mEndDay = mPlace.getmEndDay();
			String mNotes = mPlace.getmNotes();
	

			// set the value of database to the text field.
			
			String s1= Double.toString(mLatitude);
			String s2= Double.toString(mLongitude);
			mTvName.setText(mName);
			mTvPurpose.setText(mPurpose);
			mTvAddress.setText(mAddress);
			mTvLatitude.setText(s1);
			mTvLongitude.setText(s2);
			mTvStartDay.setText(mStartDay);
			mTvEndDay.setText(mEndDay);
			mTvNotes.setText(mNotes);
			mPhotoPath =mPlace.getmImage();
			previewCapturedImage();
			mShowImage.setImageBitmap(mBitmap);

		}
	}

	private void previewCapturedImage() {
		 try {
			 
	            // bimatp factory
	            BitmapFactory.Options options = new BitmapFactory.Options();
	 
	            // downsizing image as it throws OutOfMemory Exception for larger
	            // images
	            options.inSampleSize = 10 ;
	 
	            mBitmap = BitmapFactory.decodeFile(mPhotoPath, options);
	 
	            
	        } catch (NullPointerException e) {
	            e.printStackTrace();
	        }
		
	}

	
}
