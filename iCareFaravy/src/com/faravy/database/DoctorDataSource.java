package com.faravy.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.faravy.modelclass.Doctor;

public class DoctorDataSource {

	private SQLiteDatabase mIcareDatabase;
	private IcareSQLiteHelper mIcareDbHelper;
	Doctor mDoctor;

	public DoctorDataSource(Context context) {
		mIcareDbHelper = new IcareSQLiteHelper(context);
	}

	// Open a method for writable database

	public void open() throws SQLException {
		mIcareDatabase = mIcareDbHelper.getWritableDatabase();
	}

	// Close database connection

	public void close() {
		mIcareDbHelper.close();

	}

	// Insert data into the database.

	public long insertData(Doctor insertDoctor) {

		this.open();

		ContentValues cv = new ContentValues();
		cv.put(IcareSQLiteHelper.COL_NAME, insertDoctor.getmName());
		cv.put(IcareSQLiteHelper.COL_DETAILS, insertDoctor.getmDetails());
		cv.put(IcareSQLiteHelper.COL_APPOINMENT, insertDoctor.getmAppoinment());
		cv.put(IcareSQLiteHelper.COL_PHONE, insertDoctor.getmPhone());
		cv.put(IcareSQLiteHelper.COL_EMAIL, insertDoctor.getmEmail());

		long check = mIcareDatabase.insert(IcareSQLiteHelper.TABLE_DOCTOR,
				null, cv);
		mIcareDatabase.close();

		this.close();
		return check;

	}

	public ArrayList<Doctor> getAllDoctor() {

		ArrayList<Doctor> doctor_list = new ArrayList<Doctor>();
		this.open();

		Cursor cursor = mIcareDatabase.query(IcareSQLiteHelper.TABLE_DOCTOR,
				null, null, null, null, null, null);

		// looping through all rows and adding to list
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {

				String mId = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_ID));
				String mName = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_NAME));
				String mDetails = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_DETAILS));
				String mAppoinment = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_APPOINMENT));
				String mPhone = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_PHONE));
				String mEmail = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_EMAIL));

				doctor_list.add(new Doctor(mId, mName, mDetails, mAppoinment,
						mPhone, mEmail));

				cursor.moveToNext();
			}
		}
		cursor.close();
		mIcareDatabase.close();

		return doctor_list;
	}

	public Doctor singleDoctor(String id) {
		this.open();
		Doctor doctor;

		Cursor cursor = mIcareDatabase.query(IcareSQLiteHelper.TABLE_DOCTOR,
				new String[] { IcareSQLiteHelper.COL_ID,
						IcareSQLiteHelper.COL_NAME,
						IcareSQLiteHelper.COL_DETAILS,
						IcareSQLiteHelper.COL_APPOINMENT,
						IcareSQLiteHelper.COL_PHONE,
						IcareSQLiteHelper.COL_EMAIL, },

				IcareSQLiteHelper.COL_ID + "=" + id, null, null, null, null);

		cursor.moveToFirst();

		String mId = cursor.getString(cursor
				.getColumnIndex(IcareSQLiteHelper.COL_ID));
		String mName = cursor.getString(cursor
				.getColumnIndex(IcareSQLiteHelper.COL_NAME));
		String mDetails = cursor.getString(cursor
				.getColumnIndex(IcareSQLiteHelper.COL_DETAILS));
		String mAppoinment = cursor.getString(cursor
				.getColumnIndex(IcareSQLiteHelper.COL_APPOINMENT));
		String mPhone = cursor.getString(cursor
				.getColumnIndex(IcareSQLiteHelper.COL_PHONE));
		String mEmail = cursor.getString(cursor
				.getColumnIndex(IcareSQLiteHelper.COL_EMAIL));

		cursor.close();
		doctor = new Doctor(mId, mName, mDetails, mAppoinment, mPhone, mEmail);

		this.close();
		return doctor;
	}

	// Delete data form database.
	public boolean deleteData(String id) {
		this.open();
		try {
			mIcareDatabase.delete(IcareSQLiteHelper.TABLE_DOCTOR,
					IcareSQLiteHelper.COL_ID + "=" + id, null);
		} catch (Exception ex) {
			Log.e("ERROR", "data insertion problem");
			return false;
		}
		this.close();
		return true;
	}

}
