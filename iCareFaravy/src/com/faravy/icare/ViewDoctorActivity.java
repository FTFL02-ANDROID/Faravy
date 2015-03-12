package com.faravy.icare;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.faravy.database.DoctorDataSource;
import com.faravy.modelclass.Doctor;

public class ViewDoctorActivity extends Activity {
	Doctor mDoctor;
	DoctorDataSource mDataSource;
	TextView mEtName;
	TextView mEtDetail;
	TextView mEtDate;
	TextView mEtPhone;
	TextView mEtEmail;
	String mID = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_doctor);
		
		ActionBar ab = getActionBar();
		ColorDrawable colorDrawable = new ColorDrawable(
				Color.parseColor("#0080FF"));
		ab.setBackgroundDrawable(colorDrawable);

		mEtName = (TextView) findViewById(R.id.addName);
		mEtDetail = (TextView) findViewById(R.id.addDetails);
		mEtDate = (TextView) findViewById(R.id.addAppointment);
		mEtPhone = (TextView) findViewById(R.id.addPhone);
		mEtEmail = (TextView) findViewById(R.id.addEmail);

		Intent mActivityIntent = getIntent();
		mID = mActivityIntent.getStringExtra("mId");
		mDataSource = new DoctorDataSource(this);
		mDoctor = mDataSource.singleDoctor(mID);

		String name = mDoctor.getmName();
		String detail = mDoctor.getmDetails();
		String date = mDoctor.getmAppoinment();
		String phone = mDoctor.getmPhone();
		String email = mDoctor.getmEmail();

		mEtName.setText(name);
		mEtDetail.setText(detail);
		mEtDate.setText(date);
		mEtPhone.setText(phone);
		mEtEmail.setText(email);

	}

	public void makeCall(View v) {

		String number = mEtPhone.getText().toString().trim();
		Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
				+ number));
		startActivity(callIntent);

	}

	public void sendSms(View v) {

		String number = mEtPhone.getText().toString().trim();

		Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
				+ number));
		startActivity(smsIntent);

	}

	public void sendEmail(View v) {

		String email = mEtEmail.getText().toString();

		/*Intent emailIntent = new Intent(Intent.ACTION_SEND,
				Uri.parse("mailto:"));
		emailIntent.setType("text/plain");

		emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
		emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");
		startActivity(Intent.createChooser(emailIntent, "Send mail..."));*/

		Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
				"mailto", email, null));
		startActivity(Intent.createChooser(intent, "Send email..."));
	}

	public void addToContact(View v) {

		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		int rawContactInsertIndex = ops.size();

		ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
				.withValue(RawContacts.ACCOUNT_TYPE, null)
				.withValue(RawContacts.ACCOUNT_NAME, null).build());
		ops.add(ContentProviderOperation
				.newInsert(Data.CONTENT_URI)
				.withValueBackReference(Data.RAW_CONTACT_ID,
						rawContactInsertIndex)
				.withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
				.withValue(StructuredName.DISPLAY_NAME,
						mEtName.getText().toString()) // Name of the
														// person
				.build());
		ops.add(ContentProviderOperation
				.newInsert(Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
						rawContactInsertIndex)
				.withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
				.withValue(Phone.NUMBER, mEtPhone.getText().toString()) // Number
																		// of
																		// the
																		// person
				.withValue(Phone.TYPE, Phone.TYPE_MOBILE).build()); // Type of
																	// mobile
																	// number
		try {
			getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);

			Toast.makeText(getApplicationContext(),
					"Successfully  Contract Added !!!!!!!", Toast.LENGTH_LONG)
					.show();
		} catch (RemoteException e) {
			// error
		} catch (OperationApplicationException e) {
			// error
		}

		Intent contacts = new Intent(Intent.ACTION_VIEW,
				ContactsContract.Contacts.CONTENT_URI);
		startActivity(contacts);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_doctor, menu);
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
