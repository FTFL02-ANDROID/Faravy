package com.ftfl.mylastvisiting;

public class Places {

	String mId = "";
	String mName = "";
	String mPurpose="";
	String mAddress = "";	
	String mLatitude = "";
	String mLongitude = "";
	String mStartDay = "";
	String mEndDay = "";
	String mNotes = "";

	

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



	public String getmNotes() {
		return mNotes;
	}



	public void setmNotes(String mNotes) {
		this.mNotes = mNotes;
	}



	public String getmLatitude() {
		return mLatitude;
	}



	public void setmLatitude(String mLatitude) {
		this.mLatitude = mLatitude;
	}



	public String getmLongitude() {
		return mLongitude;
	}



	public void setmLongitude(String mLongitude) {
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



	public Places(String mId, String mName, String mPurpose, String mAddress,
			String mLatitude, String mLongitude, String mStartDay,
			String mEndDay, String mNotes) {
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
	}



	public Places() {
		super();
	}



	


	

}
