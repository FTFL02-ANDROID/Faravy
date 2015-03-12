package com.faravy.modelclass;

public class Doctor {
	String mId;
	String mName;
	String mDetails;
	String mAppoinment;
	String mPhone;
	String mEmail;

	public Doctor(String mId, String mName, String mDetails,
			String mAppoinment, String mPhone, String mEmail) {
		super();
		this.mId = mId;
		this.mName = mName;
		this.mDetails = mDetails;
		this.mAppoinment = mAppoinment;
		this.mPhone = mPhone;
		this.mEmail = mEmail;
	}

	public Doctor(String mName, String mDetails, String mAppoinment,
			String mPhone, String mEmail) {
		super();
		this.mName = mName;
		this.mDetails = mDetails;
		this.mAppoinment = mAppoinment;
		this.mPhone = mPhone;
		this.mEmail = mEmail;
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

	public String getmAppoinment() {
		return mAppoinment;
	}

	public void setmAppoinment(String mAppoinment) {
		this.mAppoinment = mAppoinment;
	}

	public String getmPhone() {
		return mPhone;
	}

	public void setmPhone(String mPhone) {
		this.mPhone = mPhone;
	}

	public String getmEmail() {
		return mEmail;
	}

	public void setmEmail(String mEmail) {
		this.mEmail = mEmail;
	}

}
