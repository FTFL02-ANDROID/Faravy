package com.faravy.icare;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.faravy.adapter.DoctorAdapter;
import com.faravy.database.DoctorDataSource;
import com.faravy.database.HealthCenterDataSource;
import com.faravy.modelclass.Doctor;
import com.faravy.modelclass.HealthCenter;
import com.faravy.modelclass.Profile;

public class AddCareCenterActivity extends Activity {

	HealthCenterDataSource mDataSource = null;
	HealthCenter mHealthCenter;

	EditText mEtName;
	EditText mEtAddress;
	EditText mEtLatitude;
	EditText mEtLongitude;

	String mName;
	String mAddress;
	String mLatitude;
	String mLongitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_care_center);

		ActionBar ab = getActionBar();
		ColorDrawable colorDrawable = new ColorDrawable(
				Color.parseColor("#0080FF"));
		ab.setBackgroundDrawable(colorDrawable);

		mEtName = (EditText) findViewById(R.id.addName);
		mEtAddress = (EditText) findViewById(R.id.addAddress);
		mEtLatitude = (EditText) findViewById(R.id.addLatitude);
		mEtLongitude = (EditText) findViewById(R.id.addLongitude);

		mDataSource = new HealthCenterDataSource(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_profile, menu);
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
		mAddress = mEtAddress.getText().toString();
		mLatitude = mEtLatitude.getText().toString();
		mLongitude = mEtLongitude.getText().toString();

		mHealthCenter = new HealthCenter(mName, mAddress, mLatitude, mLongitude);

		long inserted = mDataSource.insertData(mHealthCenter);

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

	}
}
