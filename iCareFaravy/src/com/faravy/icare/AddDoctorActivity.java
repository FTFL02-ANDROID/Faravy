package com.faravy.icare;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.faravy.database.DoctorDataSource;
import com.faravy.modelclass.Doctor;

public class AddDoctorActivity extends Activity {

	EditText mEtName;
	EditText mEtDetails;
	EditText mEtAppoinment;
	EditText mEtPhone;
	EditText mEtEmail;
	Button mBtnSave;

	String mName;
	String mDetails;
	String mAppoinment;
	String mPhone;
	String mEmail;

	Doctor mDoctor;
	DoctorDataSource mDataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_doctor);
		ActionBar ab = getActionBar();
		ColorDrawable colorDrawable = new ColorDrawable(
				Color.parseColor("#0080FF"));
		ab.setBackgroundDrawable(colorDrawable);

		mEtName = (EditText) findViewById(R.id.addName);
		mEtDetails = (EditText) findViewById(R.id.addDetails);
		mEtAppoinment = (EditText) findViewById(R.id.addAppointment);
		mEtPhone = (EditText) findViewById(R.id.addPhone);
		mEtEmail = (EditText) findViewById(R.id.addEmail);

		mDataSource = new DoctorDataSource(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_doctor, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.action_settings:
			// search action
			return true;

		case R.id.save:
			save();
			return true;
		default:

			return super.onOptionsItemSelected(item);
		}
	}

	private void save() {

		mName = mEtName.getText().toString();
		mDetails = mEtDetails.getText().toString();
		mAppoinment = mEtAppoinment.getText().toString();
		mPhone = mEtPhone.getText().toString();
		mEmail = mEtEmail.getText().toString();

		mDoctor = new Doctor(mName, mDetails, mAppoinment, mPhone, mEmail);

		long inserted = mDataSource.insertData(mDoctor);
		if (inserted >= 0) {
			Toast.makeText(getApplicationContext(),mName+" is added in List" ,
					Toast.LENGTH_LONG).show();

			Intent mIntent = new Intent(getApplicationContext(),
					DrawerActivity.class);
			startActivity(mIntent);
			exit();
		} else {
			Toast.makeText(getApplicationContext(), "Insertion failed",
					Toast.LENGTH_LONG).show();

		}

	}

	private void exit() {
		super.finishAffinity();
		
	}

}
