package com.ftfl.modelclass;

public class VisitedPlace {

	String mId = "";
	String mName = "";
	String mPurpose= "";
	String mAddress = "";	
	double mLatitude;
	double mLongitude;
	String mStartDay = "";
	String mEndDay = "";
	String mNotes = "";
	String mImage = "";
	
	
	public VisitedPlace(String mId, String mName, String mPurpose, String mAddress,
			double mLatitude, double mLongitude, String mStartDay,
			String mEndDay, String mNotes, String mImage) {
		super();
		this.mId = mId;
		this.mName = mName;
		this.mPurpose = mPurpose;
		this.mAddress = mAddress;
		this.mLatitude = mLatitude;
		this.mLongitude = mLongitude;
		this.mStartDay = mStartDay;
		this.mEndDay = mEndDay;
		this.mNotes = mNotes;
		this.mImage = mImage;
	}
	
	


	public VisitedPlace(String mName, String mPurpose, String mAddress,
			double mLatitude, double mLongitude, String mStartDay,
			String mEndDay, String mNotes, String mImage) {
		super();
		this.mName = mName;
		this.mPurpose = mPurpose;
		this.mAddress = mAddress;
		this.mLatitude = mLatitude;
		this.mLongitude = mLongitude;
		this.mStartDay = mStartDay;
		this.mEndDay = mEndDay;
		this.mNotes = mNotes;
		this.mImage = mImage;
	}




	public VisitedPlace() {
		super();
	}


	public String getmId() {
		return mId;
	}


	public void setmId(String mId) {
		this.mId = mId;
	}


	public String getmName() {
		return mName;
	}


	public void setmName(String mName) {
		this.mName = mName;
	}


	public String getmPurpose() {
		return mPurpose;
	}


	public void setmPurpose(String mPurpose) {
		this.mPurpose = mPurpose;
	}


	public String getmAddress() {
		return mAddress;
	}


	public void setmAddress(String mAddress) {
		this.mAddress = mAddress;
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


	public String getmStartDay() {
		return mStartDay;
	}


	public void setmStartDay(String mStartDay) {
		this.mStartDay = mStartDay;
	}


	public String getmEndDay() {
		return mEndDay;
	}


	public void setmEndDay(String mEndDay) {
		this.mEndDay = mEndDay;
	}


	public String getmNotes() {
		return mNotes;
	}


	public void setmNotes(String mNotes) {
		this.mNotes = mNotes;
	}


	public String getmImage() {
		return mImage;
	}


	public void setmImage(String mImage) {
		this.mImage = mImage;
	}
	
}
