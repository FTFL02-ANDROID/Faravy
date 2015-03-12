package com.faravy.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.faravy.modelclass.MedicalHistory;

public class MedicalHistoryDataSource {

	private SQLiteDatabase mIcareDatabase;
	private IcareSQLiteHelper mIcareDbHelper;
	MedicalHistory mHistory;

	public MedicalHistoryDataSource(Context context) {
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

	public long insertData(MedicalHistory insertHistory) {

		this.open();

		ContentValues cv = new ContentValues();
		cv.put(IcareSQLiteHelper.COL_DOCTOR_NAME,
				insertHistory.getmDoctorName());
		cv.put(IcareSQLiteHelper.COL_DETAILS, insertHistory.getmDetails());
		cv.put(IcareSQLiteHelper.COL_DATE, insertHistory.getmDate());
		cv.put(IcareSQLiteHelper.COL_PHOTO, insertHistory.getmPhoto());

		long check = mIcareDatabase.insert(
				IcareSQLiteHelper.TABLE_MEDICAL_HISTORY, null, cv);
		mIcareDatabase.close();

		this.close();
		return check;

	}

	public ArrayList<MedicalHistory> getAllHistory() {

		ArrayList<MedicalHistory> history_list = new ArrayList<MedicalHistory>();
		this.open();

		Cursor cursor = mIcareDatabase.query(
				IcareSQLiteHelper.TABLE_MEDICAL_HISTORY, null, null, null,
				null, null, null);

		// looping through all rows and adding to list
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {

				String mId = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_ID));
				String mDoctorName = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_DOCTOR_NAME));
				String mDetails = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_DETAILS));
				String mDate = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_DATE));
				String mPhoto = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_PHOTO));

				history_list.add(new MedicalHistory(mId, mDoctorName, mDetails,
						mDate, mPhoto));

				cursor.moveToNext();
			}
		}
		cursor.close();
		mIcareDatabase.close();

		return history_list;
	}

	public MedicalHistory singleHistory(String id) {
		this.open();
		MedicalHistory medicalHistory;

		Cursor cursor = mIcareDatabase.query(
				IcareSQLiteHelper.TABLE_MEDICAL_HISTORY, new String[] {
						IcareSQLiteHelper.COL_ID,
						IcareSQLiteHelper.COL_DOCTOR_NAME,
						IcareSQLiteHelper.COL_DETAILS,
						IcareSQLiteHelper.COL_DATE,
						IcareSQLiteHelper.COL_PHOTO, },

				IcareSQLiteHelper.COL_ID + "=" + id, null, null, null, null);

		cursor.moveToFirst();

		String mId = cursor.getString(cursor
				.getColumnIndex(IcareSQLiteHelper.COL_ID));
		String mDoctorName = cursor.getString(cursor
				.getColumnIndex(IcareSQLiteHelper.COL_DOCTOR_NAME));
		String mDetails = cursor.getString(cursor
				.getColumnIndex(IcareSQLiteHelper.COL_DETAILS));
		String mDate = cursor.getString(cursor
				.getColumnIndex(IcareSQLiteHelper.COL_DATE));
		String mPhoto = cursor.getString(cursor
				.getColumnIndex(IcareSQLiteHelper.COL_PHOTO));

		cursor.close();
		medicalHistory = new MedicalHistory(mId, mDoctorName, mDetails,
				mDate, mPhoto);
		this.close();
		return medicalHistory;
	}

	// Delete data form database.
	public boolean deleteData(String eId) {
		this.open();
		try {
			mIcareDatabase.delete(IcareSQLiteHelper.TABLE_MEDICAL_HISTORY,
					IcareSQLiteHelper.COL_ID + "=" + eId, null);
		} catch (Exception ex) {
			Log.e("ERROR", "data insertion problem");
			return false;
		}
		this.close();
		return true;
	}

}
