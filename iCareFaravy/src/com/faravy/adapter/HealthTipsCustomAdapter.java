package com.faravy.adapter;

import java.util.ArrayList;

import com.faravy.icare.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



public class HealthTipsCustomAdapter extends ArrayAdapter<String> {

	// Variable Declaration
	Activity mContext = null;
	ArrayList<String> mNameList = null;
	ArrayList<String> mTipsList = null;

	public HealthTipsCustomAdapter(Activity context,
			ArrayList<String> eNameList, ArrayList<String> eTipsList) {
		super(context, R.layout.activity_health_tips_single_row, eNameList);
		this.mContext = context;
		this.mNameList = eNameList;
		this.mTipsList = eTipsList;

	}

	@SuppressLint({ "ViewHolder", "InflateParams" })
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater mInflater = mContext.getLayoutInflater();
		View rowView = mInflater.inflate(
				R.layout.activity_health_tips_single_row, null, true);

		// definition - gives variable a reference
		TextView mTvName = (TextView) rowView
				.findViewById(R.id.tvHealthTipsName);
		TextView mTvList = (TextView) rowView
				.findViewById(R.id.tvHealthTipsTips);

		// set text in view
		mTvName.setText(mNameList.get(position));
		mTvList.setText(mTipsList.get(position));

		return rowView;
	}
}
