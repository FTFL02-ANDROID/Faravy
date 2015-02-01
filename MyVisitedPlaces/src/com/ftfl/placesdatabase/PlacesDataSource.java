package com.ftfl.placesdatabase;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.ftfl.modelclass.VisitedPlace;


public class PlacesDataSource {

	// Database fields
	private SQLiteDatabase mPlacesDatabase;
	private PlacesSQLiteHelper mPlacesDbHelper;
	List<VisitedPlace> mPlacesList = new ArrayList<VisitedPlace>();

	public PlacesDataSource(Context context) {
		mPlacesDbHelper = new PlacesSQLiteHelper(context);
	}

	/*
	 * open a method for writable database
	 */
	public void open() throws SQLException {
		mPlacesDatabase = mPlacesDbHelper.getWritableDatabase();
	}

	/*
	 * close database connection
	 */
	public void close() {
		mPlacesDbHelper.close();
	}

	/*
	 * insert data into the database.
	 */

	public boolean insert(VisitedPlace insertPlace) {

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
		cv.put(PlacesSQLiteHelper.COL_PHOTO,insertPlace.getmImage());


		long check = mPlacesDatabase.insert(PlacesSQLiteHelper.TABLE_PLACES, null, cv);
			         mPlacesDatabase.close();
	
		this.close();
		if(check <0)
			return false;
		else
			return true;
	}

	// Updating database by mId
	public boolean updateData(long eId, VisitedPlace updatePlace) {
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
		cv.put(PlacesSQLiteHelper.COL_PHOTO,updatePlace.getmImage());

		
		int check =  mPlacesDatabase.update(PlacesSQLiteHelper.TABLE_PLACES, cv,
					 PlacesSQLiteHelper.COL_ID + "=" + eId,null);
		             mPlacesDatabase.close();
	
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
			mPlacesDatabase.delete(PlacesSQLiteHelper.TABLE_PLACES,
					PlacesSQLiteHelper.COL_ID + "=" + eId,null);
		}   
		catch (Exception ex) {
			Log.e("ERROR", "data insertion problem");
			return false;
		}
		this.close();
		return true;
	}

	/*
	 * using cursor for display data from database.
	 */
	public List<VisitedPlace> placesData() {
		this.open();

		Cursor cursor = mPlacesDatabase.query(PlacesSQLiteHelper.TABLE_PLACES,
		new String[] {  
				        PlacesSQLiteHelper.COL_ID,
						PlacesSQLiteHelper.COL_NAME,
						PlacesSQLiteHelper.COL_PURPOSE,
						PlacesSQLiteHelper.COL_ADDRESS,
						PlacesSQLiteHelper.COL_LATTITUDE,
						PlacesSQLiteHelper.COL_LONGITUDE,
						PlacesSQLiteHelper.COL_START_DAY,
						PlacesSQLiteHelper.COL_END_DAY,
						PlacesSQLiteHelper.COL_NOTES,
						PlacesSQLiteHelper.COL_PHOTO}, 
						null,null, null, null, null);

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
					double mLatitude = cursor.getDouble(cursor
							.getColumnIndex(PlacesSQLiteHelper.COL_LATTITUDE));
					double mLongitude = cursor.getDouble(cursor
							.getColumnIndex(PlacesSQLiteHelper.COL_LONGITUDE));
					String mStartDay = cursor.getString(cursor
							.getColumnIndex(PlacesSQLiteHelper.COL_START_DAY));
					String mEndDay = cursor.getString(cursor
							.getColumnIndex(PlacesSQLiteHelper.COL_END_DAY));
					String mNotes = cursor.getString(cursor
							.getColumnIndex(PlacesSQLiteHelper.COL_NOTES));
					String mImage = cursor.getString(cursor
							.getColumnIndex(PlacesSQLiteHelper.COL_PHOTO));
					
					mPlacesList.add(new VisitedPlace(mId, mName, mPurpose, mAddress, mLatitude, mLongitude, mStartDay, mEndDay, mNotes, mImage));
				} while (cursor.moveToNext());
			}
			cursor.close();
		}
		this.close();
		return mPlacesList;
	}

	/*
	 * create a profile of ICareProfile. Here the data of the database according
	 * to the given mId is set to the profile and return a profile.
	 */
	public VisitedPlace singlePlaceData(long mActivityId ) {
		this.open();
		VisitedPlace informationObject;
		String mId;
		String mName;
		String mPurpose;
		String mAddress;
		double mLatitude;
		double mLongitude;
		String mStartDay;
		String mEndDay;	
		String mNotes;
		String mImage;
	

		Cursor mUpdateCursor = mPlacesDatabase.query(PlacesSQLiteHelper.TABLE_PLACES, 
		  new String[] {
						PlacesSQLiteHelper.COL_ID,
						PlacesSQLiteHelper.COL_NAME,
						PlacesSQLiteHelper.COL_PURPOSE,
						PlacesSQLiteHelper.COL_ADDRESS,
						PlacesSQLiteHelper.COL_LATTITUDE,
						PlacesSQLiteHelper.COL_LONGITUDE,
						PlacesSQLiteHelper.COL_START_DAY,
						PlacesSQLiteHelper.COL_END_DAY,
						PlacesSQLiteHelper.COL_NOTES,
						PlacesSQLiteHelper.COL_PHOTO},
				       
		 PlacesSQLiteHelper.COL_ID + "=" + mActivityId, null,null, null, null);

		mUpdateCursor.moveToFirst();

		mId = mUpdateCursor.getString(mUpdateCursor.
				getColumnIndex(PlacesSQLiteHelper.COL_ID));
		mName = mUpdateCursor.getString(mUpdateCursor.
				getColumnIndex(PlacesSQLiteHelper.COL_NAME));
		mPurpose = mUpdateCursor.getString(mUpdateCursor.
				getColumnIndex(PlacesSQLiteHelper.COL_PURPOSE));
		mAddress = mUpdateCursor.getString(mUpdateCursor.
				getColumnIndex(PlacesSQLiteHelper.COL_ADDRESS));
		mLatitude = mUpdateCursor.getDouble(mUpdateCursor
				.getColumnIndex(PlacesSQLiteHelper.COL_LATTITUDE));
		mLongitude = mUpdateCursor.getDouble(mUpdateCursor
				.getColumnIndex(PlacesSQLiteHelper.COL_LONGITUDE));
		mStartDay = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(PlacesSQLiteHelper.COL_START_DAY));
		mEndDay = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(PlacesSQLiteHelper.COL_END_DAY));
		mNotes = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(PlacesSQLiteHelper.COL_NOTES));
		mImage = mUpdateCursor.getString(mUpdateCursor
				.getColumnIndex(PlacesSQLiteHelper.COL_PHOTO));
		mUpdateCursor.close();
		informationObject = new VisitedPlace(mId, mName, mPurpose, mAddress, mLatitude, mLongitude, mStartDay, mEndDay, mNotes, mImage);
		this.close();
		return informationObject;
	}
	
	public boolean isEmpty(){
		this.open();
		Cursor mCursor = mPlacesDatabase.query(PlacesSQLiteHelper.TABLE_PLACES,
		  new String[] { 
				        PlacesSQLiteHelper.COL_ID,
						PlacesSQLiteHelper.COL_NAME,
						PlacesSQLiteHelper.COL_PURPOSE,
						PlacesSQLiteHelper.COL_ADDRESS,
						PlacesSQLiteHelper.COL_LATTITUDE,
						PlacesSQLiteHelper.COL_LONGITUDE,
						PlacesSQLiteHelper.COL_START_DAY,
						PlacesSQLiteHelper.COL_END_DAY,
						PlacesSQLiteHelper.COL_NOTES,
						PlacesSQLiteHelper.COL_PHOTO},
						null,null, null, null, null);
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
