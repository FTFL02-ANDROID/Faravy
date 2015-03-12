package com.faravy.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.faravy.icare.R;
import com.faravy.modelclass.Diet;

public class DietAdapter extends ArrayAdapter<Diet> {

	Activity mContext = null;
	Diet mDiet = null;
	ArrayList<Diet> mDietList = null;

	public DietAdapter(Activity context, ArrayList<Diet> objects) {
		super(context, R.layout.adapter_diet, objects);
		this.mContext = context;
		this.mDietList = objects;
	}

	// holder Class to contain inflated xml file elements
	static class ViewHolder {

		public TextView name=null;
		public TextView menu = null;
		public TextView date = null;
		public TextView time = null;
	}

	// Create ListView row
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// Get Model object from Array list
		mDiet = mDietList.get(position);
		ViewHolder mVHolder = null;

		View rowView = convertView;
		if (convertView == null) {

			// Layout inflater to call external xml layout ()
			LayoutInflater inflater = mContext.getLayoutInflater();

			rowView = inflater.inflate(R.layout.adapter_diet, parent, false);
			mVHolder = new ViewHolder();
			mVHolder.name = (TextView) rowView.findViewById(R.id.showName);
			mVHolder.menu = (TextView) rowView.findViewById(R.id.showMenu);
			mVHolder.date = (TextView) rowView.findViewById(R.id.showDate);
			mVHolder.time = (TextView) rowView.findViewById(R.id.showTime);

			rowView.setTag(mVHolder);

		} else
			mVHolder = (ViewHolder) rowView.getTag();

		mVHolder.name.setText(mDiet.getmName().toString());
		mVHolder.menu.setText(mDiet.getmManu().toString());
		mVHolder.date.setText(mDiet.getmDate().toString());
		mVHolder.time.setText(mDiet.getmTime().toString());

		return rowView;
	}

}
