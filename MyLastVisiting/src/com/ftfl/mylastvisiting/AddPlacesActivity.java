package com.ftfl.mylastvisiting;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;



public class AddPlacesActivity extends Activity implements
OnClickListener, OnTimeSetListener {
	Button btns_save = null;
	
	EditText etName = null;
	EditText etPurpose=null;
	EditText etAddress = null;
	EditText etLatitude = null;
	EditText etLongitude = null;
	EditText etStartDay = null;
	EditText etEndDay = null;
	EditText etNotes = null;
	
	String mName = "";
	String mPurpose="";
	String mAddress = "";
	String mLatitude = "";
	String mLongitude = "";
	String mStartDay = "";
	String mEndDay = "";
	String mNotes = "";
	
	
	TextView tvItem = null;

	int mHour = 0;
	int mMinute = 0;
	int mYear = 0;
	int mDay = 0;
	int mMonth = 0;
	String mStrProfileID = "";
	String mID = "";
	Long mLId ;
	PlacesDataSource placeDataSource = null;
	Places UpdatePlace = null;
	
	String mActivityCurrentDate = "";
	String mActivityProfileId = "";
	Integer mSetHour = 0;
	Integer mSetMinute = 0;
	String mCurrentDate = "";
	Calendar mCalendar = Calendar.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_places);
		
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		Date date = new Date();
		mCurrentDate = dateFormat.format(date);

		etName = (EditText) findViewById(R.id.addName);
		etPurpose=(EditText)findViewById(R.id.addPurpose);
		etAddress = (EditText) findViewById(R.id.addAddress);
		etLatitude = (EditText) findViewById(R.id.addLatitude);
		etLongitude = (EditText) findViewById(R.id.addLongitude);
		etStartDay = (EditText) findViewById(R.id.addStartDay);
		etEndDay = (EditText) findViewById(R.id.addEndDay);
		etNotes = (EditText) findViewById(R.id.addNotes);
		btns_save = (Button) findViewById(R.id.btnSave);

	
		etEndDay.setOnClickListener(this);
		btns_save.setOnClickListener(this);

		Intent mActivityIntent = getIntent();
		mID = mActivityIntent.getStringExtra("mId");

		if (mID != null) {
			mLId = Long.parseLong(mID);

			/*
			 * get the activity which include all data from database according
			 * profileId of the clicked item.
			 */
			placeDataSource = new PlacesDataSource(this);
			UpdatePlace = placeDataSource
					.singlePlaceData(mLId);

			String mName = UpdatePlace.getmName();
			String mPurpose=UpdatePlace.getmPurpose();
			String mAddress = UpdatePlace.getmAddress();
			String mLatitude = UpdatePlace.getmLatitude();
			String mLongitude = UpdatePlace.getmLongitude();
			String mStartDay = UpdatePlace.getmStartDay();
			String mEndDay = UpdatePlace.getmEndDay();
			String mNotes = UpdatePlace.getmNotes();
	

			// set the value of database to the text field.
	
			etName.setText(mName);
			etPurpose.setText(mPurpose);
			etAddress.setText(mAddress);
			etLatitude.setText(mLatitude);
			etLongitude.setText(mLongitude);
			etStartDay.setText(mStartDay);
			etEndDay.setText(mEndDay);
			etNotes.setText(mNotes);

			/*
			 * change button mName
			 */
			btns_save.setText("Update");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_place, menu);
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


	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub

		mSetHour = hourOfDay;
		mSetMinute = minute;
		int hour = 0;
		String st = "";
		if (hourOfDay > 12) {
			hour = hourOfDay - 12;
			st = "PM";
		}

		else if (hourOfDay == 12) {
			hour = hourOfDay;
			st = "PM";
		}

		else if (hourOfDay == 0) {
			hour = hourOfDay + 12;
			st = "AM";
		} else {
			hour = hourOfDay;
			st = "AM";
		}
		etEndDay.setText(new StringBuilder().append(hour).append(" : ")
				.append(minute).append(" ").append(st));

	}

	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Toast toast = null;
		switch (v.getId()) {

		case R.id.addStartDay:
			// Process to get Current Time

			mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
			mMinute = mCalendar.get(Calendar.MINUTE);

			// Launch Time Picker Dialog
			TimePickerDialog tpd = new TimePickerDialog(this, this, mHour,
					mMinute, false);
			tpd.show();
			break;

		case R.id.btnSave:

		
		
		
			mName = etName.getText().toString();
			mPurpose=etPurpose.getText().toString();
			mAddress = etAddress.getText().toString();
			mLatitude = etLatitude.getText().toString();
			mLongitude = etLongitude.getText().toString();			
			mStartDay = etStartDay.getText().toString();
			mEndDay = etEndDay.getText().toString();			
			mNotes = etNotes.getText().toString();

			Places place = new Places();
				place.setmName(mName);
				place.setmAddress(mAddress);
				place.setmLatitude(mLatitude);
				place.setmLongitude(mLongitude);
				place.setmStartDay(mStartDay);
				place.setmEndDay(mEndDay);
				place.setmNotes(mNotes);
				

			/*
			 * if update is needed then update otherwise submit
			 */
			if (mID != null) {

				mLId = Long.parseLong(mID);

				placeDataSource = new PlacesDataSource(this);

				if (placeDataSource.updateData(mLId, place) == true) {
					toast = Toast.makeText(this, "Successfully Updated.",
							Toast.LENGTH_SHORT);
					toast.show();
					startActivity(new Intent(AddPlacesActivity.this,
							PlacesListActivity.class));
					finish();
				} else {
					toast = Toast.makeText(this, "Not Updated.",
							Toast.LENGTH_LONG);
					toast.show();
				}
			} else {
				placeDataSource = new PlacesDataSource(this);
				if (placeDataSource.insert(place) == true) {
					toast = Toast.makeText(this, "Successfully Saved.",
							Toast.LENGTH_SHORT);
					toast.show();

					startActivity(new Intent(AddPlacesActivity.this,
							PlacesListActivity.class));

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
}
