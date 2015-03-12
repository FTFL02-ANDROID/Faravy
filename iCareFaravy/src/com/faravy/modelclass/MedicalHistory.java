package com.faravy.modelclass;

public class MedicalHistory {
	String mId;
	String mDoctorName;
	String mDetails;
	String mDate;
	String mPhoto;

	public MedicalHistory(String mId, String mDoctorName, String mDetails,
			String mDate, String mPhoto) {
		super();
		this.mId = mId;
		this.mDoctorName = mDoctorName;
		this.mDetails = mDetails;
		this.mDate = mDate;
		this.mPhoto = mPhoto;
	}

	public MedicalHistory(String mDoctorName, String mDetails, String mDate,
			String mPhoto) {
		super();
		this.mDoctorName = mDoctorName;
		this.mDetails = mDetails;
		this.mDate = mDate;
		this.mPhoto = mPhoto;
	}

	public String getmId() {
		return mId;
	}

	public void setmId(String mId) {
		this.mId = mId;
	}

	public String getmDoctorName() {
		return mDoctorName;
	}

	public void setmDoctorName(String mDoctorName) {
		this.mDoctorName = mDoctorName;
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

	public String getmPhoto() {
		return mPhoto;
	}

	public void setmPhoto(String mPhoto) {
		this.mPhoto = mPhoto;
	}

}
