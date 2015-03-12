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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.faravy.database.VaccineDataSource;
import com.faravy.modelclass.Vaccine;

public class AddVaccineActivity extends Activity {

	EditText mEtName;
	EditText mEtDetails;
	TextView mEtDate;
	TextView mEtTime;

	String mName;
	String mDetails;
	String mDate;
	String mTime;
	CheckBox mReminder;

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

	Vaccine mVaccine;
	VaccineDataSource mDataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_vaccin);

		ActionBar ab = getActionBar();
		ColorDrawable colorDrawable = new ColorDrawable(
				Color.parseColor("#0080FF"));
		ab.setBackgroundDrawable(colorDrawable);

		mEtName = (EditText) findViewById(R.id.addName);
		mEtDetails = (EditText) findViewById(R.id.addDetail);
		mEtDate = (TextView) findViewById(R.id.addDate);
		mEtTime = (TextView) findViewById(R.id.addTime);
		mReminder = (CheckBox) findViewById(R.id.reminder);

		mDataSource = new VaccineDataSource(this);

		mEtDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mYear = mCalendar.get(Calendar.YEAR);
				mMonth = mCalendar.get(Calendar.MONTH);
				mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

				DatePickerDialog dialog = new DatePickerDialog(
						AddVaccineActivity.this, mDateSetListener, mYear,
						mMonth, mDay);
				dialog.show();

			}
		});

		mEtTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
				mMinute = mCalendar.get(Calendar.MINUTE);

				TimePickerDialog timeDialog = new TimePickerDialog(
						AddVaccineActivity.this, timePickerListener, mHour,
						mMinute, false);
				timeDialog.show();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_vaccin, menu);
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

		mName = mEtName.getText().toString();
		mDetails = mEtDetails.getText().toString();
		mDate = mEtDate.getText().toString();
		mTime = mEtTime.getText().toString();

		mVaccine = new Vaccine(mName, mDetails, mDate, mTime);

		long inserted = mDataSource.insertData(mVaccine);
		if (inserted >= 0) {
			Toast.makeText(getApplicationContext(), "Data inserted",
					Toast.LENGTH_LONG).show();

			Intent mIntent = new Intent(getApplicationContext(),
					DrawerActivity.class);
			startActivity(mIntent);
			finish();
		} else {
			Toast.makeText(getApplicationContext(), "Insertion failed",
					Toast.LENGTH_LONG).show();

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
			mEtDate.setText(new StringBuilder()
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
			mEtTime.setText(new StringBuilder()
					.append(new DecimalFormat("00").format(hour)).append(" : ")
					.append(new DecimalFormat("00").format(minute)).append(" ")
					.append(st));

		}
	};
}
