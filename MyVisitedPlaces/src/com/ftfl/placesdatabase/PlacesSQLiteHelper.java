package com.ftfl.placesdatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PlacesSQLiteHelper extends SQLiteOpenHelper {
	
	public static final String TABLE_PLACES = "places_info";
	public static final String COL_ID = "id";
	public static final String COL_NAME = "name";
	public static final String COL_PURPOSE = "purpose";
	public static final String COL_ADDRESS = "address";
	public static final String COL_LATTITUDE = "latitude";
	public static final String COL_LONGITUDE = "longitude";
	public static final String COL_START_DAY = "start_day";
	public static final String COL_END_DAY = "end_day";
	public static final String COL_NOTES = "notes";
	public static final String COL_PHOTO = "photo";


	private static final String DATABASE_NAME = "VisitedPlaces.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE_CHART = "create table "
			+ TABLE_PLACES + "( " + COL_ID
			+ " integer primary key autoincrement, " + " "
			+ COL_NAME + " text not null," + " "
			+ COL_PURPOSE + " text not null," + " "
			+ COL_ADDRESS + " text not null," + " "
			+ COL_LATTITUDE + " text not null," + " "
			+ COL_LONGITUDE + " text not null," + " "
			+ COL_START_DAY + " text not null," + " "
			+ COL_END_DAY + " text not null," + " "
			+ COL_NOTES + " text not null," + " "
			+ COL_PHOTO + " text not null);";
			

	 // Create Database
	public PlacesSQLiteHelper(Context context) {
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
		Log.w(PlacesSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
		onCreate(db);
	}

}
