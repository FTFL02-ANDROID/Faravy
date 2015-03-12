package com.faravy.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.faravy.icare.R;
import com.faravy.modelclass.HealthCenter;

public class CareCenterAdapter extends ArrayAdapter<HealthCenter> {

	Activity mContext = null;
	HealthCenter mCenter = null;
	ArrayList<HealthCenter> mCenterList = null;

	public CareCenterAdapter(Activity context, ArrayList<HealthCenter> objects) {
		super(context, R.layout.adapter_center, objects);
		this.mContext = context;
		this.mCenterList = objects;

	}

	// holder Class to contain inflated xml file elements
	static class ViewHolder {

		public TextView name;
		public TextView address = null;
		public TextView latitude = null;
		public TextView longitude = null;
	}

	// Create ListView row
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// Get Model object from Array list
		mCenter = mCenterList.get(position);
		ViewHolder mVHolder = null;

		View rowView = convertView;
		if (convertView == null) {

			// Layout inflater to call external xml layout ()
			LayoutInflater inflater = mContext.getLayoutInflater();

			rowView = inflater.inflate(R.layout.adapter_center, parent, false);
			mVHolder = new ViewHolder();
			mVHolder.name = (TextView) rowView.findViewById(R.id.addName);
			mVHolder.address = (TextView) rowView.findViewById(R.id.addAddress);
			mVHolder.latitude = (TextView) rowView
					.findViewById(R.id.addLatitude);
			mVHolder.longitude = (TextView) rowView
					.findViewById(R.id.addLongitude);

			rowView.setTag(mVHolder);

		} else
			mVHolder = (ViewHolder) rowView.getTag();

		mVHolder.name.setText(mCenter.getmName().toString());
		mVHolder.address.setText(mCenter.getmAddress().toString());
		mVHolder.latitude.setText(mCenter.getmLatitude().toString());
		mVHolder.longitude.setText(mCenter.getmLongitude().toString());

		return rowView;
	}

}