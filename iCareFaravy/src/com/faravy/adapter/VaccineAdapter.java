package com.faravy.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.faravy.icare.R;
import com.faravy.modelclass.Vaccine;

public class VaccineAdapter extends ArrayAdapter<Vaccine> {

	Activity mContext = null;
	Vaccine mVaccine = null;
	ArrayList<Vaccine> mVaccineList = null;

	public VaccineAdapter(Activity context, ArrayList<Vaccine> objects) {
		super(context, R.layout.adapter_vaccine, objects);
		this.mContext = context;
		this.mVaccineList = objects;
	}

	// holder Class to contain inflated xml file elements
	static class ViewHolder {

		public TextView name = null;
		public TextView detail = null;
		public TextView date = null;
		public TextView time = null;
	}

	// Create ListView row
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// Get Model object from Array list
		mVaccine = mVaccineList.get(position);
		ViewHolder mVHolder = null;

		View rowView = convertView;
		if (convertView == null) {

			// Layout inflater to call external xml layout ()
			LayoutInflater inflater = mContext.getLayoutInflater();

			rowView = inflater.inflate(R.layout.adapter_vaccine, parent, false);
			mVHolder = new ViewHolder();
			mVHolder.name = (TextView) rowView.findViewById(R.id.showName);
			mVHolder.detail = (TextView) rowView.findViewById(R.id.showDetail);
			mVHolder.date = (TextView) rowView.findViewById(R.id.showDate);
			mVHolder.time = (TextView) rowView.findViewById(R.id.showTime);

			rowView.setTag(mVHolder);

		} else
			mVHolder = (ViewHolder) rowView.getTag();

		mVHolder.name.setText(mVaccine.getmName().toString());
		mVHolder.detail.setText(mVaccine.getmDetails().toString());
		mVHolder.date.setText(mVaccine.getmDate().toString());
		mVHolder.time.setText(mVaccine.getmTime().toString());

		return rowView;
	}

}
