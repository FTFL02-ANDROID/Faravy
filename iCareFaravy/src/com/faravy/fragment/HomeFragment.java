package com.faravy.fragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.faravy.adapter.ProfileAdapter;
import com.faravy.database.ProfileDataSource;
import com.faravy.icare.AddDietActivity;
import com.faravy.icare.AddDoctorActivity;
import com.faravy.icare.AddVaccineActivity;
import com.faravy.icare.EmergencyNoActivity;
import com.faravy.icare.R;
import com.faravy.modelclass.Profile;

public class HomeFragment extends Fragment {
	ListView mList = null;
	ProfileDataSource mDataSource = null;
	ProfileAdapter mAdapter = null;
	ArrayList<Profile> mPlacelist = null;
	Button mBtnCall;
	Button mBtnDiet;
	Button mBtnGallery;
	Button mBtnProfile;
	Button mBtnDoctor;
	Button mBtnVaccine;

	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);
		mList = (ListView) rootView.findViewById(R.id.addList);
		mBtnCall = (Button) rootView.findViewById(R.id.btnNumber);
		mBtnDiet = (Button) rootView.findViewById(R.id.btnDiet);
		mBtnGallery = (Button) rootView.findViewById(R.id.btnGalary);
		mBtnProfile = (Button) rootView.findViewById(R.id.btnProfile);
		mBtnVaccine = (Button) rootView.findViewById(R.id.btnVaccin);
		mBtnDoctor = (Button) rootView.findViewById(R.id.btnDoctor);
		mDataSource = new ProfileDataSource(getActivity());

		// Getting All Profile list from database
		mPlacelist = mDataSource.getAllProfile();
		mAdapter = new ProfileAdapter(getActivity(), mPlacelist);

		// Set each row in list using custom adapter
		mList.setAdapter(mAdapter);
		mBtnCall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(),
						EmergencyNoActivity.class));
			}
		});

		mBtnDiet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), AddDietActivity.class));

			}
		});
		mBtnGallery.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				GalleryFragment gallery = new GalleryFragment();
				FragmentTransaction fragTransaction = getFragmentManager()
						.beginTransaction();
				fragTransaction.replace(R.id.frame_container, gallery);

				fragTransaction.commit();

			}
		});
		mBtnProfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), " You Cant Create More Profile",
						Toast.LENGTH_LONG).show();

			}
		});
		mBtnDoctor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), AddDoctorActivity.class));

			}
		});
		mBtnVaccine.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(),
						AddVaccineActivity.class));

			}
		});

		return rootView;
	}
}
