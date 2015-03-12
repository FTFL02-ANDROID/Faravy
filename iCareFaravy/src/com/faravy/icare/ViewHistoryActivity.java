package com.faravy.icare;

import com.faravy.database.MedicalHistoryDataSource;
import com.faravy.modelclass.MedicalHistory;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHistoryActivity extends Activity {
	MedicalHistory mHistory;
	MedicalHistoryDataSource mDataSource;
	ImageView mPresCription;
	TextView mDoctorName;
	TextView mDetails;
	TextView mDate;
	String mPhotoPath = "";
	Bitmap mBitmap = null;
	String mID = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_history);
		
		ActionBar ab = getActionBar();
		ColorDrawable colorDrawable = new ColorDrawable(
				Color.parseColor("#0080FF"));
		ab.setBackgroundDrawable(colorDrawable);
		mPresCription = (ImageView) findViewById(R.id.showPrescription);
		mDoctorName = (TextView) findViewById(R.id.showDoctorName);
		mDetails = (TextView) findViewById(R.id.showDetails);
		mDate = (TextView) findViewById(R.id.showDate);

		Intent mActivityIntent = getIntent();
		mID = mActivityIntent.getStringExtra("mId");
		mDataSource = new MedicalHistoryDataSource(this);
		mHistory = mDataSource.singleHistory(mID);

		String doctorName = mHistory.getmDoctorName();
		String details = mHistory.getmDetails();
		String date = mHistory.getmDate();

		mDoctorName.setText("Dr. "+doctorName);
		mDetails.setText(details);
		mDate.setText(date);
		mPhotoPath = mHistory.getmPhoto();
		preViewImage();
		mPresCription.setImageBitmap(mBitmap);

	}

	private void preViewImage() {
		try {

			// bimatp factory
			BitmapFactory.Options options = new BitmapFactory.Options();

			// downsizing image as it throws OutOfMemory Exception for larger
			// images
			options.inSampleSize = 1;

			mBitmap = BitmapFactory.decodeFile(mPhotoPath, options);

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_history, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
