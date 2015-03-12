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

import com.faravy.database.ProfileDataSource;
import com.faravy.modelclass.Profile;

public class AddProfileActivity extends Activity {

	EditText mEtName;
	EditText mEtDob;
	EditText mEtHeight;
	EditText mEtWeight;
	Button mBtnSave;

	String mName;
	String mDob;
	String mHeight;
	String mWeight;

	Profile mProfile;
	ProfileDataSource mProfileDataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_profile);

		ActionBar ab = getActionBar();
		ColorDrawable colorDrawable = new ColorDrawable(
				Color.parseColor("#0080FF"));
		ab.setBackgroundDrawable(colorDrawable);

		mEtName = (EditText) findViewById(R.id.addName);
		mEtDob = (EditText) findViewById(R.id.addBirthDay);
		mEtHeight = (EditText) findViewById(R.id.addHeight);
		mEtWeight = (EditText) findViewById(R.id.addWeight);

		mProfileDataSource = new ProfileDataSource(this);

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
		mDob = mEtDob.getText().toString();
		mHeight = mEtHeight.getText().toString();
		mWeight = mEtWeight.getText().toString();

		mProfile = new Profile(mName, mDob, mHeight, mWeight);

		long inserted = mProfileDataSource.insertData(mProfile);

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
