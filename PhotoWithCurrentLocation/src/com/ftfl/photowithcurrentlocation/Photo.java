package com.ftfl.photowithcurrentlocation;

public class Photo {
	String mId = "";
	double mLatitude;
	double mLongitude;
	String mDescription = "";
	String mImage = "";
	String mDate= "";
	String mTime = "";
	
	
	public Photo(String mId, double mLatitude, double mLongitude,
			String mDescription, String mImage, String mDate, String mTime) {
		super();
		this.mId = mId;
		this.mLatitude = mLatitude;
		this.mLongitude = mLongitude;
		this.mDescription = mDescription;
		this.mImage = mImage;
		this.mDate = mDate;
		this.mTime = mTime;
	}
	
	public Photo(double mLatitude, double mLongitude, String mDescription,
			String mImage, String mDate, String mTime) {
		super();
		this.mLatitude = mLatitude;
		this.mLongitude = mLongitude;
		this.mDescription = mDescription;
		this.mImage = mImage;
		this.mDate = mDate;
		this.mTime = mTime;
	}
	
	public Photo() {
		super();
	}
	
	public String getmId() {
		return mId;
	}
	public void setmId(String mId) {
		this.mId = mId;
	}
	public double getmLatitude() {
		return mLatitude;
	}
	public void setmLatitude(double mLatitude) {
		this.mLatitude = mLatitude;
	}
	public double getmLongitude() {
		return mLongitude;
	}
	public void setmLongitude(double mLongitude) {
		this.mLongitude = mLongitude;
	}
	public String getmDescription() {
		return mDescription;
	}
	public void setmDescription(String mDescription) {
		this.mDescription = mDescription;
	}
	public String getmImage() {
		return mImage;
	}
	public void setmImage(String mImage) {
		this.mImage = mImage;
	}
	public String getmDate() {
		return mDate;
	}
	public void setmDate(String mDate) {
		this.mDate = mDate;
	}
	public String getmTime() {
		return mTime;
	}
	public void setmTime(String mTime) {
		this.mTime = mTime;
	}	

}
