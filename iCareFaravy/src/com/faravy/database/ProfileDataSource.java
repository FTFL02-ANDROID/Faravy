package com.faravy.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.faravy.modelclass.Profile;

public class ProfileDataSource {

	private SQLiteDatabase mIcareDatabase;
	private IcareSQLiteHelper mIcareDbHelper;
	Profile mProfile;
	List<Profile> mProfileList = new ArrayList<Profile>();

	public ProfileDataSource(Context context) {
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

	public long insertData(Profile insertProfile) {

		this.open();

		ContentValues cv = new ContentValues();
		cv.put(IcareSQLiteHelper.COL_NAME, insertProfile.getmName());
		cv.put(IcareSQLiteHelper.COL_DOB, insertProfile.getmDOB());
		cv.put(IcareSQLiteHelper.COL_HEIGHT, insertProfile.getmHeight());
		cv.put(IcareSQLiteHelper.COL_WEIGHT, insertProfile.getmWeight());

		long check = mIcareDatabase.insert(IcareSQLiteHelper.TABLE_PROFILE,
				null, cv);
		mIcareDatabase.close();

		this.close();
		return check;

	}

	public ArrayList<Profile> getAllProfile() {

		ArrayList<Profile> profile_list = new ArrayList<Profile>();
		this.open();

		Cursor cursor = mIcareDatabase.query(IcareSQLiteHelper.TABLE_PROFILE,
				null, null, null, null, null, null);

		// looping through all rows and adding to list
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {

				String mId = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_ID));
				String mName = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_NAME));
				String mDOB = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_DOB));
				String mHeight = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_HEIGHT));
				String mWeight = cursor.getString(cursor
						.getColumnIndex(IcareSQLiteHelper.COL_WEIGHT));

				profile_list
						.add(new Profile(mId, mName, mDOB, mHeight, mWeight));

				cursor.moveToNext();
			}
		}
		cursor.close();
		mIcareDatabase.close();

		return profile_list;
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

	public boolean isEmpty() {
		this.open();
		Cursor mCursor = mIcareDatabase.query(IcareSQLiteHelper.TABLE_PROFILE,
				null, null, null, null, null, null);

		if (mCursor.getCount() == 0) {
			this.close();
			return true;
		} else {
			this.close();
			return false;
		}
	}

}
