package com.faravy.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.faravy.icare.R;
import com.faravy.modelclass.Doctor;

public class DoctorAdapter extends ArrayAdapter<Doctor> {

	Activity mContext = null;
	Doctor mDoctor = null;
	ArrayList<Doctor> mDoctorList = null;

	public DoctorAdapter(Activity context, ArrayList<Doctor> objects) {
		super(context, R.layout.adapter_doctor, objects);
		this.mContext = context;
		this.mDoctorList = objects;

	}

	// holder Class to contain inflated xml file elements
	static class ViewHolder {

		public TextView name;
		public TextView details = null;
		public TextView appoinment = null;
		public TextView phone = null;
		public TextView email = null;
	}

	// Create ListView row
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// Get Model object from Array list
		mDoctor = mDoctorList.get(position);
		ViewHolder mVHolder = null;

		View rowView = convertView;
		if (convertView == null) {

			// Layout inflater to call external xml layout ()
			LayoutInflater inflater = mContext.getLayoutInflater();

			rowView = inflater.inflate(R.layout.adapter_doctor, parent, false);
			mVHolder = new ViewHolder();
			mVHolder.name = (TextView) rowView.findViewById(R.id.addName);
			mVHolder.details = (TextView) rowView.findViewById(R.id.addDetails);
			mVHolder.appoinment = (TextView) rowView
					.findViewById(R.id.addAppointment);
			mVHolder.phone = (TextView) rowView.findViewById(R.id.addPhone);
			mVHolder.email = (TextView) rowView.findViewById(R.id.addEmail);

			rowView.setTag(mVHolder);

		} else
			mVHolder = (ViewHolder) rowView.getTag();

		mVHolder.name.setText("Dr."+mDoctor.getmName().toString());
		mVHolder.details.setText(mDoctor.getmDetails().toString());
		mVHolder.appoinment.setText(mDoctor.getmAppoinment().toString());
		mVHolder.phone.setText(mDoctor.getmPhone().toString());
		mVHolder.email.setText(mDoctor.getmEmail().toString());

		return rowView;
	}

}
