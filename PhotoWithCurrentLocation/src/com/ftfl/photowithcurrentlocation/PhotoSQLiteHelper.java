package com.ftfl.photowithcurrentlocation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PhotoSQLiteHelper extends SQLiteOpenHelper{
	
	public static final String TABLE_NAME = "photo_info";
	public static final String COL_ID = "id";
	public static final String COL_LATTITUDE = "latitude";
	public static final String COL_LONGITUDE = "longitude";
	public static final String COL_DESSCRIPTION = "remarks";
	public static final String COL_PHOTO = "photo";
	public static final String COL_DATE = "date";
	public static final String COL_TIME = "time";	
	
	
	private static final String DATABASE_NAME = "Photo.db";
	private static final int DATABASE_VERSION = 1;
	
	// Database creation sql statement
		private static final String DATABASE_CREATE_CHART = "create table "
				+ TABLE_NAME + "( " + COL_ID
				+ " integer primary key autoincrement, " + " "
				+ COL_LATTITUDE + " text not null," + " "
				+ COL_LONGITUDE + " text not null," + " "
				+ COL_DESSCRIPTION + " text not null," + " "
				+ COL_PHOTO+ " text not null," + " "
				+ COL_DATE+ " text not null," + " "
				+ COL_TIME + " text not null);";

		
		 // Create Database
		public PhotoSQLiteHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		// Creating Tables
		@Override
		public void onCreate(SQLiteDatabase database) {
			database.execSQL(DATABASE_CREATE_CHART);

		}

		// Upgrading database
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(PhotoSQLiteHelper.class.getName(),
					"Upgrading database from version " + oldVersion + " to "
							+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}

	}


	

	
