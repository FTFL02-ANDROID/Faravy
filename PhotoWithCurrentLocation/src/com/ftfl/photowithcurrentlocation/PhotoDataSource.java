package com.ftfl.photowithcurrentlocation;

import java.util.ArrayList;
import java.util.List;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class PhotoDataSource {
	// Database fields
		private SQLiteDatabase mPhotoDatabase;
		private PhotoSQLiteHelper mPhotoDbHelper;
		List<Photo> mPhotoList = new ArrayList<Photo>();

		public PhotoDataSource(Context context) {
			mPhotoDbHelper = new PhotoSQLiteHelper(context);
		}
		
		/*
		 * open a method for writable database
		 */
		public void open() throws SQLException {
			mPhotoDatabase = mPhotoDbHelper.getWritableDatabase();
		}

		/*
		 * close database connection
		 */
		public void close() {
			mPhotoDbHelper.close();
		}
		/*
		 * insert data into the database.
		 */

		public boolean insert(Photo insertPhoto) {

			this.open();

			ContentValues cv = new ContentValues();
			
			cv.put(PhotoSQLiteHelper.COL_LATTITUDE,insertPhoto.getmLatitude());
			cv.put(PhotoSQLiteHelper.COL_LONGITUDE, insertPhoto.getmLongitude());
			cv.put(PhotoSQLiteHelper.COL_DESSCRIPTION, insertPhoto.getmDescription());
			cv.put(PhotoSQLiteHelper.COL_PHOTO,insertPhoto.getmImage());
			cv.put(PhotoSQLiteHelper.COL_DATE,insertPhoto.getmDate());
			cv.put(PhotoSQLiteHelper.COL_TIME,insertPhoto.getmTime());
			


			long check = mPhotoDatabase.insert(PhotoSQLiteHelper.TABLE_NAME, null, cv);
				         mPhotoDatabase.close();
		
			this.close();
			if(check <0)
				return false;
			else
				return true;
		}
		
		// Updating database by mId
		public boolean updateData(long eId, Photo upDatePhoto) {
			this.open();
			ContentValues cv = new ContentValues();

			cv.put(PhotoSQLiteHelper.COL_LATTITUDE,upDatePhoto.getmLatitude());
			cv.put(PhotoSQLiteHelper.COL_LONGITUDE, upDatePhoto.getmLongitude());
			cv.put(PhotoSQLiteHelper.COL_DESSCRIPTION, upDatePhoto.getmDescription());
			cv.put(PhotoSQLiteHelper.COL_PHOTO,upDatePhoto.getmImage());
			cv.put(PhotoSQLiteHelper.COL_DATE,upDatePhoto.getmDate());
			cv.put(PhotoSQLiteHelper.COL_TIME,upDatePhoto.getmTime());
			
			
			int check =  mPhotoDatabase.update(PhotoSQLiteHelper.TABLE_NAME, cv,
						 PhotoSQLiteHelper.COL_ID + "=" + eId,null);
			             mPhotoDatabase.close();
		
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
				mPhotoDatabase.delete(PhotoSQLiteHelper.TABLE_NAME,
						PhotoSQLiteHelper.COL_ID + "=" + eId,null);
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
		public List<Photo> photoData() {
			this.open();

			Cursor cursor = mPhotoDatabase.query(PhotoSQLiteHelper.TABLE_NAME,
			new String[] {  
					        PhotoSQLiteHelper.COL_ID,			
							PhotoSQLiteHelper.COL_LATTITUDE,
							PhotoSQLiteHelper.COL_LONGITUDE,
							PhotoSQLiteHelper.COL_DESSCRIPTION,
							PhotoSQLiteHelper.COL_PHOTO},
							PhotoSQLiteHelper.COL_DATE,
							null,null, null, null);

			if (cursor != null) {
				if (cursor.moveToFirst()) {

					do {

						String mId = cursor
								.getString(cursor.getColumnIndex(PhotoSQLiteHelper.COL_ID));					
						double mLatitude = cursor.getDouble(cursor
								.getColumnIndex(PhotoSQLiteHelper.COL_LATTITUDE));
						double mLongitude = cursor.getDouble(cursor
								.getColumnIndex(PhotoSQLiteHelper.COL_LONGITUDE));
						String mDescription = cursor.getString(cursor
								.getColumnIndex(PhotoSQLiteHelper.COL_DESSCRIPTION));
						String mImage = cursor.getString(cursor
								.getColumnIndex(PhotoSQLiteHelper.COL_PHOTO));
						String mDate = cursor.getString(cursor
								.getColumnIndex(PhotoSQLiteHelper.COL_DATE));
						String mTime = cursor.getString(cursor
								.getColumnIndex(PhotoSQLiteHelper.COL_TIME));
						
						
						mPhotoList.add(new Photo(mId, mLatitude, mLongitude, mDescription, mImage, mDate, mTime));
					} while (cursor.moveToNext());
				}
				cursor.close();
			}
			this.close();
			return mPhotoList;
		}
		

		/*
		 * create a profile of ICareProfile. Here the data of the database according
		 * to the given mId is set to the profile and return a profile.
		 */
		public Photo singlePhotoData(long mActivityId ) {
			this.open();
			Photo informationObject;
			String mId ;
			double mLatitude;
			double mLongitude;
			String mDescription;
			String mImage;
			String mDate;
			String mTime;
		

			Cursor mUpdateCursor = mPhotoDatabase.query(PhotoSQLiteHelper.TABLE_NAME, 
			  new String[] {
					PhotoSQLiteHelper.COL_ID,			
					PhotoSQLiteHelper.COL_LATTITUDE,
					PhotoSQLiteHelper.COL_LONGITUDE,
					PhotoSQLiteHelper.COL_DESSCRIPTION,
					PhotoSQLiteHelper.COL_PHOTO},
					PhotoSQLiteHelper.COL_DATE,
					null,null, null, null);

			mUpdateCursor.moveToFirst();

		    mId = mUpdateCursor
					.getString(mUpdateCursor.getColumnIndex(PhotoSQLiteHelper.COL_ID));					
			mLatitude = mUpdateCursor.getDouble(mUpdateCursor
					.getColumnIndex(PhotoSQLiteHelper.COL_LATTITUDE));
			mLongitude = mUpdateCursor.getDouble(mUpdateCursor
					.getColumnIndex(PhotoSQLiteHelper.COL_LONGITUDE));
			mDescription = mUpdateCursor.getString(mUpdateCursor
					.getColumnIndex(PhotoSQLiteHelper.COL_DESSCRIPTION));
			mImage = mUpdateCursor.getString(mUpdateCursor
					.getColumnIndex(PhotoSQLiteHelper.COL_PHOTO));
			mDate = mUpdateCursor.getString(mUpdateCursor
					.getColumnIndex(PhotoSQLiteHelper.COL_DATE));
			mTime = mUpdateCursor.getString(mUpdateCursor
					.getColumnIndex(PhotoSQLiteHelper.COL_TIME));
			mUpdateCursor.close();
			informationObject = new Photo(mId, mLatitude, mLongitude, mDescription, mImage, mDate, mTime);
			this.close();
			return informationObject;
		}
		
		public boolean isEmpty(){
			this.open();
			Cursor mCursor = mPhotoDatabase.query(PhotoSQLiteHelper.TABLE_NAME,
			  new String[] { 
					PhotoSQLiteHelper.COL_ID,			
					PhotoSQLiteHelper.COL_LATTITUDE,
					PhotoSQLiteHelper.COL_LONGITUDE,
					PhotoSQLiteHelper.COL_DESSCRIPTION,
					PhotoSQLiteHelper.COL_PHOTO},
					PhotoSQLiteHelper.COL_DATE,
					
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
