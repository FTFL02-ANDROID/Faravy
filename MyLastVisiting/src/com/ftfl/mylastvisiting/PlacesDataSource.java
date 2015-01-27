package com.ftfl.mylastvisiting;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class PlacesDataSource {

	// Database fields
	private SQLiteDatabase placesDatabase;
	private PlacesSQLiteHelper placesDbHelper;
	List<Places> placesList = new ArrayList<Places>();

	public PlacesDataSource(Context context) {
		placesDbHelper = new PlacesSQLiteHelper(context);
	}

	/*
	 * open a method for writable database
	 */
	public void open() throws SQLException {
		placesDatabase = placesDbHelper.getWritableDatabase();
	}

	/*
	 * close database connection
	 */
	public void close() {
		placesDbHelper.close();
	}

	/*
	 * insert data into the database.
	 */

	public boolean insert(Places insertPlace) {

		this.open();

		ContentValues cv = new ContentValues();

		cv.put(PlacesSQLiteHelper.COL_NAME, insertPlace.getmName());
		cv.put(PlacesSQLiteHelper.COL_PURPOSE, insertPlace.getmPurpose());
		cv.put(PlacesSQLiteHelper.COL_ADDRESS, insertPlace.getmAddress());
		cv.put(PlacesSQLiteHelper.COL_LATTITUDE,insertPlace.getmLatitude());
		cv.put(PlacesSQLiteHelper.COL_LONGITUDE, insertPlace.getmLongitude());
		cv.put(PlacesSQLiteHelper.COL_START_DAY, insertPlace.getmStartDay());
		cv.put(PlacesSQLiteHelper.COL_END_DAY,insertPlace.getmEndDay());
		cv.put(PlacesSQLiteHelper.COL_NOTES,insertPlace.getmNotes());


		long check = placesDatabase.insert(PlacesSQLiteHelper.TABLE_PLACES, null, cv);
			placesDatabase.close();
	
		this.close();
		if(check <0)
			return false;
		else
			return true;
	}

	// Updating database by mId
	public boolean updateData(long eId, Places updatePlace) {
		this.open();
		ContentValues cv = new ContentValues();

		cv.put(PlacesSQLiteHelper.COL_NAME, updatePlace.getmName());
		cv.put(PlacesSQLiteHelper.COL_PURPOSE, updatePlace.getmPurpose());
		cv.put(PlacesSQLiteHelper.COL_ADDRESS, updatePlace.getmAddress());
		cv.put(PlacesSQLiteHelper.COL_LATTITUDE,updatePlace.getmLatitude());
		cv.put(PlacesSQLiteHelper.COL_LONGITUDE, updatePlace.getmLongitude());
		cv.put(PlacesSQLiteHelper.COL_START_DAY, updatePlace.getmStartDay());
		cv.put(PlacesSQLiteHelper.COL_END_DAY,updatePlace.getmEndDay());
		cv.put(PlacesSQLiteHelper.COL_NOTES,updatePlace.getmNotes());

		
		int check =  placesDatabase.update(PlacesSQLiteHelper.TABLE_PLACES, cv,
					PlacesSQLiteHelper.COL_ID + "=" + eId,
					null);
			placesDatabase.close();
	
		this.close();
		if(check ==0)
			return false;
		else
			return true;
	}

	// delete data form database.
	public boolean deleteData(long eId) {
		this.open();
		try {
			placesDatabase.delete(PlacesSQLiteHelper.TABLE_PLACES,
					PlacesSQLiteHelper.COL_ID + "=" + eId,
					null);
		} catch (Exception ex) {
			Log.e("ERROR", "data insertion problem");
			return false;
		}
		this.close();
		return true;
	}

	/*
	 * using cursor for display data from database.
	 */
	public List<Places> placesData() {
		this.open();

		Cursor cursor = placesDatabase.query(PlacesSQLiteHelper.TABLE_PLACES,
				new String[] { PlacesSQLiteHelper.COL_ID,
						PlacesSQLiteHelper.COL_NAME,
						PlacesSQLiteHelper.COL_PURPOSE,
						PlacesSQLiteHelper.COL_ADDRESS,
						PlacesSQLiteHelper.COL_LATTITUDE,
						PlacesSQLiteHelper.COL_LONGITUDE,
						PlacesSQLiteHelper.COL_START_DAY,
						PlacesSQLiteHelper.COL_END_DAY,
						PlacesSQLiteHelper.COL_NOTES}, null,
				null, null, null, null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {

				do {

					String mId = cursor
							.getString(cursor.getColumnIndex(PlacesSQLiteHelper.COL_ID));					
					String mName = cursor.getString(cursor
							.getColumnIndex(PlacesSQLiteHelper.COL_NAME));
					String mPurpose = cursor
							.getString(cursor.getColumnIndex(PlacesSQLiteHelper.COL_PURPOSE));
					String mAddress = cursor.getString(cursor
							.getColumnIndex(PlacesSQLiteHelper.COL_ADDRESS));
					String mLatitude = cursor.getString(cursor
							.getColumnIndex(PlacesSQLiteHelper.COL_LATTITUDE));
					String mLongitude = cursor.getString(cursor
							.getColumnIndex(PlacesSQLiteHelper.COL_LONGITUDE));
					String mStartDay = cursor.getString(cursor
							.getColumnIndex(PlacesSQLiteHelper.COL_START_DAY));
					String mEndDay = cursor.getString(cursor
							.getColumnIndex(PlacesSQLiteHelper.COL_END_DAY));
					String mNotes = cursor.getString(cursor
							.getColumnIndex(PlacesSQLiteHelper.COL_NOTES));
					
					placesList.add(new Places(mId, mName, mPurpose, mAddress, mLatitude, mLongitude, mStartDay, mEndDay, mNotes));
				} while (cursor.moveToNext());
			}
			cursor.close();
		}
		this.close();
		return placesList;
	}

	/*
	 * create a profile of ICareProfile. Here the data of the database according
	 * to the given mId is set to the profile and return a profile.
	 */
	public Places singlePlaceData(long mActivityId ) {
		this.open();
		Places informationObject;
		String mId;
		String mName;
		String mPurpose;
		String mAddress;
		String mLatitude;
		String mLongitude;
		String mStartDay;
		String mEndDay;	
		String mNotes;
	

		Cursor mUpdateCursor = placesDatabase.query(
				PlacesSQLiteHelper.TABLE_PLACES, new String[] {
						PlacesSQLiteHelper.COL_ID,
						PlacesSQLiteHelper.COL_NAME,
						PlacesSQLiteHelper.COL_PURPOSE,
						PlacesSQLiteHelper.COL_ADDRESS,
						PlacesSQLiteHelper.COL_LATTITUDE,
						PlacesSQLiteHelper.COL_LONGITUDE,
						PlacesSQLiteHelper.COL_START_DAY,
						PlacesSQLiteHelper.COL_END_DAY,
						PlacesSQLiteHelper.COL_NOTES, },
				PlacesSQLiteHelper.COL_ID + "=" + mActivityId, null,
				null, null, null);

		mUpdateCursor.moveToFirst();

		mId = mUpdateCursor.getString(mUpdateCursor.getColumnIndex(PlacesSQLiteHelper.COL_ID));
		mName = mUpdateCursor.getString(mUpdateCursor.getColumnIndex(PlacesSQLiteHelper.COL_NAME));
		mPurpose = mUpdateCursor.getString(mUpdateCursor.getColumnIndex(PlacesSQLiteHelper.COL_PURPOSE));
		mAddress = mUpdateCursor.getString(mUpdateCursor.getColumnIndex(PlacesSQLiteHelper.COL_ADDRESS));
		mLatitude = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(PlacesSQLiteHelper.COL_LATTITUDE));
		mLongitude = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(PlacesSQLiteHelper.COL_LONGITUDE));
		mStartDay = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(PlacesSQLiteHelper.COL_START_DAY));
		mEndDay = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(PlacesSQLiteHelper.COL_END_DAY));
		mNotes = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(PlacesSQLiteHelper.COL_NOTES));
		mUpdateCursor.close();
		informationObject = new Places(mId, mName, mPurpose, mAddress, mLatitude, mLongitude, mStartDay, mEndDay, mNotes);
		this.close();
		return informationObject;
	}
	
	public boolean isEmpty(){
		this.open();
		Cursor mCursor = placesDatabase.query(PlacesSQLiteHelper.TABLE_PLACES,
				new String[] { PlacesSQLiteHelper.COL_ID,
						PlacesSQLiteHelper.COL_NAME,
						PlacesSQLiteHelper.COL_PURPOSE,
						PlacesSQLiteHelper.COL_ADDRESS,
						PlacesSQLiteHelper.COL_LATTITUDE,
						PlacesSQLiteHelper.COL_LONGITUDE,
						PlacesSQLiteHelper.COL_START_DAY,
						PlacesSQLiteHelper.COL_END_DAY,
						PlacesSQLiteHelper.COL_NOTES}, null,
				null, null, null, null);
        if(mCursor.getCount() == 0) {
        	this.close();
        	return true;
        }
        
        else
        {
        	this.close();
        	return false;
        }
    }

}
