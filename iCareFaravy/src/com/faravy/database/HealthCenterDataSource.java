package com.faravy.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.faravy.modelclass.HealthCenter;

public class HealthCenterDataSource {

	private SQLiteDatabase mIcareDatabase;
	private IcareSQLiteHelper mIcareDbHelper;
	HealthCenter mHealthCenter;

	public HealthCenterDataSource(Context context) {
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

	public long insertData(HealthCenter insertCenter) {

		this.open();

		ContentValues cv = new ContentValues();
		cv.put(IcareSQLiteHelper.COL_NAME, insertCenter.getmName());
		cv.put(IcareSQLiteHelper.COL_ADDRESS, insertCenter.getmAddress());
		cv.put(IcareSQLiteHelper.COL_LATTITUDE, insertCenter.getmLatitude());
		cv.put(IcareSQLiteHelper.COL_LONGITUDE, insertCenter.getmLongitude());

		long check = mIcareDatabase.insert(
				IcareSQLiteHelper.TABLE_HEALTH_CENTER, null, cv);
		mIcareDatabase.close();

		this.close();
		return check;

	}

	public HealthCenter singleCenter(String id) {
		this.open();
		HealthCenter healthCenter;

		Cursor cursor = mIcareDatabase.query(
				IcareSQLiteHelper.TABLE_HEALTH_CENTER, new String[] {
						IcareSQLiteHelper.COL_ID, IcareSQLiteHelper.COL_NAME,
						IcareSQLiteHelper.COL_ADDRESS,
						IcareSQLiteHelper.COL_LATTITUDE,
						IcareSQLiteHelper.COL_LONGITUDE, },

				IcareSQLiteHelper.COL_ID + "=" + id, null, null, null, null);

		cursor.moveToFirst();

		String mId = cursor.getString(cursor
				.getColumnIndex(IcareSQLiteHelper.COL_ID));
		String mName = cursor.getString(cursor
				.getColumnIndex(IcareSQLiteHelper.COL_NAME));
		String mAddress = cursor.getString(cursor
				.getColumnIndex(IcareSQLiteHelper.COL_ADDRESS));
		String mLatitude = cursor.getString(cursor
				.getColumnIndex(IcareSQLiteHelper.COL_LATTITUDE));
		String mLongitude = cursor.getString(cursor
				.getColumnIndex(IcareSQLiteHelper.COL_LONGITUDE));

		cursor.close();
		healthCenter = new HealthCenter(mId, mName, mAddress, mLatitude,
				mLongitude);
		this.close();
		return healthCenter;
	}

	public ArrayList<HealthCenter> getAllCenter() {

		ArrayList<HealthCenter> center_list = new ArrayList<HealthCenter>();
		this.open();

		Cursor cursor = mIcareDatabase.query(
				IcareSQLiteHelper.TABLE_HEALTH_CENTER, null, null, null, null,
				null, null);

		// looping through all rows and adding to list
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				String mId = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_ID));
				String mName = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_NAME));
				String mAddress = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_ADDRESS));
				String mLatitude = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_LATTITUDE));
				String mLongitude = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_LONGITUDE));

				center_list.add(new HealthCenter(mId, mName, mAddress,
						mLatitude, mLongitude));

				cursor.moveToNext();
			}
		}
		cursor.close();
		mIcareDatabase.close();

		return center_list;
	}

	// Delete data form database.
	public boolean deleteData(long eId) {
		this.open();
		try {
			mIcareDatabase.delete(IcareSQLiteHelper.TABLE_PROFILE,
					IcareSQLiteHelper.COL_ID + "=" + eId, null);
		} catch (Exception ex) {
			Log.e("ERROR", "data insertion problem");
			return false;
		}
		this.close();
		return true;
	}

}
