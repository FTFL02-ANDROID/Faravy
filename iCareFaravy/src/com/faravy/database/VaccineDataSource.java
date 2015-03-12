package com.faravy.database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.faravy.modelclass.Vaccine;

public class VaccineDataSource {

	private SQLiteDatabase mIcareDatabase;
	private IcareSQLiteHelper mIcareDbHelper;
	private Vaccine mVaccine;
	public String mCurrentDate;
	public VaccineDataSource(Context context) {
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
	public void cDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		mCurrentDate = dateFormat.format(date);
	}

	// Insert data into the database.

	public long insertData(Vaccine insertVaccine) {

		this.open();

		ContentValues cv = new ContentValues();
		cv.put(IcareSQLiteHelper.COL_NAME, insertVaccine.getmName());
		cv.put(IcareSQLiteHelper.COL_DETAILS, insertVaccine.getmDetails());
		cv.put(IcareSQLiteHelper.COL_DATE, insertVaccine.getmDate());
		cv.put(IcareSQLiteHelper.COL_TIME, insertVaccine.getmTime());

		long check = mIcareDatabase.insert(IcareSQLiteHelper.TABLE_VACCINE,
				null, cv);
		mIcareDatabase.close();

		this.close();
		return check;

	}

	public ArrayList<Vaccine> getUpcomingVaccine() {

		ArrayList<Vaccine> vaccine_list = new ArrayList<Vaccine>();
		this.open();
		this.cDate();

		Cursor cursor = mIcareDatabase
				.query(IcareSQLiteHelper.TABLE_VACCINE, new String[] {
						IcareSQLiteHelper.COL_ID, IcareSQLiteHelper.COL_NAME,
						IcareSQLiteHelper.COL_DETAILS, IcareSQLiteHelper.COL_DATE,
						IcareSQLiteHelper.COL_TIME, },

				IcareSQLiteHelper.COL_DATE + " > '" + mCurrentDate + "' ", null, null,
						null, null);

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
				String mDate = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_DATE));
				String mTime = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_TIME));

				vaccine_list
						.add(new Vaccine(mId, mName, mDetails, mDate, mTime));

				cursor.moveToNext();
			}
		}
		cursor.close();
		this.mIcareDatabase.close();

		return vaccine_list;
	}
	
	public ArrayList<Vaccine> getCompletedVaccine() {

		ArrayList<Vaccine> vaccine_list = new ArrayList<Vaccine>();
		this.open();
		this.cDate();

		Cursor cursor = mIcareDatabase
				.query(IcareSQLiteHelper.TABLE_VACCINE, new String[] {
						IcareSQLiteHelper.COL_ID, IcareSQLiteHelper.COL_NAME,
						IcareSQLiteHelper.COL_DETAILS, IcareSQLiteHelper.COL_DATE,
						IcareSQLiteHelper.COL_TIME, },

				IcareSQLiteHelper.COL_DATE + " <= '" + mCurrentDate + "' ", null, null,
						null, null);

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
				String mDate = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_DATE));
				String mTime = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_TIME));

				vaccine_list
						.add(new Vaccine(mId, mName, mDetails, mDate, mTime));

				cursor.moveToNext();
			}
		}
		cursor.close();
		this.mIcareDatabase.close();

		return vaccine_list;
	}

	// Delete data form database.
	public boolean deleteData(String eId) {
		this.open();
		try {
			mIcareDatabase.delete(IcareSQLiteHelper.TABLE_VACCINE,
					IcareSQLiteHelper.COL_ID + "=" + eId, null);
		} catch (Exception ex) {
			Log.e("ERROR", "data insertion problem");
			return false;
		}
		this.close();
		return true;
	}

}
