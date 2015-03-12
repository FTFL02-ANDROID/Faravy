package com.faravy.modelclass;

public class Vaccine {
	String mId;
	String mName;
	String mDetails;
	String mDate;
	String mTime;

	public Vaccine(String mId, String mName, String mDetails, String mDate,
			String mTime) {
		super();
		this.mId = mId;
		this.mName = mName;
		this.mDetails = mDetails;
		this.mDate = mDate;
		this.mTime = mTime;
	}

	public Vaccine(String mName, String mDetails, String mDate, String mTime) {
		super();
		this.mName = mName;
		this.mDetails = mDetails;
		this.mDate = mDate;
		this.mTime = mTime;
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

	public String getmDetails() {
		return mDetails;
	}

	public void setmDetails(String mDetails) {
		this.mDetails = mDetails;
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
