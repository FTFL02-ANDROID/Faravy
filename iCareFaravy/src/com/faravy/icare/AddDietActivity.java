package com.faravy.icare;

import java.text.DecimalFormat;
import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.faravy.database.DietDataSource;
import com.faravy.modelclass.Diet;

public class AddDietActivity extends Activity {

	// Declaration Of View
	Spinner mSpinner;
	EditText mEtMenu;
	TextView mEtDietDate;
	TextView mEtDietTime;
	CheckBox mDailyAlarm;
	CheckBox mReminder;

	// String Values
	String mDietType;
	String mDietDate;
	String mDietTime;
	String mMenu;

	Integer mSettingHour = 0;
	Integer mSettingMinute = 0;
	int mHour = 0;
	int mMinute = 0;
	int mYear = 0;
	int mDay = 0;
	int mMonth = 0;
	Integer mSetHour = 0;
	Integer mSetMinute = 0;
	final Calendar mCalendar = Calendar.getInstance();

	// ArrayAdaper for Spinner
	ArrayAdapter<String> mSpinnerAdapter;

	// Spinner DataSource for Spinner
	private String[] mDietChartType = { "Select Diet Type", "Breakfast",
			"Snack", "Launch", "Dinner" };

	// Model Class Object
	Diet mDiet;

	// DataSource Object
	DietDataSource mDataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_diet);

		ActionBar ab = getActionBar();
		ColorDrawable colorDrawable = new ColorDrawable(
				Color.parseColor("#0080FF"));
		ab.setBackgroundDrawable(colorDrawable);

		// FindViewById
		findView();

		// Setting Spinner Adapter
		mSpinnerAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, mDietChartType);
		mSpinner.setAdapter(mSpinnerAdapter);
		mDataSource = new DietDataSource(this);
		
		Intent mActivityIntent = getIntent();
		String mStID = mActivityIntent.getStringExtra("id");

		if (mStID != null) {
			long mActivityId = Long.parseLong(mStID);
			mDataSource = new DietDataSource(this);
			mDiet = mDataSource.updateDiet(mStID);

			String mDate = mDiet.getmDate();
			String mTime = mDiet.getmTime();
			String mDietType = mDiet.getmName();
			String mMenu = mDiet.getmManu();

			// set the value of database to the text field.
			mEtDietDate.setText(mDate);
			mEtDietTime.setText(mTime);
			mEtMenu.setText(mMenu);
		}
			
			
			
			
		mEtDietDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mYear = mCalendar.get(Calendar.YEAR);
				mMonth = mCalendar.get(Calendar.MONTH);
				mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

				DatePickerDialog dialog = new DatePickerDialog(
						AddDietActivity.this, mDateSetListener, mYear, mMonth,
						mDay);
				dialog.show();

			}
		});

		mEtDietTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
				mMinute = mCalendar.get(Calendar.MINUTE);

				TimePickerDialog timeDialog = new TimePickerDialog(
						AddDietActivity.this, timePickerListener, mHour,
						mMinute, false);
				timeDialog.show();

			}
		});

	}

	private void findView() {
		mSpinner = (Spinner) findViewById(R.id.spinner);
		mEtDietDate = (TextView) findViewById(R.id.addDate);
		mEtDietTime = (TextView) findViewById(R.id.addTime);
		mEtMenu = (EditText) findViewById(R.id.addMenu);
		mDailyAlarm = (CheckBox) findViewById(R.id.dailyAlarm);
		mReminder = (CheckBox) findViewById(R.id.reminder);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_diet, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.save) {
			save();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void save() {

		mDietType = mSpinner.getSelectedItem().toString();
		mMenu = mEtMenu.getText().toString();
		mDietDate = mEtDietDate.getText().toString();
		mDietTime = mEtDietTime.getText().toString();

		mDiet = new Diet(mDietType, mMenu, mDietDate, mDietTime);

		long inserted = mDataSource.insertData(mDiet);

		if (inserted >= 0) {
			Toast.makeText(getApplicationContext(), "Data inserted",
					Toast.LENGTH_LONG).show();

			Intent mIntent = new Intent(getApplicationContext(),
					DrawerActivity.class);
			mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(mIntent);
			/*
			 * DietFragment d=new DietFragment();
			 * getFragmentManager().beginTransaction() .add(R.id.frame,
			 * d).commit();
			 */
			finishAffinity();
		} else {
			Toast.makeText(getApplicationContext(), "Insertion failed",
					Toast.LENGTH_LONG).show();
		}

		if (mDailyAlarm.isChecked()) {

			Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
			alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, mSettingHour);
			alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, mSettingMinute);
			alarmIntent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
			alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(alarmIntent);
		}

		if (mReminder.isChecked()) {

			Calendar cal = Calendar.getInstance();
			Intent intent = new Intent(Intent.ACTION_EDIT);
			intent.setType("vnd.android.cursor.item/event");
			intent.putExtra("beginTime", cal.getTimeInMillis());
			intent.putExtra("allDay", true);
			intent.putExtra("rrule", "FREQ=YEARLY");
			intent.putExtra("endTime", cal.getTimeInMillis() + 60 * 60 * 1000);
			intent.putExtra("title", "Diet Chart from iCare");
			startActivity(intent);

		}

	}

	// call DateSetListener for set selected date in edittext field
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			mEtDietDate.setText(new StringBuilder()
					.append(new DecimalFormat("00").format(mDay)).append("-")
					.append(new DecimalFormat("00").format(mMonth + 1))
					.append("-").append(mYear));
		}
	};

	// timepicker in dialogbox
	// call TimeSetListener for set selected time in edittext field
	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mSettingHour = hourOfDay;
			mSettingMinute = minute;
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
			mEtDietTime.setText(new StringBuilder()
					.append(new DecimalFormat("00").format(hour)).append(" : ")
					.append(new DecimalFormat("00").format(minute)).append(" ")
					.append(st));

		}
	};
}
