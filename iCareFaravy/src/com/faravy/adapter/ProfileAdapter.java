package com.faravy.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.faravy.icare.R;
import com.faravy.modelclass.Profile;

public class ProfileAdapter extends ArrayAdapter<Profile> {

	Activity mContext = null;
	Profile mProfile = null;
	ArrayList<Profile> mProfileList = null;

	public ProfileAdapter(Activity context, ArrayList<Profile> objects) {
		super(context, R.layout.adapter_profile, objects);
		this.mContext = context;
		this.mProfileList = objects;

	}

	// holder Class to contain inflated xml file elements
	static class ViewHolder {

		public TextView name ;
		public TextView dob = null;
		public TextView height = null;
		public TextView weight = null;
	}

	// Create ListView row
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// Get Model object from Array list
		mProfile = mProfileList.get(position);
		ViewHolder mVHolder = null;

		View rowView = convertView;
		if (convertView == null) {

			// Layout inflater to call external xml layout ()
			LayoutInflater inflater = mContext.getLayoutInflater();

			rowView = inflater.inflate(R.layout.adapter_profile, parent, false);
			mVHolder = new ViewHolder();
			mVHolder.name = (TextView) rowView.findViewById(R.id.addName);
			mVHolder.dob = (TextView) rowView.findViewById(R.id.addBirthDay);
			mVHolder.height = (TextView) rowView.findViewById(R.id.addHeight);
			mVHolder.weight = (TextView) rowView.findViewById(R.id.addWeight);
			
			rowView.setTag(mVHolder);

		} else
			mVHolder = (ViewHolder) rowView.getTag();

		mVHolder.name.setText(mProfile.getmName().toString()+"'s Profile");
		mVHolder.dob.setText(mProfile.getmDOB().toString()+" years old");
		mVHolder.height.setText(mProfile.getmHeight().toString()+" c.m.");
		mVHolder.weight.setText(mProfile.getmWeight().toString()+" kg");
		
		return rowView;
	}

}
