package com.faravy.fragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.faravy.adapter.CareCenterAdapter;
import com.faravy.database.HealthCenterDataSource;
import com.faravy.icare.AddCareCenterActivity;
import com.faravy.icare.GoogleMapActivity;
import com.faravy.icare.R;
import com.faravy.modelclass.HealthCenter;

public class HealthCenterFragment extends Fragment {

	ListView mList = null;
	HealthCenterDataSource mDataSource = null;
	CareCenterAdapter mAdapter = null;
	ArrayList<HealthCenter> mCenterlist = null;
	ImageButton mFab;
	Integer mPosition = 0;

	public HealthCenterFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_doctor, container,
				false);
		mFab = (ImageButton) rootView.findViewById(R.id.fab);
		mList = (ListView) rootView.findViewById(R.id.doctorList);
		mDataSource = new HealthCenterDataSource(getActivity());

		// Getting All Doctor list from database
		mCenterlist = mDataSource.getAllCenter();

		mAdapter = new CareCenterAdapter(getActivity(), mCenterlist);

		// Set each row in list using custom adapter
		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mPosition = position;
				Intent mIntent = new Intent(getActivity(),
						GoogleMapActivity.class);
				String sId = mCenterlist.get(mPosition).getmId();
				mIntent.putExtra("mId", sId);
				startActivity(mIntent);

			}
		});

		mFab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(),
						AddCareCenterActivity.class));

			}
		});

		return rootView;
	}
}
