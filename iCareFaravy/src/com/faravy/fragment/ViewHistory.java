package com.faravy.fragment;

import com.faravy.database.MedicalHistoryDataSource;
import com.faravy.icare.R;
import com.faravy.modelclass.MedicalHistory;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHistory extends Fragment{
	MedicalHistory mHistory;
	MedicalHistoryDataSource mDataSource;
	ImageView mPresCription;
	TextView mDoctorName;
	TextView mDetails;
	TextView mDate;
	
	public ViewHistory(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_history,
				container, false);
		mPresCription=(ImageView)rootView.findViewById(R.id.showPrescription);
		mDoctorName=(TextView)rootView.findViewById(R.id.showDoctorName);
		mDetails=(TextView)rootView.findViewById(R.id.showDetails);
		mDate=(TextView)rootView.findViewById(R.id.showDate);
		mDataSource = new MedicalHistoryDataSource(getActivity());
		
		
		return rootView;
	}
  
}
