package com.faravy.modelclass;

public class HealthCenter {
	String mId;
	String mName;
	String mAddress;
	String mLatitude;
	String mLongitude;

	public HealthCenter(String mId, String mName, String mAddress,
			String mLatitude, String mLongitude) {
		super();
		this.mId = mId;
		this.mName = mName;
		this.mAddress = mAddress;
		this.mLatitude = mLatitude;
		this.mLongitude = mLongitude;
	}

	public HealthCenter(String mName, String mAddress, String mLatitude,
			String mLongitude) {
		super();
		this.mName = mName;
		this.mAddress = mAddress;
		this.mLatitude = mLatitude;
		this.mLongitude = mLongitude;
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

	public String getmAddress() {
		return mAddress;
	}

	public void setmAddress(String mAddress) {
		this.mAddress = mAddress;
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

}
