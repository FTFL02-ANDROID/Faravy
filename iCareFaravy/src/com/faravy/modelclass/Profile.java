package com.faravy.modelclass;

public class Profile {
	String mId;
	String mName;
	String mDOB;
	String mHeight;
	String mWeight;

	public Profile(String mId, String mName, String mDOB, String mHeight,
			String mWeight) {
		super();
		this.mId = mId;
		this.mName = mName;
		this.mDOB = mDOB;
		this.mHeight = mHeight;
		this.mWeight = mWeight;
	}

	public Profile(String mName, String mDOB, String mHeight, String mWeight) {
		super();
		this.mName = mName;
		this.mDOB = mDOB;
		this.mHeight = mHeight;
		this.mWeight = mWeight;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getmDOB() {
		return mDOB;
	}

	public void setmDOB(String mDOB) {
		this.mDOB = mDOB;
	}

	public String getmHeight() {
		return mHeight;
	}

	public void setmHeight(String mHeight) {
		this.mHeight = mHeight;
	}

	public String getmWeight() {
		return mWeight;
	}

	public void setmWeight(String mWeight) {
		this.mWeight = mWeight;
	}

}
