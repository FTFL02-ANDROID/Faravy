package com.faravy.database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.faravy.modelclass.Diet;

public class DietDataSource {

	private SQLiteDatabase mIcareDatabase;
	private IcareSQLiteHelper mIcareDbHelper;
	Diet mDiet;
	public String mCurrentDate;
	List<String> upcomingDates = new ArrayList<String>();

	public DietDataSource(Context context) {
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

	public long insertData(Diet insertDiet) {

		this.open();

		ContentValues cv = new ContentValues();
		cv.put(IcareSQLiteHelper.COL_NAME, insertDiet.getmName());
		cv.put(IcareSQLiteHelper.COL_MANU, insertDiet.getmManu());
		cv.put(IcareSQLiteHelper.COL_DATE, insertDiet.getmDate());
		cv.put(IcareSQLiteHelper.COL_TIME, insertDiet.getmTime());

		long check = mIcareDatabase.insert(IcareSQLiteHelper.TABLE_DIET, null,
				cv);
		mIcareDatabase.close();

		this.close();
		return check;

	}

	public ArrayList<Diet> getAllDiet() {

		ArrayList<Diet> diet_list = new ArrayList<Diet>();
		this.open();
		this.cDate();

		Cursor cursor = mIcareDatabase
				.query(IcareSQLiteHelper.TABLE_DIET, new String[] {
						IcareSQLiteHelper.COL_DIET_ID, IcareSQLiteHelper.COL_NAME,
						IcareSQLiteHelper.COL_MANU, IcareSQLiteHelper.COL_DATE,
						IcareSQLiteHelper.COL_TIME, },

				IcareSQLiteHelper.COL_DATE + " = '" + mCurrentDate + "' ", null, null,
						null, null);
	

		// looping through all rows and adding to list
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {

				String mId = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_DIET_ID));
				String mName = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_NAME));
				String mManu = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_MANU));
				String mDate = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_DATE));
				String mTime = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_TIME));

				diet_list.add(new Diet(mId, mName, mManu, mDate, mTime));

				cursor.moveToNext();
			}
		}
		cursor.close();
		this.mIcareDatabase.close();

		return diet_list;
	}
	
	public ArrayList<Diet> getUpcomingDiet() {

		ArrayList<Diet> diet_list = new ArrayList<Diet>();
		this.open();
		this.cDate();

		Cursor cursor = mIcareDatabase
				.query(IcareSQLiteHelper.TABLE_DIET, new String[] {
						IcareSQLiteHelper.COL_DIET_ID, IcareSQLiteHelper.COL_NAME,
						IcareSQLiteHelper.COL_MANU, IcareSQLiteHelper.COL_DATE,
						IcareSQLiteHelper.COL_TIME, },

				IcareSQLiteHelper.COL_DATE + " > '" + mCurrentDate + "' ", null, null,
						null,null);
	

		// looping through all rows and adding to list
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {

				String mId = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_DIET_ID));
				String mName = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_NAME));
				String mManu = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_MANU));
				String mDate = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_DATE));
				String mTime = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_TIME));

				diet_list.add(new Diet(mId, mName, mManu, mDate, mTime));

				cursor.moveToNext();
			}
		}
		cursor.close();
		this.mIcareDatabase.close();

		return diet_list;
	}

	public List<String> upcomingDates() {
		this.open();
		this.cDate();

		Cursor mCursor = mIcareDatabase.rawQuery(
				"SELECT DISTINCT date FROM  diet_info  WHERE date > '"
						+ mCurrentDate + "'", null);

		if (mCursor != null) {
			if (mCursor.moveToFirst()) {

				do {
					String mActivityDate = mCursor.getString(mCursor
							.getColumnIndex(IcareSQLiteHelper.COL_DATE));
					upcomingDates.add(mActivityDate);

				} while (mCursor.moveToNext());
			}
			mCursor.close();
		}
		this.close();
		return upcomingDates;
	}

	// Delete data form database.
	public boolean deleteData(String eId) {
		this.open();
		try {
			mIcareDatabase.delete(IcareSQLiteHelper.TABLE_DIET,
					IcareSQLiteHelper.COL_DIET_ID + "=" + eId, null);
		} catch (Exception ex) {
			Log.e("ERROR", "data insertion problem");
			return false;
		}
		this.close();
		return true;
	}
	
	public Diet updateDiet (String id) {

		this.open();

		Cursor cursor = mIcareDatabase
				.query(IcareSQLiteHelper.TABLE_DIET, new String[] {
						IcareSQLiteHelper.COL_DIET_ID, IcareSQLiteHelper.COL_NAME,
						IcareSQLiteHelper.COL_MANU, IcareSQLiteHelper.COL_DATE,
						IcareSQLiteHelper.COL_TIME, },

				IcareSQLiteHelper.COL_DIET_ID + " = " + id, null, null,
						null, null);
	

		// looping through all rows and adding to list
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {

				String mId = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_DIET_ID));
				String mName = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_NAME));
				String mManu = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_MANU));
				String mDate = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_DATE));
				String mTime = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_TIME));

				mDiet=(new Diet(mId, mName, mManu, mDate, mTime));

			
			}
		}
		cursor.close();
		this.mIcareDatabase.close();

		return mDiet;
	}

}
