package com.faravy.modelclass;

public class Diet {
	String mId;
	String mName;
	String mManu;
	String mDate;
	String mTime;

	public Diet(String mId, String mName, String mManu, String mDate,
			String mTime) {
		super();
		this.mId = mId;
		this.mName = mName;
		this.mManu = mManu;
		this.mDate = mDate;
		this.mTime = mTime;
	}

	public Diet(String mName, String mManu, String mDate, String mTime) {
		super();
		this.mName = mName;
		this.mManu = mManu;
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

	public String getmManu() {
		return mManu;
	}

	public void setmManu(String mManu) {
		this.mManu = mManu;
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
